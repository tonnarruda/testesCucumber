package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ProntuarioDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ProntuarioFactory;

public class ProntuarioDaoHibernateTest extends GenericDaoHibernateTest<Prontuario>
{
	ProntuarioDao prontuarioDao;
	ColaboradorDao colaboradorDao;

	public void setProntuarioDao(ProntuarioDao prontuarioDao)
	{
		this.prontuarioDao = prontuarioDao;
	}

	@Override
	public Prontuario getEntity()
	{
		return ProntuarioFactory.getEntity();
	}

	@Override
	public GenericDao<Prontuario> getGenericDao()
	{
		return prontuarioDao;
	}

	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Prontuario prontuario = ProntuarioFactory.getEntity();
		prontuario.setColaborador(colaborador);
		prontuarioDao.save(prontuario);
		
		assertEquals(1, prontuarioDao.findByColaborador(colaborador).size());
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}
}