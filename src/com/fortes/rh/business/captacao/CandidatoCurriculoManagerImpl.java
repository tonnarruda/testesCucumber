package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.CandidatoCurriculoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;

@Component
public class CandidatoCurriculoManagerImpl extends GenericManagerImpl<CandidatoCurriculo, CandidatoCurriculoDao> implements CandidatoCurriculoManager
{
	
	@Autowired
	CandidatoCurriculoManagerImpl(CandidatoCurriculoDao candidatoCurriculoDao) {
		setDao(candidatoCurriculoDao);
	}
	
	public void removeCandidato(Candidato candidato) throws Exception
	{
		getDao().removeCandidato(candidato);
	}

}