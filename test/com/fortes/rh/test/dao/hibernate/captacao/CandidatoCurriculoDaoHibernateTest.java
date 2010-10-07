package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoCurriculoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;

public class CandidatoCurriculoDaoHibernateTest extends GenericDaoHibernateTest<CandidatoCurriculo>
{
	private CandidatoCurriculoDao candidatoCurriculoDao;
	private CandidatoDao candidatoDao;

	public CandidatoCurriculo getEntity()
	{
		CandidatoCurriculo candidatoCurriculo = new CandidatoCurriculo();
		candidatoCurriculo.setCandidato(null);
		candidatoCurriculo.setCurriculo("curriculo");

		return candidatoCurriculo;
	}

	public GenericDao<CandidatoCurriculo> getGenericDao()
	{
		return candidatoCurriculoDao;
	}

	public void setCandidatoCurriculoDao(CandidatoCurriculoDao candidatoCurriculoDao)
	{
		this.candidatoCurriculoDao = candidatoCurriculoDao;
	}
	
	public void testRemoveCandidato() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);
		
		CandidatoCurriculo candidatoCurriculo = getEntity();
		candidatoCurriculo.setCandidato(candidato);
		candidatoCurriculo = candidatoCurriculoDao.save(candidatoCurriculo);
		
		candidatoCurriculoDao.removeCandidato(candidato);
		
		CandidatoCurriculo candidatoCurriculoRetorno = candidatoCurriculoDao.findById(candidatoCurriculo.getId(), null);
		
		assertNull(candidatoCurriculoRetorno);
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}
}
