package com.fortes.rh.model.geral.relatorio;

import java.util.Date;

import com.fortes.rh.model.geral.Ocorrencia;

public class OcorrenciaRelatorio
{
	private Ocorrencia ocorrencia;
    private Date dataIni;
    private Date dataFim;
    private String observacao;

	public Date getDataFim()
	{
		return dataFim;
	}
	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}
	public Date getDataIni()
	{
		return dataIni;
	}
	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}
	public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}
	public Ocorrencia getOcorrencia()
	{
		return ocorrencia;
	}
	public void setOcorrencia(Ocorrencia ocorrencia)
	{
		this.ocorrencia = ocorrencia;
	}
}
