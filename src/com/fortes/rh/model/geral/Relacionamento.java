package com.fortes.rh.model.geral;

public class Relacionamento {

	private Long tabelaId;
	private Long relacionamentoId;
	
	public Relacionamento(Long id, Long relacionamentoId) 
	{
		super();
		this.tabelaId = id;
		this.relacionamentoId = relacionamentoId;
	}
	public Long getRelacionamentoId() {
		return relacionamentoId;
	}
	public void setRelacionamentoId(Long relacionamentoId) {
		this.relacionamentoId = relacionamentoId;
	}
	public Long getTabelaId() {
		return tabelaId;
	}
	public void setTabelaId(Long tabelaId) {
		this.tabelaId = tabelaId;
	}
}
