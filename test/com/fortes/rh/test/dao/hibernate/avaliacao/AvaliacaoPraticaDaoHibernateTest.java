package com.fortes.rh.test.dao.hibernate.avaliacao;

import java.util.Arrays;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;

public class AvaliacaoPraticaDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoPratica>
{
	private AvaliacaoPraticaDao avaliacaoPraticaDao;
	private CertificacaoDao certificacaoDao;

	public AvaliacaoPratica getEntity()
	{
		return AvaliacaoPraticaFactory.getEntity();
	}

	public GenericDao<AvaliacaoPratica> getGenericDao()
	{
		return avaliacaoPraticaDao;
	}
	
	public void testFindByCertificacaoId() 
	{
		Certificacao certificacao = CertificacaoFactory.getEntity();
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		Collection<AvaliacaoPratica> avaliacoesPraticas = Arrays.asList(avaliacaoPratica);
		
		certificacao.setAvaliacoesPraticas(avaliacoesPraticas);
		
		certificacaoDao.save(certificacao);
		avaliacaoPraticaDao.save(avaliacaoPratica);

		assertEquals(1, avaliacaoPraticaDao.findByCertificacaoId(certificacao.getId()).size());
	}

	public void setAvaliacaoPraticaDao(AvaliacaoPraticaDao avaliacaoPraticaDao)
	{
		this.avaliacaoPraticaDao = avaliacaoPraticaDao;
	}

	public void setCertificacaoDao(CertificacaoDao certificacaoDao) {
		this.certificacaoDao = certificacaoDao;
	}
}
