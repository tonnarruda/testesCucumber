package com.fortes.rh.model.json;

public class EmpresaJson {
	private String id;
	private String nome;
	private String baseCnpj;
	
	public EmpresaJson(Long id, String nome, String baseCnpj) {
		this.id = String.valueOf(id);
		this.nome = nome;
		this.baseCnpj = baseCnpj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBaseCnpj() {
		return baseCnpj;
	}

	public void setBaseCnpj(String baseCnpj) {
		this.baseCnpj = baseCnpj;
	}
}
