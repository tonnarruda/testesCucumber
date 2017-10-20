package com.fortes.rh.test.dao.hibernate.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoFactory;

public class CodificacaoAcidenteTrabalhoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<CodificacaoAcidenteTrabalho>
{
	@Autowired
	private CodificacaoAcidenteTrabalhoDao codificacaoAcidenteTrabalhoDao;

	@Override
	public CodificacaoAcidenteTrabalho getEntity()
	{
		return CodificacaoAcidenteTrabalhoFactory.getEntity();
	}

	public GenericDao<CodificacaoAcidenteTrabalho> getGenericDao()
	{
		return codificacaoAcidenteTrabalhoDao;
	}
	
	@Test
	public void testFindAll(){
		assertEquals(22, codificacaoAcidenteTrabalhoDao.findAllSelect().size());
	}
}
