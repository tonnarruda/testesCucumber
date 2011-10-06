package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoManagerImpl extends GenericManagerImpl<ColaboradorPeriodoExperienciaAvaliacao, ColaboradorPeriodoExperienciaAvaliacaoDao> implements ColaboradorPeriodoExperienciaAvaliacaoManager 
{
	public void saveConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes) 
	{
		for (ColaboradorPeriodoExperienciaAvaliacao colabPerExpAvaliacao : colaboradorAvaliacoes) 
		{
			if (colabPerExpAvaliacao.getAvaliacao() != null && colabPerExpAvaliacao.getAvaliacao().getId() != null)
			{
				colabPerExpAvaliacao.setColaborador(colaborador);
				getDao().save(colabPerExpAvaliacao);
			}
		}
	}

	public void removeConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador) 
	{
		getDao().removeByColaborador(colaborador.getId());
	}
}