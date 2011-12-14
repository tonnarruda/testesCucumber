package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ComposicaoSesmtDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComposicaoSesmtFactory;

public class ComposicaoSesmtDaoHibernateTest extends GenericDaoHibernateTest<ComposicaoSesmt>
{
	private ComposicaoSesmtDao composicaoSesmtDao;
	private EmpresaDao empresaDao;

	@Override
	public ComposicaoSesmt getEntity()
	{
		return ComposicaoSesmtFactory.getEntity();
	}

	@Override
	public GenericDao<ComposicaoSesmt> getGenericDao()
	{
		return composicaoSesmtDao;
	}

	public void testFindAllSelect() {
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity();
		composicaoSesmt.setData(new Date());
		composicaoSesmt.setEmpresa(empresa);
		composicaoSesmtDao.save(composicaoSesmt);
		
		assertEquals(1, composicaoSesmtDao.findAllSelect(empresa.getId()).size());
	}
	
	public void testFindByIdProjection() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity();
		composicaoSesmt.setData(new Date());
		composicaoSesmt.setEmpresa(empresa);
		composicaoSesmtDao.save(composicaoSesmt);
		
		assertEquals(composicaoSesmt.getId(), (composicaoSesmtDao.findByIdProjection(composicaoSesmt.getId())).getId());
		
	}
	
	public void setComposicaoSesmtDao(ComposicaoSesmtDao composicaoSesmtDao)
	{
		this.composicaoSesmtDao = composicaoSesmtDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
