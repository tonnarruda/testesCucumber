package com.fortes.rh.model.dicionario;

public enum FiltroControleVencimentoCertificacao {

	CURSO(1, "Periodicidade do curso"),
	CERTIFICACOES(2, "Periodicidade da certificação");

	private int opcao;
	private String descricao;

	private FiltroControleVencimentoCertificacao(int opcao, String descricao){
		this.opcao = opcao;
		this.descricao = descricao;
	}

	public int getOpcao() {
		return opcao;
	}

	public void setOpcao(int opcao) {
		this.opcao = opcao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
