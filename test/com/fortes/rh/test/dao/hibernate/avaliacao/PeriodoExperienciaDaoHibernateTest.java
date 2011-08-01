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
