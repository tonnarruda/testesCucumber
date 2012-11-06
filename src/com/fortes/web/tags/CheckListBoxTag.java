package com.fortes.web.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckListBoxTag extends TagSupport
{
	private String name = ""; // nome do componente
	private String label = ""; // label do componente
	private String list = ""; // elementos checks
	private String onClick = ""; // onclick do radio
	private String form = "document.forms[0]";
	private String width = "";
	private String height = "";
	private boolean valueString = false;
	private boolean readonly = false;

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

			String checkGroup = "";
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

			checkGroup += "<li id=\"wwgrp_"+ name +"\" class=\"wwgrp\"> <div id=\"wwlbl_"+ name +"\" class=\"wwlbl\"> " +
				"<label for=\""+ name +"\" class=\"desc\"> "+ label +": </label> </div>\n <div id=\"wwctrl_"+ name +"\" class=\"wwctrl\">\n";

			checkGroup += "<div class='listCheckBoxContainer' "+  dimension + "> <div class='listCheckBoxBarra'>";

			if(!readonly)
				checkGroup += "&nbsp;<span class='linkCheck' onclick=\"marcarDesmarcarListCheckBox("+ form +", '"+ name +"',true); "+ onClick +"\">" +
						"Marcar todos</span> | <span class='linkCheck' onclick=\"marcarDesmarcarListCheckBox("+ form +", '"+ name +"',false); "+ onClick +"\">" +
						"Desmarcar todos</span></div>";
			else
				checkGroup += "&nbsp;<span class='linkCheckDisabled'>" +
						"Marcar todos</span> | <span class='linkCheckDisabled'>" +
						"Desmarcar todos</span></div>";

			checkGroup += "<div id='listCheckBox"+ name +"' class='listCheckBox' " + dimensionList + ">";

			for (CheckBox cb : checks)
			{
				String check = "";

				if (cb.isSelecionado())
				{
					check = "checked";
				}

				//Caso o value do checkbox precise ser o nome. (Gustavo / Francisco) 07/01/2008
				String value = cb.getId().toString();
				if(valueString)
					value = cb.getNome();

				String disabled = "";
				if(readonly)
					disabled = "onclick=\"return false;\"";

				checkGroup += "<label for=\"checkGroup" + name + value + "\" ><input name=\"" + name + "\" value=\"" + value + "\" type=\"checkbox\""+disabled+""
						+ " id=\"checkGroup" + name + value + "\" " + check + " onclick=\""+ onClick +"\" >" + cb.getNome() + "</label>\n<br>";
			}

			checkGroup += "</div></div></li>";

			pageContext.getOut().print(checkGroup);
		}
		catch (IOException e)
		{
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}

	public String getList()
	{
		return list;
	}

	public void setList(String list)
	{
		this.list = list;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getOnClick()
	{
		return onClick;
	}

	public void setOnClick(String onClick)
	{
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

	public String getForm()
	{
		return form;
	}

	public void setForm(String form)
	{
		this.form = form;
	}
}