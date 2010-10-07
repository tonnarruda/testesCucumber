package com.fortes.rh.model.captacao.relatorio;

import java.util.Collection;

import com.fortes.rh.model.pesquisa.Pergunta;

public class PerguntaResposta
{
	private Pergunta pergunta;

	private Collection<RespostaRelatorio> respostas;
	private String comentarioRelatorio;

	public String getComentarioRelatorio()
	{
		return comentarioRelatorio;
	}

	public void setComentarioRelatorio(String comentarioRelatorio)
	{
		this.comentarioRelatorio = comentarioRelatorio;
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public Collection<RespostaRelatorio> getRespostas()
	{
		return respostas;
	}

	public void setRespostas(Collection<RespostaRelatorio> respostas)
	{
		this.respostas = respostas;
	}
}
