package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.TransactionManager;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtilVerifyRole;
import com.fortes.rh.web.action.captacao.SolicitacaoListAction;
import com.fortes.web.tags.CheckBox;

public class SolicitacaoListActionTest extends MockObjectTestCase
{
	private SolicitacaoListAction action;
	private Mock manager;
	private Mock candidatoSolicitacaoManager;
	private Mock areaorganizacionalManager;
	private Mock motivoSolicitacaoManager;
	private Mock candidatoManager;
	private Mock cargoManager;
	private Mock estabelecimentoManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;
	private Mock transactionManager;

    protected void setUp() throws Exception
    {
        action = new SolicitacaoListAction();
        manager = new Mock(SolicitacaoManager.class);
        candidatoManager = new Mock(CandidatoManager.class);
        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        areaorganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        motivoSolicitacaoManager = new Mock(MotivoSolicitacaoManager.class);
        cargoManager = new Mock(CargoManager.class);
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        transactionManager = new Mock(PlatformTransactionManager.class);
        
        action.setSolicitacaoManager((SolicitacaoManager) manager.proxy());
        action.setCandidatoManager((CandidatoManager) candidatoManager.proxy());
        action.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaorganizacionalManager.proxy());
        action.setMotivoSolicitacaoManager((MotivoSolicitacaoManager) motivoSolicitacaoManager.proxy());
        action.setCargoManager((CargoManager)cargoManager.proxy());
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        action.setEmpresaManager((EmpresaManager)empresaManager.proxy());
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager)parametrosDoSistemaManager.proxy());
        action.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        candidatoManager = null;
        candidatoSolicitacaoManager = null;
        action = null;
    }

    public void testList() throws Exception
    {
    	Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
    	s1.setId(1L);
    	Solicitacao s2 = SolicitacaoFactory.getSolicitacao();
    	s2.setId(2L);

    	Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
    	solicitacaos.add(s1);
    	solicitacaos.add(s2);
    	
    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(2));
    	areaorganizacionalManager.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").withAnyArguments();
    	areaorganizacionalManager.expects(once()).method("findAllSelectOrderDescricaoByUsuarioId").withAnyArguments();
    	manager.expects(once()).method("findAllByVisualizacao").withAnyArguments().will(returnValue(solicitacaos));
    	cargoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Cargo>()));
    	estabelecimentoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Estabelecimento>()));
    	motivoSolicitacaoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<MotivoSolicitacao>()));
    	
    	assertEquals(action.list(), "success");
    	manager.verify();
    }
    
    public void testListRoleMovSolicitacaoSelecao() throws Exception
    {
    	Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtilVerifyRole.class);
    	Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
    	s1.setId(1L);
    	
    	Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
    	solicitacaos.add(s1);
    	
    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(1));
    	manager.expects(once()).method("findAllByVisualizacao").withAnyArguments().will(returnValue(solicitacaos));
    	cargoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Cargo>()));
    	estabelecimentoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Estabelecimento>()));
    	areaorganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").withAnyArguments();
    	motivoSolicitacaoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<MotivoSolicitacao>()));
    	
    	assertEquals(action.list(), "success");
    }

    public void testDelete() throws Exception
    {
    	Solicitacao ms = new Solicitacao();
    	ms.setId(1L);

    	Cargo cargo = CargoFactory.getEntity(1L);

    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	cargos.add(cargo);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	
    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento);
    	
    	action.setSolicitacao(ms);

    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(1));
    	areaorganizacionalManager.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").withAnyArguments();
    	areaorganizacionalManager.expects(once()).method("findAllSelectOrderDescricaoByUsuarioId").withAnyArguments();
    	manager.expects(once()).method("findAllByVisualizacao").withAnyArguments().will(returnValue(new ArrayList<Solicitacao>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(cargos));
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(estabelecimentos));
    	motivoSolicitacaoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<MotivoSolicitacao>()));
    	
    	manager.expects(once()).method("removeCascade").with(ANYTHING).will(returnValue(true));

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionMessages().isEmpty());
    }

    public void testDeleteError() throws Exception
    {
    	Solicitacao ms = new Solicitacao();
    	ms.setId(1L);

    	Cargo cargo = CargoFactory.getEntity(1L);

    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	cargos.add(cargo);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	
    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento);
    	
    	action.setSolicitacao(ms);

    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(1));
    	areaorganizacionalManager.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").withAnyArguments();
    	areaorganizacionalManager.expects(once()).method("findAllSelectOrderDescricaoByUsuarioId").withAnyArguments();
    	manager.expects(once()).method("findAllByVisualizacao").withAnyArguments().will(returnValue(new ArrayList<Solicitacao>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING,ANYTHING,ANYTHING).will(returnValue(cargos));
    	estabelecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(estabelecimentos));
    	manager.expects(once()).method("removeCascade").with(ANYTHING).will(returnValue(false));
    	motivoSolicitacaoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<MotivoSolicitacao>()));

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionWarnings().isEmpty());
    }
    
    public void testListRecebidas() throws Exception
    {
    	assertEquals("success", action.listRecebidas());
    }
    
    public void testVerSolicitacoes() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(100L);
    	action.setCandidato(candidato);

    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");

		candidatoManager.expects(once()).method("findByCandidatoId").with(eq(100L)).will(returnValue(candidato));
		manager.expects(once()).method("findSolicitacaoList").with(eq(1L),eq(false),eq(StatusAprovacaoSolicitacao.APROVADO),eq(false)).will(returnValue(new ArrayList<Solicitacao>()));
		
		assertEquals("success", action.verSolicitacoes());
    }
    
    public void testGravarSolicitacoesCandidato() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato(2L);
    	action.setCandidato(candidato);
    	
    	String[] solicitacaosCheckIds = new String[]{"20","100"};
		action.setSolicitacaosCheckIds(solicitacaosCheckIds);
		action.setCandidato(candidato);
		action.setStatusCandSol('A');
		candidatoSolicitacaoManager.expects(atLeastOnce()).method("insertCandidatos").with(ANYTHING, ANYTHING, ANYTHING).isVoid();
		
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		candidatoManager.expects(once()).method("updateDisponivel").withAnyArguments().isVoid();
		candidatoManager.expects(once()).method("findByCandidatoId").with(eq(2L)).will(returnValue(candidato));
		manager.expects(once()).method("findSolicitacaoList").with(eq(1L),eq(false),eq(StatusAprovacaoSolicitacao.APROVADO),eq(false)).will(returnValue(new ArrayList<Solicitacao>()));
		
		
		assertEquals("success",action.gravarSolicitacoesCandidato());
    }

    
    public void testEncerrarSolicitacao() throws Exception
    {
    	action.setSolicitacao(SolicitacaoFactory.getSolicitacao(1L));
    	action.setDataEncerramento("20/01/2010");
    	
    	manager.expects(once()).method("encerraSolicitacao").with(ANYTHING,ANYTHING).isVoid();
    	
    	assertEquals("success",action.encerrarSolicitacao());
    }
    public void testEncerrarSolicitacaoException() throws Exception
    {
    	action.setSolicitacao(SolicitacaoFactory.getSolicitacao(1L));
    	action.setDataEncerramento("");
    	
    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(0));
    	areaorganizacionalManager.expects(once()).method("findIdsAreasDoResponsavelCoResponsavel").withAnyArguments();
    	areaorganizacionalManager.expects(once()).method("findAllSelectOrderDescricaoByUsuarioId").withAnyArguments();
    	manager.expects(once()).method("findAllByVisualizacao").withAnyArguments().will(returnValue(new ArrayList<Solicitacao>()));
    	cargoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Cargo>()));
    	estabelecimentoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Estabelecimento>()));
    	motivoSolicitacaoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<MotivoSolicitacao>()));
    	
    	assertEquals("input",action.encerrarSolicitacao());
    	assertEquals("Data de Encerramento Inválida.", action.getActionWarnings().toArray()[0]);
    }
    
    public void testReabrirSolicitacao() throws Exception
    {
    	action.setSolicitacao(SolicitacaoFactory.getSolicitacao(1L));
    	manager.expects(once()).method("updateEncerraSolicitacao").with(eq(false),eq(null),eq(1L)).isVoid();
    	assertEquals("success",action.reabrirSolicitacao());
    }
    public void testSuspenderSolicitacao() throws Exception
    {
    	action.setSolicitacao(SolicitacaoFactory.getSolicitacao(1L));
    	manager.expects(once()).method("updateSuspendeSolicitacao").with(eq(true),ANYTHING,eq(1L)).isVoid();
    	assertEquals("success",action.suspenderSolicitacao());
    }
    public void testLiberarSolicitacao() throws Exception
    {
    	action.setSolicitacao(SolicitacaoFactory.getSolicitacao(1L));
    	manager.expects(once()).method("updateSuspendeSolicitacao").with(eq(false),eq(""),eq(1L)).isVoid();
    	assertEquals("success",action.liberarSolicitacao());
    }

    public void testGetSets() throws Exception
    {
    	action.setCargos(new ArrayList<Cargo>());
    	action.getCargos();

    	action.setCargo(new Cargo());
    	action.getCargo();
    	
    	action.getVisualizar();
    	action.setVisualizar(' ');
    	action.getCandidato();
    	action.setCandidato(null);
    	action.getSolicitacaosCheck();
    	action.setSolicitacaosCheck(new ArrayList<CheckBox>());
    	action.getSolicitacaosCheckIds();
    	action.setSolicitacaosCheckIds(null);
    	action.getDataEncerramento();
    	action.setDataEncerramento("");
    	action.getVisualizar();
    	action.getSolicitacaos();
    	
    	assertNotNull(action.getSolicitacao());
    }
}