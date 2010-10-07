package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.NivelIdioma;

@Entity
@SequenceGenerator(name="sequence", sequenceName="candidatoidioma_sequence", allocationSize=1)
public class CandidatoIdioma extends AbstractModel implements Serializable
{
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private Idioma idioma;
	private char nivel;


	public void setCandidatoId(Long candidatoId)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setId(candidatoId);
	}

	public void setProjectionIdiomaNome(String projectionIdiomaNome)
	{
		if (idioma == null)
			idioma = new Idioma();

		idioma.setNome(projectionIdiomaNome);
	}

	public void setProjectionIdiomaId(Long projectionIdiomaId)
	{
		if (idioma == null)
			idioma = new Idioma();

		idioma.setId(projectionIdiomaId);
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public Idioma getIdioma()
	{
		return idioma;
	}

	public void setIdioma(Idioma idioma)
	{
		this.idioma = idioma;
	}

	public char getNivel()
	{
		return nivel;
	}

	public void setNivel(char nivel)
	{
		this.nivel = nivel;
	}

	public String getNivelDescricao()
	{
		NivelIdioma nivelDesc = new NivelIdioma();

		return (String) nivelDesc.get(((Character)this.nivel));
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId()).append("idioma", this.idioma).append(
						"nivel", this.nivel)
				.append("candidato", this.candidato).toString();
	}
}