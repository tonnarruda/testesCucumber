package com.fortes.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class FortesPagingTag extends TagSupport {

	private String totalSize;
	private String pagingSize;
	private String link;
	private String page;
	private String idFormulario;
	private String url;
	private String limpaCampos;

	public FortesPagingTag()
	{
		super();
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try
		{
			Integer p, ts, ps;
			p  = Integer.parseInt((page.replace(".", "")).replace(",", ""));
			ts = Integer.parseInt((totalSize.replace(".", "")).replace(",", ""));
			ps = Integer.parseInt((pagingSize.replace(".", "")).replace(",", ""));

			String links = PaginacaoUtil.makeRealPaging(p, ts, ps, link, idFormulario, url, limpaCampos);

			pageContext.getOut().print(links);
		}
		catch (IOException e)
		{
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPagingSize() {
		return pagingSize;
	}

	public void setPagingSize(String pagingSize) {
		this.pagingSize = pagingSize;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getIdFormulario()
	{
		return idFormulario;
	}

	public void setIdFormulario(String idFormulario)
	{
		this.idFormulario = idFormulario;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLimpaCampos() {
		return limpaCampos;
	}

	public void setLimpaCampos(String limpaCampos) {
		this.limpaCampos = limpaCampos;
	}

}