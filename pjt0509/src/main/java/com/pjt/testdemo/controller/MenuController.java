package com.pjt.testdemo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.testdemo.model.dto.Menu;
import com.pjt.testdemo.service.MenuService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/menu")
public class MenuController {
	private final MenuService menuService;

	@Autowired
	public MenuController(MenuService ms) {
		menuService = ms;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addMenus(@RequestBody List<Menu> menus // 여기 파라메터 어떻게 받을겨
	) {
		for (Menu m : menus) {
			menuService.addMenus(m);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<Menu>> weeklyMenu() {
		printLog("weeklymenu");
		List<Menu> result = menuService.weeklyMenu();
		boolean status = result == null || result.isEmpty() || result.size() == 0 ? false : true;

		if (status) {
			for (Menu m : result) {
				m.setContent((m.getContent().replace("\\n", "<br/>")));
			}
			return new ResponseEntity<List<Menu>>(result, HttpStatus.OK);
		}
		return new ResponseEntity<List<Menu>>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/soldout/{id}/{status}")
	public ResponseEntity<Void> setSoldout(@PathVariable("id") int id, @PathVariable("status") int status) {
		printLog("setsoldout");
		// menu id를 받아서 목표 status로 변경하면 된다.
		System.out.println(id);
		System.out.println(menuService.setSoldout(id, status));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/soldout")
	public ResponseEntity<List<Integer>> getSoldout() {
		printLog("getsoldout");
		List<Integer> result = menuService.getSoldout();
		if (result.size() == 0)
			System.out.println("GOT EMPTY");
		return new ResponseEntity<List<Integer>>(result, HttpStatus.OK);
	}

	// 테스트
	@PutMapping("/test")
	public ResponseEntity<Void> testing() {
		printLog("test");
		// System.out.println(menuService.test());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@Scheduled(cron = "${schedule.cron}")
	public void sendMM() {
		List<Menu> result = menuService.todayMenu();

		boolean status = result == null || result.isEmpty() || result.size() != 5 ? false : true;
		if (status) {
			for (Menu m : result) {
				m.setContent((m.getContent().replace("*", "")));
				m.setContent((m.getContent().replace("\\n", ", ")));
			}
			
			String message = "### :duck-walk: "+result.get(0).getDate()+" 오늘의 메뉴 :duck_dance:\\r\\n"
							+ "|위치|이름|메뉴|\\r\\n|:------|:------:|:------|\\r\\n"
							+ "|20F|한식(A코스)|"+result.get(0).getContent()+"|\\r\\n"
							+ "|20F|일품(B코스)|"+result.get(1).getContent()+"|\\r\\n"
							+ "|10F|도시락|"+result.get(2).getContent()+"|\\r\\n"
							+ "|10F|샌드위치&샐러드|"+result.get(3).getContent()+"|\\r\\n"
							+ "|10F|샐러드|"+result.get(4).getContent()+"|";
			
			System.out.println(message);
			try {

				URL url = new URL("https://meeting.ssafy.com/hooks/wp3hwhdq7tbb7qcpteydrd9dwa");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				String requestBody = "{\"channel\":\"Menu\",\"text\":\""+message+"\"}";
				System.out.println(requestBody);
				conn.setDoOutput(true);
				OutputStream os = conn.getOutputStream();
				os.write(requestBody.getBytes());
				os.flush();
				os.close();
				int responseCode = conn.getResponseCode();
				System.out.println("Response Code: " + responseCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println("Response Body: " + response.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 콘솔에 요청 시각, 메서드 출력
	public void printLog(String request) {
		System.out.printf("Requested %s: ", request.toUpperCase());
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));
	}

}
