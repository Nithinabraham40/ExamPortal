package com.tutorial.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.exam.model.ExamCategory;
import com.tutorial.exam.model.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

	List<Quiz> findByExamCategory(ExamCategory category);

	
	
}
