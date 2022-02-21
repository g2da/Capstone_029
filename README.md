# Capstone_029

ABeeC Project

---

# DataBase

### 1. Mysql 다운로드

[참고 사이트](https://velog.io/@joajoa/MySQL-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C-%EB%B0%8F-%EC%84%A4%EC%B9%98-%EB%B0%A9%EB%B2%95) 8.0 버전 사용중입니다. 환경변수 설정 꼭 하셔야 되요!

### 2. DB 폴더의 .sql파일 import

[참고 사이트](https://rkatk1523.tistory.com/25) 위의 다운로드 이후 mysql서버가 실행중이여야 합니다. `.sql파일`의 경로에 한글이 포함될경우 잘 실행되지 않은 것 같습니다. 데이테베이스 이름은 abeec로 생성해주세용. 밑의 사진에 밑줄 그은거 역슬래시 아니에요 ㅠ (역슬래신줄 알고 제가 헤맷어서 ..ㅎ )  
![](https://images.velog.io/images/yoojinjangjang/post/9009fa03-83a7-41a6-938e-9b4894783c42/image.png)

---

# Server

### 1. Intellij 다운로드

[참고 사이트](https://goddaehee.tistory.com/215) Community 버전으로 공짜 있는데 학생 메일 인증하면 Ultimate 다운로드 가능해요. 사이트 링크 걸어놨습니다.

### 2. server 폴더의 abeec 프로젝트 실행

오른쪽위에 실행버튼 누르면 서버가 동작합니다.  
request url 이랑 요청 방식, 요청 정보는 벨로그에 작성해뒀어요 링크 걸어둘게요.  
[abeec server 기능 명세 벨로그](https://velog.io/@yoojinjangjang/ABeeC-%EC%84%9C%EB%B2%84-%EA%B5%AC%EC%B6%95)
![](https://images.velog.io/images/yoojinjangjang/post/8239619e-0583-4ae6-a49e-fa1a06022d22/image.png)

- 2/2 일자 : ID중복검사, 회원가입, 로그인 기능 개발 -- **유진** [안되는거 말해주세요 이상한거도 말해주세요 ]
- 2/14일자 : 단어장 개발
- 2/21일자 : 서버 배포 ( DB 서버& 웹서버 ) -- 퍼블릭IP 카톡방 공유, 서버 배포 관련 내용  
  [벨로그 - EC2](https://velog.io/@yoojinjangjang/AWS-Spring-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC-EC2)  
  [벨로그 - RDS](https://velog.io/@yoojinjangjang/AWS-EC2-Spring-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC)

# Server API

> #### 요청 Url이나 요청 방식, 요청 데이터형식은 Controller부분에 작성해 놨습니다.

# 1. 데이터베이스 구축

MySql을 이용한 데이터베이스를 구축하고자 하였으며, user,my_voca,voca 테이블을 생성하였다.

## User

id,name,password,age,phone,words_count,level 필드

## my_voca

user_id,voca_id,image 필드

## voca

english,korea 필드

- 이미지넷 파일 db에 저장 필요 [ ]

---

# 2. 서버 구축

## 엔티티 생성

User , MyVoca , Voca 엔티티를 생성하였다.

### 1] User

primary key : **id**
**name,password,age,phone,wordsCount,level**로 구성된다.

### 2] MyVoca

primary key 인**id** 와 **user_id**,**voca_id**,**image** 로 구성된다.
user_id는 user entity 를 참조하는 외래키 이다. ( N대1)
voca_id는 voca entity 를 참조하는 외래키 이다. (N대1)

### 3] Voca

primary key 인 **id** 와 **english** , **korean** 으로 구성된다.

---

## 서비스

### 1] JoinService

회원 가입시 id에 대한 중복 검사와 회원정보를 등록,로그인하는 서비스 기능을 제공하는 클래스이다. **@Service** 어노테이션은 해당 클래스가 서비스로서 빈으로 등록되게 한다.

#### idCheck 메소드

넘어온 id 값을 user table에서 찾은 후 값이 없는 경우 1을 값이 중복되는 경우 -1을 전송한다.

#### saveUser 메소드

넘어온 userDto 를 데이터베이스에 저장해주기 위하여 **entity**로 변경해준다. 해당 엔티티를 User 테이블에 저장한다. 반환은 Dto타입으로 해주기 위하여 entity를 다시 dto 로 변경한뒤 반환해준다.

#### loginCheck 메소드

넘어온 id값으로 가져온 user 객체의 password 값과 넘어온 password 값이 일치하는지 검사하여 일치하는 경우 1을 일치하지 않는 경우 -1을 반환한다.

### 2] 단어찾기

### 3] VocaService

#### getMyVocas 메소드

id값을 받아서 해당 사용자를 user 데이터베이스에서 찾은뒤 해당 사용자가 학습한 단어들을 MyVocaDto 타입으로 변환한뒤 list 로 반환해준다.

### 4] 일일 미션

### 5] 친구 목록

### 6] 게임

---

## 컨트롤러

### 1] JoinController

회원가입과 아이디 중복검사,로그인의 컨트롤러를 제공하는 클래스이다.

#### ** idCheck 메소드** : 퍼블릭IP:8080/abeec/join/{id} 의 **get**요청

id 의 중복을 검사한다.

- **request** : String 형태의 id - path variable
- **response** : 중복시 "another id is required", 중복이 아닐시 id

#### join 메소드 : 퍼블릭IP:8080/abeec/join 의 **post**요청

회원가입 기능을 수행한다.

- **request** : json - user 정보 ( id,password,age,phone,name)
- **response** : 유효검사(validation)통과시 - user 정보 (id,password,age,phone,name,level,words_count)
  ![](https://images.velog.io/images/yoojinjangjang/post/5d668986-c11a-4d03-9117-45a66656352e/image.png)

  유효검사 미통과시 - 400에러와 해당 에러 field명,에러메세지  
  ![](https://images.velog.io/images/yoojinjangjang/post/a2eb8cd4-6763-460b-86a3-fbd3c16c18f1/image.png)

  > 해당 유효검사 항목은 밑의 **user dto** 부분에서 확인 가능하다.

#### login 메소드 : `퍼블릭IP:8080/abeec/login?id=""&password=""` 의 get 요청

로그인 기능을 수행한다.

- **request** : String 형태의 id와 String 형태의 password - query parameter
- **response** : login 완료시 1 , login 실패시 -1 을 전송

### 2] 단어찾기

[참고](https://bohyeon-n.github.io/deploy/web/image-upload.html)  
[참고](https://eastflag.co.kr/fullstack/rest-with-spring/spring-rest_image/)

### 3] VocaController

각 사용자가 학습한 단어들을 반환해주는 클래스이다.

#### GetVocaList 메소드 : 퍼블릭IP:8080/abeec/voca_list/{id}의 get 요청

해당 id의 사용자가 학습한 단어 리스트를 반환한다.

- **request** : String 형태의 id - path variable
- **respone** :

1. user id 가 db내에 존재시 - 단어리스트 (english,korean,image)  
   ![](https://images.velog.io/images/yoojinjangjang/post/4d9bf219-0008-4558-bc1d-5e60e068e512/image.png)

2. user id 가 db내에 존재하지 않을 시 - 400 에러

---

## DTO

> 엔티티를 직접 교환하는 것이 아닌 클라이언트 단에서 사용할 데이터를 dto 로 구성한다.

### 1] user dto

사용자 dto이다.

- **id** : not blank로 null,공백이 불가하다.
- **name** : not blank
- **password** : not blank , 글자수 : min 4~max 11
- **age** : not blank, max 값이 100살
- **phone** : 000-1234-1234 또는 01012341234 정규식 표현 -> `@Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$") `
- **wordsCount** : 맞힌 단어 개수
- **level** : 레벨

### 2] myvoca dto

학습단어 dto이다.

- **english** : 영단어
- **korean** : 한국댠어
- **image** : 이미지

---

# 에러모음

### 1] cannot resolve symbol

[참고자료](https://ottl-seo.tistory.com/entry/IntelliJ-Cannot-resolve-symbol-%EC%97%90%EB%9F%AC-%ED%95%B4%EA%B2%B0)
