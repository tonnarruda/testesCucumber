package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="habilidade_sequence", allocationSize=1)
public class Habilidade extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;
	private String observacao;
	@ManyToMany
	private Collection<AreaOrganizacional> areaOrganizacionals;
	@ManyToMany
	private Collection<Curso> cursos; 
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="habilidade", cascade=CascadeType.ALL)
	private Collection<CriterioAvaliacaoCompetencia> criteriosAvaliacaoCompetencia;
	
	public Habilidade()	{
	}

	public Habilidade(Long id, String nome)
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

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public Collection<CriterioAvaliacaoCompetencia> getCriteriosAvaliacaoCompetencia() {
		if (criteriosAvaliacaoCompetencia == null)
			criteriosAvaliacaoCompetencia = new ArrayList<CriterioAvaliacaoCompetencia>();
		return criteriosAvaliacaoCompetencia;
	}

	public void setCriteriosAvaliacaoCompetencia(
			Collection<CriterioAvaliacaoCompetencia> criteriosAvaliacaoCompetencia) {
		this.criteriosAvaliacaoCompetencia = criteriosAvaliacaoCompetencia;
	}
}