package com.tutorial.exam.Dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDao {
	
    private String question;
	
	private String option1;
	
	private String option2;
	
	private String option3;
	
	private String option4;
	
	private String answer;
	
	private Long quizId;

}
