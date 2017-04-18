package com.fortes.rh.test.dao.hibernate.pesquisa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class ColaboradorQuestionarioDaoHibernateTest_JUnit4  extends DaoHibernateAnnotationTest
{
	@Autowired
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	@Autowired
	private AvaliacaoDao avaliacaoDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;

	@Test
	public void testGetAvaliacoesBySolicitacaoIdAndCandidatoSolicitacaoId(){

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		Candidato candidato3 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato3);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacaoDao.save(candidatoSolicitacao1);
		
		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		CandidatoSolicitacao candidatoSolicitacao3 = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao3.setCandidato(candidato3);
		candidatoSolicitacaoDao.save(candidatoSolicitacao3);

		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacao(avaliacao1);
		colaboradorQuestionario1.setCandidato(candidato1);
		colaboradorQuestionario1.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);		
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacao(avaliacao2);
		colaboradorQuestionario2.setCandidato(candidato2);
		colaboradorQuestionario2.setSolicitacao(solicitacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		Long[] candidatosSolicitacaoIds = new Long[]{candidatoSolicitacao1.getId(),candidatoSolicitacao2.getId(),candidatoSolicitacao3.getId()};
		
		assertEquals(2, colaboradorQuestionarioDao.getAvaliacoesBySolicitacaoIdAndCandidatoSolicitacaoId(solicitacao.getId(), candidatosSolicitacaoIds).size());
	}
}