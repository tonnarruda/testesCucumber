package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.HistoricoAmbienteManagerImpl;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;

public class HistoricoAmbienteManagerTest extends MockObjectTestCase
{
	private HistoricoAmbienteManagerImpl historicoAmbienteManager = new HistoricoAmbienteManagerImpl();
	private Mock historicoAmbienteDao = null;
	private Mock riscoAmbienteManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        historicoAmbienteDao = new Mock(HistoricoAmbienteDao.class);
        historicoAmbienteManager.setDao((HistoricoAmbienteDao) historicoAmbienteDao.proxy());
        
        riscoAmbienteManager = mock(RiscoAmbienteManager.class);
        historicoAmbienteManager.setRiscoAmbienteManager((RiscoAmbienteManager) riscoAmbienteManager.proxy());
    }
	
	public void testSaveInserindo() throws Exception
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDescricao("Histórico do ambiente");
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		
		historicoAmbienteDao.expects(once()).method("findByData").with(eq(historicoAmbiente.getData()), eq(historicoAmbiente.getId()), eq(ambiente.getId())).will(returnValue(null));
		historicoAmbienteDao.expects(once()).method("save");
		
		Exception exception = null;
		
		try 
		{
			historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		} 
		catch (Exception e)  { exception = e; }	
		
		assertNull(exception);
	}
	
	public void testSaveAtualizando()
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDescricao("Histórico do ambiente");
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		
		historicoAmbienteDao.expects(once()).method("findByData").with(eq(historicoAmbiente.getData()), eq(historicoAmbiente.getId()), eq(ambiente.getId())).will(returnValue(null));
		riscoAmbienteManager.expects(once()).method("removeByHistoricoAmbiente").with(eq(historicoAmbiente.getId())).will(returnValue(true));
		historicoAmbienteDao.expects(once()).method("update");
		
		Exception exception = null;
		
		try 
		{
			historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		} 
		catch (Exception e) { exception = e; }
		
		assertNull(exception);
	}
	
	public void testFindById()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		Collection<RiscoAmbiente> riscoAmbientes = new ArrayList<RiscoAmbiente>();
		RiscoAmbiente riscoAmbiente = new RiscoAmbiente();
		riscoAmbiente.setId(1L);
		riscoAmbiente.setId(2L);
		
		riscoAmbientes.add(riscoAmbiente);
		
		historicoAmbiente.setRiscoAmbientes(riscoAmbientes);
		
		historicoAmbienteDao.expects(once()).method("findById").will(returnValue(historicoAmbiente));
		
		historicoAmbiente = historicoAmbienteManager.findById(1L);
		
		assertEquals(1, historicoAmbiente.getRiscoAmbientes().size());
	}
	
	public void testRemoveByAmbiente()
	{
		historicoAmbienteDao.expects(once()).method("removeByAmbiente").will(returnValue(true));
		assertTrue(historicoAmbienteManager.removeByAmbiente(1L));
	}
	
	public void testRemoveCascade()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		historicoAmbienteDao.expects(once()).method("findById").with(eq(historicoAmbiente.getId())).will(returnValue(historicoAmbiente));
		historicoAmbienteDao.expects(once()).method("remove").with(eq(historicoAmbiente));
		
		historicoAmbienteManager.removeCascade(historicoAmbiente.getId());
	}
	
	public void testFindByAmbiente()
	{
		Long ambienteId = 3L;
		historicoAmbienteDao.expects(once()).method("findByAmbiente").with(eq(ambienteId)).will(returnValue(new ArrayList<HistoricoAmbiente>()));
		
		assertNotNull(historicoAmbienteManager.findByAmbiente(ambienteId));
	}
	
	public void testFindUltimoHistorico()
	{
		Date hoje = new Date();
		
		Long ambienteId = 3L;
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(hoje);
		historicoAmbiente.setId(5L);
		
		historicoAmbienteDao.expects(once()).method("findUltimoHistorico").with(eq(ambienteId)).will(returnValue(historicoAmbiente));
		
		assertEquals(historicoAmbiente, historicoAmbienteManager.findUltimoHistorico(ambienteId));
	}

}