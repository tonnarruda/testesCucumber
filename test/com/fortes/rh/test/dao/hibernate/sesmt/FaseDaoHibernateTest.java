package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.FaseDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.FaseFactory;

public class FaseDaoHibernateTest extends GenericDaoHibernateTest<Fase>
{
	private FaseDao faseDao;
	private EmpresaDao empresaDao;

	@Override
	public Fase getEntity()
	{
		return FaseFactory.getEntity();
	}

	@Override
	public GenericDao<Fase> getGenericDao()
	{
		return faseDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresaDao.save(empresa1);
		empresaDao.save(empresa2);

		Fase fase1 = FaseFactory.getEntity();
		fase1.setEmpresa(empresa1);
		faseDao.save(fase1);
		
		Fase fase2 = FaseFactory.getEntity();
		fase2.setEmpresa(empresa2);
		faseDao.save(fase2);
		
		Collection<Fase> retorno = faseDao.findAllSelect(null, empresa1.getId());
		assertEquals("pcmat1", 1, retorno.size());

	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	
	public void setFaseDao(FaseDao faseDao)
	{
		this.faseDao = faseDao;
	}
}