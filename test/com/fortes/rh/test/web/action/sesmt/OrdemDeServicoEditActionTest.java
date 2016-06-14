package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.FiltroOrdemDeServico;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.web.action.sesmt.OrdemDeServicoEditAction;

public class OrdemDeServicoEditActionTest
{
	private OrdemDeServicoEditAction action;
	private OrdemDeServicoManager ordemDeServicoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;


	@Before
	public void setUp() throws Exception {
		action = new OrdemDeServicoEditAction();
		
		ordemDeServicoManager = mock(OrdemDeServicoManager.class);
		action.setOrdemDeServicoManager(ordemDeServicoManager);
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
		action.setUsuarioEmpresaManager(usuarioEmpresaManager);
		
		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager(colaboradorManager);
		
		cargoManager = mock(CargoManager.class);
		action.setCargoManager(cargoManager);
	}
	
	@Before
	public void inicializarDadosDeSesao(){
		Usuario usuario = UsuarioFactory.getEntity(1L);
		action.setUsuarioLogado(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
	}
	
	@Test
	public void listGerenciamentoOS() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		action.setHistoricoColaborador(historicoColaborador);

		when(areaOrganizacionalManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Cargo>());

		when(colaboradorManager.getCountColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.TODOS)).thenReturn(1);
		when(colaboradorManager.findColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, new Long[]{}, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.TODOS, action.getPage(), action.getPagingSize())).thenReturn(Arrays.asList(ColaboradorFactory.getEntity(1L)));

		assertEquals("success", action.listGerenciamentoOS());
		assertTrue(action.getActionMessages().isEmpty());
	}
	
	@Test
	public void listGerenciamentoOSCollectionVazia() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		action.setHistoricoColaborador(historicoColaborador);
		action.setFiltroOrdemDeServico(FiltroOrdemDeServico.COM_ORDEM_DE_SERVICO);
	
		when(areaOrganizacionalManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Cargo>());

		when(colaboradorManager.getCountColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.COM_ORDEM_DE_SERVICO)).thenReturn(1);
		when(colaboradorManager.findColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.COM_ORDEM_DE_SERVICO, action.getPage(), action.getPagingSize())).thenReturn(new ArrayList<Colaborador>());

		assertEquals("success", action.listGerenciamentoOS());
		assertEquals("Não existem colaboradores a serem listados para os filtros informados.",action.getActionMessages().iterator().next());
	}
	
	@Test
	public void listGerenciamentoOSUsuarioComPermissaoVerTodasAsAreas() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		action.setHistoricoColaborador(historicoColaborador);
		action.getUsuarioLogado().setId(2L);
		action.setFiltroOrdemDeServico(FiltroOrdemDeServico.SEM_ORDEM_DE_SERVICO);
		
		when(usuarioEmpresaManager.containsRole(action.getUsuarioLogado().getId(), action.getEmpresaSistema().getId(), "ROLE_VER_AREAS")).thenReturn(true);
		when(areaOrganizacionalManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Cargo>());

		when(colaboradorManager.getCountColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO,  FiltroOrdemDeServico.SEM_ORDEM_DE_SERVICO)).thenReturn(1);
		when(colaboradorManager.findColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.SEM_ORDEM_DE_SERVICO, action.getPage(), action.getPagingSize())).thenReturn(new ArrayList<Colaborador>());

		assertEquals("success", action.listGerenciamentoOS());
	}
	
	@Test
	public void listGerenciamentoOSUsuarioSemPermissaoVerTodasAsAreas() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		action.setHistoricoColaborador(historicoColaborador);
		action.getUsuarioLogado().setId(2L);
		
		when(usuarioEmpresaManager.containsRole(action.getUsuarioLogado().getId(), action.getEmpresaSistema().getId(), "ROLE_VER_AREAS")).thenReturn(false);
		when(areaOrganizacionalManager.findAreasByUsuarioResponsavel(action.getUsuarioLogado(),action.getEmpresaSistema().getId())).thenReturn(new ArrayList<AreaOrganizacional>());
	
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Cargo>());

		when(colaboradorManager.getCountColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.TODOS)).thenReturn(1);
		when(colaboradorManager.findColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, null, SituacaoColaborador.ATIVO, FiltroOrdemDeServico.TODOS, action.getPage(), action.getPagingSize())).thenReturn(Arrays.asList(ColaboradorFactory.getEntity()));

		assertEquals("success", action.listGerenciamentoOS());
	}

//	public void testList() throws Exception
//	{
//		ordemDeServicoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<OrdemDeServico>()));
//		assertEquals("success", action.list());
//		assertNotNull(action.getOrdemDeServicos());
//	}
//
//	public void testDelete() throws Exception
//	{
//		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
//		action.setOrdemDeServico(ordemDeServico);
//
//		ordemDeServicoManager.expects(once()).method("remove");
//		ordemDeServicoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<OrdemDeServico>()));
//		assertEquals("success", action.delete());
//	}
//	
//	public void testDeleteException() throws Exception
//	{
//		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
//		action.setOrdemDeServico(ordemDeServico);
//		
//		ordemDeServicoManager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
//		assertEquals("success", action.delete());
//	}
//
//	public void testInsert() throws Exception
//	{
//		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
//		action.setOrdemDeServico(ordemDeServico);
//
//		ordemDeServicoManager.expects(once()).method("save").with(eq(ordemDeServico)).will(returnValue(ordemDeServico));
//
//		assertEquals("success", action.insert());
//	}
//
//	public void testInsertException() throws Exception
//	{
//		ordemDeServicoManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
//		assertEquals("input", action.insert());
//	}
//
//	public void testUpdate() throws Exception
//	{
//		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
//		action.setOrdemDeServico(ordemDeServico);
//
//		ordemDeServicoManager.expects(once()).method("update").with(eq(ordemDeServico)).isVoid();
//
//		assertEquals("success", action.update());
//	}
//
//	public void testUpdateException() throws Exception
//	{
//		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
//		action.setOrdemDeServico(ordemDeServico);
//
//		ordemDeServicoManager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
//		ordemDeServicoManager.expects(once()).method("findById").with(eq(ordemDeServico.getId())).will(returnValue(ordemDeServico));
//
//		assertEquals("input", action.update());
//	}
//
//	public void testGetSet() throws Exception
//	{
//		action.setOrdemDeServico(null);
//
//		assertNotNull(action.getOrdemDeServico());
//		assertTrue(action.getOrdemDeServico() instanceof OrdemDeServico);
//	}
}
