import 'package:capstone_loginjoin/JoinPage.dart';
import 'package:capstone_loginjoin/MainPage.dart';
import 'package:capstone_loginjoin/controller/user_controller.dart';
import 'package:capstone_loginjoin/joinlogin_tff.dart';
import 'package:capstone_loginjoin/user/user.dart';
import 'package:capstone_loginjoin/user/user_repository.dart';
import 'package:capstone_loginjoin/validator.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class LoginPage extends StatelessWidget {

  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("로그인 화면"),
      ),
      body: ListView(
        children: [
          Container(
            alignment: Alignment.center,
            height: 200,
            child: Text("ABeeC", style: TextStyle(fontSize: 40,)),
          ),
          _loginForm(),
        ],
      ),
    );
  }

  Widget _loginForm() {
    return Form(
      key: _formKey,
      child: Column(
      children: [
        JoinLogin_TFF(hint: "아이디를", funcValidator: validateId(),),
        JoinLogin_TFF(hint: "비밀번호를", funcValidator: validatePassword(),),
        ElevatedButton(
          onPressed: () {
            if(_formKey.currentState!.validate()) {
              Get.to(MainPage());
            }
          },
          child: Text("로그인하기"),
        ),
        TextButton(
          onPressed: () {
            Get.to(JoinPage());},
          child: Text("회원가입하기"),
        ),
      ],
    ),
    );
  }
}
