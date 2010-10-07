package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.CandidatoEleicao;

public class CandidatoEleicaoFactory
{
	public static CandidatoEleicao getEntity()
	{
		CandidatoEleicao candidatoCandidatoEleicao = new CandidatoEleicao();

		return candidatoCandidatoEleicao;
	}

	public static CandidatoEleicao getEntity(Long id)
	{
		CandidatoEleicao candidatoCandidatoEleicao = getEntity();
		candidatoCandidatoEleicao.setId(id);
		
		return candidatoCandidatoEleicao;
	}
}
