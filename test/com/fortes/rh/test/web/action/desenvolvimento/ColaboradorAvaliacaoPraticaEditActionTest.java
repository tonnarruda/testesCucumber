package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.geral.AreaOrganizacional;
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
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorAvaliacaoPraticaEditAction;

public class ColaboradorAvaliacaoPraticaEditActionTest extends MockObjectTestCase
{
	private ColaboradorAvaliacaoPraticaEditAction action;
	private Mock certificacaoManager;
	private Mock manager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	private Mock colaboradorTurmaManager;
	private Mock avaliacaoPraticaManager;
	private Mock colaboradorAvaliacaoPraticaManager;
	private Mock colaboradorCertificacaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new ColaboradorAvaliacaoPraticaEditAction();
		action.setColaboradorAvaliacaoPratica(new ColaboradorAvaliacaoPratica());
		
		manager = new Mock(ColaboradorAvaliacaoPraticaManager.class);
		action.setColaboradorAvaliacaoPraticaManager((ColaboradorAvaliacaoPraticaManager) manager.proxy());
		
		certificacaoManager = new Mock(CertificacaoManager.class);
		action.setCertificacaoManager((CertificacaoManager) certificacaoManager.proxy());
		
		areaOrganizacionalManager  = new Mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
		action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
		
		avaliacaoPraticaManager = new Mock(AvaliacaoPraticaManager.class);
		action.setAvaliacaoPraticaManager((AvaliacaoPraticaManager) avaliacaoPraticaManager.proxy());
		
		colaboradorAvaliacaoPraticaManager = new Mock(ColaboradorAvaliacaoPraticaManager.class);
		action.setColaboradorAvaliacaoPraticaManager((ColaboradorAvaliacaoPraticaManager) colaboradorAvaliacaoPraticaManager.proxy());
		
