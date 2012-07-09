package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoReuniaoPresencaDaoHibernateTest extends GenericDaoHibernateTest<ComissaoReuniaoPresenca>
{
	ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao;
	ComissaoReuniaoDao comissaoReuniaoDao;
	ColaboradorDao colaboradorDao;
	ComissaoDao comissaoDao;

	public void setComissaoReuniaoDao(ComissaoReuniaoDao comissaoReuniaoDao)
	{
		this.comissaoReuniaoDao = comissaoReuniaoDao;
	}

	public void setComissaoReuniaoPresencaDao(ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao)
	{
		this.comissaoReuniaoPresencaDao = comissaoReuniaoPresencaDao;
	}

	@Override
	public ComissaoReuniaoPresenca getEntity()
	{
		return new ComissaoReuniaoPresenca();
	}

	@Override
	public GenericDao<ComissaoReuniaoPresenca> getGenericDao()
	{
		return comissaoReuniaoPresencaDao;
	}

	public void testRemoveByReuniao()
	{
		ComissaoReuniao comissaoReuniao = new ComissaoReuniao();
		comissaoReuniaoDao.save(comissaoReuniao);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca.setComissaoReuniao(comissaoReuniao);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca);

		comissaoReuniaoPresencaDao.removeByReuniao(comissaoReuniao.getId());
	}

	public void testFindByReuniao()
	{
		ComissaoReuniao comissaoReuniao = new ComissaoReuniao();
		comissaoReuniaoDao.save(comissaoReuniao);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca.setComissaoReuniao(comissaoReuniao);
		comissaoReuniaoPresenca.setColaborador(colaborador);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca);

		Collection<ComissaoReuniaoPresenca> resultado = comissaoReuniaoPresencaDao.findByReuniao(comissaoReuniao.getId());
		assertEquals(1, resultado.size());

		ComissaoReuniaoPresenca elemento = ((ComissaoReuniaoPresenca)resultado.toArray()[0]);
		assertEquals(elemento.getColaborador(), colaborador);
		assertEquals(elemento.getId(), comissaoReuniaoPresenca.getId());
	}

	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);

		ComissaoReuniao comissaoReuniao1 = new ComissaoReuniao();
		comissaoReuniao1.setComissao(comissao);
		comissaoReuniao1.setData(DateUtil.criarDataMesAno(07, 07, 2011));
		comissaoReuniaoDao.save(comissaoReuniao1);

		ComissaoReuniao comissaoReuniao2 = new ComissaoReuniao();
		comissaoReuniao2.setComissao(comissao);
		comissaoReuniao2.setData(DateUtil.criarDataMesAno(10, 10, 2011));
		comissaoReuniaoDao.save(comissaoReuniao2);

		Colaborador antonio = ColaboradorFactory.getEntity();
		antonio.setNome("antonio");
		colaboradorDao.save(antonio);

		Colaborador jose = ColaboradorFactory.getEntity();
		jose.setNome("jose");
		colaboradorDao.save(jose);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca1 = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca1.setComissaoReuniao(comissaoReuniao1);
		comissaoReuniaoPresenca1.setColaborador(antonio);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca1);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca2 = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca2.setComissaoReuniao(comissaoReuniao1);
		comissaoReuniaoPresenca2.setColaborador(jose);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca2);
		
		ComissaoReuniaoPresenca comissaoReuniaoPresenca3 = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca3.setComissaoReuniao(comissaoReuniao2);
		comissaoReuniaoPresenca3.setColaborador(antonio);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca3);

		Collection<ComissaoReuniaoPresenca> resultado = comissaoReuniaoPresencaDao.findByComissao(comissao.getId(), false);
		assertEquals(3, resultado.size());
		assertEquals(antonio, ((ComissaoReuniaoPresenca)resultado.toArray()[0]).getColaborador());
		assertEquals(antonio, ((ComissaoReuniaoPresenca)resultado.toArray()[1]).getColaborador());
		assertEquals(jose, ((ComissaoReuniaoPresenca)resultado.toArray()[2]).getColaborador());
		
		resultado = comissaoReuniaoPresencaDao.findByComissao(comissao.getId(), true);
		assertEquals(3, resultado.size());
		assertEquals(antonio, ((ComissaoReuniaoPresenca)resultado.toArray()[0]).getColaborador());
		assertEquals(jose, ((ComissaoReuniaoPresenca)resultado.toArray()[1]).getColaborador());
		assertEquals(antonio, ((ComissaoReuniaoPresenca)resultado.toArray()[2]).getColaborador());
	}
	
	public void testTexistePresencaNaReuniao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		ComissaoReuniao comissaoReuniao1 = new ComissaoReuniao();
		comissaoReuniao1.setComissao(comissao);
		comissaoReuniao1.setData(DateUtil.criarDataMesAno(07, 07, 2011));
		comissaoReuniaoDao.save(comissaoReuniao1);
		
		ComissaoReuniao comissaoReuniao2 = new ComissaoReuniao();
		comissaoReuniao2.setComissao(comissao);
		comissaoReuniao2.setData(DateUtil.criarDataMesAno(10, 10, 2011));
		comissaoReuniaoDao.save(comissaoReuniao2);
		
		Colaborador antonio = ColaboradorFactory.getEntity();
		antonio.setNome("antonio");
		colaboradorDao.save(antonio);
		
		Colaborador jose = ColaboradorFactory.getEntity();
		jose.setNome("jose");
		colaboradorDao.save(jose);
		
		ComissaoReuniaoPresenca comissaoReuniaoPresenca1 = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca1.setComissaoReuniao(comissaoReuniao1);
		comissaoReuniaoPresenca1.setColaborador(antonio);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca1);
		
		ComissaoReuniaoPresenca comissaoReuniaoPresenca2 = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca2.setComissaoReuniao(comissaoReuniao1);
		comissaoReuniaoPresenca2.setColaborador(jose);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca2);
		
		ComissaoReuniaoPresenca comissaoReuniaoPresenca3 = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca3.setComissaoReuniao(comissaoReuniao2);
		comissaoReuniaoPresenca3.setColaborador(antonio);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca3);
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(antonio.getId());
		colaboradorIds.add(jose.getId());
		
		assertTrue(comissaoReuniaoPresencaDao.existeReuniaoPresensa(comissao.getId(), colaboradorIds));
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
}