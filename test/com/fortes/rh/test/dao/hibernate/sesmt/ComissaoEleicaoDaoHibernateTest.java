package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ComissaoEleicaoDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class ComissaoEleicaoDaoHibernateTest extends GenericDaoHibernateTest<ComissaoEleicao>
{
	ComissaoEleicaoDao comissaoEleicaoDao;
	EleicaoDao eleicaoDao;
	ColaboradorDao colaboradorDao;
	
	public void setComissaoEleicaoDao(ComissaoEleicaoDao comissaoEleicaoDao)
	{
		this.comissaoEleicaoDao = comissaoEleicaoDao;
	}

	@Override
	public ComissaoEleicao getEntity()
	{
		return ComissaoEleicaoFactory.getEntity();
	}

	@Override
	public GenericDao<ComissaoEleicao> getGenericDao()
	{
		return comissaoEleicaoDao;
	}
	
	public void testFindByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Joao");
		colaboradorDao.save(colaborador);
		
		ComissaoEleicao comissaoEleicao = ComissaoEleicaoFactory.getEntity();
		comissaoEleicao.setEleicao(eleicao);
		comissaoEleicao.setColaborador(colaborador);
		comissaoEleicaoDao.save(comissaoEleicao);
		
		Collection<ComissaoEleicao> comissaos = comissaoEleicaoDao.findByEleicao(eleicao.getId());
		assertEquals(1, comissaos.size());
	}
	
	public void testRemoveByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		ComissaoEleicao comissaoEleicao = ComissaoEleicaoFactory.getEntity();
		comissaoEleicao.setEleicao(eleicao);
		comissaoEleicaoDao.save(comissaoEleicao);
		
		comissaoEleicaoDao.removeByEleicao(eleicao.getId());
		
		Collection<ComissaoEleicao> comissaos = comissaoEleicaoDao.findByEleicao(eleicao.getId());
		assertEquals(0, comissaos.size());
	}
	
	public void testUpdateFuncao()
	{
		ComissaoEleicao comissaoEleicao = ComissaoEleicaoFactory.getEntity();
		comissaoEleicao.setFuncao("V");
		comissaoEleicaoDao.save(comissaoEleicao);

		comissaoEleicaoDao.updateFuncao(comissaoEleicao.getId(), "P");
		ComissaoEleicao comissaoEleicaoTmp = comissaoEleicaoDao.findByIdProjection(comissaoEleicao.getId());
		assertEquals("P", comissaoEleicaoTmp.getFuncao());
	}
	
	public void testFindByIdProjection()
	{
		ComissaoEleicao comissaoEleicao = ComissaoEleicaoFactory.getEntity();
		comissaoEleicaoDao.save(comissaoEleicao);
		
		assertEquals(comissaoEleicao.getId(), comissaoEleicaoDao.findByIdProjection(comissaoEleicao.getId()).getId());
	}

	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}
}