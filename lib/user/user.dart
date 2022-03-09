class User {
  final String? id;
  final String? name;
  final String? password;
  final int? age;
  final String? phone;

  User({
    this.id,
    this.name,
    this.password,
    this.age,
    this.phone,
  });

  User.fromJson(Map<String, dynamic> json)
    : id = json["id"],
      name = json["name"],
      password = json["password"],
      age =  json["age"],
      phone = json["phone"];
}