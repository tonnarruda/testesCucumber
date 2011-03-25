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

		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity();
		periodoExperiencia.setEmpresa(empresa);
		periodoExperiencia = periodoExperienciaDao.save(periodoExperiencia);

		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaDao.findAllSelect(empresa.getId(), false); 
		assertEquals(1, periodoExperiencias.size());
	}
	
	public void testFindPeriodoAnterior()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa = empresaDao.save(empresa);
		
		
		PeriodoExperiencia periodoExperiencia = new PeriodoExperiencia();
		periodoExperiencia.setEmpresa(empresa);
		periodoExperiencia.setDias(60);
		periodoExperiencia = periodoExperienciaDao.save(periodoExperiencia);
		
		PeriodoExperiencia periodoExperiencia2 = PeriodoExperienciaFactory.getEntity();
		periodoExperiencia2.setEmpresa(empresa);
		periodoExperiencia2.setDias(30);
		periodoExperiencia2 = periodoExperienciaDao.save(periodoExperiencia2);
		
		Integer dias = periodoExperienciaDao.findPeriodoAnterior(empresa.getId(), 60); 
		assertEquals(30, dias.intValue());
	}
	
	public void testFindPeriodoSugerido()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa = empresaDao.save(empresa);
		
		PeriodoExperiencia periodoExperiencia = new PeriodoExperiencia();
		periodoExperiencia.setEmpresa(empresa);
		periodoExperiencia.setDias(60);
		periodoExperiencia = periodoExperienciaDao.save(periodoExperiencia);
		
		PeriodoExperiencia periodoExperiencia2 = PeriodoExperienciaFactory.getEntity();
		periodoExperiencia2.setEmpresa(empresa);
		periodoExperiencia2.setDias(30);
		periodoExperiencia2 = periodoExperienciaDao.save(periodoExperiencia2);
		
		Integer dias = periodoExperienciaDao.findPeriodoSugerido(empresa.getId(), 30); 
		assertEquals(60, dias.intValue());
	}
	
	
	
	@Override
	public PeriodoExperiencia getEntity()
	{
		return PeriodoExperienciaFactory.getEntity();
	}

	@Override
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
