package com.meta.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.config.jwt.JwtProperties;
import com.meta.config.jwt.JwtProvider;
import com.meta.config.principal.PrincipalDetails;
import com.meta.dto.user.req.UserLoginReqDto;
import com.meta.dto.user.res.UserResDto;
import com.meta.dto.user.req.UserJoinReqDto;
import com.meta.response.ResponseDto;
import com.meta.response.ResponseService;
import com.meta.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
	
	private final ResponseService responseService;
	
	private final AccountService accountService;
	
	
	@PostMapping("/join")
	public ResponseDto saveJoin(@Validated @RequestBody UserJoinReqDto reqData) {		
		accountService.saveJoin(reqData);		
		return responseService.getSuccessResult();
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> getLogin(@Validated @RequestBody UserLoginReqDto reqData) {				
		String token = accountService.getLogin(reqData);						
		HttpHeaders headers= new HttpHeaders();
		headers.add(JwtProperties.RESPONSE_TOKEN_HEADER, token);		
		return ResponseEntity.ok().headers(headers).body(responseService.getSuccessResult());
	}
	
	@GetMapping("/profile")
	public ResponseEntity<UserResDto> getProfile(@AuthenticationPrincipal PrincipalDetails principalDetails) {				
		UserResDto resData = accountService.getProfile(principalDetails);		
		HttpHeaders headers= new HttpHeaders();	
		return ResponseEntity.ok().headers(headers).body(resData);
	}
	
	@GetMapping("/refresh-token")
	public ResponseEntity<ResponseDto> getRefreshToken(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		String token = accountService.getRefreshToken(principalDetails);						
		HttpHeaders headers= new HttpHeaders();
		headers.add(JwtProperties.RESPONSE_TOKEN_HEADER, token);		
		return ResponseEntity.ok().headers(headers).body(responseService.getSuccessResult());
		
	}	
	
}
