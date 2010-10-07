package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class RiscoDaoHibernateTest extends GenericDaoHibernateTest<Risco>
{
	private RiscoDao riscoDao;
	private EpiDao epiDao;
	private EmpresaDao empresaDao;

	public Risco getEntity()
	{
		return RiscoFactory.getEntity();
	}

	@SuppressWarnings("unchecked")
	public void testFindEpisByRisco()
	{
		Empresa empresa = null;

		Epi e1 = new Epi();
		e1.setNome("nome da epi");
		e1.setFabricante("TecToy");
		e1.setEmpresa(empresa);

		Epi e2 = new Epi();
		e2.setNome("nome da epi");
		e2.setFabricante("TecToy");
		e2.setEmpresa(empresa);

		e1 = epiDao.save(e1);
		e2 = epiDao.save(e2);

		Collection<Epi> episTest = new ArrayList<Epi>();
		episTest.add(e2);
		episTest.add(e1);

		Risco risco = new Risco();
		risco.setEpis(episTest);

		risco = riscoDao.save(risco);

		List<Epi> epis = riscoDao.findEpisByRisco(risco.getId());

		assertEquals(episTest.size(), epis.size());
	}
	
	public void testFindAllSelect()
	{
		
		Epi epi = EpiFactory.getEntity();
		epiDao.save(epi);
		
		Epi epi2 = EpiFactory.getEntity();
		epiDao.save(epi2);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi);
		epis.add(epi2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Risco risco = RiscoFactory.getEntity();
		risco.setEmpresa(empresa);
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		Collection<Risco> colecao = riscoDao.findAllSelect(empresa.getId());
		
		assertEquals(1, colecao.size());
	}

	public GenericDao<Risco> getGenericDao()
	{
		return riscoDao;
	}

	public void setRiscoDao(RiscoDao riscoDao)
	{
		this.riscoDao = riscoDao;
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}