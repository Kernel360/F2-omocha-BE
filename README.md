# 🧸 **Omocha**

<img src="https://github.com/limbaba1120/limbaba1120_images/blob/master/notion_image/opanchu.jpeg" width="200px;" alt="Omocha Logo">

## 📖 **프로젝트 소개**

- **오타쿠를 위한 피규어, 장난감 등의 다양한 굿즈를 경매하는 서비스!**
- 희귀한 제품이 많은 피규어 시장을 타겟으로 함
- **개발 기간**
  **2024.09.09 ~ 2024.12.20**

<details>
<summary> 소개 </summary>
<p align="left"><img src="https://github.com/limbaba1120/limbaba1120_images/blob/master/notion_image/1%ED%8C%80_omocha_panel.jpg" width="800"></p>
</details>

## ⭐ **주요 기능**

<!-- 
<p align="center"><img src="" width="350"></p>
-->

### **로그인 페이지 | 메인 페이지**

| 로그인 & 회원가입 페이지                                                                                                     | 메인 페이지                                                                                                               |
|--------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| <p align="center"> <img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/login.gif" width="350"></p> | <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/mainpage.gif" width="350"></p> |
| 회원가입, 로그인을 제공합니다.                                                                                                  | 경매 게시글 전체 확인, 검색을 제공합니다.                                                                                             |
| - OAuth 2.0(Google, Naver)을 통한 로그인이 가능합니다.<br/>- 일반 회원가입으로 생성한 ID,PW로 로그인이 가능합니다.<br/>- 회원가입시 이메일 인증을 하여야 합니다.     | - 배너를 통해 여러 정보를 제공합니다.<br/>- 인기 경매, 신규 경매 상품, 마감 임박 상품을 확인 할 수 있습니다.                                                 |

---

### **경매 게시글 생성 | 입찰**

| 경매 게시글 생성                                                                                                                                 | 입찰                                                                                                                  |
|-------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/AuctionCreate.gif" width="350"></p>                 | <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/Bidding.gif" width="350"></p> |
| 새로운 경매 게시글을 생성하고 상품 정보 및 경매 기간을 설정할 수 있습니다.                                                                                               | 상품에 입찰하고 실시간으로 경쟁 입찰 상황을 확인할 수 있습니다.                                                                                |
| - 경매는 일반 경매 or 라이브 경매로 생성할 수 있음. (현재는 일반형만 가능) <br/> - 상품명, 상품 사진(최대 10개), 상품 정보, 시작가, 입찰단위, 경매 시간을 입력해야함. <br/> - 한번 등록된 경매는 수정, 삭제가 불가함 | - 판매자가 설정한 입찰 단위 맞춰 입찰 할 수 있습니다.<br/>- 즉시 구매를 통해 바로 구매 할 수 있습니다.                                                    |

---

### **채팅 | 마이페이지**

| 채팅                                                                                                                                                                                                                            | 마이페이지                                                                                                              |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/chat.gif" width="350"></p>                                                                                                              | <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/mypage.gif" width="350"></p> |
| 낙찰 후 자동으로 채팅방이 생성됩니다. 판매자와 구매자가 거래를 할 수 있습니다.                                                                                                                                                                                 | 회원 정보(찜 목록 / 판매 내역 / 입찰 내역)를 확인하고 개인 설정을 관리할 수 있습니다.                                                               |
| - 채팅방에 들어가면 WebSocket이 연결되고 상대방에게 메시지를 보낼 수 있음.   <br/> - STOMP(Simple/Streaming Text Oriented Messaging Protocal) 텍스트 기반의 메세지 프로토콜 사용 <br/> - 채팅방 입장: Topic 구독 <br/> - 채팅방에서 메세지를 송수신: 해당 Topic으로 메세지를 송신(pub), 메세지를 수신(sub) | - 회원의 기본 정보(비밀번호, 프로필 등)를 변경할 수 있습니다. <br/>- 비밀번호 변경은 일반 회원가입자만 사용 가능합니다.  <br/>- 거래내역에서 입찰 내역과 판매내역을 확인 할 수 있습니다. |

---

### **QnA | Like(찜)**

| QnA                                                                                                             | Like(찜)                                                                                                          |
|-----------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/qna.gif" width="350"></p> | <p align="center"><img src="https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/like.gif" width="350"></p> |
| 경매 물품에 대해 질문,답변을 할 수 있습니다.                                                                                      | 관심있는 경매물을 찜하고 한 곳에 모아 쉽게 확인 할 수 있습니다.                                                                            |                                                                                      |
| - 회원 : 경매 물품에 대한 질문을 작성 할 수 있습니다.<br/>- 답변이 달리면 추가 수정 및 삭제가 `불가`합니다. <br/> - 판매자 : 답변을 작성할 수 있습니다.              | - 경매물 상단의 버튼을 클릭하여 `찜` 할 수 있습니다.<br/>- 이후 다시 누르게 되면 `찜`이 해제됩니다.                                                  |

## **기술 스택**

<details>
<summary> 접기 / 펼치기</summary>

**Framework<BR>**
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=Spring-Boot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/.env-4D4D4D?style=for-the-badge&logo=dotenv&logoColor=white">
<img src="https://img.shields.io/badge/Querydsl-3E6E87?style=for-the-badge&logo=graphql&logoColor=white">

**Language<BR>**
<img src="https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><br>

**INFRA<BR>**
<img src="https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white">
<img src="https://img.shields.io/badge/AWS_RDS-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white">
<img src="https://img.shields.io/badge/AWS_S3-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white">
<img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
<img src="https://img.shields.io/badge/Redis-D82C20?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/STOMP-6A0FAD?style=for-the-badge&logo=apache&logoColor=white"><br>

**CI/CD<BR>**
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white"><br>

**Version Control<BR>**
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/GitHub-2088FF?style=for-the-badge&logo=github&logoColor=white"><br>

**Authentication<BR>**
<img src="https://img.shields.io/badge/OAuth2.0-005EA2?style=for-the-badge&logo=oauth&logoColor=white">
<img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=json-web-tokens&logoColor=white"><br>




</details>

## **System Architecture**

![SA](https://github.com/gooot/ProjectImage/blob/main/Omocha/v1/SAv2.png)

## 👨‍👩‍👧‍👦 **팀원 소개**

<table>
  <tr>
    <td align="center"><b>Frontend</b></td>
    <td align="center"><b>Frontend</b></td>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Backend</b></td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/haejinyun">
        <img src="https://avatars.githubusercontent.com/u/86779590?v=4" width="100px;" alt="윤해진"/><br />
        <sub><b>팀장 : 윤해진</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/kimeodml">
        <img src="https://avatars.githubusercontent.com/u/88065770?v=4" width="100px;" alt="김대의"/><br />
        <sub><b>팀원 : 김대의</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/limbaba1120">
        <img src="https://avatars.githubusercontent.com/u/102224840?v=4" width="100px;" alt="임건우"/><br />
        <sub><b>팀원 : 임건우</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/ss0ngcode">
        <img src="https://avatars.githubusercontent.com/u/86779839?v=4" width="100px;" alt="송해덕"/><br />
        <sub><b>팀원 : 송해덕</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/gooot">
        <img src="https://avatars.githubusercontent.com/u/26480629?v=4" width="100px;" alt="이재윤"/><br />
        <sub><b>팀원 : 이재윤</b></sub>
      </a>
    </td>
  </tr>
</table>
