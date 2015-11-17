package com.fortes.rh.test.factory.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public class ColaboradorCertificacaoFactory
{
	public static ColaboradorCertificacao getEntity()
	{
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setId(null);
		return colaboradorCertificacao;
	}

	public static ColaboradorCertificacao getEntity(Long id)
	{
		ColaboradorCertificacao colaboradorCertificacao = getEntity();
		colaboradorCertificacao.setId(id);

		return colaboradorCertificacao;
	}

	public static Collection<ColaboradorCertificacao> getCollection()
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(getEntity());

		return colaboradorCertificacaos;
	}
	
	public static Collection<ColaboradorCertificacao> getCollection(Long id)
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(getEntity(id));
		
		return colaboradorCertificacaos;
	}
}
