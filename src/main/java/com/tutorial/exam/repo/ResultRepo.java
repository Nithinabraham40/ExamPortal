package com.tutorial.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.exam.model.Result;
import com.tutorial.exam.model.StartExam;

public interface ResultRepo extends JpaRepository<Result, Long> {

	Result findBystartExam(StartExam startExam);
	
	

}
