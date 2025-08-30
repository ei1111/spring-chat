package com.chat_service.exception.errorCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
      BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST","필수 필드 누락, 잘못된 데이터 형식" )

    , NOT_FOUND_USER(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않는 사용자 입니다")

    , CONFLICT_EMAIL(HttpStatus.CONFLICT, "CONFLICT", "이미 가입된 이메일입니다")
    , CONFLICT_PHONENUMBER(HttpStatus.CONFLICT, "CONFLICT", "이미 가입된 휴대폰 번호입니다")

    , NOT_FOUND_DOG(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않는 강아지 입니다")
    , NOT_DELETE_USER(HttpStatus.BAD_REQUEST, "NOT_DELETE_USER", "사용자가 등록한 강아지가 있습니다")

    , CONFLICT_DOG(HttpStatus.CONFLICT, "CONFLICT", "이미 관심목록에 있는 강아지 입니다")
    , NOT_FOUND_FAVORITE_USER(HttpStatus.NOT_FOUND, "NOT_FOUND", "관심 강아지를 등록하지 않은 사용자 입니다")
    , NOT_FOUND_FAVORITE_DOG(HttpStatus.NOT_FOUND, "NOT_FOUND", "사용자의 관심 강아지에 등록되지 않은 강아지 입니다")

    //통신애러
    , NOT_SUCESS_API_CONNECTION(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "API 통신에 문제가 있습니다");

    ErrorCode(HttpStatus httpStatus, String error, String message) {
        this.httpStatus = httpStatus;
        this.error = error;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String error;
    private String message;
}