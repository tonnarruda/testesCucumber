package com.fortes.rh.test.web.action.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.jmock.core.Constraint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.cargosalario.FaixaSalarialHistoricoEditAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.ActionContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class, RelatorioUtil.class})
public class FaixaSalarialHistoricoEditActionTest_Junit4
{
	private FaixaSalarialHistoricoEditAction action;
	private FaixaSalarialHistoricoManager manager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CargoManager cargoManager;
	

	@Before
    public void setUp() throws Exception
    {
        action = new FaixaSalarialHistoricoEditAction();

        manager = mock(FaixaSalarialHistoricoManager.class);
        action.setFaixaSalarialHistoricoManager(manager);

        faixaSalarialManager = mock(FaixaSalarialManager.class);
        action.setFaixaSalarialManager(faixaSalarialManager);

        indiceManager = mock(IndiceManager.class);
        action.setIndiceManager(indiceManager);
        
        grupoOcupacionalManager = mock(GrupoOcupacionalManager.class);
        action.setGrupoOcupacionalManager(grupoOcupacionalManager);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager(areaOrganizacionalManager);
        
        cargoManager = mock(CargoManager.class);
        action.setCargoManager(cargoManager);

        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(RelatorioUtil.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }
    
	@Test
    public void testAnaliseTabelaSalarialFiltro() throws Exception
    {
		Long empresaId = action.getEmpresaSistema().getId();
		
		when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(true);
    	when(grupoOcupacionalManager.populaCheckOrderNome(empresaId)).thenReturn(new ArrayList<CheckBox>());
    	when(areaOrganizacionalManager.populaCheckOrderDescricao(empresaId)).thenReturn(new ArrayList<CheckBox>());
    	when(cargoManager.findAllSelect("nome", null, Cargo.TODOS, empresaId)).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("success",action.analiseTabelaSalarialFiltro());
    	
    	verify(grupoOcupacionalManager).populaCheckOrderNome(empresaId);
    	verify(areaOrganizacionalManager).populaCheckOrderDescricao(empresaId);
    	verify(cargoManager).findAllSelect("nome", null, Cargo.TODOS, empresaId);
    }
	
	@Test
    public void testAnaliseTabelaSalarialFiltroSemRoleVerAreas() throws Exception
    {
		Long empresaId = action.getEmpresaSistema().getId();
		
    	when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
    	when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(new ArrayList<AreaOrganizacional>());
    	when(grupoOcupacionalManager.populaCheckByAreasResponsavelCoresponsavel(action.getEmpresaSistema().getId(), new Long[]{})).thenReturn(new ArrayList<CheckBox>());
    	when(cargoManager.findByAreasAndGrupoOcapcinal(empresaId, null, null, new Long[]{})).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("success",action.analiseTabelaSalarialFiltro());
    	
    	verify(areaOrganizacionalManager).findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null);
    	verify(grupoOcupacionalManager).populaCheckByAreasResponsavelCoresponsavel(action.getEmpresaSistema().getId(), new Long[]{});
    	verify(cargoManager).findByAreasAndGrupoOcapcinal(empresaId, null, null, new Long[]{});
    }
	
	@Test
    public void testAnaliseTabelaSalarialListException() throws Exception
    {
		Long empresaId = action.getEmpresaSistema().getId();
		String[] areasCheck={""};
    	Date data = new Date();
    	action.setAreasCheck(areasCheck);
    	action.setData(data);
    	
    	when(manager.findByGrupoCargoAreaData(null, null, areasCheck, data, true, 1L, null)).thenThrow(new Exception("Sem dados para o filtro"));
    	//prepare
    	when(grupoOcupacionalManager.populaCheckOrderNome(empresaId)).thenReturn(new ArrayList<CheckBox>());
    	when(areaOrganizacionalManager.populaCheckOrderDescricao(empresaId)).thenReturn(new ArrayList<CheckBox>());
    	when(cargoManager.findAllSelect("nome", null, Cargo.TODOS, empresaId)).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("input",action.analiseTabelaSalarialList());
    	assertNotNull(action.getActionMsg());
    }
	
