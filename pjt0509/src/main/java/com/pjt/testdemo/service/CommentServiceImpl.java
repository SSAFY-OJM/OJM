package com.pjt.testdemo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.testdemo.model.dao.CommentDao;
import com.pjt.testdemo.model.dto.Comment;
import com.pjt.testdemo.model.dto.CommentResult;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentDao commentDao;
	
	@Autowired
	public CommentServiceImpl(CommentDao dao) {
		commentDao = dao;
	}
	

	@Override
	public void writeComment(Comment comment) {
		commentDao.writeComment(comment);
	}

	@Override
	public List<CommentResult> getAllComments() {
		return commentDao.getAllComments();
	}


	@Override
	public List<CommentResult> getComments(String id) {
		return commentDao.getComments(id);
	}


	@Override
	public boolean alreadyExists(Comment comment) {
		HashMap<String, String> param = new HashMap<>();
		param.put("menuId", comment.getMenu()+"");
		param.put("userId", comment.getUserId());
		int res = commentDao.alreadyExists(param);
		System.out.println(res);
		boolean result = commentDao.alreadyExists(param) > 0 ? true : false;
		return result;
	}

}
