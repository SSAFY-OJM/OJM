package com.pjt.testdemo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.testdemo.model.dto.User;
import com.pjt.testdemo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
		
	@Autowired
	public UserController(UserService us) {
		userService = us;
	}
		
	@PostMapping("/join") // URL 유의해서 주세요
	public ResponseEntity<Void> signIn(@ModelAttribute("user") User user, @RequestParam(name="adcode", required=false) String adCode){
		printLog("signin");
		System.out.println(user);
		System.out.println(adCode);
		String adcode = "0524";
		if(adcode.equals(adCode)) userService.joinAdmin(user);
		else userService.joinUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@GetMapping("/join/valID")
	public ResponseEntity<Boolean> valID(@RequestParam(name="id") String id){
		printLog("valid");
		System.out.println(id);
		if(!id.isBlank()) { // 문자열이 비어 있지 않다면
			if(userService.valID(id)) return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	//public ResponseEntity<Boolean> login(@ModelAttribute("user") User.LoginSet set, HttpServletRequest httpServletRequest){
	public ResponseEntity<User> login(@ModelAttribute("user")User.LoginSet set, HttpServletRequest httpServletRequest){
		printLog("login");
		
		User result = userService.login(set, httpServletRequest);
		if(result != null) { // 로그인에 성공한 것임
			return new ResponseEntity<User>(result, HttpStatus.OK);
		}
		// 로그인 실패
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		
	//	if(userService.login(set, httpServletRequest)) return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	//	return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest){
		printLog("logout");
		userService.logout(httpServletRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	public void printLog(String request) {
		System.out.printf("Requested %s: ", request.toUpperCase());
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));
	}
}
