package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.Certificacao;

public class CertificacaoFactory
{
	public static Certificacao getEntity()
	{
		Certificacao certificacao = new Certificacao();
		certificacao.setId(null);
		certificacao.setNome("Teste");
		certificacao.setPeriodicidade(1);
		return certificacao;
	}

	public static Certificacao getEntity(long id)
	{
		Certificacao certificacao = getEntity();
		certificacao.setId(id);
		return certificacao;
	}
}
