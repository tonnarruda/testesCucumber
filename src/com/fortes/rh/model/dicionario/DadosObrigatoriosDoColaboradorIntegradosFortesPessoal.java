package com.fortes.rh.model.dicionario;

public enum DadosObrigatoriosDoColaboradorIntegradosFortesPessoal {
	
	NOME("nome"),
	NOME_COMERCIAL("nomeComercial"),
	NASCIMENTO("nascimento"),
	SEXO("sexo"),
	CPF("cpf"),
	ESCOLARIDADE("escolaridade"),
	ENDE("ende"),
	NUM("num"),
	CIDADE("cidade"),
	EMAIL("email"),
	FONE("fone"),
	CELULAR("celular"),
	ESTADO_CIVIL("estadoCivil"),
	NOME_CONJUGE("nomeConjuge"),
	NOME_PAI("nomePai"),
	NOME_MAE("nomeMae"),
	DEFICIENCIA("deficiencia"),
	IDENTIDADE("identidade"),
	CARTEIRA_HABILITACAO("carteiraHabilitacao"),
	CERTIFICADO_MILITAR("certificadoMilitar"),
	CTPS("ctps");
	

	private String opcao;

	private DadosObrigatoriosDoColaboradorIntegradosFortesPessoal(String opcao){
		this.opcao = opcao;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}
}


//"nome,nomeComercial,nascimento,sexo,cpf,escolaridade,ende,num,cidade,email,fone,celular,estadoCivil,qtdFilhos,nomeConjuge,nomePai,nomeMae,deficiencia,matricula,dt_admissao,vinculo,
//dt_encerramentoContrato,regimeRevezamento,identidade,carteiraHabilitacao,tituloEleitoral,certificadoMilitar,ctps"

//update parametrosdosistema set camposcolaboradorvisivel = 'nome,nomeComercial,nascimento,sexo,cpf,escolaridade,endereco,email,fone,celular,estadoCivil,qtdFilhos,nomeConjuge,nomePai,nomeMae,deficiencia,matricula,dt_admissao,vinculo,dt_encerramentoContrato,regimeRevezamento,formacao,idioma,desCursos,expProfissional,infoAdicionais,identidade,carteiraHabilitacao,tituloEleitoral,certificadoMilitar,ctps,pis,modelosAvaliacao'; --.go
//update parametrosdosistema set camposcolaboradorobrigatorio = 'nome,nomeComercial,nascimento,cpf,escolaridade,ende,num,cidade,email,fone,dt_admissao'; --.go
