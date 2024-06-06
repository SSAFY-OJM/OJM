package com.pjt.testdemo.model.dto;

import java.sql.Date; // 시간 정보는 갖다버리고 사용하지 않을 때 sql.date를 쓴다 

public class Menu {
	private int id;
	private int type;
	private String content;
	private int status;
	private Date date; // java.sql.Date
	
	public Menu() {}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", type=" + type + ", content=" + content + ", date=" + date + "]";
	}
	
}
