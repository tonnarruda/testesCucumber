package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.dao.geral.GastoEmpresaDao;
import com.fortes.rh.dao.geral.GastoEmpresaItemDao;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.GastoEmpresaFactory;
import com.fortes.rh.test.factory.geral.GastoEmpresaItemFactory;
import com.fortes.rh.test.factory.geral.GastoFactory;

public class GastoEmpresaItemDaoHibernateTest extends GenericDaoHibernateTest<GastoEmpresaItem>
{
	private GastoEmpresaItemDao gastoEmpresaItemDao;
	private GastoEmpresaDao gastoEmpresaDao;
	private GastoDao gastoDao;

	public GastoEmpresaItem getEntity()
	{
		GastoEmpresaItem gastoEmpresaItem = new GastoEmpresaItem();

		gastoEmpresaItem.setId(null);
		gastoEmpresaItem.setGasto(null);
		gastoEmpresaItem.setGastoEmpresa(null);
		gastoEmpresaItem.setValor(1D);

		return gastoEmpresaItem;
	}

	public void testRemoveGastos()
	{
		GastoEmpresa gastoEmpresa = GastoEmpresaFactory.getEntity();
		gastoEmpresa = gastoEmpresaDao.save(gastoEmpresa);

		GastoEmpresaItem gastoEmpresaItem = getEntity();
		gastoEmpresaItem.setGastoEmpresa(gastoEmpresa);
		gastoEmpresaItem = gastoEmpresaItemDao.save(gastoEmpresaItem);

		gastoEmpresaItemDao.removeGastos(gastoEmpresa);
	}

	public void testGetGastosImportaveis()
	{
		GastoEmpresa gastoEmpresa = GastoEmpresaFactory.getEntity();
		gastoEmpresa = gastoEmpresaDao.save(gastoEmpresa);

		Gasto gasto = GastoFactory.getEntity();
		gasto.setNaoImportar(false);
		gasto = gastoDao.save(gasto);

		GastoEmpresaItem gastoEmpresaItem = GastoEmpresaItemFactory.getEntity();
		gastoEmpresaItem.setGastoEmpresa(gastoEmpresa);
		gastoEmpresaItem.setGasto(gasto);
		gastoEmpresaItem = gastoEmpresaItemDao.save(gastoEmpresaItem);

		Collection<GastoEmpresaItem> retorno = gastoEmpresaItemDao.getGastosImportaveis(gastoEmpresa);

		assertEquals(1, retorno.size());
	}

	public GenericDao<GastoEmpresaItem> getGenericDao()
	{
		return gastoEmpresaItemDao;
	}

	public void setGastoEmpresaItemDao(GastoEmpresaItemDao gastoEmpresaItemDao)
	{
		this.gastoEmpresaItemDao = gastoEmpresaItemDao;
	}

	public void setGastoDao(GastoDao gastoDao)
	{
		this.gastoDao = gastoDao;
	}


	public void setGastoEmpresaDao(GastoEmpresaDao gastoEmpresaDao)
	{
		this.gastoEmpresaDao = gastoEmpresaDao;
	}

}