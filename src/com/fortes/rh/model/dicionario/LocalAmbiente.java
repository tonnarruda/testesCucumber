package com.fortes.rh.model.dicionario;

import java.util.HashMap;

public enum LocalAmbiente {
	
	ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR(1, "Estabelecimento do próprio empregador"),
	ESTABELECIMENTO_DE_TERCEIROS(2, "Estabelecimento de terceiros");

	private Integer opcao;
	private String descricao;

	private LocalAmbiente(Integer opcao, String descricao){
		this.opcao = opcao;
		this.descricao = descricao;
	}

	public Integer getOpcao() {
		return opcao;
	}

	public void setOpcao(Integer opcao) {
		this.opcao = opcao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static HashMap<Integer, String> mapLocalAmbiente(){
		HashMap<Integer, String> mapLocaisAmb = new HashMap<Integer, String>();
		for (LocalAmbiente localAmb : values()) {
			mapLocaisAmb.put(localAmb.getOpcao(), localAmb.getDescricao());
		}
		return mapLocaisAmb;
	}
}