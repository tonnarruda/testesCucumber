package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;

public class CandidatoEleicaoFactory
{
	public static CandidatoEleicao getEntity()
	{
		CandidatoEleicao candidatoEleicao = new CandidatoEleicao();

		return candidatoEleicao;
	}

	public static CandidatoEleicao getEntity(Long id)
	{
		CandidatoEleicao candidatoEleicao = getEntity();
		candidatoEleicao.setId(id);
		
		return candidatoEleicao;
	}

	public static CandidatoEleicao getEntity(int qtdVoto, Eleicao eleicao)
	{
		CandidatoEleicao candidatoEleicao = getEntity();
		candidatoEleicao.setQtdVoto(qtdVoto);
		candidatoEleicao.setEleicao(eleicao);
		
		return candidatoEleicao;
	}
}
