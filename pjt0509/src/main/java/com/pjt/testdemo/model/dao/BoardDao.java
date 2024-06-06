package com.pjt.testdemo.model.dao;

import java.util.List;

import com.pjt.testdemo.model.dto.Board;

public interface BoardDao {
	void write(Board board);
	List<Board> getEntireBoard();
	Board getBoard(int id);
	void delete(int id);
}
