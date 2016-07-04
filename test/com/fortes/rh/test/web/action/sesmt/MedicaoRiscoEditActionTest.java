package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.MedicaoRiscoManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoMedicaoRiscoFactory;
import com.fortes.rh.web.action.sesmt.MedicaoRiscoEditAction;

public class MedicaoRiscoEditActionTest extends MockObjectTestCase
{
	private MedicaoRiscoEditAction action;
	private Mock manager;
	private Mock ambienteManager;
	private Mock funcaoManager;
	private Mock riscoAmbienteManager;
	private Mock riscoFuncaoManager;
	private Mock estabelecimentoManager;
	private Mock cargoManager;
	private Mock riscoMedicaoRiscoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(MedicaoRiscoManager.class);
		action = new MedicaoRiscoEditAction();
		action.setMedicaoRiscoManager((MedicaoRiscoManager) manager.proxy());
		
		ambienteManager = mock(AmbienteManager.class);
		action.setAmbienteManager((AmbienteManager) ambienteManager.proxy());
		
		funcaoManager = mock(FuncaoManager.class);
		action.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
		
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
		cargoManager = new Mock(CargoManager.class);
		action.setCargoManager((CargoManager) cargoManager.proxy());
		
		riscoAmbienteManager = mock(RiscoAmbienteManager.class);
		action.setRiscoAmbienteManager((RiscoAmbienteManager) riscoAmbienteManager.proxy());
        
		riscoFuncaoManager = mock(RiscoFuncaoManager.class);
		action.setRiscoFuncaoManager((RiscoFuncaoManager) riscoFuncaoManager.proxy());

		riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
		action.setRiscoMedicaoRiscoManager((RiscoMedicaoRiscoManager) riscoMedicaoRiscoManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));		
        action.setMedicaoRisco(new MedicaoRisco());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testListByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		ambienteManager.expects(once()).method("findAmbientes");
		manager.expects(once()).method("findAllSelectByAmbiente").will(returnValue(new ArrayList<MedicaoRisco>()));
		
		assertEquals(action.list(), "success");
		assertNotNull(action.getMedicaoRiscos());
	}

	public void testListByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		funcaoManager.expects(once()).method("findByEmpresa").with(eq(empresa.getId()));
		manager.expects(once()).method("findAllSelectByFuncao").will(returnValue(new ArrayList<MedicaoRisco>()));
		
		assertEquals(action.list(), "success");
		assertNotNull(action.getMedicaoRiscos());
	}
	
	public void testPrepareInsertByAmbiente() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity(2L);
		ambiente.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setAmbiente(ambiente);
		action.setMedicaoRisco(medicaoRisco);
		
		manager.expects(once()).method("getTecnicasUtilizadas").with(eq(empresa.getId()));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()));
		manager.expects(once()).method("findById").with(eq(medicaoRisco.getId())).will(returnValue(medicaoRisco));
		
		assertEquals("success", action.prepareInsert());
	}
	
	public void testPrepareInsertByFuncao() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		Funcao funcao  = FuncaoFactory.getEntity(2L);
		funcao.setCargo(CargoFactory.getEntity(1L));
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setFuncao(funcao);
		action.setMedicaoRisco(medicaoRisco);
		
		manager.expects(once()).method("getTecnicasUtilizadas").with(eq(empresa.getId()));
		cargoManager.expects(once()).method("findCargos");
		manager.expects(once()).method("getFuncaoByMedicaoRisco").with(eq(medicaoRisco.getId())).will(returnValue(medicaoRisco));
		manager.expects(once()).method("findRiscoMedicaoRiscos").with(eq(medicaoRisco.getId()));
		
		assertEquals("success", action.prepareInsert());
	}
	
	public void testPrepareUpdateByAmbiente() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		
		Ambiente ambiente = AmbienteFactory.getEntity(2L);
		ambiente.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setAmbiente(ambiente);
		action.setMedicaoRisco(medicaoRisco);
		
		manager.expects(once()).method("getTecnicasUtilizadas").with(eq(empresa.getId()));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()));
		manager.expects(once()).method("findById").with(eq(medicaoRisco.getId())).will(returnValue(medicaoRisco));
		ambienteManager.expects(once()).method("findByEstabelecimento");
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.prepareUpdate());
	}
	
	public void testPrepareUpdateByFuncao() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		Funcao funcao  = FuncaoFactory.getEntity(2L);
		funcao.setCargo(CargoFactory.getEntity(1L));
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setFuncao(funcao);
		action.setMedicaoRisco(medicaoRisco);
		
		manager.expects(once()).method("getTecnicasUtilizadas").with(eq(empresa.getId()));
		cargoManager.expects(once()).method("findCargos");
		manager.expects(once()).method("getFuncaoByMedicaoRisco").with(eq(medicaoRisco.getId())).will(returnValue(medicaoRisco));
		manager.expects(once()).method("findRiscoMedicaoRiscos").with(eq(medicaoRisco.getId()));
		funcaoManager.expects(once()).method("findByCargo");
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.prepareUpdate());
	}
	
	public void testDelete() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);

		manager.expects(once()).method("removeCascade");
		assertEquals(action.delete(), "success");
	}
	
	public void testCarregarRiscosByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		manager.expects(once()).method("getTecnicasUtilizadas");
		estabelecimentoManager.expects(once()).method("findAllSelect");
		
		ambienteManager.expects(once()).method("findByEstabelecimento");
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.carregarRiscos());
	}
	
	public void testCarregarRiscosMedicaoByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity();
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(riscoMedicaoRisco);
		
		manager.expects(once()).method("getTecnicasUtilizadas");
		riscoMedicaoRiscoManager.expects(once()).method("findMedicoesDeRiscosDoAmbiente").with(eq(ambiente.getId()), ANYTHING).will(returnValue(riscoMedicaoRiscos));
		estabelecimentoManager.expects(once()).method("findAllSelect");
		
		ambienteManager.expects(once()).method("findByEstabelecimento");
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.carregarRiscosComMedicao());
		assertTrue(action.getActionMessages().size() == 0);
	}
	
	public void testCarregarRiscosMedicaoByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		action.setCargo(CargoFactory.getEntity(1L));
		
		Funcao funcao = FuncaoFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setFuncao(funcao);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity();
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(riscoMedicaoRisco);
		
		manager.expects(once()).method("getTecnicasUtilizadas");
		riscoMedicaoRiscoManager.expects(once()).method("findMedicoesDeRiscosDaFuncao").with(eq(funcao.getId()), ANYTHING).will(returnValue(riscoMedicaoRiscos));
		cargoManager.expects(once()).method("findCargos");
		
		funcaoManager.expects(once()).method("findByCargo");
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.carregarRiscosComMedicao());
		assertTrue(action.getActionMessages().size() == 0);
	}

	public void testCarregarRiscosMedicaosemRicosASerCarregado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		action.setCargo(CargoFactory.getEntity(1L));
		
		Funcao funcao = FuncaoFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setFuncao(funcao);
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		
		manager.expects(once()).method("getTecnicasUtilizadas");
		riscoMedicaoRiscoManager.expects(once()).method("findMedicoesDeRiscosDaFuncao").with(eq(funcao.getId()), ANYTHING).will(returnValue(riscoMedicaoRiscos));
		cargoManager.expects(once()).method("findCargos");
		
		funcaoManager.expects(once()).method("findByCargo");
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.carregarRiscosComMedicao());
		assertEquals("Não há medição anterior para esta Função",action.getActionMessages().toArray()[0]);
	}
	
	
	public void testCarregarRiscosByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		action.setCargo(CargoFactory.getEntity(1L));
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		manager.expects(once()).method("getTecnicasUtilizadas");
		cargoManager.expects(once()).method("findCargos");
		
		funcaoManager.expects(once()).method("findByCargo");
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.carregarRiscos());
	}
	
	public void testCarregarRiscosBy() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		action.setCargo(CargoFactory.getEntity(1L));
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		manager.expects(once()).method("getTecnicasUtilizadas");
		cargoManager.expects(once()).method("findCargos");
		
		funcaoManager.expects(once()).method("findByCargo");
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData");
		manager.expects(once()).method("preparaRiscosDaMedicao");
		
		assertEquals("success", action.carregarRiscos());
	}
	
	private String[] ltcatValues = new String[]{"ltcat"};
	private String[] ppraValues = new String[]{"ppra"};
	private String[] tecnicaValues = new String[]{"tecnica"};
	private String[] intensidadeValues = new String[]{"120 graus"};
	private String[] riscoIds = new String[]{"1"};

	public void testInsertByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setLtcatValues(ltcatValues);
		action.setPpraValues(ppraValues);
		action.setIntensidadeValues(intensidadeValues);
		action.setTecnicaValues(tecnicaValues);
		action.setRiscoIds(riscoIds);

		manager.expects(once()).method("save");
		
		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		action.setControlaRiscoPor('A');
		
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("getTecnicasUtilizadas");
		estabelecimentoManager.expects(once()).method("findAllSelect");
		assertEquals("input", action.insert());
	}

	public void testPrepareUpdate() throws Exception
	{
		
	}
	
	public void testUpdate() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setLtcatValues(ltcatValues);
		action.setPpraValues(ppraValues);
		action.setIntensidadeValues(intensidadeValues);
		action.setTecnicaValues(tecnicaValues);
		action.setRiscoIds(riscoIds);

		manager.expects(once()).method("save");

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		ambiente.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		
		action.setAmbiente(ambiente);
		action.setData(new Date());
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setAmbiente(null);
		
		action.setControlaRiscoPor('A');
		action.setMedicaoRisco(medicaoRisco);
		
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("getTecnicasUtilizadas");
		estabelecimentoManager.expects(once()).method("findAllSelect");
		manager.expects(once()).method("findById").with(eq(medicaoRisco.getId())).will(returnValue(medicaoRisco));
		
		ambienteManager.expects(once()).method("findByEstabelecimento");
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData");
		manager.expects(once()).method("preparaRiscosDaMedicao");

		assertEquals("input", action.update());
		assertEquals(Long.valueOf(2L), action.getEstabelecimento().getId());
	}

	public void testGetSet() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		
		action.setMedicaoRisco(null);

		assertNotNull(action.getMedicaoRisco());
		assertTrue(action.getMedicaoRisco() instanceof MedicaoRisco);
	}
}
