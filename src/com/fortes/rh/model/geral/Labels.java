package com.fortes.rh.model.geral;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Labels implements Serializable
{
	private String name;
	private String color;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
