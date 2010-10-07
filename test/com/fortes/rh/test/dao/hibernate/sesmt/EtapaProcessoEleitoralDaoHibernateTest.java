package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.dao.sesmt.EtapaProcessoEleitoralDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EtapaProcessoEleitoralFactory;

public class EtapaProcessoEleitoralDaoHibernateTest extends GenericDaoHibernateTest<EtapaProcessoEleitoral>
{
	EtapaProcessoEleitoralDao etapaProcessoEleitoralDao;
	EleicaoDao eleicaoDao;
	EmpresaDao empresaDao;

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity();
		etapaProcessoEleitoral.setEmpresa(empresa);
		etapaProcessoEleitoralDao.save(etapaProcessoEleitoral);
		Collection<EtapaProcessoEleitoral> collection = new ArrayList<EtapaProcessoEleitoral>();
		collection.add(etapaProcessoEleitoral);

		Collection<EtapaProcessoEleitoral> resultado = etapaProcessoEleitoralDao.findAllSelect(empresa.getId(), null, "prazo");

		assertEquals(resultado,collection);
	}

	public void testFindAllSelectComEleicao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity();
		etapaProcessoEleitoral.setEmpresa(empresa);
		etapaProcessoEleitoral.setEleicao(eleicao);
		etapaProcessoEleitoralDao.save(etapaProcessoEleitoral);

		EtapaProcessoEleitoral etapaProcessoEleitoralSemEleicao = EtapaProcessoEleitoralFactory.getEntity();
		etapaProcessoEleitoralSemEleicao.setEmpresa(empresa);
		etapaProcessoEleitoralDao.save(etapaProcessoEleitoralSemEleicao);

		Collection<EtapaProcessoEleitoral> collection = new ArrayList<EtapaProcessoEleitoral>();
		collection.add(etapaProcessoEleitoral);

		Collection<EtapaProcessoEleitoral> resultado = etapaProcessoEleitoralDao.findAllSelect(empresa.getId(), eleicao.getId(), "prazo");

		assertEquals(resultado,collection);
	}

	public void testRemoveByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity();
		etapaProcessoEleitoral.setEmpresa(empresa);
		etapaProcessoEleitoral.setEleicao(eleicao);
		etapaProcessoEleitoralDao.save(etapaProcessoEleitoral);
		
		etapaProcessoEleitoralDao.removeByEleicao(eleicao.getId());
		
		Collection<EtapaProcessoEleitoral> resultado = etapaProcessoEleitoralDao.findAllSelect(empresa.getId(), eleicao.getId(), "prazo");
		assertEquals(0, resultado.size());
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEtapaProcessoEleitoralDao(EtapaProcessoEleitoralDao etapaProcessoEleitoral)
	{
		this.etapaProcessoEleitoralDao = etapaProcessoEleitoral;
	}

	@Override
	public EtapaProcessoEleitoral getEntity()
	{
		return EtapaProcessoEleitoralFactory.getEntity();
	}

	@Override
	public GenericDao<EtapaProcessoEleitoral> getGenericDao()
	{
		return etapaProcessoEleitoralDao;
	}

	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}
}