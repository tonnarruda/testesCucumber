package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="candidatocurriculo_sequence", allocationSize=1)
public class CandidatoCurriculo extends AbstractModel implements Serializable
{
	@ManyToOne
	private Candidato candidato;
	
	private String curriculo;

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public String getCurriculo()
	{
		return curriculo;
	}

	public void setCurriculo(String curriculo)
	{
		this.curriculo = curriculo;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId()).append("candidato", this.candidato)
		.append("curriculo", this.curriculo).toString();
	}
}