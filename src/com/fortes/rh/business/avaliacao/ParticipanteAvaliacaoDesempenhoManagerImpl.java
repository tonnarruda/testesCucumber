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
import com.fortes.rh.util.LongUtil;

public class ParticipanteAvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<ParticipanteAvaliacaoDesempenho, ParticipanteAvaliacaoDesempenhoDao> implements ParticipanteAvaliacaoDesempenhoManager
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	
	public void save(AvaliacaoDesempenho avaliacaoDesempenho, Long[] colaboradorIds, char tipo) 
	{
		for (Long colaboradorId : colaboradorIds) 
		{
			Collection<Colaborador> participantes = findParticipantes(avaliacaoDesempenho.getId(), tipo);
			
			if (!LongUtil.contains(colaboradorId, participantes))//verifica se já é um  participante
			{
				ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho = new ParticipanteAvaliacaoDesempenho();
				participanteAvaliacaoDesempenho.setAvaliacaoDesempenho(avaliacaoDesempenho);
				participanteAvaliacaoDesempenho.setColaboradorId(colaboradorId);
				participanteAvaliacaoDesempenho.setTipo(tipo);
				getDao().save(participanteAvaliacaoDesempenho);
			}
		}
	}
	
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		Collection<FaixaSalarial> faixaSalarials = getDao().findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(avaliacaoDesempenhoId);
		for (FaixaSalarial faixaSalarial : faixaSalarials) {
			faixaSalarial.setConfiguracaoNivelCompetencias(configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId(), new Date()));
		}
		return faixaSalarials;
	}
	
	public Collection<Colaborador> findParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		return getDao().findParticipantes(avaliacaoDesempenhoId, tipo);
	}
	
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception
	{
		getDao().removeNotIn( participantes, avaliacaoDesempenhoId, tipo );
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
}