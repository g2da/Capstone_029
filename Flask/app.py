import io
import base64
import numpy as np
import torch
import cv2
from torchvision import models, transforms
from PIL import Image
from flask import Flask, jsonify, request, session, make_response
import pymysql
import werkzeug

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

db = pymysql.connect(
        user='yoojinjangjang',  # user name
        passwd='aledma123!',  # 패스워드
        host='spring-abeec.ciqmuaoilajl.us-east-1.rds.amazonaws.com',  # DB가 있는 host
        port=3306,
        db='abeec',  # 연결할 DB 이름
        charset='utf8mb4'  # 인코딩 설정, 한글 깨짐 방지
    )
cursor = db.cursor()

#PIL로 변환
def opencv2PIL(img):
    return Image.fromarray(img)

# db 접속 함수
def db_connector():
    sql = '''SELECT * FROM voca;'''
    cursor.execute(sql)
    result = cursor.fetchall()
    db.close()
    return result

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

# 전처리 과정
def transform_Image(image_bytes):
    img = Image.open(io.BytesIO(image_bytes))
    timg = image_transforms(img)
    return timg

@app.route('/')
def root():
    return "<h1>hello</h1>"

@app.route('/word', methods=['POST', 'GET'])
def word():
    base64Image = request.json[0]['image']
    imageStr = base64.b64decode(base64Image)
    nparr = np.fromstring(imageStr, dtype=np.uint8)
    img_np = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    pil_image = opencv2PIL(img_np)
    input_tensor = image_transforms(pil_image)
    korean, english = predict_image(input_tensor)
    return jsonify({'korean': english, 'english': korean})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)