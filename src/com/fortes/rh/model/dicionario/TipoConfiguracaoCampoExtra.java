package com.fortes.rh.model.dicionario;

public enum TipoConfiguracaoCampoExtra {
	
	CANDIDATO_EXTERNO("candidatoExterno"),
	CANDIDATO("candidato"),
	COLABORADOR("colaborador");

	private String tipo;

	private TipoConfiguracaoCampoExtra(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
