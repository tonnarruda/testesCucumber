package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.util.DateUtil;

public class CandidatoSolicitacaoDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<CandidatoSolicitacao>
{
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private HistoricoCandidatoDao historicoCandidatoDao;
	@Autowired
	private EtapaSeletivaDao etapaSeletivaDao;

	private Candidato candidato;
	private Solicitacao solicitacao;
	private CandidatoSolicitacao candidatoSolicitacao;

	@Override
	public GenericDao<CandidatoSolicitacao> getGenericDao() {
		return candidatoSolicitacaoDao;
	}

	@Override
	public CandidatoSolicitacao getEntity() {
		solicitacao = new Solicitacao();
		solicitacao = solicitacaoDao.save(solicitacao);

		candidato = CandidatoFactory.getCandidato();
		candidato.getEndereco().setUf(null);
		candidato.getEndereco().setCidade(null);
		candidato.setContratado(false);
		candidatoDao.save(candidato);

		candidatoSolicitacao = new CandidatoSolicitacao();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);

		return candidatoSolicitacao;
	}

	private EtapaSeletiva testGetCandidatoSolicitacaoListDados()
	{
		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva1.setOrdem(1);
		etapaSeletivaDao.save(etapaSeletiva1);

		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva2.setOrdem(4);
		etapaSeletivaDao.save(etapaSeletiva2);

		candidatoSolicitacao = getEntity();
		candidatoSolicitacaoDao.save(candidatoSolicitacao);

		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity(etapaSeletiva1, DateUtil.criarDataMesAno(01, 02, 2009), candidatoSolicitacao);
		historicoCandidato1.setHoraIni("08:00");
		historicoCandidato1.setObservacao("HistoricoCandidato 1");
		historicoCandidatoDao.save(historicoCandidato1);

		HistoricoCandidato historicoCandidato2 = HistoricoCandidatoFactory.getEntity(etapaSeletiva2, DateUtil.criarDataMesAno(01, 02, 2009), candidatoSolicitacao);
		historicoCandidato2.setHoraIni("09:00");
		historicoCandidato2.setObservacao("HistoricoCandidato 2");
		historicoCandidatoDao.save(historicoCandidato2);
		
		return etapaSeletiva2;
	}

	@Test
	public void testGetCandidatoSolicitacaoListOrder() {
		EtapaSeletiva etapaSeletiva2 = testGetCandidatoSolicitacaoListDados();
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatoSolicitacaoList(null, null, solicitacao.getId(), null, null, null, false, true, null, null, null);
		assertEquals(1, candidatoSolicitacaos.size());

		CandidatoSolicitacao candidatoSolicitacaoTmp = (CandidatoSolicitacao) candidatoSolicitacaos.toArray()[0];
		assertEquals(etapaSeletiva2, candidatoSolicitacaoTmp.getEtapaSeletiva());
		assertEquals("HistoricoCandidato 2", candidatoSolicitacaoTmp.getObservacao());
	}
	
	@Test
	public void testGetCandidatoSolicitacaoListComCondicoes()
	{
		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva1.setOrdem(1);
		etapaSeletivaDao.save(etapaSeletiva1);
		
		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva2.setOrdem(4);
		etapaSeletivaDao.save(etapaSeletiva2);
		
		candidatoSolicitacao = getEntity();
		candidatoSolicitacao.setTriagem(false);
		candidatoSolicitacao = candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity(etapaSeletiva1, DateUtil.criarDataMesAno(01, 03, 2009), candidatoSolicitacao);
		historicoCandidatoDao.save(historicoCandidato1);
		
		HistoricoCandidato historicoCandidato2 = HistoricoCandidatoFactory.getEntity(etapaSeletiva2, DateUtil.criarDataMesAno(01, 02, 2009), candidatoSolicitacao);
		historicoCandidatoDao.save(historicoCandidato2);
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatoSolicitacaoList(null, null, solicitacao.getId(), etapaSeletiva1.getId(), null, null, false, true, null, null, null);
		assertEquals(1, candidatoSolicitacaos.size());
		
		CandidatoSolicitacao candidatoSolicitacaoTmp = (CandidatoSolicitacao) candidatoSolicitacaos.toArray()[0];
		assertEquals(etapaSeletiva1, candidatoSolicitacaoTmp.getEtapaSeletiva());
	}
}
