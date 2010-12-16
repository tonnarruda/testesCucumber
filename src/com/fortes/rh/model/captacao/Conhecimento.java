package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="conhecimento_sequence", allocationSize=1)
public class Conhecimento extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<AreaOrganizacional> areaOrganizacionals;
	@ManyToOne
	private Empresa empresa;
	@Lob
	private String observacao;

	public Conhecimento()
	{

	}

	public Conhecimento(Long id, String nome)
	{
		this.setId(id);
		this.nome = nome;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}
	
	public void addAreaOrganizacional(Long areaId)
	{
		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setId(areaId);
		
		if (areaOrganizacionals == null)
			areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		
		areaOrganizacionals.add(areaOrganizacional);
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
		if (this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}
	
	@Override
	public String toString()
	{
		 ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		 string.append("id", this.getId());
		 string.append("nome", this.nome);

		 return string.toString();
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}