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
		periodoExperiencia.setDescricao("");
		periodoExperiencia = periodoExperienciaDao.save(periodoExperiencia);

		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaDao.findAllSelect(empresa.getId(), false); 
		assertEquals(1, periodoExperiencias.size());
	}
	
	public void testFindAllSelectDistinctDias()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		PeriodoExperiencia p1 = PeriodoExperienciaFactory.getEntity();
		p1.setEmpresa(empresa);
		p1.setDias(30);
		p1 = periodoExperienciaDao.save(p1);
		
		PeriodoExperiencia p2 = PeriodoExperienciaFactory.getEntity();
		p2.setEmpresa(empresa);
		p2.setDias(30);
		p2 = periodoExperienciaDao.save(p2);

		PeriodoExperiencia p3 = PeriodoExperienciaFactory.getEntity();
		p3.setEmpresa(empresa);
		p3.setDias(45);
		p3 = periodoExperienciaDao.save(p3);

		PeriodoExperiencia p4 = PeriodoExperienciaFactory.getEntity();
		p4.setEmpresa(empresa);
		p4.setDias(60);
		p4 = periodoExperienciaDao.save(p4);

		PeriodoExperiencia p5 = PeriodoExperienciaFactory.getEntity();
		p5.setEmpresa(empresa);
		p5.setDias(60);
		p5 = periodoExperienciaDao.save(p5);
		
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaDao.findAllSelectDistinctDias(empresa.getId()); 
		assertEquals(3, periodoExperiencias.size());
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
