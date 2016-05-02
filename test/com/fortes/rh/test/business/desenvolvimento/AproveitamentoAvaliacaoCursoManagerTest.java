package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
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
	
	public void testUpdateNotas() throws Exception {
		MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);
		
		Long[] colaboradorTurmaIds = new Long[]{1L,2L};
		String[] notas = new String[]{"4.0","5.0"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1);
		avaliacaoCurso.setMinimoAprovacao(5.0);
		
		AproveitamentoAvaliacaoCurso aproveitamento = new AproveitamentoAvaliacaoCurso();
		aproveitamento.setAvaliacaoCurso(avaliacaoCurso);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("update");
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(aproveitamento));
		colaboradorCertificacaoManager.expects(atLeastOnce()).method("descertificarColaboradorByColaboradorTurma").with(ANYTHING, ANYTHING);
		
		colaboradorTurmaManager.expects(atLeastOnce()).method("aprovarOrReprovarColaboradorTurma").with(eq(colaboradorTurma.getId()), eq(colaboradorTurma.getTurma().getId()), eq(colaboradorTurma.getCurso().getId())).isVoid();
		colaboradorTurmaManager.expects(atLeastOnce()).method("findByProjection").with(ANYTHING).will(returnValue(colaboradorTurma));

		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, false);
	}

	public void testSaveNotas() throws Exception
	{
		MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
		Long[] colaboradorTurmaIds = new Long[]{1L,2L,3L};
		String[] notas = new String[]{"4.0","5.0",""};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(10L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("save");
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(null));
		colaboradorTurmaManager.expects(atLeastOnce()).method("aprovarOrReprovarColaboradorTurma").withAnyArguments().isVoid();
		colaboradorTurmaManager.expects(atLeastOnce()).method("findByProjection").with(ANYTHING).will(returnValue(colaboradorTurma));
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, false);
	}
	
	public void testSaveNotasComConfiguracaoPeriodicidadePorCertificacao() throws Exception
	{
		MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
		Long[] colaboradorTurmaIds = new Long[]{1L,2L,3L};
		String[] notas = new String[]{"4.0","5.0",""};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(10L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colaborador, curso, turma);
		colaboradorTurma.setId(123L);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("save");
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(null));
		colaboradorTurmaManager.expects(atLeastOnce()).method("aprovarOrReprovarColaboradorTurma").withAnyArguments().isVoid();
		colaboradorTurmaManager.expects(atLeastOnce()).method("findByProjection").with(ANYTHING).will(returnValue(colaboradorTurma));
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso, true);
	}
	
	public void testSaveNotas2()
	{
		MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
		
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
		
		colaboradorTurmaManager.expects(atLeastOnce()).method("findByProjection").with(eq(colaboradorTurma.getId())).will(returnValue(colaboradorTurma));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		colaboradorTurmaManager.expects(atLeastOnce()).method("aprovarOrReprovarColaboradorTurma").with(eq(colaboradorTurma.getId()), eq(colaboradorTurma.getTurma().getId()), eq(colaboradorTurma.getCurso().getId())).isVoid();
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurma, notas, avaliacaoCursoIds);
	}

	
	public void testSaveNotasException() throws Exception
	{
		MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
		
		Long[] colaboradorTurmaIds = new Long[]{1L};
		String[] notas = new String[]{"4.0"};
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		aproveitamentoAvaliacaoCursoDao.expects(once()).method("save").withAnyArguments().will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));;
		aproveitamentoAvaliacaoCursoDao.expects(atLeastOnce()).method("findByColaboradorTurmaAvaliacaoId").with(ANYTHING, ANYTHING).will(returnValue(null));
		colaboradorTurmaManager.expects(once()).method("findByProjection").with(ANYTHING);

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
