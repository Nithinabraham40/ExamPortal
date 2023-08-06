# Welcome to readme-md-generator &#x1F44B; 
![example workflow](https://img.shields.io/badge/Eclipse-Version%3A%202022--09%20(4.25.0)-orange)
![example workflow](https://img.shields.io/badge/SpringBoot-2.2.1-yellowgreen)
![example workflow](https://img.shields.io/badge/Java-8-yellowgreen)
![example workflow](https://img.shields.io/badge/Postman-v10.13-orange)
![example workflow](https://img.shields.io/badge/Documentation-Yes-green)
![example workflow](https://img.shields.io/badge/Manitained%3F-Yes-green)
 >CLI that generate beautiful **ReadME**.md files

  :house:  <b><span style="color: blue;">Homepage</span></b>
  


 # Prerequisties

 - **Eclipse >=4.55.0**
 - **Postman >=10.13**
 


# Install
```
Maven Install
SpringTool Install
```
 # Framework And Language

 - **Framework :  SpringBoot**
 - **Language :  Java**

 # Dependencies Required

 
 - **spring-boot-starter-web**
 - **spring-boot-devtools**
 - **spring-boot-starter-data-jpa**

 
 - **mysql-connector**
 - **lambok**
 - **jbcrypt**

 - **spring-boot-starter-test**
 


# Models Used



 - **User**
 -  **Question**
 -  **Authentication**
 -  **Quiz**
 -  **Result**
 -  **StartExam**
 -  **ExamRegister**
 -  **ExamCategory**
 
 


	
	



#  Data flow

- **User send a request to ApI endpoint**
- **api forward it to the controller**
- **controller forward it to the Service layer**
- **service layer provide the necessary business logic and ask the repository for data**
- **Repository fetch the data from Mysql and give it back to service layer**
- **service layer give it to controller**
- **contoller give it to api**
- **Api give the response to user**





#  Api end points used at User Controller

- **user/**
- **user/admin**
- **user/signin**
- **user//admin/{token}/add/category**
- **user/get/category/{token}**
- **user/add/quiz/{token}**
- **user/add/question/{token}**
- **user/get/quiz/categoryname/{name}/{token}**
- **user/register/quiz/{quizId}/{token}**
- **user/start/exam/{registrationtoken}/{token}**
- **user/submit/{registrationtoken}/{token}**
- **user/get/allexam/done/today/{token}**
- **user/signout/{token}**






# About my Examportal Project

 

  
  Introducing "ExamPortal" - Empowering Knowledge Assessment with Java-Powered Brilliance

Embark on a seamless educational journey with ExamPortal, a dynamic and innovative Java-based backend project crafted using the cutting-edge Spring Boot framework. Dive into a realm of unparalleled features designed to redefine the way users and administrators engage with online assessments.

🚀 Unveiling Unique Features:
🔐 Dual Access Magic: Seamlessly switch between user and admin roles through secure and hassle-free signup and login processes, ensuring airtight authentication.

🧩 Curate Quiz Galore: Empower administrators to effortlessly create and manage an extensive array of quiz categories and quizzes, amplifying the spectrum of knowledge exploration.

❓ Question Crafter: Unleash creativity as administrators compose thought-provoking questions for each quiz, paving the path for engaging and insightful user experiences.

📝 Select & Soar: Users can handpick their preferred quizzes and embark on a journey of self-discovery, igniting the quest for knowledge within a personalized environment.

⏰ Time Mastery: Challenge yourself against the clock as our meticulously integrated timer adds an element of excitement, ensuring a stimulating and efficient examination process.

📊 Instant Gratification: Witness real-time score calculation upon submission, offering instant insights into your performance and paving the way for informed self-improvement.

🎯 Admin Empowerment: Administrators can effortlessly track daily activities, gaining valuable insights into quiz participation, user engagement, and success rates, all in a single, streamlined interface.

Elevate your learning experience with ExamPortal, where knowledge meets innovation, and excellence is just a click away. Join us in shaping the future of online assessments – one quiz at a time.


#   User Controller
```

@RestController 
@RequestMapping("/user")

public class UserController {
	
	
	@Autowired
	private UserServiceImpl userService;
	
	//user signup
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
		
		
		String roleName="USER";
		
		return userService.createUser(user, roleName);
		
	}
	
	//admin signup
	@PostMapping("/admin")
	public User createAdmin(@RequestBody User user) throws Exception{
		
	String roleName="ADMIN";
		
		return userService.createUser(user, roleName);
		
		
	}
	
	//signin
	@GetMapping("/signIn")
	public ResponseEntity<String> getUser(@RequestBody UserInput input ) throws Exception{
		
		return userService.signIn(input);
		
	}
	
	
	//add quiz category for admin
	@PostMapping("/admin/{token}/add/category")
	
	public ResponseEntity<String>addExamCategory(@RequestBody ExamCategory examCategory,@PathVariable String token){
		
		
		return userService.addCategory(examCategory,token);
		
		
	}
	
	
	//see quiz category for both admin and user
	@GetMapping("get/category/{token}")
	
	public ResponseEntity<List<ExamCategory>>getAllExamCategory(@PathVariable("token") String token)throws Exception{
		
		
		
		return userService.getAllExamCategory(token);
	}
	
	
	//add quiz only for admin
	@PostMapping("add/quiz/{token}")
	public ResponseEntity<String>addQuiz(@RequestBody QuizDao quiz,@PathVariable("token")String token){
		
		return userService.addQuiz(quiz,token);
		
		
	}
	
	
	//add question only for admin
	@PostMapping("add/question/{token}")
	
	public ResponseEntity<String>addQuestion(@RequestBody QuestionDao question,@PathVariable("token")String token){
		
		
		return userService.addQuestion(question,token);
	}
	
	
	//get quiz by category
	@GetMapping("get/quiz/categoryname/{name}/{token}")
	
	public ResponseEntity<List<Quiz>>getQuizByCatgeoryName(@PathVariable("name")String categoryName,@PathVariable("token")String token)throws Exception{
		
		
		return userService.getQuizByCatgeoryName(categoryName,token);
	}
	
	
	
	//register for the quiz
	@PostMapping("register/quiz/{quizId}/{token}")
	
	public ResponseEntity<String>registerQuiz(@PathVariable("quizId")Long quizId,@PathVariable("token")String token){
		
		
		return userService.registerQuiz(quizId,token);
		
	}
	
	
	//start the quiz
	@GetMapping("start/exam/{registrationtoken}/{token}")
	
	public ResponseEntity<List<QuestionOutput>>startExam(@PathVariable("registrationtoken")String regToken,@PathVariable("token")String token) throws Exception{
		
		return userService.startExam(regToken,token);
	}
	
	
	//submit the quiz
	@PostMapping("submit/{registrationtoken}/{token}")
	
	public ResponseEntity<String>submit(@PathVariable("registrationtoken")String regToken,@PathVariable("token")String token,@RequestBody UserAnswer userAnser)throws Exception{
		return userService.submit(regToken,token,userAnser);
	}
	
	
	//for admin to see daily activities
	@GetMapping("get/allexam/done/today/{token}")
	
	public ResponseEntity<List<TodaysAnalysis>>todaysResults(@PathVariable("token") String token)throws Exception{
		
		
		return userService.todaysResults(token);
	}
	
	
	//signout
	@DeleteMapping("signout/{token}")
	
	public ResponseEntity<String>signOut(@PathVariable("token")String token){
		
		
		return userService.signOut(token);
	}

}

```







	
	


  


	







	



# DataBase Used

<details>
<summary><b><span style="color: white;">Clickme</span></b> &#x1F4F2; </summary>

*Mysql*






</details>






  




# Summary


   
   Experience education like never before with ExamPortal, a Java-powered marvel built on the robust Spring Boot framework. Seamlessly switch between user and admin roles, as you explore an expansive realm of quizzes and categories meticulously curated by administrators. Engage in stimulating exams against the ticking clock, with real-time score calculation, providing instant insights into your prowess. Administrators wield the power to craft questions, track daily activities, and shape a dynamic learning landscape. ExamPortal: Where knowledge thrives, innovation reigns, and excellence prevails."






# :handshake:  Contributing
  Contributions,issues and features request are welcome! 
  

  #


  This *README* was generated with &#x2764;&#xFE0F; by <b><span style="color: blue;">readme-md-generator</span></b> 
