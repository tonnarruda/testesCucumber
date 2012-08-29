package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoManagerImpl extends GenericManagerImpl<ColaboradorPeriodoExperienciaAvaliacao, ColaboradorPeriodoExperienciaAvaliacaoDao> implements ColaboradorPeriodoExperienciaAvaliacaoManager 
{
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	public void saveConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor) 
	{
		if(colaboradorAvaliacoes != null)
		{
			for (ColaboradorPeriodoExperienciaAvaliacao colabPerExpAvaliacao : colaboradorAvaliacoes) 
			{
				if (colabPerExpAvaliacao.getAvaliacao() != null && colabPerExpAvaliacao.getAvaliacao().getId() != null)
				{
					colabPerExpAvaliacao.setColaborador(colaborador);
					colabPerExpAvaliacao.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
					getDao().save(colabPerExpAvaliacao);
				}
			}
		}

		if(colaboradorAvaliacoesGestor != null)
		{
			for (ColaboradorPeriodoExperienciaAvaliacao colabPerExpAvaliacaoGestor : colaboradorAvaliacoesGestor) 
			{
				if (colabPerExpAvaliacaoGestor.getAvaliacao() != null && colabPerExpAvaliacaoGestor.getAvaliacao().getId() != null)
				{
					colabPerExpAvaliacaoGestor.setColaborador(colaborador);
					colabPerExpAvaliacaoGestor.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_GESTOR);
					getDao().save(colabPerExpAvaliacaoGestor);
				}
			}
		}
	}

	public void removeConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador) 
	{
		getDao().removeByColaborador(colaborador.getId());
	}

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId) 
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo() 
	{
		Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores = getDao().getColaboradoresComAvaliacaoVencidaHoje();
		gerenciadorComunicacaoManager.enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(colaboradores);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}