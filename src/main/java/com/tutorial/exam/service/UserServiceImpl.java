package com.tutorial.exam.service;



import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tutorial.exam.Dao.QuestionDao;
import com.tutorial.exam.Dao.QuestionOutput;
import com.tutorial.exam.Dao.QuizDao;
import com.tutorial.exam.Dao.TodaysAnalysis;
import com.tutorial.exam.Dao.UserAnswer;
import com.tutorial.exam.Dao.UserInput;
import com.tutorial.exam.model.Authentication;
import com.tutorial.exam.model.ExamCategory;
import com.tutorial.exam.model.ExamRegister;
import com.tutorial.exam.model.Question;
import com.tutorial.exam.model.Quiz;
import com.tutorial.exam.model.Result;
import com.tutorial.exam.model.StartExam;
import com.tutorial.exam.model.User;
import com.tutorial.exam.repo.AuthRepo;
import com.tutorial.exam.repo.ExamCategoryrRepo;
import com.tutorial.exam.repo.ExamRegisteRepo;
import com.tutorial.exam.repo.QuestionsRepo;
import com.tutorial.exam.repo.QuizRepo;
import com.tutorial.exam.repo.ResultRepo;
import com.tutorial.exam.repo.StartExamRepo;
import com.tutorial.exam.repo.UserRepository;


@Service
public class UserServiceImpl  {
	
	
	@Autowired
	private UserRepository userRepo;
	
	
	
	
	@Autowired
	
	private AuthRepo authRepo;
	
	@Autowired
	private ExamCategoryrRepo examCategoryRepo;
	
	@Autowired
	private QuizRepo quizRepo;
	
	@Autowired
	private QuestionsRepo questionRepo;
	
	@Autowired
	private ExamRegisteRepo examRegisterRepo;
	
	@Autowired
	private StartExamRepo startExamRepo;
	
	@Autowired
	private ResultRepo resultRepo;
	
	

	
	public User createUser(User user, String role) throws Exception {
		
	User local=	userRepo.findByEmail(user.getEmail());
		
	
	  if(local!=null) {
		  
		  
		  System.out.println("User is already there !!");
		  throw new Exception("User already present");
	  }
	  else {
		  
		 String password=user.getPassword();
		 String encryptPassword=encryptPassword(password);
		 user.setPassword(encryptPassword);
		  
		
		
		
		 
		user.setRoleName(role);
		 local=userRepo.save(user);
	  }
		
		return local;
	}


	public User findUser(String username) {
		
		return userRepo.findByUserName(username);
	}
	
	
	public  void deleteUser(Long userId) {
		
		
		userRepo.deleteById(userId);
	}
	


    //encrypt password
      private String encryptPassword(String password) {
      
	     String salt = BCrypt.gensalt();

	     
	     String hashedPassword = BCrypt.hashpw(password, salt);

	     return hashedPassword;
	 }

      //verify password
      
	   private boolean verifyPassword(String password, String hashedPassword) {
	    return BCrypt.checkpw(password, hashedPassword);
	}


	public ResponseEntity<String> signIn(UserInput input) throws Exception {
		
		User user=userRepo.findByEmail(input.getEmail());
		if(user==null) {
			
			System.out.println("Invalid Email");
			  throw new Exception("Invalid Email");
		}
		
		String password=input.getPassword();
		String encryPassword=user.getPassword();
		
		Boolean passwordVerification=verifyPassword(password, encryPassword);
		
		if(passwordVerification==false) {
			

			System.out.println("SignIn failed");
			  throw new Exception("SignIn Failed");
		}
		
		Authentication auth=new Authentication(user);
		
		authRepo.save(auth);
		
		return new ResponseEntity<String>(auth.getToken(),HttpStatus.OK);
		
		
		
		

	}


	public ResponseEntity<String> addCategory(ExamCategory examCategory, String token) {
	
		Authentication authentation=authRepo.findByToken(token);
		User user=authentation.getUser();
		String role=user.getRoleName();
		
		if(!role.equals("ADMIN")) {
			return new ResponseEntity<String>("Access Denied",HttpStatus.BAD_REQUEST);
		}
		
		examCategoryRepo.save(examCategory);
		
		
		
		return new ResponseEntity<String>("Sucessfully added",HttpStatus.OK);
	}


	public ResponseEntity<List<ExamCategory>> getAllExamCategory(String token) throws Exception {
		
		
		Authentication authentation=authRepo.findByToken(token);
		if(authentation==null) {
			throw new Exception("Authentication Fail!!");
		}
		
		List<ExamCategory>allCategory=new ArrayList<>();
		allCategory=examCategoryRepo.findAll();
		
		return new ResponseEntity<List<ExamCategory>>(allCategory,HttpStatus.ACCEPTED);
	}


