package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@Entity
@SequenceGenerator(name="sequence", sequenceName="grupoocupacional_sequence", allocationSize=1)
public class GrupoOcupacional extends AbstractModel implements Serializable
{

	private static final long serialVersionUID = -4853064334544443437L;
	
	@Column(length=100)
	private String nome;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="grupoOcupacional")
	private Collection<Cargo> cargos;
	@ManyToOne
	private Empresa empresa;

	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(projectionEmpresaId);
	}
	
	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos)
	{
		this.cargos = cargos;
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

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId()).append("nome", this.nome).toString();
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
}