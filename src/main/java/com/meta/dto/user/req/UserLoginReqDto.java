package com.meta.dto.user.req;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginReqDto {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}
