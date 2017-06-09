package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.Exame;

public class ExamesRealizadosRelatorio 
{
	private Long exameId;
	private Long clinicaId;
	private Exame exame;
	private String nome;
	private Character tipo;
	private String exameNome;
	private String clinicaNome;
	private String resultado;
	private String motivoSolicitacaoExame;
	private Date data;
	private Integer qtdExamesRealizados;
	private Long estabelecimentoId;
	private String estabelecimentoNome;
	private String solicitacaoExameObservacao;

	public ExamesRealizadosRelatorio() {
	}
	
	public ExamesRealizadosRelatorio(Long exameId, String nome, String tipoPessoa, String exameNome, Date realizacaoData, Long clinicaId, String clinicaNome, String exameResultado, String motivoSolicitacaoExame, Long estabelecimentoId, String estabelecimentoNome, String solicitacaoExameObservacao)
	{
		this.exameId = exameId;
		this.clinicaId = clinicaId;
		this.nome = nome;
		
		if(tipoPessoa != null)
			this.tipo = tipoPessoa.toCharArray()[0];
		
		this.exameNome = exameNome;
		this.clinicaNome = StringUtils.isEmpty(clinicaNome) ? "(Sem clínica definida)" : clinicaNome.trim();
		this.data = realizacaoData;
		this.motivoSolicitacaoExame = motivoSolicitacaoExame;
		this.estabelecimentoId = estabelecimentoId;
		this.estabelecimentoNome = StringUtils.isEmpty(estabelecimentoNome) ? "(Sem estabelecimento definido)" : estabelecimentoNome.trim();
		this.solicitacaoExameObservacao = solicitacaoExameObservacao;
		this.resultado = exameResultado == null ? ResultadoExame.NAO_REALIZADO.toString() : exameResultado;
	}

	public ExamesRealizadosRelatorio(Long exameId, String exameNome, Long clinicaId, String clinicaNome, Integer qtdExamesRealizados)
	{
		this.exameId = exameId;
		this.exameNome = exameNome;
		this.clinicaId = clinicaId;
		this.clinicaNome = StringUtils.isEmpty(clinicaNome) ? "(Sem clínica definida)" : clinicaNome.trim();
		this.qtdExamesRealizados = qtdExamesRealizados;
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

	public Integer getQtdExamesRealizados() {
		return qtdExamesRealizados;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Exame getExame() {
		return exame;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstabelecimentoNome() {
		return estabelecimentoNome;
	}

	public void setEstabelecimentoNome(String estabelecimentoNome) {
		this.estabelecimentoNome = estabelecimentoNome;
	}

	public Long getEstabelecimentoId() {
		return estabelecimentoId;
	}

	public void setEstabelecimentoId(Long estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public String getSolicitacaoExameObservacao() {
		return solicitacaoExameObservacao;
	}

	public void setSolicitacaoExameObservacao(String solicitacaoExameObservacao) {
		this.solicitacaoExameObservacao = solicitacaoExameObservacao;
	}
}
