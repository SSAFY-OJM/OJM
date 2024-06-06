package com.pjt.testdemo.service;

import java.util.List;

import com.pjt.testdemo.model.dto.Board;

public interface BoardService {
	void write(Board board);
	List<Board> getEntireBoard();
	Board getBoard(int id);
	void delete(int id);
	
}
