package com.meta.config.jwt;

import java.sql.Date;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.meta.advice.exception.CTokenCreationException;
import com.meta.advice.exception.CTokenExpiredException;
import com.meta.advice.exception.CTokenInvalidException;
import com.meta.advice.exception.CTokenUnknownException;
import com.meta.advice.exception.CUserNotExistException;
import com.meta.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {
	
	public String makeAccessToken(UserEntity user) {
		
		if (user == null) {
			throw new CUserNotExistException();
		}
		
		String jwtToken = null;
		try {			
			jwtToken = JWT.create()
					.withSubject(JwtProperties.SUBJECT_USER_AUTH)
					.withIssuer(JwtProperties.ISSUER)
					.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME_USER_AUTH))		
					.withClaim("id", user.getId())				
					.withClaim("username", user.getUsername())
					.withClaim("role", user.getRole())
					.sign(JwtProperties.ALGORITHM);			
						
		} catch (JWTCreationException e){
		    throw new CTokenCreationException("������ū ������ ������ �߻��߽��ϴ�.");
		}
		
		return JwtProperties.TOKEN_PREFIX + jwtToken;

	}
	
	public boolean isValid(String token) {
		
		boolean result = false;
		
		try {		    
		    JWTVerifier verifier = JWT.require(JwtProperties.ALGORITHM)		    		
		    		.withIssuer(JwtProperties.ISSUER)
		    		.build();
		    
		    DecodedJWT jwt = verifier.verify(token);
		    
		    result = true;
		    
		} catch (TokenExpiredException e){
			throw new CTokenExpiredException("������ū�� ����ð��� ����߽��ϴ�.");			
		} catch (JWTVerificationException e){
			throw new CTokenInvalidException("������ū�� ���������� �ʽ��ϴ�.");			
		} catch (Exception e) {			
			throw new CTokenUnknownException("������ū ó���� �˼����� ������ �߻��߽��ϴ�.");
		}
		
		return result;
		
	}
	
	public String getUsername(String token) {
		
		String username = null;
		
		try {		    
			username = JWT.require(JwtProperties.ALGORITHM).build().verify(token).getClaim("username").asString();		    
		} catch (TokenExpiredException e){
			throw new CTokenExpiredException("������ū�� ����ð��� ����߽��ϴ�.");			
		} catch (JWTVerificationException e){
			throw new CTokenInvalidException("������ū�� ���������� �ʽ��ϴ�.");			
		} catch (Exception e) {			
			throw new CTokenUnknownException("������ū ó���� �˼����� ������ �߻��߽��ϴ�.");
		}		
		
		return username;
	}
	
}
