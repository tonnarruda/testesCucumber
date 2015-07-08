package com.fortes.rh.model.dicionario;

public enum FiltroSituacaoCurso {
	
	TODOS('0', "Vencidos e a Vencer"),
	A_VENCER('1', "a Vencer"),
	VENCIDOS('2', "Vencidos");

	private char opcao;
	private String descricao;

	private FiltroSituacaoCurso(char opcao, String descricao){
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
