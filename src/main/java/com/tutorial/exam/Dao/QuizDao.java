package com.tutorial.exam.Dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDao {

	
	
    private String quizName;
	
	private String description;
	
	
	private int numberOfQuestions;
	
    private  long quizdurationInMillSec;
    
    private Long catgetoryId;
    
}
