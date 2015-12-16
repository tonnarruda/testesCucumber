package com.fortes.rh.model.dicionario;

public enum FiltroSituacaoAvaliacao {
	TODAS('T', "Todas"),
	RESPONDIDA('R', "Respondidas"),
	RESPONDIDA_PARCIALMENTE('P', "Respondidas Parcialmente"),
	NAO_RESPONDIDA('N', "Não Responidas");
	
	private char opcao;
	private String descricao;

	private FiltroSituacaoAvaliacao(char opcao, String descricao){
		this.opcao = opcao;
		this.descricao = descricao;
	}

	public char getOpcao() {
		return opcao;
	}

	public void setOpcao(char opcao) {
		this.opcao = opcao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
