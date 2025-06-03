# CMMS3 DB 테이블 정의서 (Full Version)

### 마스터 데이터 관리(단수 표현현)
## company

| 필드명      | 데이터 타입   | 설명          |
|:------------|:--------------|:--------------|
| companyId   | Char(5)       | 회사 ID (PK)  |
| companyName | Varchar(100)  | 회사 이름     |
| note        | Text          | 설명         |
| fileGroupId | CHAR(10)      | 첨부파일 그룹 |
| createBy    | CHAR(5)       | 생성자        |
| createDate  | DATETIME      | 생성일        |
| updateBy    | CHAR(5)       | 수정자        |
| updateDate  | DATETIME      | 수정일        |
| deleteMark  | CHAR(1)       | 삭제 마크     |

## site

| 필드명      | 데이터 타입   | 설명          |
|:------------|:--------------|:--------------|
| companyId   | Char(5)       | 회사 ID (PK)  |
| siteId    | CHAR(5)       | 사이트 ID (PK)  |
| siteName    | VARCHAR(100)  | 사이트 이름   |
| fileGroupId | CHAR(10)      | 첨부파일 그룹 |
| createBy    | CHAR(5)       | 생성자        |
| createDate  | DATETIME      | 생성일        |
| updateBy    | CHAR(5)       | 수정자        |
| updateDate  | DATETIME      | 수정일        |
| deleteMark  | CHAR(1)       | 삭제 마크     |

## dept : 한 회사 내에 dept는 1개 임. 사이트별로 다를 수 없음 

| 필드명       | 데이터 타입   | 설명                                     |
|:-------------|:--------------|:-----------------------------------------|
| companyId    | Char(5)       | 회사 ID (PK)                             |
| deptId       | Char(5)       | 부서 ID (PK)                             |
| deptName     | Varchar(100)  | 부서 이름                                |
| parentDeptId | CHAR(5)       | 상위 부서 ID (NULL이면 최상위)           |
| deptLevel    | INT           | 계층 단계 (예: 1=본사, 2=사업부, 3=파트) |
| sortOrder    | INT           | 정렬 순서                                |                  |
| note         | Text          | 설명                                     |
| siteId     | CHAR(5)       | 사이트 ID                              |
| fileGroupId  | CHAR(10)      | 첨부파일 그룹                            |
| createBy     | CHAR(5)       | 생성자                                   |
| createDate   | DATETIME      | 생성일                                   |
| updateBy     | CHAR(5)       | 수정자                                   |
| updateDate   | DATETIME      | 수정일                                   |
| deleteMark   | CHAR(1)       | 삭제마크                                |

## user : 복수의 site 담당자는 siteAccess 참조  : Spring boot에 맞게 userId 대신 username 활용함

| 필드명      | 데이터 타입   | 설명                 |
|:------------|:--------------|:---------------------|
| companyId   | Char(5)       | 회사 ID (PK)         |
| username     | Char(5)       | 사용자 ID (PK)       |
| userFullName    | Varchar(100)  | 사용자 이름          |
| password    | Varchar(100)  | 패스워드             |
| email       | Varchar(100)  | 이메일               |
| phone       | Varchar(100)  | 전화번호             |
| deptId      | Char(5)       | 부서 ID              |
| roleId        | Char(5)       | 권한 ID           |
| note        | Text          | 설명                 |
| siteAccessId  | CHAR(1)       | userSiteAccess참조자 |
| siteId    | CHAR(5)       | 사이트 ID          |
| fileGroupId | CHAR(10)      | 첨부파일 그룹        |
| createBy    | CHAR(5)       | 생성자               |
| createDate  | DATETIME      | 생성일               |
| updateBy    | CHAR(5)       | 수정자               |
| updateDate  | DATETIME      | 수정일               |
| deleteMark  | CHAR(1)       | 삭제 마크            |

## siteAccess : 이력 관리 안 함함

