package com.fortes.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fortes.security.auditoria.NaoAudita;

@MappedSuperclass
public abstract class AbstractModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@NaoAudita
	public String[] getDependenciasDesconsideradasNaRemocao(){
		return null;
	}
	
	@Override
	public boolean equals(Object object)
	{
    	if(object == null)
    		return false;

    	if(object == this)
    		return true;

    	if (!object.getClass().getName().equals(this.getClass().getName()))
    		return false;

    	if(this.getId() == null)
    		return false;

    	return this.getId().equals(((AbstractModel)object).getId());
	}

	@Override
	public int hashCode()
	{
	   return (this.getId() != null ? this.getId().hashCode() : 0);
	}
	
}