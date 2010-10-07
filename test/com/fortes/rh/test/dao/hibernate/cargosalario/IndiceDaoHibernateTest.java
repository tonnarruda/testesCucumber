package com.fortes.rh.test.dao.hibernate.cargosalario;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;

public class IndiceDaoHibernateTest extends GenericDaoHibernateTest<Indice>
{
	private IndiceDao indiceDao;
	private IndiceHistoricoDao indiceHistoricoDao;

	public Indice getEntity()
	{
		return IndiceFactory.getEntity();
	}

	public GenericDao<Indice> getGenericDao()
	{
		return indiceDao;
	}

	public void testFindByIdProjection()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Indice indiceRetorno = indiceDao.findByIdProjection(indice.getId());
		assertEquals(indice, indiceRetorno);
	}

	public void testFindByCodigo()
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0013254765");
		indice = indiceDao.save(indice);

		Indice indiceRetorno = indiceDao.findByCodigo("0013254765");
		assertEquals(indice, indiceRetorno);
	}

	public void testRemoveByCodigo()
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0013254765");
		indice = indiceDao.save(indice);

		assertTrue(indiceDao.remove("0013254765"));
		assertNull(indiceDao.findByCodigo("0013254765"));
	}

	public void testFindHistoricoAtual()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		indiceHistorico.setIndice(indice);

		Indice retorno = indiceDao.findHistoricoAtual(indice.getId(), null);

		assertEquals(indice.getId(), retorno.getId());
	}

	public void testFindIndiceByCodigoAc()
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("010203");
		indice = indiceDao.save(indice);
		
		Indice indiceRetorno = indiceDao.findIndiceByCodigoAc(indice.getCodigoAC());
		
		assertEquals(indice, indiceRetorno);
	}
	
	public void setIndiceDao(IndiceDao indiceDao)
	{
		this.indiceDao = indiceDao;
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao)
	{
		this.indiceHistoricoDao = indiceHistoricoDao;
	}
}