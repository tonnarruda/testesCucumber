package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.util.LongUtil;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl extends GenericManagerImpl<ConfiguracaoCompetenciaAvaliacaoDesempenho, ConfiguracaoCompetenciaAvaliacaoDesempenhoDao> implements ConfiguracaoCompetenciaAvaliacaoDesempenhoManager
{                                                          
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
	
	public void reajusteByConfiguracaoNivelCompetenciaFaixaSalarial(Collection<Competencia> competenciasAnteriores, Collection<Competencia> competencias, Character tipoCompetencia, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) {
		Collection<Long> competenciasAnterioresIds = new ArrayList<Long>(LongUtil.collectionSimpleModelToCollectionLong(competenciasAnteriores));
		Collection<Long> competenciasAtuaisIds = new ArrayList<Long>(LongUtil.collectionSimpleModelToCollectionLong(competencias));
		
		Collection<Long> competenciasExcluidas = new ArrayList<Long>(competenciasAnterioresIds);
		Collection<Long> competenciasInseridas = new ArrayList<Long>(competenciasAtuaisIds);
		
		competenciasExcluidas.removeAll(competenciasAtuaisIds);
		competenciasInseridas.removeAll(competenciasAnterioresIds);
		
		Long[] competenciasExcluidasIds = Arrays.copyOf(competenciasExcluidas.toArray(), competenciasExcluidas.toArray().length, Long[].class);
		
		if (competenciasExcluidasIds.length > 0)
			getDao().removeByCompetenciaAndFaixaSalarial(competenciasExcluidasIds, configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), tipoCompetencia);
		
		getDao().replaceConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
	}
}