package com.fortes.rh.model.pesquisa.relatorio;

import java.io.Serializable;

public class QuestionarioResultadoPerguntaObjetiva implements Serializable
{
	private Long respostaId;
	private Integer qtdRespostas;
	
	private String qtdPercentualRespostas;
	
	public String getQtdPercentualRespostas()
	{
		return qtdPercentualRespostas;
	}
	public void setQtdPercentualRespostas(String qtdPercentualRespostas)
	{
		this.qtdPercentualRespostas = qtdPercentualRespostas;
	}
	public Integer getQtdRespostas()
	{
		return qtdRespostas;
	}
	public void setQtdRespostas(Integer qtdRespostas)
	{
		this.qtdRespostas = qtdRespostas;
	}
	public Long getRespostaId()
	{
		return respostaId;
	}
	public void setRespostaId(Long respostaId)
	{
		this.respostaId = respostaId;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof QuestionarioResultadoPerguntaObjetiva))
			return false;

		return ((QuestionarioResultadoPerguntaObjetiva) object).getRespostaId().equals(this.getRespostaId());
	}

	public int hashCode()
	{
	   return (this.getRespostaId() != null ? this.getRespostaId().hashCode() : 0);
	}
}