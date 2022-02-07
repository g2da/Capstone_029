import torch
import torch.nn as nn
from torchvision import models,transforms
from PIL import Image

transform = transforms.Compose([
    transforms.Resize(256),transforms.ToTensor(),
    transforms.CenterCrop(224),
    transforms.Normalize(mean=[0.485, 0.456, 0.406],std=[0.229, 0.224, 0.225]             )
])

model = models.resnet50(pretrained = True)
model.eval()

with open("1.txt","r",encoding="utf-8") as f:
    classes = [line.strip() for line in f.readlines()]

def predict_image2(image):
    img_t = transform(image)
    batch_t = torch.unsqueeze(img_t, 0).cuda()
    out = model(batch_t)
    _, indices = torch.sort(out, descending=True)
    percentage = torch.nn.functional.softmax(out, dim=1)[0] * 100
    # result = [(classes[idx], percentage[idx].item()) for idx in indices[0][:1]]
    result = [classes[idx] for idx in indices[0][:1]]
    #print(result)
    result = result[0].split(':')
    result = result[1][2:-2].split(',')

    res_eng = result[0]
    res_kor = result[1]

    return res_eng, res_kor.strip()