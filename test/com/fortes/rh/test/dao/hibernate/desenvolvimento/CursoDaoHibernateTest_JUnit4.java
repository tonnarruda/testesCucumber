package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;

public class CursoDaoHibernateTest_JUnit4 extends DaoHibernateAnnotationTest {
	@Autowired
	private CursoDao cursoDao;

	@Test
	public void testRemove() {
		try {
			cursoDao.removeVinculoComConhecimento(1l);
			assertTrue(true);
		} catch (Exception e) {
			fail("Erro ao remover vinculo com conhecimento");
		}
	}

}