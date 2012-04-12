package com.fortes.rh.model.github;

public class User {

	private String login;
	private String id;
    private String avatar_url;
	private String gravatar_id;
	private String url;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAvatar_url() {
		return avatar_url;
	}
	
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	
	public String getGravatar_id() {
		return gravatar_id;
	}
	
	public void setGravatar_id(String gravatar_id) {
		this.gravatar_id = gravatar_id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
