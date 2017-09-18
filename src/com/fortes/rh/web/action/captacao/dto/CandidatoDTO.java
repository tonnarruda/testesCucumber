package com.fortes.rh.web.action.captacao.dto;

import java.util.Date;

public class CandidatoDTO {
	
	private String nomeBusca;
	private String cpfBusca;
	private String dddFoneFixo;
	private String foneFixo;
	private String dddCelular;
	private String foneCelular;
	private String indicadoPor;
	private char visualizar;
	private Date dataIni;
	private Date dataFim;
	private String observacaoRH;
	private boolean exibeContratados;
	private boolean exibeExterno;
	
	public String getNomeBusca() {
		return nomeBusca;
	}
	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}
	public String getCpfBusca() {
		return cpfBusca;
	}
	public void setCpfBusca(String cpfBusca) {
		this.cpfBusca = cpfBusca;
	}
	public String getDddFoneFixo() {
		return dddFoneFixo;
	}
	public void setDddFoneFixo(String dddFoneFixo) {
		this.dddFoneFixo = dddFoneFixo;
	}
	public String getFoneFixo() {
		return foneFixo;
	}
	public void setFoneFixo(String foneFixo) {
		this.foneFixo = foneFixo;
	}
	public String getDddCelular() {
		return dddCelular;
	}
	public void setDddCelular(String dddCelular) {
		this.dddCelular = dddCelular;
	}
	public String getFoneCelular() {
		return foneCelular;
	}
	public void setFoneCelular(String foneCelular) {
		this.foneCelular = foneCelular;
	}
	public String getIndicadoPor() {
		return indicadoPor;
	}
	public void setIndicadoPor(String indicadoPor) {
		this.indicadoPor = indicadoPor;
	}
	public char getVisualizar() {
		return visualizar;
	}
	public void setVisualizar(char visualizar) {
		this.visualizar = visualizar;
	}
	public Date getDataIni() {
		return dataIni;
	}
	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getObservacaoRH() {
		return observacaoRH;
	}
	public void setObservacaoRH(String observacaoRH) {
		this.observacaoRH = observacaoRH;
	}
	public boolean isExibeContratados() {
		return exibeContratados;
	}
	public void setExibeContratados(boolean exibeContratados) {
		this.exibeContratados = exibeContratados;
	}
	public boolean isExibeExterno() {
		return exibeExterno;
	}
	public void setExibeExterno(boolean exibeExterno) {
		this.exibeExterno = exibeExterno;
	}
}