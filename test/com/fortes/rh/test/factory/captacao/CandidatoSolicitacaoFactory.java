package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.Apto;

public class CandidatoSolicitacaoFactory
{
	public static CandidatoSolicitacao getEntity()
	{
    	Solicitacao solicitacao = new Solicitacao();
    	solicitacao.setId(1L);

    	EtapaSeletiva etapaSeletiva = new EtapaSeletiva();
    	etapaSeletiva.setId(1L);

    	CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
    	candidatoSolicitacao.setId(1L);
    	candidatoSolicitacao.setSolicitacao(solicitacao);
    	candidatoSolicitacao.setApto(Apto.SIM);
    	candidatoSolicitacao.setEtapaSeletiva(etapaSeletiva);
    	candidatoSolicitacao.setColaboradorId(1L);
    	candidatoSolicitacao.setCandidato(CandidatoFactory.getCandidato());

    	return candidatoSolicitacao;
	}

	public static CandidatoSolicitacao getEntity(Long id, char status, Date dataContratacaoOrPromocao)
	{
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(id);
		candidatoSolicitacao.setStatus(status);
		candidatoSolicitacao.setDataContratacaoOrPromocao(dataContratacaoOrPromocao);
		return candidatoSolicitacao;
	}

	public static CandidatoSolicitacao getEntity(Long id)
	{

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setId(id);

		return candidatoSolicitacao;
	}
	
	public static CandidatoSolicitacao getEntity(Candidato candidato, Solicitacao solicitacao, Character statusAutorizacaoGestor)
	{
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao.setStatusAutorizacaoGestor(statusAutorizacaoGestor);

		return candidatoSolicitacao;
	}
	
	public static CandidatoSolicitacao getEntity(Candidato candidato, Solicitacao solicitacao, Date dataContrratacaoOrpromocao)
	{
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao.setDataContratacaoOrPromocao(dataContrratacaoOrpromocao);
		return candidatoSolicitacao;
	}
}