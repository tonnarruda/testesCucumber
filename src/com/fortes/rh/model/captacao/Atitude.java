package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="atitude_sequence", allocationSize=1)
public class Atitude extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;
	@Lob
	private String observacao;
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<AreaOrganizacional> areaOrganizacionals;

	public Atitude()	{
	}

	public Atitude(Long id, String nome)
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

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setEmpresaId(Long empresaId)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}
	
	public Collection<AreaOrganizacional> getAreaOrganizacionals() {
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals) {
		this.areaOrganizacionals = areaOrganizacionals;
	}
}
