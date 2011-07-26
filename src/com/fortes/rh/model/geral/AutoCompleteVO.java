package com.fortes.rh.model.geral;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AutoCompleteVO
{
	@Id
	private String id;
    private String value;

    public AutoCompleteVO()
    {
    }
    
	public AutoCompleteVO(String id, String value)
	{
		this.id = id;
		this.value = value;
	}
	
	public AutoCompleteVO(Long id, String value)
	{
		if(id != null)
			this.id = String.valueOf(id);
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}