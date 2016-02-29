package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.dicionario.Apto;

public class HistoricoCandidatoFactory
{

	public static HistoricoCandidato getEntity()
	{
		HistoricoCandidato entity = new HistoricoCandidato();
		entity.setId(1L);
		entity.setApto(Apto.INDIFERENTE);

		return entity;
	}

	public static HistoricoCandidato getEntity(EtapaSeletiva etapaSeletiva,Date data, CandidatoSolicitacao candidatoSolicitacao) 
	{
		HistoricoCandidato entity = new HistoricoCandidato();
		entity.setApto(Apto.INDIFERENTE);
		entity.setEtapaSeletiva(etapaSeletiva);
		entity.setData(data);
		entity.setCandidatoSolicitacao(candidatoSolicitacao);
		
		return entity;
	}

}
