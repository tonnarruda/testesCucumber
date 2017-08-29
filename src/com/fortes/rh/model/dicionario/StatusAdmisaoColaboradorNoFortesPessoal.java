package com.fortes.rh.model.dicionario;

public enum StatusAdmisaoColaboradorNoFortesPessoal {
	
	NA_TABELA_TEMPORARIA(1),
	EM_ADMISSAO(2),
	EMPREGADO(3);

	private Integer opcao;

	private StatusAdmisaoColaboradorNoFortesPessoal(Integer opcao){
		this.opcao = opcao;
	}

	public Integer getOpcao() {
		return opcao;
	}

	public void setOpcao(Integer opcao) {
		this.opcao = opcao;
	}
}
