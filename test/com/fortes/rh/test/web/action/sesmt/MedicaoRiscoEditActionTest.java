package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.MedicaoRiscoManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.web.action.sesmt.MedicaoRiscoEditAction;

public class MedicaoRiscoEditActionTest extends MockObjectTestCase
{
	private MedicaoRiscoEditAction action;
	private Mock manager;
	private Mock ambienteManager;
	private Mock riscoAmbienteManager;
	private Mock estabelecimentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(MedicaoRiscoManager.class);
		action = new MedicaoRiscoEditAction();
		action.setMedicaoRiscoManager((MedicaoRiscoManager) manager.proxy());
		
		ambienteManager = mock(AmbienteManager.class);
		action.setAmbienteManager((AmbienteManager) ambienteManager.proxy());
		
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
		riscoAmbienteManager = mock(RiscoAmbienteManager.class);
		action.setRiscoAmbienteManager((RiscoAmbienteManager) riscoAmbienteManager.proxy());
        
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));		
        action.setMedicaoRisco(new MedicaoRisco());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		ambienteManager.expects(once()).method("findAmbientes");
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<MedicaoRisco>()));
		
		assertEquals(action.list(), "success");
		assertNotNull(action.getMedicaoRiscos());
	}

	public void testDelete() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);

		manager.expects(once()).method("removeCascade");
		assertEquals(action.delete(), "success");
	}
	
	public void testCarregarRiscos() throws Exception
	{
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
	
	private String[] ltcatValues = new String[]{"ltcat"};
	private String[] ppraValues = new String[]{"ppra"};
	private String[] tecnicaValues = new String[]{"tecnica"};
	private String[] intensidadeValues = new String[]{"120 graus"};
	private String[] riscoIds = new String[]{"1"};

	public void testInsert() throws Exception
	{
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
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("getTecnicasUtilizadas");
		estabelecimentoManager.expects(once()).method("findAllSelect");
		assertEquals("input", action.insert());
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
		action.setMedicaoRisco(null);

		assertNotNull(action.getMedicaoRisco());
		assertTrue(action.getMedicaoRisco() instanceof MedicaoRisco);
	}
}
