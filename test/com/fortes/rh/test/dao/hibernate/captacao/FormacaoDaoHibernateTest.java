package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;

public class FormacaoDaoHibernateTest extends GenericDaoHibernateTest<Formacao>
{
	private FormacaoDao formacaoDao;
	private CandidatoDao candidatoDao;
	private ColaboradorDao colaboradorDao;

	public Formacao getEntity()
	{
		Formacao formacao = new Formacao();
		formacao.setCandidato(null);
		formacao.setConclusao("conclusao");
		formacao.setCurso("curso");
		formacao.setId(null);
		formacao.setLocal("local");
		formacao.setSituacao('a');
		formacao.setTipo('a');
		return formacao;
	}

	public GenericDao<Formacao> getGenericDao()
	{
		return formacaoDao;
	}

	public void testRemoveCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato = candidatoDao.save(candidato);

		Formacao formacao = FormacaoFactory.getEntity();
		formacao.setCandidato(candidato);
		formacao = formacaoDao.save(formacao);

		formacaoDao.removeCandidato(candidato);
		assertNull(formacaoDao.findById(formacao.getId(), null));
	}
	
	public void testRemoveColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		Formacao formacao = FormacaoFactory.getEntity();
		formacao.setColaborador(colaborador);
		formacao = formacaoDao.save(formacao);
		
		formacaoDao.removeColaborador(colaborador);
		assertNull(formacaoDao.findById(formacao.getId(), null));
	}
	
	public void testFindByCandidato(){

		Candidato candidato1 = CandidatoFactory.getCandidato();
		Candidato candidato2 = CandidatoFactory.getCandidato();

		candidato1.getEndereco().setUf(null);
		candidato1.getEndereco().setCidade(null);

		candidato2.getEndereco().setCidade(null);
		candidato2.getEndereco().setUf(null);

		candidato1 = candidatoDao.save(candidato1);
		candidato2 = candidatoDao.save(candidato2);

		Formacao formacao1 = FormacaoFactory.getEntity();
		formacao1.setCandidato(candidato1);
		formacao1 = formacaoDao.save(formacao1);

		Formacao formacao2 = FormacaoFactory.getEntity();
		formacao2.setCandidato(candidato1);
		formacao2 = formacaoDao.save(formacao2);

		Formacao formacao3 = FormacaoFactory.getEntity();
		formacao3.setCandidato(candidato2);
		formacao3 = formacaoDao.save(formacao3);

		Collection<Formacao> formacaos = formacaoDao.findByCandidato(candidato1.getId());

		assertEquals("Test 1", 2, formacaos.size());

		formacaos = formacaoDao.findByCandidato(candidato2.getId());

		assertEquals("Test 2", 1, formacaos.size());
	}
	
	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		Formacao formacao = FormacaoFactory.getEntity();
		formacao.setColaborador(colaborador);
		formacao = formacaoDao.save(formacao);
		
		Collection<Formacao> formacaos = formacaoDao.findByColaborador(colaborador.getId());
		
		assertEquals(1, formacaos.size());
	}
	
	public void testFindInCandidatos()
	{
		Candidato candidato = CandidatoFactory.getCandidato();		
		candidato = candidatoDao.save(candidato);
		
		Formacao formacao = FormacaoFactory.getEntity();
		formacao.setCandidato(candidato);
		formacao = formacaoDao.save(formacao);
				
		Long[] candidatoIds = new Long[]{candidato.getId()};
		
		Collection<Formacao> formacaos = formacaoDao.findInCandidatos(candidatoIds);
		
		assertEquals(1, formacaos.size());
	}

	public void setFormacaoDao(FormacaoDao formacaoDao)
	{
		this.formacaoDao = formacaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

}
