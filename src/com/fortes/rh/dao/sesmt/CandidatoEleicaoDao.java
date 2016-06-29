package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.CandidatoEleicao;

public interface CandidatoEleicaoDao extends GenericDao<CandidatoEleicao> 
{
	Collection<CandidatoEleicao> findByEleicao(Long eleicaoId, boolean somenteEleitos, boolean retornoAC);

	void setEleito(boolean eleito, Long[] ids);

	CandidatoEleicao findByIdProjection(Long id);

	void setQtdVotos(int qtd, Long id);

	void removeByEleicao(Long eleicaoId);

	Collection<CandidatoEleicao> findByColaborador(Long colaboradorId);
	
	public CandidatoEleicao findCandidatoEleicao(Long candidatoEleicaoId);
	
	public CandidatoEleicao findByColaboradorIdAndEleicaoId(Long colaboradorId, Long eleicaoId);
}