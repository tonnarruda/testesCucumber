package com.fortes.rh.test.factory.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

public class ColaboradorAvaliacaoPraticaFactory
{
	public static ColaboradorAvaliacaoPratica getEntity()
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
		colaboradorAvaliacaoPratica.setId(null);
		return colaboradorAvaliacaoPratica;
	}

	public static ColaboradorAvaliacaoPratica getEntity(Long id)
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = getEntity();
		colaboradorAvaliacaoPratica.setId(id);

		return colaboradorAvaliacaoPratica;
	}
	
	public static ColaboradorAvaliacaoPratica getEntity(ColaboradorCertificacao colaboradorCertificacao, Colaborador colaborador, Certificacao certificacao, AvaliacaoPratica avaliacaoPratica, Double nota)
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = getEntity();
		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		colaboradorAvaliacaoPratica.setNota(90.0);
		colaboradorAvaliacaoPratica.setData(DateUtil.criarDataMesAno(1, 10, 2015));

		return colaboradorAvaliacaoPratica;
	}

	public static Collection<ColaboradorAvaliacaoPratica> getCollection()
	{
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(getEntity());

		return colaboradorAvaliacaoPraticas;
	}
	
	public static Collection<ColaboradorAvaliacaoPratica> getCollection(Long id)
	{
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(getEntity(id));
		
		return colaboradorAvaliacaoPraticas;
	}
}
