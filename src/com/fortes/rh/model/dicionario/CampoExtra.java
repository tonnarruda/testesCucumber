package com.fortes.rh.model.dicionario;


public enum CampoExtra
{

	CAMPO_DE_TEXTO_1("Campo de Texto 1"),
	CAMPO_DE_TEXTO_2("Campo de Texto 2"),
	CAMPO_DE_TEXTO_3("Campo de Texto 3"),
	CAMPO_DE_TEXTO_4("Campo de Texto 4"),
	CAMPO_DE_TEXTO_5("Campo de Texto 5"),
	CAMPO_DE_TEXTO_6("Campo de Texto 6"),
	CAMPO_DE_TEXTO_7("Campo de Texto 7"),
	CAMPO_DE_TEXTO_8("Campo de Texto 8"),
	CAMPO_DE_DATA_1("Campo de Data 1"),
	CAMPO_DE_DATA_2("Campo de Data 2"),
	CAMPO_DE_DATA_3("Campo de Data 3"),
	CAMPO_DE_VALOR_1("Campo de Valor 1"),
	CAMPO_DE_VALOR_2("Campo de Valor 2"),
	CAMPO_DE_NUMERO("Campo de Numero"),
	CAMPO_DE_TEXTO_LONGO_1("Campo de Texto Longo 1"),
	CAMPO_DE_TEXTO_LONGO_2("Campo de Texto Longo 2");
	
	CampoExtra(String descricao)
	{
		this.descricao = descricao;
	}

	private String descricao;
	
	public String getDescricao() 
	{
		return descricao;
	}
}