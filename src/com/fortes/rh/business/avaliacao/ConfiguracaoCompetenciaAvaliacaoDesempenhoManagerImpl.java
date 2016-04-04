package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.LongUtil;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<ConfiguracaoCompetenciaAvaliacaoDesempenho, ConfiguracaoCompetenciaAvaliacaoDesempenhoDao> implements ConfiguracaoCompetenciaAvaliacaoDesempenhoManager
{                  
	ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	FaixaSalarialManager faixaSalarialManager;
	CompetenciaManager competenciaManager;
	
	public void save(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, AvaliacaoDesempenho avaliacaoDesempenho) 
	{
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhosOld = getDao().findByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		
		configuracaoCompetenciaAvaliacaoDesempenhos.removeAll(configuracaoCompetenciaAvaliacaoDesempenhosOld);
		
		saveOrUpdate(configuracaoCompetenciaAvaliacaoDesempenhos);
	}
	
	public void removeNotIn(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId) throws Exception
	{
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhosOld = getDao().findByAvaliacaoDesempenho(avaliacaoDesempenhoId);
		
		configuracaoCompetenciaAvaliacaoDesempenhosOld.removeAll(configuracaoCompetenciaAvaliacaoDesempenhos);
		
		getDao().remove( LongUtil.collectionToArrayLong(configuracaoCompetenciaAvaliacaoDesempenhosOld) );
	}
	
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId) {
		return getDao().findByAvaliador(avaliadorId, faixaSalarialId, avaliacaoDesempenhoId);
	}
	
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		Collection<FaixaSalarial> faixaSalarials = getDao().findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(avaliacaoDesempenhoId);
		for (FaixaSalarial faixaSalarial : faixaSalarials) {
			faixaSalarial.setConfiguracaoNivelCompetencias(configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaFaixaSalarial(faixaSalarial.getConfiguracaoNivelCompetenciaFaixaSalarialId()));
		}
		return faixaSalarials;
	}
	
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId) {
		return getDao().existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenhoId);
	}
	
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		getDao().removeByAvaliacaoDesempenho(avaliacaoDesempenhoId);
	}
	
	public boolean existe(Long configuracaoNivelCompetenciaFaixaSalarialId, Long avaliacaoDesempenhoId) {
		return getDao().existe(configuracaoNivelCompetenciaFaixaSalarialId, avaliacaoDesempenhoId);
	}

	public Collection<Colaborador> findColabSemCompetenciaConfiguradaByAvalDesempenhoId(Long avaliacaoDesempenhoId) {
		return getDao().findColabSemCompetenciaConfiguradaByAvalDesempenhoId(avaliacaoDesempenhoId);
	}
	
	public Collection<AvaliacaoDesempenho> findAvaliacoesComColabSemCompetenciaConfiguradaByAvalDesempenhoIds(Long[] avaliacaoDesempenhoIds) {
		return getDao().findAvaliacoesComColabSemCompetenciaConfiguradaByAvalDesempenhoIds(avaliacaoDesempenhoIds);
	}
	
	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setCompetenciaManager(CompetenciaManager competenciaManager) {
		this.competenciaManager = competenciaManager;
	}

	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager) {
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}
}