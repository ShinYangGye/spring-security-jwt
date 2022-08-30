package com.meta.config.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.meta.entity.UserEntity;
import lombok.Data;
import lombok.Getter;

@Getter
public class PrincipalDetails implements UserDetails {
	
	private UserEntity userEntity;
	
	public PrincipalDetails (UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	/*
	public UserEntity getUserEntity() {
		return userEntity;
	}
	*/
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       
		//  다중 권한
		/*
		List<GrantedAuthority> authorities = new ArrayList<>();		  
		for (RolesDto role : user.getRoles()) {		    
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
		*/
		
		// 단건 권한
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				return userEntity.getRole();
			}
			
		});		
		return collect;		
		
    }

	@Override
	public String getPassword() {
		return this.userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
