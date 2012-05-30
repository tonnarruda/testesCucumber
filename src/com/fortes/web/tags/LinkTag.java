package com.fortes.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.fortes.rh.security.SecurityUtil;
import com.opensymphony.xwork.ActionContext;

public class LinkTag extends TagSupport
{
	private String href = "";
	private String imgTitle = "";
	private String imgName = "";
	private String verifyRole = "";
	private String onclick = "";
	
	public LinkTag()
	{
		super();
	}

	public int doStartTag() throws JspException
	{
		try {
		StringBuffer link = new StringBuffer("");

		if(verifyRole.equals("") || SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{verifyRole}))
		{
			link.append("<a");
			
			if(!href.equals(""))
				link.append(" href=\"" + href + "\"");

			if(!onclick.equals(""))
				link.append(" onclick=\"" + onclick + "\"");
			
			link.append(" >");
			
			if(!imgName.equals(""))
			{
				link.append(" <img border=\"0\" ");
				
				if(!imgTitle.equals(""))
					link.append(" title=\"" + imgTitle + "\" ");
				
				link.append(" src=\"/fortesrh/imgs/" + imgName + "\"");				
				link.append(" >");
			}
			
			link.append("</a>");
		}

		pageContext.getOut().print(link);
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getVerifyRole() {
		return verifyRole;
	}

	public void setVerifyRole(String verifyRole) {
		this.verifyRole = verifyRole;
	}
}