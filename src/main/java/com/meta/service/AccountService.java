package com.meta.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.advice.exception.CUserNotExistException;
import com.meta.config.jwt.JwtProvider;
import com.meta.config.principal.PrincipalDetails;
import com.meta.dto.user.req.UserLoginReqDto;
import com.meta.dto.user.res.UserResDto;
import com.meta.dto.user.req.UserJoinReqDto;
import com.meta.entity.UserEntity;
import com.meta.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
	
	private final AuthenticationManager authenticationManager;		
	private final PasswordEncoder passwordEncoder;	
	private final JwtProvider jwtProvider;	
	private final UserRepository userRepository;
	
	// 회원가입
	public void saveJoin(UserJoinReqDto reqData) {
		
		Boolean agree = "true".equals(reqData.getAgree()) ? true : false;	
		String encPassword = passwordEncoder.encode(reqData.getPassword());
		
		UserEntity userEntity = UserEntity.builder()
				.username(reqData.getUsername())
				.password(encPassword)
				.role(reqData.getRole())
				.email(reqData.getEmail())
				.addr(reqData.getAddr())
				.agree(agree)
				.build();
		
		userRepository.save(userEntity);
	}
	
	// 로그인
	public String getLogin(UserLoginReqDto reqData) {
		
		String token = "";
		UserEntity user = null;
		
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqData.getUsername(), reqData.getPassword()));			
			SecurityContextHolder.getContext().setAuthentication(authentication);					
					
			PrincipalDetails PrincipalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = PrincipalDetails.getUserEntity();			
		} catch (NullPointerException e) {				
			throw new CUserNotExistException();			
		}

		token = jwtProvider.makeAccessToken(user);
		
		log.info("############################## token : {}", token);
		
		return token;
		
	}
	
	// 로그인 프로필 조회
	public UserResDto getProfile(PrincipalDetails principalDetails) {
		
		long userId = principalDetails.getUserEntity().getId();
		
		UserEntity userEntity = userRepository.findById(userId).orElseThrow();
		
		UserResDto userResDto = UserResDto.builder()
				.id(userEntity.getId())
				.username(userEntity.getUsername())
				.role(userEntity.getRole())
				.email(userEntity.getEmail())
				.addr(userEntity.getAddr())
				.regDate(userEntity.getRegDate())
				.build();
		
		return userResDto;
		
	}
	
	// jwt Token 갱신
	public String getRefreshToken(PrincipalDetails principalDetails) {			
		return jwtProvider.makeAccessToken(principalDetails.getUserEntity());			
	}
	
}
