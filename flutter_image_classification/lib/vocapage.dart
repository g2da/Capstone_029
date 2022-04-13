import 'dart:convert';
import 'dart:developer';
import 'dart:io';


import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:image_picker/image_picker.dart';
import 'package:http/http.dart' as http;

class SearchVoca extends StatefulWidget {

  @override
  _SearchVocaState createState() => _SearchVocaState();
}

class _SearchVocaState extends State<SearchVoca> {

  final ImagePicker _picker = ImagePicker();
  File? image;

  Future pickImage() async {
    try {
      final XFile? image = await _picker.pickImage(source: ImageSource.camera);
      if (image == null) return;

      final imageTemporary = File(image.path);
      setState(() {
        this.image = imageTemporary;
      });

    } on PlatformException catch (e) {
      print("$e");
    }
  }

  uploadImage() async {
    File imageFile = File(image!.path);
    List<int> imageBytes = imageFile.readAsBytesSync();
    String base64Image = base64Encode(imageBytes);
    print("~~~~~~~~~~~~~~");
    print(base64Image);
    print("~~~~~~~~~~~~~~");
   // log(jsonEncode({'image':'$base64Image','id':'abeec'}));

    Uri url = Uri.parse('http://54.157.224.91:5000/test'); //https 쓰면 handshake exception 에러 발생
    http.Response response = await http.post(
      url,
      headers: <String, String> {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({'image': '$base64Image','id': 'abeec'}),
    );
    var responseBody = utf8.decode(response.bodyBytes);
    var responseItems = jsonDecode(responseBody);
    print("===========");
    print(responseBody);
    print("===========");

    //if(responseItems['duplicate'] == "duplicate")
    //  return ShowDialog(baseImage64,responseItems['id']);
    //else
    //  return; //모바일 디비에 저장

    // 중복시 응답 형식
    // {'duplicate':'duplicate','id':'id'}
    // 중복이 아닐 시 응답 형식
    // {'english':'영단어','korean':'한글'}
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("단어찾기 화면"),
      ),
      body: Container(
        padding: EdgeInsets.all(32),
        child: Column(
          children: [
            const SizedBox(height: 24,),
            Text("ABeeC", style: TextStyle(fontSize: 48, fontWeight: FontWeight.bold),),
            Spacer(),
            image != null ? Image.file(image!,
              width: 300, height: 300, fit: BoxFit.cover,) : FlutterLogo(size: 300),
            //const SizedBox(height: 20,),
            ElevatedButton(
              onPressed: () {pickImage();},
              child: const Text("사진 찍기"),
              style: ElevatedButton.styleFrom(primary: Colors.amber.shade300),
            ),
            ElevatedButton(
              onPressed: () {uploadImage();},
              child: const Text("단어 찾기"),
              style: ElevatedButton.styleFrom(primary: Colors.amber.shade300),
            ),
            ElevatedButton(onPressed: () {ShowDialog();}, child: Text("체크체크")),
            Spacer(),
          ],
        ),
      ),
    );
  }

  // void ShowDialog(String base64Image,String id) {
  void ShowDialog() {
    showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return AlertDialog(
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20)),
            title: Column(
              children: <Widget>[
                Text("중복 확인"),
              ],
            ),
            content: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text("내 단어장에 이미 있는 단어입니다."),
                Text("사진을 변경하시겠습니까?"),
              ],
            ),
            actions: <Widget>[
              TextButton(onPressed: () {ifDuplicate();}, child: Text("YES")),
              // TextButton(onPressed: () {ifDuplicate(base64Image,id);}, child: Text("YES")),
              TextButton(onPressed: () {Get.back();}, child: Text("NO")),
            ],
          );
        });
  }

  //ifDuplicate(String base64Image,String id) async {
  ifDuplicate() async {
    Uri url = Uri.parse('http://54.157.224.91:5000/dbinsert');
    http.Response response = await http.post(
      url,
      headers: <String, String> {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode([{'response': 'yes'}]),
      //body: jsonEncode({'image':'$base64Image','id':'id'}),  // id - my_voca 테이블의 id update my_voca set image = 해당이미지 where id = id;
    );
    print("------------");
    print(response.body);
    print("------------");
  }

}
// 덮어쓰기 할 시 요청 :
//{ 'image' : '$base64Image', 'id' : 'id' }
// 해당 image를 서버에 저장하고 데이터베이스 my_voca table에 해당 'id'의 'image' 필드를 새로 갱신하기