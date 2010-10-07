package com.fortes.rh.model.pesquisa.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;

public class QuestionarioRelatorio implements Serializable
{
	private static final long serialVersionUID = 1449150353781722413L;
	
	private Avaliacao avaliacaoExperiencia;
	private Questionario questionario;
	private Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}
	public void setPerguntas(Collection<Pergunta> perguntas)
	{
		this.perguntas = perguntas;
	}
	
	public Questionario getQuestionario()
	{
		return questionario;
	}
	
	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}
	public Avaliacao getAvaliacaoExperiencia() {
		return avaliacaoExperiencia;
	}
	public void setAvaliacaoExperiencia(Avaliacao avaliacaoExperiencia) {
		this.avaliacaoExperiencia = avaliacaoExperiencia;
	}
}