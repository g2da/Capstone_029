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
    print(jsonify({'english': output_english, 'korean': output_korean}))
    if result:
        return jsonify({'english': output_english, 'korean': output_korean})  # 'insert complete'
    if not result:
        return jsonify({'english': 'no', 'korean': output_korean})


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
    percentages = torch.nn.functional.softmax(out, dim=1)[0] * 100
    '''ex) 사진 결과의 정확도라기보단, 바나나 사진에 대한 분류 결과가 바나나, 사과, 포도 등등 순서라면
    바나나 50%, 사과 30%, 포도 15%, 기타 5% 의 확률로 이미지가 일치한다. 라는 걸 나타내는 변수임
    예를 한번 더 들자면, 바나나 사진을 넣고 percentage가 80 나오면, 전체 1000개 클래스 중에서
    바나나일 확률이 80이라는 뜻. 나머지 999개 클래스가 20을 나눠가질것이고.
    
    percentage 값도 함께 return 해줘야하니 아래 return 변경해야합니다!
    + res_eng랑 res_kor 값을 받게 되는 곳도요!
    index() 함수랑 duplication_db() 이곳이요!
    '''
    result = [(pytorch_result[idx],percentages[idx].item()) for idx in indices[0]]
    percentage = result[0][1]
    result = result[0][0]
    res_eng = result[1]
    res_kor = result[2]
    print(percentage)
    return res_eng, res_kor,percentage


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
    output_english, output_korean,output_percentage = predict_image(input_tensor)
    duplicate_Check = duplicate_Checker(user_id, output_english)
    if not duplicate_Check:  # 중복아님
        return jsonify({'duplicate':'no','english':output_english,'korean':output_korean,'percentage':output_percentage})
    elif duplicate_Check:  # 중복임
        return jsonify({'duplicate': 'yes', 'english':output_english,'korean':output_korean})


@app.route('/save',methods=['POST','GET'])
def save_db():
    user_id = request.json['id']
    english = request.json['english']
    korean = request.json['korean']
    print(user_id)
    print(english)
    print(korean)
    db_insert = insert_word(user_id,english,korean)
    print(db_insert)
    return db_insert



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

'''
퍼블릭IP:5000/duplicate의 post요청 --> request('image':---,'id':---), response('duplicate':---,'english':---,'korean':----)
프론트 -- duplicate : yes ( 중복이라는 창 띄워줌 ) , duplicate : no ( 저장할지 말지 결정 ( 개별단어장에 영어와 한글 & o,x 버튼 ) 

퍼블릭IP:5000/save의 post요청 
저장시 ( yes 버튼 클릭시  --> request({'id':---,'english':---,'korean':---}),response({'english':---,'korean':---}) ) , 
            --> 프론트 -- 해당 개별단어장 표시 & 모바일 단어장 데베 저장 & 미션 검사  
저장안할시 -- searchVoca화면으로 리다이렉트 


'''