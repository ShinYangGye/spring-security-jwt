package com.meta.dto.user.res;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResDto {
	
	private long id;
	
	private String username;
	
	private String role;
	
	private String email;
			
	private String addr;
	
	private Timestamp regDate;
	
}
