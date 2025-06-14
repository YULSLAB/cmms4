######## CMMS4 개발 규칙 및 가이드
### 인프라 구성
JDK 21
Spring Boot, gradle, groovy
Maria DB (현재 개발환경:10.6.21-MariaDB)
springframework.boot' version '3.2.3'
Gradle 8.14
운영환경 : Ubuntu, 개발환경 : Windows
Thymeleaf, tailwind css 적용

### 기본 원칙
- 각 코드는 시작 부분에 이름, 기능 설명, 생성자, 생성일, 수정자, 수정일, 파라미터 주석 포함
- 각 단계별 주요 기능에 대해 간단한 설명 포함
- 연계되는 프로그램에 대해 파라미터의 type과 name을 확인할 것
- 코딩은 최대한 간단하게 명확한 방향으로 제시할 것. 유지보수성을 고려하여 디버깅이 어렵거나 코드가 난해하지 않도록 할 것 

### 파일 Naming Rule 
- Controller: 폴더명Controller.java → 예: inspectionController.java
- Entity: 폴더명.java → 예: inspection.java
- Service: 폴더명Service.java → 예: inspectionService.java
- Repository: 폴더명Repository.java → 예: inspectionRepository.java
- DTO: 폴더명DTO.java → 예: inspectionDTO.java
- View: 폴더명.html → 예: inspection.html
- 각 

## 화면 Naming Rule
- 입력: 이름Form.html
- 리스트: 이름List.html
- 상세: 이름Detail.html

## 화면 Layout 구조 
- 로그인 Login.html
- Header, body(왼쪽 site menu), footer 
- Layout.html 구성, 각 페이지는 Layout.html을 상속속
- 왼쪽 site menu의 toggle 구성 
- 각 화면은 최대한 공통적은 요소를 재활용 하도록 함   
  : inspectionForm, workorderForm 의 기본정보 동일 
    1행 id ,name
    2행 plantid, plantname, performdept, deptname, jobType
    --inspectionForm
    3행(복수) scheduleId, frequency, scheduleDate, executeDate
    4행(scheduleId에 대한 복수) itemId, itemName, itemMethod, itemUnit, itemLower, itemUpper, itemStandard, itemResult, note    
    --workorderForm
    3행(복수) itemId, itemName, itemMethod, itemResult, note
    4행 없음

    5행 notes
    6행 fileGroupId
    7행(hidden) createBy, createDate, updateBy, updateDate
- 로그인 후, 화면 상단 오른쪽에 로그인 유저 정보(logout가능, 사용자이름, siteAccess 리스트 복수인 경우 변경선택택)
## 다국어 구현
- 한국어와 영어를 기본 구현하고 resources/messages_en.properties/messages_ko.properties

## Template 구조 
templates/cmms4/
├── src/
│   ├── main/
│   │   ├── java/           /** Java 코드 */
│   │   │   ├── com/
│   │   │   │   └── cmms4/
│   │   │   │       ├── config/         /** 설정 관련련 */
│   │   │   │       ├── auth/           /** 인증 관련 */
│   │   │   │       │   ├── controller/
│   │   │   │       │   ├── service/
│   │   │   │       │   └── dto/
│   │   │   │       ├── domain/         /** 도메인 엔티티 */
│   │   │   │       │   ├── company/
│   │   │   │       │   ├── site/
│   │   │   │       │   ├── dept/
│   │   │   │       │   ├── user/
│   │   │   │       │   ├── siteAccess/
│   │   │   │       │   └── roleAuth/
│   │   │   │       ├── plantMaster/
│   │   │   │       ├── inventoryMaster/
│   │   │   │       ├── inspection/
│   │   │   │       ├── workorder/
│   │   │   │       ├── memo/
│   │   │   │       └── common/     /** 공통코드드 */
│   │   │   └──   cmms4Application.java       
│   │   │   
│   │   └── resources/           /** 리소스 */
│   │       ├── static/          /** 정적 리소스 */
│   │       │   ├── css/         /** CSS 파일 */
│   │       │   └── js/          /** JavaScript 파일 */
│   │       ├── templates/       /** HTML 템플릿 */
│   │       │   ├── Layout.html 
│   │       │   ├── auth/
│   │       │   │   ├── Login.html
│   │       │   │   └── Main.html
│   │       │   ├── domain/
│   │       │   │   ├── company/
│   │       │   │   ├── site/
│   │       │   │   ├── dept/
│   │       │   │   ├── user/
│   │       │   │   ├── siteAccess/
│   │       │   │   └── roleAuth/
│   │       │   ├── plantMaster/
│   │       │   ├── inventoryMaster/
│   │       │   ├── inspection/
│   │       │   ├── workorder/
│   │       │   ├── memo/
|   |       |   └── common/
│   │       ├── messages/       /** 다국어 메시지 */
│   │       │   ├── messages_ko.properties
│   │       │   └── messages_en.properties
│   │       └── application.properties
│   └── test/            /** 테스트 코드 */
└── build.gradle         /** 빌드 설정 파일 */
**주의:** 각 도메인 엔티티는 데이터베이스 테이블과 1:1 매핑됩니다. 

## ID 채번 규칙
- 수동: companyId, siteId, deptId, username, codeId
- 자동 숫자 일련번호 : scheduleId, itemId, memoId, commentId
- 자동이나 채번규칙 있음 : plantId(1로 시작,10자리 숫자)  , inventoryId(2로 시작,10자리 숫자), inspectionId(3로 시작,10자리 숫자), workOrderId(5로 시작,10자리 숫자), fileGroupId(테이블 이름 + YYMM + 5자리번호)

## 보안 및 로깅
- CSRF, CORS, AOP 적용
- daily log: logging_연도날짜일

## commonCode 하드코딩 
- AssetType : ASSET
- jobType : JOBYP
* 등록 화면 하단 주석으로 보여줄 것 

## user 테이블에서 userId 대신 username 사용함
