package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.TurmaTipoDespesaManagerImpl;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.TurmaTipoDespesaFactory;

public class TurmaTipoDespesaManagerTest extends MockObjectTestCase
{
	private TurmaTipoDespesaManagerImpl turmaTipoDespesaManager = new TurmaTipoDespesaManagerImpl();
	private Mock turmaTipoDespesaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        turmaTipoDespesaDao = new Mock(TurmaTipoDespesaDao.class);
        turmaTipoDespesaManager.setDao((TurmaTipoDespesaDao) turmaTipoDespesaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<TurmaTipoDespesa> turmaTipoDespesas = TurmaTipoDespesaFactory.getCollection(1L);

		turmaTipoDespesaDao.expects(once()).method("findAll").will(returnValue(turmaTipoDespesas));
		assertEquals(turmaTipoDespesas, turmaTipoDespesaManager.findAll());
	}
	

	public void testSaveJSON()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String json = "[{tipoDespesaId:2, despesa:545.56}, {tipoDespesaId:3, despesa:1044.56}]";
		
		turmaTipoDespesaDao.expects(once()).method("removeByTurma").with(eq(turma.getId())).isVoid();
		turmaTipoDespesaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		turmaTipoDespesaDao.expects(once()).method("save").with(ANYTHING).isVoid();

		Exception exception = null;
		try {
			turmaTipoDespesaManager.save(json, turma.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
		
	}
}
