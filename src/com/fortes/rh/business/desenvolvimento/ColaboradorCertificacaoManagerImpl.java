package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}
	
	public ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}

	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds)
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();

		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBean("colaboradorTurmaManager");
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBean("certificacaoManager");

		Collection<Certificacao> certificacoes = certificacaoManager.findById(certificacoesIds);
		certificacoes = new CollectionUtil<Certificacao>().sortCollection(certificacoes, "nome");
		
		Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCerttificacao = new ArrayList<ColaboradorCertificacao>();
		for (Certificacao certificacao : certificacoes) 
			colaboradoresQueParticipamDaCerttificacao.addAll(getDao().colaboradoresQueParticipaDoCertificado(areaIds, estabelecimentoIds, certificacao.getId()));
		
		colaboradoresQueParticipamDaCerttificacao = new CollectionUtil<ColaboradorCertificacao>().sortCollection(colaboradoresQueParticipamDaCerttificacao, "colaborador.nome");
		
		ColaboradorCertificacao	colabCertificacao;
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresQueParticipamDaCerttificacao) 
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
			
			Collection<AvaliacaoPratica> avaliacoesPraticas = avaliacaoPraticaManager.findByCertificacaoId(colaboradorCertificacao.getCertificacao().getId());
			Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getId());
			
			for (AvaliacaoPratica avaliacaoPratica : avaliacoesPraticas) 
			{
				colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
				colabCertificacao.setPeriodoTurma("-");
				colabCertificacao.setNomeCurso("Avaliação Prática: " + avaliacaoPratica.getTitulo());
				
				colabCertificacao.setAprovadoNaTurma(false);
				for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
				{
					if(avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPratica.getId()))
					{
						colabCertificacao.setAprovadoNaTurma(avaliacaoPraticaDoColaboradorRealizada.getNota() >= avaliacaoPratica.getNotaMinima());
						break;
					}
				}
				
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

	public void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId, boolean removerColaboradorAvaliacaoPratica) {
		ColaboradorCertificacao colaboradorCertificacao = getDao().findByColaboradorTurma(colaboradorTurmaId);
		if(colaboradorCertificacao != null)
			this.descertificarColaborador(colaboradorCertificacao.getId(), removerColaboradorAvaliacaoPratica);
	}

	
	public void descertificarColaborador(Long colaboradorCertificacaoId, boolean removerColaboradorAvaliacaoPratica) {
		if(colaboradorCertificacaoId != null){
			if(removerColaboradorAvaliacaoPratica)
				colaboradorAvaliacaoPraticaManager.removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(colaboradorCertificacaoId);
			else
				getDao().removeDependencias(colaboradorCertificacaoId);
			
			remove(colaboradorCertificacaoId);
		}
	}

	public void saveColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao) {
		Collection<ColaboradorTurma> colaboradoresTurmas = getDao().colaboradoresTurmaCertificados(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId());
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId());
		
		Date dataColaboradorCertificacao = null;
		
		if(colaboradoresTurmas != null && colaboradoresTurmas.size() > 0)
			dataColaboradorCertificacao = ((ColaboradorTurma) colaboradoresTurmas.toArray()[0]).getTurma().getDataPrevFim();
		
		if(colaboradorAvaliacoesPraticas != null && colaboradorAvaliacoesPraticas.size() > 0)
			dataColaboradorCertificacao = ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData().after(dataColaboradorCertificacao) ? ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData() : dataColaboradorCertificacao;
		
		colaboradorCertificacao.setColaboradoresTurmas(colaboradoresTurmas);
		colaboradorCertificacao.setData(dataColaboradorCertificacao);
		getDao().save(colaboradorCertificacao);

		for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
			colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
			colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
		}
	}

	public ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId) {
		return getDao().findByColaboradorTurma(colaboradorTurmaId);
	}

	public void setAvaliacaoPraticaManager(
			AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}
}