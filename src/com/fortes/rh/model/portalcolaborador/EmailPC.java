package com.fortes.rh.model.portalcolaborador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EmailPC extends AbstractAdapterPC
{
	private String to;
	private String subject; 
	private String title;
	private String body;
	
	public EmailPC() {
	}

	public EmailPC(String to,String subject,String title,String body) 
	{
		this.to = to;
		this.subject = subject;
		this.title = title;
		this.body = body;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
	
	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("to", gson.toJsonTree(this.to));
		jsonObject.add("subject", gson.toJsonTree(this.subject));
		jsonObject.add("title", gson.toJsonTree(this.title));
		jsonObject.add("body", gson.toJsonTree(this.body));
		
		return jsonObject.toString();
	}
}
