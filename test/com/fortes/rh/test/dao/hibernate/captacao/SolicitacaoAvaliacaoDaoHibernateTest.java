package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;

public class SolicitacaoAvaliacaoDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoAvaliacao>
{
	private AvaliacaoDao avaliacaoDao;
	private SolicitacaoDao solicitacaoDao;
	private SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao;
	
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
		
		Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos =  solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao1.getId());
		assertEquals(1, solicitacaoAvaliacaos.size());

		solicitacaoAvaliacaos =  solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao2.getId());
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
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao2.setSolicitacao(solicitacao2);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);

		SolicitacaoAvaliacao solicitacaoAvaliacao3 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao3.setAvaliacao(avaliacao2);
		solicitacaoAvaliacao3.setSolicitacao(solicitacao2);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao3);
		
		assertEquals(1, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao1.getId()).size());
		assertEquals(2, solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacao2.getId()).size());
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
}
