package com.fortes.rh.test.web.action.desenvolvimento;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorAvaliacaoPraticaEditAction;

public class ColaboradorAvaliacaoPraticaEditActionTest
{
	private ColaboradorAvaliacaoPraticaEditAction action;
	private CertificacaoManager certificacaoManager;
	private ColaboradorAvaliacaoPraticaManager manager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;

	@Before
	public void setUp() throws Exception
	{
		action = new ColaboradorAvaliacaoPraticaEditAction();
		action.setColaboradorAvaliacaoPratica(new ColaboradorAvaliacaoPratica());
		
		manager = mock(ColaboradorAvaliacaoPraticaManager.class);
		action.setColaboradorAvaliacaoPraticaManager(manager);
		
		certificacaoManager = mock(CertificacaoManager.class);
		action.setCertificacaoManager(certificacaoManager);

		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		action.setColaboradorTurmaManager(colaboradorTurmaManager);
		
		avaliacaoPraticaManager = mock(AvaliacaoPraticaManager.class);
		action.setAvaliacaoPraticaManager(avaliacaoPraticaManager);
		
		colaboradorAvaliacaoPraticaManager = mock(ColaboradorAvaliacaoPraticaManager.class);
		action.setColaboradorAvaliacaoPraticaManager(colaboradorAvaliacaoPraticaManager);
		
		colaboradorCertificacaoManager = mock(ColaboradorCertificacaoManager.class);
		action.setColaboradorCertificacaoManager(colaboradorCertificacaoManager);
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	@Test
	public void testBuscaColaboradores() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());		
		