	public ResponseEntity<String> addQuiz(QuizDao quiz, String token) {
		
		Authentication authentation=authRepo.findByToken(token);
		User user=authentation.getUser();
		String role=user.getRoleName();
	
		if(!role.equals("ADMIN")) {
			return new ResponseEntity<String>("Access Denied",HttpStatus.BAD_REQUEST);
		}
		
		
		
		Long categoryId=quiz.getCatgetoryId();
		ExamCategory category=examCategoryRepo.findById(categoryId).get();
		
		Quiz incomingQuiz=Quiz.builder().description(quiz.getDescription()).examCategory(category).numberOfQuestions(quiz.getNumberOfQuestions())
		.quizdurationInMillSec(quiz.getQuizdurationInMillSec()).quizName(quiz.getQuizName()).build();
		
		quizRepo.save(incomingQuiz);
		
		return new ResponseEntity<String>("Sucessfully added",HttpStatus.OK);
	}


	public ResponseEntity<String> addQuestion(QuestionDao question, String token) {
		
		
		Authentication authentation=authRepo.findByToken(token);
		User user=authentation.getUser();
		String role=user.getRoleName();
	
		if(!role.equals("ADMIN")) {
			return new ResponseEntity<String>("Access Denied",HttpStatus.BAD_REQUEST);
		}
		Long quizId=question.getQuizId();
		Quiz quiz=quizRepo.findById(quizId).get();
		
		Question incomingQuestion=Question.builder().answer(question.getAnswer()).option1(question.getOption1())
				.option2(question.getOption2()).option3(question.getOption3()).option4(question.getOption4()).question(question.getQuestion())
				.quiz(quiz).build();
		
		questionRepo.save(incomingQuestion);
		
		
		return new ResponseEntity<String>("Sucessfully added",HttpStatus.OK);
	}

	
	
	

	public ResponseEntity<List<Quiz>> getQuizByCatgeoryName(String categoryName, String token) throws Exception {
		
		
		Authentication authentation=authRepo.findByToken(token);
		if(authentation==null) {
			throw new Exception("Authentication Fail!!");
		}
		
		List<Quiz>getAllQuiz=new ArrayList<>();
		
		ExamCategory category=examCategoryRepo.findByCategoryName(categoryName);
		
	
		
		getAllQuiz=quizRepo.findByExamCategory(category);
		
		
		
		return new ResponseEntity<List<Quiz>>(getAllQuiz,HttpStatus.OK);
	}

	
	
	

	public  ResponseEntity<String> registerQuiz(Long quizId, String token) {
		
		Authentication authentation=authRepo.findByToken(token);
		
		if(authentation==null) {
			return new ResponseEntity<String>("Authentication fail",HttpStatus.BAD_GATEWAY);
		}
		User user=authentation.getUser();
		Quiz quiz=quizRepo.findById(quizId).get();
		
		
		
		ExamRegister register=new ExamRegister();
		Long userId=user.getId();
		
		
		register=examRegisterRepo.findByUserAndQuiz(userId,quizId);
		
		if(register!=null) {
			return new ResponseEntity<String>("You already registed for these contest no need to register again",HttpStatus.OK);
		}
		
		register=ExamRegister.builder().quiz(quiz).user(user).token(UUID.randomUUID().toString()).build();
		
		examRegisterRepo.save(register);
		return  new ResponseEntity<String>("Registration Sucessfull token is "+register.getToken(),HttpStatus.OK);
		
	}

	
	
	

	public ResponseEntity<List<QuestionOutput>> startExam(String regToken, String token)throws Exception {
		
		
		Authentication authentation=authRepo.findByToken(token);
		if(authentation==null) {
			throw new Exception("Authentication Fail!!");
		}
		User user=authentation.getUser();
		ExamRegister examRegister=examRegisterRepo.findByToken(regToken);
		if(examRegister==null) {
			throw new Exception("Please Register first man");
		}
		Quiz quiz=examRegister.getQuiz();
		User regUser=examRegister.getUser();
		if(user!=regUser) {
			
			throw new Exception("Please Register first bro");
		}
		List<Question>allQuestions=new ArrayList<>();
		
		Long quizId=quiz.getQuizId();
		
		allQuestions=questionRepo.findByQuizID(quizId);
		
		
		List<QuestionOutput>questionToUser=new ArrayList<>();
		
		for(int i=0;i<allQuestions.size();i++) {
			
			QuestionOutput questionOutput=new QuestionOutput();
			
			questionOutput=QuestionOutput.builder().question(allQuestions.get(i).getQuestion()).option1(allQuestions.get(i).getOption1()).
					option2(allQuestions.get(i).getOption2()).option3(allQuestions.get(i).getOption3()).option4(allQuestions.get(i).getOption4())
					.build();
			questionToUser.add(questionOutput);
					
		}
		
		StartExam startExam=new StartExam();
		startExam=StartExam.builder().date(LocalDate.now()).startTime(LocalTime.now()).quiz(quiz).user(regUser).build();
		
		startExamRepo.save(startExam);
		return new ResponseEntity<List<QuestionOutput>>(questionToUser,HttpStatus.OK);
	}




