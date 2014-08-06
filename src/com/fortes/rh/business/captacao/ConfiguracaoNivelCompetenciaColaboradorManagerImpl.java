package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;

public class ConfiguracaoNivelCompetenciaColaboradorManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaColaborador, ConfiguracaoNivelCompetenciaColaboradorDao> implements ConfiguracaoNivelCompetenciaColaboradorManager
{
	public ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getDao().findByIdProjection(configuracaoNivelCompetenciaColaboradorId);
		verificaAvaliadorAnonimo(configuracaoNivelCompetenciaColaborador);
		return configuracaoNivelCompetenciaColaborador;
	}

	public Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId) 
	{
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores = getDao().findByColaborador(colaboradorId);
		for (ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador : configuracaoNivelCompetenciaColaboradores) 
			verificaAvaliadorAnonimo(configuracaoNivelCompetenciaColaborador);
		 
		return configuracaoNivelCompetenciaColaboradores;
	}

	public void verificaAvaliadorAnonimo(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		if(configuracaoNivelCompetenciaColaborador.getAvaliador() == null)
			configuracaoNivelCompetenciaColaborador.setAvaliador(new Colaborador());
		
		if(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getAvaliacaoDesempenho().isAnonima() 
		|| configuracaoNivelCompetenciaColaborador.getAvaliador().getNome() == null 
		|| configuracaoNivelCompetenciaColaborador.getAvaliador().getNome().equals(""))
			configuracaoNivelCompetenciaColaborador.getAvaliador().setNome("Anônimo");
	}

	public void removeColaborador(Colaborador colaborador) 
	{
		getDao().removeColaborador(colaborador);
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception 
	{
		getDao().deleteByFaixaSalarial(faixaIds);
	}

	public ConfiguracaoNivelCompetenciaColaborador findByData(Date data, Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId) 
	{
		return getDao().findByData(data, colaboradorId, avaliadorId, colaboradorQuestionarioId);
	}
}
