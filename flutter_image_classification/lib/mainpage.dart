
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:untitled/unity.dart';
import 'package:untitled/vocapage.dart';

class MainPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
    return Scaffold(
      appBar: AppBar(
        title: Text("메인 화면"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
                onPressed: () {Get.to(SearchVoca());},
                child: Text("단어찾기")),
            ElevatedButton(
                onPressed: () {Get.to(UnityDemoScreen());},
                child: Text("미니게임")),
          ],
        ),
      ),
    );
  }
}