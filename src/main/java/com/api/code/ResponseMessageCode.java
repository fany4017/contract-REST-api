package com.api.code;

public class ResponseMessageCode {
    public static final String SUCCESS_ADD_NEW_CONTRACT = "계약이 정상적으로 생성되었습니다.";
    public static final String NOT_MATCHED_PRODUCT_MAIN_GUARANTEE = "상품 코드 또는 주담보 코드 값이 표준과 일치하지 않습니다.(sol : P로 시작 & 6자리)";
    public static final String NOT_MATCHED_TYPE_CONTRACT_TERM = "계약기간의 자료형이 표준과 일치하지 않습니다.(sol : 0 초과 정수만 가능)";
    public static final String NOT_MATCHED_DATE_TYPE = "계약시작일과 종료일이 표준과 일치하지 않습니다.";
    public static final String NOT_MATCHED_CONTRACT_ID_WITH_PARAMETER = "계약 ID가 보유한 주 담보코드가 아닙니다.(sol : 주 담보 코드 확인)";
    public static final String NOT_INCLUDED_SUB_GUARUANTEE = "세부 담보 코드가 담보 관리 테이블에 존재하지 않습니다.(sol : 세부 담보 코드 확인)";
    public static final String NOT_INCLUDED_PRODUCT_CD = "상품코드가 상품 관리 테이블에 존재하지 않습니다.(sol : 상품 코드 확인)";
    public static final String NOT_INCLUDED_MAIN_GUARANTEE_CD = "주담보코드가 요청한 상품코드에 속해있지 않습니다.(sol : 주 담보 코드 확인)";
    public static final String READ_CONTRACT_INFO = "계약 정보 조회 성공";
    public static final String NOT_FOUND_CONTRACT_INFO = "계약 정보를 찾을 수 없습니다.";
    public static final String NOT_INCLUDED_CONTRACT_STATE = "계약 상태가 올바르지 않습니다.(sol : \"정상계약\",\"청약철회\",\"기간만료\" 중 택1)";
    public static final String SUCCESS_UPDATE_CONTRACT_INFO = "계약 정보 수정 성공";
    public static final String READ_TOTAL_PREMIUM = "보험료 조회 성공";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String READ_PRODUCT_CD = "이미 등록된 상품코드 입니다.";
    public static final String SUCCESS_ADD_NEW_PRODUCT = "상품이 정상적으로 생성되었습니다.";
    public static final String ETC_MESSAGGE_01 = "기간만료건은 계약상태가 변경되지 않습니다.";
    public static final String RuntimeException = "서버 런타임 오류";
    public static final String UnexpectedTypeException = "전송 파라미터 타입 오류";
}