	public ResponseEntity<String> submit(String regToken, String token, UserAnswer userAnswer)throws Exception {
		

		Authentication authentation=authRepo.findByToken(token);
		if(authentation==null) {
			throw new Exception("Authentication Fail!!");
		}
		User user=authentation.getUser();
		Long userId=user.getId();
		ExamRegister examRegister=examRegisterRepo.findByToken(regToken);
		if(examRegister==null) {
			throw new Exception("Please Register first man");
		}
		Quiz quiz=examRegister.getQuiz();
		Long quizId=quiz.getQuizId();
		User regUser=examRegister.getUser();
		if(user!=regUser) {
			
			throw new Exception("Please Register first bro");
		}
		LocalDate date=LocalDate.now();
		System.out.println(date);
		System.out.println(quizId);
		System.out.println(userId);
		
		StartExam startExam=startExamRepo.findByDateAndUserAndQuiz(date,quizId,userId);
		
		LocalTime currentTime=LocalTime.now();
		
		LocalTime startTime=startExam.getStartTime();
		
		Duration duration = Duration.between(startTime,currentTime);

        // Get the duration in milliseconds
        Long milliseconds = duration.toMillis();
        if(milliseconds>quiz.getQuizdurationInMillSec()) {
        	
        	return new ResponseEntity<String>("Submission Failed Timelimit Exeed",HttpStatus.OK);
        }
        
		int count=0;
		
		List<String>correctAnswers=new ArrayList<>();
		
		
		correctAnswers=questionRepo.getAllAnswersById(quizId);
		List<String>userAnswers=new ArrayList<>();
		userAnswers=userAnswer.getUserAnswers();
		
		for(int i=0;i<userAnswers.size();i++) {
			
			if(userAnswers.get(i).equals(correctAnswers.get(i))) {
				count++;
				
			}
		}
		
		int numberOfQuestions=quiz.getNumberOfQuestions();
		int percentage=(count*100)/numberOfQuestions;
		
		examRegisterRepo.deleteByQuizIdAndUserId(quizId,userId);
		
		if(percentage>60) {
			
			Result result=new Result();
			result=Result.builder().pass(true).startExam(startExam).build();
			resultRepo.save(result);
			
			return new ResponseEntity<String>("Congrats You passed with "+percentage+"% mark",HttpStatus.OK);
		}
		Result result=new Result();
		result=Result.builder().pass(false).startExam(startExam).build();
		resultRepo.save(result);
		return new ResponseEntity<String>("Sorry you failed you attain only  "+percentage+"% mark better luck next time",HttpStatus.OK);
	}


	public ResponseEntity<List<TodaysAnalysis>> todaysResults(String token)throws Exception {
		
		Authentication authentation=authRepo.findByToken(token);
		User user=authentation.getUser();
		String role=user.getRoleName();
	
		if(!role.equals("ADMIN")) {
			throw new Exception("Access Denied!!!");
		}
		LocalDate todaysDate=LocalDate.now();
		List<StartExam>allExamStartToday=new ArrayList<>();
		allExamStartToday=startExamRepo.findByDate(todaysDate);
		
		List<TodaysAnalysis>todaysAnalysisList=new ArrayList<>();
		
		
		for(int i=0;i<allExamStartToday.size();i++) {
			
			TodaysAnalysis todaysAnalysis=new TodaysAnalysis();
			
			User usr=allExamStartToday.get(i).getUser();
			Quiz quiz=allExamStartToday.get(i).getQuiz();
			Result result=resultRepo.findBystartExam(allExamStartToday.get(i));
			if(result.isPass()==true) {
			
			todaysAnalysis=TodaysAnalysis.builder().email(usr.getEmail()).quizName(quiz.getQuizName()).userName(usr.getUserName()).result("Passed").build();
			}
			else {
				todaysAnalysis=TodaysAnalysis.builder().email(usr.getEmail()).quizName(quiz.getQuizName()).userName(usr.getUserName()).result("Failed").build();
			}
			
			todaysAnalysisList.add(todaysAnalysis);
		}
		
		
		return new ResponseEntity<List<TodaysAnalysis>>(todaysAnalysisList,HttpStatus.OK);
	}


	public ResponseEntity<String> signOut(String token) {
		
           Authentication authentation=authRepo.findByToken(token);
		
		if(authentation==null) {
			return new ResponseEntity<String>("Authentication fail",HttpStatus.BAD_GATEWAY);
		}
		
		authRepo.delete(authentation);
		return new ResponseEntity<String>("SignOut Sucessfully",HttpStatus.OK);
	}
	
	
	
	
	

}
