package com.fortes.rh.test.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.cargosalario.CargoEditAction;

public class CargoEditActionTest extends MockObjectTestCase
{
	private CargoEditAction action;
	private Mock faixaSalarialManager;
	private Mock manager;
	private Mock etapaSeletivaManager;
	private Mock areaOrganizacionalManager;
	private Mock conhecimentoManager;
	private Mock areaFormacaoManager;
	private Mock grupoOcupacionalManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CargoEditAction();

        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        action.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());

        manager = new Mock(CargoManager.class);
        action.setCargoManager((CargoManager) manager.proxy());
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        conhecimentoManager = mock(ConhecimentoManager.class);
        action.setConhecimentoManager((ConhecimentoManager) conhecimentoManager.proxy());
        
        areaFormacaoManager = mock(AreaFormacaoManager.class);
        action.setAreaFormacaoManager((AreaFormacaoManager) areaFormacaoManager.proxy());
        
        grupoOcupacionalManager = mock(GrupoOcupacionalManager.class);
        action.setGrupoOcupacionalManager((GrupoOcupacionalManager) grupoOcupacionalManager.proxy());
        
		etapaSeletivaManager = mock(EtapaSeletivaManager.class);
		action.setEtapaSeletivaManager((EtapaSeletivaManager) etapaSeletivaManager.proxy());

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

    public void testImprimir() throws Exception
    {
    	Cargo cargo = CargoFactory.getEntity(10L);
    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	cargos.add(cargo);
    	
    	action.setCargo(cargo);
    	
    	manager.expects(once()).method("getCargosByIds").will(returnValue(cargos));
    	
    	assertEquals("success",action.imprimir());
    }
    
    public void testImprimirSemCArgo() throws Exception
    {
    	action.setCargo(null);
    	assertEquals("input",action.imprimir());
    }
    
    public void testRelatorioCargo() throws Exception
    {
    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	Cargo cargo = CargoFactory.getEntity(100L);
    	Cargo cargo2 = CargoFactory.getEntity(200L);
    	
    	cargos.add(cargo);
    	cargos.add(cargo2);
    	
    	Long[] ids = new Long[]{100L,200L};
    	String[] cargosCheck = new String[]{"100","200"};
		
    	action.setCargosCheck(cargosCheck);
		
    	manager.expects(once()).method("getCargosByIds").with(eq(ids), ANYTHING).will(returnValue(cargos));
    	
    	assertEquals("success", action.relatorioCargo());
    }
    
    public void testRelatorioCargoException() throws Exception
    {
    	Cargo cargo = CargoFactory.getEntity(100L);
    	
    	Long[] ids = new Long[]{19L};
    	String[] cargosCheck = new String[]{"19"};
    	
    	action.setCargosCheck(cargosCheck);
    	
    	manager.expects(once()).method("getCargosByIds").with(eq(ids), ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(cargo.getId(),""))));
    	
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(null));
    	areaFormacaoManager.expects(once()).method("populaCheckOrderNome").withNoArguments();
    	grupoOcupacionalManager.expects(once()).method("populaCheckOrderNome").will(returnValue(null));
    	etapaSeletivaManager.expects(once()).method("populaCheckOrderNome").will(returnValue(null));
    	manager.expects(once()).method("populaCheckBox").will(returnValue(null));
    	
    	assertEquals("input", action.relatorioCargo());
    }
    
    public void testPrepareTransferirFaixasCargo() throws Exception
    {
    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	Cargo cargo1 = CargoFactory.getEntity(1l);
    	cargo1.setNome("Secretária");
    	Cargo cargo2 = CargoFactory.getEntity(2l);
    	cargo2.setNome("Motorista");
    	cargos.add(cargo1);
    	cargos.add(cargo2);
    	
    	action.setCargo(cargo1);
    	
    	manager.expects(once()).method("findByIdProjection").will(returnValue(cargo1));
    	manager.expects(once()).method("findAllSelect").will(returnValue(cargos));
    	faixaSalarialManager.expects(once()).method("findFaixaSalarialByCargo").will(returnValue(new ArrayList<FaixaSalarial>()));
    	
    	action.prepareTransferirFaixasCargo();
    	
    	assertEquals(1, action.getCargos().size());
    }
    
    public void testTransferirFaixasCargo() throws Exception
    {
    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	Cargo cargo1 = CargoFactory.getEntity(1l);
    	cargo1.setNome("Secretária");
    	cargos.add(cargo1);
    	
    	action.setCargo(cargo1);
		action.setFaixa(FaixaSalarialFactory.getEntity(33L));
		action.setNovaFaixaNome("I");
    	
    	faixaSalarialManager.expects(once()).method("transfereFaixasCargo").isVoid();
    	
    	manager.expects(once()).method("findByIdProjection").will(returnValue(cargo1));
    	manager.expects(once()).method("findAllSelect").will(returnValue(cargos));
    	faixaSalarialManager.expects(once()).method("findFaixaSalarialByCargo").will(returnValue(new ArrayList<FaixaSalarial>()));
    	
    	assertEquals("success", action.transferirFaixasCargo());
    }
    
    public void testTransferirFaixasCargoException() throws Exception
    {
    	Collection<Cargo> cargos = new ArrayList<Cargo>();
    	Cargo cargo1 = CargoFactory.getEntity(1l);
    	cargo1.setNome("Secretária");
    	cargos.add(cargo1);
    	
    	action.setCargo(cargo1);
    	action.setFaixa(FaixaSalarialFactory.getEntity(33L));
    	
    	faixaSalarialManager.expects(once()).method("transfereFaixasCargo").will(throwException(new Exception()));
    	
    	manager.expects(once()).method("findByIdProjection").will(returnValue(cargo1));
    	manager.expects(once()).method("findAllSelect").will(returnValue(cargos));
    	faixaSalarialManager.expects(once()).method("findFaixaSalarialByCargo").will(returnValue(new ArrayList<FaixaSalarial>()));
    	
    	assertEquals("input", action.transferirFaixasCargo());
    }

    public void testGet() throws Exception
    {
    	action.getCargosCheck();
    	action.getCargosCheckList();
    	action.setCargosCheckList(null);
    	action.getGruposCheck();
    	action.setGruposCheck(null);
    	action.getGruposCheckList();
    	action.setGruposCheckList(null);
    	action.getGrupoOcupacionals();
    	action.setGrupoOcupacionals(null);
    	action.getPage();
    	action.setPage(1);
    	action.getFaixasDoCargo();
    	action.setFaixasDoCargo(new ArrayList<FaixaSalarial>());
    	action.setFaixa(new FaixaSalarial());
    	action.setNovaFaixaNome("I");
    	action.isIntegraAC();
    	action.setIntegraAC(false);
    	action.getParametros();
    	action.setConhecimentosCheck(null);
    	action.getConhecimentosCheck();
    	action.getConhecimentosCheckList();
    	action.setConhecimentosCheckList(null);
    }
}