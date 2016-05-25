package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.FaixaPerformanceAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class PeriodoExperienciaManagerTest extends MockObjectTestCaseManager<PeriodoExperienciaManagerImpl> implements TesteAutomaticoManager
{
	private Mock periodoExperienciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new PeriodoExperienciaManagerImpl();
        periodoExperienciaDao = new Mock(PeriodoExperienciaDao.class);
        manager.setDao((PeriodoExperienciaDao) periodoExperienciaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<PeriodoExperiencia> periodoExperiencias = PeriodoExperienciaFactory.getCollection(1L);

		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresaId), eq(false), eq(null)).will(returnValue(periodoExperiencias));
		assertEquals(periodoExperiencias, manager.findAllSelect(empresaId, false));
	}
	
	public void testAgrupaFaixaAvaliacao() throws Exception
	{
		Colaborador joao = new Colaborador("joao", "", "", null, 0.40, "");
		Colaborador toin = new Colaborador("toin", "", "", null, 0.70, "");
		Colaborador pedro = new Colaborador("pedro", "", "", null, 0.10, "");
		Colaborador maria = new Colaborador("maria", "", "", null, 0.50, "");
		Colaborador jurema = new Colaborador("jurema", "", "", null, 0.90, "");
		Colaborador babau = new Colaborador("babau", "", "", null, 1.0, "");
		
		Collection<Colaborador> colaboradores = Arrays.asList(joao, toin, pedro, maria, jurema, babau);
		
		String[] percentualInicial = new String[]{"10", "31", null, "51",  "", null, "11"};
		String[] percentualFinal = new String[]  {"30", "50", "",   "100", "", "",   "2", null};
		
		Collection<FaixaPerformanceAvaliacaoDesempenho> faixas = manager.agrupaFaixaAvaliacao(colaboradores, percentualInicial, percentualFinal);
		assertEquals(3, faixas.size());
		
		FaixaPerformanceAvaliacaoDesempenho faixa_10_30 = (FaixaPerformanceAvaliacaoDesempenho)faixas.toArray()[0];
		assertEquals("De 10.0% à 30.0%", faixa_10_30.getFaixa());
		assertEquals(1, faixa_10_30.getQuantidade());
		assertEquals("16.67", faixa_10_30.getPercentual());
		
		FaixaPerformanceAvaliacaoDesempenho faixa_31_50 = (FaixaPerformanceAvaliacaoDesempenho)faixas.toArray()[1];
		assertEquals("De 31.0% à 50.0%", faixa_31_50.getFaixa());
		assertEquals(1, faixa_31_50.getQuantidade());
		assertEquals("16.67", faixa_31_50.getPercentual());
		
		FaixaPerformanceAvaliacaoDesempenho faixa_51_100 = (FaixaPerformanceAvaliacaoDesempenho)faixas.toArray()[2];
		assertEquals("De 51.0% à 100.0%", faixa_51_100.getFaixa());
		assertEquals(2, faixa_51_100.getQuantidade());
		assertEquals("33.33", faixa_51_100.getPercentual());
	}
	
	public void testClonarPeriodoExperiencia(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);//Empresa para a qual o período de experiência será clonado
		
		PeriodoExperiencia experiencia = PeriodoExperienciaFactory.getEntity();
		experiencia.setDias(20);
		
		Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
		periodoExperiencias.add(experiencia);
		
		
		PeriodoExperiencia periodoExperienciaClone = PeriodoExperienciaFactory.getEntity(1L);
		periodoExperienciaClone.setEmpresaId(2L);
		periodoExperienciaClone.setDias(15);
	
		periodoExperienciaDao.expects(once()).method("findById").with(eq(periodoExperienciaClone.getId())).will(returnValue(periodoExperienciaClone));
		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresa.getId()), eq(false), eq(true)).will(returnValue(periodoExperiencias));
		periodoExperienciaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(periodoExperienciaClone));
		
		long empresaId = manager.clonarPeriodoExperiencia(periodoExperienciaClone.getId(), empresa).getEmpresa().getId();
		assertEquals(empresaId, 1L);
	}
	
	public void testClonarPeriodoExperienciaJaExistenteNaEmpresa(){
		Empresa empresaDeDestinoDoClone = EmpresaFactory.getEmpresa(1L); //Empresa para a qual o período de experiência será clonado
		
		PeriodoExperiencia periodoExperiencia1 = PeriodoExperienciaFactory.getEntity(100L);
		periodoExperiencia1.setEmpresa(empresaDeDestinoDoClone);
		periodoExperiencia1.setDias(15);
		
		Collection<PeriodoExperiencia> periodoExperienciasExistentes = new ArrayList<PeriodoExperiencia>();
		periodoExperienciasExistentes.add(periodoExperiencia1);
		
		
		PeriodoExperiencia periodoExperienciaClone = PeriodoExperienciaFactory.getEntity(1L);
		periodoExperienciaClone.setEmpresaId(2L);
		periodoExperienciaClone.setDias(15);
	
		periodoExperienciaDao.expects(once()).method("findById").with(eq(periodoExperienciaClone.getId())).will(returnValue(periodoExperienciaClone));
		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresaDeDestinoDoClone.getId()), eq(false), eq(true)).will(returnValue(periodoExperienciasExistentes));
		
		PeriodoExperiencia experienciaRetonada =  manager.clonarPeriodoExperiencia(periodoExperienciaClone.getId(), empresaDeDestinoDoClone);
		assertEquals(periodoExperiencia1.getId(), experienciaRetonada.getId());
		assertEquals(empresaDeDestinoDoClone.getId(), experienciaRetonada.getEmpresa().getId());
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(periodoExperienciaDao);
	}
}
