package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.relatorio.LinhaCedulaEleitoralRelatorio;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;

public interface CandidatoEleicaoManager extends GenericManager<CandidatoEleicao>
{
	Collection<CandidatoEleicao> findByEleicao(Long eleicaoId);

	void save(String[] candidatosCheck, Eleicao eleicao)throws Exception;

	Collection<CandidatoEleicao> getColaboradoresByEleicao(Long eleicaoId, Long empresaId)throws Exception;

	void saveVotosEleicao(String[] eleitosIds, String[] qtdVotos, String[] idCandidatoEleicaos, Eleicao eleicao)throws Exception;

	Collection<LinhaCedulaEleitoralRelatorio> montaCedulas(Collection<CandidatoEleicao> candidatoEleicaos)throws Exception;

	void removeByEleicao(Long eleicaoId);
	
	public Collection<CandidatoEleicao> findByColaborador(Long colaboradorId);
}