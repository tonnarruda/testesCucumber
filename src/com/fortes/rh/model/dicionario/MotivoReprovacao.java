package com.fortes.rh.model.dicionario;

public	enum MotivoReprovacao {

	NOTA("NOTA", "reprovado por nota"),
	FREQUENCIA("FREQUENCIA", "reprovado por falta"),
	NOTA_FREQUENCIA("NOTA_FREQUENCIA", "reprovado por nota e falta"),
	REPROVADO("REPROVADO", "reprovado");

	private String motivo;
	private String descricao;

	private MotivoReprovacao(String motivo, String descricao){
		this.motivo = motivo;
		this.descricao = descricao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}