| 필드명      | 데이터 타입   | 설명             |
|:------------|:--------------|:-----------------|
| companyId   | Char(5)       | 회사 ID (PK)     |
| siteAccessId   | Char(1)       | siteAccess (PK)   |
| username      | Char(5)       | 사용자 ID (PK)   |
| siteId    | CHAR(5)       | 사이트 ID  |

## roleAuth : 이력 관리 안 함함

| 필드명   | 타입        | PK   | FK          | 설명                   |
|:---------|:------------|:-----|:------------|:-----------------------|
| roleId   | char(5)     | PK   |          | 권한 ID              |
| pageId   | varchar(50) | PK   |         | 페이지 ID (폴더명)       |
| authGranted | char(10)     | PK  |          | 권한 문자열 (SAVE, deleteMark,...) |


### 기준 정보관리 : 기준정보는 삭제하지 않고 deleteMarkYN 마크처리 (삭제된 데이터는 복구할 수 있도록. 기본 null, 삭제 "X")
## plantMaster

| 필드명               | 데이터 타입   | 설명                                              |
|:---------------------|:--------------|:--------------------------------------------------|
| companyId            | CHAR(5)       | 회사 ID (PK)                                      |
| plantId              | INT(10)      | 설비 ID (PK)                                      |
| plantName            | VARCHAR(100)  | 설비명                                            |
| plantLoc             | VARCHAR(100)  | 설비위치                                          |
| funcId               | CHAR(4)       | 기능 위치 코드(설비에 대한 기능분류류)                                    |
| respDept             | CHAR(5)       | 관리 부서 ID                                      |
| installDate          | Datetime      | 설치일                                            |
| assetType            | Char(5)       | 자산 타입                                         |
| depreMethod          | Char(5)       | 상각방법                                          |
| acquitionValue       | Decimal(15,2) | 취득가                                            |
| residualValue        | Decimal(15,2) | 잔존가                                            |
| manufacturer         | Varchar(100)  | 제조사                                            |
| manufacturerModel    | Varchar(100)  | 모델넘버                                          |
| manufacturerSN       | Varchar(100)  | 시리얼번호                                        |
| manufacturerSpec     | Varchar(100)  | 규격                                              |
| inspectionYN         | Char(1)       | 계획정비대상                                      |
| plannedMaintenanceYN | Char(1)       | 예방점검대상                                      |
| psmYN                | Char(1)       | PSM대상                                           |
| tagYN                | Char(1)       | TAG대상                                           |
| note                 | VARCHAR(200)  | 비고(notes 아닌 note. Naming rule에 복수형 안 씀) |
| siteId             | CHAR(5)       | 사이트 ID                                       |
| fileGroupId          | CHAR(10)      | 첨부파일 그룹                                     |
| createBy             | CHAR(5)       | 생성자                                            |
| createDate           | DATETIME      | 생성일                                            |
| updateBy             | CHAR(5)       | 수정자                                            |
| updateDate           | DATETIME      | 수정일                                            |
| deleteMark        | CHAR(1)       | 삭제 마크                                         |
## inventoryMaster

| 필드명            | 데이터 타입   | 설명                                              |
|:------------------|:--------------|:--------------------------------------------------|
| companyId         | CHAR(5)       | 회사 ID (PK)                                      |
| inventoryId       | INT(10)      | 재고 ID (PK)                                      |
| inventoryName     | VARCHAR(100)  | 재고명                                            |
| inventoryLoc      | VARCHAR(100)  | 재고 위치                                         |
| respDept          | CHAR(5)       | 관리 부서 ID                                      |
| assetType         | Char(5)       | 자산 타입                                         |
| purchaseDate      | Datetime      | 구매일                                            |
| purchaseValue     | Decimal(15,2) | 취득가                                            |
| manufacturer      | Varchar(100)  | 제조사                                            |
| manufacturerModel | Varchar(100)  | 모델넘버                                          |
| manufacturerSN    | Varchar(100)  | 시리얼번호                                        |
| manufacturerSpec  | Varchar(100)  | 규격                                              |
| note              | VARCHAR(200)  | 비고(notes 아닌 note. Naming rule에 복수형 안 씀) |
| siteId          | CHAR(5)       | 사이트 ID                                       |
| fileGroupId       | CHAR(10)      | 첨부파일 그룹                                     |
| createBy          | CHAR(5)       | 생성자                                            |
| createDate        | DATETIME      | 생성일                                            |
| updateBy          | CHAR(5)       | 수정자                                            |
| updateDate        | DATETIME      | 수정일                                            |
| deleteMark        | CHAR(1)       | 삭제 마크                                         |

