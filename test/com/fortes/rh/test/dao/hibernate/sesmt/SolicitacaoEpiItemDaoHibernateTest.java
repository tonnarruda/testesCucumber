package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiItemDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoEpiItem>
{
	SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	EpiDao epiDao;
	SolicitacaoEpiDao solicitacaoEpiDao;
	SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao;
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

		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setSolicitacaoEpiItem(epiSolicitacaoEpi);
		entrega.setDataEntrega(DateUtil.criarDataMesAno(9, 3, 2012));
		entrega.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
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

		SolicitacaoEpiItem epiSolicitacaoEpi = new SolicitacaoEpiItem();
		epiSolicitacaoEpi.setEpi(epi);
		epiSolicitacaoEpi.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(epiSolicitacaoEpi);

		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(epiSolicitacaoEpi);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega);

		Long id = solicitacaoEpi.getId();

		Collection<SolicitacaoEpiItem> resultado = solicitacaoEpiItemDao.findAllEntregasBySolicitacaoEpi(id);

		assertEquals(1, resultado.size());
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
	
	public void testFindByIdProjection()
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
		
		assertEquals(epiSolicitacaoEpi, solicitacaoEpiItemDao.findByIdProjection(epiSolicitacaoEpi.getId()));
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

	public void setSolicitacaoEpiItemEntregaDao(SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao) {
		this.solicitacaoEpiItemEntregaDao = solicitacaoEpiItemEntregaDao;
	}
}