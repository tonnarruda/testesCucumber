package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManagerImpl;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;

public class ExameSolicitacaoExameManagerTest extends MockObjectTestCase
{
	private ExameSolicitacaoExameManagerImpl exameSolicitacaoExameManager = new ExameSolicitacaoExameManagerImpl();
	private Mock exameSolicitacaoExameDao;

	protected void setUp() throws Exception
    {
        super.setUp();
        exameSolicitacaoExameDao = new Mock(ExameSolicitacaoExameDao.class);
        exameSolicitacaoExameManager.setDao((ExameSolicitacaoExameDao) exameSolicitacaoExameDao.proxy());
    }

	public void testFindByIdProjection() throws Exception
	{
		Collection<ExameSolicitacaoExame> colecao = new ArrayList<ExameSolicitacaoExame>();
		colecao.add(new ExameSolicitacaoExame());

		exameSolicitacaoExameDao.expects(once()).method("findBySolicitacaoExame").with(ANYTHING).will(returnValue(colecao));

		assertEquals(1, exameSolicitacaoExameManager.findBySolicitacaoExame(new Long[]{1L}).size());
	}

	public void testFindByIdProjectionVazio() throws Exception
	{
		assertEquals(0, exameSolicitacaoExameManager.findBySolicitacaoExame(new Long[0]).size());
	}

	public void testRemoveAllBySolicitacaoExame()
	{
		exameSolicitacaoExameDao.expects(once()).method("removeAllBySolicitacaoExame");
		exameSolicitacaoExameManager.removeAllBySolicitacaoExame(1L);
	}

	public void testSave()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		String[] exameIds = new String[]{"1"};
		String[] selectClinicas = new String[]{"1"};
		Integer[] periodicidades = new Integer[]{12};
		
		exameSolicitacaoExameDao.expects(atLeastOnce()).method("save");
		
		exameSolicitacaoExameManager.save(solicitacaoExame, exameIds, selectClinicas, periodicidades );
	}

	public void testVerificaExisteResultado()
	{
		Collection<ExameSolicitacaoExame> exameSolicitacaoExames = new ArrayList<ExameSolicitacaoExame>();
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setResultado(ResultadoExame.NORMAL.toString());
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExames.add(exameSolicitacaoExame);

		assertTrue(exameSolicitacaoExameManager.verificaExisteResultado(exameSolicitacaoExames));
	}
}