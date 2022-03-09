import 'package:flutter/material.dart';

class JoinLogin_TFF extends StatelessWidget {

  final String hint;
  final funcValidator;
  final controller;

  const JoinLogin_TFF({required this.hint, this.funcValidator, this.controller});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: TextFormField(
        controller: controller,
        validator: funcValidator,
        obscureText: hint=="비밀번호를" ? true : false,
          decoration: InputDecoration(
            hintText: "$hint 입력하시오.",
            enabledBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(20),
            ),
            focusedBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(20),
            ),
            errorBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(20),
            ),
            focusedErrorBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          )
      ),
    );
  }
}