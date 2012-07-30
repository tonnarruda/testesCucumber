package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoPeriodoDaoHibernateTest extends GenericDaoHibernateTest<ComissaoPeriodo>
{
	ComissaoPeriodoDao comissaoPeriodoDao;
	ComissaoDao comissaoDao;

	public void setComissaoPeriodoDao(ComissaoPeriodoDao comissaoPeriodoDao)
	{
		this.comissaoPeriodoDao = comissaoPeriodoDao;
	}

	@Override
	public ComissaoPeriodo getEntity()
	{
		return new ComissaoPeriodo();
	}

	@Override
	public GenericDao<ComissaoPeriodo> getGenericDao()
	{
		return comissaoPeriodoDao;
	}

	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);

		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);

		Collection<ComissaoPeriodo> colecao = comissaoPeriodoDao.findByComissao(comissao.getId());
		assertEquals(1,colecao.size());
	}

	public void testFindByIdProjection()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);

		assertEquals(comissaoPeriodo, comissaoPeriodoDao.findByIdProjection(comissaoPeriodo.getId()));
	}
	
	public void testFindProximo()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(9, 1, 2010);
		Date dataPeriodo3 = DateUtil.criarDataMesAno(5, 4, 2010);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2.setComissao(comissao);
		comissaoPeriodo2.setaPartirDe(dataPeriodo2);
		comissaoPeriodoDao.save(comissaoPeriodo2);
		
		ComissaoPeriodo comissaoPeriodo3 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo3.setComissao(comissao);
		comissaoPeriodo3.setaPartirDe(dataPeriodo3);
		comissaoPeriodoDao.save(comissaoPeriodo3);
		
		ComissaoPeriodo resultado = comissaoPeriodoDao.findProximo(comissaoPeriodo);
		
		assertEquals(dataPeriodo2, resultado.getaPartirDe());
	}
	
	public void testMaxDataComissaoPeriodo()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(9, 1, 2010);
		Date dataPeriodo3 = DateUtil.criarDataMesAno(5, 4, 2010);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2.setComissao(comissao);
		comissaoPeriodo2.setaPartirDe(dataPeriodo2);
		comissaoPeriodoDao.save(comissaoPeriodo2);
		
		ComissaoPeriodo comissaoPeriodo3 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo3.setComissao(comissao);
		comissaoPeriodo3.setaPartirDe(dataPeriodo3);
		comissaoPeriodoDao.save(comissaoPeriodo3);
		
		Date resultado = comissaoPeriodoDao.maxDataComissaoPeriodo(comissao.getId());
		
		assertEquals("05/04/2010", DateUtil.formataDiaMesAno(resultado));
	}
	
	public void testExisteComissaoPeriodoNaMesmaData()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		Date dataPeriodo = DateUtil.criarDataMesAno(10, 12, 2009);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setaPartirDe(dataPeriodo);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2.setComissao(comissao);
		comissaoPeriodo2.setaPartirDe(dataPeriodo);
		comissaoPeriodoDao.save(comissaoPeriodo2);
		
		assertTrue(comissaoPeriodoDao.verificaComissaoNaMesmaData(comissaoPeriodo));
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
}