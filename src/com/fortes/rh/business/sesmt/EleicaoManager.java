package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;

public interface EleicaoManager extends GenericManager<Eleicao>
{
	Collection<Eleicao> findAllSelect(Long empresaId);
	void updateVotos(Eleicao eleicao);
	Eleicao findByIdProjection(Long id);
	Eleicao findImprimirResultado(Long eleicaoId) throws ColecaoVaziaException;
	void removeCascade(Long id)throws Exception;
	Collection<CandidatoEleicao> setCandidatosGrafico(Eleicao eleicao);
	Eleicao montaAtaDaEleicao(Long eleicaoId) throws ColecaoVaziaException;
	Collection<ParticipacaoColaboradorCipa> getParticipacoesDeColaboradorEmEleicoes(Long colaboradorId);
}