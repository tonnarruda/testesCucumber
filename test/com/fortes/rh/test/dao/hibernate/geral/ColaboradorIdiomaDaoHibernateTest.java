package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorIdiomaDao;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.IdiomaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorIdiomaFactory;

public class ColaboradorIdiomaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorIdioma>
{
	private ColaboradorIdiomaDao colaboradorIdiomaDao;
	private ColaboradorDao colaboradorDao;
	private IdiomaDao idiomaDao;

	public ColaboradorIdioma getEntity()
	{
		ColaboradorIdioma colaboradorIdioma = new ColaboradorIdioma();
		colaboradorIdioma.setId(null);
		colaboradorIdioma.setColaborador(null);
		colaboradorIdioma.setIdioma(null);
		colaboradorIdioma.setNivel('A');

		return colaboradorIdioma;
	}

	public GenericDao<ColaboradorIdioma> getGenericDao()
	{
		return colaboradorIdiomaDao;
	}

	public void setColaboradorIdiomaDao(ColaboradorIdiomaDao colaboradorIdiomaDao)
	{
		this.colaboradorIdiomaDao = colaboradorIdiomaDao;
	}

	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("colaborador1");
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("colaborador2");
		colaborador2 = colaboradorDao.save(colaborador2);

		Idioma idioma = IdiomaFactory.getIdioma();
		idioma = idiomaDao.save(idioma);

		ColaboradorIdioma colaboradorIdioma = ColaboradorIdiomaFactory.getEntity();
		colaboradorIdioma.setColaborador(colaborador);
		colaboradorIdioma.setIdioma(idioma);
		colaboradorIdioma = colaboradorIdiomaDao.save(colaboradorIdioma);

		ColaboradorIdioma colaboradorIdioma2 = ColaboradorIdiomaFactory.getEntity();
		colaboradorIdioma2.setColaborador(colaborador2);
		colaboradorIdioma2.setIdioma(idioma);
		colaboradorIdioma2 = colaboradorIdiomaDao.save(colaboradorIdioma2);

		Collection<ColaboradorIdioma> colaboradorIdiomas = colaboradorIdiomaDao.findByColaborador(colaborador.getId());

		assertEquals(1, colaboradorIdiomas.size());
	}
	
	public void testRemoveColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		Idioma idioma = IdiomaFactory.getIdioma();
		idioma = idiomaDao.save(idioma);
		
		ColaboradorIdioma colaboradorIdioma = ColaboradorIdiomaFactory.getEntity();
		colaboradorIdioma.setColaborador(colaborador);
		colaboradorIdioma.setIdioma(idioma);
		colaboradorIdioma = colaboradorIdiomaDao.save(colaboradorIdioma);
		
		colaboradorIdiomaDao.removeColaborador(colaborador);
		
		assertEquals(0, colaboradorIdiomaDao.findByColaborador(colaborador.getId()).size());
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setIdiomaDao(IdiomaDao idiomaDao)
	{
		this.idiomaDao = idiomaDao;
	}
}