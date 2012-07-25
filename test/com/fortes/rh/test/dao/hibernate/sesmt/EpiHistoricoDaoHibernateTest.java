package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.util.DateUtil;

public class EpiHistoricoDaoHibernateTest extends GenericDaoHibernateTest<EpiHistorico>
{
	EpiHistoricoDao epiHistoricoDao;
	EpiDao epiDao;

	public EpiHistorico getEntity()
	{
		EpiHistorico epiHistorico = new EpiHistorico();

		epiHistorico.setId(1L);

		return epiHistorico;
	}

	public void testFindByData()
	{
		epiHistoricoDao.findByData(new Date(), 1L);
	}

	public void testGetHistoricosEpi()
	{
		Epi epi = new Epi();
		epi.setId(1L);
		epiDao.save(epi);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setEpi(epi);
		epiHistorico.setData(DateUtil.criarAnoMesDia(2007, 2, 1));
		epiHistorico.setVencimentoCA(DateUtil.criarAnoMesDia(2007, 3, 1));
		epiHistorico.setCA("ca1");
		epiHistoricoDao.save(epiHistorico);

		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setEpi(epi);
		epiHistorico2.setData(DateUtil.criarAnoMesDia(2006, 2, 1));
		epiHistorico2.setVencimentoCA(DateUtil.criarAnoMesDia(2006, 12, 1));
		epiHistorico2.setCA("ca2");
		epiHistoricoDao.save(epiHistorico2);

		EpiHistorico epiHistorico3 = new EpiHistorico();
		epiHistorico3.setEpi(epi);
		epiHistorico3.setData(DateUtil.criarAnoMesDia(2008, 2, 1));
		epiHistorico3.setVencimentoCA(DateUtil.criarAnoMesDia(2008, 12, 1));
		epiHistorico3.setCA("ca3");
		epiHistoricoDao.save(epiHistorico3);

		Collection<EpiHistorico> retorno = epiHistoricoDao.getHistoricosEpi(epi.getId(),DateUtil.criarAnoMesDia(2007, 1, 1),DateUtil.criarAnoMesDia(2008, 1, 1));

		assertEquals(1, retorno.size());
	}

	public void testFindByEpi()
	{
		Epi epi = new Epi();
		epi.setId(1L);
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		epiHistorico.setEpi(epi);
		epiHistoricoDao.save(epiHistorico);
		
		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setId(2L);
		epiHistorico2.setEpi(epi);
		epiHistoricoDao.save(epiHistorico2);
		
		Collection<EpiHistorico> retorno = epiHistoricoDao.findByEpi(epi.getId());
		
		assertEquals(2, retorno.size());
	}

	public void testFindUltimoByEpi()
	{
		Epi epi = new Epi();
		epi.setId(1L);
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		epiHistorico.setEpi(epi);
		epiHistorico.setData(DateUtil.criarDataMesAno(22, 3, 2012));
		epiHistoricoDao.save(epiHistorico);
		
		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setId(2L);
		epiHistorico2.setEpi(epi);
		epiHistorico2.setData(DateUtil.criarDataMesAno(23, 3, 2012));
		epiHistoricoDao.save(epiHistorico2);
		
		EpiHistorico retorno = epiHistoricoDao.findUltimoByEpiId(epi.getId());
		
		assertEquals(epiHistorico2.getId(), retorno.getId());
	}
	
	public void testRemoveByEpiId()
	{
		Epi epi = new Epi();
		epiDao.save(epi);
		
		Epi epi2 = new Epi();
		epiDao.save(epi2);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setEpi(epi);
		epiHistorico.setData(DateUtil.criarDataMesAno(22, 3, 2012));
		epiHistoricoDao.save(epiHistorico);
		
		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setEpi(epi);
		epiHistorico2.setData(DateUtil.criarDataMesAno(23, 3, 2012));
		epiHistoricoDao.save(epiHistorico2);
		
		EpiHistorico epiHistorico3 = new EpiHistorico();
		epiHistorico3.setEpi(epi2);
		epiHistorico3.setData(DateUtil.criarDataMesAno(22, 3, 2012));
		epiHistoricoDao.save(epiHistorico3);
		
		epiHistoricoDao.removeByEpi(epi2.getId());
		
		Collection<EpiHistorico> retorno1 = epiHistoricoDao.findByEpi(epi.getId());
		assertEquals(2, retorno1.size());

		Collection<EpiHistorico> retorno2 = epiHistoricoDao.findByEpi(epi2.getId());
		assertTrue(retorno2.isEmpty());
	}

	@Override
	public GenericDao<EpiHistorico> getGenericDao()
	{
		return epiHistoricoDao;
	}

	public void setEpiHistoricoDao(EpiHistoricoDao epiHistoricoDao)
	{
		this.epiHistoricoDao = epiHistoricoDao;
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}
}
