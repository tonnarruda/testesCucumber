package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCandidatoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;

public class ConfiguracaoNivelCompetenciaCandidatoDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaCandidato>
{
	private ConfiguracaoNivelCompetenciaCandidatoDao configuracaoNivelCompetenciaCandidatoDao;
	private CandidatoDao candidatoDao;
	private SolicitacaoDao solicitacaoDao;

	public ConfiguracaoNivelCompetenciaCandidato getEntity()
	{
		ConfiguracaoNivelCompetenciaCandidato cncCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity();
		cncCandidato.setData(new Date());
		return cncCandidato;
	}

	public GenericDao<ConfiguracaoNivelCompetenciaCandidato> getGenericDao()
	{
		return configuracaoNivelCompetenciaCandidatoDao;
	}
	
	public void testFindByCandidatoAndSolicitacao() {
		Candidato candidato = getCandidato("Júlio");
		Solicitacao solicitacao = getSolicitacao();
		
		ConfiguracaoNivelCompetenciaCandidato cncCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(candidato, solicitacao, null, new Date());
		configuracaoNivelCompetenciaCandidatoDao.save(cncCandidato);
		
		assertEquals(cncCandidato.getId(), configuracaoNivelCompetenciaCandidatoDao.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId()).getId());
	}
	
	public void testRemoveByCandidatoAndSolicitacao() {
		Candidato candidato = getCandidato("Júlia");
		Solicitacao solicitacao = getSolicitacao();
		
		ConfiguracaoNivelCompetenciaCandidato cncCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(candidato, solicitacao, null, new Date());
		configuracaoNivelCompetenciaCandidatoDao.save(cncCandidato);
		assertEquals(cncCandidato.getId(), configuracaoNivelCompetenciaCandidatoDao.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId()).getId());
		
		configuracaoNivelCompetenciaCandidatoDao.removeByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId());
		
		assertNull(configuracaoNivelCompetenciaCandidatoDao.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId()));
	}
	
	public void testRemoveByCandidato() {
		Candidato candidato = getCandidato("Isabel");
		Solicitacao solicitacao = getSolicitacao();
		
		ConfiguracaoNivelCompetenciaCandidato cncCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(candidato, solicitacao, null, new Date());
		configuracaoNivelCompetenciaCandidatoDao.save(cncCandidato);
		assertEquals(cncCandidato.getId(), configuracaoNivelCompetenciaCandidatoDao.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId()).getId());
		
		configuracaoNivelCompetenciaCandidatoDao.removeByCandidato(candidato.getId());
		
		assertNull(configuracaoNivelCompetenciaCandidatoDao.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId()));
	}

	private Candidato getCandidato(String nome){
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setNome(nome);
		candidatoDao.save(candidato);
		return candidato;
	}
	
	private Solicitacao getSolicitacao(){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
	

	public void setConfiguracaoNivelCompetenciaCandidatoDao(
			ConfiguracaoNivelCompetenciaCandidatoDao configuracaoNivelCompetenciaCandidatoDao) {
		this.configuracaoNivelCompetenciaCandidatoDao = configuracaoNivelCompetenciaCandidatoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}
}