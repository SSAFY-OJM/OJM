package com.pjt.testdemo.service;

import java.util.List;

import com.pjt.testdemo.model.dto.Comment;
import com.pjt.testdemo.model.dto.CommentResult;


public interface CommentService {
	void writeComment(Comment comment);
	boolean alreadyExists(Comment comment);
	List<CommentResult> getAllComments();
	List<CommentResult> getComments(String id);
	
	
}
