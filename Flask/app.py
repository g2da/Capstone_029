import torch
import io

import pandas as pd
import torch.nn as nn
from torchvision import models, transforms

from PIL import Image
from flask import Flask, jsonify, request
import pymysql


app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False
model = models.resnet50(pretrained=True)  # ImageNet의 1000개 클래스를 학습
model.eval()  # autograd 끄기

image_transforms = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])


# db 접속 함수
def db_connector():
    db = pymysql.connect(
        user='yoojinjangjang',  # user name
        passwd='aledma123!',  # 패스워드
        host='spring-abeec.ciqmuaoilajl.us-east-1.rds.amazonaws.com',  # DB가 있는 host
        port=3306,
        db='abeec',  # 연결할 DB 이름
        charset='utf8mb4'  # 인코딩 설정, 한글 깨짐 방지
    )
    cursor = db.cursor()
    sql = '''SELECT * FROM voca;'''
    cursor.execute(sql)
    result = cursor.fetchall()
    db.close()
    return result


# 수정한 전처리 과정
def transform_Image(image_bytes):
    # input_transforms = [transforms.Resize(256),  # 이미지 준비를 위해 여러 TorchVision transforms 사용
    #                     transforms.ToTensor(),  # 나중에 안 되면 밑이랑 순서를 바꿔봐 ~
    #                     transforms.CenterCrop(224),
    #                     transforms.Normalize(mean=[0.485, 0.456, 0.406],
    #                                          std=[0.229, 0.224, 0.225])]  # ImageNet 모델 입력에 대한 표준 정규화
    # my_transforms = transforms.Compose(input_transforms)
    # timg = my_transforms(infile)  # PIL 이미지를 적절한 모양의 PyTorch 텐서로 변환
    img = Image.open(io.BytesIO(image_bytes))
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




@app.route('/join', methods=['POST','GET'])
def index():
    if request.method=='POST':
        file = request.files['file']
        img_bytes = file.read()
        input_tensor = transform_Image(image_bytes=img_bytes)
        korean, english = predict_image(input_tensor)
        return jsonify({'korean': english, 'english': korean})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
