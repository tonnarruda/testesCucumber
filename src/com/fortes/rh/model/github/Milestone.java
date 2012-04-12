package com.fortes.rh.model.github;

public class Milestone {

	private String url;
	private String number;
	private String state;
	private String title;
	private String description;
	private User user;
	private String open_issues;
	private String closed_issues;
	private String created_at;
	private String due_on;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getOpen_issues() {
		return open_issues;
	}
	
	public void setOpen_issues(String open_issues) {
		this.open_issues = open_issues;
	}
	
	public String getClosed_issues() {
		return closed_issues;
	}
	
	public void setClosed_issues(String closed_issues) {
		this.closed_issues = closed_issues;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public String getDue_on() {
		return due_on;
	}
	
	public void setDue_on(String due_on) {
		this.due_on = due_on;
	}
}
