package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;

public class OrdemDeServicoDaoHibernateTest extends GenericDaoHibernateTest<OrdemDeServico>
{
	private OrdemDeServicoDao ordemDeServicoDao;

	@Override
	public OrdemDeServico getEntity()
	{
		return OrdemDeServicoFactory.getEntity();
	}

	@Override
	public GenericDao<OrdemDeServico> getGenericDao()
	{
		return ordemDeServicoDao;
	}

	public void setOrdemDeServicoDao(OrdemDeServicoDao ordemDeServicoDao)
	{
		this.ordemDeServicoDao = ordemDeServicoDao;
	}
}
