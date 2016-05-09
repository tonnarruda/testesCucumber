package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorAvaliacaoPraticaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorAvaliacaoPratica>
{
	private ColaboradorAvaliacaoPraticaDao colaboradorAvaliacaoPraticaDao;
	private ColaboradorCertificacaoDao colaboradorCertificacaoDao;
	private AvaliacaoPraticaDao avaliacaoPraticaDao;
	private ColaboradorDao colaboradorDao;
	private CertificacaoDao certificacaoDao;

	public ColaboradorAvaliacaoPratica getEntity()
	{
		return ColaboradorAvaliacaoPraticaFactory.getEntity();
	}

	public GenericDao<ColaboradorAvaliacaoPratica> getGenericDao()
	{
		return colaboradorAvaliacaoPraticaDao;
	}

	public void testFindByColaboradorIdAndCertificacaoId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 12, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		avaliacaoPratica.setNotaMinima(80.0);
		avaliacaoPraticaDao.save(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity();
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		colaboradorAvaliacaoPratica.setNota(90.0);
		colaboradorAvaliacaoPratica.setData(DateUtil.criarDataMesAno(1, 10, 2015));
		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = colaboradorAvaliacaoPraticaDao.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId(), null, null, true);
		
		assertEquals(1, colaboradorAvaliacaoPraticas.size());
		
		ColaboradorAvaliacaoPratica result = ((ColaboradorAvaliacaoPratica) colaboradorAvaliacaoPraticas.toArray()[0]);
		
		assertEquals(90.0, result.getNota());
		assertEquals(80.0, result.getAvaliacaoPratica().getNotaMinima());
	}
	
	public void testFindByColaboradorIdAndCertificacaoIdSemColaboradorCertificacaoId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		avaliacaoPratica.setNotaMinima(80.0);
		avaliacaoPraticaDao.save(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity();
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		colaboradorAvaliacaoPratica.setNota(90.0);
		colaboradorAvaliacaoPratica.setData(DateUtil.criarDataMesAno(1, 10, 2015));
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = colaboradorAvaliacaoPraticaDao.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), null, null, null, true);
		
		assertEquals(1, colaboradorAvaliacaoPraticas.size());
		
		ColaboradorAvaliacaoPratica result = ((ColaboradorAvaliacaoPratica) colaboradorAvaliacaoPraticas.toArray()[0]);
		
		assertEquals(90.0, result.getNota());
		assertEquals(80.0, result.getAvaliacaoPratica().getNotaMinima());
	}
	
	public void testRemoveColaboradorAvaliacaoPraticaByColaboradorCertificacaoId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 12, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		avaliacaoPratica.setNotaMinima(80.0);
		avaliacaoPraticaDao.save(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity();
		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		colaboradorAvaliacaoPratica.setNota(90.0);
		colaboradorAvaliacaoPratica.setData(DateUtil.criarDataMesAno(1, 10, 2015));
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica);

		colaboradorAvaliacaoPraticaDao.removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(colaboradorCertificacao.getId());
	}
	
	
	public void testFindByCertificacaoIdAndColaboradoresIds(){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(colaborador, certificacao, DateUtil.criarDataMesAno(1, 12, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(colaborador, certificacao, DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacaoDao.save(colaboradorCertificacao2);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		avaliacaoPraticaDao.save(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao, colaborador, certificacao, avaliacaoPratica, 90.0);
		colaboradorAvaliacaoPratica.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica);
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao2, colaborador, certificacao, avaliacaoPratica, 40.0);
		colaboradorAvaliacaoPratica2.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica2);
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica3 = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao2, colaborador, certificacao, avaliacaoPratica, 90.0);
		colaboradorAvaliacaoPratica3.setData(DateUtil.criarDataMesAno(1, 2, 2016));
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica3);

		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaDao.findByCertificacaoIdAndColaboradoresIds(certificacao.getId(), new Long[]{colaborador.getId()});
		assertEquals(3, colaboradorAvaliacoesPraticas.size());
		assertEquals(colaboradorCertificacao2.getId(), ((ColaboradorAvaliacaoPratica) colaboradorAvaliacoesPraticas.toArray()[0]).getColaboradorCertificacao().getId());
	}
	
	public void setColaboradorAvaliacaoPraticaDao(ColaboradorAvaliacaoPraticaDao colaboradorAvaliacaoPraticaDao)
	{
		this.colaboradorAvaliacaoPraticaDao = colaboradorAvaliacaoPraticaDao;
	}

	public void setAvaliacaoPraticaDao(AvaliacaoPraticaDao avaliacaoPraticaDao) {
		this.avaliacaoPraticaDao = avaliacaoPraticaDao;
	}

	public void setColaboradorCertificacaoDao(
			ColaboradorCertificacaoDao colaboradorCertificacaoDao) {
		this.colaboradorCertificacaoDao = colaboradorCertificacaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setCertificacaoDao(CertificacaoDao certificacaoDao) {
		this.certificacaoDao = certificacaoDao;
	}
}
