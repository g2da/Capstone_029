import io
import os
import base64
import numpy as np
import torch
import base64
import cv2
from torchvision import models, transforms
from PIL import Image
from flask import Flask, jsonify, request, make_response
import pymysql
from werkzeug.utils import secure_filename

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False
model = models.resnet50(pretrained=True)
model.eval()  # autograd 끄기

image_transforms = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])


def opencv2PIL(img):
    return Image.fromarray(img)


# db 접속 함수
def db_connector():
    db = pymysql.connect(
        user='yoojinjangjang',  # user name
        passwd='aledma123!',  # 패스워드
        host='spring-abeec.ciqmuaoilajl.us-east-1.rds.amazonaws.com',  # DB가 있는 host
        port=3306,
        db='abeec',
        charset='utf8mb4'  # 인코딩 설정, 한글 깨짐 방지
    )
    cursor = db.cursor()
    sql = '''SELECT * FROM voca;'''
    cursor.execute(sql)
    result = cursor.fetchall()
    db.close()
    return result


def db_connector_check(user_name):
    db = pymysql.connect(
        user='yoojinjangjang',  # user name
        passwd='aledma123!',  # 패스워드
        host='spring-abeec.ciqmuaoilajl.us-east-1.rds.amazonaws.com',  # DB가 있는 host
        port=3306,
        db='abeec',
        charset='utf8mb4'  # 인코딩 설정, 한글 깨짐 방지
    )
    cursor = db.cursor()
    sql = '''SELECT id FROM my_voca WHERE user_id="%s";'''
    cursor.execute(sql, user_name)
    result = cursor.fetchall()
    db.close()
    return result


def duplicate_Checker(user_id, output_english):
    db = pymysql.connect(
        user='yoojinjangjang',  # user name
        passwd='aledma123!',  # 패스워드
        host='spring-abeec.ciqmuaoilajl.us-east-1.rds.amazonaws.com',  # DB가 있는 host
        port=3306,
        db='abeec',
        charset='utf8mb4'  # 인코딩 설정, 한글 깨짐 방지
    )
    cursor = db.cursor()
    sql = "SELECT * FROM my_voca WHERE user_id=%s AND english=%s;"
    cursor.execute(sql, (user_id, output_english))
    result = cursor.fetchone()
    db.close()
    return result


def insert_word(user_id, output_english, output_korean):
    db = pymysql.connect(
        user='yoojinjangjang',  # user name
        passwd='aledma123!',  # 패스워드
        host='spring-abeec.ciqmuaoilajl.us-east-1.rds.amazonaws.com',  # DB가 있는 host
        port=3306,
        db='abeec',
        charset='utf8mb4'  # 인코딩 설정, 한글 깨짐 방지
    )
    cursor = db.cursor()
    sql = '''INSERT INTO my_voca (user_id, english, korean) VALUES (%s, %s, %s);'''
    cursor.execute(sql, (user_id, output_english, output_korean))
    db.commit()
    sql = "SELECT * FROM my_voca WHERE user_id=%s AND english=%s;"
    cursor.execute(sql, (user_id, output_english))
    result = cursor.fetchone()
    if result:  # 중복아닐때 삽입하고 모바일에 영어,한글,db에 있는 id까지 보내주기
        return jsonify({'duplicate': 'no', 'english': output_english, 'korean': output_korean})  # 'insert complete'
    if not result:
        return jsonify({'insert': 'error'})


# 전처리 과정
def transform_Image(image_bytes):
    img = Image.open(io.BytesIO(image_bytes)).convert('RGB')
    timg = image_transforms(img)
    return timg


def transform_image(base64_string):
    imageStr = base64.b64decode(base64_string)
    img = Image.open(io.BytesIO(imageStr)).convert('RGB')
    timg = image_transforms(img)
    return timg


# 기존 이미지 추론
def predict_image(image):
    pytorch_result = db_connector()
    batch_t = torch.unsqueeze(image, 0)
    out = model(batch_t)
    _, indices = torch.sort(out, descending=True)
    result = [pytorch_result[idx] for idx in indices[0]]
    result = result[0]
    res_eng = result[1]
    res_kor = result[2]
    return res_eng, res_kor


@app.route('/')
def root():
    return "<h1>hello</h1>"


@app.route('/test', methods=['POST', 'GET'])
def index():
    base64Image = request.json['image']
    user_id = request.json['id']
    imageStr = base64.b64decode(base64Image)
    nparr = np.fromstring(imageStr, dtype=np.uint8)
    img_np = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    pil_image = opencv2PIL(img_np)
    input_tensor = image_transforms(pil_image)
    english, korean = predict_image(input_tensor)
    return jsonify({'english': english, 'korean': korean})


@app.route('/duplication', methods=['POST', 'GET'])
def duplication_db():
    base64Image = request.json['image']
    user_id = request.json['id']
    imageStr = base64.b64decode(base64Image)
    nparr = np.fromstring(imageStr, dtype=np.uint8)
    img_np = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    pil_image = opencv2PIL(img_np)
    input_tensor = image_transforms(pil_image)
    output_english, output_korean = predict_image(input_tensor)
    duplicate_Check = duplicate_Checker(user_id, output_english)
    if not duplicate_Check:  # 중복아님
        db_insert = insert_word(user_id, output_english, output_korean)
        return db_insert
    elif duplicate_Check:  # 중복임
        return jsonify({'duplicate': 'yes', 'id': duplicate_Check[0]})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
