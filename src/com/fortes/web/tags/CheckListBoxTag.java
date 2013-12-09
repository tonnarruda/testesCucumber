package com.fortes.web.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class CheckListBoxTag extends TagSupport
{
	private String name = ""; // nome do componente
	private String label = ""; // label do componente
	private String list = ""; // elementos checks
	private String onClick = ""; // onclick do radio
	private String form = "document.forms[0]";
	private String width = "";
	private String height = "";
	private String liClass = "";
	private boolean valueString = false;
	private boolean readonly = false;
	private boolean filtro = false;
	private boolean showTitle = false;

	public CheckListBoxTag()
	{
		super();
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try
		{
			ServletRequest request = pageContext.getRequest();
			Collection<CheckBox> checks = (Collection<CheckBox>) request.getAttribute(list);

			StringBuilder checkGroup = new StringBuilder();
			String dimension = "";
			String dimensionList = "";

			if (!height.equals("") || !width.equals(""))
			{
				dimension = "style='";

				if (!height.equals(""))
				{
					dimension += "height: " + height + "px;";
					dimensionList = "style='height: " + (Integer.valueOf(height) - 20) +"px;'";
				}

				if (!width.equals(""))
					dimension += "width: " + width + "px;";

				dimension += "'";
			}

			String labelFormatado = label + (StringUtils.isBlank(label)?"":":");
			
			String clss = "wwgrp";
			if (StringUtils.isNotEmpty(liClass))
				clss += " " + liClass;
					
			checkGroup.append("<li id=\"wwgrp_"+ name +"\" class=\"" + clss + "\"> <div id=\"wwlbl_"+ name +"\" class=\"wwlbl\"> ");
			checkGroup.append("<label for=\""+ name +"\" class=\"desc\"> "+ labelFormatado +" </label> </div>\n <div id=\"wwctrl_"+ name +"\" class=\"wwctrl\">\n");

			checkGroup.append("<div class='listCheckBoxContainer' "+  dimension + "> <div class='listCheckBoxBarra'>");

			if (filtro)
				checkGroup.append("<input type=\"text\" id=\"listCheckBoxFilter" + name + "\" class=\"listCheckBoxFilter\" title=\"Digite para filtrar\"/>\n");
			
			if(!readonly)
			{
				checkGroup.append("&nbsp;<span class='linkCheck' onclick=\"marcarDesmarcarListCheckBox("+ form +", '"+ name +"',true); "+ onClick +"\">");
				checkGroup.append("Marcar todos</span> | <span class='linkCheck' onclick=\"marcarDesmarcarListCheckBox("+ form +", '"+ name +"',false); "+ onClick +"\">");
				checkGroup.append("Desmarcar todos</span>");
			}
			else
			{
				checkGroup.append("&nbsp;<span class='linkCheckDisabled'>");
				checkGroup.append("Marcar todos</span> | <span class='linkCheckDisabled'>");
				checkGroup.append("Desmarcar todos</span>");
			}
			
			checkGroup.append("</div><div id='listCheckBox"+ name +"' class='listCheckBox' " + dimensionList + ">");

			for (CheckBox cb : checks)
			{
				String check = "";

				if (cb.isSelecionado())
				{
					check = "checked";
				}

				//Caso o value do checkbox precise ser o nome. (Gustavo / Francisco) 07/01/2008
				String value = cb.getId();
				if(valueString)
					value = cb.getNome();

				String disabled = "";
				if(readonly)
					disabled = "onclick=\"return false;\"";

				checkGroup.append("<label for=\"checkGroup" + name + value + "\"><input name=\"" + name + "\" value=\"" + value + "\" type=\"checkbox\""+disabled+"");
				checkGroup.append(" id=\"checkGroup" + name + value + "\" " + check + " onclick=\""+ onClick +"\">"); 
				
				if(showTitle)
				{
					String conteudo = cb.getNome().length() >= 130 ? cb.getNome().substring(0, 130)+"..." : cb.getNome(); 					
					
					checkGroup.append("<span href=# style=\"cursor:help;\" " );
					checkGroup.append("				onmouseout=\"hideTooltip()\" " );
					checkGroup.append(" 			onmouseover=\"showTooltip(event,'" + cb.getNome() + "', 600);return false\" > ");
					checkGroup.append(				conteudo);
					checkGroup.append("</span>");
				}
				else
					checkGroup.append(cb.getNome());
				
				checkGroup.append(" </label>\n");
			}

			checkGroup.append("</div></div></li>");

			pageContext.getOut().print(checkGroup);
		}
		catch (IOException e)
		{
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
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

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isValueString() {
		return valueString;
	}

	public void setValueString(boolean valueString) {
		this.valueString = valueString;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public void setFiltro(boolean filtro) {
		this.filtro = filtro;
	}

	public String getLiClass() {
		return liClass;
	}

	public void setLiClass(String liClass) {
		this.liClass = liClass;
	}
	
	public boolean isShowTitle()
	{
		return showTitle;
	}
	
	public void setShowTitle(boolean showTitle)
	{
		this.showTitle = showTitle;
	}
}