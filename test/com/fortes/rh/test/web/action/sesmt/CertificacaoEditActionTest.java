package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.desenvolvimento.CertificacaoEditAction;

public class CertificacaoEditActionTest extends MockObjectTestCase
{
	private CertificacaoEditAction action;
	private Mock certificacaoManager;
	private Mock cursoManager;
	private Mock avaliacaoPraticaManager;
	private Mock colaboradorCertificacaoManager;

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
        
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
	}
	
	public void testPrepareInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		certificacaoManager.expects(once()).method("findById").with(eq(certificacao.getId())).will(returnValue(certificacao));
		certificacaoManager.expects(once()).method("findAllSelectNotCertificacaoId").withAnyArguments().will(returnValue(new ArrayList<Certificacao>()));
		cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(eq(new Long[] {empresa.getId()})).will(returnValue(new ArrayList<Curso>()));
		avaliacaoPraticaManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
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
		certificacaoManager.expects(once()).method("findAllSelectNotCertificacaoId").withAnyArguments().will(returnValue(new ArrayList<Certificacao>()));
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
}
