package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
				colabCertificacao.getCertificacao().setAprovadoNaTurma(colaboradorTurma.isAprovado());
				colaboradorCertificacaosRetorno.add(colabCertificacao);
			}
			
			populaAvaliacoesPraticas(colaboradorCertificacaosRetorno,colaboradorCertificacao);
		}	
		
		populaStatusAprovadoNaCertificacao(colaboradorCertificacaosRetorno);
			
		return colaboradorCertificacaosRetorno;
	}

	private void populaStatusAprovadoNaCertificacao(Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno) 
	{
		Long certificacaIdTemp = 0L;
		Long colaboradorIdTemp = 0L;
		boolean certificadoAnterior = false;
		Map<String, Certificacao> colaboradorCertificadoMap = new HashMap<String, Certificacao>();
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificacaosRetorno) 
		{
			if(certificacaIdTemp.equals(colaboradorCertificacao.getCertificacao().getId()) && colaboradorIdTemp.equals(colaboradorCertificacao.getColaborador().getId())){
				certificadoAnterior = certificadoAnterior && colaboradorCertificacao.getCertificacao().getAprovadoNaTurma();	
			}else{
				certificacaIdTemp = colaboradorCertificacao.getCertificacao().getId();
				colaboradorIdTemp = colaboradorCertificacao.getColaborador().getId();
				certificadoAnterior = colaboradorCertificacao.getCertificacao().getAprovadoNaTurma();
			}

			colaboradorCertificacao.getCertificacao().setAprovadoNaTurma(certificadoAnterior);
			colaboradorCertificadoMap.put(colaboradorCertificacao.getColaborador().getId() + "_" + colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getCertificacao());
		}
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificacaosRetorno) 
			colaboradorCertificacao.setAprovadoNaCertificacao(verificaStatusCertificacaoRecursivo(colaboradorCertificadoMap, colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao()));
	}

	private boolean verificaStatusCertificacaoRecursivo(Map<String, Certificacao> colaboradorCertificadoMap, Long colaboradorId, Certificacao certificacao) 
	{
		String colaboradorCertificacaoId = colaboradorId + "_" + certificacao.getId();
		
		if(certificacao.getCertificacaoPreRequisito() == null || certificacao.getCertificacaoPreRequisito().getId() == null)
			return colaboradorCertificadoMap.get(colaboradorCertificacaoId).getAprovadoNaTurma();
		else{
			
			boolean aprovadoNoCertificadoFilha = colaboradorCertificadoMap.get(colaboradorCertificacaoId).getAprovadoNaTurma();
			boolean aprovadoNoCertificadoMae = false;
			
			if(colaboradorCertificadoMap.containsKey(colaboradorId + "_" + certificacao.getCertificacaoPreRequisito().getId()))
				aprovadoNoCertificadoMae = verificaStatusCertificacaoRecursivo(colaboradorCertificadoMap, colaboradorId, colaboradorCertificadoMap.get(colaboradorId + "_" + certificacao.getCertificacaoPreRequisito().getId()));
			
			return aprovadoNoCertificadoFilha && aprovadoNoCertificadoMae;
		}
	}

	private void populaAvaliacoesPraticas(Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno, ColaboradorCertificacao colaboradorCertificacao) 
	{
		ColaboradorCertificacao colabCertificacao;
		Collection<AvaliacaoPratica> avaliacoesPraticas = avaliacaoPraticaManager.findByCertificacaoId(colaboradorCertificacao.getCertificacao().getId());
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), colaboradorCertificacao.getId());
		
		for (AvaliacaoPratica avaliacaoPratica : avaliacoesPraticas) 
		{
			if(avaliacaoPratica.getId() == null)
				continue;
			
			colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
			colabCertificacao.setPeriodoTurma("-");
			colabCertificacao.setNomeCurso("Avaliação Prática: " + avaliacaoPratica.getTitulo());
			
			colabCertificacao.getCertificacao().setAprovadoNaTurma(false);
			for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
			{
				if(avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPratica.getId()))
				{
					colabCertificacao.getCertificacao().setAprovadoNaTurma(avaliacaoPraticaDoColaboradorRealizada.getNota() >= avaliacaoPratica.getNotaMinima());
					break;
				}
			}
			
			colaboradorCertificacaosRetorno.add(colabCertificacao);
		}
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
	
	@SuppressWarnings("deprecation")
	public Collection<ColaboradorCertificacao> certificaByColaboradorTurmaId(Long colaboradorTurmaId){
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");
		Collection<ColaboradorCertificacao> colaboradorCertificadosRetorno = new ArrayList<ColaboradorCertificacao>();
		Collection<ColaboradorCertificacao> colaboradorCertificados  = getDao().colaboradoresCertificadosByColaboradorTurmaId(colaboradorTurmaId);
		
		for (ColaboradorCertificacao colaboradorCertificado : colaboradorCertificados){
			if(colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito() == null || colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId() == null){
				colaboradorCertificadosRetorno.add(colaboradorCertificado);
			}else if(verificaCertificacaoPreRequisitoRecursivo(colaboradorCertificado.getColaborador().getId(), colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId()).size() > 0){
				colaboradorCertificadosRetorno.add(colaboradorCertificado);
			}
		}
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificadosRetorno){ 
			saveColaboradorCertificacao(colaboradorCertificacao);
			certificaDependentesRecursivo(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId(), certificacaoManager);
		}
		
		return colaboradorCertificadosRetorno;
	}

	private void certificaDependentesRecursivo(Long colaboradorId, Long certificacaoId, CertificacaoManager certificacaoManager) {
		Collection<Certificacao> certificadosDependentes = certificacaoManager.findDependentes(certificacaoId);
		
		if(certificadosDependentes != null){
			for (Certificacao certificacao : certificadosDependentes){ 
				ColaboradorCertificacao colaboradorCertificacao  = getDao().verificaCertificacao(colaboradorId,certificacao.getId());
				if(colaboradorCertificacao != null){
					saveColaboradorCertificacao(colaboradorCertificacao);
					certificaDependentesRecursivo(colaboradorId, certificacao.getId(), certificacaoManager);
				}
			}
		}
	}

	private Collection<ColaboradorCertificacao> verificaCertificacaoPreRequisitoRecursivo(Long colaboradorId, Long certificacaoId)
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificados = new ArrayList<ColaboradorCertificacao>();
		ColaboradorCertificacao colaboradorCertificado  = getDao().findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
		
		if(colaboradorCertificado != null && DateUtil.incrementaMes(colaboradorCertificado.getData(),colaboradorCertificado.getCertificacao().getPeriodicidade()).getTime() >= (new Date()).getTime())
		{
			if(colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito() == null || colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId() == null){
				colaboradoresCertificados.add(colaboradorCertificado);
			}else if(verificaCertificacaoPreRequisitoRecursivo(colaboradorId, colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId()).size() > 0){
				colaboradoresCertificados.add(colaboradorCertificado);
			}
		}
		
		return colaboradoresCertificados;
	}
	
	//Verificar Samuel
	public Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorIdAndCertificacaId(Long colaboradorId, Long certificacaoId)
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificados = new ArrayList<ColaboradorCertificacao>();
		ColaboradorCertificacao colaboradorCertificado  = getDao().verificaCertificacao(colaboradorId, certificacaoId);
		
		if(colaboradorCertificado != null)
		{
			colaboradoresCertificados.add(colaboradorCertificado);
			if(colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito() != null && colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId() != null)
				colaboradoresCertificados.addAll(colaboradoresCertificadosByColaboradorIdAndCertificacaId(colaboradorCertificado.getColaborador().getId(), colaboradorCertificado.getCertificacao().getCertificacaoPreRequisito().getId()));
		}

//		if(colaboradoresCertificados.size() > 0)
//		{
//			Collection<ColaboradorCertificacao> colaboradoresCertificadosFilhas = getDao().getColaboradorCertificadoFilhas(new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificados), colaboradorId);  
//			for (ColaboradorCertificacao colaboradorCertificacaoFilha : colaboradoresCertificadosFilhas) 
//				colaboradoresCertificados.add(getDao().colaboradorCertificadoByColaboradorIdAndCertificacaId(colaboradorId, colaboradorCertificacaoFilha.getCertificacao().getId()));
//		}
//		
		return colaboradoresCertificados;
	}
	
	public Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId) 
	{
		return getDao().getCertificacoesAVencer(data, empresaId);
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