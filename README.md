
# contract-REST-api
보험 계약관리 시스템 REST API

## 1.목적
보험사의 보험계약관리 API 시스템을 신규 구축

## 2.개발요건(필수)
- 계약 신규 생성 API
- 계약 정보 조회 API
- 계약 정보 수정 API
- 예상 총 보험료 계산 API

## 3.개발요건(선택)
- 상품/담보 생성 API
- 만기 도래 계약 안내장 발송 기능

## 4.개발 환경
- IntelliJ IDEA 2021.2.3
- OS : Windows 10

## 5.개발 프레임워크 구성

- 개발 프레임워크 구성은 아래와 같으며, maven을 사용

### 5.1.SpringBoot
- spring-boot-starter-parent : 2.1.4.RELEASE

### 5.2.Json
- jackson-core : 2.12.4
- jackson-databind : 2.12.4
- jackson-annotations : 2.12.4

### 5.3.Database
- h2database 
- 현재 H2를 사용한 메모리 DB로 설정한 상태입니다.
- 재기동을 하면 기존에 저장된 정보는 모두 사라집니다.

### 5.4.Database
- querydsl-jpa 4.1.3

## 6.테이블 구성
![테이블스키마](https://user-images.githubusercontent.com/9305295/140636709-631d9ba4-ff8c-4b5b-bd58-5785754686e2.JPG)

## 7.프로그램 실행 방법
### 메인 컨트롤러 : contract-api/src/main/java/com/api/controller/MainController.java
### ContractApiApplication.java 마우스 우클릭 후 실행
- 프로그램이 실행되면 schema.sql과 data.sql에 설정해 놓은 DML / DDL 이 호출되어 기본 데이터가 생성됩니다.



- 기본 데이터는 http://localhost:8080/h2-console/ 에 로그인하여 확인 가능합니다.
![image](https://user-images.githubusercontent.com/9305295/140636999-81d4b5c9-3daa-460b-96c6-ee6511cffe03.png)

- select * from contract_info;
- select * from product_info;
- select * from guarantee_info;
- select * from contract_guarantee_relationship;
- select a.contract_id, d.product_name, c.sub_guarantee_name <br/>
from contract_info a, contract_guarantee_relationship b, guarantee_info c, product_info d <br/>
where a.contract_id = b.contract_id <br/>
and a.product_cd = d.product_cd <br/>
and b.sub_guarantee_cd = c.sub_guarantee_cd <br/>
and a.contract_id = '1'; <br/>
![image](https://user-images.githubusercontent.com/9305295/140637051-9e1a12fc-bca5-47fa-a9c7-4bd51c241b80.png)


## 8.테스트 프로그램 실행 방법
### 테스트 프로그램 소스 : kakao-api/src/test/java/com/api/controller/ApiControllerTest.java
### ApiControllerTest.java 마우스 우클릭 후 실행
- 계약정보_조회테스트
- 계약정보_생성테스트
- 예상보험료_조회테스트
- 계약정보_수정테스트
- 계약정보_담보_조회테스트
- 계약정보_생성테스트_상품코드값_P로미시작
- 계약정보_생성테스트_상품코드값_6자리가아닐때
- 계약정보_생성테스트_계약기간_자료형불일치
- 계약정보_생성테스트_계약시작일_종료일_표준불일치
- 계약정보_생성테스트_주담보코드값_상품코드_불일치
- 계약정보_생성테스트_세부담보코드값_주담보에불일치
![image](https://user-images.githubusercontent.com/9305295/140637125-e564c16a-6b79-40d1-ac4a-8641f3ae0e55.png)


## 제공 API

### 1. 신규 계약 등록 API

**파라미터 전송 규칙 
- 1 : 상품코드는 시작이 대문자 P로 시작해야함
- 2 : 상품코드는 총 6자리여야함
- 3 : 상품코드는 상품 기본 테이블에 존재해야함
- 4 : 주담보코드는 주담보 기본 테이블에 존재해야함
- 5 : 세부담보코드는 세부담보 기본 테이블에 존재해야함
- 6 : 계약시작일은 종료일보다 작아야하며, 둘다 음수일수 없음 <br/>
**URL** : /api/new <br/>
**방식** : POST <br/>
**입력 양식>** <br/>

```json
{
 "product_cd":"상품코드",
 "main_guarantee_cd":"주담보코드",
 "sub_guarantee_cd":["세부담보코드1","세부담보코드2"],
 "contract_term" : "계약기간",
 "contract_start" : "계약시작일",
 "contract_end" : "계약종료일"
}
```

```json
{
 "product_cd":"P00002",
 "main_guarantee_cd":"cellphone",
 "sub_guarantee_cd":["damaged_part","damaged_all"],
 "contract_term" : "10",
 "contract_start" : "20210101",
 "contract_end" : "20211231"
}
```

**출력 양식>**
```json
{
    "statusCode": "상태코드",
    "responseMessage": "상태명",
    "data": {
        "product_cd": "상품코드",
        "main_guarantee_cd": "주담보코드",
        "contract_term": "계약기간",
        "contract_start": "계약시작일",
        "contract_end": "계약종료일",
        "sub_guarantee_list": [
            "세부담보명1",
            "세부담보명2"
        ],
        "total_premium": "총보험료",
        "contract_id": "계약번호"
    }
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "계약이 정상적으로 생성되었습니다.",
    "data": {
        "product_cd": "P00002",
        "main_guarantee_cd": "cellphone",
        "contract_term": "10",
        "contract_start": "20210101",
        "contract_end": "20211231",
        "sub_guarantee_list": [
            "damaged_part",
            "damaged_all"
        ],
        "total_premium": "589868.42",
        "contract_id": "2"
    }
}
```
### 2. 보험료 계산 API

**파라미터 전송 규칙 
- 1 : 상품코드는 상품 기본 테이블에 존재해야함
- 2 : 주담보코드는 주담보 기본 테이블에 존재해야함
- 3 : 세부담보코드는 세부담보 기본 테이블에 존재해야함
- 4 : 세부담보코드는 주담보 하위에 존재해야함<br/>
**URL** : /api/get/calculation <br/>
**방식** : GET <br/>
**입력 양식>** <br/>

```json
{
 "product_cd":"상품코드",
 "main_guarantee_cd":"주담보코드",
 "sub_guarantee_cd":["세부담보코드1","세부담보코드2"],
 "contract_term" : "계약기간"
}
```

```json
{
 "product_cd":"P00001",
 "main_guarantee_cd":"travel",
 "sub_guarantee_cd":["airplane_delay","traveler_injured"],
 "contract_term" : "10"
}
```

**출력 양식>**
```json
{
    "statusCode": "상태코드",
    "responseMessage": "상태명",
    "data": {
        "product_cd": "상품코드",
        "main_guarantee_cd": "주담보코드",
        "sub_guarantee_cd": [
            "세부담보코드1",
            "세부담보코드2"
        ],
        "contract_term": "계약기간",
        "totalFee": "총보험료"
    }
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "보험료 조회 성공",
    "data": {
        "product_cd": "P00001",
        "main_guarantee_cd": "travel",
        "sub_guarantee_cd": [
            "airplane_delay",
            "traveler_injured"
        ],
        "contract_term": "10",
        "totalFee": "150000.00"
    }
}
```
### 3. 계약정보조회 API

**파라미터 전송 규칙 
- 1 : 계약코드는 계약 기본 테이블에 존재해야함<br/>

**URL** : /api/get/{계약ID} <br/>
**방식** : GET <br/>

**출력 양식>**
```json
{
    "statusCode": "상태코드",
    "responseMessage": "상태명",
    "data": {
        "contract_id": "계약ID",
        "product_cd": "상품코드",
        "main_guarantee_cd": "주담보코드",
        "contract_term": "계약기간",
        "contract_start": "계약시작일",
        "contract_end": "계약종료일",
        "total_premium": "총보험료",
        "contract_state": "계약상태"
    }
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "계약 정보 조회 성공",
    "data": {
        "contract_id": 1,
        "product_cd": "P00001",
        "main_guarantee_cd": "travel",
        "contract_term": "10",
        "contract_start": "20210101",
        "contract_end": "20211231",
        "total_premium": "150000.00",
        "contract_state": "정상계약"
    }
}
```

### 4. 계약 정보 수정 API

**파라미터 전송 규칙 
- 1 : 담보 추가/삭제 가능
- 2 : 계약기간만 변경(시작일은 변경 불가, 기간만 변경 가능)<br/>
- 3 : 계약상태 변경(단, 기간만료 상태인 경우 상태는 변경되지 않음)<br/>

**URL** : /api/change/{계약ID} <br/>
**방식** : PUT <br/>
**입력 양식>** <br/>

```json
{
 "main_guarantee_cd":"주담보코드",
 "sub_guarantee_cd":["세부담보코드1","세두담보코드2"],
 "contract_term" : "계약기간",
 "contract_state" : "계약상태명"
}
```

```json
{
 "main_guarantee_cd":"travel",
 "sub_guarantee_cd":["airplane_delay","traveler_injured"],
 "contract_term" : "5",
 "contract_state" : "기간만료"
}
```


**출력 양식>**
```json
{
    "statusCode": "상태코드",
    "responseMessage": "상태명",
    "data": {
        "main_guarantee_cd": "주담보코드",
        "contract_term": "계약기간",
        "contract_state": "계약상태",
        "contract_id": "계약ID",
        "sub_guarantee_list": [
            "세부담보코드1",
            "세부담보코드2"
        ],
        "total_premium": "75000.00"
    }
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "계약 정보 수정 성공",
    "data": {
        "main_guarantee_cd": "travel",
        "contract_term": "5",
        "contract_state": "기간만료",
        "contract_id": "1",
        "sub_guarantee_list": [
            "airplane_delay",
            "traveler_injured"
        ],
        "total_premium": "75000.00"
    }
}
```

### 5. 계약ID 별 보유 담보 조회 API

**URL** : /api/change/{계약ID} <br/>
**방식** : PUT <br/>

**출력 양식>**
```json
{
    "statusCode": "상태코드",
    "responseMessage": "상태명",
    "data": [
        {
            "SUB_GUARANTEE_NAME": "세부 담보명 1",
            "CONTRACT_ID": "계약ID",
            "PRODUCT_NAME": "상품명"
        },
        {
            "SUB_GUARANTEE_NAME": "세부 담보명 1",
            "CONTRACT_ID": "계약ID",
            "PRODUCT_NAME": "상품명"
        }
    ]
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "계약 정보 조회 성공",
    "data": [
        {
            "SUB_GUARANTEE_NAME": "항공기 지연도착시 보상금",
            "CONTRACT_ID": 1,
            "PRODUCT_NAME": "여행자보험"
        },
        {
            "SUB_GUARANTEE_NAME": "상해치료비",
            "CONTRACT_ID": 1,
            "PRODUCT_NAME": "여행자보험"
        }
    ]
}
```
## 추가 기능 구현 
### 1.만기 도래 계약 안내장 발송 기능
- 스프링 @Scheduled 적용, 로그로 대체
![image](https://user-images.githubusercontent.com/9305295/140638699-fe5e823e-1e4c-47ee-8b3b-36cfc642f9af.png)

### 고민 및 해결 방식
- 3번 과제를 선택한 이유는 보험 업무를 하면서 알게 된 개념들을 사용해서 구성하는 소재였기 때문입니다.
- 처음에는 계약 기본 테이블만을 만들어서 진행하려했으나 계약에 연결된 담보들이 여러 개 존재할 수 있음에 따라 데이터 구조를 고민했습니다.
- 이를 해결하기 위해 현 보험 업무에서 사용하는 주담보 코드라는 개념을 가지고 와서 세부 담보 코드와 연결했습니다.
# 20211102-hkj
