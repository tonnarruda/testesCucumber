package com.fortes.rh.model.json;

import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TurmaJson {
	private String id;
	private String nomeCurso;
	private String descricaoTurma;
	private String dataInicio;
	private String dataFim;
	private String qtdParticipantesPrevistos;
	private String realizada;
	private EmpresaJson empresa;
	
	public TurmaJson(){
		
	}
	
	public TurmaJson(Long id, String nomeCurso, String descricaoTurma, Date dataInicio, Date dataFim, boolean realizada, Integer qtdParticipantesPrevistos, 
			Long empresaId, String empresaNome, String empresaBaseCnpj) {
		this.id = String.valueOf(id);
		this.nomeCurso = nomeCurso;
		this.descricaoTurma = descricaoTurma;
		this.dataInicio = DateUtil.formataDiaMesAno(dataInicio);
		this.dataFim = DateUtil.formataDiaMesAno(dataFim);
		this.qtdParticipantesPrevistos = String.valueOf(qtdParticipantesPrevistos);
		this.realizada = realizada ? "Sim" : "Não";
		this.empresa = new EmpresaJson(empresaId, empresaNome, empresaBaseCnpj);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNomeCurso() {
		return nomeCurso;
	}
	
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	public String getDescricaoTurma() {
		return descricaoTurma;
	}
	
	public void setDescricaoTurma(String descricaoTurma) {
		this.descricaoTurma = descricaoTurma;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public String getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getQtdParticipantesPrevistos() {
		return qtdParticipantesPrevistos;
	}
	
	public void setQtdParticipantesPrevistos(String qtdParticipantesPrevistos) {
		this.qtdParticipantesPrevistos = qtdParticipantesPrevistos;
	}

	public String getRealizada() {
		return realizada;
	}

	public void setRealizada(String realizada) {
		this.realizada = realizada;
	}

	public EmpresaJson getEmpresa() {
		return empresa;
	}

	public void setEmpresaJson(EmpresaJson empresa) {
		this.empresa = empresa;
	}
}