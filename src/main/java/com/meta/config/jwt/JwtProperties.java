package com.meta.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;

public interface JwtProperties {
	
	String SECRET = "secret-#xay-@39y-$a97#a";
	String ISSUER = "auth0";
	
	Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);
	
	String TOKEN_HEADER = "Authorization";
	String TOKEN_PREFIX = "Bearer ";
	
	String RESPONSE_TOKEN_HEADER = "token";
	
	int EXPIRATION_TIME_USER_AUTH = 1000*60*60; // ����� ���� ��ū ���� �ð� (60 ��)
	String SUBJECT_USER_AUTH = "AccessToken";
			
}


/*
 * ��ū ����ð�
864000000; // 10��
86400000; // 1�� 
3600000; // 1 �ð�
60000; // 1 ��
1000; //1 ��
*/

