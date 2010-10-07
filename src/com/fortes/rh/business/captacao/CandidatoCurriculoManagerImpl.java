package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.CandidatoCurriculoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;

public class CandidatoCurriculoManagerImpl extends GenericManagerImpl<CandidatoCurriculo, CandidatoCurriculoDao> implements CandidatoCurriculoManager
{
	public void removeCandidato(Candidato candidato) throws Exception
	{
		getDao().removeCandidato(candidato);
	}


}