package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.EmpresaBds;

public class EmpresaBdsFactory
{
	public static EmpresaBds getEntity()
	{
		EmpresaBds empresaBds = new EmpresaBds();
		empresaBds.setContato("contato");
		empresaBds.setEmail("mail@mail.com.br");
		empresaBds.setFone("33333333");
		empresaBds.setId(null);
		empresaBds.setNome("nome emp");
		empresaBds.setDdd("085");
		return empresaBds;
	}
}
