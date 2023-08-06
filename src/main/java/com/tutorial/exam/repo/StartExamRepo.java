package com.tutorial.exam.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.exam.model.StartExam;


public interface StartExamRepo extends JpaRepository<StartExam, Long>{
	
	
	
@Query(value="select * from startexam where date=:date and fk_quiz_id=:quizId and fk_user_id=:userId",nativeQuery = true)

	StartExam findByDateAndUserAndQuiz(LocalDate date, Long quizId, Long userId);

List<StartExam> findByDate(LocalDate todaysDate);
	
	
	

}
