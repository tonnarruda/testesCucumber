package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;
import com.fortes.rh.util.DateUtil;

public class DNTDaoHibernateTest extends GenericDaoHibernateTest<DNT>
{
	private DNTDao dntDao;
	private EmpresaDao empresaDao;

	public DNT getEntity()
	{
		return DntFactory.getEntity();
	}

	public GenericDao<DNT> getGenericDao()
	{
		return dntDao;
	}

	public void setDNTDao(DNTDao dntDao)
	{
		this.dntDao = dntDao;
	}
	
	public void testGetUltimaDNT()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		DNT dntAnterior = DntFactory.getEntity();
		dntAnterior.setEmpresa(empresa);
		dntAnterior.setData(DateUtil.criarDataMesAno(1, 1, 2000));
		dntAnterior = dntDao.save(dntAnterior);
		
		DNT dntUltima = DntFactory.getEntity();
		dntUltima.setEmpresa(empresa);
		dntUltima.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		dntUltima = dntDao.save(dntUltima);
		
		assertEquals(dntUltima, dntDao.getUltimaDNT(empresa.getId()));
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}