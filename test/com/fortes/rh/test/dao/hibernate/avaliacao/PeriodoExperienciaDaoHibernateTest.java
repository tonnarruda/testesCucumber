package com.fortes.rh.test.dao.hibernate.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class PeriodoExperienciaDaoHibernateTest extends GenericDaoHibernateTest<PeriodoExperiencia>
{
	private PeriodoExperienciaDao periodoExperienciaDao;
	private EmpresaDao empresaDao;

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa = empresaDao.save(empresa);

		PeriodoExperiencia periodoExperiencia1 = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperiencia1 = periodoExperienciaDao.save(periodoExperiencia1);

		PeriodoExperiencia periodoExperiencia2 = PeriodoExperienciaFactory.getEntity(empresa, 60, true);
		periodoExperiencia2 = periodoExperienciaDao.save(periodoExperiencia2);
		
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaDao.findAllSelect(empresa.getId(), false, null); 
		assertEquals(2, periodoExperiencias.size());
		assertEquals("1o da ordem ascendente é 30", new Integer(30), ((PeriodoExperiencia) periodoExperiencias.toArray()[0]).getDias());

		periodoExperiencias = periodoExperienciaDao.findAllSelect(empresa.getId(), true, null); 
		assertEquals(2, periodoExperiencias.size());
		assertEquals("1o da ordem descendente é 60", new Integer(60), ((PeriodoExperiencia) periodoExperiencias.toArray()[0]).getDias());
	}
	
	public void testFindAllSelectDistinctDias()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		PeriodoExperiencia p1 = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		p1 = periodoExperienciaDao.save(p1);
		
		PeriodoExperiencia p2 = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		p2 = periodoExperienciaDao.save(p2);

		PeriodoExperiencia p3 = PeriodoExperienciaFactory.getEntity(empresa, 45, true);
		p3 = periodoExperienciaDao.save(p3);

		PeriodoExperiencia p4 = PeriodoExperienciaFactory.getEntity(empresa, 60, true);
		p4 = periodoExperienciaDao.save(p4);

		PeriodoExperiencia p5 = PeriodoExperienciaFactory.getEntity(empresa, 60, true);
		p5 = periodoExperienciaDao.save(p5);
		
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaDao.findAllSelectDistinctDias(empresa.getId()); 
		assertEquals(3, periodoExperiencias.size());
	}
	
	
	public void testFindByIdsOrder() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		PeriodoExperiencia p5 = PeriodoExperienciaFactory.getEntity(empresa, 180, true);
		p5 = periodoExperienciaDao.save(p5);
		
		PeriodoExperiencia p1 = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		p1 = periodoExperienciaDao.save(p1);
		
		PeriodoExperiencia p2 = PeriodoExperienciaFactory.getEntity(empresa, 45, true);
		p2 = periodoExperienciaDao.save(p2);

		PeriodoExperiencia p3 = PeriodoExperienciaFactory.getEntity(empresa, 90, true);
		p3 = periodoExperienciaDao.save(p3);

		PeriodoExperiencia p4 = PeriodoExperienciaFactory.getEntity(empresa, 120, true);
		p4 = periodoExperienciaDao.save(p4);

		Long[] periodoExperienciaIds = new Long[]{ p5.getId(), p3.getId(), p1.getId()};
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaDao.findByIdsOrderDias(periodoExperienciaIds);
		assertEquals(p1.getDias(), ((PeriodoExperiencia)periodoExperiencias.toArray()[0]).getDias());
	}
	
	public void testFindAtivos(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		PeriodoExperiencia p1 = PeriodoExperienciaFactory.getEntity(empresa, 180, true);
		periodoExperienciaDao.save(p1);
		
		PeriodoExperiencia p2 = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperienciaDao.save(p2);
		
		PeriodoExperiencia p3 = PeriodoExperienciaFactory.getEntity(empresa, 45, false);
		periodoExperienciaDao.save(p3);
		
		Collection<PeriodoExperiencia> periodosExperiencias = periodoExperienciaDao.findAllAtivos();
		assertTrue(periodosExperiencias.size() >= 2);
	}
	
	public PeriodoExperiencia getEntity()
	{
		return PeriodoExperienciaFactory.getEntity();
	}

	public GenericDao<PeriodoExperiencia> getGenericDao()
	{
		return periodoExperienciaDao;
	}

	public void setPeriodoExperienciaDao(PeriodoExperienciaDao periodoExperienciaDao)
	{
		this.periodoExperienciaDao = periodoExperienciaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
