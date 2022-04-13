import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_unity_widget/flutter_unity_widget.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:untitled/main.dart';
import 'package:untitled/mainpage.dart';

void main() {

  runApp(MaterialApp(
      home: UnityDemoScreen()
  ));

}

class UnityDemoScreen extends StatefulWidget {

  UnityDemoScreen({Key? key}) : super(key: key);

  @override
  _UnityDemoScreenState createState() => _UnityDemoScreenState();




}

class _UnityDemoScreenState extends State<UnityDemoScreen>{
  static final GlobalKey<ScaffoldState> _scaffoldKey =
  GlobalKey<ScaffoldState>();
  late UnityWidgetController _unityWidgetController;


    Widget build(BuildContext context) {

      return Scaffold(
        key: _scaffoldKey,
        body: SafeArea(
          bottom: false,
          child: WillPopScope(
            onWillPop: () async {
              showDialog(
                  context: context,
                  builder: (context) => AlertDialog(
                    title: Text("Do you want to exit the app?"),
                    actions: <Widget>[
                      FlatButton(
                        child: Text("Yes"),
                        onPressed: ()=>   {
                          Navigator.pushAndRemoveUntil(context, MaterialPageRoute(
                            builder: (BuildContext context) =>
                                MainPage()), (route) => false),
                          _unityWidgetController.unload(),
                        }
                      ),
                      FlatButton(
                        child: Text("No"),
                        onPressed: ()=>Navigator.pop(context, true),
                      )
                    ],
                  )
              );
              return await Future.value(true);
            },
            child: Container(
              child: UnityWidget(
                onUnityCreated: onUnityCreated,
              ),
            ),
          ),
        ),
      );
    }





  // Callback that connects the created controller to the unity controller
  void onUnityCreated(controller) {
    _unityWidgetController = controller;
    _unityWidgetController.postMessage('Game', 'setId', 'yoojinjangjang',);

  }



}


