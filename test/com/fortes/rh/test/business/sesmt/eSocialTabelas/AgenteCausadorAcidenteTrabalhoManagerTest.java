package com.fortes.rh.test.business.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoFactory;

public class AgenteCausadorAcidenteTrabalhoManagerTest
{
	private AgenteCausadorAcidenteTrabalhoManagerImpl agenteCausadorAcidenteTrabalhoManager = new AgenteCausadorAcidenteTrabalhoManagerImpl();
	private AgenteCausadorAcidenteTrabalhoDao agenteCausadorAcidenteTrabalhoDao;
	
	@Before
	public void setUp() throws Exception
    {
        agenteCausadorAcidenteTrabalhoDao = mock(AgenteCausadorAcidenteTrabalhoDao.class);
        agenteCausadorAcidenteTrabalhoManager.setDao(agenteCausadorAcidenteTrabalhoDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<AgenteCausadorAcidenteTrabalho> agenteCausadorAcidenteTrabalhos = AgenteCausadorAcidenteTrabalhoFactory.getCollection(1L);

		when(agenteCausadorAcidenteTrabalhoDao.findAllSelect()).thenReturn(agenteCausadorAcidenteTrabalhos);
		assertEquals(agenteCausadorAcidenteTrabalhos, agenteCausadorAcidenteTrabalhoManager.findAllSelect());
	}
}
