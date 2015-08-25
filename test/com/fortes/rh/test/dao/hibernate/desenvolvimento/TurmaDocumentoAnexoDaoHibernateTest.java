package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Hibernate;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDocumentoAnexoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.DocumentoAnexoFactory;

public class TurmaDocumentoAnexoDaoHibernateTest extends GenericDaoHibernateTest<TurmaDocumentoAnexo>
{
	private TurmaDocumentoAnexoDao turmaDocumentoAnexoDao;
	private TurmaDao turmaDao;
	private DocumentoAnexoDao documentoAnexoDao;
	private ColaboradorDao colaboradorDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private CursoDao cursoDao;

	public GenericDao<TurmaDocumentoAnexo> getGenericDao()
	{
		return turmaDocumentoAnexoDao;
	}

	@Override
	public TurmaDocumentoAnexo getEntity() {
		return new TurmaDocumentoAnexo();
	}
	
	public void testRemoveByTurma()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		DocumentoAnexo documentoAnexo = DocumentoAnexoFactory.getEntity();
		documentoAnexo.setDescricao("Documento");
		documentoAnexo.setOrigemId(curso.getId());
		documentoAnexo.setOrigem('U');
		documentoAnexoDao.save(documentoAnexo);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso);
		turmaDao.save(turma1);
		
		TurmaDocumentoAnexo turmaDocumentoAnexo1 = new TurmaDocumentoAnexo();
		turmaDocumentoAnexo1.setTurma(turma1);
		turmaDocumentoAnexo1.setDocumentoAnexo(documentoAnexo );
		turmaDocumentoAnexoDao.save(turmaDocumentoAnexo1);
		turmaDocumentoAnexoDao.removeByTurma(turma1.getId());
		
		Collection<TurmaDocumentoAnexo> turmaDocumentoAnexos1 = turmaDocumentoAnexoDao.find(new String[]{"turma.id"},new Object[]{turma1.getId()});
		assertEquals("Turma 1", 0, turmaDocumentoAnexos1.size());
	}

	@SuppressWarnings("deprecation")
	public void testFindByColaborador()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		DocumentoAnexo documentoAnexo = DocumentoAnexoFactory.getEntity();
		documentoAnexo.setDescricao("Documento");
		documentoAnexo.setOrigemId(curso.getId());
		documentoAnexo.setOrigem('U');
		documentoAnexoDao.save(documentoAnexo);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso);
		turma1.setDataPrevIni(new Date(100, 01, 01));
		turma1.setDataPrevFim(new Date(120, 01, 01));
		turmaDao.save(turma1);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		TurmaDocumentoAnexo turmaDocumentoAnexo1 = new TurmaDocumentoAnexo();
		turmaDocumentoAnexo1.setTurma(turma1);
		turmaDocumentoAnexo1.setDocumentoAnexo(documentoAnexo);
		
		turmaDocumentoAnexoDao.save(turmaDocumentoAnexo1);
		
		Collection<TurmaDocumentoAnexo> turmaDocumentoAnexos1 = turmaDocumentoAnexoDao.findByColaborador(colaborador.getId());
		assertEquals("Turma 1", 1, turmaDocumentoAnexos1.size());
	}
	
	@SuppressWarnings("deprecation")
	public void testRemove() throws Exception {
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		DocumentoAnexo documentoAnexo = DocumentoAnexoFactory.getEntity();
		documentoAnexo.setDescricao("Documento");
		documentoAnexo.setOrigemId(curso.getId());
		documentoAnexo.setOrigem('U');
		documentoAnexoDao.save(documentoAnexo);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso);
		turma1.setDataPrevIni(new Date(100, 01, 01));
		turma1.setDataPrevFim(new Date(120, 01, 01));
		turmaDao.save(turma1);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma1);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		TurmaDocumentoAnexo turmaDocumentoAnexo1 = new TurmaDocumentoAnexo();
		turmaDocumentoAnexo1.setTurma(turma1);
		turmaDocumentoAnexo1.setDocumentoAnexo(documentoAnexo);
		
		turmaDocumentoAnexoDao.save(turmaDocumentoAnexo1);
		
		turmaDocumentoAnexoDao.remove(turmaDocumentoAnexo1.getId());
		
		Collection<TurmaDocumentoAnexo> turmaDocumentoAnexos1 = turmaDocumentoAnexoDao.findByColaborador(colaborador.getId());
		assertEquals("Turma 1", 0, turmaDocumentoAnexos1.size());
		
	}
	
	public void setTurmaDocumentoAnexoDao(TurmaDocumentoAnexoDao turmaDocumentoAnexoDao) {
		this.turmaDocumentoAnexoDao = turmaDocumentoAnexoDao;
	}
	
	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setDocumentoAnexoDao(DocumentoAnexoDao documentoAnexoDao) {
		this.documentoAnexoDao = documentoAnexoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao) {
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}
}