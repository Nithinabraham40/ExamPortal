package com.tutorial.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.exam.Dao.QuestionDao;
import com.tutorial.exam.Dao.QuestionOutput;
import com.tutorial.exam.Dao.QuizDao;
import com.tutorial.exam.Dao.TodaysAnalysis;
import com.tutorial.exam.Dao.UserAnswer;
import com.tutorial.exam.Dao.UserInput;
import com.tutorial.exam.model.ExamCategory;
import com.tutorial.exam.model.Quiz;
import com.tutorial.exam.model.User;


import com.tutorial.exam.service.UserServiceImpl;

@RestController 
@RequestMapping("/user")

public class UserController {
	
	
	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
		
		
		String roleName="USER";
		
		return userService.createUser(user, roleName);
		
	}
	
	
	@PostMapping("/admin")
	public User createAdmin(@RequestBody User user) throws Exception{
		
	String roleName="ADMIN";
		
		return userService.createUser(user, roleName);
		
		
	}
	
	
	@GetMapping("/signIn")
	public ResponseEntity<String> getUser(@RequestBody UserInput input ) throws Exception{
		
		return userService.signIn(input);
		
	}
	
	
	
	@PostMapping("/admin/{token}/add/category")
	
	public ResponseEntity<String>addExamCategory(@RequestBody ExamCategory examCategory,@PathVariable String token){
		
		
		return userService.addCategory(examCategory,token);
		
		
	}
	
	
	
	@GetMapping("get/category/{token}")
	
	public ResponseEntity<List<ExamCategory>>getAllExamCategory(@PathVariable("token") String token)throws Exception{
		
		
		
		return userService.getAllExamCategory(token);
	}
	
	
	
	@PostMapping("add/quiz/{token}")
	public ResponseEntity<String>addQuiz(@RequestBody QuizDao quiz,@PathVariable("token")String token){
		
		return userService.addQuiz(quiz,token);
		
		
	}
	
	
	
	@PostMapping("add/question/{token}")
	
	public ResponseEntity<String>addQuestion(@RequestBody QuestionDao question,@PathVariable("token")String token){
		
		
		return userService.addQuestion(question,token);
	}
	
	
	
	@GetMapping("get/quiz/categoryname/{name}/{token}")
	
	public ResponseEntity<List<Quiz>>getQuizByCatgeoryName(@PathVariable("name")String categoryName,@PathVariable("token")String token)throws Exception{
		
		
		return userService.getQuizByCatgeoryName(categoryName,token);
	}
	
	
	
	
	@PostMapping("register/quiz/{quizId}/{token}")
	
	public ResponseEntity<String>registerQuiz(@PathVariable("quizId")Long quizId,@PathVariable("token")String token){
		
		
		return userService.registerQuiz(quizId,token);
		
	}
	
	
	
	@GetMapping("start/exam/{registrationtoken}/{token}")
	
	public ResponseEntity<List<QuestionOutput>>startExam(@PathVariable("registrationtoken")String regToken,@PathVariable("token")String token) throws Exception{
		
		return userService.startExam(regToken,token);
	}
	
	
	
	@PostMapping("submit/{registrationtoken}/{token}")
	
	public ResponseEntity<String>submit(@PathVariable("registrationtoken")String regToken,@PathVariable("token")String token,@RequestBody UserAnswer userAnser)throws Exception{
		return userService.submit(regToken,token,userAnser);
	}
	
	
	
	@GetMapping("get/allexam/done/today/{token}")
	
	public ResponseEntity<List<TodaysAnalysis>>todaysResults(@PathVariable("token") String token)throws Exception{
		
		
		return userService.todaysResults(token);
	}
	
	
	
	@DeleteMapping("signout/{token}")
	
	public ResponseEntity<String>signOut(@PathVariable("token")String token){
		
		
		return userService.signOut(token);
	}

}
