package com.fortes.rh.test.factory.sesmt;

import java.util.Date;

import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;

public class SolicitacaoEpiFactory
{
	public static SolicitacaoEpi getEntity()
	{
		SolicitacaoEpi solicitacaoEpi = new SolicitacaoEpi();
		solicitacaoEpi.setData(new Date());
		solicitacaoEpi.setSituacaoSolicitacaoEpi(SituacaoSolicitacaoEpi.ABERTA);

		return solicitacaoEpi;
	}

	public static SolicitacaoEpi getEntity(Long id)
	{
		SolicitacaoEpi solicitacaoEpi = getEntity();
		solicitacaoEpi.setId(id);

		return solicitacaoEpi;
	}
}
