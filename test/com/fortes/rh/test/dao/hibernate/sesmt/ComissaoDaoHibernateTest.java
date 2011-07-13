package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class ComissaoDaoHibernateTest extends GenericDaoHibernateTest<Comissao>
{
	ComissaoDao comissaoDao;
	EleicaoDao eleicaoDao;
	EmpresaDao empresaDao;

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}

	@Override
	public Comissao getEntity()
	{
		return ComissaoFactory.getEntity();
	}

	@Override
	public GenericDao<Comissao> getGenericDao()
	{
		return comissaoDao;
	}

	public void testFindByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setEleicao(eleicao);
		comissaoDao.save(comissao);

		Collection<Comissao> comissaos = comissaoDao.findByEleicao(eleicao.getId());
		assertEquals(1, comissaos.size());
	}

	public void testFindByIdProjection()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);
		
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setEleicao(eleicao);
		comissaoDao.save(comissao);

		assertEquals(comissao.getId(), comissaoDao.findByIdProjection(comissao.getId()).getId());
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicao.setEmpresa(empresa);
		eleicaoDao.save(eleicao);
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setEleicao(eleicao);
		comissaoDao.save(comissao);

		Collection<Comissao> colecao = comissaoDao.findAllSelect(empresa.getId());
		assertEquals(1,colecao.size());
	}
	
	public void testUpdateTextosComunicados()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setAtaPosseTexto1("Texto de Ata de instalação e posse...");
		comissao.setAtaPosseTexto2("");
		comissaoDao.save(comissao);
		
		comissao.setAtaPosseTexto2("Segunda parte do Texto de Ata de instalação e posse...");
		assertTrue(comissaoDao.updateTextosComunicados(comissao));
	}

	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}