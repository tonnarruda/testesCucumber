package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.LongUtil;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<ConfiguracaoCompetenciaAvaliacaoDesempenho, ConfiguracaoCompetenciaAvaliacaoDesempenhoDao> implements ConfiguracaoCompetenciaAvaliacaoDesempenhoManager
{                  
	ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	FaixaSalarialManager faixaSalarialManager;
	CompetenciaManager competenciaManager;
	
	public void save(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId) 
	{
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhosOld = getDao().findByAvaliacaoDesempenho(avaliacaoDesempenhoId);
		
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
	
	public void reajusteByConfiguracaoNivelCompetenciaFaixaSalarial(Collection<Competencia> conhecimentosAnteriores, Collection<Competencia> habilidadesAnteriores, Collection<Competencia> atitudesAnteriores, Collection<Competencia> competencias, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Empresa empresa, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarialAnterior) {
		removeCompetenciasQueNaoPermaneceram(conhecimentosAnteriores, competencias, configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), TipoCompetencia.CONHECIMENTO);
		removeCompetenciasQueNaoPermaneceram(habilidadesAnteriores, competencias, configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), TipoCompetencia.HABILIDADE);
		removeCompetenciasQueNaoPermaneceram(atitudesAnteriores, competencias, configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), TipoCompetencia.ATITUDE);
		
		Collection<Competencia> competenciasAnteriores = new ArrayList<Competencia>();
		competenciasAnteriores.addAll(conhecimentosAnteriores);
		competenciasAnteriores.addAll(habilidadesAnteriores);
		competenciasAnteriores.addAll(atitudesAnteriores);
		
		Collection<Competencia> competenciasExcluidas = new ArrayList<Competencia>(competenciasAnteriores);
		competenciasExcluidas.removeAll(competencias);
		
		Collection<Competencia> competenciasInseridasTemp = new ArrayList<Competencia>(competencias);
		competenciasInseridasTemp.removeAll(competenciasAnteriores);
		
		Collection<Competencia> competenciasInseridas = new ArrayList<Competencia>();
		
		for (Competencia competencia : competenciasInseridasTemp) {
			 competenciasInseridas.add(competenciaManager.findCompetencia(competencia.getId(), competencia.getTipo()));
		}
		
		FaixaSalarial faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId());
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoManager.findAvaliacaoDesempenhoBloqueadaComConfiguracaoCompetencia(configuracaoNivelCompetenciaFaixaSalarialAnterior.getId());
		gerenciadorComunicacaoManager.enviaEmailAoInserirConfiguracaoCompetenciaFaixaSalarial(competenciasInseridas, competenciasExcluidas, faixaSalarial, empresa, avaliacoesDesempenho);
		
		getDao().replaceConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
	}
	
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		Collection<FaixaSalarial> faixaSalarials = getDao().findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(avaliacaoDesempenhoId);
		for (FaixaSalarial faixaSalarial : faixaSalarials) {
			faixaSalarial.setConfiguracaoNivelCompetencias(configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaFaixaSalarial(faixaSalarial.getConfiguracaoNivelCompetenciaFaixaSalarialId(), null));
		}
		return faixaSalarials;
	}
	
	public void removeCompetenciasQueNaoPermaneceram(Collection<Competencia> competenciasAnteriores, Collection<Competencia> competencias, Long faixaSalarialId, Character tipoCompetencia) {
		Collection<Competencia> competenciasQueNaoPermaneceram = new ArrayList<Competencia>(competenciasAnteriores);
		competenciasQueNaoPermaneceram.removeAll(competencias);
		
		Collection<Long> competenciasQueNaoPermaneceramIds = LongUtil.collectionSimpleModelToCollectionLong(competenciasQueNaoPermaneceram);
		
		if(LongUtil.isNotEmpty(competenciasQueNaoPermaneceramIds))
			getDao().removeByCompetenciasQueNaoPermaneceram(Arrays.copyOf(competenciasQueNaoPermaneceramIds.toArray(), competenciasQueNaoPermaneceramIds.toArray().length, Long[].class), faixaSalarialId, tipoCompetencia);
	}
	
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId) {
		return getDao().existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenhoId);
	}
	
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		getDao().removeByAvaliacaoDesempenho(avaliacaoDesempenhoId);
	}
	
	public void replaceConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) {
		getDao().replaceConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
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