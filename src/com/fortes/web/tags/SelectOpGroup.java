package com.fortes.web.tags;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;


@SuppressWarnings("serial")
public class SelectOpGroup extends TagSupport{

	private String id;
	private String name;
	private String label;
	private String map;
	private String optionValue;
	private String optionText;
	private String onchange = "";
	private String required = "false";
	private String liClass;
	private String cssStyle = "";
	private String disabled = "false";
	private String theme = "";
	
	public SelectOpGroup() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try {
			if(id == null || name == null || map == null)
				return 0;
			
			ServletRequest request = pageContext.getRequest();
			LinkedHashMap<Object, Collection<Object>> objects = (LinkedHashMap<Object, Collection<Object>>) request.getAttribute(map);
			Object objectValor = (Object) request.getAttribute(name);
			
			String itemSelecionadoValor = "";

			if(objectValor != null)
				itemSelecionadoValor = objectValor.toString();
					
			String labelFormatado = label + (StringUtils.isBlank(label)?"":":");
			if (Boolean.getBoolean(required))
				labelFormatado+= "*";
			
			StringBuilder selectGroup = new StringBuilder();
			
			String clss = "wwgrp";
			if (StringUtils.isNotEmpty(liClass))
				clss += " " + liClass;
			
			
			Method methodKey = null;
			Method methodValue = null;
			String value = null;
			String text =  null;
			
			if(!theme.equals("simple")){
				selectGroup.append("<li id=\"wwgrp_"+ id +"\" class=\"" + clss + "\">");
				selectGroup.append("	<div id=\"wwlbl_"+ id +"\" class=\"wwlbl\"> ");
				selectGroup.append("		<label for=\""+ id +"\" class=\"desc\"> "+ labelFormatado +" </label>");
				selectGroup.append("	</div>");
				selectGroup.append("	<div id=\"wwctrl_"+ id +"\" class=\"wwctrl\">");
			}
			
			selectGroup.append("	<select name=\""+name+"\" id=\""+ id + "\" style=\""+cssStyle+"\" onchange=\""+onchange+"\" " +getDisabled()+">");
			
			if(objects == null || objects.isEmpty())
				selectGroup.append("<option value=\"0\">Nenhum</option>");
			else{
				for (Map.Entry<Object,Collection<Object>> map : objects.entrySet()) {
					selectGroup.append("		<optgroup label=\""+map.getKey()+"\">");
					for (Object object : map.getValue()) {
						methodKey = object.getClass().getMethod("get" +  StringUtils.capitalize(optionValue));
						methodValue = object.getClass().getMethod("get" +  StringUtils.capitalize(optionText));
						
						value = methodKey.invoke(object,new Object[]{}).toString();
						text =  methodValue.invoke(object,new Object[]{}).toString();
						if(value.equals(itemSelecionadoValor))
							selectGroup.append("		<option value=\""+value+"\" selected>" + text + "</option>");
						else
							selectGroup.append("		<option value=\""+value+"\">" + text + "</option>");
					}
					selectGroup.append("		</optgroup>");
				}
			}
			selectGroup.append("		</select>");
			
			if(!theme.equals("simple")){
				selectGroup.append("	</div>");
				selectGroup.append("</li>");
			}
			
			pageContext.getOut().print(selectGroup);			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getRequired() {
		return this.required;
	}
	
	public void setRequired(String required) {
		this.required = required;
	}

	public String getLiClass() {
		return liClass;
	}

	public void setLiClass(String liClass) {
		this.liClass = liClass;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getDisabled() {
		if(this.disabled.equals("true"))
			return "disabled";
		else
			return "";
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}