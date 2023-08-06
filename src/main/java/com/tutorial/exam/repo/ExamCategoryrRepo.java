package com.tutorial.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.exam.model.ExamCategory;

public interface ExamCategoryrRepo extends JpaRepository<ExamCategory, Long> {

	ExamCategory findByCategoryName(String categoryName);
	
	

}
