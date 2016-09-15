package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.acesso.Usuario;
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
}