		assertEquals("success", action.buscaColaboradores());
	}
	
	@Test
	public void testBuscaColaboradoresComCertificado() throws Exception
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		when(colaboradorCertificacaoManager.colaboradoresQueParticipamDaCertificacao(empresa.getId())).thenReturn(new ArrayList<Colaborador>());
		
		assertEquals("success", action.buscaColaboradores());
	}
	
	@Test
	public void testBuscaColaboradoresComCertificadoEColaborador() throws Exception
	{
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("João");
		action.setColaborador(colaborador);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		Collection<Colaborador> colaboradoresPermitidos = new ArrayList<Colaborador>();
		colaboradoresPermitidos.add(colaborador);
		
		Collection<Colaborador> colaboradoresNaCertificacao = new ArrayList<Colaborador>();
		colaboradoresNaCertificacao.add(colaborador);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(2L);
		avaliacaoPratica.setTitulo("Mega avaliação");
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		
		Curso curso = CursoFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setCurso(curso);
		Collection<ColaboradorTurma> colaboradorTurmas = Arrays.asList(colaboradorTurma);
		
		Collection<Curso> cursos =  new ArrayList<Curso>();
		cursos.add(curso);

		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		when(colaboradorCertificacaoManager.colaboradoresQueParticipamDaCertificacao(certificacao.getId())).thenReturn(colaboradoresNaCertificacao);
		when(colaboradorCertificacaoManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId())).thenReturn(colaboradorCertificacoes);
		when(certificacaoManager.findCursosByCertificacaoId(certificacao.getId())).thenReturn(cursos);
		when(colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacao.getId(), colaboradorCertificacao.getId(), colaborador.getId())).thenReturn(colaboradorTurmas);
		when(avaliacaoPraticaManager.findByCertificacaoId(certificacao.getId())).thenReturn(avaliacaoPraticas);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId(), null, true, true)).thenReturn(colaboradorAvaliacaoPraticas);
		when(colaboradorCertificacaoManager.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorCertificacao.getId(), colaborador.getId(), certificacao.getId())).thenReturn(colaboradorCertificacao);
		
		assertEquals("success", action.buscaColaboradores());
		assertEquals(1, action.getColaboradores().size());
		assertEquals("João", ((Colaborador) action.getColaboradores().toArray()[0]).getNome());
		assertEquals(1, action.getColaboradorAvaliacaoPraticas().size());
		assertEquals("Mega avaliação", ((ColaboradorAvaliacaoPratica)action.getColaboradorAvaliacaoPraticas().toArray()[0]).getAvaliacaoPratica().getTitulo());
	}

	@Test
	public void testInsertOrUpdateSemDataENota() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);

		ColaboradorAvaliacaoPratica colabAvPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity();
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPratica = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPratica.add(colabAvPratica1);
		
		action.setColaboradorAvaliacaoPraticas(colaboradorAvaliacoesPratica);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	@Test
	public void testInsertOrUpdateSemDataENotaComColabAvPraticaId() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		ColaboradorAvaliacaoPratica colabAvPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		ColaboradorAvaliacaoPratica colabAvPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(2L);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPratica = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPratica.add(colabAvPratica1);
		colaboradorAvaliacoesPratica.add(colabAvPratica2);
		
		action.setColaboradorAvaliacaoPraticas(colaboradorAvaliacoesPratica);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	@Test
	public void testInsertOrUpdateComDataENotaAprovado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		
		ColaboradorAvaliacaoPratica colabAvPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colabAvPratica.setAvaliacaoPratica(avaliacaoPratica);
		colabAvPratica.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		colabAvPratica.setNota(9.0);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPratica = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPratica.add(colabAvPratica);
		
		action.setColaboradorAvaliacaoPraticas(colaboradorAvaliacoesPratica);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		when(colaboradorCertificacaoManager.getMaiorDataDasTurmasDaCertificacao(colaboradorCertificacao.getId())).thenReturn(DateUtil.criarDataMesAno(1, 2, 2015));
		when(colaboradorCertificacaoManager.findById(colaboradorCertificacao.getId())).thenReturn(colaboradorCertificacao);
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	@Test
	public void testInsertOrUpdateComDataENotaReprovado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		
		ColaboradorAvaliacaoPratica colabAvPraticaReprovado = ColaboradorAvaliacaoPraticaFactory.getEntity(2L);
		colabAvPraticaReprovado.setAvaliacaoPratica(avaliacaoPratica);
		colabAvPraticaReprovado.setData(new Date());
		colabAvPraticaReprovado.setNota(7.0);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPratica = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPratica.add(colabAvPraticaReprovado);
		
		action.setColaboradorAvaliacaoPraticas(colaboradorAvaliacoesPratica);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	@Test
	public void testInsertOrUpdateComColaboradorCertificacaoNull() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		Colaborador colaboradorJoao = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaboradorJoao);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		action.setCertificacao(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		
		ColaboradorAvaliacaoPratica colabAvPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colabAvPratica.setAvaliacaoPratica(avaliacaoPratica);
		colabAvPratica.setData(new Date());
		colabAvPratica.setNota(9.0);
		
		ColaboradorAvaliacaoPratica colabAvPraticaReprovado = ColaboradorAvaliacaoPraticaFactory.getEntity(2L);
		colabAvPraticaReprovado.setAvaliacaoPratica(avaliacaoPratica);
		colabAvPraticaReprovado.setData(new Date());
		colabAvPraticaReprovado.setNota(7.0);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPratica = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPratica.add(colabAvPratica);
		colaboradorAvaliacoesPratica.add(colabAvPraticaReprovado);
		
		action.setColaboradorAvaliacaoPraticas(colaboradorAvaliacoesPratica);
		
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(empresa.getId())).thenReturn(new ArrayList<Certificacao>());
		when(colaboradorCertificacaoManager.certificaColaborador(null, colaboradorJoao.getId(), certificacao.getId(), certificacaoManager)).thenReturn(colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	private void mocksBuscaColaboradoresLote(Collection<AvaliacaoPratica> avaliacaoPraticas,Collection<Certificacao> certificacoes,Collection<ColaboradorCertificacao> colaboradorCertificacoes) 
	{
		when(certificacaoManager.findOsQuePossuemAvaliacaoPratica(action.getEmpresaSistema().getId())).thenReturn(certificacoes);
		when(avaliacaoPraticaManager.findByCertificacaoId(action.getCertificacao().getId())).thenReturn(avaliacaoPraticas);
		when(colaboradorCertificacaoManager.possuemAvaliacoesPraticasRealizadas(action.getCertificacao().getId())).thenReturn(colaboradorCertificacoes);
	}
	
	@Test
	public void testBuscaColaboradoresLote() throws Exception
	{
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		colaboradoraMaria.setNome("Maria");
		action.setColaborador(colaboradoraMaria);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);

		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao.setColaborador(colaboradoraMaria);
		colaboradorCertificacao.setCertificacao(certificacao);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(2L);
		avaliacaoPratica.setTitulo("Mega avaliação");
		action.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorCertificacoes);
		
		assertEquals("success", action.buscaColaboradoresLote());
		assertEquals(1, action.getColaboradorCertificacaos().size());
	}
	
	@Test
	public void testInsertOrUpdateLote() throws Exception
	{
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2000);
		Date data2 = DateUtil.criarDataMesAno(1, 1, 2001);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		action.setAvaliacaoPratica(avaliacaoPratica);
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		colaboradoraMaria.setNome("Maria");
		action.setColaborador(colaboradoraMaria);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);

		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity();
		colaboradorAvaliacaoPratica1.setData(data1);
		colaboradorAvaliacaoPratica1.setNota(10.0);
		colaboradorAvaliacaoPratica1.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica1);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity();
		colaboradorAvaliacaoPratica2.setData(data2);
		colaboradorAvaliacaoPratica2.setNota(10.0);
		colaboradorAvaliacaoPratica2.setAvaliacaoPratica(avaliacaoPratica);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao.setColaborador(colaboradoraMaria);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setColaboradorAvaliacaoPraticaAtual(colaboradorAvaliacaoPratica2);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		action.setColaboradorCertificacaos(colaboradorCertificacoes);
		
		when(avaliacaoPraticaManager.findById(avaliacaoPratica.getId())).thenReturn(avaliacaoPratica);
		when(colaboradorCertificacaoManager.getMaiorDataDasTurmasDaCertificacao(colaboradorCertificacao.getId())).thenReturn(data2);
		when(colaboradorCertificacaoManager.findById(colaboradorCertificacao.getId())).thenReturn(colaboradorCertificacao);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradoraMaria.getId(), certificacao.getId(), colaboradorCertificacao.getId(), null, true, true)).thenReturn(colaboradorAvaliacaoPraticas);
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}

	@Test
	public void testInsertOrUpdateLoteAprovadoCertificar() throws Exception
	{
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2000);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		action.setAvaliacaoPratica(avaliacaoPratica);
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		colaboradoraMaria.setNome("Maria");
		action.setColaborador(colaboradoraMaria);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);

		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setData(data1);
		colaboradorAvaliacaoPratica.setNota(6.0);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		colaboradorCertificacao.setColaborador(colaboradoraMaria);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setColaboradorAvaliacaoPraticaAtual(colaboradorAvaliacaoPratica);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		action.setColaboradorCertificacaos(colaboradorCertificacoes);
		
		when(avaliacaoPraticaManager.findById(avaliacaoPratica.getId())).thenReturn(avaliacaoPratica);
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes,	colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}
	
	public void testInsertOrUpdateLoteAprovadoDescertificar() throws Exception
	{
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2000);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		action.setAvaliacaoPratica(avaliacaoPratica);
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		colaboradoraMaria.setNome("Maria");
		action.setColaborador(colaboradoraMaria);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);

		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setData(data1);
		colaboradorAvaliacaoPratica.setNota(6.0);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao.setColaborador(colaboradoraMaria);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setColaboradorAvaliacaoPraticaAtual(colaboradorAvaliacaoPratica);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		action.setColaboradorCertificacaos(colaboradorCertificacoes);
		
		when(avaliacaoPraticaManager.findById(avaliacaoPratica.getId())).thenReturn(avaliacaoPratica);
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}
	
	public void testInsertOrUpdateLoteSemData() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		action.setAvaliacaoPratica(avaliacaoPratica);
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setNota(6.0);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao.setUltimaCertificacao(true);
		colaboradorCertificacao.setColaborador(colaboradoraMaria);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setColaboradorAvaliacaoPraticaAtual(colaboradorAvaliacaoPratica);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		action.setColaboradorCertificacaos(colaboradorCertificacoes);
		
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);
		
		when(avaliacaoPraticaManager.findById(avaliacaoPratica.getId())).thenReturn(avaliacaoPratica);
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}
	
	public void testInsertOrUpdateLoteSemDataESemColaboradorCertificacaoId() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(8.0);
		action.setAvaliacaoPratica(avaliacaoPratica);
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setNota(6.0);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		colaboradorCertificacao.setUltimaCertificacao(true);
		colaboradorCertificacao.setColaborador(colaboradoraMaria);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setColaboradorAvaliacaoPraticaAtual(colaboradorAvaliacaoPratica);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacoes.add(colaboradorCertificacao);
		action.setColaboradorCertificacaos(colaboradorCertificacoes);
		
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);
		
		when(avaliacaoPraticaManager.findById(avaliacaoPratica.getId())).thenReturn(avaliacaoPratica);
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}
}
