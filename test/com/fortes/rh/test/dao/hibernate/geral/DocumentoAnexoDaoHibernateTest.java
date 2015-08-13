package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDocumentoAnexoDao;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.DocumentoAnexoFactory;

public class DocumentoAnexoDaoHibernateTest extends GenericDaoHibernateTest<DocumentoAnexo>
{
	private DocumentoAnexoDao documentoAnexoDao;
	private CursoDao cursoDao;
	private TurmaDao turmaDao;
	private TurmaDocumentoAnexoDao turmaDocumentoAnexoDao;

	public DocumentoAnexo getEntity()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(null);
		documentoAnexo.setDescricao("documentoAnexo");
		documentoAnexo.setEtapaSeletiva(null);
		documentoAnexo.setData(new Date());
		documentoAnexo.setUrl("url");
		documentoAnexo.setOrigem('Z');
		documentoAnexo.setOrigemId(1L);

		return documentoAnexo;
	}

	public void testGetDocumentoAnexoByOrigemId()
	{
		Long origemId = 23423423L;
		char origem = 'W';

		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo.setOrigemId(origemId);
		documentoAnexo.setOrigem(origem);
		documentoAnexo = documentoAnexoDao.save(documentoAnexo);

		DocumentoAnexo documentoAnexo2 = getEntity();
		documentoAnexo2.setOrigemId(436546L);
		documentoAnexo2.setOrigem(origem);
		documentoAnexo2 = documentoAnexoDao.save(documentoAnexo2);

		Collection<DocumentoAnexo> retorno = documentoAnexoDao.getDocumentoAnexoByOrigemId(null,origem, origemId, null);

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjection()
	{
		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo = documentoAnexoDao.save(documentoAnexo);
		
		assertEquals(documentoAnexo, documentoAnexoDao.findByIdProjection(documentoAnexo.getId()));
	}
	
	public void testFindByTurma(){
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		DocumentoAnexo documentoAnexo = DocumentoAnexoFactory.getEntity();
		documentoAnexo.setDescricao("Documento");
		documentoAnexo.setOrigemId(curso.getId());
		documentoAnexo.setOrigem('U');
		documentoAnexoDao.save(documentoAnexo);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity();
		documentoAnexo2.setDescricao("Documento 2");
		documentoAnexo2.setOrigemId(curso.getId());
		documentoAnexo2.setOrigem('U');
		documentoAnexoDao.save(documentoAnexo2);
		
		DocumentoAnexo documentoAnexo3 = DocumentoAnexoFactory.getEntity();
		documentoAnexo3.setDescricao("Documento 3");
		documentoAnexo3.setOrigemId(curso.getId());
		documentoAnexo3.setOrigem('U');
		documentoAnexoDao.save(documentoAnexo3);
		
		Turma turma1 = TurmaFactory.getEntity();
		turmaDao.save(turma1);
		
		TurmaDocumentoAnexo turmaDocumentoAnexo1 = new TurmaDocumentoAnexo();
		turmaDocumentoAnexo1.setTurma(turma1);
		turmaDocumentoAnexo1.setDocumentoAnexo(documentoAnexo );
		turmaDocumentoAnexoDao.save(turmaDocumentoAnexo1);
		
		TurmaDocumentoAnexo turmaDocumentoAnexo2 = new TurmaDocumentoAnexo();
		turmaDocumentoAnexo2.setTurma(turma1);
		turmaDocumentoAnexo2.setDocumentoAnexo(documentoAnexo2 );
		turmaDocumentoAnexoDao.save(turmaDocumentoAnexo2);
		
		Turma turma2 = TurmaFactory.getEntity();
		turmaDao.save(turma2);
		
		TurmaDocumentoAnexo turmaDocumentoAnexo3 = new TurmaDocumentoAnexo();
		turmaDocumentoAnexo3.setTurma(turma2);
		turmaDocumentoAnexo3.setDocumentoAnexo(documentoAnexo3 );
		turmaDocumentoAnexoDao.save(turmaDocumentoAnexo3);
		
		Turma turma3 = TurmaFactory.getEntity();
		turmaDao.save(turma3);
		
		Collection<DocumentoAnexo> documentoAnexos = documentoAnexoDao.findByTurma(turma1.getId());
		assertEquals("Turma 1", 2, documentoAnexos.size());
		
		Collection<DocumentoAnexo> documentoAnexos2 = documentoAnexoDao.findByTurma(turma2.getId());
		assertEquals("Turma 2", 1, documentoAnexos2.size());
		
		Collection<DocumentoAnexo> documentoAnexos3 = documentoAnexoDao.findByTurma(turma3.getId());
		assertEquals("Turma 3", 0, documentoAnexos3.size());
	}

	public GenericDao<DocumentoAnexo> getGenericDao()
	{
		return documentoAnexoDao;
	}

	public void setDocumentoAnexoDao(DocumentoAnexoDao documentoAnexoDao)
	{
		this.documentoAnexoDao = documentoAnexoDao;
	}
	
	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}

	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setTurmaDocumentoAnexoDao(
			TurmaDocumentoAnexoDao turmaDocumentoAnexoDao) {
		this.turmaDocumentoAnexoDao = turmaDocumentoAnexoDao;
	}
}