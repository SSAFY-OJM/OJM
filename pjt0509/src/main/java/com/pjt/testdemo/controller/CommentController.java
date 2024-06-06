package com.pjt.testdemo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.testdemo.model.dto.Comment;
import com.pjt.testdemo.model.dto.CommentResult;
import com.pjt.testdemo.service.CommentService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
		
	@Autowired
	public CommentController(CommentService cs) {
		commentService = cs;
	}

	@PostMapping("/write")
	public ResponseEntity<Void> writeComment(@ModelAttribute("comment") Comment comment){
		printLog("writecomment");
		System.out.println(comment);
		 // 이미 작성한 코멘트 없음
		if(!commentService.alreadyExists(comment)) {
			commentService.writeComment(comment);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("")
	public ResponseEntity<List<CommentResult>> getAllComments(){
		printLog("getallcomments");
		List<CommentResult> result = commentService.getAllComments();
		boolean status = result == null || result.isEmpty() || result.size() == 0 ? false : true;
		if(status) return new ResponseEntity<List<CommentResult>>(result, HttpStatus.OK);
		return new ResponseEntity<List<CommentResult>>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<CommentResult>> getComments(@PathVariable("id") String id){
		printLog("getcomments");
		List<CommentResult> result = commentService.getComments(id);
		boolean status = result == null || result.isEmpty() || result.size() == 0 ? false : true;
		if(status) return new ResponseEntity<List<CommentResult>>(result, HttpStatus.OK);
		return new ResponseEntity<List<CommentResult>>(HttpStatus.NOT_FOUND);
	
	}
	
	public void printLog(String request) {
		System.out.printf("Requested %s: ", request.toUpperCase());
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));
	}
}
