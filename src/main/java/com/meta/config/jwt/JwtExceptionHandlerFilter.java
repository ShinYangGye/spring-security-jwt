package com.meta.config.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

	private final MessageSource messageSource;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {	
    	
		try{
			filterChain.doFilter(request,response);		
		} catch (Exception ex){
			setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response,ex);
		}

    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
    	
    	log.error("############################## {}, {}", ex.getClass().getSimpleName(), ex.getMessage());
    	
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); 
        
        ResponseDto errorResponse = new ResponseDto();
        
        if ("CTokenExpiredException".equals(ex.getClass().getSimpleName())) {
        	
        	errorResponse.setCode(getMessage("TokenExpiredException.code"));
            errorResponse.setMessage(getMessage("TokenExpiredException.msg"));
            
        } else if ("CTokenInvalidException".equals(ex.getClass().getSimpleName())) {
        	
        	errorResponse.setCode(getMessage("TokenInvalidException.code"));
            errorResponse.setMessage(getMessage("TokenInvalidException.msg"));
            
        } else {
        	errorResponse.setCode(getMessage("TokenUnknownException.code"));
            errorResponse.setMessage(getMessage("TokenUnknownException.msg"));
        }
                     
        try{
            response.getWriter().write(convertToJson(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
    
	public String convertToJson(ResponseDto errorResponse) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(errorResponse);
	}
}



