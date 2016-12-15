package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtilVerifyRole;
import com.fortes.rh.web.action.captacao.SolicitacaoListAction;

public class SolicitacaoListActionTest_JUnit4
{
	private SolicitacaoListAction action = new SolicitacaoListAction();
	private SolicitacaoManager solicitacaoManager;
	private AreaOrganizacionalManager areaorganizacionalManager;
	private MotivoSolicitacaoManager motivoSolicitacaoManager;
	private CargoManager cargoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	@Before
    public void setUp() throws Exception
    {
        solicitacaoManager = mock(SolicitacaoManager.class);
        areaorganizacionalManager = mock(AreaOrganizacionalManager.class);
        motivoSolicitacaoManager = mock(MotivoSolicitacaoManager.class);
        cargoManager = mock(CargoManager.class);
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        empresaManager = mock(EmpresaManager.class);
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        
        action.setSolicitacaoManager(solicitacaoManager);
        action.setAreaOrganizacionalManager(areaorganizacionalManager);
        action.setMotivoSolicitacaoManager(motivoSolicitacaoManager);
        action.setCargoManager(cargoManager);
        action.setEstabelecimentoManager(estabelecimentoManager);
        action.setEmpresaManager(empresaManager);
        action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
   
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

	@After
	public void tearDown() throws Exception
	{
		solicitacaoManager = null;
		action = null;
		MockSecurityUtil.verifyRole = false;
	}

	@Test
    public void testList() throws Exception {
    	Solicitacao s1 = SolicitacaoFactory.getSolicitacao(1L);
    	Solicitacao s2 = SolicitacaoFactory.getSolicitacao(2L);

    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	action.setUsuarioLogado(usuario);

    	Collection<Solicitacao> solicitacaos = Arrays.asList(s1, s2);
    	
    	when(solicitacaoManager.getCount(eq('T'), eq(action.getEmpresaSistema().getId()), eq(action.getUsuarioLogado().getId()),
    			anyLong(), anyLong(), anyLong(), anyLong(), anyString(), anyChar(), any(Long[].class), anyString(), any(Date.class), any(Date.class), eq(true), any(Date.class), any(Date.class))).thenReturn(solicitacaos.size()); 
    			
    	when(areaorganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(action.getUsuarioLogado(), action.getEmpresaSistema().getId())).thenReturn(new Long[]{});
    	when(areaorganizacionalManager.findAllSelectOrderDescricaoByUsuarioId(action.getEmpresaSistema().getId(), action.getUsuarioLogado().getId(), AreaOrganizacional.ATIVA, null)).thenReturn(new ArrayList<AreaOrganizacional>());
    	when(solicitacaoManager.findAllByVisualizacao(eq(0), eq(15), eq('T'), eq(action.getEmpresaSistema().getId()), eq(action.getUsuarioLogado().getId()),
    			anyLong(), anyLong(), anyLong(), anyLong(), anyString(), anyChar(), any(Long[].class), anyString(), any(Date.class), any(Date.class), eq(true), any(Date.class), any(Date.class))).
    			thenReturn(solicitacaos);
    	when(cargoManager.findAllSelect("nome", null, Cargo.TODOS, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Cargo>());
    	when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(motivoSolicitacaoManager.findAll()).thenReturn(new ArrayList<MotivoSolicitacao>());
    	assertEquals(action.list(), "success");
    }
    
	@Test
    public void testListRoleMovSolicitacaoSelecao() throws Exception {
    	Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtilVerifyRole.class);
    	Solicitacao s1 = SolicitacaoFactory.getSolicitacao();
    	s1.setId(1L);
    	
    	Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
    	solicitacaos.add(s1);
    	
    	action.setDataEncerramento(null);
    	action.setDataEncerramentoFim(null);
    	
    	when(solicitacaoManager.getCount(eq('A'), eq(action.getEmpresaSistema().getId()), eq(action.getUsuarioLogado().getId()),
    			anyLong(), anyLong(), anyLong(), anyLong(), anyString(), anyChar(), any(Long[].class), anyString(), any(Date.class), any(Date.class), eq(true), eq(action.getDataEncerramentoIni()), eq(action.getDataEncerramentoFim()))).thenReturn(solicitacaos.size()); 
    	
      	when(solicitacaoManager.findAllByVisualizacao(eq(0), eq(15), eq('A'), eq(action.getEmpresaSistema().getId()), eq(action.getUsuarioLogado().getId()),
    			anyLong(), anyLong(), anyLong(), anyLong(), anyString(), anyChar(), any(Long[].class), anyString(), any(Date.class), any(Date.class), eq(true), eq(action.getDataEncerramentoIni()), eq(action.getDataEncerramentoFim()))).thenReturn(solicitacaos); 

    	when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
    	when(motivoSolicitacaoManager.findAll()).thenReturn(new ArrayList<MotivoSolicitacao>());
    	when(areaorganizacionalManager.findAllSelectOrderDescricao(action.getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, null, false)).thenReturn(new ArrayList<AreaOrganizacional>());
    	
    	assertEquals(action.list(), "success");
    }
}