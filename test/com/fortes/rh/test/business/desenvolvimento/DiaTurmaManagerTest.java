package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManagerImpl;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

@SuppressWarnings("deprecation")
public class DiaTurmaManagerTest extends MockObjectTestCase
{
	private DiaTurmaManagerImpl diaTurmaManager = new DiaTurmaManagerImpl();
	Mock diaTurmaDao;
	Mock transactionManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        diaTurmaDao = new Mock(DiaTurmaDao.class);
        diaTurmaManager.setDao((DiaTurmaDao) diaTurmaDao.proxy());
        
		transactionManager = new Mock(PlatformTransactionManager.class);
		diaTurmaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }

	public void testMontaListaDias()
    {
		Date dataPrevIni = new Date("2007/12/10");
    	Date dataPrevFim = new Date("2007/12/05");

    	Collection<DiaTurma> diaTurmas = diaTurmaManager.montaListaDias(dataPrevIni, dataPrevFim, false);
    	assertEquals(0, diaTurmas.size());

    	dataPrevIni = new Date("2007/12/01");

    	diaTurmas = diaTurmaManager.montaListaDias(dataPrevIni, dataPrevFim, false);

    	assertEquals(5, diaTurmas.size());

    	dataPrevIni = new Date("2007/12/01");
    	dataPrevFim = new Date("2007/12/01");

    	diaTurmas = diaTurmaManager.montaListaDias(dataPrevIni, dataPrevFim, false);

    	assertEquals(1, diaTurmas.size());

    }

	public void testMontaListaDiasComTurnos()
	{
		Date dataPrevIni = new Date("2007/12/10");
		Date dataPrevFim = new Date("2007/12/05");
		
		Collection<DiaTurma> diaTurmas = diaTurmaManager.montaListaDias(dataPrevIni, dataPrevFim, true);
		assertEquals(0, diaTurmas.size());
		
		dataPrevIni = new Date("2007/12/01");
		
		diaTurmas = diaTurmaManager.montaListaDias(dataPrevIni, dataPrevFim, true);
		
		assertEquals(15, diaTurmas.size());
		
		dataPrevIni = new Date("2007/12/01");
		dataPrevFim = new Date("2007/12/01");
		
		diaTurmas = diaTurmaManager.montaListaDias(dataPrevIni, dataPrevFim, true);
		
		assertEquals(3, diaTurmas.size());
		
	}
	
	public void testDeleteDiasTurma()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		diaTurmaDao.expects(once()).method("deleteDiasTurma").with(eq(turma.getId())).isVoid();
		diaTurmaManager.deleteDiasTurma(turma.getId());
	}
	
	public void testFindByTurma()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
		
		diaTurmaDao.expects(once()).method("findByTurma").with(eq(turma.getId())).will(returnValue(diaTurmas));
		assertEquals(diaTurmas, diaTurmaManager.findByTurma(turma.getId()));
	}
	
	public void testSaveDiasTurma()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"01/01/2009"};
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		diaTurmaDao.expects(once()).method("deleteDiasTurma").with(eq(turma.getId())).isVoid();
		diaTurmaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		Exception ex = null;
		try
		{
			diaTurmaManager.saveDiasTurma(turma, diasCheck, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNull(ex);
	}
	
	public void testSaveDiasTurmaException()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"01/01/2009"};
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		diaTurmaDao.expects(once()).method("deleteDiasTurma").with(eq(turma.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception ex = null;
		try
		{
			diaTurmaManager.saveDiasTurma(turma, diasCheck, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNotNull(ex);
	}
	
	public void testdiasDasTurmas()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity();
		diaTurma1.setTurma(turma);
		
		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity();
		diaTurma2.setTurma(turma);
		
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma.getId());
		
		diaTurmaDao.expects(once()).method("qtdDiasDasTurmas").with(eq(turma.getId())).will(returnValue(new Integer(2)));
		
		assertEquals(new Integer(2), diaTurmaManager.qtdDiasDasTurmas(turma.getId()));
	}

	public void testClonarDiaTurmasDeTurma()
	{
		Turma turmaDelphi = TurmaFactory.getEntity(1L);
		Turma turmaDelphiClonada = TurmaFactory.getEntity(2L);

		DiaTurma delphi = DiaTurmaFactory.getEntity(3L);
		delphi.setTurma(turmaDelphi);

		DiaTurma java = DiaTurmaFactory.getEntity(4L);
		java.setTurma(turmaDelphi);
		
		Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
		diaTurmas.add(delphi);
		diaTurmas.add(java);

		diaTurmaDao.expects(once()).method("findByTurma").with(eq(turmaDelphi.getId())).will(returnValue(diaTurmas));
		diaTurmaDao.expects(once()).method("save").with(ANYTHING);
		diaTurmaDao.expects(once()).method("save").with(ANYTHING);

		diaTurmaManager.clonarDiaTurmasDeTurma(turmaDelphi, turmaDelphiClonada);
	}
}