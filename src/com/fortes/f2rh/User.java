package com.fortes.f2rh;

public class User {
	
	private String login; //cpf
	private String email;
	private String name;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login.replace(".", "").replace("-", "");
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	
}
