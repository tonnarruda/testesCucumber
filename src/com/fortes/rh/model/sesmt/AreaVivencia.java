package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="areaVivencia_sequence", allocationSize=1)
public class AreaVivencia extends AbstractModel implements Serializable
{
	private String nome;
	@ManyToOne
	private Empresa empresa;
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public Empresa getEmpresa()
	{
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}
}