		colaboradorCertificacaoManager = new Mock(ColaboradorCertificacaoManager.class);
		action.setColaboradorCertificacaoManager((ColaboradorCertificacaoManager) colaboradorCertificacaoManager.proxy());
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testBuscaColaboradores() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		
		assertEquals("success", action.buscaColaboradores());
	}
	
	public void testBuscaColaboradoresComCertificado() throws Exception
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		colaboradorCertificacaoManager.expects(once()).method("colaboradoresQueParticipamDaCertificacao").will(returnValue(new ArrayList<Colaborador>()));
		areaOrganizacionalManager.expects(once()).method("findAreasByUsuarioResponsavel").will(returnValue(new ArrayList<AreaOrganizacional>()));
		colaboradorManager.expects(once()).method("findByNomeCpfMatriculaComHistoricoComfirmado").will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success", action.buscaColaboradores());
	}
	
	public void testBuscaColaboradoresComCertificadoEColaborador() throws Exception
	{
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		action.setColaboradorCertificacao(colaboradorCertificacao);
		
		Colaborador colaboradoraMaria = ColaboradorFactory.getEntity(1L);
		colaboradoraMaria.setNome("Maria");
		action.setColaborador(colaboradoraMaria);
		
		Colaborador colaboradorJoao = ColaboradorFactory.getEntity(1L);
		colaboradorJoao.setNome("João");
		action.setColaborador(colaboradorJoao);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		Collection<Colaborador> colaboradoresPermitidos = new ArrayList<Colaborador>();
		colaboradoresPermitidos.add(colaboradorJoao);
		
		Collection<Colaborador> colaboradoresNaCertificacao = new ArrayList<Colaborador>();
		colaboradoresNaCertificacao.add(colaboradorJoao);
		colaboradoresNaCertificacao.add(colaboradoraMaria);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(2L);
		avaliacaoPratica.setTitulo("Mega avaliação");
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = Arrays.asList(colaboradorTurma);
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		colaboradorCertificacaoManager.expects(once()).method("colaboradoresQueParticipamDaCertificacao").will(returnValue(colaboradoresNaCertificacao));
		areaOrganizacionalManager.expects(once()).method("findAreasByUsuarioResponsavel").will(returnValue(new ArrayList<AreaOrganizacional>()));
		colaboradorManager.expects(once()).method("findByNomeCpfMatriculaComHistoricoComfirmado").will(returnValue(colaboradoresPermitidos));
		colaboradorCertificacaoManager.expects(once()).method("findByColaboradorIdAndCertificacaoId").will(returnValue(new ArrayList<ColaboradorCertificacao>()));
		avaliacaoPraticaManager.expects(once()).method("findByCertificacaoId").will(returnValue(avaliacaoPraticas));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("findByColaboradorIdAndCertificacaoId").will(returnValue(colaboradorAvaliacaoPraticas));
		colaboradorTurmaManager.expects(once()).method("findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId").will(returnValue(colaboradorTurmas));
		colaboradorCertificacaoManager.expects(once()).method("findColaboradorCertificadoInfomandoSeEUltimaCertificacao").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorCertificacao));
		
		assertEquals("success", action.buscaColaboradores());
		assertEquals(2, action.getColaboradores().size());
		assertEquals("João", ((Colaborador) action.getColaboradores().toArray()[0]).getNome());
		assertEquals(1, action.getColaboradorAvaliacaoPraticas().size());
		assertEquals("Mega avaliação", ((ColaboradorAvaliacaoPratica)action.getColaboradorAvaliacaoPraticas().toArray()[0]).getAvaliacaoPratica().getTitulo());
	}

	public void testGetSet() throws Exception
	{
		action.setColaboradorAvaliacaoPratica(null);

		assertNotNull(action.getColaboradorAvaliacaoPratica());
		assertTrue(action.getColaboradorAvaliacaoPratica() instanceof ColaboradorAvaliacaoPratica);
	}
	
	public void testInsertOrUpdateSemDataENota() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);

		ColaboradorAvaliacaoPratica colabAvPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity();
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPratica = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPratica.add(colabAvPratica1);
		
		action.setColaboradorAvaliacaoPraticas(colaboradorAvaliacoesPratica);
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		
		assertEquals("success", action.insertOrUpdate());
	}
	
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
		
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaborador");
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaborador");
		colaboradorAvaliacaoPraticaManager.expects(once()).method("removeByColaboradorCertificacaoId");
		colaboradorAvaliacaoPraticaManager.expects(once()).method("removeByColaboradorCertificacaoId");
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		
		assertEquals("success", action.insertOrUpdate());
	}
	
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
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		colaboradorCertificacaoManager.expects(once()).method("getMaiorDataDasTurmasDaCertificacao").with(eq(colaboradorCertificacao.getId())).will(returnValue(DateUtil.criarDataMesAno(1, 2, 2015)));
		colaboradorCertificacaoManager.expects(once()).method("findById").with(eq(colaboradorCertificacao.getId())).will(returnValue(colaboradorCertificacao));
		colaboradorCertificacaoManager.expects(once()).method("update").with(eq(colaboradorCertificacao));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("update");
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	
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
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaborador").with(eq(colaboradorCertificacao.getId()));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("update");
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	public void testInsertOrUpdateComColaboradorCertificacaoNull() throws Exception
	{
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
		
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(new ArrayList<Certificacao>()));
		
		colaboradorAvaliacaoPraticaManager.expects(atLeastOnce()).method("update");
		colaboradorAvaliacaoPraticaManager.expects(atLeastOnce()).method("saveOrUpdate");
		colaboradorCertificacaoManager.expects(once()).method("certificaColaborador").withAnyArguments().will(returnValue(colaboradorCertificacoes));
		colaboradorCertificacaoManager.expects(once()).method("certificaColaborador").withAnyArguments().will(returnValue(colaboradorCertificacoes));
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	private void mocksBuscaColaboradoresLote(Collection<AvaliacaoPratica> avaliacaoPraticas,Collection<Certificacao> certificacoes,Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas,Collection<ColaboradorCertificacao> colaboradorCertificacoes) 
	{
		certificacaoManager.expects(once()).method("findOsQuePossuemAvaliacaoPratica").will(returnValue(certificacoes));
		avaliacaoPraticaManager.expects(once()).method("findByCertificacaoId").will(returnValue(avaliacaoPraticas));
		colaboradorCertificacaoManager.expects(once()).method("populaAvaliaçõesPraticasRealizadas").will(returnValue(colaboradorCertificacoes));
	}
	
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
		mockFindColaboradoresIdsPermitidosNaCertificacao();
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes,	colaboradorAvaliacaoPraticas, colaboradorCertificacoes);
		
		assertEquals("success", action.buscaColaboradoresLote());
		assertEquals(1, action.getColaboradorCertificacaos().size());
	}
	
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
		
		avaliacaoPraticaManager.expects(once()).method("findById").will(returnValue(avaliacaoPratica));
		colaboradorCertificacaoManager.expects(once()).method("getMaiorDataDasTurmasDaCertificacao").will(returnValue(data2));
		colaboradorCertificacaoManager.expects(once()).method("findById").will(returnValue(colaboradorCertificacao));
		colaboradorCertificacaoManager.expects(once()).method("update");
		colaboradorAvaliacaoPraticaManager.expects(once()).method("findByColaboradorIdAndCertificacaoId").will(returnValue(colaboradorAvaliacaoPraticas));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("save");
		mockFindColaboradoresIdsPermitidosNaCertificacao();
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes,	colaboradorAvaliacaoPraticas, colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}

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
		
		avaliacaoPraticaManager.expects(once()).method("findById").will(returnValue(avaliacaoPratica));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("update");
		colaboradorCertificacaoManager.expects(once()).method("certificaColaborador").will(returnValue(colaboradorCertificacoes));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("update");
		mockFindColaboradoresIdsPermitidosNaCertificacao();
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes,	colaboradorAvaliacaoPraticas, colaboradorCertificacoes);
		
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
		
		avaliacaoPraticaManager.expects(once()).method("findById").will(returnValue(avaliacaoPratica));
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaborador");
		colaboradorAvaliacaoPraticaManager.expects(once()).method("update");
		mockFindColaboradoresIdsPermitidosNaCertificacao();
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorAvaliacaoPraticas, colaboradorCertificacoes);
		
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
		
		avaliacaoPraticaManager.expects(once()).method("findById").will(returnValue(avaliacaoPratica));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("removeByColaboradorCertificacaoId");
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaborador");
		mockFindColaboradoresIdsPermitidosNaCertificacao();
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorAvaliacaoPraticas, colaboradorCertificacoes);
		
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
		
		avaliacaoPraticaManager.expects(once()).method("findById").will(returnValue(avaliacaoPratica));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("remove");
		mockFindColaboradoresIdsPermitidosNaCertificacao();
		
		mocksBuscaColaboradoresLote(avaliacaoPraticas, certificacoes, colaboradorAvaliacaoPraticas, colaboradorCertificacoes);
		
		assertEquals("success", action.insertOrUpdateLote());
	}
	
	private void mockFindColaboradoresIdsPermitidosNaCertificacao(){
		areaOrganizacionalManager.expects(once()).method("findAreasByUsuarioResponsavel").will(returnValue(new ArrayList<AreaOrganizacional>()));
		colaboradorManager.expects(once()).method("findByNomeCpfMatriculaComHistoricoComfirmado").will(returnValue(new ArrayList<Colaborador>()));
	}
}
