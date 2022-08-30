package com.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByUsername(String username);
	
}
