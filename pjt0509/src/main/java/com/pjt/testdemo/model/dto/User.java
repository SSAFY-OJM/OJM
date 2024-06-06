package com.pjt.testdemo.model.dto;

public class User {
	
	public static class LoginSet{
		private String id, password;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "LoginSet [id=" + id + ", password=" + password + "]";
		}
		
	}
	
	
	private String id;
	private String password;
	private boolean type; // 매니저인지 아닌지? : true이면 매니저 / false이면 User
	private int nth; // 기수
	private int floor; // 층
	private int dept; // 반
	private String name; 
	private String campus; // 캠퍼스
	
	public String getCampus() {
		return campus;
	}
	
	public void setCampus(String campus) {
		this.campus = campus;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public int getNth() {
		return nth;
	}
	public void setNth(int nth) {
		this.nth = nth;
	}
	public int getDept() {
		return dept;
	}
	public void setDept(int dept) {
		this.dept = dept;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", type=" + type + ", nth=" + nth + ", floor=" + floor
				+ ", dept=" + dept + ", name=" + name + "]";
	}
	
	
	
}
