package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorCertificacaoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorCertificacao>
{
	private ColaboradorCertificacaoDao colaboradorCertificacaoDao;
	private ColaboradorDao colaboradorDao;
	private CertificacaoDao certificacaoDao;

	@Override
	public ColaboradorCertificacao getEntity()
	{
		return ColaboradorCertificacaoFactory.getEntity();
	}
	
	@Override
	public GenericDao<ColaboradorCertificacao> getGenericDao()
	{
		return colaboradorCertificacaoDao;
	}
	
	public void testFindByColaboradorIdAndCertificacaoId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Colaborador colaboradorNaoCertificado = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorNaoCertificado);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		ColaboradorCertificacao colaboradorCertificacao2 = new ColaboradorCertificacao();
		colaboradorCertificacao2.setColaborador(colaborador);
		colaboradorCertificacao2.setCertificacao(certificacao);
		colaboradorCertificacao2.setData(DateUtil.criarDataMesAno(1, 2, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao2);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = colaboradorCertificacaoDao.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId());
		Collection<ColaboradorCertificacao> colaboradorNaoCertificados = colaboradorCertificacaoDao.findByColaboradorIdAndCertificacaoId(colaboradorNaoCertificado.getId(), certificacao.getId());
		
		assertEquals(2, colaboradorCertificacaos.size());
		assertEquals(0, colaboradorNaoCertificados.size());
	}

	public void setColaboradorCertificacaoDao(ColaboradorCertificacaoDao colaboradorCertificacaoDao)
	{
		this.colaboradorCertificacaoDao = colaboradorCertificacaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}


	public void setCertificacaoDao(CertificacaoDao certificacaoDao) {
		this.certificacaoDao = certificacaoDao;
	}
}