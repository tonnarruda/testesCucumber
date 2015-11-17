package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
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
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
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
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testBuscaColaboradores() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		certificacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Certificacao>()));
		
		assertEquals("success", action.buscaColaboradores());
	}
	
	public void testBuscaColaboradoresComCertificado() throws Exception
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		certificacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Certificacao>()));
		certificacaoManager.expects(once()).method("findColaboradoresNaCertificacoa").will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success", action.buscaColaboradores());
	}
	
	public void testBuscaColaboradoresComCertificadoEColaborador() throws Exception
	{
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
		
		certificacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Certificacao>()));
		certificacaoManager.expects(once()).method("findColaboradoresNaCertificacoa").will(returnValue(colaboradoresNaCertificacao));
		areaOrganizacionalManager.expects(once()).method("findAreasByUsuarioResponsavel").will(returnValue(new ArrayList<AreaOrganizacional>()));
		colaboradorManager.expects(once()).method("findByNomeCpfMatriculaComHistoricoComfirmado").will(returnValue(colaboradoresPermitidos));
		colaboradorTurmaManager.expects(once()).method("findByColaborador").will(returnValue(new ArrayList<ColaboradorTurma>()));
		avaliacaoPraticaManager.expects(once()).method("findByCertificacaoId").will(returnValue(avaliacaoPraticas));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("findByColaboradorIdAndCertificacaoId").will(returnValue(colaboradorAvaliacaoPraticas));
		
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
}
