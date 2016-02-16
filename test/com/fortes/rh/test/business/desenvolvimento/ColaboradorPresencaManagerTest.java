package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;

public class ColaboradorPresencaManagerTest extends MockObjectTestCase
{
	private ColaboradorPresencaManagerImpl colaboradorPresencaManager = new ColaboradorPresencaManagerImpl();
	private Mock colaboradorPresencaDao;
	private Mock colaboradorTurmaManager;
	private Mock colaboradorCertificacaoManager;

    protected void setUp() throws Exception
    {
    	colaboradorPresencaDao = new Mock(ColaboradorPresencaDao.class);
		colaboradorPresencaManager.setDao((ColaboradorPresencaDao) colaboradorPresencaDao.proxy());
		
		colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
		colaboradorPresencaManager.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
		
		colaboradorCertificacaoManager = new Mock(ColaboradorCertificacaoManager.class);
		colaboradorPresencaManager.setColaboradorCertificacaoManager((ColaboradorCertificacaoManager) colaboradorCertificacaoManager.proxy());
		
		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
		
        super.setUp();
    }

	public void testExistPresencaByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setId(1L);

		ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca();
		colaboradorPresenca.setId(1L);

		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencas.add(colaboradorPresenca);

		colaboradorPresencaDao.expects(once()).method("existPresencaByTurma").with(eq(turma.getId())).will(returnValue(colaboradorPresencas));

		assertTrue(colaboradorPresencaManager.existPresencaByTurma(turma.getId()));

		colaboradorPresencas.clear();
		colaboradorPresencaDao.expects(once()).method("existPresencaByTurma").with(eq(turma.getId())).will(returnValue(colaboradorPresencas));

		assertFalse(colaboradorPresencaManager.existPresencaByTurma(turma.getId()));
	}
	
	public void testFindPresencaByTurma()
	{
		Long id = 1L;
		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencaDao.expects(once()).method("findPresencaByTurma").with(eq(id)).will(returnValue(colaboradorPresencas));
		assertEquals(colaboradorPresencas, colaboradorPresencaManager.findPresencaByTurma(id));
	}
	
	public void testMarcarTodos() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setColaborador(colaborador);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);
		
		colaboradorTurmaManager.expects(once()).method("findByTurmaSemPresenca").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
		colaboradorCertificacaoManager.expects(once()).method("existeColaboradorCertificadoEmUmaTurmaPosterior").with(ANYTHING, eq(colaboradorTurma.getColaborador().getId())).will(returnValue(false));
		colaboradorPresencaDao.expects(once()).method("save").with(ANYTHING);
		colaboradorPresencaDao.expects(once()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		
		colaboradorPresencaManager.marcarTodos(null, null, true);
	}
	
	public void testMarcarTodosComColaboradorCertificadoEmTurmaPosterior() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setColaborador(colaborador);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);
		
		colaboradorTurmaManager.expects(once()).method("findByTurmaSemPresenca").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
		colaboradorCertificacaoManager.expects(once()).method("existeColaboradorCertificadoEmUmaTurmaPosterior").with(ANYTHING, eq(colaboradorTurma.getColaborador().getId())).will(returnValue(true));
		
		colaboradorPresencaManager.marcarTodos(null, null, false);
	}
	
	public void testRemoveByDiaTurma() throws Exception
	{
		colaboradorPresencaDao.expects(once()).method("remove").with(ANYTHING, ANYTHING).isVoid();
		colaboradorPresencaManager.removeByDiaTurma(null, null, false);
	}
	
	public void testUpdatePresenca() throws Exception
	{
		colaboradorPresencaDao.expects(once()).method("save").with(ANYTHING);
		colaboradorPresencaDao.expects(once()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		colaboradorPresencaManager.updateFrequencia(null, null, true, false);
		
		colaboradorPresencaDao.expects(once()).method("save").with(ANYTHING);
		colaboradorPresencaDao.expects(once()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		colaboradorPresencaManager.updateFrequencia(null, null, true, true);
		
		colaboradorPresencaDao.expects(once()).method("remove").with(ANYTHING,ANYTHING);
		colaboradorCertificacaoManager.expects(once()).method("descertificarColaboradorByColaboradorTurma").with(ANYTHING,ANYTHING);
		colaboradorPresencaManager.updateFrequencia(null, null, false, false);
	}
	
	public void testCalculaFrequencia() throws Exception
	{
		colaboradorPresencaDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(5));
		
		assertEquals("50,00", colaboradorPresencaManager.calculaFrequencia(1L, 10));
	}
	
	public void testRemoveByColaboradorTurma() throws Exception
	{
		colaboradorPresencaDao.expects(once()).method("removeByColaboradorTurma").with(ANYTHING).isVoid();
		colaboradorPresencaManager.removeByColaboradorTurma(null);
	}
	
	public void testPreparaLinhaEmBranco() throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		Collection<ColaboradorTurma> retorno = colaboradorPresencaManager.preparaLinhaEmBranco(colaboradorTurmas, 30, null);
		
		assertEquals(30, retorno.size());
	}
	
	public void testqtdDiaPresentesTurma() 
	{
		Curso curso = CursoFactory.getEntity(1L);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setCurso(curso);
		turma.setRealizada(true);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity(1L);
		diaTurma1.setTurma(turma);
		
		ColaboradorPresenca colaboradorPresenca1 = ColaboradorPresencaFactory.getEntity(1L);
		colaboradorPresenca1.setDiaTurma(diaTurma1);
		
		ColaboradorPresenca colaboradorPresenca2 = ColaboradorPresencaFactory.getEntity(2L);
		colaboradorPresenca2.setDiaTurma(diaTurma1);
		
		colaboradorPresencaDao.expects(once()).method("qtdDiaPresentesTurma").withAnyArguments().will(returnValue(2));
		
		assertEquals(new Integer(2), colaboradorPresencaManager.qtdDiaPresentesTurma(null, null, null, new Long[]{turma.getId()}, null, null));
	}
}
