package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		String json = "[{\"ddd_rh\":\"85\",\"endereco\":\"Rua Coronel Linhares 115/202\",\"cidade_rh\":\"Fortaleza\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cep\":\"60170-240\",\"escolaridade_rh\":\"Superior Incompleto\",\"id\":15,\"user\":{\"name\":null,\"login\":\"010.178.063-06\",\"email\":\"henriquesoares@grupofortes.com.br\"},\"nacionalidade\":\"Brasileiro\",\"data_nascimento_rh\":\"27/12/1984\",\"bairro\":\"Meireles\",\"estado_civil\":\"1\",\"estado\":\"CE\",\"curso\":\"Letras\",\"telefone_rh\":\"87472023\",\"sexo\":\"M\",\"observacoes_complementares\":\"\",\"salario\":0.0,\"area_formacao\":\"2\"}]";
		return json;
	}
	
}
