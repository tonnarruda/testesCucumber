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
import com.fortes.rh.util.DateUtil;

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
	
	public void testFindByData() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2011);
		Date data2 = DateUtil.criarDataMesAno(2, 2, 2011);
		Date data3 = DateUtil.criarDataMesAno(3, 3, 2011);
		
		ComposicaoSesmt c1 = ComposicaoSesmtFactory.getEntity();
		c1.setData(data1);
		c1.setEmpresa(empresa);
		composicaoSesmtDao.save(c1);

		ComposicaoSesmt c2 = ComposicaoSesmtFactory.getEntity();
		c2.setData(data2);
		c2.setEmpresa(empresa);
		composicaoSesmtDao.save(c2);

		ComposicaoSesmt c3 = ComposicaoSesmtFactory.getEntity();
		c3.setData(data3);
		c3.setEmpresa(empresa);
		composicaoSesmtDao.save(c3);
		
		assertNull(composicaoSesmtDao.findByData(empresa.getId(), DateUtil.criarDataMesAno(31, 12, 2010)));
		assertEquals(data1, (composicaoSesmtDao.findByData(empresa.getId(), DateUtil.criarDataMesAno(1, 2, 2011))).getData());
		assertEquals(data2, (composicaoSesmtDao.findByData(empresa.getId(), DateUtil.criarDataMesAno(2, 2, 2011))).getData());
		assertEquals(data2, (composicaoSesmtDao.findByData(empresa.getId(), DateUtil.criarDataMesAno(3, 2, 2011))).getData());
		assertEquals(data3, (composicaoSesmtDao.findByData(empresa.getId(), DateUtil.criarDataMesAno(3, 3, 2011))).getData());
		assertEquals(data3, (composicaoSesmtDao.findByData(empresa.getId(), DateUtil.criarDataMesAno(4, 3, 2011))).getData());
		
	}
	
	public void setComposicaoSesmtDao(ComposicaoSesmtDao composicaoSesmtDao)
	{
		this.composicaoSesmtDao = composicaoSesmtDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
