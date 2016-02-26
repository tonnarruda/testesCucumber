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
	
	public Collection<ColaboradorCertificacao> colaboradoresQueParticipaDoCertificado(Long certificacaoId){
		return getDao().colaboradoresQueParticipaDoCertificado(null, certificacaoId, null, null, null);
	}

	@SuppressWarnings("deprecation")
	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Integer mesesCertificacoesAVencer, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] colaboradoresIds)
	{
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");

		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		Collection<Certificacao> certificacoes = certificacaoManager.findById(certificacoesIds);
		certificacoes = new CollectionUtil<Certificacao>().sortCollectionStringIgnoreCase(certificacoes, "nome");
		
		Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCerttificacao = new ArrayList<ColaboradorCertificacao>();
		for (Certificacao certificacao : certificacoes) 
			colaboradoresQueParticipamDaCerttificacao.addAll(getDao().colaboradoresQueParticipaDoCertificado(dataFim, certificacao.getId(), areaIds, estabelecimentoIds, colaboradoresIds));
		
		Date hoje = new Date();
		Date dataDoVencimento = null;
		ColaboradorCertificacao	colabCertificacao;
		colaboradoresQueParticipamDaCerttificacao = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradoresQueParticipamDaCerttificacao, "colaborador.nome");
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresQueParticipamDaCerttificacao){
			if( colaboradorCertificacao.getData() != null && colaboradorCertificacao.getCertificacao() != null && colaboradorCertificacao.getCertificacao().getPeriodicidade() != null)
				dataDoVencimento = DateUtil.incrementaMes(colaboradorCertificacao.getData(), colaboradorCertificacao.getCertificacao().getPeriodicidade());

			if(colaboradorCertificado && !colaboradorNaoCertificado){//Marca apenas Colaboradores Certificados
				if(colaboradorCertificacao.getId() == null)
					continue;
				else if(dataDoVencimento != null && dataDoVencimento.getTime() < hoje.getTime())
					continue;
			}
			
			if(!colaboradorCertificado && colaboradorNaoCertificado){//Marca apenas Colaboradores NÃO Certificados
				if(colaboradorCertificacao.getId() != null && dataDoVencimento != null && dataDoVencimento.getTime() >= hoje.getTime())
					continue;
			}
			
			if(colaboradorCertificado && colaboradorCertificacao.getId() != null){
				if(dataIni != null && colaboradorCertificacao.getData().getTime() < dataIni.getTime())
					continue;
				if(dataFim != null && colaboradorCertificacao.getData().getTime() > dataFim.getTime())
					continue;
				if(mesesCertificacoesAVencer != null && mesesCertificacoesAVencer != 0 && dataDoVencimento != null && dataDoVencimento.getTime() >= hoje.getTime())
				{
					Date dataDeAntecendenciaDoVencimento = DateUtil.incrementaMes(dataDoVencimento, (-1*mesesCertificacoesAVencer));
					if(dataDeAntecendenciaDoVencimento.getTime() < hoje.getTime())
						continue;
				}
			}
			
			Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getId());
		
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
			{
				colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
				colabCertificacao.setPeriodoTurma(formataPeriodo(colaboradorTurma.getTurma().getDataPrevIni(), colaboradorTurma.getTurma().getDataPrevFim()));
				colabCertificacao.setNomeCurso(colaboradorTurma.getCurso().getNome());
				colabCertificacao.setCertificacao((Certificacao)colaboradorCertificacao.getCertificacao().clone());
				colabCertificacao.getCertificacao().setAprovadoNaTurma(colaboradorTurma.isAprovado() && colaboradorTurma.getTurma().getRealizada());
				colaboradorCertificacaosRetorno.add(colabCertificacao);
			}
			
			populaAvaliacoesPraticas(colaboradorCertificacaosRetorno,colaboradorCertificacao);
		}	
			
		return colaboradorCertificacaosRetorno;
	}

	private void populaAvaliacoesPraticas(Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno, ColaboradorCertificacao colaboradorCertificacao) 
	{
		ColaboradorCertificacao colabCertificacao;
		Collection<AvaliacaoPratica> avaliacoesPraticas = avaliacaoPraticaManager.findByCertificacaoId(colaboradorCertificacao.getCertificacao().getId());
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getId(), null, true, true);
		
		for (AvaliacaoPratica avaliacaoPratica : avaliacoesPraticas) 
		{
			if(avaliacaoPratica.getId() == null)
				continue;
			
			colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
			colabCertificacao.setPeriodoTurma("-");
			colabCertificacao.setNomeCurso("Avaliação Prática: " + avaliacaoPratica.getTitulo());
			colabCertificacao.setCertificacao((Certificacao)colaboradorCertificacao.getCertificacao().clone());
			colabCertificacao.getCertificacao().setAprovadoNaTurma(false);
			
			for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
			{
				if(avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPratica.getId()))
				{
					colabCertificacao.getCertificacao().setAprovadoNaTurma(avaliacaoPraticaDoColaboradorRealizada.getNota() >= avaliacaoPratica.getNotaMinima());
					colabCertificacao.setPeriodoTurma("Data da Avaliação: " + avaliacaoPraticaDoColaboradorRealizada.getDataFormatada());
					break;
				}
			}
			
			colaboradorCertificacaosRetorno.add(colabCertificacao);
		}
	}

	private String formataPeriodo(Date dataPrevIni, Date dataPrevFim)
	{
		String periodo = "";
		if (dataPrevIni != null)
			periodo += DateUtil.formataDiaMesAno(dataPrevIni);
		if (dataPrevFim != null)
			periodo += " a " + DateUtil.formataDiaMesAno(dataPrevFim);

		return periodo;
	}
	
	public Collection<ColaboradorCertificacao> certificaColaborador(Long colaboradorTurmaId, Long colaboradorId, Long certificacaoId, CertificacaoManager certificacaoManager){
		Collection<ColaboradorCertificacao> colaboradorCertificadosRetorno = new ArrayList<ColaboradorCertificacao>();
		Collection<ColaboradorCertificacao> colaboradorCertificados = new ArrayList<ColaboradorCertificacao>();
		
		if(colaboradorTurmaId != null)
			colaboradorCertificados  = getDao().colaboradoresCertificadosByColaboradorTurmaId(colaboradorTurmaId);
		else if(colaboradorId != null && certificacaoId != null){
			ColaboradorCertificacao colaboradorCertificacao = getDao().verificaCertificacao(colaboradorId, certificacaoId);
			if(colaboradorCertificacao != null)
				colaboradorCertificados.add(colaboradorCertificacao);
		}
		
		for (ColaboradorCertificacao colaboradorCertificado : colaboradorCertificados){
			if(colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito() == null || colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId() == null){
				colaboradorCertificadosRetorno.add(colaboradorCertificado);
			}else if(verificaCertificacaoPreRequisitoRecursivo(colaboradorCertificado)){
				colaboradorCertificadosRetorno.add(colaboradorCertificado);
			}
		}

		for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificadosRetorno){ 
			saveColaboradorCertificacao(colaboradorCertificacao);
			certificaDependentesRecursivo(colaboradorCertificacao, certificacaoManager);
		}
		
		return colaboradorCertificadosRetorno;
	}

	private void certificaDependentesRecursivo(ColaboradorCertificacao colaboradorCertificacao, CertificacaoManager certificacaoManager) {
		if(certificacaoManager == null)
			certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");
			
		Collection<Certificacao> certificadosDependentes = certificacaoManager.findDependentes(colaboradorCertificacao.getCertificacao().getId());
		
		if(certificadosDependentes != null){
			for (Certificacao certificacao : certificadosDependentes){ 
				ColaboradorCertificacao colaboradorCertificacaoTemp  = getDao().verificaCertificacao(colaboradorCertificacao.getColaborador().getId(),certificacao.getId());
				if(colaboradorCertificacaoTemp != null){
					colaboradorCertificacaoTemp.setColaboradorCertificacaoPreRequisito(colaboradorCertificacao);
					saveColaboradorCertificacao(colaboradorCertificacaoTemp);
					certificaDependentesRecursivo(colaboradorCertificacaoTemp, certificacaoManager);
				}
			}
		}
	}

	private boolean verificaCertificacaoPreRequisitoRecursivo(ColaboradorCertificacao colaboradorCertificacao)
	{
		boolean colaboradorCertificadoNoPreRequisito = false;
		ColaboradorCertificacao colaboradorCertificadoTemp = getDao().findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getCertificacaoPreRequisito().getId());
		
		if(colaboradorCertificadoTemp != null && DateUtil.incrementaMes(colaboradorCertificadoTemp.getData(),colaboradorCertificadoTemp.getCertificacao().getPeriodicidade()).getTime() >= (new Date()).getTime())
		{
			colaboradorCertificacao.setColaboradorCertificacaoPreRequisito(colaboradorCertificadoTemp);
			
			if(colaboradorCertificadoTemp.getCertificacao().getCertificacaoPreRequisito() == null || colaboradorCertificadoTemp.getCertificacao().getCertificacaoPreRequisito().getId() == null){
				colaboradorCertificadoNoPreRequisito = true;
			}else if(verificaCertificacaoPreRequisitoRecursivo(colaboradorCertificadoTemp)){
				colaboradorCertificadoNoPreRequisito = true;
			}
		}
		
		return colaboradorCertificadoNoPreRequisito;
	}
	
	public Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId) 
	{
		return getDao().getCertificacoesAVencer(data, empresaId);
	}

	public void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId, boolean removerColaboradorAvaliacaoPratica) {
		ColaboradorCertificacao colaboradorCertificacao = getDao().findByColaboradorTurma(colaboradorTurmaId);
		if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null)
		{
			if(removerColaboradorAvaliacaoPratica)
				colaboradorAvaliacaoPraticaManager.removeByColaboradorCertificacaoId(colaboradorCertificacao.getId());
			
			this.descertificarColaborador(colaboradorCertificacao.getId());
		}
	}
	
	public void descertificarColaborador(Long colaboradorCertificacaoId) {
		getDao().removeDependenciaDaAvPratica(colaboradorCertificacaoId);
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = getDao().findColaboradorCertificacaoPreRequisito(colaboradorCertificacaoId);
		
		for (ColaboradorCertificacao colaboradorCertificacaoTemp : colaboradorCertificacoes) 
			descertificarColaborador(colaboradorCertificacaoTemp.getId());
		
		remove(colaboradorCertificacaoId);
	}

	public void saveColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao) {
		Collection<ColaboradorTurma> colaboradoresTurmas = getDao().colaboradoresTurmaCertificados(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId());
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), null, null, true, true);
		
		Date dataColaboradorCertificacao = null;
		
		if(colaboradoresTurmas != null && colaboradoresTurmas.size() > 0)
			dataColaboradorCertificacao = ((ColaboradorTurma) colaboradoresTurmas.toArray()[0]).getTurma().getDataPrevFim();
		
		if(colaboradorAvaliacoesPraticas != null && colaboradorAvaliacoesPraticas.size() > 0)
			dataColaboradorCertificacao = ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData().after(dataColaboradorCertificacao) ? ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData() : dataColaboradorCertificacao;
		
		colaboradorCertificacao.setColaboradoresTurmas(colaboradoresTurmas);
		colaboradorCertificacao.setData(dataColaboradorCertificacao);
		getDao().save(colaboradorCertificacao);
		getDao().getHibernateTemplateByGenericDao().flush();

		for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
			colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
			colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
		}
	}

	public ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId) {
		return getDao().findByColaboradorTurma(colaboradorTurmaId);
	}

	public ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao( Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId) {
		return getDao().findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorCertificacaoId, colaboradorId, certificacaoId);
	}

	public boolean existeColaboradorCertificadoEmUmaTurmaPosterior(Long turmaId, Long colaboradorCertificacaoId) {
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = getDao().findColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorCertificacaoId);
		return colaboradorCertificacoes.size() > 0;
	}
	
	public Date getMaiorDataDasTurmasDaCertificacao( Long colaboradorCertificacaoId) {
		return getDao().getMaiorDataDasTurmasDaCertificacao(colaboradorCertificacaoId);
	}

	public void setAvaliacaoPraticaManager( AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager) {
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}
}