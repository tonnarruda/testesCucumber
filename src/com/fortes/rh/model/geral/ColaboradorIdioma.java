package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.dicionario.NivelIdioma;

@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradoridioma_sequence", allocationSize=1)
public class ColaboradorIdioma extends AbstractModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne
	private Idioma idioma;
	private char nivel;

	public void setIdiomaNome(String idiomaNome)
	{
		if (this.idioma == null)
			this.idioma = new Idioma();
		this.idioma.setNome(idiomaNome);
	}
	
	public void setIdiomaId(Long idiomaId)
	{
		if (this.idioma == null)
			this.idioma = new Idioma();
		
		this.idioma.setId(idiomaId);
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Idioma getIdioma()
	{
		return idioma;
	}
	public void setIdioma(Idioma idioma)
	{
		this.idioma = idioma;
	}
	
	public String getNivelDescricao()
	{
		if (nivel != ' ')
			return new NivelIdioma().get(nivel).toString();
		else
			return "";
	}
	
	public char getNivel()
	{
		return nivel;
	}
	public void setNivel(char nivel)
	{
		this.nivel = nivel;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId()).append("colaborador", this.colaborador)
				.append("idioma", this.idioma).append("nivel", this.nivel)
				.toString();
	}

}