package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="criterioavaliacaocompetencia_sequence", allocationSize=1)
public class CriterioAvaliacaoCompetencia extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;
	
	@ManyToOne
	private Habilidade habilidade;
	
	@ManyToOne
	private Conhecimento conhecimento;
	
	@ManyToOne
	private Atitude atitude;

	public CriterioAvaliacaoCompetencia()
	{
	}

	public CriterioAvaliacaoCompetencia(Long id, String descricao)
	{
		this.setId(id);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Habilidade getHabilidade() {
		return habilidade;
	}

	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}

	public Conhecimento getConhecimento() {
		return conhecimento;
	}

	public void setConhecimento(Conhecimento conhecimento) {
		this.conhecimento = conhecimento;
	}

	public Atitude getAtitude() {
		return atitude;
	}

	public void setAtitude(Atitude atitude) {
		this.atitude = atitude;
	}
}