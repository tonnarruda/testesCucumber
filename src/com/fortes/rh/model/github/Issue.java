package com.fortes.rh.model.github;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.model.github.Label;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
public class Issue implements Serializable
{
	private String number;
	private String title;
	private String body;
	private String created_at;
	private String updated_at;
	private String closed_at;
	private String closed_by;
	private String state;
	private String url;
	private String comments;
	private User user;
	private User assignee;
	private Milestone milestone;
	private PullRequest pull_request;
	private Label[] labels;
	
	public Date getCreated_at_date() {
		return DateUtil.montaDataByStringJson(created_at);
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

	public String getClosed_by() {
		return closed_by;
	}

	public void setClosed_by(String closed_by) {
		this.closed_by = closed_by;
	}

	public Label[] getLabels() {
		return labels;
	}

	public void setLabels(Label[] labels) {
		this.labels = labels;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public PullRequest getPull_request() {
		return pull_request;
	}

	public void setPull_request(PullRequest pull_request) {
		this.pull_request = pull_request;
	}
}
