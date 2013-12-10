package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaFactory;

public class AreaVivenciaDaoHibernateTest extends GenericDaoHibernateTest<AreaVivencia>
{
	private AreaVivenciaDao areaVivenciaDao;
	private EmpresaDao empresaDao;

	public AreaVivencia getEntity()
	{
		return AreaVivenciaFactory.getEntity();
	}

	public GenericDao<AreaVivencia> getGenericDao()
	{
		return areaVivenciaDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		AreaVivencia areaVivencia1 = AreaVivenciaFactory.getEntity();
		areaVivencia1.setNome("Banheiro");
		areaVivencia1.setEmpresa(empresa1);
		areaVivenciaDao.save(areaVivencia1);
		
		AreaVivencia areaVivencia2 = AreaVivenciaFactory.getEntity();
		areaVivencia2.setNome("Area de Alimentação");
		areaVivencia2.setEmpresa(empresa1);
		areaVivenciaDao.save(areaVivencia2);
		
		AreaVivencia areaVivencia3 = AreaVivenciaFactory.getEntity();
		areaVivencia3.setNome("Copa");
		areaVivencia3.setEmpresa(empresa2);
		areaVivenciaDao.save(areaVivencia3);
		
		Collection<AreaVivencia> areaVivencias = areaVivenciaDao.findAllSelect("banheiro", empresa1.getId());
		
		assertEquals("Nome e empresa", areaVivencia1, (AreaVivencia)areaVivencias.toArray()[0]);
		
		areaVivencias = areaVivenciaDao.findAllSelect(null, empresa1.getId());
		
		assertEquals(2, areaVivencias.size());
		assertEquals("Empresa", areaVivencia2, (AreaVivencia)areaVivencias.toArray()[0]);
	}
	
	public void testFindByIdProjection()
	{
		AreaVivencia areaVivencia1 = AreaVivenciaFactory.getEntity();
		areaVivenciaDao.save(areaVivencia1);
		
		AreaVivencia areaVivencia2 = AreaVivenciaFactory.getEntity();
		areaVivenciaDao.save(areaVivencia2);
		
		AreaVivencia areaVivenciaRetorno = areaVivenciaDao.findByIdProjection(areaVivencia2.getId());
		
		assertEquals(areaVivencia2, areaVivenciaRetorno);
	}
	
	public void setAreaVivenciaDao(AreaVivenciaDao areaVivenciaDao)
	{
		this.areaVivenciaDao = areaVivenciaDao;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}
