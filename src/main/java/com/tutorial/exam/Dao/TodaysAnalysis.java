package com.tutorial.exam.Dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodaysAnalysis {

	
	private String userName;
	private String email;
	private String quizName;
	private String result;
	
}
