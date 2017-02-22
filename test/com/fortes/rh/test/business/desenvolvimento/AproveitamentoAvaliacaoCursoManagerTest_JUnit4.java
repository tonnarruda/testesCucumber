package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class AproveitamentoAvaliacaoCursoManagerTest_JUnit4
{
	AproveitamentoAvaliacaoCursoManagerImpl aproveitamentoAvaliacaoCursoManager = new AproveitamentoAvaliacaoCursoManagerImpl();
	AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao = null;
	PlatformTransactionManager transactionManager;
	ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	ColaboradorTurmaManager colaboradorTurmaManager;

	@Before
	public void setUp() throws Exception
	{
		aproveitamentoAvaliacaoCursoDao = mock(AproveitamentoAvaliacaoCursoDao.class);
		aproveitamentoAvaliacaoCursoManager.setDao((AproveitamentoAvaliacaoCursoDao) aproveitamentoAvaliacaoCursoDao);
		
		colaboradorCertificacaoManager = mock(ColaboradorCertificacaoManager.class);
		aproveitamentoAvaliacaoCursoManager.setColaboradorCertificacaoManager((ColaboradorCertificacaoManager) colaboradorCertificacaoManager);
		
		transactionManager = mock(PlatformTransactionManager.class);
		aproveitamentoAvaliacaoCursoManager.setTransactionManager((PlatformTransactionManager) transactionManager);
		
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
		MockSpringUtilJUnit4.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
	}
	
	@Test
	public void testUpdateNotas() throws Exception {
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);
		
		String[] colaboradorTurmaIds_notas = new String[]{"1_4.0","2_5.0"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1);
		avaliacaoCurso.setMinimoAprovacao(5.0);
		
		AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso();
		aproveitamento.setAvaliacaoCurso(avaliacaoCurso);
		
		when(transactionManager.getTransaction(null)).thenReturn(null);
		when(aproveitamentoAvaliacaoCursoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(aproveitamentoAvaliacaoCursoDao.findByColaboradorTurmaAvaliacaoId(1L, 1L)).thenReturn(aproveitamento);
		when(aproveitamentoAvaliacaoCursoDao.findByColaboradorTurmaAvaliacaoId(2L, 1L)).thenReturn(aproveitamento);
		when(colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId())).thenReturn(false);
		when(colaboradorTurmaManager.findByProjection(1L)).thenReturn(colaboradorTurma);
		when(colaboradorTurmaManager.findByProjection(2L)).thenReturn(colaboradorTurma);

		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds_notas, avaliacaoCurso, true);
	}

	@Test
	public void testSaveNotas() throws Exception
	{
		String[] colaboradorTurmaIds_notas = new String[]{"1_4.0","2_5.0","3_"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(10L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);

		when(transactionManager.getTransaction(null)).thenReturn(null);
		when(aproveitamentoAvaliacaoCursoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId())).thenReturn(true);
		when(colaboradorTurmaManager.findByProjection(1L)).thenReturn(colaboradorTurma);
		when(colaboradorTurmaManager.findByProjection(2L)).thenReturn(colaboradorTurma);
		when(colaboradorTurmaManager.findByProjection(3L)).thenReturn(colaboradorTurma);
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds_notas, avaliacaoCurso, false);
	}
	
	@Test
	public void testSaveNotasComConfiguracaoPeriodicidadePorCertificacao() throws Exception
	{
		String[] colaboradorTurmaIds_notas = new String[]{"1_4.0","2_5.0","3_"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(10L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);
		
		when(transactionManager.getTransaction(null)).thenReturn(null);
		when(aproveitamentoAvaliacaoCursoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId())).thenReturn(true);
		when(colaboradorTurmaManager.findByProjection(1L)).thenReturn(colaboradorTurma);
		when(colaboradorTurmaManager.findByProjection(2L)).thenReturn(colaboradorTurma);
		when(colaboradorTurmaManager.findByProjection(3L)).thenReturn(colaboradorTurma);
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds_notas, avaliacaoCurso, false);
	}
	
	@Test
	public void testSaveNotas2()
	{
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity(1L);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity(2L);
		AvaliacaoCurso avaliacaoCurso3 = AvaliacaoCursoFactory.getEntity(3L);

		Long[] avaliacaoCursoIds = new Long[]{avaliacaoCurso1.getId(), avaliacaoCurso2.getId(), avaliacaoCurso3.getId()};
		
		String[] notas = new String[]{"","3.1","5,5"};
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);
		
		when(colaboradorTurmaManager.findByProjection(colaboradorTurma.getId())).thenReturn(colaboradorTurma);
		when(colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId())).thenReturn(true);
		when(aproveitamentoAvaliacaoCursoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(transactionManager.getTransaction(null)).thenReturn(null);

		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurma, notas, avaliacaoCursoIds, false);
	}
	
	@Test
	public void testSaveNotasException() throws Exception
	{
		String[] colaboradorTurmaIds_notas = new String[]{"1_4.0"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1);
		
		when(colaboradorTurmaManager.findByProjection(1L)).thenReturn(null);
		when(aproveitamentoAvaliacaoCursoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(transactionManager.getTransaction(null)).thenReturn(null);
		
		Exception exc = null;
		try
		{
			aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds_notas, avaliacaoCurso, false);			
		}
		catch (Exception e)
		{
			exc = e;
		}
		assertNotNull(exc);
		verify(transactionManager).rollback(null);
	}

}
