package com.fortes.rh.dao.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;

public interface CandidatoCurriculoDao extends GenericDao<CandidatoCurriculo>
{
	void removeCandidato(Candidato candidato) throws Exception;
}