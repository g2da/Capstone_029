import 'package:get/get.dart';

const host = "http://54.234.11.99:8080";

class UserProvider extends GetConnect {
  Future<Response> idCheck(String id) => get("$host/abeec/join/$id");
  Future<Response> join(Map data) => post("$host/abeec/join", data);
  Future<Response> login(String id, String password) => get("$host/abeec/login?id=$id&password=$password");
}