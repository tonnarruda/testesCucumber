package com.fortes.rh.test.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.cargosalario.FaixaSalarialHistoricoEditAction;

public class FaixaSalarialHistoricoEditActionTest extends MockObjectTestCase
{
	private FaixaSalarialHistoricoEditAction action;
	private Mock manager;
	private Mock faixaSalarialManager;
	private Mock indiceManager;
	private Mock grupoOcupacionalManager;
	private Mock areaOrganizacionalManager;
	private Mock cargoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new FaixaSalarialHistoricoEditAction();

        manager = new Mock(FaixaSalarialHistoricoManager.class);
        action.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager) manager.proxy());

        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        action.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());

        indiceManager = new Mock(IndiceManager.class);
        action.setIndiceManager((IndiceManager) indiceManager.proxy());
        
        grupoOcupacionalManager = mock(GrupoOcupacionalManager.class);
        action.setGrupoOcupacionalManager((GrupoOcupacionalManager) grupoOcupacionalManager.proxy());
        
        areaOrganizacionalManager = mock (AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        cargoManager = mock(CargoManager.class);
        action.setCargoManager((CargoManager) cargoManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
    	MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", action.execute());
    }

    public void testInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	Indice indice = IndiceFactory.getEntity(1L);

    	Date data = new Date();

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setData(data);
    	faixaSalarialHistorico.setIndice(indice);
    	faixaSalarialHistorico.setTipo(2);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);

    	manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(false));
    	manager.expects(once()).method("verifyHistoricoIndiceNaData").with(eq(data), eq(indice.getId())).will(returnValue(true));
    	manager.expects(once()).method("save").with(eq(faixaSalarialHistorico), eq(faixaSalarial), eq(empresa), eq(true));

    	assertEquals("success", action.insert());
    }
    public void testInsertException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	
    	Date data = new Date();
    	
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setData(data);
    	faixaSalarialHistorico.setIndice(indice);
    	faixaSalarialHistorico.setTipo(2);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);
    	
    	manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(false));
    	manager.expects(once()).method("verifyHistoricoIndiceNaData").with(eq(data), eq(indice.getId())).will(returnValue(true));
    	manager.expects(once()).method("save").with(eq(faixaSalarialHistorico), eq(faixaSalarial), eq(empresa), eq(true)).will(throwException(new Exception()));
    	
    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
    	indiceManager.expects(once()).method("findAll");
    	
    	assertEquals("input", action.insert());
    }

    public void testInsertInput() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setTipo(2);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Collection<Indice> indices = IndiceFactory.getCollection();

		action.setFaixaSalarialHistorico(faixaSalarialHistorico);
		action.setFaixaSalarialAux(faixaSalarial);

		manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(true));
		manager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));
    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
    	indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(indices));

    	assertEquals("input", action.insert());
    }

    @SuppressWarnings("static-access")
	public void testInsertInputPorIndice() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	Indice indice = IndiceFactory.getEntity(1L);

    	Date data = new Date();

    	Collection<Indice> indices = IndiceFactory.getCollection();

    	TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setData(data);
    	faixaSalarialHistorico.setIndice(indice);
    	faixaSalarialHistorico.setTipo(tipoAplicacaoIndice.getIndice());
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);

    	manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(false));
    	manager.expects(once()).method("verifyHistoricoIndiceNaData").with(eq(data), eq(indice.getId())).will(returnValue(false));
    	manager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));
    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
    	indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(indices));

    	assertEquals("input", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	Indice indice = IndiceFactory.getEntity(1L);

    	Date data = new Date();

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setData(data);
    	faixaSalarialHistorico.setIndice(indice);
    	faixaSalarialHistorico.setTipo(2);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);

    	manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(false));
    	manager.expects(once()).method("verifyHistoricoIndiceNaData").with(eq(data), eq(indice.getId())).will(returnValue(true));
    	manager.expects(once()).method("update").with(eq(faixaSalarialHistorico), eq(faixaSalarial), eq(empresa));

    	assertEquals("success", action.update());
    }

    public void testUpdateInput() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setTipo(2);
    	faixaSalarialHistorico.setData(new Date());
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		action.setFaixaSalarialAux(faixaSalarial);

		manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(true));
		manager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));
    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId()));
    	indiceManager.expects(once()).method("findAll").with(ANYTHING);

    	assertEquals("input", action.update());
    }

    public void testUpdateInputIndice() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	Indice indice = IndiceFactory.getEntity(1L);

    	Date data = new Date();

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setData(data);
    	faixaSalarialHistorico.setIndice(indice);
    	faixaSalarialHistorico.setTipo(2);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);

    	manager.expects(once()).method("verifyData").with(eq(faixaSalarialHistorico.getId()), eq(faixaSalarialHistorico.getData()), eq(faixaSalarial.getId())).will(returnValue(false));
    	manager.expects(once()).method("verifyHistoricoIndiceNaData").with(eq(data), eq(indice.getId())).will(returnValue(false));
    	manager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));
    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId()));
    	indiceManager.expects(once()).method("findAll").with(ANYTHING);

    	assertEquals("input", action.update());
    }

    public void testPrepareInsert() throws Exception
    {
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Collection<Indice> indices = IndiceFactory.getCollection();

		action.setFaixaSalarialHistorico(faixaSalarialHistorico);
		action.setFaixaSalarialAux(faixaSalarial);

		manager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));
    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
    	indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(indices));

    	assertEquals("success", action.prepareInsert());
    	assertNotNull(action.getFaixaSalarialHistorico());
    	assertNotNull(action.getFaixaSalarialAux());
    	assertNotNull(action.getTipoAplicacaoIndices());
    	assertNotNull(action.getIndices());
    }

    public void testPrepareUpdate() throws Exception
    {
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		action.setFaixaSalarialAux(faixaSalarial);

    	faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId()));
    	indiceManager.expects(once()).method("findAll").with(ANYTHING);

    	assertEquals("success", action.prepareUpdate());
    }

    public void testGet() throws Exception
    {
    	assertNotNull(action.getModel());
    	assertNotNull(action.getTipoAplicacaoIndice());
    	action.getGrupoOcupacionalsCheckList();
    	action.getData();
    	action.getCargos();
    	action.getAreasCheckList();
    	action.getAreasCheck();
    	action.getCargosCheckList();
    	action.getCargosCheck();
    	action.getEmpresaId();
    }
}