package com.meta.response;

import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    // enum���� api ��û ����� ���� code, message�� �����մϴ�.
    public enum CommonResponse {
        SUCCESS("success", "�����߽��ϴ�."),
    	FAIL("fail", "�����߽��ϴ�.");

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
  
    // ���� ����� ó���ϴ� �޼ҵ�
    public ResponseDto getSuccessResult() {
    	ResponseDto result = new ResponseDto();
        setSuccessResult(result);
        return result;
    }
    
    // ���� ����� ó���ϴ� �޼ҵ�
    public ResponseDto getFailResult(String code, String message) {
    	ResponseDto result = new ResponseDto();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    // ��� �𵨿� api ��û ���� �����͸� �������ִ� �޼ҵ�
    private void setSuccessResult(ResponseDto result) {    
    	result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }
    
}
