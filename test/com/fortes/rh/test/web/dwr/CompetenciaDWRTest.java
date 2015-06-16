package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.CompetenciaDWR;

public class CompetenciaDWRTest extends MockObjectTestCase{
	
	private CompetenciaDWR competenciaDWR;
	private Mock configuracaoNivelCompetenciaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		competenciaDWR = new CompetenciaDWR();

		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		competenciaDWR.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
	}

	public void testFindVinculosCompetenciaSoColaborador()
	{
		String dataString = "15/06/2015";
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		colaborador.setNome("Nome Colaborador");
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComColaborador").with(eq(faixa.getId()), ANYTHING).will(returnValue(colaboradores));
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComCandidato").with(eq(faixa.getId()), ANYTHING).will(returnValue(new ArrayList<Candidato>()));
		
		assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
				+ "</br></br>Colaborador(es): Nome Colaborador"
				+ "</br></br>Só é permitido criar histórico de competência para esta faixa salarial a partir do dia 16/06/2015", 
				competenciaDWR.findVinculosCompetencia(faixa.getId(), dataString));
	}
	
	public void testFindVinculosCompetenciaSoCandidato()
	{
		String dataString = "15/06/2015";
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		candidato.setNome("Nome Candidato");
		
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComCandidato").with(eq(faixa.getId()), ANYTHING).will(returnValue(candidatos));
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComColaborador").with(eq(faixa.getId()), ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
				+ "</br></br>Candidato(s): Nome Candidato"
				+ "</br></br>Só é permitido criar histórico de competência para esta faixa salarial a partir do dia 16/06/2015", 
				competenciaDWR.findVinculosCompetencia(faixa.getId(), dataString));
	}
	
	public void testFindVinculosCompetenciaSoAvaliacaoDesempenho()
	{
		String dataString = "15/06/2015";
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("Titulo Aval. Desempenho");

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setAvaliacaoDesempenhoTitulo(avaliacaoDesempenho.getTitulo());
		colaborador.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		colaborador.setNome("Nome Colaborador");
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComColaborador").with(eq(faixa.getId()), ANYTHING).will(returnValue(colaboradores));
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComCandidato").with(eq(faixa.getId()), ANYTHING).will(returnValue(new ArrayList<Candidato>()));
		
		assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
				+ "</br></br>Avaliação(ões): Titulo Aval. Desempenho"
				+ "</br></br>Só é permitido criar histórico de competência para esta faixa salarial a partir do dia 16/06/2015", 
				competenciaDWR.findVinculosCompetencia(faixa.getId(), dataString));
	}
	
	public void testFindVinculosCompetencia()
	{
		String dataString = "15/06/2015";
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("Titulo Aval. Desempenho");

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setAvaliacaoDesempenhoTitulo(avaliacaoDesempenho.getTitulo());
		colaborador.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		colaborador.setNome("Nome Colaborador");
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		colaborador2.setNome("Nome Colaborador 2");
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		colaboradores.add(colaborador2);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		candidato.setNome("Nome Candidato");
		
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComColaborador").with(eq(faixa.getId()), ANYTHING).will(returnValue(colaboradores));
		configuracaoNivelCompetenciaManager.expects(once()).method("findDependenciaComCandidato").with(eq(faixa.getId()), ANYTHING).will(returnValue(candidatos));
		
		assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
				+ "</br></br>Candidato(s): Nome Candidato"
				+ "</br></br>Colaborador(es): Nome Colaborador 2"
				+ "</br></br>Avaliação(ões): Titulo Aval. Desempenho"
				+ "</br></br>Só é permitido criar histórico de competência para esta faixa salarial a partir do dia 16/06/2015", 
				competenciaDWR.findVinculosCompetencia(faixa.getId(), dataString));
	}
}
