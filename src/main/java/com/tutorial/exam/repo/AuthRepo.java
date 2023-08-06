package com.tutorial.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.exam.model.Authentication;

public interface AuthRepo extends JpaRepository<Authentication, Long> {

	Authentication findByToken(String token);
	
	
	
	

}
