import 'package:validators/validators.dart';

Function validateId() {
  return (String? value) {
    if (value!.isEmpty) {
      return "공백은 불가능합니다.";
    }
    else if (!isAlphanumeric(value)) {
      return "영어와 숫자로만 입력해주세요.";
    }
    else {
      return null;
    }
  };
}

Function validateName() {
  return (String? value) {
    if (value!.isEmpty) {
      return "공백은 불가능합니다.";
    }
    else {
      return null;
    }
  };
}

Function validatePassword() {
  return (String? value) {
    if (value!.isEmpty) {
      return "공백은 불가능합니다.";
    }
    else if (value.length < 4 || value.length > 11) {
      return "비밀번호는 4~11 글자수로 맞춰주세요.";
    }
    else {
      return null;
    }
  };
}

Function validateAge() {
  return (String? value) {
    if (value!.isEmpty) {
      return "공백은 불가능합니다.";
    }
    else if (value.length > 2) {
      return "입력할 수 있는 나이는 99살 이하입니다.";
    }
    else {
      return null;
    }
  };
}

Function validatePhone() {
  return (String? value) {
    if (value!.isEmpty) {
      return "공백은 불가능합니다.";
    }
    else if (!matches(value, "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})")) {
      return "휴대폰번호 형식이 맞지 않습니다.";
    }
    else {
      return null;
    }
  };
}