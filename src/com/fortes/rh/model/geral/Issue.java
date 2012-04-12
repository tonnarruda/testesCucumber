package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
public class Issue implements Serializable
{
	private String id;
	private String title;
	private String body;
	private String created_at;
	private String updated_at;
	private String closed_at;
	private String state;
	private String number;
	private String[] labels;
//	private String html_url;
//	private String assignee;
//	private String milestone;
//	private String comments;
//	private String pull_request;
//	private String user;
//	private String url;
//	private String patch_url;
	
	public Date getCreated_at_date() {
		return DateUtil.montaDataByStringJson(created_at);
	}
	
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
	
	public String getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public String getUpdated_at() {
		return updated_at;
	}
	
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public String getClosed_at() {
		return closed_at;
	}
	
	public void setClosed_at(String closed_at) {
		this.closed_at = closed_at;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}
}
