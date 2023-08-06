package com.tutorial.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.exam.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String userName);

	User findByEmail(String userName);

	
	
	
}
