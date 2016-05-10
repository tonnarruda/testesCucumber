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
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
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
	
	public Collection<Colaborador> colaboradoresQueParticipamDaCertificacao(Long[] colaboradoresIds, Long... certificadosId){
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = getDao().colaboradoresQueParticipamDaCertificacao(null, null, null, certificadosId, null, null, colaboradoresIds, false);
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificacoes) {
			colaboradores.add(colaboradorCertificacao.getColaborador());
		}
		
		return colaboradores;
	}
	
	public Collection<ColaboradorCertificacao> populaAvaliaçõesPraticasRealizadas(Long[] colaboradoresIds, Long certificacaoId) {
		Collection<ColaboradorAvaliacaoPratica> colabAvaliacoesPraticasTemp = new ArrayList<ColaboradorAvaliacaoPratica>();
		CollectionUtil<ColaboradorAvaliacaoPratica> colectionUtil = new CollectionUtil<ColaboradorAvaliacaoPratica>();
		
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = getDao().colaboradoresQueParticipamDaCertificacao(null, null, null, new Long[]{certificacaoId}, null, null, colaboradoresIds, false);
		Long[] colaboradoresIdsParticipantes = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacoes, "getColaboradorId");
		Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacaoId, colaboradoresIdsParticipantes);
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificacoes) {
			Long colaboradorId = colaboradorCertificacao.getColaborador().getId();
			if(mapColaboradorAvaliacoesPraticas.containsKey(colaboradorId)){
				colabAvaliacoesPraticasTemp = colectionUtil.sortCollectionDate(mapColaboradorAvaliacoesPraticas.get(colaboradorId), "data", "desc");
				if(colabAvaliacoesPraticasTemp != null && colabAvaliacoesPraticasTemp.size() > 0){
					colaboradorCertificacao.setColaboradoresAvaliacoesPraticas(new ArrayList<ColaboradorAvaliacaoPratica>());
					colaboradorCertificacao.getColaboradoresAvaliacoesPraticas().addAll(colabAvaliacoesPraticasTemp);
					colaboradorCertificacao.setColaboradorAvaliacaoPraticaAtual((ColaboradorAvaliacaoPratica)colabAvaliacoesPraticasTemp.toArray()[0]);
				}
			}
		}
		
		return colaboradoresCertificacoes;
	}

	public Collection<ColaboradorCertificacao> colaboradoresParticipamCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds)
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificacao = new ArrayList<ColaboradorCertificacao>();
		if(colaboradorCertificado  && colaboradorNaoCertificado)
			colaboradoresCertificacao = getDao().colaboradoresQueParticipamDaCertificacao(dataIni, dataFim, mesesCertificacoesAVencer, certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds, false);
		else{
			Collection<ColaboradorCertificacao> colaboradoresCertificados = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds);
			if(colaboradorCertificado)
				colaboradoresCertificacao = colaboradoresCertificados;
			else if(colaboradorNaoCertificado){
				colaboradoresCertificacao = getDao().findColaboradoresQueParticipamDaCertificacao(certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds);
				colaboradoresCertificacao.removeAll(colaboradoresCertificados);
			}
		}
		return colaboradoresCertificacao;
	}
	
	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] colaboradoresIds) throws CloneNotSupportedException{
		Map<String, ColaboradorCertificacao> mapColaboradoresCertificacaoes = montaMapColaboradorCertificacao(dataIni, dataFim, mesesCertificacoesAVencer, colaboradorCertificado, colaboradorNaoCertificado, areaIds, estabelecimentoIds, certificacoesIds, colaboradoresIds);
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = avaliacaoPraticaManager.findMapByCertificacaoId(certificacoesIds);

		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		for (String colaboradorCertificacaoMap : mapColaboradoresCertificacaoes.keySet()){
			for (ColaboradorTurma colaboradorTurma : mapColaboradoresCertificacaoes.get(colaboradorCertificacaoMap).getColaboradoresTurmas()){
				ColaboradorCertificacao colabCertificacao = (ColaboradorCertificacao) mapColaboradoresCertificacaoes.get(colaboradorCertificacaoMap).clone();
				colabCertificacao.setNomeCurso(colaboradorTurma.getCurso().getNome());
				colabCertificacao.setCertificacao((Certificacao) colabCertificacao.getCertificacao().clone());
				
				if(colaboradorTurma.getTurma() != null){
					colabCertificacao.setPeriodoTurma(formataPeriodo(colaboradorTurma.getTurma().getDataPrevIni(), colaboradorTurma.getTurma().getDataPrevFim()));
					colabCertificacao.getCertificacao().setAprovadoNaTurma(colaboradorTurma.isAprovado() && colaboradorTurma.getTurma().getRealizada());
				} else{
					colabCertificacao.setPeriodoTurma("Não realizou o curso");
					colabCertificacao.getCertificacao().setAprovadoNaTurma(false);
				}
				colaboradorCertificacaosRetorno.add(colabCertificacao);
			}

			ColaboradorCertificacao colaboradorCertificacao = mapColaboradoresCertificacaoes.get(colaboradorCertificacaoMap);
			colaboradorCertificacaosRetorno.addAll(populaAvaliacoesPraticas(colaboradorCertificacao, mapAvaliacoesPraticas.get(colaboradorCertificacao.getCertificacao().getId())));
		}
		return colaboradorCertificacaosRetorno;
	}
	
	private Collection<ColaboradorCertificacao> populaAvaliacoesPraticas(ColaboradorCertificacao colaboradorCertificacao, Collection<AvaliacaoPratica> avaliacoesPraticas){
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorCertificacao.getColaboradoresAvaliacoesPraticas();
		for (AvaliacaoPratica avaliacaoPratica : avaliacoesPraticas){
			if(avaliacaoPratica.getId() == null) 
				continue;
			
			ColaboradorCertificacao colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
			colabCertificacao.setPeriodoTurma("Não possui nota da av. prática");
			colabCertificacao.setNomeCurso("Avaliação Prática: " + avaliacaoPratica.getTitulo());
			colabCertificacao.setCertificacao((Certificacao) colaboradorCertificacao.getCertificacao().clone());
			colabCertificacao.getCertificacao().setAprovadoNaTurma(false);
			
			for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas){
				if(colaboradorCertificacao.getId() != null){
					if(avaliacaoPraticaDoColaboradorRealizada.getColaboradorCertificacao() != null && avaliacaoPraticaDoColaboradorRealizada.getColaboradorCertificacao().getId() != null 
							&& colaboradorCertificacao.getId().equals(avaliacaoPraticaDoColaboradorRealizada.getColaboradorCertificacao().getId()) && avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPratica.getId())){
						colabCertificacao.getCertificacao().setAprovadoNaTurma(avaliacaoPraticaDoColaboradorRealizada.getNota() >= avaliacaoPratica.getNotaMinima());
						colabCertificacao.setPeriodoTurma("Data da Avaliação: " + avaliacaoPraticaDoColaboradorRealizada.getDataFormatada());
						break;
					}
				}else if(avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPratica.getId())){
					colabCertificacao.getCertificacao().setAprovadoNaTurma(avaliacaoPraticaDoColaboradorRealizada.getNota() >= avaliacaoPratica.getNotaMinima());
					colabCertificacao.setPeriodoTurma("Data da Avaliação: " + avaliacaoPraticaDoColaboradorRealizada.getDataFormatada());
					break;
				}
			}
			colaboradorCertificacaosRetorno.add(colabCertificacao);
		}
		return colaboradorCertificacaosRetorno;
	}	
	
	@SuppressWarnings("deprecation")
	private Map<String, ColaboradorCertificacao> montaMapColaboradorCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds) {
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");
		Map<String, ColaboradorCertificacao> mapColaboradorCertificacao = new HashMap<String, ColaboradorCertificacao>();
		Collection<Certificacao> certificacoes = certificacaoManager.findCollectionByIdProjection(certificacoesIds);
		for (Certificacao certificacao : certificacoes) {
			Collection<Curso> cursos = certificacaoManager.findCursosByCertificacaoId(certificacao.getId());
			Collection<ColaboradorCertificacao> colaboradoresCertificacao = colaboradoresParticipamCertificacao(dataIni, dataFim, mesesCertificacoesAVencer, colaboradorCertificado, colaboradorNaoCertificado, areaIds, estabelecimentoIds, new Long[]{certificacao.getId()}, filtroColaboradoresIds);
			Long[] colaboradoresIds = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacao, "getColaboradorId");
			if(colaboradoresIds.length > 0){
				Map<Long, Collection<ColaboradorTurma>> mapColaboradoresTurmas = colaboradorTurmaManager.findMapByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacao.getId(), colaboradoresIds);
				Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao.getId(), colaboradoresIds);
	
				for (ColaboradorCertificacao colaboradorCert : colaboradoresCertificacao) {
					Long colaboradorId = colaboradorCert.getColaborador().getId();
					String colaboradorCertificacaoMap = colaboradorId + "_" + certificacao.getId(); 
					ColaboradorCertificacao colaboradorCertificacao = (ColaboradorCertificacao) colaboradorCert.clone();
					colaboradorCertificacao.setCertificacao((Certificacao) certificacao.clone());
					if(!mapColaboradorCertificacao.containsKey(colaboradorCertificacaoMap)){
						colaboradorCertificacao.setColaboradoresTurmas(new ArrayList<ColaboradorTurma>());
						colaboradorCertificacao.setColaboradoresAvaliacoesPraticas(new ArrayList<ColaboradorAvaliacaoPratica>());
						mapColaboradorCertificacao.put(colaboradorCertificacaoMap, colaboradorCertificacao);
					}
					
					if(mapColaboradoresTurmas.containsKey(colaboradorId))
						mapColaboradorCertificacao.get(colaboradorCertificacaoMap).getColaboradoresTurmas().addAll(montaColaboradorTurma(cursos, mapColaboradoresTurmas.get(colaboradorId), colaboradorCert));
					if(mapColaboradorAvaliacoesPraticas.containsKey(colaboradorId))
						mapColaboradorCertificacao.get(colaboradorCertificacaoMap).getColaboradoresAvaliacoesPraticas().addAll(mapColaboradorAvaliacoesPraticas.get(colaboradorId));
				}
			}
		}
		return mapColaboradorCertificacao;
	}

	private Collection<ColaboradorTurma> montaColaboradorTurma(Collection<Curso> cursos, Collection<ColaboradorTurma> colaboradoresTurmas, ColaboradorCertificacao colaboradorCert){
		Collection<ColaboradorTurma> colabTurmasRetorno = new ArrayList<ColaboradorTurma>();
		
		for (Curso curso : cursos) {
			boolean possuiCurso = false;
			
			if(colaboradoresTurmas != null){
				for (ColaboradorTurma colaboradorTurma : colaboradoresTurmas){
					if(curso.getId().equals(colaboradorTurma.getCurso().getId())){
						colabTurmasRetorno.add(colaboradorTurma);
						possuiCurso = true;
						break;
					}
				}
			}
			if(!possuiCurso){
				ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
				colaboradorTurma.setColaborador(colaboradorCert.getColaborador());
				colaboradorTurma.setCurso((Curso) curso.clone());
				colabTurmasRetorno.add(colaboradorTurma);
			}
		}

		return colabTurmasRetorno;
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

	@SuppressWarnings("deprecation")
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
		if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null){
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