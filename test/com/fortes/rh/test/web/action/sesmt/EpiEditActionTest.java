package com.fortes.rh.test.web.action.sesmt;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.jmock.MockObjectTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.test.factory.sesmt.TipoEpiFactory;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.EpiEditAction;
import com.opensymphony.xwork.Action;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class, RelatorioUtil.class})
public class EpiEditActionTest extends MockObjectTestCase
{
	private EpiEditAction action;
	private EpiManager epiManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;
	private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;
	private TipoEPIManager tipoEpiManager;
	private EpiHistoricoManager epiHistoricoManager;
	private ColaboradorManager colaboradorManager;

    public void setUp() throws Exception
    {
        super.setUp();
        action = new EpiEditAction();
        
        epiManager = Mockito.mock(EpiManager.class);
        action.setEpiManager(epiManager);
        
        solicitacaoEpiItemManager = Mockito.mock(SolicitacaoEpiItemManager.class);
        action.setSolicitacaoEpiItemManager(solicitacaoEpiItemManager);
        
        solicitacaoEpiItemEntregaManager = Mockito.mock(SolicitacaoEpiItemEntregaManager.class);
        action.setSolicitacaoEpiItemEntregaManager(solicitacaoEpiItemEntregaManager);
        
        tipoEpiManager = Mockito.mock(TipoEPIManager.class);
        action.setTipoEPIManager(tipoEpiManager);
        
        epiHistoricoManager = Mockito.mock(EpiHistoricoManager.class);
        action.setEpiHistoricoManager(epiHistoricoManager);
        
		colaboradorManager = Mockito.mock(ColaboradorManager.class);
		action.setColaboradorManager(colaboradorManager);

        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        
        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(RelatorioUtil.class);
    }

