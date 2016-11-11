package com.fortes.rh.model.pesquisa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="resposta_sequence", allocationSize=1)
public class Resposta extends AbstractModel implements Serializable, Cloneable
{
    @ManyToOne
    private Pergunta pergunta;
    
    private String texto;
    private int ordem;

    private Integer peso; // peso avaliação do período de experiência
    
    //para o grafico do resultado do questionario
    @Transient
    private Integer qtdRespostas;

	@Transient
    private String legenda;
    
	public void setProjectionPerguntaId(Long perguntaId)
	{
		if (this.pergunta == null)
			this.pergunta = new Pergunta();

		this.pergunta.setId(perguntaId);
	}

	public String getLetraByOrdem()
	{
		return StringUtil.letraByNumero(this.ordem);
	}
    public int getOrdem()
	{
		return ordem;
	}
	public void setOrdem(int ordem)
	{
		this.ordem = ordem;
	}
    public String getTexto()
	{
		return texto;
	}
	public void setTexto(String texto)
	{
		this.texto = texto;
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", super.getId()).append("ordem", this.ordem).append(
						"pergunta", this.pergunta).append("texto", this.texto).toString();
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public Integer getQtdRespostas()
	{
		return qtdRespostas == null ? 0 : qtdRespostas;
	}

	public void setQtdRespostas(Integer qtdRespostas)
	{
		this.qtdRespostas = qtdRespostas;
	}

	public String getLegenda()
	{
		return legenda;
	}

	public void setLegenda(String legenda)
	{
		this.legenda = legenda;
	}

	/**
	 * Peso usado para Avaliação de Período de Experiência
	 * @return
	 */
	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
}