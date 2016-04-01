package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoManagerImpl extends GenericManagerImpl<ColaboradorPeriodoExperienciaAvaliacao, ColaboradorPeriodoExperienciaAvaliacaoDao> implements ColaboradorPeriodoExperienciaAvaliacaoManager 
{
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	public void atualizaConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor)
	{
		removeConfiguracaoAvaliacaoPeriodoExperiencia(colaborador.getId());
		saveConfiguracaoAvaliacaoPeriodoExperiencia(colaborador, colaboradorAvaliacoes, colaboradorAvaliacoesGestor);
	}
	
	public void atualizaConfiguracaoAvaliacaoPeriodoExperienciaEmVariosColaboradores(Long[] colaboradorIds, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor)
	{
		removeConfiguracaoAvaliacaoPeriodoExperiencia(colaboradorIds);
		
		for (Long colaboradorId : colaboradorIds) {
			saveConfiguracaoAvaliacaoPeriodoExperiencia(new Colaborador(null, null, colaboradorId), colaboradorAvaliacoes, colaboradorAvaliacoesGestor);
		}
	}
	
	public void saveConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor) 
	{
		if(colaboradorAvaliacoes != null)
		{
			for (ColaboradorPeriodoExperienciaAvaliacao colabPerExpAvaliacao : colaboradorAvaliacoes) 
			{
				if (colabPerExpAvaliacao.getAvaliacao() != null && colabPerExpAvaliacao.getAvaliacao().getId() != null)
				{
					ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = 
							new ColaboradorPeriodoExperienciaAvaliacao(colaborador, colabPerExpAvaliacao.getAvaliacao(), colabPerExpAvaliacao.getPeriodoExperiencia(), ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
					getDao().save(colaboradorPeriodoExperienciaAvaliacao);
				}
			}
		}

		if(colaboradorAvaliacoesGestor != null)
		{
			for (ColaboradorPeriodoExperienciaAvaliacao colabPerExpAvaliacaoGestor : colaboradorAvaliacoesGestor) 
			{
				if (colabPerExpAvaliacaoGestor.getAvaliacao() != null && colabPerExpAvaliacaoGestor.getAvaliacao().getId() != null)
				{
					ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacaoGestor = 
							new ColaboradorPeriodoExperienciaAvaliacao(colaborador, colabPerExpAvaliacaoGestor.getAvaliacao(), colabPerExpAvaliacaoGestor.getPeriodoExperiencia(), ColaboradorPeriodoExperienciaAvaliacao.TIPO_GESTOR);
					getDao().save(colaboradorPeriodoExperienciaAvaliacaoGestor);
				}
			}
		}
	}

	public void removeConfiguracaoAvaliacaoPeriodoExperiencia(Long... colaboradorIds) 
	{
		getDao().removeByColaborador(colaboradorIds);
	}
	
	@TesteAutomatico
	public void removeByAvaliacao(Long avaliacaoId) 
	{
		getDao().removeByAvaliacao(avaliacaoId);
	}

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId) 
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo() 
	{
		Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores = getDao().findColaboradoresComAvaliacaoNaoRespondida();
		gerenciadorComunicacaoManager.enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(colaboradores);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}