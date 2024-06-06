package com.pjt.testdemo.model.dto;

import java.util.Date;

public class CommentResult {
	
		private int id, type, rate;
		private Date menudate, commentdate;
		private String menu, comment;
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
		public int getRate() {
			return rate;
		}
		public void setRate(int rate) {
			this.rate = rate;
		}
		public Date getMenuDate() {
			return menudate;
		}
		public void setMenuDate(Date menudate) {
			this.menudate = menudate;
		}
		public Date getCommentDate() {
			return commentdate;
		}
		public void setCommentDate(Date commentDate) {
			this.commentdate = commentDate;
		}
		public String getMenu() {
			return menu;
		}
		public void setMenu(String menu) {
			this.menu = menu;
		}
		public String getContent() {
			return comment;
		}
		public void setContent(String content) {
			this.comment = content;
		}
		@Override
		public String toString() {
			return "CommentResult [id=" + id + ", type=" + type + ", rate=" + rate + ", menudate=" + menudate
					+ ", commentDate=" + commentdate + ", menu=" + menu + ", comment=" + comment + "]";
		}
		
		
		
}
