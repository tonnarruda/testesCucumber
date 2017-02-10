package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
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
	private ColaboradorDao colaboradorDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private EtapaSeletivaDao etapaSeletivaDao;
	@Autowired
	private HistoricoCandidatoDao historicoCandidatoDao;
	
	public CandidatoSolicitacao getEntity()
	{
		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();

		candidatoSolicitacao.setId(null);
		candidatoSolicitacao.setTriagem(true);
		return candidatoSolicitacao;
	}

	public GenericDao<CandidatoSolicitacao> getGenericDao() {
		return candidatoSolicitacaoDao;
	}

	@Test
	public void testFindByHistoricoColaboradorId() {
		CandidatoSolicitacao candidatoSolicitacao = criaCandidatoSolicitacao();
		HistoricoColaborador historicoColaborador = criaHistoricoColaboradorAndColaborador(candidatoSolicitacao);

		CandidatoSolicitacao candidatoSolicitacaoRetorno = candidatoSolicitacaoDao.findByHistoricoColaboradorId(historicoColaborador.getId());
		assertEquals(candidatoSolicitacao.getId(), candidatoSolicitacaoRetorno.getId());
	}
	
	@Test
	public void testFindByHistoricoColaboradorIdSemCandidatoSolicitacaoRelacionadoAoHistorico() {
		criaCandidatoSolicitacao();
		HistoricoColaborador historicoColaborador = criaHistoricoColaboradorAndColaborador(null);

		CandidatoSolicitacao candidatoSolicitacaoRetorno = candidatoSolicitacaoDao.findByHistoricoColaboradorId(historicoColaborador.getId());
		assertNull(candidatoSolicitacaoRetorno.getId());
	}
	
	@Test
	public void testUpdateStatusAndRemoveDataContratacaoOrPromocao(){
		Solicitacao solicitacao1 = criaSolicitacao();
		Candidato candidato = criaCandidato();
		
		CandidatoSolicitacao candidatoSolicitacao = criaCandidatoSolicitacao(candidato, solicitacao1, StatusCandidatoSolicitacao.INDIFERENTE);
		candidatoSolicitacaoDao.updateStatusAndRemoveDataContratacaoOrPromocao(candidatoSolicitacao.getId(), StatusCandidatoSolicitacao.CONTRATADO);
		assertEquals(StatusCandidatoSolicitacao.CONTRATADO, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao.getId()).getStatus());
	}
	
	@Test
	public void testUpdateStatusParaIndiferenteEmSolicitacoesEmAndamento() {
		Solicitacao solicitacao1 = criaSolicitacao();
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setEncerrada(true);
		solicitacaoDao.save(solicitacao2);
		
		Candidato candidato1 = criaCandidato();
		
		CandidatoSolicitacao candidatoSolicitacao1 = criaCandidatoSolicitacao(candidato1, solicitacao1, StatusCandidatoSolicitacao.CONTRATADO);
		CandidatoSolicitacao candidatoSolicitacao2 = criaCandidatoSolicitacao(candidato1, solicitacao1, StatusCandidatoSolicitacao.APROMOVER);
		criaCandidatoSolicitacao(candidato1, solicitacao1, StatusCandidatoSolicitacao.INDIFERENTE);
		CandidatoSolicitacao candidatoSolicitacao4 = criaCandidatoSolicitacao(candidato1, solicitacao2, StatusCandidatoSolicitacao.CONTRATADO);
		CandidatoSolicitacao candidatoSolicitacao5 = criaCandidatoSolicitacao(candidato1, solicitacao2, StatusCandidatoSolicitacao.PROMOVIDO);
		CandidatoSolicitacao candidatoSolicitacao6 = criaCandidatoSolicitacao(candidato1, solicitacao2, StatusCandidatoSolicitacao.APROMOVER);

		candidatoSolicitacaoDao.updateStatusParaIndiferenteEmSolicitacoesEmAndamento(candidato1.getId());
		assertEquals(StatusCandidatoSolicitacao.INDIFERENTE, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao1.getId()).getStatus());
		assertEquals(StatusCandidatoSolicitacao.INDIFERENTE, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao2.getId()).getStatus());
		assertEquals(candidatoSolicitacao4.getStatus(), candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao4.getId()).getStatus());
		assertEquals(candidatoSolicitacao5.getStatus(), candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao5.getId()).getStatus());
		assertEquals(candidatoSolicitacao6.getStatus(), candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao6.getId()).getStatus());
	}
	
	@Test
	public void testUpdateStatusSolicitacoesEmAndamentoByColaboradorIdAoReligar() {
		Solicitacao solicitacao1 = criaSolicitacao();
		Candidato candidato = criaCandidato();
		
		CandidatoSolicitacao candidatoSolicitacao1 = criaCandidatoSolicitacao(candidato, solicitacao1, StatusCandidatoSolicitacao.CONTRATADO);
		CandidatoSolicitacao candidatoSolicitacao2 = criaCandidatoSolicitacao(candidato, solicitacao1, StatusCandidatoSolicitacao.INDIFERENTE);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		criaHistoricoColaborador(colaborador, DateUtil.criarDataMesAno(1, 1, 2017), MotivoHistoricoColaborador.CONTRATADO, StatusRetornoAC.CONFIRMADO,candidatoSolicitacao1);
		
		candidatoSolicitacaoDao.updateStatusSolicitacoesEmAndamentoByColaboradorId(StatusCandidatoSolicitacao.APROMOVER, new Long[]{colaborador.getId()});
		
		assertEquals(StatusCandidatoSolicitacao.CONTRATADO, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao1.getId()).getStatus());
		assertEquals(StatusCandidatoSolicitacao.APROMOVER, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao2.getId()).getStatus());
	}

	@Test
	public void testUpdateStatusSolicitacoesEmAndamentoByColaboradorIdAoDesligar() {
		Solicitacao solicitacao1 = criaSolicitacao();
		Candidato candidato = criaCandidato();
		
		CandidatoSolicitacao candidatoSolicitacao1 = criaCandidatoSolicitacao(candidato, solicitacao1, StatusCandidatoSolicitacao.CONTRATADO);
		CandidatoSolicitacao candidatoSolicitacao2 = criaCandidatoSolicitacao(candidato, solicitacao1, StatusCandidatoSolicitacao.APROMOVER);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		criaHistoricoColaborador(colaborador, DateUtil.criarDataMesAno(1, 1, 2017), MotivoHistoricoColaborador.CONTRATADO, StatusRetornoAC.CONFIRMADO,candidatoSolicitacao1);
		
		candidatoSolicitacaoDao.updateStatusSolicitacoesEmAndamentoByColaboradorId(StatusCandidatoSolicitacao.INDIFERENTE, new Long[]{colaborador.getId()});
		
		assertEquals(StatusCandidatoSolicitacao.CONTRATADO, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao1.getId()).getStatus());
		assertEquals(StatusCandidatoSolicitacao.INDIFERENTE, candidatoSolicitacaoDao.findCandidatoSolicitacaoById(candidatoSolicitacao2.getId()).getStatus());
	}
	
	@Test
	public void testGetCandidatoSolicitacaoList() {
		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva1.setOrdem(1);
		etapaSeletivaDao.save(etapaSeletiva1);

		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva2.setOrdem(4);
		etapaSeletivaDao.save(etapaSeletiva2);

		CandidatoSolicitacao candidatoSolicitacao = criaCandidatoSolicitacao();
		
		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity(etapaSeletiva1, DateUtil.criarDataMesAno(01, 02, 2009), candidatoSolicitacao);
		historicoCandidatoDao.save(historicoCandidato1);

		HistoricoCandidato historicoCandidato2 = HistoricoCandidatoFactory.getEntity(etapaSeletiva2, DateUtil.criarDataMesAno(01, 02, 2009), candidatoSolicitacao);
		historicoCandidatoDao.save(historicoCandidato2);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatoSolicitacaoList(null, null, candidatoSolicitacao.getSolicitacao().getId(), null, null, null, false, true, null, null, null);
		assertEquals(1, candidatoSolicitacaos.size());

		CandidatoSolicitacao candidatoSolicitacaoTmp = (CandidatoSolicitacao) candidatoSolicitacaos.toArray()[0];
		assertEquals(etapaSeletiva2, candidatoSolicitacaoTmp.getEtapaSeletiva());
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
		
		CandidatoSolicitacao candidatoSolicitacao = criaCandidatoSolicitacao();
		
		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity(etapaSeletiva1, DateUtil.criarDataMesAno(01, 03, 2009), candidatoSolicitacao);
		historicoCandidatoDao.save(historicoCandidato1);
		
		HistoricoCandidato historicoCandidato2 = HistoricoCandidatoFactory.getEntity(etapaSeletiva2, DateUtil.criarDataMesAno(01, 02, 2009), candidatoSolicitacao);
		historicoCandidatoDao.save(historicoCandidato2);
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoDao.getCandidatoSolicitacaoList(null, null, candidatoSolicitacao.getSolicitacao().getId(), etapaSeletiva1.getId(), null, null, false, true, null, null, null);
		assertEquals(1, candidatoSolicitacaos.size());
		
		CandidatoSolicitacao candidatoSolicitacaoTmp = (CandidatoSolicitacao) candidatoSolicitacaos.toArray()[0];
		assertEquals(etapaSeletiva1, candidatoSolicitacaoTmp.getEtapaSeletiva());
	}
	
	private Candidato criaCandidato(){
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		return candidato;
	}
	
	private Solicitacao criaSolicitacao(){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
	
	private CandidatoSolicitacao criaCandidatoSolicitacao(Candidato candidato, Solicitacao solicitacao, Character status){
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, false);
		candidatoSolicitacao.setStatus(status);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		return candidatoSolicitacao;
		
	}
	
	private CandidatoSolicitacao criaCandidatoSolicitacao(){
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, false);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		return candidatoSolicitacao;
	}
	
	private HistoricoColaborador criaHistoricoColaborador(Colaborador colaborador, Date data, String motivo, Integer status, CandidatoSolicitacao candidatoSolicitacao){
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, data, status);
		historicoColaborador.setMotivo(motivo);
		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoColaboradorDao.save(historicoColaborador);
		
		return historicoColaborador;
	}
	
	private HistoricoColaborador criaHistoricoColaboradorAndColaborador(CandidatoSolicitacao candidatoSolicitacao){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoColaboradorDao.save(historicoColaborador);
		
		return historicoColaborador;
	}
}