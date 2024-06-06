package com.pjt.testdemo.model.dto;

public class Board {
	private int id;
	private String userId;
	private java.util.Date date; // timestamp를 쓰려면 java.util.Date
	private java.sql.Date startdate, enddate;
	
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
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public java.sql.Date getStartdate() {
		return startdate;
	}
	public void setStartdate(java.sql.Date startdate) {
		this.startdate = startdate;
	}
	public java.sql.Date getEnddate() {
		return enddate;
	}
	public void setEnddate(java.sql.Date enddate) {
		this.enddate = enddate;
	}
	@Override
	public String toString() {
		return "Board [id=" + id + ", userId=" + userId + ", date=" + date + ", startdate=" + startdate + ", enddate="
				+ enddate + "]";
	}
	
	
	
}
