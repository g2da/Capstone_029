import 'package:capstone_loginjoin/controller/JoinReqDto.dart';
import 'package:capstone_loginjoin/controller/LoginReqDto.dart';
import 'package:capstone_loginjoin/user/user_provider.dart';
import 'package:get/get_connect/http/src/response/response.dart';

class UserRepository {
  final UserProvider _userProvider = UserProvider();

  Future<String> join(String id, String name, String password, int age, String phone) async {
    JoinReqDto joinReqDto = JoinReqDto(id, name, password, age, phone);

    print("========");

    print(joinReqDto.toJson());
    Response response = await _userProvider.join(joinReqDto.toJson());

    print("========");
    print(response.body);
    print("========");

    dynamic body = response.body;
    String u_id = body["id"];
    return u_id;
  }
}