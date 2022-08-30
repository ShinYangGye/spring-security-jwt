package com.meta.response;

import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.
    public enum CommonResponse {
        SUCCESS("success", "성공했습니다."),
    	FAIL("fail", "실패했습니다.");

        String code;
        String message;

        CommonResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
  
    // 성공 결과만 처리하는 메소드
    public ResponseDto getSuccessResult() {
    	ResponseDto result = new ResponseDto();
        setSuccessResult(result);
        return result;
    }
    
    // 실패 결과만 처리하는 메소드
    public ResponseDto getFailResult(String code, String message) {
    	ResponseDto result = new ResponseDto();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(ResponseDto result) {    
    	result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }
    
}
