package com.meta.config.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.meta.advice.exception.CUserNotExistException;
import com.meta.config.principal.PrincipalDetails;
import com.meta.entity.UserEntity;
import com.meta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private final UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		JwtProvider jwtProvider = new JwtProvider();
		
		// ������� ���۵� ��ū
		String bearerToken = request.getHeader(JwtProperties.TOKEN_HEADER);		
		
		log.info("bearerToken ############################## {}", bearerToken);
		
		if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		// �����ū���� Bearer�� ���ܵ� ��ū
		String token = bearerToken.replace(JwtProperties.TOKEN_PREFIX, "");
		
		// �������� ��ū���� Ȯ��
		boolean isValid = jwtProvider.isValid(token);
		
		// ��ū�� �������̸� ����� ������ ��ȸ�ؼ� ��ť��Ƽ ���ǿ� �־��ش�
		if (isValid) {
			String username = jwtProvider.getUsername(token);			
			
			UserEntity userEntity = userRepository.findByUsername(username);	
			
			if (userEntity != null) {								
				PrincipalDetails principalDetails = new PrincipalDetails(userEntity);								
				Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				chain.doFilter(request, response);
			} else {
				throw new CUserNotExistException("��ū�� ����� ������ �������� �ʽ��ϴ�.");
			}
			
		}		
	}
}
