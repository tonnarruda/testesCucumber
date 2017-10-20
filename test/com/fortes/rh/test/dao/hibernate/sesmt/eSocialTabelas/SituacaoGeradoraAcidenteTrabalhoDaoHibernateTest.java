package com.fortes.rh.test.dao.hibernate.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoFactory;

public class SituacaoGeradoraAcidenteTrabalhoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<SituacaoGeradoraAcidenteTrabalho>
{
	@Autowired
	private SituacaoGeradoraAcidenteTrabalhoDao situacaoGeradoraAcidenteTrabalhoDao;

	@Override
	public SituacaoGeradoraAcidenteTrabalho getEntity()
	{
		return SituacaoGeradoraAcidenteTrabalhoFactory.getEntity();
	}

	public GenericDao<SituacaoGeradoraAcidenteTrabalho> getGenericDao()
	{
		return situacaoGeradoraAcidenteTrabalhoDao;
	}
	
	@Test
	public void testeFindAll(){
		assertEquals(59, situacaoGeradoraAcidenteTrabalhoDao.findAllSelect().size());
	}
}
