
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
![프로그램 실행 방법](https://user-images.githubusercontent.com/9305295/141683532-b9390a8f-f202-4f35-a16e-09d44349124f.JPG)

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
### 테스트 프로그램 소스 : contract-api/src/test/java/com/api/repository/ContractApiCallTest.java
### ContractApiCallTest.java 마우스 우클릭 후 실행
![테스트](https://user-images.githubusercontent.com/9305295/141683605-00c5e6aa-d2b1-48c0-9e3c-e5eb773dd786.JPG)



## 제공 API

### 1. 신규 계약 등록 API

**파라미터 전송 규칙 
- 1 : 상품코드는 상품 기본 테이블에 존재해야함
- 2 : 주담보코드는 주담보 기본 테이블에 존재해야함
- 3 : 세부담보코드는 세부담보 기본 테이블에 존재해야함<br/>
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
    "responseMessage": "상태명"
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "계약이 정상적으로 생성되었습니다."
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
 "statusCode": "결과코드",
 "responseMessage": "응답메시지",
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
    "statusCode": "결과코드",
    "responseMessage": "응답메시지",
    "data": "예상보험료"
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "보험료 조회 성공",
    "data": "150000.00"
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
    "statusCode": "결과코드",
    "responseMessage": "응답메시지",
    "data": {
        "id": "계약ID",
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
        "id": 1,
        "productCd": "P00002",
        "mainGuaranteeCd": "cellphone",
        "contractTerm": "10",
        "contractStart": "20210101",
        "contractEnd": "20211231",
        "totalPremium": "589868.42",
        "contractState": "NORMAL"
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
 "main_guarantee_cd":"cellphone",
 "sub_guarantee_cd":["damaged_part","damaged_all"],
 "contract_term" : "8",
 "contract_state" : "EXPIRED"
}
```


**출력 양식>**
```json
{
    "statusCode": "결과코드",
    "responseMessage": "응답메시지"
}
```

```json
{
    "statusCode": 200,
    "responseMessage": "계약 정보 수정 성공"
}
```

### 5. 계약ID 별 보유 담보 조회 API
**URL** : /api/get/{계약ID}/guarantee <br/>
**방식** : GET <br/>

**출력 양식>**
```json
{
    "statusCode": "결과코드",
    "responseMessage": "응답메시지",
    "data": [
        {
            "subGuaranteeCd": "세부담보코드1",
            "subGuaranteeName": "세부담보명1"
        },
        {
            "subGuaranteeCd": "세부담보코드2",
            "subGuaranteeName": "세부담보명2"
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
            "subGuaranteeCd": "damaged_all",
            "subGuaranteeName": "전체손실"
        },
        {
            "subGuaranteeCd": "damaged_part",
            "subGuaranteeName": "부분손실"
        }
    ]
}
```
## 추가 기능 구현 
### 1.만기 도래 계약 안내장 발송 기능
- 스프링 @Scheduled 적용, 로그로 대체
![배치](https://user-images.githubusercontent.com/9305295/141683966-e33fe13f-53d4-4591-ac45-e63f4c0bf2bd.JPG)

