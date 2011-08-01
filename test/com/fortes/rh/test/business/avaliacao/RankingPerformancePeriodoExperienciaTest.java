package com.fortes.rh.test.business.avaliacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.RankingPerformancePeriodoExperiencia;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class RankingPerformancePeriodoExperienciaTest extends MockObjectTestCase
{
	private Mock periodoExperienciaDao;
	private RankingPerformancePeriodoExperiencia rankingPerformancePeriodoExperiencia = new RankingPerformancePeriodoExperiencia();
	
	protected void setUp() throws Exception
    {
        super.setUp();
        periodoExperienciaDao = new Mock(PeriodoExperienciaDao.class);
        rankingPerformancePeriodoExperiencia.setDao((PeriodoExperienciaDao) periodoExperienciaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Collection<PeriodoExperiencia> periodosExperiencias = Arrays.asList(PeriodoExperienciaFactory.getEntity(1L));

		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresa.getId()),eq(false)).will(returnValue(periodosExperiencias));
		assertEquals(1, rankingPerformancePeriodoExperiencia.findAllSelect(empresa.getId(), false).size());
	}
	
	public void testFindRodapeDiasDoPeriodoDeExperiencia()
	{
		PeriodoExperiencia periodo1 = PeriodoExperienciaFactory.getEntity(1L);
		periodo1.setDias(30);

		PeriodoExperiencia periodo2 = PeriodoExperienciaFactory.getEntity(2L);
		periodo2.setDias(60);
		
		Collection<PeriodoExperiencia> periodosExperiencias = Arrays.asList(periodo1, periodo2);
		
		assertEquals("Periodos de Experiências 30, 60", rankingPerformancePeriodoExperiencia.findRodapeDiasDoPeriodoDeExperiencia(periodosExperiencias));
	}
	
	public void testPopulaCheckBox()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		PeriodoExperiencia periodo1 = PeriodoExperienciaFactory.getEntity(1L);
		periodo1.setDias(30);

		PeriodoExperiencia periodo2 = PeriodoExperienciaFactory.getEntity(2L);
		periodo2.setDias(60);
		
		Collection<PeriodoExperiencia> periodosExperiencias = Arrays.asList(periodo1, periodo2);
		
		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresa.getId()),eq(false)).will(returnValue(periodosExperiencias));
		
		assertEquals(2, rankingPerformancePeriodoExperiencia.populaCheckBox(empresa.getId()).size());
	}
}