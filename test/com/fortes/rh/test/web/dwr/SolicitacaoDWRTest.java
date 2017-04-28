package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.SolicitacaoDWR;

public class SolicitacaoDWRTest
{
	private SolicitacaoDWR solicitacoaDWR;
	private SolicitacaoManager solicitacaoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;

	@Before
	public void setUp() throws Exception
	{
		solicitacoaDWR = new SolicitacaoDWR();

		solicitacaoManager = mock(SolicitacaoManager.class);
		solicitacoaDWR.setSolicitacaoManager(solicitacaoManager);
		
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		solicitacoaDWR.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);
		
		solicitacaoAvaliacaoManager = mock(SolicitacaoAvaliacaoManager.class);
		solicitacoaDWR.setSolicitacaoAvaliacaoManager(solicitacaoAvaliacaoManager);
	}
	
	@Test
	public void testGetByEmpresaEstabelecimentosAreas()
	{
		
		Date dataSol1 = DateUtil.criarAnoMesDia(2000, 12, 01);
		Date dataSol2 = new Date();
		Date dataSol3 = DateUtil.criarAnoMesDia(2001, 05, 01);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setNome("Área 1");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		sol1.setId(1L);
		sol1.setDescricao("Um");
		sol1.setData(dataSol1);
		sol1.setAreaOrganizacional(areaOrganizacional);
		sol1.setEstabelecimento(estabelecimento);
		sol1.setEmpresa(empresa);

		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		sol2.setId(2L);
		sol2.setDescricao("Dois");
		sol2.setData(dataSol2);
		sol2.setAreaOrganizacional(areaOrganizacional);
		sol2.setEstabelecimento(estabelecimento);
		sol2.setEmpresa(empresa);
		
		Solicitacao sol3 = SolicitacaoFactory.getSolicitacao();
		sol3.setId(3L);
		sol3.setDescricao("Três");
		sol3.setData(dataSol3);
		sol3.setAreaOrganizacional(areaOrganizacional);
		sol3.setEstabelecimento(estabelecimento);
		sol3.setEmpresa(empresa);

		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(sol1);
		solicitacoes.add(sol2);
		solicitacoes.add(sol3);

		when(solicitacaoManager.findByEmpresaEstabelecimentosAreas(empresa.getId(), new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()})).thenReturn(solicitacoes);
		
		Map<Long, String> result = solicitacoaDWR.getByEmpresaEstabelecimentosAreas(empresa.getId(), new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()});
		
		assertEquals("1 - Um -  - "+ DateUtil.formataDiaMesAno(dataSol1)+" - "+areaOrganizacional.getNome(), result.get(1L));	
		assertEquals("2 - Dois -  - "+ DateUtil.formataDiaMesAno(dataSol2)+" - "+areaOrganizacional.getNome(), result.get(2L));	
		assertEquals("3 - Três -  - "+ DateUtil.formataDiaMesAno(dataSol3)+" - "+areaOrganizacional.getNome(), result.get(3L));	
	}
	
	@Test
	public void testGetSolicitacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Date dataSol1 = DateUtil.criarAnoMesDia(2000, 12, 01);
		Date dataSol2 = new Date();
		Date dataSol3 = DateUtil.criarAnoMesDia(2001, 05, 01);
		
		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		sol1.setId(1L);
		sol1.setDescricao("Um");
		sol1.setData(dataSol1);
		sol1.setEmpresa(empresa);
		
		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		sol2.setId(2L);
		sol2.setDescricao("Dois");
		sol2.setData(dataSol2);
		sol2.setEmpresa(empresa);
		
		Solicitacao sol3 = SolicitacaoFactory.getSolicitacao();
		sol3.setId(3L);
		sol3.setDescricao("Três");
		sol3.setData(dataSol3);
		sol3.setEmpresa(empresa);
		
		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(sol1);
		solicitacoes.add(sol2);
		solicitacoes.add(sol3);
		
		when(solicitacaoManager.findSolicitacaoList(empresa.getId(), false, StatusAprovacaoSolicitacao.APROVADO, false)).thenReturn(solicitacoes);
		
		Map<Long, String> result = solicitacoaDWR.getSolicitacoes(empresa.getId());
		
		assertEquals("1 - Um -  - "+ DateUtil.formataDiaMesAno(dataSol1)+" - ", result.get(1L));	
		assertEquals("2 - Dois -  - "+ DateUtil.formataDiaMesAno(dataSol2)+" - ", result.get(2L));	
		assertEquals("3 - Três -  - "+ DateUtil.formataDiaMesAno(dataSol3)+" - ", result.get(3L));	
	}
	
	@Test
	public void testVerificaModeloAvaliacaoSolicitacaoDestinoExiste(){
		Long solicitacaoOrigemId = 1L;
		Long solicitacaoDestinoId = 2L;
		Long[] candidatosSolicitacaoIds = new Long[]{1L,2L};
			
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity(1L);
		avaliacao1.setTitulo("Avaliação 1");
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity(2L);
		avaliacao2.setTitulo("Avaliação 2");
		
		Collection<Avaliacao> avliAvaliacaos = Arrays.asList(avaliacao1, avaliacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao = new SolicitacaoAvaliacao(solicitacaoDestinoId, avaliacao2.getId(), avaliacao2.getTitulo());
		Collection<SolicitacaoAvaliacao> solicitacaoDestinoAvaliacaos = Arrays.asList(solicitacaoAvaliacao); 
		
		when(colaboradorQuestionarioManager.getAvaliacoesBySolicitacaoIdAndCandidatoSolicitacaoId(solicitacaoOrigemId, candidatosSolicitacaoIds)).thenReturn(avliAvaliacaos);
		when(solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacaoDestinoId, null)).thenReturn(solicitacaoDestinoAvaliacaos);
		
		String retorno = solicitacoaDWR.verificaModeloAvaliacaoSolicitacaoDestinoExiste(solicitacaoOrigemId, solicitacaoDestinoId, candidatosSolicitacaoIds);
		
		assertEquals("Avaliação 1", retorno);
	}
	
	@Test
	public void testVerificaModeloAvaliacaoSolicitacaoDestinoExisteSemModelo(){
		Long solicitacaoOrigemId = 1L;
		Long solicitacaoDestinoId = 2L;
		Long[] candidatosSolicitacaoIds = new Long[]{1L,2L};
			
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity(1L);
		Collection<Avaliacao> avliAvaliacaos = Arrays.asList(avaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao = new SolicitacaoAvaliacao(solicitacaoDestinoId, avaliacao1.getId(), avaliacao1.getTitulo());
		Collection<SolicitacaoAvaliacao> solicitacaoDestinoAvaliacaos = Arrays.asList(solicitacaoAvaliacao); 
		
		when(colaboradorQuestionarioManager.getAvaliacoesBySolicitacaoIdAndCandidatoSolicitacaoId(solicitacaoOrigemId, candidatosSolicitacaoIds)).thenReturn(avliAvaliacaos);
		when(solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacaoDestinoId, null)).thenReturn(solicitacaoDestinoAvaliacaos);
		
		String retorno = solicitacoaDWR.verificaModeloAvaliacaoSolicitacaoDestinoExiste(solicitacaoOrigemId, solicitacaoDestinoId, candidatosSolicitacaoIds);
		
		assertEquals("", retorno);
	}
}
	