### 점검 관리 : 향후 status 필드 추가하여 결재 등 상태관리 추가
## inspection

| 필드명         | 데이터 타입   | 설명             |
|:---------------|:--------------|:-----------------|
| companyId      | CHAR(5)       | 회사 ID (PK)     |
| inspectionId   | INT(10)      | 점검계획 ID (PK) |
| inspectionName | Varchar(100)  | 예방점검 이름    |
| plantId        | INT(10)      | 설비 ID          |
| jobType        | CHAR(5)       | 작업 유형        |
| performDept    | CHAR(5)       | 수행부서         |
| note           | VARCHAR(200)  | 비고             |
| fileGroupId    | CHAR(10)      | 첨부파일 그룹    |
| siteId       | CHAR(5)       | 사이트 ID      |
| fileGroupId    | CHAR(10)      | 첨부파일 그룹    |
| createBy       | CHAR(5)       | 생성자           |
| createDate     | DATETIME      | 생성일           |
| updateBy       | CHAR(5)       | 수정자           |
| updateDate     | DATETIME      | 수정일           |

## inspectionSchedule

| 필드명       | 데이터 타입   | 설명                     |
|:-------------|:--------------|:-------------------------|
| companyId    | CHAR(5)       | 회사 ID (PK)             |
| inspectionId | INT(10)      | 점검계획 ID (PK)         |
| scheduleId   | INT(2)      | 예방점검 일정번호 (PK)   |
| frequency    | Char(5)       | 점검주기(default " day") |
| scheduleDate | Datetime      | 예방점검일               |
| executeDate  | Datetime      | 실제점검일               |

## inspectionItem

| 필드명       | 데이터 타입   | 설명                   |
|:-------------|:--------------|:-----------------------|
| companyId    | CHAR(5)       | 회사 ID (PK)           |
| inspectionId | INT(10)     | 점검계획 ID (PK)       |
| scheduleId   | INT(2)       | 예방점검 일정번호 (PK) |
| itemId       | INT(2)       | 예방점검항목번호 (PK)  |
| itemName     | Varchar(100)  | 예방점검항목이름       |
| itemMethod   | Varchar(100)  | 예방점검항목방법       |
| itemUnit     | Char(10)      | 단위                   |
| itemLower    | Decimal(15,2) | 하한값                 |
| itemUpper    | Decimal(15,2) | 상한값                 |
| itemStandard | Decimal(15,2) | 표준값                 |
| itemResult   | Decimal(15,2) | 결과값                 |
| notes        | Text          | 비고                   |

### 작업오더 관리 : 향후 status 필드 추가하여 결재 등 상태관리 추가
## workOrder

