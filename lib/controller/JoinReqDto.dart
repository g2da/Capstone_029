class JoinReqDto {
  final String? id;
  final String? name;
  final String? password;
  final int? age;
  final String? phone;

  JoinReqDto(this.id, this.name, this.password, this.age, this.phone);

  Map<String, dynamic> toJson() => {
    "id":id,
    "name":name,
    "password":password,
    "age":age,
    "phone":phone,
  };
}