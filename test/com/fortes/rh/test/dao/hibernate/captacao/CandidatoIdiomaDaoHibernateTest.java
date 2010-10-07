package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.geral.CandidatoIdiomaFactory;

public class CandidatoIdiomaDaoHibernateTest extends GenericDaoHibernateTest<CandidatoIdioma>
{
	private CandidatoIdiomaDao candidatoIdiomaDao;
	private CandidatoDao candidatoDao;

	public CandidatoIdioma getEntity()
	{
		CandidatoIdioma candidatoIdioma = new CandidatoIdioma();
		candidatoIdioma.setNivel('B');

		return candidatoIdioma;
	}

	public GenericDao<CandidatoIdioma> getGenericDao()
	{
		return candidatoIdiomaDao;
	}

	@SuppressWarnings("unchecked")
	public void testFindByCandidato()
	{
		Candidato candidato1 = CandidatoFactory.getCandidato();
		Candidato candidato2 = CandidatoFactory.getCandidato();

		candidato1.getEndereco().setUf(null);
		candidato1.getEndereco().setCidade(null);

		candidato2.getEndereco().setCidade(null);
		candidato2.getEndereco().setUf(null);

		candidato1 = candidatoDao.save(candidato1);
		candidato2 = candidatoDao.save(candidato2);

		CandidatoIdioma candidatoIdioma1 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma1.setCandidato(candidato1);
		candidatoIdioma1 = candidatoIdiomaDao.save(candidatoIdioma1);

		CandidatoIdioma candidatoIdioma2 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma2.setCandidato(candidato1);
		candidatoIdioma2 = candidatoIdiomaDao.save(candidatoIdioma2);

		CandidatoIdioma candidatoIdioma3 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma3.setCandidato(candidato2);
		candidatoIdioma3 = candidatoIdiomaDao.save(candidatoIdioma3);

		Collection<CandidatoIdioma> candidatoIdiomas = candidatoIdiomaDao.findByCandidato(candidato1.getId());

		assertEquals(2, candidatoIdiomas.size());

		candidatoIdiomas = candidatoIdiomaDao.findByCandidato(candidato2.getId());

		assertEquals(1, candidatoIdiomas.size());
	}

	public void testRemoveCandidato()
	{
		Candidato candidato = prepareFindCandidato();

		candidatoIdiomaDao.removeCandidato(candidato);

		assertEquals(0, candidatoIdiomaDao.findInCandidatos(new Long[]{candidato.getId()}).size());
	}

	public void testFindInCandidatos()
	{
		Candidato candidato = prepareFindCandidato();

		assertEquals(1, candidatoIdiomaDao.findInCandidatos(new Long[]{candidato.getId()}).size());
	}

	private Candidato prepareFindCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato();

		candidato.getEndereco().setUf(null);
		candidato.getEndereco().setCidade(null);

		candidato = candidatoDao.save(candidato);

		CandidatoIdioma candidatoIdioma = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma.setCandidato(candidato);
		candidatoIdioma = candidatoIdiomaDao.save(candidatoIdioma);

		return candidato;
	}

	public void setCandidatoIdiomaDao(CandidatoIdiomaDao candidatoIdiomaDao)
	{
		this.candidatoIdiomaDao = candidatoIdiomaDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}
}
