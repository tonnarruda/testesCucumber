package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.desenvolvimento.CertificacaoEditAction;
import com.fortes.web.tags.CheckBox;

public class CertificacaoEditActionTest extends MockObjectTestCase
{
	private CertificacaoEditAction action;
	private Mock certificacaoManager;
	private Mock cursoManager;
	private Mock avaliacaoPraticaManager;
	private Mock colaboradorCertificacaoManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new CertificacaoEditAction();

		certificacaoManager = mock(CertificacaoManager.class);
        action.setCertificacaoManager((CertificacaoManager) certificacaoManager.proxy());
        
        cursoManager = mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());

        avaliacaoPraticaManager = mock(AvaliacaoPraticaManager.class);
        action.setAvaliacaoPraticaManager((AvaliacaoPraticaManager) avaliacaoPraticaManager.proxy());

        colaboradorCertificacaoManager = mock(ColaboradorCertificacaoManager.class);
        action.setColaboradorCertificacaoManager((ColaboradorCertificacaoManager) colaboradorCertificacaoManager.proxy());
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}
	
	public void testPrepareInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		certificacaoManager.expects(once()).method("findById").with(eq(certificacao.getId())).will(returnValue(certificacao));
		cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(eq(new Long[] {empresa.getId()})).will(returnValue(new ArrayList<Curso>()));
		avaliacaoPraticaManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlarVencimentoCertificacaoPor(2);
		action.setEmpresaSistema(empresa);
		
		Curso trepa = CursoFactory.getEntity(2L);
		trepa.setNome("trepa");

		Curso come = CursoFactory.getEntity(2L);
		trepa.setNome("come");
		
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(trepa);
		cursos.add(come);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		certificacao.setCursos(cursos);
		action.setCertificacao(certificacao);
		
		certificacaoManager.expects(once()).method("verificaEmpresa").with(eq(certificacao.getId()), eq(empresa.getId())).will(returnValue(true));
		certificacaoManager.expects(once()).method("findById").with(eq(certificacao.getId())).will(returnValue(certificacao));
		cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(eq(new Long[] {empresa.getId()})).will(returnValue(new ArrayList<Curso>()));
		certificacaoManager.expects(once()).method("findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito").withAnyArguments().will(returnValue(new ArrayList<Certificacao>()));
		avaliacaoPraticaManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		colaboradorCertificacaoManager.expects(once()).method("findByColaboradorIdAndCertificacaoId").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		
		assertEquals("success", action.prepareUpdate());
	}

	public void testPrepareUpdateSemCertificacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		assertEquals("success", action.prepareUpdate());
	}
	
	public void testInsert() throws Exception
	{
		String[] cursosCheck = new String[]{"1", "2"};
		action.setCursosCheck(cursosCheck);
				
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		certificacaoManager.expects(once()).method("save").with(eq(certificacao));
		
		assertEquals("success", action.insert());
	}
	
	public void testUpdate() throws Exception
	{
		String[] cursosCheck = new String[]{"1", "2"};
		action.setCursosCheck(cursosCheck);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		certificacaoManager.expects(once()).method("update").with(eq(certificacao));
		
		assertEquals("success", action.update());
	}
	
	public void testPrepareImprimirCertificadosVencidosAVencer() throws Exception
	{
		mocksPrepareImprimir();
		assertEquals("success", action.prepareImprimirCertificadosVencidosAVencer());
	}

	private void mocksPrepareImprimir() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		certificacaoManager.expects(once()).method("populaCheckBoxSemPeriodicidade").with(eq(empresa.getId())).will(returnValue(new ArrayList<CheckBox>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(empresa.getId())).will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(empresa.getId())).will(returnValue(new ArrayList<CheckBox>()));
		empresaManager.expects(once()).method("ajustaCombo").with(ANYTHING,eq(empresa.getId())).will(returnValue(empresa.getId()));
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas").withAnyArguments().will(returnValue(new ArrayList<Empresa>()));
	}
	
	public void testImprimirCertificadosVencidosAVencer() throws Exception
	{
		action.setColaboradorNaoCertificado(true);
		action.setColaboradorCertificado(true);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao);
		
		colaboradorCertificacaoManager.expects(once()).method("montaRelatorioColaboradoresNasCertificacoes").withAnyArguments().will(returnValue(colaboradorCertificacaos));
		
		assertEquals("success", action.imprimirCertificadosVencidosAVencer());
	}
	
	public void testImprimirCertificadosVencidosAVencerVazio() throws Exception
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaoManager.expects(once()).method("montaRelatorioColaboradoresNasCertificacoes").withAnyArguments().will(returnValue(colaboradorCertificacaos));
		mocksPrepareImprimir();
		
		assertEquals("input", action.imprimirCertificadosVencidosAVencer());
	}
	
	public void testImprimirCertificadosVencidosAVencerException() throws Exception
	{
		colaboradorCertificacaoManager.expects(once()).method("montaRelatorioColaboradoresNasCertificacoes").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		mocksPrepareImprimir();
		
		assertEquals("input", action.imprimirCertificadosVencidosAVencer());
	}
	
	public void testImprimirCertificadosVencidosAVencerAgrupadoPorCertificacao() throws Exception
	{
		action.setColaboradorNaoCertificado(true);
		action.setColaboradorCertificado(true);
		action.setAgruparPor('T');;
		
		Colaborador colaboradorAprovado = ColaboradorFactory.getEntity(1L);
		Colaborador colaboradorNaoAprovado = ColaboradorFactory.getEntity(2L);
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		
		ColaboradorCertificacao colaboradorAprovadoCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorAprovadoCertificacao.setCertificacao(certificacao);
		colaboradorAprovadoCertificacao.setColaborador(colaboradorAprovado);
		
		ColaboradorCertificacao colaboradorNaoAprovadoCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorNaoAprovadoCertificacao.setCertificacao(certificacao);
		colaboradorNaoAprovadoCertificacao.setColaborador(colaboradorNaoAprovado);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorAprovadoCertificacao);
		colaboradorCertificacaos.add(colaboradorNaoAprovadoCertificacao);
		
		colaboradorCertificacaoManager.expects(once()).method("montaRelatorioColaboradoresNasCertificacoes").withAnyArguments().will(returnValue(colaboradorCertificacaos));
		
		assertEquals("sucessoAgrupadoPorCertificacao", action.imprimirCertificadosVencidosAVencer());
	}
}
