package com.fortes.rh.test.dao.hibernate.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoFactory;

public class AgenteCausadorAcidenteTrabalhoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<AgenteCausadorAcidenteTrabalho>
{
	@Autowired
	private AgenteCausadorAcidenteTrabalhoDao agenteCausadorAcidenteTrabalhoDao;

	@Override
	public AgenteCausadorAcidenteTrabalho getEntity()
	{
		return AgenteCausadorAcidenteTrabalhoFactory.getEntity();
	}

	public GenericDao<AgenteCausadorAcidenteTrabalho> getGenericDao()
	{
		return agenteCausadorAcidenteTrabalhoDao;
	}
	
	@Test
	public void testeFindAllSelect(){
		assertEquals(248, agenteCausadorAcidenteTrabalhoDao.findAllSelect().size());
	}
}
