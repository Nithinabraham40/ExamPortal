package com.tutorial.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.exam.model.Question;
import com.tutorial.exam.model.Quiz;

public interface QuestionsRepo extends JpaRepository<Question, Long> {

	List<Question> findByQuiz(Quiz quiz);

	
	@Query(value = "select * from question where fk_quiz_id=:id",nativeQuery = true)
	List<Question> findByQuizID(Long id);

	
	@Query(value = "select answer from question where fk_quiz_id=:quizId order by question_id",nativeQuery = true)

	List<String> getAllAnswersById(Long quizId);

	
}
