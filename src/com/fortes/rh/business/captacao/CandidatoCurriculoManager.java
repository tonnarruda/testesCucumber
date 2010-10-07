package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;

public interface CandidatoCurriculoManager extends GenericManager<CandidatoCurriculo>
{
	void removeCandidato(Candidato candidato) throws Exception;
}