| 필드명       | 데이터 타입   | 설명                 |
|:-------------|:--------------|:---------------------|
| companyId    | Char(5)       | 회사코드 (PK)        |
| orderId      | INT(10)     | 작업오더 ID (PK)     |
| orderName    | Varchar(100)  | 작업오더 이름        |
| plantId      | INT(10)     | 설비 마스터 ID       |
| memoId       | INT(10)     | 메모 ID 레퍼런스             |
| jobType      | Char(5)       | 작업유형             |
| performDept  | Char(5)       | 수행부서             |
| scheduleDate | Datetime      | 계획일               |
| scheduleMM   | Decimal(15,2) | 예상 공수(Man Month) |
| scheduleCost | Decimal(15,2) | 예상 비용            |
| scheduleHSE  | Varchar(100)  | 안전환경계획         |
| executeDate  | Datetime      | 실적일               |
| executeMM    | Decimal(15,2) | 실적 공수(Man Month) |
| executeCost  | Decimal(15,2) | 실적 비용            |
| executeHSE   | Varchar(100)  | 안전환경실적         |
| notes        | Text          | 비고                 |
| siteId     | CHAR(5)       | 사이트 ID          |
| fileGroupId  | CHAR(10)      | 첨부파일 그룹        |
| createBy     | CHAR(5)       | 생성자               |
| createDate   | DATETIME      | 생성일               |
| updateBy     | CHAR(5)       | 수정자               |
| updateDate   | DATETIME      | 수정일               |

## workOrderItem

| 필드명     | 데이터 타입   | 설명             |
|:-----------|:--------------|:-----------------|
| companyId  | Char(5)       | 회사코드 (PK)    |
| orderId    | INT(10)     | 작업오더 ID (PK) |
| itemId     | INT(2)      | 아이템 ID (PK)   |
| itemName   | Varchar(100)  | 이름             |
| itemMethod | Varchar(100)  | 방법             |
| itemResult | Varchar(100)  | 결과값           |
| notes      | Text          | 비고             |

### 메모 관리 : 작업 현황 메모 리스트에 등록하여 공유 목적 
## memo

| 필드명      | 데이터 타입   | 설명                     |
|:------------|:--------------|:-------------------------|
| companyId   | CHAR(5)       | 회사 ID (PK)             |
| memoId      | INT(10)      | 메모 ID (PK)             |
| memoName    | VARCHAR(100)  | 제목                     |
| notes       | VARCHAR(200)  | 내용                     |
| isPinned    | CHAR(1)       | 상단 고정 여부 (‘Y’/’N’) |
| viewCount   | INT           | 조회수                   |
| siteId    | CHAR(5)       | 사이트 ID              |
| fileGroupId | CHAR(10)      | 첨부파일 그룹            |
| createBy    | CHAR(5)       | 생성자                   |
| createDate  | DATETIME      | 생성일                   |
| updateBy    | CHAR(5)       | 수정자                   |
| updateDate  | DATETIME      | 수정일                   |

## memoComment

| 필드명    | 데이터 타입   | 설명                                     |
|:----------|:--------------|:-----------------------------------------|
| companyId | CHAR(5)       | 회사 ID (PK)                             |
| memoId    | INT(10)           | 메모 ID (PK)                             |
| commentId | INT(2)           | 댓글 ID (PK)                             |
| notes     | VARCHAR(200)  | 댓글 내용                                |
| parentId  | INT           | FK. 부모 댓글 ID (null이면 최상위 댓글)  |
| depth     | INT           | 계층 수준 (0: 루트, 1: 대댓글 등)        |
| sortOrder | INT           | 정렬 순서 (UI에서 표시 순서를 위한 필드) |


### 공통코드 관리 : AssetType(플랜트,재고마스터터), JobType(작업유형), FuncType(기능분류)-현재 없음 
## commonCode

| 필드명      | 데이터 타입   | 설명                                           |
|:------------|:--------------|:-----------------------------------------------|
| companyId   | Char(5)       | 회사 ID (PK)                                   |
| codeId      | Char(5)       | 코드 ID (PK)                                   |
| codeType    | Char(5)       | 코드 유형 / 자산유형, 작업유형, 분류유형(자산) |
| codeName    | Varchar(100)  | 코드 이름                                      |

## commonCodeItem

| 필드명       | 데이터 타입   | 설명                |
|:-------------|:--------------|:--------------------|
| companyId    | Char(5)       | 회사 ID (PK)        |
| codeId       | Char(5)       | 코드 ID (PK)        |
| codeItemId   | Char(5)       | 코드 아이템 ID (PK) |
| codeItemName | Varchar(100)  | 코드 아이템 번호    |

