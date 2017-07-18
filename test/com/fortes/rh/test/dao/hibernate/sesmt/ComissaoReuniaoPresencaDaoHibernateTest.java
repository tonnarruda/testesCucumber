package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoReuniaoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoReuniaoPresencaDaoHibernateTest extends GenericDaoHibernateTest<ComissaoReuniaoPresenca>
{
	ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao;
	ComissaoReuniaoDao comissaoReuniaoDao;
	ComissaoMembroDao comissaoMembroDao;
	ComissaoPeriodoDao comissaoPeriodoDao; 
	ColaboradorDao colaboradorDao;
	ComissaoDao comissaoDao;

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
		
		assertTrue(comissaoReuniaoPresencaDao.existeReuniaoPresenca(comissao.getId(), colaboradorIds));
	}
	
	public void testFindPresencaColaboradoresByReuniao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setDataIni(DateUtil.criarDataMesAno(1, 1, 2012));
		comissao.setDataFim(DateUtil.criarDataMesAno(31, 12, 2012));
		comissaoDao.save(comissao);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("joao");
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("maria");
		colaboradorDao.save(maria);
		
		Colaborador gil = ColaboradorFactory.getEntity();
		gil.setNome("gil");
		colaboradorDao.save(gil);
		
		ComissaoPeriodo cp1 = ComissaoPeriodoFactory.getEntity();
		cp1.setComissao(comissao);
		cp1.setaPartirDe(DateUtil.criarDataMesAno(1, 1, 2012));
		comissaoPeriodoDao.save(cp1);
		
		ComissaoPeriodo cp2 = ComissaoPeriodoFactory.getEntity();
		cp2.setComissao(comissao);
		cp2.setaPartirDe(DateUtil.criarDataMesAno(1, 7, 2012));
		comissaoPeriodoDao.save(cp2);
		
		ComissaoMembro cmJoao1 = ComissaoMembroFactory.getEntity();
		cmJoao1.setColaborador(joao);
		cmJoao1.setComissaoPeriodo(cp1);
		comissaoMembroDao.save(cmJoao1);
		
		ComissaoMembro cmMaria1 = ComissaoMembroFactory.getEntity();
		cmMaria1.setColaborador(maria);
		cmMaria1.setComissaoPeriodo(cp1);
		comissaoMembroDao.save(cmMaria1);
		
		ComissaoMembro cmMaria2 = ComissaoMembroFactory.getEntity();
		cmMaria2.setColaborador(maria);
		cmMaria2.setComissaoPeriodo(cp2);
		comissaoMembroDao.save(cmMaria2);
		
		ComissaoMembro cmGil2 = ComissaoMembroFactory.getEntity();
		cmGil2.setColaborador(gil);
		cmGil2.setComissaoPeriodo(cp2);
		comissaoMembroDao.save(cmGil2);
		
		ComissaoReuniao r1 = ComissaoReuniaoFactory.getEntity();
		r1.setData(DateUtil.criarDataMesAno(1, 2, 2012));
		r1.setComissao(comissao);
		comissaoReuniaoDao.save(r1);
		
		ComissaoReuniaoPresenca crp1 = new ComissaoReuniaoPresenca();
		crp1.setColaborador(joao);
		crp1.setComissaoReuniao(r1);
		crp1.setPresente(false);
		crp1.setJustificativaFalta("doença");
		comissaoReuniaoPresencaDao.save(crp1);
		
		Collection<ComissaoReuniaoPresenca> presencas = comissaoReuniaoPresencaDao.findPresencaColaboradoresByReuniao(r1.getId(), r1.getData());
		assertEquals(2, presencas.size());
		
		ComissaoReuniaoPresenca presenca1 = (ComissaoReuniaoPresenca) presencas.toArray()[0];
		ComissaoReuniaoPresenca presenca2 = (ComissaoReuniaoPresenca) presencas.toArray()[1];
		
		assertEquals(crp1.getColaborador().getId(), presenca1.getColaborador().getId());
		assertEquals(crp1.getColaborador().getNome(), presenca1.getColaborador().getNome());
		assertEquals(crp1.getPresente(), presenca1.getPresente());
		assertEquals(crp1.getJustificativaFalta(), presenca1.getJustificativaFalta());
		
		assertEquals(maria.getId(), presenca2.getColaborador().getId());
	}
	

	public void testFindPresencasByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setDataIni(DateUtil.criarDataMesAno(1, 1, 2012));
		comissao.setDataFim(DateUtil.criarDataMesAno(31, 12, 2012));
		comissaoDao.save(comissao);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("joao");
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("maria");
		colaboradorDao.save(maria);
		
		Colaborador gil = ColaboradorFactory.getEntity();
		gil.setNome("gil");
		colaboradorDao.save(gil);
		
		ComissaoPeriodo cp1 = ComissaoPeriodoFactory.getEntity();
		cp1.setComissao(comissao);
		cp1.setaPartirDe(DateUtil.criarDataMesAno(1, 1, 2012));
		comissaoPeriodoDao.save(cp1);
		
		ComissaoPeriodo cp2 = ComissaoPeriodoFactory.getEntity();
		cp2.setComissao(comissao);
		cp2.setaPartirDe(DateUtil.criarDataMesAno(1, 7, 2012));
		comissaoPeriodoDao.save(cp2);
		
		ComissaoMembro cmJoao1 = ComissaoMembroFactory.getEntity();
		cmJoao1.setColaborador(joao);
		cmJoao1.setComissaoPeriodo(cp1);
		comissaoMembroDao.save(cmJoao1);
		
		ComissaoMembro cmMaria1 = ComissaoMembroFactory.getEntity();
		cmMaria1.setColaborador(maria);
		cmMaria1.setComissaoPeriodo(cp1);
		comissaoMembroDao.save(cmMaria1);
		
		ComissaoMembro cmMaria2 = ComissaoMembroFactory.getEntity();
		cmMaria2.setColaborador(maria);
		cmMaria2.setComissaoPeriodo(cp2);
		comissaoMembroDao.save(cmMaria2);
		
		ComissaoMembro cmGil2 = ComissaoMembroFactory.getEntity();
		cmGil2.setColaborador(gil);
		cmGil2.setComissaoPeriodo(cp2);
		comissaoMembroDao.save(cmGil2);
		
		ComissaoReuniao r1 = ComissaoReuniaoFactory.getEntity();
		r1.setData(DateUtil.criarDataMesAno(1, 2, 2012));
		r1.setComissao(comissao);
		comissaoReuniaoDao.save(r1);
		
		ComissaoReuniao r2 = ComissaoReuniaoFactory.getEntity();
		r2.setData(DateUtil.criarDataMesAno(1, 8, 2012));
		r2.setComissao(comissao);
		comissaoReuniaoDao.save(r2);
		
		ComissaoReuniaoPresenca crp1 = new ComissaoReuniaoPresenca();
		crp1.setColaborador(joao);
		crp1.setComissaoReuniao(r1);
		crp1.setPresente(false);
		crp1.setJustificativaFalta("doença");
		comissaoReuniaoPresencaDao.save(crp1);
		
		ComissaoReuniaoPresenca crp2 = new ComissaoReuniaoPresenca();
		crp2.setColaborador(maria);
		crp2.setComissaoReuniao(r2);
		crp2.setPresente(true);
		comissaoReuniaoPresencaDao.save(crp2);
		
		Collection<ComissaoReuniaoPresenca> presencas = comissaoReuniaoPresencaDao.findPresencasByComissao(comissao.getId());
		
		assertEquals(2, presencas.size());
		
		assertEquals(joao.getId(), ((ComissaoReuniaoPresenca)presencas.toArray()[0]).getColaborador().getId());
		assertEquals(maria.getId(), ((ComissaoReuniaoPresenca)presencas.toArray()[1]).getColaborador().getId());
		
		assertFalse(((ComissaoReuniaoPresenca)presencas.toArray()[0]).getPresente());
		assertTrue(((ComissaoReuniaoPresenca)presencas.toArray()[1]).getPresente());
	
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}

	public void setComissaoReuniaoDao(ComissaoReuniaoDao comissaoReuniaoDao)
	{
		this.comissaoReuniaoDao = comissaoReuniaoDao;
	}

	public void setComissaoReuniaoPresencaDao(ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao)
	{
		this.comissaoReuniaoPresencaDao = comissaoReuniaoPresencaDao;
	}

	public void setComissaoMembroDao(ComissaoMembroDao comissaoMembroDao) {
		this.comissaoMembroDao = comissaoMembroDao;
	}

	public void setComissaoPeriodoDao(ComissaoPeriodoDao comissaoPeriodoDao) {
		this.comissaoPeriodoDao = comissaoPeriodoDao;
	}
}