package com.pjt.testdemo.model.dao;

import java.util.HashMap;
import java.util.List;

import com.pjt.testdemo.model.dto.Comment;
import com.pjt.testdemo.model.dto.CommentResult;

public interface CommentDao {
	void writeComment(Comment comment);
	int alreadyExists(HashMap<String, String> param);
	List<CommentResult> getAllComments();
	List<CommentResult> getComments(String id);
}
