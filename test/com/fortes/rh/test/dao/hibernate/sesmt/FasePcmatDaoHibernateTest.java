package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.FasePcmatFactory;

public class FasePcmatDaoHibernateTest extends GenericDaoHibernateTest<FasePcmat>
{
	private FasePcmatDao fasePcmatDao;
	private EmpresaDao empresaDao;

	@Override
	public FasePcmat getEntity()
	{
		return FasePcmatFactory.getEntity();
	}

	@Override
	public GenericDao<FasePcmat> getGenericDao()
	{
		return fasePcmatDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresaDao.save(empresa1);
		empresaDao.save(empresa2);

		FasePcmat fasePcmat1 = FasePcmatFactory.getEntity();
		fasePcmat1.setEmpresa(empresa1);
		fasePcmatDao.save(fasePcmat1);
		
		FasePcmat fasePcmat2 = FasePcmatFactory.getEntity();
		fasePcmat2.setEmpresa(empresa2);
		fasePcmatDao.save(fasePcmat2);
		
		Collection<FasePcmat> retorno = fasePcmatDao.findAllSelect(null, empresa1.getId());
		assertEquals("pcmat1", 1, retorno.size());

	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	
	public void setFasePcmatDao(FasePcmatDao fasePcmatDao)
	{
		this.fasePcmatDao = fasePcmatDao;
	}
}