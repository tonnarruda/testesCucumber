package com.fortes.rh.model.geral;

import java.util.Collection;


public class AreaOrganizacionalOrganograma
{
	private String id;
	private String title;
	private String subtitle;
	private String subtitle2;
	private String type; // staff, collateral or subordinate
	private Collection<AreaOrganizacionalOrganograma> children;
	
	public AreaOrganizacionalOrganograma()
	{
		
	}

	public AreaOrganizacionalOrganograma(String id, String title, String subtitle,String subtitle2, String type) 
	{
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.subtitle2 = subtitle2;
		this.type = type;
	}


	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Collection<AreaOrganizacionalOrganograma> getChildren() {
		return children;
	}
	
	public void setChildren(Collection<AreaOrganizacionalOrganograma> children) {
		this.children = children;
	}

	public String getSubtitle2() {
		return subtitle2;
	}

	public void setSubtitle2(String subtitle2) {
		this.subtitle2 = subtitle2;
	}
}
