import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

Future<List<Post>> fetchPost() async {
  final response = await http.get(Uri.parse('https://jsonplaceholder.typicode.com/posts'));

  if (response.statusCode == 200) {
    List list = jsonDecode(response.body);
    var postList = list.map((element) => Post.fromJson(element)).toList();
    return postList;
  } else {
    throw Exception('불러오기 실패');
  }
}

class Post {
  final int userId;
  final int id;
  final String title;
  final String body;

  Post({required this.userId, required this.id, required this.title, required this.body});

  factory Post.fromJson(Map<String, dynamic> json) {
    return Post(
      userId: json['userId'],
      id: json['id'],
      title: json['title'],
      body: json['body'],
    );
  }
}


class MyVoca extends StatefulWidget {

  @override
  _MyVocaState createState() => _MyVocaState();
}

class _MyVocaState extends State<MyVoca> {

  Future<List<Post>>? postLists;

  @override
  void initState() {
    super.initState();
    postLists = fetchPost();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ABeeC 단어장'),
      ),
      body: Center(
        child: FutureBuilder<List<Post>>(
          future: postLists,
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              return ListView.builder(
                itemCount: snapshot.data!.length,
                itemBuilder: (context, index) {
                  Post post = snapshot.data![index];
                  return Card(
                      child: ListTile(
                        title: Text(post.title),
                      )
                  );
                },
              );
            } else if (snapshot.hasError) {
              return Text("${snapshot.error}");
            }
            return CircularProgressIndicator();
          },
        ),
      ),
    );
  }
}

  /*
  Widget VocaBlock(Post post, int idx) {
    return Container(
      child: Column(
        children: <Widget>[
          Text(post.title, style: TextStyle(fontSize: 20, color: Colors.white),),
        ],
      ),
      padding: EdgeInsets.only(top: 20.0),
      color: idx % 2 == 1 ? Colors.yellow:Colors.brown[300],
    );
  }
}
 */
