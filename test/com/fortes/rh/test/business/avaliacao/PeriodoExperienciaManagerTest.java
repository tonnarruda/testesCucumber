package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.FaixaPerformanceAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class PeriodoExperienciaManagerTest extends MockObjectTestCase
{
	private PeriodoExperienciaManagerImpl periodoExperienciaManager = new PeriodoExperienciaManagerImpl();
	private Mock periodoExperienciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        periodoExperienciaDao = new Mock(PeriodoExperienciaDao.class);
        periodoExperienciaManager.setDao((PeriodoExperienciaDao) periodoExperienciaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<PeriodoExperiencia> periodoExperiencias = PeriodoExperienciaFactory.getCollection(1L);

		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresaId), eq(false)).will(returnValue(periodoExperiencias));
		assertEquals(periodoExperiencias, periodoExperienciaManager.findAllSelect(empresaId, false));
	}
	
	public void testAgrupaFaixaAvaliacao()
	{
		Colaborador joao = new Colaborador("joao", "", "", null, 40.0, "");
		Colaborador toin = new Colaborador("toin", "", "", null, 70.0, "");
		Colaborador pedro = new Colaborador("pedro", "", "", null, 10.21, "");
		Colaborador maria = new Colaborador("maria", "", "", null, 50.0, "");
		Colaborador jurema = new Colaborador("jurema", "", "", null, 90.0, "");
		Colaborador babau = new Colaborador("babau", "", "", null, 100.0, "");
		
		Collection<Colaborador> colaboradores = Arrays.asList(joao, toin, pedro, maria, jurema, babau);
		
		String[] percentualInicial = new String[]{"10.21", "31", null, "51",  "", null, "11"};
		String[] percentualFinal = new String[]  {"30", "50", "",   "100", "", "",   "2", null};
		
		Collection<FaixaPerformanceAvaliacaoDesempenho> faixas = periodoExperienciaManager.agrupaFaixaAvaliacao(colaboradores, percentualInicial, percentualFinal);
		assertEquals(3, faixas.size());
		
		FaixaPerformanceAvaliacaoDesempenho faixa_10_30 = (FaixaPerformanceAvaliacaoDesempenho)faixas.toArray()[0];
		assertEquals("De 10.21% à 30.0%", faixa_10_30.getFaixa());
		assertEquals(1, faixa_10_30.getQuantidade());
		assertEquals("16.67", faixa_10_30.getPercentual());
		
		FaixaPerformanceAvaliacaoDesempenho faixa_31_50 = (FaixaPerformanceAvaliacaoDesempenho)faixas.toArray()[1];
		assertEquals("De 31.0% à 50.0%", faixa_31_50.getFaixa());
		assertEquals(2, faixa_31_50.getQuantidade());
		assertEquals("33.33", faixa_31_50.getPercentual());
		
		FaixaPerformanceAvaliacaoDesempenho faixa_51_100 = (FaixaPerformanceAvaliacaoDesempenho)faixas.toArray()[2];
		assertEquals("De 51.0% à 100.0%", faixa_51_100.getFaixa());
		assertEquals(3, faixa_51_100.getQuantidade());
		assertEquals("50.0", faixa_51_100.getPercentual());
	}
}
