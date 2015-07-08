package com.fortes.rh.model.dicionario;

public enum FiltroAgrupamentoCursoColaborador {
	
	CURSOS('0', "Cursos"),
	COLABORADORES('1', "Colaboradores");

	private char opcao;
	private String descricao;

	private FiltroAgrupamentoCursoColaborador(char opcao, String descricao){
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
