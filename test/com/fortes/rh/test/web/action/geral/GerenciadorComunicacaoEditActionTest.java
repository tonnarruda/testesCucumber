package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.web.action.geral.GerenciadorComunicacaoEditAction;
import com.fortes.web.tags.CheckBox;

public class GerenciadorComunicacaoEditActionTest extends MockObjectTestCase
{
	private GerenciadorComunicacaoEditAction action;
	private Mock manager;
	private Mock usuarioManager;
	private Mock areaManager;
	private Mock estabelecimentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(GerenciadorComunicacaoManager.class);
		action = new GerenciadorComunicacaoEditAction();
		action.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) manager.proxy());
		
		areaManager = new Mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaManager.proxy());
		
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
		usuarioManager = new Mock(UsuarioManager.class);
		action.setUsuarioManager((UsuarioManager) usuarioManager.proxy());
		
		action.setGerenciadorComunicacao(new GerenciadorComunicacao());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("find").will(returnValue(new ArrayList<GerenciadorComunicacao>()));
		assertEquals("success", action.list());
		assertNotNull(action.getGerenciadorComunicacaos());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L);
		action.setGerenciadorComunicacao(gerenciadorComunicacao);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("find").will(returnValue(new ArrayList<GerenciadorComunicacao>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L);
		action.setGerenciadorComunicacao(gerenciadorComunicacao);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("find").will(returnValue(new ArrayList<GerenciadorComunicacao>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L);
		action.setGerenciadorComunicacao(gerenciadorComunicacao);

		manager.expects(once()).method("verifyExists").with(eq(gerenciadorComunicacao)).will(returnValue(false));
		manager.expects(once()).method("save").with(eq(gerenciadorComunicacao)).will(returnValue(gerenciadorComunicacao));

		assertEquals("success", action.insert());
	}
	
	public void testInsertExists() throws Exception
	{
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L);
		gerenciadorComunicacao.setOperacao(Operacao.ALTERAR_STATUS_SOLICITACAO.getId());
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		action.setGerenciadorComunicacao(gerenciadorComunicacao);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("verifyExists").with(eq(gerenciadorComunicacao)).will(returnValue(true));
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(gerenciadorComunicacao));
		usuarioManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Usuario>()));
		areaManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L);
		action.setGerenciadorComunicacao(gerenciadorComunicacao);

		manager.expects(once()).method("verifyExists").with(eq(gerenciadorComunicacao)).will(returnValue(false));
		manager.expects(once()).method("update").with(eq(gerenciadorComunicacao)).isVoid();

		assertEquals("success", action.update());
	}
	
	public void testUpdateExists() throws Exception
	{
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L);
		gerenciadorComunicacao.setOperacao(Operacao.ALTERAR_STATUS_SOLICITACAO.getId());
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		action.setGerenciadorComunicacao(gerenciadorComunicacao);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("verifyExists").with(eq(gerenciadorComunicacao)).will(returnValue(true));
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(gerenciadorComunicacao));
		usuarioManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Usuario>()));
		areaManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(ANYTHING).will(returnValue(new ArrayList<Estabelecimento>()));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setGerenciadorComunicacao(null);

		assertNotNull(action.getGerenciadorComunicacao());
		assertTrue(action.getGerenciadorComunicacao() instanceof GerenciadorComunicacao);
	}
	
	public void testGetOperacoesConfiguradasComLembrete() throws Exception
	{
		assertEquals(10, action.getOperacoesConfiguradasComLembrete().size());
	}
	
	public void testGetOperacoesComAvisoAposPrazo() throws Exception
	{
		assertEquals(2, action.getOperacoesComAvisoAposPrazo().size());
	}
}
