package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;
import com.fortes.rh.util.DateUtil;
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

	@Test
	public void list() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		when(ordemDeServicoManager.getCount(new String[]{"colaborador.id"}, new Long[]{colaborador.getId()})).thenReturn(1);
		when(colaboradorManager.findComDadosBasicosParaOrdemDeServico(eq(colaborador), any(Date.class))).thenReturn(colaborador);
		when(ordemDeServicoManager.find(action.getPage(), action.getPagingSize(), new String[]{"colaborador.id"}, new Long[]{colaborador.getId()}, new String[]{"data"})).thenReturn(Arrays.asList(OrdemDeServicoFactory.getEntity()));
		when(ordemDeServicoManager.findUltimaOrdemDeServico(colaborador.getId())).thenReturn(new OrdemDeServico());
		
		assertEquals("success", action.list());
	}
	
	@Test
	public void prepareInsert() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		when(ordemDeServicoManager.montaOrdemDeServico(any(OrdemDeServico.class), any(Colaborador.class), any(Empresa.class), any(Date.class))).thenReturn(ordemDeServico);
		assertEquals("success", action.prepareInsert());
	}
	
	@Test
	public void prepareUpdate() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		ordemDeServico.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		action.setOrdemDeServico(ordemDeServico);
		when(ordemDeServicoManager.montaOrdemDeServico(eq(action.getOrdemDeServico()), any(Colaborador.class), any(Empresa.class), eq(action.getOrdemDeServico().getData()))).thenReturn(ordemDeServico);
		assertEquals("success", action.prepareUpdate());
	}


	@Test
	public void delete() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		when(ordemDeServicoManager.getCount(new String[]{"colaborador.id"}, new Long[]{colaborador.getId()})).thenReturn(1);
		when(colaboradorManager.findComDadosBasicosParaOrdemDeServico(eq(colaborador), any(Date.class))).thenReturn(colaborador);
		when(ordemDeServicoManager.find(action.getPage(), action.getPagingSize(), new String[]{"colaborador.id"}, new Long[]{colaborador.getId()}, new String[]{"data"})).thenReturn(Arrays.asList(OrdemDeServicoFactory.getEntity()));
		when(ordemDeServicoManager.findUltimaOrdemDeServico(colaborador.getId())).thenReturn(ordemDeServico);
		assertEquals("success", action.delete());
		assertEquals("Ordem de serviço excluída com sucesso.",action.getActionSuccess().iterator().next());
	}
	
	@Test
	public void insert() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);
		assertEquals("success", action.insert());
		assertEquals("Ordem de serviço gravada com sucesso.",action.getActionSuccess().iterator().next());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void insertException() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);
		
		when(ordemDeServicoManager.save(ordemDeServico)).thenThrow(Exception.class);
		when(ordemDeServicoManager.montaOrdemDeServico(any(OrdemDeServico.class), any(Colaborador.class), any(Empresa.class), any(Date.class))).thenReturn(ordemDeServico);
		
		assertEquals("input", action.insert());
		assertEquals("Ocorreu um erro ao gravar a ordem de serviço.",action.getActionErrors().iterator().next());
	}

	@Test
	public void update() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);
		assertEquals("success", action.update());
		assertEquals("Ordem de serviço atualizada com sucesso.",action.getActionSuccess().iterator().next());
	}
}
