package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AgendaManager;
import com.fortes.rh.business.sesmt.EventoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.sesmt.AgendaEditAction;

public class AgendaEditActionTest extends MockObjectTestCase
{
	private AgendaEditAction action;
	private Mock manager;
	private Mock eventoManager;
	private Mock estabelecimentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AgendaManager.class);
		action = new AgendaEditAction();
		action.setAgendaManager((AgendaManager) manager.proxy());

		estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        eventoManager = mock(EventoManager.class);
        action.setEventoManager((EventoManager) eventoManager.proxy());
		
		action.setAgenda(new Agenda());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
		
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(estabelecimentos));
		manager.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Agenda>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getAgendas());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Agenda agenda = AgendaFactory.getEntity(1L);
		action.setAgenda(agenda);
		
		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}
	
	public void testPrepareInsert() throws Exception
	{
		action.setAgenda(null);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
		Collection<Evento> eventos = new ArrayList<Evento>();
		
		eventoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(eventos));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(estabelecimentos));
		
		assertEquals("success", action.prepareInsert());
		assertEquals(estabelecimentos, action.getEstabelecimentos());
		assertEquals(eventos, action.getEventos());
	}

	public void testInsert() throws Exception
	{
		action.setDataMesAno("02/2009");
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		Agenda agenda = AgendaFactory.getEntity(1L);
		action.setAgenda(agenda);

		manager.expects(once()).method("save").with(eq(agenda), ANYTHING, ANYTHING, ANYTHING);

		assertEquals("success", action.insert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		agenda.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		action.setAgenda(agenda);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
		Collection<Evento> eventos = new ArrayList<Evento>();
		
		manager.expects(once()).method("findByIdProjection").with(eq(agenda.getId())).will(returnValue(agenda));
		eventoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(eventos));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(estabelecimentos));
		
		assertEquals("success", action.prepareUpdate());
		assertEquals(estabelecimentos, action.getEstabelecimentos());
		assertEquals(eventos, action.getEventos());
		assertEquals(agenda, action.getAgenda());
		assertEquals("01/2000", action.getDataMesAno());
	}

	public void testUpdate() throws Exception
	{
		action.setDataMesAno("02/2009");
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		Agenda agenda = AgendaFactory.getEntity(1L);
		action.setAgenda(agenda);

		manager.expects(once()).method("update").with(eq(agenda)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setAgenda(null);

		assertNotNull(action.getAgenda());
		assertTrue(action.getAgenda() instanceof Agenda);
	}
	

	public void testGetDatasMesAnoAtual()
	{
		String ano = DateUtil.getAno();
		
		action.setDatasMesAnoAtual();
		assertEquals("01/" + ano, action.getDataMesAnoIni());
		assertEquals("12/" + ano, action.getDataMesAnoFim());
		
		action.setDataMesAnoIni("");
		action.setDataMesAnoFim("");

		action.setDatasMesAnoAtual();
		assertEquals("01/" + ano, action.getDataMesAnoIni());
		assertEquals("12/" + ano, action.getDataMesAnoFim());
	}
}
