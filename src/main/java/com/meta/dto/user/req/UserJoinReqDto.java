package com.meta.dto.user.req;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class UserJoinReqDto {
	
	@NotBlank	
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String role;
	
	@Email
	private String email;
			
	private String addr;
	
	@NotBlank
	private String agree;
	
	
}