    @Test
    public void testPrepareUpdate() throws Exception
    {
    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", action.getEmpresaSistema());
    	action.setEpi(epi);

    	TipoEPI tipoEPI = TipoEpiFactory.getEntity(1L);
    	Collection<TipoEPI> tiposEPIs = Arrays.asList(tipoEPI);
    	action.setTipos(tiposEPIs);

    	Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();

    	Mockito.when(epiManager.findById(epi.getId())).thenReturn(epi);
    	Mockito.when(tipoEpiManager.find(new String[]{"empresa.id"},new Object[]{action.getEmpresaSistema().getId()})).thenReturn(tiposEPIs);
    	Mockito.when(epiManager.findFabricantesDistinctByEmpresa(epi.getEmpresa().getId())).thenReturn(StringUtils.EMPTY);
    	Mockito.when(epiHistoricoManager.find(new String[]{"epi.id"},new Object[]{epi.getId()})).thenReturn(epiHistoricos);
    	
    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getTipos(),tiposEPIs);
     }

    @Test
    public void testPrepareUpdateEmpresaErrada() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(789L);

    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", empresa);
    	action.setEpi(epi);

    	TipoEPI tipoEPI = TipoEpiFactory.getEntity(1L);
    	Collection<TipoEPI> tiposEPIs = Arrays.asList(tipoEPI);
    	action.setTipos(tiposEPIs);

    	Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();

    	Mockito.when(epiManager.findById(epi.getId())).thenReturn(epi);
    	Mockito.when(tipoEpiManager.find(new String[]{"empresa.id"},new Object[]{action.getEmpresaSistema().getId()})).thenReturn(tiposEPIs);
    	Mockito.when(epiManager.findFabricantesDistinctByEmpresa(epi.getEmpresa().getId())).thenReturn(StringUtils.EMPTY);
    	Mockito.when(epiHistoricoManager.find(new String[]{"epi.id"},new Object[]{epi.getId()})).thenReturn(epiHistoricos);
    	
    	assertEquals(action.prepareUpdate(), "error");
    	assertEquals(action.getTipos(),tiposEPIs);
    	assertTrue(action.getMsgAlert()!= null && !action.getMsgAlert().equals(""));
     }

    @Test
    public void testUpdate() throws Exception
    {
    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", action.getEmpresaSistema());
    	action.setEpi(epi);

    	Mockito.when(epiManager.findByIdProjection(epi.getId())).thenReturn(epi);
    	Mockito.doNothing().when(epiManager).update(epi);

    	assertEquals(action.update(), "success");
    	Mockito.verify(epiManager, times(1)).update(epi);
    }

    @Test
    public void testUpdateEmpresaErrada() throws Exception
    {
    	Empresa empresaErrada = EmpresaFactory.getEmpresa(789L);
    	
    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", empresaErrada);
    	action.setEpi(epi);

    	Mockito.when(epiManager.findByIdProjection(epi.getId())).thenReturn(epi);
    	Mockito.doNothing().when(epiManager).update(epi);

    	assertEquals(action.update(), "error");
    	assertTrue(action.getMsgAlert().replace("%20", " ").startsWith("O EPI solicitado não existe na empresa"));
    	Mockito.verify(epiManager, times(0)).update(epi);
    }

    public void testPrepareInsert() throws Exception
    {
       	TipoEPI tipoEPI = new TipoEPI();
       	tipoEPI.setId(1L);

    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", action.getEmpresaSistema());
    	action.setEpi(epi);

    	Collection<TipoEPI> tiposEPIs = new ArrayList<TipoEPI>();
    	tiposEPIs.add(tipoEPI);
    	action.setTipos(tiposEPIs);

    	Mockito.when(epiManager.findById(epi.getId())).thenReturn(epi);
    	Mockito.when(tipoEpiManager.find(new String[]{"empresa.id"},new Object[]{action.getEmpresaSistema().getId()})).thenReturn(tiposEPIs);
    	Mockito.when(epiManager.findFabricantesDistinctByEmpresa(epi.getEmpresa().getId())).thenReturn(StringUtils.EMPTY);
    	
    	assertEquals(action.getTipos(),tiposEPIs);
    	assertEquals(action.prepareInsert(), "success");
    }

    @Test
    public void testInsert() throws Exception
    {
    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", null);
    	action.setEpi(epi);

    	TipoEPI tipoEPI = TipoEpiFactory.getEntity(1L);
    	Collection<TipoEPI> tiposEPIs = Arrays.asList(tipoEPI);
    	action.setTipos(tiposEPIs);

    	EpiHistorico epiHistorico = new EpiHistorico();
    	epiHistorico.setId(1L);
    	action.setEpiHistorico(epiHistorico);

    	Mockito.doNothing().when(epiManager).saveEpi(epi, epiHistorico);

    	assertEquals(action.insert(), "success");
    	assertEquals(action.getEpi(), epi);
    	Mockito.verify(epiManager, times(1)).saveEpi(epi, epiHistorico);
    }

    @Test
    public void testInsertException() throws Exception
    {
    	Epi epi = EpiFactory.getEntity(1L, "EPI 1", action.getEmpresaSistema());
    	action.setEpi(epi);

    	TipoEPI tipoEPI = TipoEpiFactory.getEntity(1L);
    	Collection<TipoEPI> tiposEPIs = Arrays.asList(tipoEPI);
    	action.setTipos(tiposEPIs);

    	EpiHistorico epiHistorico = new EpiHistorico();
    	epiHistorico.setId(1L);
    	action.setEpiHistorico(epiHistorico);

    	Mockito.doThrow(new Exception()).when(epiManager).saveEpi(epi, epiHistorico);

    	Mockito.when(epiManager.findById(epi.getId())).thenReturn(epi);
    	Mockito.when(tipoEpiManager.find(new String[]{"empresa.id"},new Object[]{action.getEmpresaSistema().getId()})).thenReturn(tiposEPIs);
    	Mockito.when(epiManager.findFabricantesDistinctByEmpresa(epi.getEmpresa().getId())).thenReturn(StringUtils.EMPTY);

    	assertEquals(action.insert(), "input");
    }

    @Test
    public void testPrepareImprimirFicha()
    {
    	assertEquals("success",action.prepareImprimirFicha());
    }

    @Test
    public void testFiltroImprimirFicha()
    {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Collection<Colaborador> colaboradors = Arrays.asList(colaborador);
		
		Mockito.when(colaboradorManager.findByNomeCpfMatricula(colaborador , false, null, null, action.getEmpresaSistema().getId())).thenReturn(colaboradors);

		assertEquals("success", action.filtroImprimirFicha());
    }
    
    @Test
    public void testImprimirFichaSemSolicitacaoEpiMarcada()
    {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		FichaEpiRelatorio fichaEpiRelatorio = new FichaEpiRelatorio();
		
    	action.setColaborador(colaborador);
    	action.setImprimirVerso(Boolean.TRUE);
    	
    	Mockito.when(epiManager.findImprimirFicha(action.getEmpresaSistema(), colaborador)).thenReturn(fichaEpiRelatorio);
    	
    	assertEquals("success",action.imprimirFicha());
    	assertNotNull(action.getDataSourceFichaEpi());
    	assertNull(action.getDataSourceFichaEpi().toArray(new SolicitacaoEpiItemEntrega[]{})[0].getId());
    }
    
    @Test
    public void testImprimirFichaComSolicitacaoEpiMarcadaSemSolicitacaoEpiItem()
    {
    	FichaEpiRelatorio fichaEpiRelatorio = new FichaEpiRelatorio();
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity(1L);
    	SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity(2L);
    	
    	Long[] solicitacoesEpiCheck = new Long[]{solicitacaoEpi1.getId(), solicitacaoEpi2.getId()};
    	
    	Collection<SolicitacaoEpiItem> solicitacaoEpiItems = new ArrayList<SolicitacaoEpiItem>();
    	
    	action.setSolicitacoesEpiCheck(solicitacoesEpiCheck);
    	action.setColaborador(colaborador);
    	action.setImprimirVerso(Boolean.FALSE);
    	
    	Mockito.when(epiManager.findImprimirFicha(action.getEmpresaSistema(), colaborador)).thenReturn(fichaEpiRelatorio);
    	Mockito.when(solicitacaoEpiItemManager.findBySolicitacaoEpi(Matchers.anyLong())).thenReturn(solicitacaoEpiItems);
    	
    	assertEquals("success",action.imprimirFicha());
    	assertNotNull(action.getDataSourceFichaEpi());
    	assertNull(action.getDataSourceFichaEpi().toArray(new SolicitacaoEpiItemEntrega[]{})[0].getId());
    }
    
    @Test
    public void testImprimirFichaComSolicitacaoEpiMarcadaComSolicitacaoEpiItem()
    {
    	FichaEpiRelatorio fichaEpiRelatorio = new FichaEpiRelatorio();
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity(1L);
    	SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity(2L);
    	
    	Long[] solicitacoesEpiCheck = new Long[]{solicitacaoEpi1.getId(), solicitacaoEpi2.getId()};
    	
    	SolicitacaoEpiItem solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
    	SolicitacaoEpiItem solicitacaoEpiItem2 = SolicitacaoEpiItemFactory.getEntity(2L);
    	Collection<SolicitacaoEpiItem> solicitacaoEpiItems = Arrays.asList(solicitacaoEpiItem1, solicitacaoEpiItem2);
    	
    	
    	SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega1 = SolicitacaoEpiItemEntregaFactory.getEntity(10L);
    	SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega2 = SolicitacaoEpiItemEntregaFactory.getEntity(20L);
    	SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega3 = SolicitacaoEpiItemEntregaFactory.getEntity(30L);
    	Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas = Arrays.asList(solicitacaoEpiItemEntrega1, solicitacaoEpiItemEntrega2, solicitacaoEpiItemEntrega3);
    	
    	action.setSolicitacoesEpiCheck(solicitacoesEpiCheck);
    	action.setColaborador(colaborador);
    	action.setImprimirVerso(Boolean.FALSE);
    	
    	Mockito.when(epiManager.findImprimirFicha(action.getEmpresaSistema(), colaborador)).thenReturn(fichaEpiRelatorio);
    	Mockito.when(solicitacaoEpiItemManager.findBySolicitacaoEpi(Matchers.anyLong())).thenReturn(solicitacaoEpiItems);
		Mockito.when(solicitacaoEpiItemEntregaManager.findBySolicitacaoEpiItem(Matchers.anyLong())).thenReturn(solicitacaoEpiItemEntregas);
    	
    	assertEquals(Action.SUCCESS,action.imprimirFicha());
    	assertNotNull(action.getDataSourceFichaEpi());
    	assertEquals(solicitacaoEpiItemEntregas.toArray(new SolicitacaoEpiItemEntrega[]{})[0].getId(), action.getDataSourceFichaEpi().toArray(new SolicitacaoEpiItemEntrega[]{})[0].getId());
    	assertEquals(solicitacaoEpiItemEntregas.toArray(new SolicitacaoEpiItemEntrega[]{})[1].getId(), action.getDataSourceFichaEpi().toArray(new SolicitacaoEpiItemEntrega[]{})[1].getId());
    	assertEquals(solicitacaoEpiItemEntregas.toArray(new SolicitacaoEpiItemEntrega[]{})[2].getId(), action.getDataSourceFichaEpi().toArray(new SolicitacaoEpiItemEntrega[]{})[2].getId());
    }

    @Test
    public void testGetSet() throws Exception
    {
    	action.setEpi(null);

    	assertTrue(action.getEpi() instanceof Epi);
    	
    	action.getEpiHistoricos();
    	action.setEpiHistoricos(null);
    	action.getEpiHistorico();
    	action.getDataHoje();
    	action.getColaborador();
    	action.getColaboradors();
    	action.setColaborador(null);
    	action.getParametros();
    	action.getFichaEpiRelatorio();
    	action.isImprimirVerso();
    	action.getFabricantes();
    	action.setSolicitacaoEpiItemManager(null);
    	action.setSolicitacaoEpiItemEntregaManager(null);
    	action.getSolicitacoesEpiCheck();
    	action.setSolicitacoesEpiCheck(null);
    }
}
