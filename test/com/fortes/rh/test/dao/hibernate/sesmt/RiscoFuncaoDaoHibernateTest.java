package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;

public class RiscoFuncaoDaoHibernateTest extends GenericDaoHibernateTest<RiscoFuncao>
{
	private RiscoFuncaoDao riscoFuncaoDao;
	private HistoricoFuncaoDao historicoFuncaoDao;

	@Override
	public RiscoFuncao getEntity()
	{
		return RiscoFuncaoFactory.getEntity();
	}

	@Override
	public GenericDao<RiscoFuncao> getGenericDao()
	{
		return riscoFuncaoDao;
	}

	public void testRemoveByHistoricoFuncao()
	{
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncaoDao.save(historicoFuncao);
		
		RiscoFuncao riscoFuncao = RiscoFuncaoFactory.getEntity();
		riscoFuncao.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao);
		
		assertTrue(riscoFuncaoDao.removeByHistoricoFuncao(historicoFuncao.getId()));
	}
	
	public void setRiscoFuncaoDao(RiscoFuncaoDao riscoFuncaoDao) {
		this.riscoFuncaoDao = riscoFuncaoDao;
	}

	public void setHistoricoFuncaoDao(HistoricoFuncaoDao historicoFuncaoDao) {
		this.historicoFuncaoDao = historicoFuncaoDao;
	}
}
