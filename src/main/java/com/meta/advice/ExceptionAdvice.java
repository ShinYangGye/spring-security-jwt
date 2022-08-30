package com.meta.advice;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.meta.advice.exception.CAuthenticationEntryPointException;
import com.meta.advice.exception.CPartCodeNotExistException;
import com.meta.advice.exception.CTokenCreationException;
import com.meta.advice.exception.CTokenExpiredException;
import com.meta.advice.exception.CTokenInvalidException;
import com.meta.advice.exception.CTokenUnknownException;
import com.meta.advice.exception.CUserNotExistException;
import com.meta.response.ResponseDto;
import com.meta.response.ResponseService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    
    private final ExceptionMessage exceptionMessage;
   
    // 공통 Exception ##################################################################################################################################
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error(서버 내부 에러)
    protected ResponseDto unKnownException(HttpServletRequest request, Exception e) {
        // return responseService.getFailResult(getMessage("UnknownException.code"), getMessage("UnknownException.msg") + "(" + e.getMessage() + ")");
    	return responseService.getFailResult(exceptionMessage.getMessage("UnknownException.code"), exceptionMessage.getMessage("UnknownException.msg") + "(" + e.getClass().getSimpleName() + ")");
    }

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Not Found
	protected ResponseDto notFoundException(NoHandlerFoundException e, HttpServletRequest request) {
		return responseService.getFailResult(exceptionMessage.getMessage("NotFoundException.code"), exceptionMessage.getMessage("NotFoundException.msg"));
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Not Found
	protected ResponseDto noSuchElementException(NoSuchElementException e, HttpServletRequest request) {
		return responseService.getFailResult(exceptionMessage.getMessage("NotFoundException.code"), exceptionMessage.getMessage("NotFoundException.msg"));
	}
	
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto dataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("DataIntegrityViolationException.code"), exceptionMessage.getMessage("DataIntegrityViolationException.msg"));
    }	
       
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto duplicateKeyException(HttpServletRequest request, DuplicateKeyException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("DuplicateKeyException.code"), exceptionMessage.getMessage("DuplicateKeyException.msg"));
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto badCredentialsException(HttpServletRequest request, BadCredentialsException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("BadCredentialsException.code"), exceptionMessage.getMessage("BadCredentialsException.msg"));
    }
    
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Unauthorized (인증되지 않았음)
    public ResponseDto authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("EntryPointException.code"), exceptionMessage.getMessage("EntryPointException.msg"));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403 Forbidden(금지되었음)
    public ResponseDto accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("AccessDeniedException.code"), exceptionMessage.getMessage("AccessDeniedException.msg"));
    }
    
    // 입력필드 Validation 오류 - 입력항목별로 오류 메세지 처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
	public InvalidErrors handlerInvalideArgument(MethodArgumentNotValidException ex) {
		
		Map<String, String> errorMap = new LinkedHashMap<>();
				
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			// errorMap.put(error.getField(), error.getDefaultMessage());
			// errorMap.put(error.getField(), exceptionMessage.getMessage(error.getCode())); // 오류코드로 에려 메세지파일이서 가져오기
			// errorMap.put(error.getField(), error.getCodes()[1]); // 코드값 확인			
			// errorMap.put(error.getField(), error.getDefaultMessage());
			
			errorMap.put(error.getField(), exceptionMessage.getMessage(error.getCode())); // 오류코드로 에려 메세지파일이서 가져오기
			// errorMap.put(error.getField(), exceptionMessage.getMessage(error.getCodes()[1]));
			
		});
		
		InvalidErrors invalidErrors = new InvalidErrors(exceptionMessage.getMessage("MethodArgumentNotValidException.code"), exceptionMessage.getMessage("MethodArgumentNotValidException.msg"), errorMap);				
		return invalidErrors;
	}
	// 공통 Exception
	
	// JWT 토큰 Exception ##################################################################################################################################
    @ExceptionHandler(CTokenCreationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error(서버 내부 에러)
    protected ResponseDto tokenCreationException(HttpServletRequest request, CTokenCreationException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("TokenCreationException.code"), exceptionMessage.getMessage("TokenCreationException.msg"));
    }  
    
    @ExceptionHandler(CTokenExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto tokenExpiredException(HttpServletRequest request, CTokenExpiredException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("TokenExpiredException.code"), exceptionMessage.getMessage("TokenExpiredException.msg"));
    }

    @ExceptionHandler(CTokenInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto tokenInvalidException(HttpServletRequest request, CTokenInvalidException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("TokenInvalidException.code"), exceptionMessage.getMessage("TokenInvalidException.msg"));
    }
    
    @ExceptionHandler(CTokenUnknownException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error(서버 내부 에러)
    protected ResponseDto tokenUnknownException(HttpServletRequest request, CTokenUnknownException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("TokenUnknownException.code"), exceptionMessage.getMessage("TokenUnknownException.msg"));
    }	
	// JWT 토큰 Exception
	
	// 사용자 정의 Exception ##################################################################################################################################
    @ExceptionHandler(CPartCodeNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto partCodeNotExist(HttpServletRequest request, CPartCodeNotExistException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("PartCodeNotExistException.code"), exceptionMessage.getMessage("PartCodeNotExistException.msg"));
    }
    
    @ExceptionHandler(CUserNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseDto userNotExist(HttpServletRequest request, CUserNotExistException e) {
        return responseService.getFailResult(exceptionMessage.getMessage("UserNotExistException.code"), exceptionMessage.getMessage("UserNotExistException.msg"));
    }
    // 상요자 정의 Exception

}
