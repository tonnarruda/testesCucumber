package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;

public class SolicitacaoAvaliacaoDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoAvaliacao>
{
	private AvaliacaoDao avaliacaoDao;
	private SolicitacaoDao solicitacaoDao;
	private SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao;
	private CandidatoDao candidatoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	
	@Override
	public GenericDao<SolicitacaoAvaliacao> getGenericDao() 
	{
		return solicitacaoAvaliacaoDao;
	}

	@Override
	public SolicitacaoAvaliacao getEntity() 
	{
		SolicitacaoAvaliacao solicitacaoAvaliacao = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao.setResponderModuloExterno(false);	
		return solicitacaoAvaliacao;
	}

	public void testRemove()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao.setAvaliacao(avaliacao);
		solicitacaoAvaliacao.setSolicitacao(solicitacao);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao);
		
		solicitacaoAvaliacaoDao.remove(solicitacaoAvaliacao);

		Exception ex = null;

		try {
			solicitacaoAvaliacao = solicitacaoAvaliacaoDao.findById(solicitacaoAvaliacao.getId());
		} catch (Exception e) {
			ex = e;
		}

		assertNotNull(ex);
		assertTrue(ex instanceof HibernateObjectRetrievalFailureException);
	}
	
	public void testRemoveBySolicitacaoId()
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);

		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao1 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao1.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao1.setSolicitacao(solicitacao1);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao2.setSolicitacao(solicitacao2);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);

		SolicitacaoAvaliacao solicitacaoAvaliacao3 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao3.setAvaliacao(avaliacao2);
		solicitacaoAvaliacao3.setSolicitacao(solicitacao2);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao3);
		
		solicitacaoAvaliacaoDao.removeBySolicitacaoId(solicitacao2.getId());
		
		Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos =  solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao1.getId(), null);
		assertEquals(1, solicitacaoAvaliacaos.size());

		solicitacaoAvaliacaos =  solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao2.getId(), null);
		assertEquals(0, solicitacaoAvaliacaos.size());
	}
	
	public void testFindBySolicitacaoId()
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);

		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao1);
		
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao1 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao1.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao1.setSolicitacao(solicitacao1);
		solicitacaoAvaliacao1.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao2.setSolicitacao(solicitacao2);
		solicitacaoAvaliacao2.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);

		SolicitacaoAvaliacao solicitacaoAvaliacao3 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao3.setAvaliacao(avaliacao2);
		solicitacaoAvaliacao3.setSolicitacao(solicitacao2);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao3);
		
		assertEquals(1, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao1.getId(), null).size());
		assertEquals(2, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao2.getId(), null).size());
		assertEquals(1, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao2.getId(), true).size());
		assertEquals(1, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao2.getId(), false).size());
	}
	
	public void testSetResponderModuloExterno()
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);

		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao1 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao1.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao1.setSolicitacao(solicitacao);
		solicitacaoAvaliacao1.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao2.setSolicitacao(solicitacao);
		solicitacaoAvaliacao2.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);

		SolicitacaoAvaliacao solicitacaoAvaliacao3 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao3.setAvaliacao(avaliacao2);
		solicitacaoAvaliacao3.setSolicitacao(solicitacao);
		solicitacaoAvaliacao3.setResponderModuloExterno(false);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao3);
		
		assertEquals(3, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao.getId(), null).size());
		assertEquals(2, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao.getId(), true).size());
		assertEquals(1, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao.getId(), false).size());
		
		solicitacaoAvaliacaoDao.setResponderModuloExterno(solicitacao.getId(), null, false);

		assertEquals(3, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao.getId(), false).size());

		solicitacaoAvaliacaoDao.setResponderModuloExterno(solicitacao.getId(), new Long[] { solicitacaoAvaliacao1.getId(), solicitacaoAvaliacao3.getId() }, true);

		assertEquals(2, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao.getId(), true).size());
		assertEquals(1, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao.getId(), false).size());
	}
	
	public void testFindAvaliacaoesNaoRespondidas()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		Avaliacao avaliacao3 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao3);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao1 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao1.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao1.setSolicitacao(solicitacao);
		solicitacaoAvaliacao1.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setAvaliacao(avaliacao2);
		solicitacaoAvaliacao2.setSolicitacao(solicitacao);
		solicitacaoAvaliacao2.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao3 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao3.setAvaliacao(avaliacao3);
		solicitacaoAvaliacao3.setSolicitacao(solicitacao);
		solicitacaoAvaliacao3.setResponderModuloExterno(false);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao3);
		
		assertEquals(2, solicitacaoAvaliacaoDao.findAvaliacaoesNaoRespondidas(solicitacao.getId(), candidato.getId()).size());
		
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setSolicitacao(solicitacao);
		colaboradorQuestionario.setCandidato(candidato);
		colaboradorQuestionario.setAvaliacao(avaliacao1);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos = solicitacaoAvaliacaoDao.findAvaliacaoesNaoRespondidas(solicitacao.getId(), candidato.getId());
		assertEquals(1, solicitacaoAvaliacaos.size());
		SolicitacaoAvaliacao solicitacaoAvaliacao = solicitacaoAvaliacaos.toArray(new SolicitacaoAvaliacao[] {})[0];
		assertEquals(avaliacao2.getId(), solicitacaoAvaliacao.getAvaliacaoId());
	}
	
	public void setSolicitacaoAvaliacaoDao(SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao) {
		this.solicitacaoAvaliacaoDao = solicitacaoAvaliacaoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setColaboradorQuestionarioDao(
			ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}
}
