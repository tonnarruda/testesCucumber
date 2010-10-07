package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;

public interface EleicaoDao extends GenericDao<Eleicao>
{
	Collection<Eleicao> findAllSelect(Long empresaId);
	void updateVotos(Eleicao eleicao);
	Eleicao findByIdProjection(Long id);
	Collection<CandidatoEleicao> findImprimirResultado(Long eleicaoId);
}