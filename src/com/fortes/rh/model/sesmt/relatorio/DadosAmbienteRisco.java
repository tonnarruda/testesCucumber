package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

public class DadosAmbienteRisco 
{
	private Long ambienteId;
	private Long riscoId;
	private String riscoDescricao;
	private String riscoTipo;
	private boolean epcEficaz;
	private Date historicoAmbienteData;
	
	public DadosAmbienteRisco(Long ambienteId, Long riscoId,
			String riscoDescricao, String riscoTipo, boolean epcEficaz,
			Date historicoAmbienteData) 
	{
		this.ambienteId = ambienteId;
		this.riscoId = riscoId;
		this.riscoDescricao = riscoDescricao;
		this.riscoTipo = riscoTipo;
		this.epcEficaz = epcEficaz;
		this.historicoAmbienteData = historicoAmbienteData;
	}

	public DadosAmbienteRisco() {
		// TODO Auto-generated constructor stub
	}

	public Long getAmbienteId() {
		return ambienteId;
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

	public Date getHistoricoAmbienteData() {
		return historicoAmbienteData;
	}
}
