package com.pjt.testdemo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.testdemo.model.dto.Board;
import com.pjt.testdemo.service.BoardService;
import com.pjt.testdemo.service.MenuService;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	private final MenuService menuService;
	
	@Autowired
	public BoardController(BoardService bs, MenuService ms) {
		boardService = bs;
		menuService = ms;
	}
	
	@PostMapping("/write")
	public ResponseEntity<Void> write(@RequestBody Board board){
		printLog("boardwrite");
		boardService.write(board);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("") // 목록 전체보기
	public ResponseEntity<List<Board>> list(){
		printLog("boardlist");
		List<Board> result = boardService.getEntireBoard();
		boolean status = result == null || result.isEmpty() || result.size() == 0 ? false : true;
		if(status) return new ResponseEntity<List<Board>>(result, HttpStatus.OK);
		return new ResponseEntity<List<Board>>(HttpStatus.NOT_FOUND);
	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<Object>> detail(@PathVariable("id") int id){
		printLog("boarddetail");
	// public ResponseEntity<JSONObject> detail(@PathVariable("id") int id){
	//	JSONArray menus = new JSONArray();
	//	JSONObject result = new JSONObject();
		List<Object> result = new ArrayList<>(); 
		result.add(boardService.getBoard(id));
		result.add(menuService.getBoard(id));
		
		boolean status = result == null || result.isEmpty() || result.size() == 0 ? false : true;
		if(status) return new ResponseEntity<List<Object>>(result, HttpStatus.OK);
		return new ResponseEntity<List<Object>>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id){
		printLog("boarddelete");
		System.out.println(id);
		boardService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	public void printLog(String request) {
		System.out.printf("Requested %s: ", request.toUpperCase());
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));
	}
}
