# myshopSpring
 💡나만의 쇼핑몰 만들기
☄️ stack
<br>
<img src= "https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white"
	>
<img src= "https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white">
   +jpa
   +thymeleaf
   +mysql

-Restful api
 로그인, 회원가입, 게시판
 관리자모드/유저모드 구현
 관리자모드 상품 삭제 수정 기능 구현
 6/22 경로수정

-아임포트 api 이용하여 결제기능 구현.

<h3>이미지 경로지정</h3>
폴더 경로를 mysql 테이블에 저장하는 방식.
-> 경로설정 필요. absolutepath 부분.
-> 원하는 경로에 fileUpload/images 폴더 생성후 해당경로를 webconfig, AdminController 부분에 지정해준다.

#☄️Trouble shooting
이미지 저장방식
-> 경로를 db에 저장하고 경로를 따로 지정해주는 방식으로 해결
absolutepath 변수에 경로를 지정해줌.
