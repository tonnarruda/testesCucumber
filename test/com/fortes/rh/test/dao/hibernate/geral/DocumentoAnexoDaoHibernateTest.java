package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDocumentoAnexoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.DocumentoAnexoFactory;
import com.fortes.rh.util.DateUtil;

public class DocumentoAnexoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<DocumentoAnexo>
{
	@Autowired private DocumentoAnexoDao documentoAnexoDao;
	@Autowired private CandidatoDao candidatoDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private CursoDao cursoDao;
	@Autowired private TurmaDao turmaDao;
	@Autowired private TurmaDocumentoAnexoDao turmaDocumentoAnexoDao;

	public DocumentoAnexo getEntity()
	{
		return DocumentoAnexoFactory.getEntity();
	}

	public GenericDao<DocumentoAnexo> getGenericDao()
	{
		return documentoAnexoDao;
	}

	@Test
	public void testGetDocumentoAnexoByOrigemId()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);

		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, curso.getId(), OrigemAnexo.CURSO, "Documento", new Date());
		documentoAnexoDao.save(documentoAnexo1);

		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATO, "Documento 2", null);
		documentoAnexoDao.save(documentoAnexo2);

		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.CURSO,curso.getId(), null);

		Assert.assertEquals(1, documentosAnexos.size());
		Assert.assertEquals("Documentos do curso", curso.getId(), documentosAnexos.iterator().next().getOrigemId());
	}
	
	@Test
	public void testGetDocumentoAnexoByOrigemIdColaboradorComCandidatoSemDocumento()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setCandidato(candidato);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, colaborador1.getId(), OrigemAnexo.COLABORADOR, "Documento", null);
		documentoAnexoDao.save(documentoAnexo1);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, colaborador2.getId(), OrigemAnexo.COLABORADOR, "Documento 2", null);
		documentoAnexoDao.save(documentoAnexo2);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.COLABORADOR,colaborador1.getId(), colaborador1.getCandidato().getId());
		
		Assert.assertEquals(1, documentosAnexos.size());
		Assert.assertEquals("Documentos somente do colaborador", colaborador1.getId(), documentosAnexos.iterator().next().getOrigemId());
	}
	
	@Test
	public void testGetDocumentoAnexoByOrigemIdColaboradorSemCandidato()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, colaborador1.getId(), OrigemAnexo.COLABORADOR, "Documento", null);
		documentoAnexoDao.save(documentoAnexo1);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, colaborador2.getId(), OrigemAnexo.COLABORADOR, "Documento 2", null);
		documentoAnexoDao.save(documentoAnexo2);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.COLABORADOR,colaborador1.getId(), null);
		
		Assert.assertEquals(1, documentosAnexos.size());
		Assert.assertEquals("Documentos somente do colaborador", colaborador1.getId(), documentosAnexos.iterator().next().getOrigemId());
	}
	
	@Test
	public void testGetDocumentoAnexoByOrigemIdPorColaboradorComCandidatoComDocumento()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setCandidato(candidato);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, colaborador1.getId(), OrigemAnexo.COLABORADOR, "Documento 1", DateUtil.incrementaDias(new Date(), -2));
		documentoAnexoDao.save(documentoAnexo1);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, colaborador2.getId(), OrigemAnexo.COLABORADOR, "Documento 2", DateUtil.incrementaDias(new Date(), -1));
		documentoAnexoDao.save(documentoAnexo2);
		
		DocumentoAnexo documentoAnexo3 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATO, "Documento 3", DateUtil.incrementaDias(new Date(), -3));
		documentoAnexoDao.save(documentoAnexo3);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.COLABORADOR,colaborador1.getId(), colaborador1.getCandidato().getId());
		
		Assert.assertEquals(2, documentosAnexos.size());
		Assert.assertEquals("Documento do candidato", candidato.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[0]).getOrigemId());
		Assert.assertEquals("Documento do colaborador", colaborador1.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[1]).getOrigemId());
	}
	
	@Test
	public void testGetDocumentoAnexoByOrigemIdPorColaboradorComApenasCandidatoComDocumento()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setCandidato(candidato);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, colaborador2.getId(), OrigemAnexo.COLABORADOR, "Documento 2", DateUtil.incrementaDias(new Date(), -1));
		documentoAnexoDao.save(documentoAnexo2);
		
		DocumentoAnexo documentoAnexo3 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATO, "Documento 3", DateUtil.incrementaDias(new Date(), -3));
		documentoAnexoDao.save(documentoAnexo3);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.COLABORADOR,colaborador1.getId(), colaborador1.getCandidato().getId());
		
		Assert.assertEquals(1, documentosAnexos.size());
		Assert.assertEquals("Documento do candidato", candidato.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[0]).getOrigemId());
	}
	
	@Test
	public void testGetDocumentoAnexoByOrigemIdPorColaboradorComCandidatoExternoComDocumento()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setCandidato(candidato);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, colaborador1.getId(), OrigemAnexo.COLABORADOR, "Documento 1", DateUtil.incrementaDias(new Date(), -2));
		
		documentoAnexoDao.save(documentoAnexo1);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, colaborador2.getId(), OrigemAnexo.COLABORADOR, "Documento 2", DateUtil.incrementaDias(new Date(), -1));
		documentoAnexoDao.save(documentoAnexo2);
		
		DocumentoAnexo documentoAnexo3 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATOEXTERNO, "Documento 3", DateUtil.incrementaDias(new Date(), 0));
		documentoAnexoDao.save(documentoAnexo3);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.COLABORADOR,colaborador1.getId(), candidato.getId());
		
		Assert.assertEquals(2, documentosAnexos.size());
		Assert.assertEquals("Documento do colaborador", colaborador1.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[0]).getOrigemId());
		Assert.assertEquals("Documento do candidato", candidato.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[1]).getOrigemId());
	}

	@Test
	public void testGetDocumentoAnexoByOrigemIdPorCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, colaborador.getId(), OrigemAnexo.COLABORADOR, "Documento 2", DateUtil.incrementaDias(new Date(), -1));
		documentoAnexoDao.save(documentoAnexo1);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATO, "Documento 3", DateUtil.incrementaDias(new Date(), -3));
		documentoAnexoDao.save(documentoAnexo2);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.CANDIDATO,candidato.getId(), null);
		
		Assert.assertEquals(1, documentosAnexos.size());
		Assert.assertEquals("Documento do candidato", candidato.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[0]).getOrigemId());
	}
	
	@Test
	public void testGetDocumentoAnexoByOrigemIdPorCandidatoComCandidatoExternoComDocumento()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		DocumentoAnexo documentoAnexo1 = DocumentoAnexoFactory.getEntity(null, colaborador.getId(), OrigemAnexo.COLABORADOR, "Documento 1", DateUtil.incrementaDias(new Date(), -1));
		documentoAnexoDao.save(documentoAnexo1);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATO, "Documento 2", DateUtil.incrementaDias(new Date(), -3));
		documentoAnexoDao.save(documentoAnexo2);
		
		DocumentoAnexo documentoAnexo3 = DocumentoAnexoFactory.getEntity(null, candidato.getId(), OrigemAnexo.CANDIDATOEXTERNO, "Documento 3", DateUtil.incrementaDias(new Date(), 0));
		documentoAnexoDao.save(documentoAnexo3);
		
		Collection<DocumentoAnexo> documentosAnexos = documentoAnexoDao.getDocumentoAnexoByOrigemId(OrigemAnexo.CANDIDATO,candidato.getId(), null);
		
		Assert.assertEquals(2, documentosAnexos.size());
		Assert.assertEquals("Documento do candidato", documentoAnexo2.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[0]).getId());
		Assert.assertEquals("Documento do candidato externo", documentoAnexo3.getId(), ((DocumentoAnexo)documentosAnexos.toArray()[1]).getId());
	}
	
	@Test
	public void testFindByIdProjection()
	{
		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo = documentoAnexoDao.save(documentoAnexo);
		
		Assert.assertEquals(documentoAnexo, documentoAnexoDao.findByIdProjection(documentoAnexo.getId()));
	}
	
	@Test
	public void testFindByTurma(){
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		DocumentoAnexo documentoAnexo = DocumentoAnexoFactory.getEntity(null, curso.getId(), OrigemAnexo.CURSO, "Documento", null);
		documentoAnexoDao.save(documentoAnexo);
		
		DocumentoAnexo documentoAnexo2 = DocumentoAnexoFactory.getEntity(null, curso.getId(), OrigemAnexo.CURSO, "Documento 2", null);
		documentoAnexoDao.save(documentoAnexo2);
		
		DocumentoAnexo documentoAnexo3 = DocumentoAnexoFactory.getEntity(null, curso.getId(), OrigemAnexo.CURSO, "Documento 3", null);
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
		Assert.assertEquals("Turma 1", 2, documentoAnexos.size());
		
		Collection<DocumentoAnexo> documentoAnexos2 = documentoAnexoDao.findByTurma(turma2.getId());
		Assert.assertEquals("Turma 2", 1, documentoAnexos2.size());
		
		Collection<DocumentoAnexo> documentoAnexos3 = documentoAnexoDao.findByTurma(turma3.getId());
		Assert.assertEquals("Turma 3", 0, documentoAnexos3.size());
	}

}