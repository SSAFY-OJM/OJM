package com.pjt.testdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.testdemo.model.dao.BoardDao;
import com.pjt.testdemo.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardDao boardDao;

	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	@Override
	public void write(Board board) {
		boardDao.write(board);
	}

	@Override
	public List<Board> getEntireBoard() {
		return boardDao.getEntireBoard();
	}

	@Override
	public Board getBoard(int id) {
		return boardDao.getBoard(id);
	}
	@Override
	public void delete(int id) {
		boardDao.delete(id);
	}


}
