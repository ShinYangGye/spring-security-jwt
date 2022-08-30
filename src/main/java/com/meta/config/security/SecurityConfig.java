package com.meta.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.meta.config.jwt.JwtAuthorizationFilter;
import com.meta.config.jwt.JwtExceptionHandlerFilter;
import com.meta.config.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	
	private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
				
		return http				
				.httpBasic().disable()
				.formLogin().disable()				
				.csrf().disable() 																			
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)				
				.and().cors()
				
				.and()				
				.authorizeRequests()				
				
				// .antMatchers(HttpMethod.GET,"/api/board/freeboards").permitAll()
				
				.antMatchers("/api/board/freeboards").permitAll()
				
				.antMatchers(	  "/api/account/profile", "/api/account/refresh-token"
								, "/api/board/**")
				.authenticated()
				//.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")				
				
				.antMatchers("/api/manager/**")
				.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				
				.antMatchers("/api/admin/**")
				.access("hasRole('ROLE_ADMIN')")				
				
				.anyRequest().permitAll()
				
				.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
				.and().exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				
				.and()				
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthorizationFilter.class)
				
				.build();
	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }   
    
	// http.cors() 설정 후 모든 origin 의 접근을 허용하는 경우.
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader(JwtProperties.RESPONSE_TOKEN_HEADER);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}    
    
        
	/* http.cors() 설정 후 특정 origin 만 허용할 경우
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
        		"http://localhost:8080", "http://localhost:3000", "http://localhost:80", 
        		"http://127.0.0.1:3000", "http://127.0.0.1:8080"));
        // configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }	       
	*/
    
}
