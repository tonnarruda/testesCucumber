package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.CursoManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class CursoManagerTest extends MockObjectTestCase
{
	private CursoManagerImpl cursoManager = new CursoManagerImpl();
	private Mock colaboradorManager;
	private Mock cursoDao = null;

	protected void setUp() throws Exception
	{
		cursoDao = new Mock(CursoDao.class);
		cursoManager.setDao((CursoDao) cursoDao.proxy());
		colaboradorManager = new Mock(ColaboradorManager.class);
		cursoManager.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
	}

	public void testFindAllSelect()
	{
		Collection<Curso> cursos = new ArrayList<Curso>();

		cursoDao.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(cursos));
		Collection<Curso> cursosRetorno = cursoManager.findAllSelect(1L);

		assertEquals(cursos, cursosRetorno);
	}

	public void testFindByIdProjection()
	{
		Curso curso = CursoFactory.getEntity(1L);

		cursoDao.expects(once()).method("findByIdProjection").with(eq(curso.getId())).will(returnValue(curso));

		assertEquals(curso , cursoManager.findByIdProjection(curso.getId()));
	}

	public void testGetConteudoProgramatico()
	{
		Curso curso = CursoFactory.getEntity(1L);
		cursoDao.expects(once()).method("getConteudoProgramatico").with(eq(curso.getId())).will(returnValue("teste"));

		assertEquals("teste" , cursoManager.getConteudoProgramatico(curso.getId()));
	}

	public void testFindByFiltro()
	{
		Integer page=1, pagingSize=15;
		Long empresaId=1L;
		Curso curso = CursoFactory.getEntity();
		Collection<Curso> resultado = new ArrayList<Curso>();
		resultado.add(curso);

		cursoDao.expects(once()).method("findByFiltro").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(resultado));
		assertEquals(resultado, cursoManager.findByFiltro(page, pagingSize, curso, empresaId));
	}

	public void testGetCount()
	{
		Long empresaId=1L;
		Curso curso = CursoFactory.getEntity();
		cursoDao.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));
		assertEquals(Integer.valueOf(0), cursoManager.getCount(curso, empresaId));
	}

	public void testSomaCargaHoraria() {

		Curso curso1 = CursoFactory.getEntity();
		curso1.setCargaHoraria(null);
		
		Curso curso2 = CursoFactory.getEntity();
		curso2.setCargaHoraria(135);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso2);
		
		Turma turma3 = TurmaFactory.getEntity();
		turma3.setCurso(curso2);
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		turmas.add(turma2);
		turmas.add(turma3);
		
		assertEquals("4:30", cursoManager.somaCargaHoraria(turmas));
	}
	
	public void testClonarParaAMesmaEmpresa(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Curso curso = CursoFactory.getEntity(1L);
		curso.setEmpresa(empresa);
		curso.setAvaliacaoCursos(Arrays.asList(AvaliacaoCursoFactory.getEntity()));
		curso.setAtitudes(Arrays.asList(AtitudeFactory.getEntity()));
		curso.setConhecimentos(ConhecimentoFactory.getCollection());
		curso.setHabilidades(HabilidadeFactory.getCollection());
		curso.setCertificacaos(Arrays.asList(CertificacaoFactory.getEntity()));
		curso.setTurmas(Arrays.asList(TurmaFactory.getEntity()));
		curso.setEmpresasParticipantes(Arrays.asList(EmpresaFactory.getEmpresa()));
		
		cursoDao.expects(once()).method("findById").with(eq(curso.getId())).will(returnValue(curso));
		cursoDao.expects(once()).method("save").with(ANYTHING);
		
		Exception exception = null;

    	try
		{
    		cursoManager.clonar(curso.getId(), empresa.getId(), null);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
    	assertNull(exception);
    	
	}
	
	public void testClonarParaOutraEmpresa(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Curso curso = CursoFactory.getEntity(1L);
		curso.setEmpresa(empresa);
		curso.setAvaliacaoCursos(Arrays.asList(AvaliacaoCursoFactory.getEntity()));
		curso.setAtitudes(Arrays.asList(AtitudeFactory.getEntity()));
		curso.setConhecimentos(ConhecimentoFactory.getCollection());
		curso.setHabilidades(HabilidadeFactory.getCollection());
		curso.setCertificacaos(Arrays.asList(CertificacaoFactory.getEntity()));
		curso.setTurmas(Arrays.asList(TurmaFactory.getEntity()));
		curso.setEmpresasParticipantes(Arrays.asList(EmpresaFactory.getEmpresa()));
		
		cursoDao.expects(atLeastOnce()).method("findById").with(eq(curso.getId())).will(returnValue(curso));
		cursoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		
		Exception exception = null;

    	try
		{
    		cursoManager.clonar(curso.getId(), empresa.getId(), new Long[]{2L,3L});
		}
		catch (Exception e)
		{
			exception = e;
		}

    	assertNull(exception);
    	
	}
}
