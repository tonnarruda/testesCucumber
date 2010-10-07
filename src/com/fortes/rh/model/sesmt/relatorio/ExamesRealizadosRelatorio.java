package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.ResultadoExame;

public class ExamesRealizadosRelatorio 
{
	private Long exameId;
	private Long clinicaId;
	private String colaboradorNome;
	private String candidatoNome;
	private String exameNome;
	private String clinicaNome;
	private String resultado;
	private String motivoSolicitacaoExame;
	private Date data;

	public ExamesRealizadosRelatorio() {
	}
	
	public ExamesRealizadosRelatorio(Long exameId, String colaboradorNome, String candidatoNome, String exameNome, Date realizacaoData, Long clinicaId, String clinicaNome, String exameResultado, String motivoSolicitacaoExame)
	{
		this.exameId = exameId;
		this.clinicaId = clinicaId;
		this.colaboradorNome = colaboradorNome;
		this.candidatoNome = candidatoNome;
		this.exameNome = exameNome;
		this.clinicaNome = clinicaNome;
		this.data = realizacaoData;
		this.motivoSolicitacaoExame = motivoSolicitacaoExame;
		if(exameResultado == null)
			this.resultado = ResultadoExame.NAO_REALIZADO.toString();
		else
			this.resultado = exameResultado;
	}
	
	public String getNome()
	{
		if (StringUtils.isNotBlank(colaboradorNome))
			return colaboradorNome;
		else
			return candidatoNome;
	}
	
	public String getResultado() {
		return StringUtils.isNotBlank(resultado) ? ResultadoExame.valueOf(resultado).getDescricao() : "";
	}
	
	public String getClinicaNome() {
		return StringUtils.isNotBlank(clinicaNome)  ? clinicaNome : "";
	}
	
	public String getExameNome() {
		return exameNome;
	}

	public Date getData() {
		return data;
	}

	public Long getExameId() {
		return exameId;
	}

	public Long getClinicaId() {
		return clinicaId;
	}

	public String getMotivoSolicitacaoExame() {
		return motivoSolicitacaoExame;
	}

}
