package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class Issue implements Serializable
{
	private String id;
	private String title;
	private String body;
//	private String created_at;
//	private String updated_at;
//	private String closed_at;
//	private String state;
//	private String number;
//	private String html_url;
//	private String assignee;
//	private String milestone;
//	private String comments;
//	private String pull_request;
//	private String user;
//	private String url;
//	private String labels;
//	private String patch_url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
