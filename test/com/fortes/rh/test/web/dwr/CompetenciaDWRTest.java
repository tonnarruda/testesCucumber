package com.fortes.rh.test.web.dwr;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.CompetenciasConsideradas;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.CompetenciaDWR;

public class CompetenciaDWRTest {
	
	private CompetenciaDWR competenciaDWR;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private CompetenciaManager competenciaManager;

	@Before
	public void setUp() throws Exception
	{
		competenciaDWR = new CompetenciaDWR();

		configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);
		competenciaManager = mock(CompetenciaManager.class);
		
		competenciaDWR.setConfiguracaoNivelCompetenciaManager(configuracaoNivelCompetenciaManager);
		competenciaDWR.setCompetenciaManager(competenciaManager);
	}

	@Test
	public void testFindVinculosCompetenciaSoColaborador()
	{
		String dataString = "15/06/2015";
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAtualizacao(DateUtil.criarDataDiaMesAno(dataString));
		colaborador.setNome("Nome Colaborador");
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		when(configuracaoNivelCompetenciaManager.findDependenciaComColaborador(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(colaboradores);
		when(configuracaoNivelCompetenciaManager.findDependenciaComCandidato(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(new ArrayList<Candidato>() );
		
		Assert.assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
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
		
		when(configuracaoNivelCompetenciaManager.findDependenciaComCandidato(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(candidatos);
		when(configuracaoNivelCompetenciaManager.findDependenciaComColaborador(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(new ArrayList<Colaborador>());
		
		Assert.assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
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
		
		when(configuracaoNivelCompetenciaManager.findDependenciaComColaborador(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(colaboradores);
		when(configuracaoNivelCompetenciaManager.findDependenciaComCandidato(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(new ArrayList<Candidato>() );
		
		Assert.assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
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
		
		when(configuracaoNivelCompetenciaManager.findDependenciaComColaborador(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(colaboradores);
		when(configuracaoNivelCompetenciaManager.findDependenciaComCandidato(faixa.getId(), DateUtil.criarDataDiaMesAno(dataString))).thenReturn(candidatos);
		
		Assert.assertEquals("O histórico de competência da faixa salarial não pode ser criado nesta data (15/06/2015), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:"
				+ "</br></br>Candidato(s): Nome Candidato"
				+ "</br></br>Colaborador(es): Nome Colaborador 2"
				+ "</br></br>Avaliação(ões): Titulo Aval. Desempenho"
				+ "</br></br>Só é permitido criar histórico de competência para esta faixa salarial a partir do dia 16/06/2015", 
				competenciaDWR.findVinculosCompetencia(faixa.getId(), dataString));
	}
	
	@Test
	public void testGetByAvaliacoes() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] avaliacoesIds = new Long[]{1L};

		Competencia competencia1 = new Competencia();
		competencia1.setId(1L);
		competencia1.setNome("Comp A");
		
		Competencia competencia2 = new Competencia();
		competencia2.setId(2L);
		competencia2.setNome("Comp B");
		
		Collection<Competencia> competencias = Arrays.asList(competencia1, competencia2);
		
		when(competenciaManager.findByAvaliacoesDesempenho(empresa.getId(), avaliacoesIds , CompetenciasConsideradas.TODAS)).thenReturn(competencias);
		
		Map<Object, Object> mapCompetencias = competenciaDWR.getByAvaliacoes(empresa.getId(), avaliacoesIds, CompetenciasConsideradas.TODAS);

		Assert.assertEquals(competencias.size(), mapCompetencias.size());
		Assert.assertEquals(competencia1.getNome(), mapCompetencias.get(competencia1.getId()));
		Assert.assertEquals(competencia2.getNome(), mapCompetencias.get(competencia2.getId()));
	}
	
}
