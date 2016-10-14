package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.geral.Contato;

public class ContatoFactory {
	public static Contato getEntity()
	{
		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");

		return contato;
	}
	

}
