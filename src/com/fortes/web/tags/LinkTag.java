package com.fortes.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class LinkTag extends TagSupport
{
	private String href = "";
	private String onclick = "";
	private String imgTitle = "";
	private String imgTitleDisabled = "";
	private String imgName = "";
	private String verifyRole = "";
	private String iconeClass = "";
	private boolean opacity = false;
	private boolean disabled = false;
	
	public LinkTag()
	{
		super();
	}

	public int doStartTag() throws JspException
	{
		try {
		StringBuffer link = new StringBuffer("");

		pageContext.getOut().print(montaLink(link));
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public StringBuffer montaLink(StringBuffer link) 
	{
		if(verifyRole.equals("") || SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{verifyRole}))
		{
			if(!iconeClass.equals(""))
				montaLinkFonteAwesome(link);
			else
				montLink(link);
		}

		return link;
	}

	private void montLink(StringBuffer link) {
		link.append("<a");

		if(!disabled)
		{
			if(!href.equals(""))
				link.append(" href=\"" + href + "\"");

			if(!onclick.equals(""))
				link.append(" onclick=\"" + onclick + "\"");
		}else
			opacity = true;

		link.append(" >");

		if(!imgName.equals(""))
		{
			link.append("<img border=\"0\" ");

			if(disabled && !imgTitleDisabled.equals(""))
				link.append(" title=\"" + imgTitleDisabled + "\" ");
			else if(!imgTitle.equals(""))
				link.append(" title=\"" + imgTitle + "\" ");

			link.append(" src=\"/" +ArquivoUtil.getContextName()+"/imgs/" + imgName + "\"");	

			if(opacity)
				link.append(" style=\"opacity:0.2;filter:alpha(opacity=20);\"");

			link.append(" >");
		}

		link.append("</a>");
	}

	private void montaLinkFonteAwesome(StringBuffer link) {
		link.append("<a onmouseover=\"$(this).find('i').addClass('fa-2x').css('color','#6965ec')\" onmouseout=\"$(this).find('i').removeClass('fa-2x').css('color','black')\"");

		if(!disabled)
		{
			if(!href.equals(""))
				link.append(" href=\"" + href + "\"");

			if(!onclick.equals(""))
				link.append(" onclick=\"" + onclick + "\"");
		}else
			opacity = true;
		
		link.append("><i ");

		if(disabled && !imgTitleDisabled.equals(""))
			link.append(" title=\"" + imgTitleDisabled + "\" ");
		else if(!imgTitle.equals(""))
			link.append(" title=\"" + imgTitle + "\" ");

		link.append(" class='fa " + iconeClass +" fa-lg'  aria-hidden='true'");

		if(opacity)
			link.append(" style=\"opacity:0.2;filter:alpha(opacity=20);\"");

		link.append(" ></i>&nbsp;</a>");
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public String getImgTitle()
	{
		return imgTitle;
	}

	public void setImgTitle(String imgTitle)
	{
		this.imgTitle = imgTitle;
	}

	public String getImgName()
	{
		return imgName;
	}

	public void setImgName(String imgName)
	{
		this.imgName = imgName;
	}

	public String getOnclick()
	{
		return onclick;
	}

	public void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}

	public String getVerifyRole()
	{
		return verifyRole;
	}

	public void setVerifyRole(String verifyRole)
	{
		this.verifyRole = verifyRole;
	}

	public boolean isOpacity()
	{
		return opacity;
	}

	public void setOpacity(boolean opacity)
	{
		this.opacity = opacity;
	}

	public boolean isDisabled()
	{
		return disabled;
	}

	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
	
	public String getImgTitleDisabled()
	{
		return imgTitleDisabled;
	}

	public void setImgTitleDisabled(String imgTitleDisabled)
	{
		this.imgTitleDisabled = imgTitleDisabled;
	}

	public String getIconeClass() {
		return iconeClass;
	}

	public void setIconeClass(String iconeClass) {
		this.iconeClass = iconeClass;
	}
}