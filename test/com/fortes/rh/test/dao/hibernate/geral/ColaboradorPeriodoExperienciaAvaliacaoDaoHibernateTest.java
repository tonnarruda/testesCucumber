package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class ColaboradorPeriodoExperienciaAvaliacaoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorPeriodoExperienciaAvaliacao>
{
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	private ColaboradorDao colaboradorDao;
	
	public ColaboradorPeriodoExperienciaAvaliacao getEntity()
	{
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = new ColaboradorPeriodoExperienciaAvaliacao();

		return colaboradorPeriodoExperienciaAvaliacao;
	}

	public void testRemoveByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorPeriodoExperienciaAvaliacao configuracao = new ColaboradorPeriodoExperienciaAvaliacao();
		configuracao.setColaborador(colaborador);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configuracao);
		
		colaboradorPeriodoExperienciaAvaliacaoDao.removeByColaborador(colaborador.getId());
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configuracoes = colaboradorPeriodoExperienciaAvaliacaoDao.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()});
		
		assertEquals(0, configuracoes.size());
	}

	public GenericDao<ColaboradorPeriodoExperienciaAvaliacao> getGenericDao()
	{
		return colaboradorPeriodoExperienciaAvaliacaoDao;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaoDao(ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao)
	{
		this.colaboradorPeriodoExperienciaAvaliacaoDao = colaboradorPeriodoExperienciaAvaliacaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}
}