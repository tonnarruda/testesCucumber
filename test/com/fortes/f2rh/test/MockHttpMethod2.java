package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod2 extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		String json = "[{\"endereco\":\"Rua Coronel Linhares 115/202\",\"data_nascimento_rh\":\"27/12/1984\",\"cidade_rh\":\"Alta Floresta D`Oeste\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cep\":\"60170-240\",\"escolaridade_rh\":\"Superior Incompleto\",\"user\":{\"name\":null,\"login\":\"010.178.063-06\",\"email\":\"henriquesoares@grupofortes.com.br\"},\"id\":15,\"created_rh\":\"23/10/2009\",\"nacionalidade\":\"Brasileiro\",\"bairro\":\"Meireles\",\"curriculo_telefones\":[{\"ddd\":\"85\",\"numero\":\"8747-2023\"}],\"updated_rh\":\"25/10/2010\",\"estado_civil\":\"1\",\"estado\":\"RO\",\"curso\":\"Letras\",\"sexo\":\"M\",\"observacoes_complementares\":\"\",\"salario\":0.0,\"area_formacao\":\"2\"}]";
		return json;
	}
	
}
