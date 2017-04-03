package com.fortes.rh.business.avaliacao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.SpringUtil;

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
	
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho, Long[] notFaixasSalariaisId) {
		Collection<FaixaSalarial> faixaSalarials = getDao().findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), notFaixasSalariaisId);
		for (FaixaSalarial faixaSalarial : faixaSalarials) {
			faixaSalarial.setConfiguracaoNivelCompetencias(configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId(), avaliacaoDesempenho.getInicio()));
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
	
	public void save(AvaliacaoDesempenho avaliacaoDesempenho,Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliados,Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliadores,Collection<ColaboradorQuestionario> colaboradorQuestionarios, Long[] colaboradorQuestionariosRemovidos, Long[] participantesAvaliadosRemovidos, Long[] participantesAvaliadoresRemovidos) throws Exception {
		participantesAvaliados.removeAll(Collections.singleton(null));
		this.saveOrUpdate(participantesAvaliados);
			
		if ( !avaliacaoDesempenho.isLiberada() ) {
			removeParticipantes(participantesAvaliadosRemovidos);
			
			participantesAvaliadores.removeAll(Collections.singleton(null));
			this.saveOrUpdate(participantesAvaliadores);
			removeParticipantes(participantesAvaliadoresRemovidos);
			
			ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
			colaboradorQuestionarios.removeAll(Collections.singleton(null));
			
			Avaliacao avaliacao = avaliacaoDesempenho.getAvaliacao();
			
			if (colaboradorQuestionarios != null) {
				for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios) {
					colaboradorQuestionario.setAvaliacao(avaliacao);
				}
			}
				
			colaboradorQuestionarioManager.saveOrUpdate(colaboradorQuestionarios);
			removerColaboradorQuestionario(colaboradorQuestionarioManager, colaboradorQuestionariosRemovidos);
		}
	}
	
	private void removeParticipantes(Long[] idParticipantes){
		if (idParticipantes != null){
			Set<Long> set = new HashSet<>();
			set.addAll(Arrays.asList(idParticipantes));
			this.remove(set.toArray(new Long[set.size()]));
		}
	}
	
	private void removerColaboradorQuestionario(ColaboradorQuestionarioManager colaboradorQuestionarioManager, Long[] colaboradorQuestionariosRemovidos){
		if (colaboradorQuestionariosRemovidos != null){
			Set<Long> set = new HashSet<>();
			set.addAll(Arrays.asList(colaboradorQuestionariosRemovidos));
			colaboradorQuestionarioManager.remove(set.toArray(new Long[set.size()]));
		}
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
}