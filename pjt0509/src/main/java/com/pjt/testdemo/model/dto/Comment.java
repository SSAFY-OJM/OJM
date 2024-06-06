package com.pjt.testdemo.model.dto;

import java.util.Date; // 시간 정보도 필요하거나 SQL column type이 TIMESTAMP라면 util.Date를 쓴다

public class Comment {
	
	private int id; // AI, readonly
	private String userId; // user FK, 누가 작성한 comment인가?
	private int menu; // menu FK, 무슨 메뉴에 대한 comment인가?
	private String content; // comment 내용
	private int rate; // 부여한 별점
	private Date date; // 작성일시
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getMenu() {
		return menu;
	}
	public void setMenu(int menu) {
		this.menu = menu;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", menu=" + menu + ", content=" + content + ", rate=" + rate
				+ ", date=" + date + "]";
	}
	
	
}
