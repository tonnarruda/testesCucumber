package com.fortes.rh.model.sesmt;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="realizacaoexame_sequence", allocationSize=1)
public class RealizacaoExame extends AbstractModel implements Serializable
{
    @Temporal(DATE)
    private Date data;
    private String observacao;

    @OneToOne(mappedBy="realizacaoExame")
    private ExameSolicitacaoExame exameSolicitacaoExame;

    private String resultado;

    public RealizacaoExame()
    {
    }

    public  RealizacaoExame(Date realizacaoExameData, String exameNome, String colaboradorNomeComercial, String realizacaoExameResultado, Long realizacaoExameId)
	{
		this.setId(realizacaoExameId);
		this.data = realizacaoExameData;
		this.resultado = realizacaoExameResultado;

		this.exameSolicitacaoExame = new ExameSolicitacaoExame();
		this.exameSolicitacaoExame.setProjectionExameNome(exameNome);
	}

    public RealizacaoExame(Date data, String exameNome, String resultado, String observacao, String solicitacaoExameMotivo)
    {
    	this.exameSolicitacaoExame = new ExameSolicitacaoExame();
		this.exameSolicitacaoExame.setProjectionExameNome(exameNome);
    	this.data = data;
    	this.resultado = resultado;
    	this.observacao = observacao;
    	
    	this.exameSolicitacaoExame.setSolicitacaoExame(new SolicitacaoExame());
    	this.exameSolicitacaoExame.getSolicitacaoExame().setMotivo(solicitacaoExameMotivo);
    }

    public String getDataFormatada()
	{
		String dataFormatada = "-";
		if (data != null)
		{
			dataFormatada = DateUtil.formataDiaMesAno(data);
		}
		return dataFormatada;
	}

    public String getDataFormatadaRelatorio()
    {
    	String dataFormatada = "____/____/____";
    	if (data != null && !ResultadoExame.NAO_REALIZADO.toString().equals(resultado))
    	{
    		dataFormatada = DateUtil.formataDiaMesAno(data);
    	}
    	return dataFormatada;
    }

	public String getDescResultado()
	{
		if (resultado != null)
			return ResultadoExame.valueOf(resultado).getDescricao();
		return "";
	}
	public String getResultado()
	{
		return resultado;
	}
	public void setResultado(String resultado)
	{
		this.resultado = resultado;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public ExameSolicitacaoExame getExameSolicitacaoExame()
	{
		return exameSolicitacaoExame;
	}

	public void setExameSolicitacaoExame(ExameSolicitacaoExame exameSolicitacaoExame)
	{
		this.exameSolicitacaoExame = exameSolicitacaoExame;
	}
}