package com.meta.advice;

import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.meta.response.ResponseService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExceptionMessage {
	
	private final MessageSource messageSource;

    // code������ �ش��ϴ� �޽����� ��ȸ�մϴ�.
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    // code����, �߰� argument�� ���� locale�� �´� �޽����� ��ȸ�մϴ�.
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
	
}

@Data
@AllArgsConstructor
class InvalidErrors {
	private String code;
	private String message;
	private Map<String, String> errors;
}