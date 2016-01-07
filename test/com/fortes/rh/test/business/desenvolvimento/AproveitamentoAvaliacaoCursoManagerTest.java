package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;

public class AproveitamentoAvaliacaoCursoManagerTest extends MockObjectTestCase
{
	AproveitamentoAvaliacaoCursoManagerImpl aproveitamentoAvaliacaoCursoManager = new AproveitamentoAvaliacaoCursoManagerImpl();
	Mock aproveitamentoAvaliacaoCursoDao = null;
	Mock transactionManager;
	Mock colaboradorCertificacaoManager;

	protected void setUp() throws Exception
	{
		aproveitamentoAvaliacaoCursoDao = new Mock(AproveitamentoAvaliacaoCursoDao.class);
		aproveitamentoAvaliacaoCursoManager.setDao((AproveitamentoAvaliacaoCursoDao) aproveitamentoAvaliacaoCursoDao.proxy());
		colaboradorCertificacaoManager = new Mock(ColaboradorCertificacaoManager.class);
		aproveitamentoAvaliacaoCursoManager.setColaboradorCertificacaoManager((ColaboradorCertificacaoManager) colaboradorCertificacaoManager.proxy());
		
		transactionManager = new Mock(PlatformTransactionManager.class);
		aproveitamentoAvaliacaoCursoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
	}
	
	public void testUpdateNotas() throws Exception
	{
		Long[] colaboradorTurmaIds = new Long[]{1L,2L};
		String[] notas = new String[]{"4.0","5.0"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1);
		avaliacaoCurso.setMinimoAprovacao(5.0);
		
		AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso();
		aproveitamento.setAvaliacaoCurso(avaliacaoCurso);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(aproveitamento));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("update").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaboradorByColaboradorTurma").with(ANYTHING, ANYTHING).isVoid();

		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, false);
	}

	public void testSaveNotas() throws Exception
	{
		Long[] colaboradorTurmaIds = new Long[]{1L,2L,3L};
		String[] notas = new String[]{"4.0","5.0",""};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(10L);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(1L), eq(10L)).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(2L), eq(10L)).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(3L), ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		//aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(3L), eq(10L)).will(returnValue(new AproveitamentoAvaliacaoCurso()));
		//aproveitamentoAvaliacaoCursoDao.expects(once()).method("remove");
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, false);
	}
	
	public void testSaveNotasComConfiguracaoPeriodicidadePorCertificacao() throws Exception
	{
		Long[] colaboradorTurmaIds = new Long[]{1L,2L,3L};
		String[] notas = new String[]{"4.0","5.0",""};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(10L);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(1L), eq(10L)).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(2L), eq(10L)).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(eq(3L), ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		colaboradorCertificacaoManager.expects(atLeastOnce()).method("verificaCertificacaoByColaboradorTurmaId").with(ANYTHING).isVoid();
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, true);
	}
	
	public void testSaveNotas2()
	{
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity(1L);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity(2L);
		AvaliacaoCurso avaliacaoCurso3 = AvaliacaoCursoFactory.getEntity(3L);

		Long[] avaliacaoCursoIds = new Long[]{avaliacaoCurso1.getId(), avaliacaoCurso2.getId(), avaliacaoCurso3.getId()};
		
		String[] notas = new String[]{"","3.1","5,5"};
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurma, notas, avaliacaoCursoIds);
	}

	
	public void testSaveNotasException() throws Exception
	{
		Long[] colaboradorTurmaIds = new Long[]{1L};
		String[] notas = new String[]{"4.0"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("save").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));;
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exc = null;
		try
		{
			aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, false);			
		}
		catch (Exception e)
		{
			exc = e;
		}
		assertNotNull(exc);
	}
	
	public void testFindNotas() throws Exception
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findNotas").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AproveitamentoAvaliacaoCurso>()));
		assertNotNull(aproveitamentoAvaliacaoCursoManager.findNotas(null, null));
	}
	
	public void testRemoveByAvaliacao() throws Exception
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("remove").with(ANYTHING, ANYTHING).isVoid();
		aproveitamentoAvaliacaoCursoManager.remove(null, null);
	}
	
	public void testRemoveByTurma() throws Exception
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("removeByTurma").with(ANYTHING).isVoid();
		aproveitamentoAvaliacaoCursoManager.removeByTurma(null);
	}
	
	public void testFind()
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("find").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Long>()));
		aproveitamentoAvaliacaoCursoManager.find(1L, 1, "C", true);
	}
	
	public void testFindComArrayCursos()
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("find").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Long>()));
		Long[] cursoIds = new Long[]{1L};
		aproveitamentoAvaliacaoCursoManager.find(cursoIds , 1, true);
	}
	
	public void testFindColaboradores()
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findColaboradores").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Long>()));
		aproveitamentoAvaliacaoCursoManager.findColaboradores(1L, 1, "C", true);
	}
	public void testFindAprovadosComAvaliacao()
	{
		Collection<Long> cursoIds = new ArrayList<Long>();
		cursoIds.add(1L);
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findAprovadosComAvaliacao").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Long>()));
		assertNotNull(aproveitamentoAvaliacaoCursoManager.findAprovadosComAvaliacao(cursoIds, new Date(), new Date()));
	}
	public void testFindReprovados()
	{
		Collection<Long> cursoIds = new ArrayList<Long>();
		cursoIds.add(1L);
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findReprovados").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Long>()));
		Date dataIni = new Date(),dataFim = new Date();
		assertNotNull(aproveitamentoAvaliacaoCursoManager.findReprovados(dataIni, dataFim, 1L));
	}
	public void testFindColaboradorTurma()
	{
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("findColaboradorTurma").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
		assertNotNull(aproveitamentoAvaliacaoCursoManager.findColaboradorTurma(1L, 1, "T", true));
	}
}
