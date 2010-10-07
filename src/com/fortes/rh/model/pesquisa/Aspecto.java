package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="aspecto_sequence", allocationSize=1)
public class Aspecto extends AbstractModel implements Serializable, Cloneable
{
    @Column(length=100)
    private String nome;
    @ManyToOne
    private Questionario questionario;
    
    @ManyToOne
    private Avaliacao avaliacao;

    @Transient
    private Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

    public void setProjectionQuestionarioId(Long questionarioId)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();
    	this.questionario.setId(questionarioId);
    }
    
    public void setProjectionAvaliacaoId(Long avaliacaoId)
    {
    	if(this.avaliacao == null)
    		this.avaliacao = new Avaliacao();
    	
    	this.avaliacao.setId(avaliacaoId);
    }

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}

	public void setPerguntas(Collection<Pergunta> perguntas)
	{
		this.perguntas = perguntas;
	}

	@Override
	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto.");
	   }
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
}