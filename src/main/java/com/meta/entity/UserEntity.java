package com.meta.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.annotation.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 50, unique = true, nullable = false)
	private String username;
	
	@Column(length = 150, nullable = false)
	private String password;
	
	@Column(length = 20, nullable = false)
	private String role;
	
	@Column(length = 150, nullable = false)
	private String email;
	
	
	@Column(length = 150, nullable = true)
	private String addr;
		
	@CreationTimestamp // 시간이 자동으로 입력됨
	private Timestamp regDate;
	
	private Boolean agree;
}
