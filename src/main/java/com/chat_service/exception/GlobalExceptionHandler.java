package com.chat_service.exception;

import com.chat_service.exception.custom.BusinessException;
import com.chat_service.exception.errorResponse.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
/*RestController에서 발생하는 예외들을 처리*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우 검증을 통과하지 못하면 bindException 예외
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.of(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.of(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormat(InvalidFormatException e) {
        ErrorResponse errorResponse = ErrorResponse.of(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), "잘못된 요청 값입니다");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(),
                "잘못된 요청 값입니다");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 필수 파라미터 누락
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException e) {
        String message = String.format("필수 파라미터 '%s'가 누락되었습니다.", e.getParameterName());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        ErrorResponse errorResponse = ErrorResponse.of(
                String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()), "method 확인 바랍니다");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();

        // Enum 변환 실패
        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            String message = String.format("잘못된 값입니다: '%s'. 가능한 값: %s", ife.getValue(),
                    Arrays.toString(ife.getTargetType().getEnumConstants()));
            ErrorResponse errorResponse = ErrorResponse.of(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // 그 외 JSON 파싱 오류
        ErrorResponse errorResponse = ErrorResponse.of(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), "JSON 파싱 오류");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(BusinessException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getError(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("오류  {}", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "서버 확인 바랍니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
