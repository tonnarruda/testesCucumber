package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.util.DateUtil;

public class IndiceHistoricoDaoHibernateTest extends GenericDaoHibernateTest<IndiceHistorico>
{
	private IndiceHistoricoDao indiceHistoricoDao;
	private IndiceDao indiceDao;

	public IndiceHistorico getEntity()
	{
		return IndiceHistoricoFactory.getEntity();
	}

	public GenericDao<IndiceHistorico> getGenericDao()
	{
		return indiceHistoricoDao;
	}

	public void testFindAllSelect()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoDao.findAllSelect(indice.getId());

		assertEquals(1, indiceHistoricos.size());
	}

	public void testFindByIdProjection()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		IndiceHistorico indiceHistoricoRetorno = indiceHistoricoDao.findByIdProjection(indiceHistorico.getId());
		assertEquals(indiceHistorico, indiceHistoricoRetorno);
		assertEquals(indice, indiceHistoricoRetorno.getIndice());
	}

	public void testVerifyData()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Date data = new Date();

		assertEquals(false, indiceHistoricoDao.verifyData(null, data, indice.getId()));
	}

	public void testRemoveCodigoAC()
	{
		Date data = new Date();

		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("001");
		indice = indiceDao.save(indice);

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setData(data);
		indiceHistorico.setIndice(indice);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		assertTrue(indiceHistoricoDao.remove(data, indice.getId()));
	}

	public void testVerifyDataUpdate()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Date data = new Date();

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(data);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		assertEquals(false, indiceHistoricoDao.verifyData(indiceHistorico.getId(), data, indice.getId()));
	}

	public void testVerifyDataInsert()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Date data = new Date();

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(data);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		assertEquals(true, indiceHistoricoDao.verifyData(null, data, indice.getId()));
	}
	
	public void testUpdateValor()
	{
		Indice indice = IndiceFactory.getEntity();
		indiceDao.save(indice);
		
		Date data = new Date();
		
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setValor(5.00);
		indiceHistorico.setData(data);
		indiceHistoricoDao.save(indiceHistorico);
		
		indiceHistoricoDao.updateValor(data, indice.getId(), 12.00);
		assertEquals(12.00, indiceHistoricoDao.findByIdProjection(indiceHistorico.getId()).getValor());
	}

	public void testFindUltimoSalarioIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Date data = new Date();

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(data);
		indiceHistorico.setValor(150.00);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		assertEquals(150.00, indiceHistoricoDao.findUltimoSalarioIndice(indice.getId()));

		indiceHistorico.setIndice(null);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		indiceHistoricoDao.findUltimoSalarioIndice(indice.getId());
	}
	
	public void testFindByPeriodo()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);
		
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(DateUtil.criarDataMesAno(01, 01, 1999));
		indiceHistorico.setValor(150.00);
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		assertEquals(1, indiceHistoricoDao.findByPeriodo(indice.getId(), DateUtil.criarDataMesAno(01, 01, 1998), DateUtil.criarDataMesAno(01, 01, 2000)).size());
	}

	public void testVerifyDataIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		IndiceHistorico indiceHistorico1 = IndiceHistoricoFactory.getEntity();
		indiceHistorico1.setIndice(indice);
		indiceHistorico1.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		indiceHistoricoDao.save(indiceHistorico1);
		
		IndiceHistorico indiceHistorico2 = IndiceHistoricoFactory.getEntity();
		indiceHistorico2.setIndice(indice);
		indiceHistorico2.setData(DateUtil.criarDataMesAno(01, 01, 2009));
		indiceHistoricoDao.save(indiceHistorico2);
		
		assertEquals(false, indiceHistoricoDao.existsAnteriorByDataIndice(DateUtil.criarDataMesAno(01, 01, 1989), indice.getId()));
		assertEquals(true, indiceHistoricoDao.existsAnteriorByDataIndice(DateUtil.criarDataMesAno(01, 01, 2000), indice.getId()));
		assertEquals(true, indiceHistoricoDao.existsAnteriorByDataIndice(DateUtil.criarDataMesAno(01, 01, 2001), indice.getId()));
		assertEquals(true, indiceHistoricoDao.existsAnteriorByDataIndice(DateUtil.criarDataMesAno(01, 01, 2009), indice.getId()));
		assertEquals(true, indiceHistoricoDao.existsAnteriorByDataIndice(DateUtil.criarDataMesAno(01, 01, 2010), indice.getId()));
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao)
	{
		this.indiceHistoricoDao = indiceHistoricoDao;
	}

	public void setIndiceDao(IndiceDao indiceDao)
	{
		this.indiceDao = indiceDao;
	}
}