package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AgendaManagerImpl;
import com.fortes.rh.dao.sesmt.AgendaDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.test.factory.sesmt.EventoFactory;

public class AgendaManagerTest extends MockObjectTestCase
{
	private AgendaManagerImpl agendaManager = new AgendaManagerImpl();
	private Mock agendaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        agendaDao = new Mock(AgendaDao.class);
        agendaManager.setDao((AgendaDao) agendaDao.proxy());
    }

	public void testFindByPeriodo()
	{
		Long empresaId = 1L;
		
		Collection<Agenda> agendas = AgendaFactory.getCollection(1L);

		agendaDao.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, eq(empresaId), ANYTHING).will(returnValue(agendas));
		assertEquals(agendas, agendaManager.findByPeriodo(null, null, empresaId, null));
	}
	
	public void testFindByIdProjection()
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		
		agendaDao.expects(once()).method("findByIdProjection").with(eq(agenda.getId())).will(returnValue(agenda));
		assertEquals(agenda, agendaManager.findByIdProjection(1L));
	}
	
	public void testSave() throws Exception
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		agenda.setData(new Date());
		agendaDao.expects(atLeastOnce()).method("findByPeriodoEvento").will(returnValue(new ArrayList<Agenda>()));		
		agendaDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(AgendaFactory.getEntity(1L)));

		agendaManager.save(agenda, 2, 2, 0);
	}
	
	public void testSaveComAgendaJaExistente()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Evento evento = EventoFactory.getEntity(10L);
		Date data = new Date();
		
		Agenda agenda = AgendaFactory.getEntity();
		agenda.setEstabelecimento(estabelecimento);
		agenda.setEvento(evento);
		agenda.setData(data);
		
		Agenda agendaJaExistente = AgendaFactory.getEntity(32L);
		agendaJaExistente.setEstabelecimento(estabelecimento);
		agendaJaExistente.setEvento(evento);
		agendaJaExistente.setData(data);
		
		Collection<Agenda> agendasExistentes = new ArrayList<Agenda>();
		agendasExistentes.add(agendaJaExistente);
		
		agendaDao.expects(atLeastOnce()).method("findByPeriodoEvento").will(returnValue(agendasExistentes));
		
		agendaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(AgendaFactory.getEntity(1L)));
		
		Exception exception = null;
		
		try
		{
			agendaManager.save(agenda, 2, 2, 0);
		}
		catch(Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}
}
