package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.PausaPreenchimentoVagasDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;

public class PausaPreenchimentoVagasDaoHibernateTest extends GenericDaoHibernateTest<PausaPreenchimentoVagas>
{

	private PausaPreenchimentoVagasDao pausaPreenchimentoVagasDao;
	private SolicitacaoDao solicitacaoDao;

	public GenericDao<PausaPreenchimentoVagas> getGenericDao()
	{
		return pausaPreenchimentoVagasDao;
	}
	
	public PausaPreenchimentoVagas getEntity() {
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("Teste Pausa Preenchimento Vagas");
		solicitacao = solicitacaoDao.save(solicitacao);
		
		PausaPreenchimentoVagas pausaPreenchimentoVagas = new PausaPreenchimentoVagas();
		pausaPreenchimentoVagas.setSolicitacao(solicitacao);
		return pausaPreenchimentoVagas;
	}

	public void testUltimaPausaBySolicitacaoId()
	{

		Date dataPausa = new Date();
		PausaPreenchimentoVagas pausaPreenchimentoVagas = getEntity();
		pausaPreenchimentoVagas.setDataPausa(dataPausa);
		pausaPreenchimentoVagasDao.save(pausaPreenchimentoVagas);
		pausaPreenchimentoVagas = pausaPreenchimentoVagasDao.findUltimaPausaBySolicitacaoId(pausaPreenchimentoVagas.getSolicitacao().getId());

		assertEquals(dataPausa, pausaPreenchimentoVagas.getDataPausa());
	}


	public void setPausaPreenchimentoVagasDao(
			PausaPreenchimentoVagasDao pausaPreenchimentoVagasDao) {
		this.pausaPreenchimentoVagasDao = pausaPreenchimentoVagasDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}


}
