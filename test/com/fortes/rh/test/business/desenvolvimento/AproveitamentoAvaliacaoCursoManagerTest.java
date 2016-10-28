package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class AproveitamentoAvaliacaoCursoManagerTest extends MockObjectTestCase
{
	AproveitamentoAvaliacaoCursoManagerImpl aproveitamentoAvaliacaoCursoManager = new AproveitamentoAvaliacaoCursoManagerImpl();
	Mock aproveitamentoAvaliacaoCursoDao = null;
	Mock transactionManager;
	Mock colaboradorCertificacaoManager;
	Mock colaboradorTurmaManager;

	protected void setUp() throws Exception
	{
		aproveitamentoAvaliacaoCursoDao = new Mock(AproveitamentoAvaliacaoCursoDao.class);
		aproveitamentoAvaliacaoCursoManager.setDao((AproveitamentoAvaliacaoCursoDao) aproveitamentoAvaliacaoCursoDao.proxy());
		colaboradorCertificacaoManager = new Mock(ColaboradorCertificacaoManager.class);
		aproveitamentoAvaliacaoCursoManager.setColaboradorCertificacaoManager((ColaboradorCertificacaoManager) colaboradorCertificacaoManager.proxy());
		transactionManager = new Mock(PlatformTransactionManager.class);
		aproveitamentoAvaliacaoCursoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
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
