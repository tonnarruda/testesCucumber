package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

public class DadosAmbienteOuFuncaoRisco 
{
	private Long ambienteOuFuncaoId;
	private Long riscoId;
	private String riscoDescricao;
	private String riscoTipo;
	private boolean epcEficaz;
	private Date historicoAmbienteOuFuncaoData;
	
	public DadosAmbienteOuFuncaoRisco(Long ambienteOuFuncaoId, Long riscoId, String riscoDescricao, String riscoTipo, boolean epcEficaz, Date historicoAmbienteOuFuncaoData) 
	{
		this.ambienteOuFuncaoId = ambienteOuFuncaoId;
		this.riscoId = riscoId;
		this.riscoDescricao = riscoDescricao;
		this.riscoTipo = riscoTipo;
		this.epcEficaz = epcEficaz;
		this.historicoAmbienteOuFuncaoData = historicoAmbienteOuFuncaoData;
	}

	public DadosAmbienteOuFuncaoRisco() {
	}

	public Long getAmbienteOuFuncaoId() {
		return ambienteOuFuncaoId;
	}

	public Long getRiscoId() {
		return riscoId;
	}

	public String getRiscoDescricao() {
		return riscoDescricao;
	}

	public String getRiscoTipo() {
		return riscoTipo;
	}

	public boolean isEpcEficaz() {
		return epcEficaz;
	}

	public Date getHistoricoAmbienteOuFuncaoData() {
		return historicoAmbienteOuFuncaoData;
	}
}
