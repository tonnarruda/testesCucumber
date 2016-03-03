package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.dao.sesmt.TestemunhaDao;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.Testemunha;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class TestemunhaDaoHibernateTest extends GenericDaoHibernateTest<Testemunha>
{
	private TestemunhaDao testemunhaDao;
	private CatDao catDao;

	public Testemunha getEntity()
	{
		Testemunha testemunha = new Testemunha();
		return testemunha;
	}

	public GenericDao<Testemunha> getGenericDao() {
		return testemunhaDao;
	}

	public void testRemoveTestemunha(){
		Cat cat = new Cat();
		cat.setData(new Date());
		cat.setNumeroCat("111");
		
		Testemunha testemunha1 = new Testemunha();
		testemunha1.setNome("Primeira Testemunha");
				
		cat.setTestemunha1(testemunha1);
		catDao.save(cat);
		Long testemunha1Id = testemunha1.getId();
		assertEquals(testemunha1.getNome(), testemunhaDao.findById(testemunha1Id).getNome());
		testemunhaDao.removerDependenciaComCat(testemunha1Id, "testemunha1");
		testemunhaDao.remove(testemunha1Id); 
		testemunhaDao.getHibernateTemplateByGenericDao().flush();
		assertNull(testemunhaDao.findById(testemunha1Id));
	}

	public void setTestemunhaDao(TestemunhaDao testemunhaDao) {
		this.testemunhaDao = testemunhaDao;
	}

	public void setCatDao(CatDao catDao) {
		this.catDao = catDao;
	}
	
}