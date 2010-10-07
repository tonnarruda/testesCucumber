package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;

public class SolicitacaoEpiItemDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoEpiItem>
{
	SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	EpiDao epiDao;
	SolicitacaoEpiDao solicitacaoEpiDao;
	EmpresaDao empresaDao;

	@Override
	public SolicitacaoEpiItem getEntity()
	{
		SolicitacaoEpiItem epiSolicitacaoEpi = new SolicitacaoEpiItem();
		epiSolicitacaoEpi.setId(null);

		return epiSolicitacaoEpi;
	}

	@Override
	public GenericDao<SolicitacaoEpiItem> getGenericDao()
	{
		return solicitacaoEpiItemDao;
	}

	public void testFindBySolicitacaoEpi()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Epi epi = new Epi();
		epi.setEmpresa(empresa);
		epi.setNome("EPI");
		epiDao.save(epi);

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpiDao.save(solicitacaoEpi);

		SolicitacaoEpiItem epiSolicitacaoEpi = new SolicitacaoEpiItem();
		epiSolicitacaoEpi.setEpi(epi);
		epiSolicitacaoEpi.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(epiSolicitacaoEpi);

		Collection<SolicitacaoEpiItem> resultado = solicitacaoEpiItemDao.findBySolicitacaoEpi(solicitacaoEpi.getId());

		assertEquals(1, resultado.size());
		assertEquals(solicitacaoEpi.getId(), ((SolicitacaoEpiItem)resultado.toArray()[0]).getSolicitacaoEpi().getId());
	}

	public void testFindBySolicitacaoEpiArray()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Epi epi = new Epi();
		epi.setEmpresa(empresa);
		epi.setNome("EPI");
		epiDao.save(epi);

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpiDao.save(solicitacaoEpi);

		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setEmpresa(empresa);
		solicitacaoEpiDao.save(solicitacaoEpi2);

		SolicitacaoEpiItem epiSolicitacaoEpi = new SolicitacaoEpiItem();
		epiSolicitacaoEpi.setEpi(epi);
		epiSolicitacaoEpi.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(epiSolicitacaoEpi);

		SolicitacaoEpiItem epiSolicitacaoEpi2 = new SolicitacaoEpiItem();
		epiSolicitacaoEpi2.setEpi(epi);
		epiSolicitacaoEpi2.setSolicitacaoEpi(solicitacaoEpi2);
		solicitacaoEpiItemDao.save(epiSolicitacaoEpi2);

		Long[] ids = new Long[]{solicitacaoEpi.getId(),solicitacaoEpi2.getId()};

		Collection<SolicitacaoEpiItem> resultado = solicitacaoEpiItemDao.findBySolicitacaoEpi(ids);

		assertEquals(2, resultado.size());
	}

	public void testFindBySolicitacaoEpiAndEpi()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epiDao.save(epi);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpiDao.save(solicitacaoEpi);
		SolicitacaoEpiItem epiSolicitacaoEpi = new SolicitacaoEpiItem();
		epiSolicitacaoEpi.setEpi(epi);
		epiSolicitacaoEpi.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(epiSolicitacaoEpi);

		SolicitacaoEpiItem resultado = solicitacaoEpiItemDao.findBySolicitacaoEpiAndEpi(solicitacaoEpi.getId(), epi.getId());
		assertEquals(resultado, epiSolicitacaoEpi);
	}
	public void testRemoveAllBySolicitacaoEpi()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epiDao.save(epi);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpiDao.save(solicitacaoEpi);
		SolicitacaoEpiItem epiSolicitacaoEpi = new SolicitacaoEpiItem();
		epiSolicitacaoEpi.setEpi(epi);
		epiSolicitacaoEpi.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(epiSolicitacaoEpi);

		solicitacaoEpiItemDao.removeAllBySolicitacaoEpi(solicitacaoEpi.getId());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}

	public void setSolicitacaoEpiDao(SolicitacaoEpiDao solicitacaoEpiDao)
	{
		this.solicitacaoEpiDao = solicitacaoEpiDao;
	}

	public void setSolicitacaoEpiItemDao(SolicitacaoEpiItemDao solicitacaoEpiItemDao)
	{
		this.solicitacaoEpiItemDao = solicitacaoEpiItemDao;
	}
}