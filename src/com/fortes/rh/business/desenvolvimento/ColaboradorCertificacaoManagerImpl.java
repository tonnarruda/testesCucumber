package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}
	
	public ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}

	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds)
	{
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBean("colaboradorTurmaManager");
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = getDao().colaboradoresCertificados(dataIni, dataFim, filtroCetificacao, areaIds, estabelecimentoIds, certificacoesIds);
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		ColaboradorCertificacao colabCertificacao;
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificacaos) 
		{
			Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getId());
		
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
			{
				colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
				colabCertificacao.setPeriodoTurma(formataPeriodo(colaboradorTurma.getTurma().getDataPrevIni(), colaboradorTurma.getTurma().getDataPrevFim()));
				colabCertificacao.setNomeCurso(colaboradorTurma.getCurso().getNome());
				colabCertificacao.setAprovadoNaTurma(colaboradorTurma.isAprovado());
				colaboradorCertificacaosRetorno.add(colabCertificacao);
			}
			
			Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getId());
				
			for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
			{
				colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
				colabCertificacao.setPeriodoTurma("-");
				colabCertificacao.setNomeCurso("Avaliação Prática: " + avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getTitulo());
				colabCertificacao.setAprovadoNaTurma(avaliacaoPraticaDoColaboradorRealizada.getNota() >= avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getNotaMinima());
				colaboradorCertificacaosRetorno.add(colabCertificacao);
			}
		}	
		
		return colaboradorCertificacaosRetorno;
	}

	public String formataPeriodo(Date dataPrevIni, Date dataPrevFim)
	{
		String periodo = "";
		if (dataPrevIni != null)
			periodo += DateUtil.formataDiaMesAno(dataPrevIni);
		if (dataPrevFim != null)
			periodo += " a " + DateUtil.formataDiaMesAno(dataPrevFim);

		return periodo;
	}
	
	public void verificaCertificacaoByColaboradorTurmaId(Long colaboradorTurmaId) 
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificados  = getDao().colaboradoresCertificadosByColaboradorTurmaId(colaboradorTurmaId);
		Collection<ColaboradorCertificacao> colaboradoresCertificadosTemp = new ArrayList<ColaboradorCertificacao>();
		
		for (ColaboradorCertificacao colaboradorCertificado : colaboradoresCertificados) 
			if(colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito() != null && colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId() != null)
				colaboradoresCertificadosTemp.addAll(colaboradoresCertificadosByColaboradorIdAndCertificacaId(colaboradorCertificado.getColaborador().getId(), colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId()));
		
		colaboradoresCertificados.addAll(colaboradoresCertificadosTemp);
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificados) 
		{
			saveColaboradorCertificacao(colaboradorCertificacao);
		}
	}

	public Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorIdAndCertificacaId(Long colaboradorId, Long certificacaoId)
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificados = new ArrayList<ColaboradorCertificacao>();
		ColaboradorCertificacao colaboradorCertificado  = getDao().colaboradorCertificadoByColaboradorIdAndCertificacaId(colaboradorId, certificacaoId);
		
		if(colaboradorCertificado != null)
		{
			colaboradoresCertificados.add(colaboradorCertificado);
			if(colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito() != null && colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId() != null)
				colaboradoresCertificados.addAll(colaboradoresCertificadosByColaboradorIdAndCertificacaId(colaboradorCertificado.getColaborador().getId(), colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId()));
		}

		if(colaboradoresCertificados.size() > 0)
		{
			Collection<ColaboradorCertificacao> colaboradoresCertificadosFilhas = getDao().getColaboradorCertificadoFilhas(new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificados), colaboradorId);  
			for (ColaboradorCertificacao colaboradorCertificacaoFilha : colaboradoresCertificadosFilhas) 
				colaboradoresCertificados.add(getDao().colaboradorCertificadoByColaboradorIdAndCertificacaId(colaboradorId, colaboradorCertificacaoFilha.getCertificacao().getId()));
		}
		
		return colaboradoresCertificados;
	}
	
	public Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId) 
	{
		return getDao().getCertificacoesAVencer(data, empresaId);
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager) {
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}

	public void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId) {
		ColaboradorCertificacao colaboradorCertificacao = getDao().findByColaboradorTurma(colaboradorTurmaId);
		
		this.descertificarColaborador(colaboradorCertificacao.getId());
	}

	
	public void descertificarColaborador(Long colaboradorCertificacaoId) {
		if(colaboradorCertificacaoId != null){
			getDao().removeDependencias(colaboradorCertificacaoId);
			remove(colaboradorCertificacaoId);
		}
	}

	public void saveColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao) {
		Collection<ColaboradorTurma> colaboradoresTurmas = getDao().colaboradoresTurmaCertificados(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId());
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId());
		
		Date dataColaboradorCertificacao = null;
		
		if(colaboradoresTurmas != null)
			dataColaboradorCertificacao = ((ColaboradorTurma) colaboradoresTurmas.toArray()[0]).getTurma().getDataPrevFim();
		
		if(colaboradorAvaliacoesPraticas != null)
			dataColaboradorCertificacao = ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData().after(dataColaboradorCertificacao) ? ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData() : dataColaboradorCertificacao;
		
		colaboradorCertificacao.setColaboradoresTurmas(colaboradoresTurmas);
		colaboradorCertificacao.setData(dataColaboradorCertificacao);
		getDao().save(colaboradorCertificacao);

		for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
			colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
			colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
		}
	}
}