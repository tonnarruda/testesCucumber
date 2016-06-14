package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.OrdemDeServico;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="ordemDeServico_sequence", allocationSize=1)
public class OrdemDeServico extends AbstractModel implements Serializable
{
	@ManyToOne(fetch=FetchType.LAZY)
	private Colaborador colaborador;
	
	private String nomeColaborador;
	
	private Date dataAdmisaoColaborador;
	
	private String codigoCBO;
	
	private String nomeFuncao;
	
	private String nomeEmpresa;
	
	@Temporal(TemporalType.TIMESTAMP)
	
	private Date data;
	
	private Double revisao; 
	
	@Lob
	private String atividades;
	@Lob
	private String riscoDaOperacao;
	@Lob
	private String epis;
	@Lob
	private String medidasDePrevencao;
	@Lob
	private String treinamentos;
	@Lob
	private String normasInternas;
	@Lob
	private String procedimentoEmCasoDeAcidente;
	@Lob
	private String termoDeResponsabilidade;
	
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public String getNomeColaborador() {
		return nomeColaborador;
	}
	
	public void setNomeColaborador(String nomeColaborador) {
		this.nomeColaborador = nomeColaborador;
	}
	
	public Date getDataAdmisaoColaborador() {
		return dataAdmisaoColaborador;
	}
	
	public void setDataAdmisaoColaborador(Date dataAdmisaoColaborador) {
		this.dataAdmisaoColaborador = dataAdmisaoColaborador;
	}
	
	public String getCodigoCBO() {
		return codigoCBO;
	}
	
	public void setCodigoCBO(String codigoCBO) {
		this.codigoCBO = codigoCBO;
	}
	
	public String getNomeFuncao() {
		return nomeFuncao;
	}
	
	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatada() {
		if(this.data != null){
			SimpleDateFormat formatDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			return formatDataHora.format(data);
		}
		return "";
	}
	
	public Double getRevisao() {
		return revisao;
	}
	
	public void setRevisao(Double revisao) {
		this.revisao = revisao;
	}
	
	public String getAtividades() {
		return atividades;
	}
	
	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}
	
	public String getRiscoDaOperacao() {
		return riscoDaOperacao;
	}
	
	public void setRiscoDaOperacao(String riscoDaOperacao) {
		this.riscoDaOperacao = riscoDaOperacao;
	}
	
	public String getEpis() {
		return epis;
	}
	
	public void setEpis(String epis) {
		this.epis = epis;
	}
	
	public String getMedidasDePrevencao() {
		return medidasDePrevencao;
	}
	
	public void setMedidasDePrevencao(String medidasDePrevencao) {
		this.medidasDePrevencao = medidasDePrevencao;
	}
	
	public String getTreinamentos() {
		return treinamentos;
	}
	
	public void setTreinamentos(String treinamentos) {
		this.treinamentos = treinamentos;
	}
	
	public String getNormasInternas() {
		return normasInternas;
	}
	
	public void setNormasInternas(String normasInternas) {
		this.normasInternas = normasInternas;
	}
	
	public String getProcedimentoEmCasoDeAcidente() {
		return procedimentoEmCasoDeAcidente;
	}
	
	public void setProcedimentoEmCasoDeAcidente(String procedimentoEmCasoDeAcidente) {
		this.procedimentoEmCasoDeAcidente = procedimentoEmCasoDeAcidente;
	}
	
	public String getTermoDeResponsabilidade() {
		return termoDeResponsabilidade;
	}
	
	public void setTermoDeResponsabilidade(String termoDeResponsabilidade) {
		this.termoDeResponsabilidade = termoDeResponsabilidade;
	}
}
