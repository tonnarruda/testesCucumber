package com.fortes.rh.test.model.geral;

import com.fortes.rh.model.geral.Endereco;

public class EnderecoFactory {

	
	public static Endereco getEntity(){
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("123456789012345678901");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		
		return endereco;
	}
	
}
