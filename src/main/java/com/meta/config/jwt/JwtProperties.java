package com.meta.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;

public interface JwtProperties {
	
	String SECRET = "secret-#xay-@39y-$a97#a";
	String ISSUER = "auth0";
	
	Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);
	
	String TOKEN_HEADER = "Authorization";
	String TOKEN_PREFIX = "Bearer ";
	
	String RESPONSE_TOKEN_HEADER = "token";
	
	int EXPIRATION_TIME_USER_AUTH = 1000*60*60; // 사용자 인증 토큰 만료 시간 (60 분)
	String SUBJECT_USER_AUTH = "AccessToken";
			
}


/*
 * 토큰 만료시간
864000000; // 10일
86400000; // 1일 
3600000; // 1 시간
60000; // 1 분
1000; //1 초
*/