	@Test
    public void testAnaliseTabelaSalarialListGrupoOcupacionalComFiltros() throws Exception
    {
    	String[] grupoOcupacionalsCheck={"1"}, cargosCheck={"2"};
    	Date data = new Date();
    	
    	action.setGrupoOcupacionalsCheck(grupoOcupacionalsCheck);
    	action.setCargosCheck(cargosCheck);
    	action.setData(data);
    	action.setFiltro("1");
    	
    	Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
    	faixaSalarialHistoricos.add(FaixaSalarialHistoricoFactory.getEntity(232L));
    	
    	Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();
		
    	when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
    	when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
    	when(manager.findByGrupoCargoAreaData(grupoOcupacionalsCheck, cargosCheck, null, data, Boolean.TRUE, 1L, null)).thenReturn(faixaSalarialHistoricos);
    	when(cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos)).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("success",action.analiseTabelaSalarialList());
    	assertEquals(faixaSalarialHistoricos, action.getFaixaSalarialHistoricos());
    }
	
	@Test
    public void testAnaliseTabelaSalarialListGrupoOcupacionalSemFiltros() throws Exception
    {
    	Date data = new Date();
    	
    	action.setData(data);
    	action.setFiltro("1");
    	
    	Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
    	faixaSalarialHistoricos.add(FaixaSalarialHistoricoFactory.getEntity(232L));
    	
    	Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();
    	Long[] areasIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas);

    	GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional(22L);
    	String[] grupoOcupacionalsCheck={"22"};
    	
    	Cargo cargo = CargoFactory.getEntity(55L);
    	String[] cargosCheck={"55"};
    	
    	when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
    	when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
    	when(grupoOcupacionalManager.findAllSelectByAreasResponsavelCoresponsavel(action.getEmpresaSistema().getId(), areasIds)).thenReturn(Arrays.asList(grupoOcupacional));
    	when(cargoManager.findByAreasAndGrupoOcapcinal(action.getEmpresaSistema().getId(), null, null, areasIds)).thenReturn(Arrays.asList(cargo));
    	when(manager.findByGrupoCargoAreaData(grupoOcupacionalsCheck, cargosCheck, null, data, Boolean.TRUE, action.getEmpresaSistema().getId(), null)).thenReturn(faixaSalarialHistoricos);
    	when(cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos)).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("success",action.analiseTabelaSalarialList());
    	assertEquals(faixaSalarialHistoricos, action.getFaixaSalarialHistoricos());
    }
	
	@Test
    public void testAnaliseTabelaSalarialListAreaOrganizacionalComFiltros() throws Exception
    {
    	String[] areasCheck={"2"};
    	Date data = new Date();
    	
    	action.setAreasCheck(areasCheck);
    	action.setData(data);
    	action.setFiltro("2");
    	
    	Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
    	faixaSalarialHistoricos.add(FaixaSalarialHistoricoFactory.getEntity(232L));
		
    	Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();
    	
    	when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
    	when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
    	when(manager.findByGrupoCargoAreaData(null, null, areasCheck, data, Boolean.TRUE, 1L, null)).thenReturn(faixaSalarialHistoricos);
    	when(cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos)).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("success",action.analiseTabelaSalarialList());
    	assertEquals(faixaSalarialHistoricos, action.getFaixaSalarialHistoricos());
    }
	
	@Test
    public void testAnaliseTabelaSalarialListAreaOrganizacionalSemFiltros() throws Exception
    {
    	Date data = new Date();
    	action.setData(data);
    	action.setFiltro("2");
    	
    	Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
    	faixaSalarialHistoricos.add(FaixaSalarialHistoricoFactory.getEntity(232L));
		
    	Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(2L));
    	String[] areasCheck={"2"};
    	
    	when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
    	when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
    	
    	when(manager.findByGrupoCargoAreaData(null, null, areasCheck, data, Boolean.TRUE, 1L, null)).thenReturn(faixaSalarialHistoricos);
    	when(cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos)).thenReturn(new ArrayList<Cargo>());
    	
    	assertEquals("success",action.analiseTabelaSalarialList());
    	assertEquals(faixaSalarialHistoricos, action.getFaixaSalarialHistoricos());
    }
	
	@Test
    public void testRelatorioAnaliseTabelaSalarial() throws Exception
    {
    	String[] grupoOcupacionalsCheck={"1"}, cargosCheck={"2"};
    	Date data = new Date();
    	
    	action.setGrupoOcupacionalsCheck(grupoOcupacionalsCheck);
    	action.setCargosCheck(cargosCheck);
    	action.setData(data);
    	action.setFiltro("1");
    	
    	Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
    	faixaSalarialHistoricos.add(FaixaSalarialHistoricoFactory.getEntity(232L));
		
    	Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();
    	
    	when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
    	when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
    	when(manager.findByGrupoCargoAreaData(grupoOcupacionalsCheck, cargosCheck, null, data, Boolean.TRUE, action.getEmpresaSistema().getId(), null)).thenReturn(faixaSalarialHistoricos);
    	when(cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos)).thenReturn(new ArrayList<Cargo>());
    	when(RelatorioUtil.getParametrosRelatorio("Análise de Tabela Salarial", action.getEmpresaSistema(), "Data: " + DateUtil.formataDiaMesAno(data))).thenReturn(new HashMap<String, Object>());
    	
    	assertEquals("success",action.relatorioAnaliseTabelaSalarial());
    	assertEquals(faixaSalarialHistoricos, action.getFaixaSalarialHistoricos());
    	assertNotNull(action.getParametros());
    }
}