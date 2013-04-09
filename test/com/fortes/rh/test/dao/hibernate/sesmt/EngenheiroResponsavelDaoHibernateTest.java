package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class EngenheiroResponsavelDaoHibernateTest extends GenericDaoHibernateTest<EngenheiroResponsavel>
{
	EngenheiroResponsavelDao engenheiroResponsavelDao = null;
	EmpresaDao empresaDao;

	@Override
	public EngenheiroResponsavel getEntity()
	{
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setNome("engenheiroResponsavel");
		return engenheiroResponsavel;
	}

	@Override
	public GenericDao<EngenheiroResponsavel> getGenericDao()
	{
		return engenheiroResponsavelDao;
	}

	public void setEngenheiroResponsavelDao(EngenheiroResponsavelDao engenheiroResponsavelDao)
	{
		this.engenheiroResponsavelDao = engenheiroResponsavelDao;
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setEmpresa(empresa);
		engenheiroResponsavel = engenheiroResponsavelDao.save(engenheiroResponsavel);

		EngenheiroResponsavel engenheiroResponsavelRetorno = engenheiroResponsavelDao.findByIdProjection(engenheiroResponsavel.getId());

		assertEquals(engenheiroResponsavel.getId(), engenheiroResponsavelRetorno.getId());
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setEmpresa(empresa);
		engenheiroResponsavel = engenheiroResponsavelDao.save(engenheiroResponsavel);
		
		EngenheiroResponsavel engenheiroResponsavel2 = new EngenheiroResponsavel();
		engenheiroResponsavel2.setEmpresa(empresa);
		engenheiroResponsavel2 = engenheiroResponsavelDao.save(engenheiroResponsavel2);
		
		Collection<EngenheiroResponsavel> result = engenheiroResponsavelDao.findAllByEmpresa(empresa.getId());
		
		assertEquals(2, result.size());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}