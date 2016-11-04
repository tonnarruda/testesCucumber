package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="areainteresse_sequence", allocationSize=1)
public class AreaInteresse extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@Lob
	private String observacao;
	@ManyToMany(targetEntity=AreaOrganizacional.class)
	private Collection<AreaOrganizacional> areasOrganizacionais;

	@ManyToOne
	private Empresa empresa;

	//Projections
	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();

		this.empresa.setId(projectionEmpresaId);
	}
	
	public Collection<AreaOrganizacional> getAreasOrganizacionais()
	{
		return areasOrganizacionais;
	}

	public void setAreasOrganizacionais(
			Collection<AreaOrganizacional> areasOrganizacionais)
	{
		this.areasOrganizacionais = areasOrganizacionais;
	}

	@ChaveDaAuditoria
	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId()).append("nome", this.nome).append(
						"observacao", this.observacao)
				.toString();
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
}