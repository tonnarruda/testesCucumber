package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	
	public Collection<Colaborador> colaboradoresQueParticipamDaCertificacao(Long certificacaoId){
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = getDao().colaboradoresAprovadosEmTodosOsCursosDaCertificacao(certificacaoId);
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificacoes) {
			if(!colaboradores.contains(colaboradorCertificacao.getColaborador()))
				colaboradores.add(colaboradorCertificacao.getColaborador());
		}
		
		return colaboradores;
	}
	
	public Collection<ColaboradorCertificacao> possuemAvaliacoesPraticasRealizadas(Long certificacaoId) {
		Collection<ColaboradorAvaliacaoPratica> colabAvaliacoesPraticasTemp = new ArrayList<ColaboradorAvaliacaoPratica>();
		CollectionUtil<ColaboradorAvaliacaoPratica> colectionUtil = new CollectionUtil<ColaboradorAvaliacaoPratica>();
		
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = getDao().colaboradoresAprovadosEmTodosOsCursosDaCertificacao(certificacaoId);
		if(colaboradoresCertificacoes == null || colaboradoresCertificacoes.size() == 0)
			return colaboradoresCertificacoes;
		
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

	public Collection<ColaboradorCertificacao> colaboradoresParticipamCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds, String situacaoColaborador)	{
			Collection<ColaboradorCertificacao> colaboradoresRetorno = new ArrayList<ColaboradorCertificacao>(); 
			if(colaboradorCertificado && colaboradorNaoCertificado){
				colaboradoresRetorno = getDao().findColaboradoresQueParticipamDaCertificacao(certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, null);
			}else{ 
				if(colaboradorCertificado)
					colaboradoresRetorno = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);
				else if(colaboradorNaoCertificado){
					Collection<ColaboradorCertificacao> colaboradoresCertificados = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);
					colaboradoresRetorno = getDao().findColaboradoresQueParticipamDaCertificacao(certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, null);
					Collection<ColaboradorCertificacao> colabCertificacaoASeremRemovidos = new ArrayList<ColaboradorCertificacao>();
					for (ColaboradorCertificacao colaboradorCertificacaoRetorno : colaboradoresRetorno){
						for (ColaboradorCertificacao colaboradorCertificados : colaboradoresCertificados) {
							if(colaboradorCertificados.getColaborador().getId().equals(colaboradorCertificacaoRetorno.getColaborador().getId())){
								colabCertificacaoASeremRemovidos.add(colaboradorCertificacaoRetorno);
							}
						}
					}
					colaboradoresRetorno.removeAll(colabCertificacaoASeremRemovidos);
				}
			}
				
		return colaboradoresRetorno;
	}
	
	private Collection<ColaboradorCertificacao> populaAvaliacoesPraticas(ColaboradorCertificacao colaboradorCertificacao, Certificacao certificacao, Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas, Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas){
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		if(mapAvaliacoesPraticas.containsKey(certificacao.getId())){
			for (AvaliacaoPratica avaliacaoPratica : mapAvaliacoesPraticas.get(certificacao.getId())){
				if(avaliacaoPratica.getId() == null) 
					continue;

				ColaboradorCertificacao colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
				colabCertificacao.setPeriodoTurma("Não possui nota da av. prática");
				colabCertificacao.setNomeCurso("Avaliação Prática: " + avaliacaoPratica.getTitulo());
				colabCertificacao.setCertificacao((Certificacao) certificacao.clone());
				colabCertificacao.getCertificacao().setAprovadoNaTurma(false);

				if(mapColaboradorAvaliacoesPraticas.containsKey(colaboradorCertificacao.getColaborador().getId())){
					for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : mapColaboradorAvaliacoesPraticas.get(colaboradorCertificacao.getColaborador().getId())){
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
				}
				colaboradorCertificacaosRetorno.add(colabCertificacao);
			}
		}
		return colaboradorCertificacaosRetorno;
	}	
	
	@SuppressWarnings("deprecation")
	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Boolean certificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds, String situacaoColaborador) {
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");
		Collection<Certificacao> certificacoes = certificacaoManager.findCollectionByIdProjection(certificacoesIds);
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = avaliacaoPraticaManager.findMapByCertificacaoId(certificacoesIds);
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>(); 
		List<Long> arrayCursosIds = new ArrayList<Long>();

		for (Certificacao certificacao : certificacoes) {
			Collection<Curso> cursos = certificacaoManager.findCursosByCertificacaoId(certificacao.getId());
			if(cursos.size() == 0)	
				continue;
			
			Collection<ColaboradorCertificacao> colaboradoresCertificacaoes = new ArrayList<ColaboradorCertificacao>();
			if(certificado == null){
				colaboradoresCertificacaoes = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);
				Collection<ColaboradorCertificacao> colaboradoresCertificadosVencidos = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, true);
				Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacaoNaoCertificados = getDao().findColaboradoresQueParticipamDaCertificacao(new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacaoes, "getColaboradorId"));
				colaboradoresCertificacaoes.addAll(decideColaboradorCertificacaoAdicionar(colaboradoresCertificadosVencidos, colaboradoresQueParticipamDaCertificacaoNaoCertificados));
				colaboradoresCertificacaoes = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradoresCertificacaoes, "colaborador.nome");
			} else if(certificado){
				colaboradoresCertificacaoes = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);
			} else {
				Collection<ColaboradorCertificacao> colaboradoresCertificados = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);
				Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacaoNaoCertificados = getDao().findColaboradoresQueParticipamDaCertificacao(new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificados, "getColaboradorId"));
				Collection<ColaboradorCertificacao> colaboradoresCertificadosVencidos = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, true);
				colaboradoresCertificacaoes.addAll(decideColaboradorCertificacaoAdicionar(colaboradoresCertificadosVencidos, colaboradoresQueParticipamDaCertificacaoNaoCertificados));
				colaboradoresCertificacaoes = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradoresCertificacaoes, "colaborador.nome");
			}
			
			if(colaboradoresCertificacaoes.size() == 0)
				continue;
			
			Long[] colaboradoresIds = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacaoes, "getColaboradorId");
			Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao.getId(), colaboradoresIds);
			ColaboradorCertificacao colaboradorCertificacaoAnterior = null;

			Long[] cursosIds = new CollectionUtil<Curso>().convertCollectionToArrayIds(cursos, "getId");
			for (ColaboradorCertificacao colabCertificacao : colaboradoresCertificacaoes) {
				if(colaboradorCertificacaoAnterior != null && !colabCertificacao.getColaborador().getId().equals(colaboradorCertificacaoAnterior.getColaborador().getId())){
					if( arrayCursosIds.size() < cursosIds.length ){
						for (Curso curso : cursos) {
							if(!arrayCursosIds.contains(curso.getId())){
								ColaboradorCertificacao colaboradorCertificacao = (ColaboradorCertificacao) colaboradorCertificacaoAnterior.clone();
								colaboradorCertificacao.setCertificacao((Certificacao) certificacao.clone());
								colaboradorCertificacao.setNomeCurso(curso.getNome());
								colaboradorCertificacao.setPeriodoTurma("Não realizou o curso");
								colaboradorCertificacao.getCertificacao().setAprovadoNaTurma(false);
								colaboradorCertificacaosRetorno.add(colaboradorCertificacao);
							}
						}
					}

					colaboradorCertificacaosRetorno.addAll(populaAvaliacoesPraticas(colaboradorCertificacaoAnterior, certificacao, mapColaboradorAvaliacoesPraticas, mapAvaliacoesPraticas));
					arrayCursosIds.clear();
				}

				colaboradorCertificacaoAnterior = colabCertificacao;
				if(colabCertificacao.getColaboradorTurma().getTurma() != null)
					colabCertificacao.setPeriodoTurma(formataPeriodo(colabCertificacao.getColaboradorTurma().getTurma().getDataPrevIni(), colabCertificacao.getColaboradorTurma().getTurma().getDataPrevFim()));

				colabCertificacao.setNomeCurso(colabCertificacao.getColaboradorTurma().getCurso().getNome());
				colabCertificacao.setCertificacao((Certificacao) certificacao.clone());
				colabCertificacao.getCertificacao().setAprovadoNaTurma(colabCertificacao.getColaboradorTurma().isAprovado());
				colaboradorCertificacaosRetorno.add(colabCertificacao);
				arrayCursosIds.add(colabCertificacao.getColaboradorTurma().getCurso().getId());
			}

			if(colaboradorCertificacaoAnterior != null){ 
				if( arrayCursosIds.size() < cursosIds.length ){
					for (Curso curso : cursos) {
						if(!arrayCursosIds.contains(curso.getId())){
							ColaboradorCertificacao colaboradorCertificacao = (ColaboradorCertificacao) colaboradorCertificacaoAnterior.clone();
							colaboradorCertificacao.setCertificacao((Certificacao) certificacao.clone());
							colaboradorCertificacao.setNomeCurso(curso.getNome());
							colaboradorCertificacao.setPeriodoTurma("Não realizou o curso");
							colaboradorCertificacao.getCertificacao().setAprovadoNaTurma(false);
							colaboradorCertificacaosRetorno.add(colaboradorCertificacao);
						}
					}
				}
				colaboradorCertificacaosRetorno.addAll(populaAvaliacoesPraticas(colaboradorCertificacaoAnterior, certificacao, mapColaboradorAvaliacoesPraticas, mapAvaliacoesPraticas));
			}

			arrayCursosIds.clear();
		}
		
		return colaboradorCertificacaosRetorno;
	}


	private Collection<ColaboradorCertificacao> decideColaboradorCertificacaoAdicionar(Collection<ColaboradorCertificacao> colaboradoresCertificadosVencidos,Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacaoNaoCertificados) {
		Collection<ColaboradorCertificacao> colaboradoresCertificacaoes = new ArrayList<ColaboradorCertificacao>();

		Map<Long, Collection<ColaboradorCertificacao>> mapColaboradorTurmaVencido = new HashMap<Long, Collection<ColaboradorCertificacao>>();
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificadosVencidos) {
			if(!mapColaboradorTurmaVencido.containsKey(colaboradorCertificacao.getColaboradorId()))
				mapColaboradorTurmaVencido.put(colaboradorCertificacao.getColaboradorId(), new ArrayList<ColaboradorCertificacao>());
			mapColaboradorTurmaVencido.get(colaboradorCertificacao.getColaboradorId()).add(colaboradorCertificacao);
		}

		Map<Long, Collection<ColaboradorCertificacao>> colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap = new HashMap<Long, Collection<ColaboradorCertificacao>>();
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresQueParticipamDaCertificacaoNaoCertificados) {
			if(!colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.containsKey(colaboradorCertificacao.getColaboradorId()))
				colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.put(colaboradorCertificacao.getColaboradorId(), new ArrayList<ColaboradorCertificacao>());
			colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.get(colaboradorCertificacao.getColaboradorId()).add(colaboradorCertificacao);
		}

		Collection<ColaboradorCertificacao> colabCertificacaoDiferentes = new ArrayList<ColaboradorCertificacao>();
		for(Long colaboradorId : colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.keySet()){
			colabCertificacaoDiferentes.clear();

			if(mapColaboradorTurmaVencido.containsKey(colaboradorId)){
				for (ColaboradorCertificacao colabCertificacaoVencido : mapColaboradorTurmaVencido.get(colaboradorId)){
					for (ColaboradorCertificacao colabCertificacao : colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.get(colaboradorId)){
						if(colabCertificacaoVencido.getColaboradorTurma().getCurso().getId().equals(colabCertificacao.getColaboradorTurma().getCurso().getId())	
								&& !colabCertificacaoVencido.getColaboradorTurma().getId().equals(colabCertificacao.getColaboradorTurma().getId())){
							colabCertificacaoDiferentes.add(colabCertificacao);
						}
					}
				}
			}
			if(colabCertificacaoDiferentes.size() > 0){
				for (ColaboradorCertificacao colabCertificacao : colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.get(colaboradorId)){	
					if(!colabCertificacaoDiferentes.contains(colabCertificacao)){
						colabCertificacao.setPeriodoTurma("Não realizou o curso");
						colabCertificacao.getColaboradorTurma().setAprovado(false);
						colabCertificacao.getColaboradorTurma().setTurma(null);
					}
					colaboradoresCertificacaoes.add(colabCertificacao);
				}
			}else{
				if(mapColaboradorTurmaVencido.containsKey(colaboradorId))
					colaboradoresCertificacaoes.addAll(mapColaboradorTurmaVencido.get(colaboradorId));
				else
					colaboradoresCertificacaoes.addAll(colaboradoresQueParticipamDaCertificacaoNaoCertificadosMap.get(colaboradorId));
			}
		}

		return colaboradoresCertificacaoes;
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
		
		if(colaboradorTurmaId != null){
			colaboradorCertificados  = getDao().colaboradoresCertificadosByColaboradorTurmaId(colaboradorTurmaId);
		}else if(colaboradorId != null && certificacaoId != null){
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
		
		if(colaboradorCertificadoTemp != null && (colaboradorCertificadoTemp.getCertificacao().getPeriodicidade() == null || DateUtil.incrementaMes(colaboradorCertificadoTemp.getData(),colaboradorCertificadoTemp.getCertificacao().getPeriodicidade()).getTime() >= (new Date()).getTime()))
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
		Collection<ColaboradorCertificacao> colaboradoresCertificados = getDao().findByColaboradorTurma(colaboradorTurmaId);
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificados) {
			if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null){
				if(removerColaboradorAvaliacaoPratica)
					colaboradorAvaliacaoPraticaManager.removeByColaboradorCertificacaoId(colaboradorCertificacao.getId());
				
				this.descertificarColaborador(colaboradorCertificacao.getId());
			}
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
		ColaboradorCertificacao colaboradorCertificadoExistente = getDao().findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getCertificacao().getId());
		
		Date dataColaboradorCertificacao = new Date();
		
		if(colaboradoresTurmas != null && colaboradoresTurmas.size() > 0)
			dataColaboradorCertificacao = ((ColaboradorTurma) colaboradoresTurmas.toArray()[0]).getTurma().getDataPrevFim();
		
		if(colaboradorAvaliacoesPraticas != null && colaboradorAvaliacoesPraticas.size() > 0)
			dataColaboradorCertificacao = ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData().after(dataColaboradorCertificacao) ? ((ColaboradorAvaliacaoPratica)colaboradorAvaliacoesPraticas.toArray()[0]).getData() : dataColaboradorCertificacao;
		
		if(colaboradorCertificadoExistente == null || colaboradorCertificadoExistente.getData().getTime() < dataColaboradorCertificacao.getTime()){	
			colaboradorCertificacao.setColaboradoresTurmas(colaboradoresTurmas);
			colaboradorCertificacao.setData(dataColaboradorCertificacao);
			getDao().save(colaboradorCertificacao);
			getDao().getHibernateTemplateByGenericDao().flush();
	
			for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
				colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
				colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
			}
		}
	}

	public void reprocessaCertificacao(Long certificacaoId, CertificacaoManager certificacaoManager) {
		descertificaRecursivo(certificacaoId);
		getDao().getHibernateTemplateByGenericDao().flush();
		Collection<Long> colaboradoresIds = getDao().possiveisColaboradoresCertificados(certificacaoId);
		for (Long colaboradorId : colaboradoresIds) {
			certificaColaborador(null, colaboradorId, certificacaoId, certificacaoManager);
		}
	}

	private void descertificaRecursivo(Long certificacaoId) {
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = getDao().findByColaboradorIdAndCertificacaoId(null, certificacaoId);
		colaboradorAvaliacaoPraticaManager.removeByCertificacaoId(certificacaoId);
		
		if(colaboradoresCertificacoes.size() > 0){
			Long[] colaboradorcertificacaoIds = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacoes);
			Collection<Long> certificacoesIdsPrerequisito = getDao().findCertificacoesIdsDependentes(colaboradorcertificacaoIds);
			
			if(certificacoesIdsPrerequisito.size() > 0){
				for (Long certificacoId : certificacoesIdsPrerequisito) 
					descertificaRecursivo(certificacoId);
			}
			
			getDao().removeColaboradorCertificacaoColaboradorTurma(certificacaoId);
			colaboradorAvaliacaoPraticaManager.setColaboradorCertificacoNull(colaboradorcertificacaoIds);
			getDao().remove(colaboradorcertificacaoIds);
		}
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