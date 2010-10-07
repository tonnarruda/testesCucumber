package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;

@Entity
@SequenceGenerator(name="sequence", sequenceName="certificacao_sequence", allocationSize=1)
public class Certificacao extends AbstractModel implements Serializable
{
	@Transient private static final long serialVersionUID = 1L;
	
	private String nome;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Curso> cursos;
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="certificacaos")
	private Collection<FaixaSalarial> faixaSalarials;
	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public Collection<FaixaSalarial> getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public void setFaixaSalarials(Collection<FaixaSalarial> faixaSalarials)
	{
		this.faixaSalarials = faixaSalarials;
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