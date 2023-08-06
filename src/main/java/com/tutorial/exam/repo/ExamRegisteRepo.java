package com.tutorial.exam.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.exam.model.ExamRegister;


import jakarta.transaction.Transactional;

public interface ExamRegisteRepo extends JpaRepository<ExamRegister, Long>{

	ExamRegister findByToken(String regToken);

	
	
	@Query(value = "select * from examregister where fk_quiz_id=:quizId And fk_user_id=:userId",nativeQuery = true)
	ExamRegister findByUserAndQuiz(Long userId, Long quizId);


	@Modifying
	@Transactional
	@Query(
			value="delete from examregister where fk_quiz_id=:quizId and fk_user_id=:userId",
			nativeQuery = true
			
			)
	
	void deleteByQuizIdAndUserId(Long quizId, Long userId);


	
	
}
