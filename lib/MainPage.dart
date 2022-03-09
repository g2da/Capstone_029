import 'package:flutter/material.dart';

class MainPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("메인 화면"),
      ),
      body: Center(
        child: Text("메인화면"),
      ),
    );
  }
}
