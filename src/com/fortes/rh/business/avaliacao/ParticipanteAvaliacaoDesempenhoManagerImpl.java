package com.fortes.rh.business.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

public class ParticipanteAvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<ParticipanteAvaliacaoDesempenho, ParticipanteAvaliacaoDesempenhoDao> implements ParticipanteAvaliacaoDesempenhoManager
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	
	public void clone(AvaliacaoDesempenho avaliacaoDesempenho, Collection<ParticipanteAvaliacaoDesempenho> participantes) 
	{
		for (ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho : participantes) {
			ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenhoTemp = new ParticipanteAvaliacaoDesempenho();
			participanteAvaliacaoDesempenhoTemp.setAvaliacaoDesempenho(avaliacaoDesempenho);
			participanteAvaliacaoDesempenhoTemp.setColaboradorId(participanteAvaliacaoDesempenho.getColaborador().getId());
			participanteAvaliacaoDesempenhoTemp.setTipo(participanteAvaliacaoDesempenho.getTipo());
			participanteAvaliacaoDesempenhoTemp.setProdutividade(participanteAvaliacaoDesempenho.getProdutividade());
			getDao().save(participanteAvaliacaoDesempenhoTemp);
		}
	}
	
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		Collection<FaixaSalarial> faixaSalarials = getDao().findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(avaliacaoDesempenhoId);
		for (FaixaSalarial faixaSalarial : faixaSalarials) {
			faixaSalarial.setConfiguracaoNivelCompetencias(configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId(), new Date()));
		}
		return faixaSalarials;
	}
	
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId) {
		return getDao().findFaixasSalariaisDosAvaliadosByAvaliador(avaliacaoDesempenhoId, avaliadorId);
	}
	
	public Collection<Colaborador> findColaboradoresParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		return getDao().findColaboradoresParticipantes(avaliacaoDesempenhoId, tipo);
	}
	
	public Collection<ParticipanteAvaliacaoDesempenho> findParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		return getDao().findParticipantes(avaliacaoDesempenhoId, tipo);
	}
	
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception
	{
		getDao().removeNotIn( participantes, avaliacaoDesempenhoId, tipo );
	}

	public Double findByAvalDesempenhoIdAbadColaboradorId(Long avaliacaoDesempenhoId, Long avaliadoId, Character tipoParticipanteAvaliacao) {
		return getDao().findByAvalDesempenhoIdAbadColaboradorId(avaliacaoDesempenhoId, avaliadoId, tipoParticipanteAvaliacao);
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
}