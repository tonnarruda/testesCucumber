package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
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
import com.fortes.web.tags.CheckBox;

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
	
	public Collection<ColaboradorCertificacao> possuemAvaliacoesPraticasRealizadas(Long certificacaoId, ColaboradorTurmaManager colaboradorTurmaManager) {
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = getDao().colaboradoresAprovadosEmTodosOsCursosDaCertificacao(certificacaoId);
		if(CollectionUtils.isEmpty(colaboradoresCertificacoes))
			return colaboradoresCertificacoes;
		
		Collection<ColaboradorAvaliacaoPratica> colabAvaliacoesPraticasTemp = new ArrayList<ColaboradorAvaliacaoPratica>();
		CollectionUtil<ColaboradorAvaliacaoPratica> colectionUtil = new CollectionUtil<ColaboradorAvaliacaoPratica>();
		
		Long[] colaboradoresIdsParticipantes = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacoes, "getColaboradorId");
		Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacaoId, colaboradoresIdsParticipantes);
		
		Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacaoId, null, colaboradoresIdsParticipantes);

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
			if(CollectionUtils.isNotEmpty(colaboradoresTurmas))
				colaboradoresTurmas = configuraSePossivelInserirNovaNotaAvaPratica(colaboradoresTurmas, colaboradorCertificacao);
		}
		return colaboradoresCertificacoes;
	}
	
	private Collection<ColaboradorTurma> configuraSePossivelInserirNovaNotaAvaPratica(Collection<ColaboradorTurma> colaboradoresTurmas, final ColaboradorCertificacao colaboradorCertificacao) {
		Collection<ColaboradorTurma> colaboradoresTurmasDoColaboradorCertificacao = new ArrayList<>();
		Collection<ColaboradorTurma> colaboradoresTurmasRetorno = new ArrayList<>();

		CollectionUtils.select(colaboradoresTurmas, new Predicate<ColaboradorTurma>() {
			public boolean evaluate(ColaboradorTurma colaboradorTurma) {
				return colaboradorTurma.getColaborador().getId().equals(colaboradorCertificacao.getColaborador().getId());
			}
		}, colaboradoresTurmasDoColaboradorCertificacao, colaboradoresTurmasRetorno);

		if(CollectionUtils.isNotEmpty(colaboradoresTurmasDoColaboradorCertificacao)){
			colaboradorCertificacao.setPossivelInserirNotaAvPratica(true);
			for (ColaboradorTurma colaboradorTurma : colaboradoresTurmasDoColaboradorCertificacao) {
				if(!colaboradorTurma.isAprovado()){
					colaboradorCertificacao.setPossivelInserirNotaAvPratica(false);
					break;
				}
			}
		}
		return colaboradoresTurmasRetorno;
	}
	
	public Collection<ColaboradorCertificacao> colaboradoresCertificados(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Long[] certificacoesIds, Long[] areaIds, Long[] estabelecimentoIds, Long[] filtroColaboradoresIds, String situacaoColaborador){
		return getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, certificacoesIds, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);
	}
	
	private Collection<ColaboradorCertificacao> populaAvaliacoesPraticas(ColaboradorCertificacao colaboradorCertificacao, Certificacao certificacao, Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas, Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas){
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		if(mapAvaliacoesPraticas.containsKey(certificacao.getId())){
			for (AvaliacaoPratica avaliacaoPratica : mapAvaliacoesPraticas.get(certificacao.getId())){
				if(avaliacaoPratica.getId() == null) 
					continue;
				
				ColaboradorCertificacao colabCertificacao = (ColaboradorCertificacao) colaboradorCertificacao.clone();
				colabCertificacao.setPeriodoTurma("Não possui nota da av. prática");
				colabCertificacao.setNomeCurso(AvaliacaoPratica.SUFIXO_ORDENACAO_ULTIMA_POSICAO + "Avaliação Prática: " + avaliacaoPratica.getTitulo());
				colabCertificacao.setCertificacao((Certificacao) certificacao.clone());
				colabCertificacao.getCertificacao().setAprovadoNaTurma(false);

				if(mapColaboradorAvaliacoesPraticas != null && mapColaboradorAvaliacoesPraticas.containsKey(colaboradorCertificacao.getColaborador().getId())){
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
	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Boolean certificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds, String situacaoColaborador, Long empresaId) throws CloneNotSupportedException {
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");
		Collection<Certificacao> certificacoes = certificacaoManager.findCollectionByIdProjection(certificacoesIds);
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = avaliacaoPraticaManager.findMapByCertificacaoId(certificacoesIds);
		Map<Long, Collection<Curso>> mapCursosCertificacao = new HashMap<Long, Collection<Curso>>();
		Map<Long, Map<Long, Collection<ColaboradorAvaliacaoPratica>>> mapCertificadoColaboradorAvaliacoesPraticas = new HashMap<Long, Map<Long, Collection<ColaboradorAvaliacaoPratica>>>();
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>(); 
		List<Long> arrayCursosIds = new ArrayList<Long>();
		
		for (Certificacao certificacao : certificacoes) {
			Collection<Curso> cursos = certificacaoManager.findCursosByCertificacaoId(certificacao.getId());
			if(cursos.size() == 0)	
				continue;
			
			mapCursosCertificacao.put(certificacao.getId(), cursos);
			Collection<ColaboradorCertificacao> colaboradoresCertificacaoes = new ArrayList<ColaboradorCertificacao>();
			colaboradoresCertificacaoes = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, false);

			if(Boolean.FALSE.equals(certificado)){
				Collection<ColaboradorCertificacao> colaboradoresCertificadosVencidos = getDao().findColaboradoresCertificados(dataIni, dataFim, mesesCertificacoesAVencer, new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, true);
				Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacaoNaoCertificados = getDao().findColaboradoresQueParticipamDaCertificacao(new Long[]{certificacao.getId()}, areaIds, estabelecimentoIds, filtroColaboradoresIds, situacaoColaborador, new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacaoes, "getColaboradorId"));
				colaboradoresCertificacaoes.addAll(decideColaboradorCertificacaoAdicionar(colaboradoresCertificadosVencidos, colaboradoresQueParticipamDaCertificacaoNaoCertificados));
				colaboradoresCertificacaoes = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradoresCertificacaoes, "colaborador.nome");
			}
			
			if(colaboradoresCertificacaoes.size() == 0)
				continue;
			
			Long[] colaboradoresIds = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacaoes, "getColaboradorId");
			Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao.getId(), colaboradoresIds);
			mapCertificadoColaboradorAvaliacoesPraticas.put(certificacao.getId(), mapColaboradorAvaliacoesPraticas);
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

		if(colaboradorCertificacaosRetorno.size() == 0 || (certificado != null && certificado))
			return colaboradorCertificacaosRetorno;
		else{ 
			adicionarCertificacoesATodosOsColaboradores(certificacoes,mapAvaliacoesPraticas, mapCursosCertificacao, mapCertificadoColaboradorAvaliacoesPraticas, colaboradorCertificacaosRetorno, filtroColaboradoresIds, areaIds, estabelecimentoIds, situacaoColaborador, empresaId);
		
			if(certificado == null)
				return colaboradorCertificacaosRetorno;
			
			CollectionUtils.filterInverse(colaboradorCertificacaosRetorno, new Predicate<ColaboradorCertificacao>() {
				@Override
				public boolean evaluate(ColaboradorCertificacao colaboradorCertificacao) {
					return colaboradorCertificacao.getAprovadoNaCertificacaoString().equals("Certificado");
				}
			});
			
			return colaboradorCertificacaosRetorno;
		}
	}

	private void adicionarCertificacoesATodosOsColaboradores(Collection<Certificacao> certificacoes,	Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas, Map<Long, Collection<Curso>> mapCursosCertificacao, Map<Long, Map<Long, Collection<ColaboradorAvaliacaoPratica>>> mapCertificadoColaboradorAvaliacoesPraticas, Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno, Long[] filtroColaboradoresIds, Long[] areasIds, Long[] estabelecimentosIds, String situacaoColaborador, Long empresaId) throws CloneNotSupportedException {
		Map<Long, Collection<Long>> mapColabCertificoes = new HashMap<Long, Collection<Long>>();
		Map<Long, Colaborador> mapColaborador = new HashMap<Long, Colaborador>();
		Long idColaborador = null;
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificacaosRetorno) {
			idColaborador = colaboradorCertificacao.getColaborador().getId();

			if(!mapColabCertificoes.containsKey(idColaborador))
				mapColabCertificoes.put(idColaborador, new ArrayList<Long>());
			
			mapColabCertificoes.get(idColaborador).add(colaboradorCertificacao.getCertificacao().getId());
			
			if(!mapColaborador.containsKey(idColaborador))
				mapColaborador.put(idColaborador, colaboradorCertificacao.getColaborador());
		}
		
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Collection<Colaborador> colaboradoresAindaNaoAdicionadosNaCertificacao = colaboradorManager.findDadosBasicosNotIds(mapColaborador.keySet(), filtroColaboradoresIds, areasIds, estabelecimentosIds, situacaoColaborador, empresaId);
		for (Colaborador colaborador : colaboradoresAindaNaoAdicionadosNaCertificacao) {
			idColaborador = colaborador.getId();
			
			if(!mapColabCertificoes.containsKey(idColaborador))
				mapColabCertificoes.put(idColaborador, new ArrayList<Long>());
			
			if(!mapColaborador.containsKey(idColaborador))
				mapColaborador.put(idColaborador, colaborador);	
		}
		
		for(Map.Entry<Long, Collection<Long>> mapColabCertificao : mapColabCertificoes.entrySet()){
			for(Certificacao certificacao : certificacoes) {
				if(mapColabCertificao.getValue().contains(certificacao.getId()))
					continue;
				else{
					ColaboradorCertificacao colaboradorCertificacao = null;
					
					for(Curso curso : mapCursosCertificacao.get(certificacao.getId())) {
						colaboradorCertificacao = new ColaboradorCertificacao();
						colaboradorCertificacao.setColaborador((Colaborador) mapColaborador.get(mapColabCertificao.getKey()).clone());
						colaboradorCertificacao.setCertificacao((Certificacao) certificacao.clone());
						colaboradorCertificacao.setNomeCurso(curso.getNome());
						colaboradorCertificacao.setPeriodoTurma("Não realizou o curso");
						colaboradorCertificacao.getCertificacao().setAprovadoNaTurma(false);
						colaboradorCertificacaosRetorno.add(colaboradorCertificacao);
					}
					
					if(colaboradorCertificacao != null)
						colaboradorCertificacaosRetorno.addAll(populaAvaliacoesPraticas(colaboradorCertificacao, certificacao, mapCertificadoColaboradorAvaliacoesPraticas.get(certificacao.getId()), mapAvaliacoesPraticas));
				}
			}
		}
	}

	private Collection<ColaboradorCertificacao> decideColaboradorCertificacaoAdicionar(Collection<ColaboradorCertificacao> colaboradoresCertificadosVencidos,Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacaoNaoCertificados) {
		Collection<ColaboradorCertificacao> colaboradoresCertificacaoes = new ArrayList<ColaboradorCertificacao>();
		
		Map<Long, Collection<ColaboradorCertificacao>> mapColaboradoresComCertificacaoVencida = new HashMap<Long, Collection<ColaboradorCertificacao>>();
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificadosVencidos) {
			if(!mapColaboradoresComCertificacaoVencida.containsKey(colaboradorCertificacao.getColaboradorId()))
				mapColaboradoresComCertificacaoVencida.put(colaboradorCertificacao.getColaboradorId(), new ArrayList<ColaboradorCertificacao>());
			mapColaboradoresComCertificacaoVencida.get(colaboradorCertificacao.getColaboradorId()).add(colaboradorCertificacao);
		}

		Map<Long, Collection<ColaboradorCertificacao>> mapColaboradoresQueParticipamDaCertificacaoNaoCertificados = new HashMap<Long, Collection<ColaboradorCertificacao>>();
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresQueParticipamDaCertificacaoNaoCertificados) {
			if(!mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.containsKey(colaboradorCertificacao.getColaboradorId()))
				mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.put(colaboradorCertificacao.getColaboradorId(), new ArrayList<ColaboradorCertificacao>());
			mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.get(colaboradorCertificacao.getColaboradorId()).add(colaboradorCertificacao);
		}

		Collection<ColaboradorCertificacao> colabCertificacaoDiferentes = new ArrayList<ColaboradorCertificacao>();
		for(Long colaboradorId : mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.keySet()){
			colabCertificacaoDiferentes.clear();

			if(mapColaboradoresComCertificacaoVencida.containsKey(colaboradorId)){
				for (ColaboradorCertificacao colabCertificacaoVencido : mapColaboradoresComCertificacaoVencida.get(colaboradorId)){
					for (ColaboradorCertificacao colabCertificacao : mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.get(colaboradorId)){
						if(colabCertificacaoVencido.getColaboradorTurma().getCurso().getId().equals(colabCertificacao.getColaboradorTurma().getCurso().getId())	
								&& !colabCertificacaoVencido.getColaboradorTurma().getId().equals(colabCertificacao.getColaboradorTurma().getId())){
							colabCertificacaoDiferentes.add(colabCertificacao);
						}
					}
				}
			}
			if(colabCertificacaoDiferentes.size() > 0){
				for (ColaboradorCertificacao colabCertificacao : mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.get(colaboradorId)){	
					if(!colabCertificacaoDiferentes.contains(colabCertificacao)){
						colabCertificacao.setPeriodoTurma("Não realizou o curso");
						colabCertificacao.getColaboradorTurma().setAprovado(false);
						colabCertificacao.getColaboradorTurma().setTurma(null);
					}
					colaboradoresCertificacaoes.add(colabCertificacao);
				}
			}else{
				if(mapColaboradoresComCertificacaoVencida.containsKey(colaboradorId))
					colaboradoresCertificacaoes.addAll(mapColaboradoresComCertificacaoVencida.get(colaboradorId));
				else
					colaboradoresCertificacaoes.addAll(mapColaboradoresQueParticipamDaCertificacaoNaoCertificados.get(colaboradorId));
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

	public void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId) {
		Collection<ColaboradorCertificacao> colaboradoresCertificados = getDao().findByColaboradorTurma(colaboradorTurmaId);
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificados) {
			if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null){
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
		if(colaboradoresCertificacoes.size() > 0){
			Long[] colaboradorcertificacaoIds = new CollectionUtil<ColaboradorCertificacao>().convertCollectionToArrayIds(colaboradoresCertificacoes);
			Collection<Long> certificacoesIdsPrerequisito = getDao().findCertificacoesIdsDependentes(colaboradorcertificacaoIds);
			colaboradorAvaliacaoPraticaManager.setColaboradorCertificacoNull(colaboradorcertificacaoIds);
			
			if(certificacoesIdsPrerequisito.size() > 0){
				for (Long certificacoId : certificacoesIdsPrerequisito) 
					descertificaRecursivo(certificacoId);
			}
			
			getDao().removeColaboradorCertificacaoColaboradorTurma(certificacaoId);
			getDao().remove(colaboradorcertificacaoIds);
		}
		
		getDao().getHibernateTemplateByGenericDao().flush();
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

	public void setCertificaçõesNomesInColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas) {
		if(colaboradorTurmas.size() == 0)
			return;
		
		Map<Long, ColaboradorTurma> colaboradoresTurmaMap = getDao().findCertificaçõesNomesByColaboradoresTurmasIds(new CollectionUtil<ColaboradorTurma>().convertCollectionToArrayIds(colaboradorTurmas));
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
			if(colaboradoresTurmaMap.containsKey(colaboradorTurma.getId())){
				colaboradorTurma.setCertificado(true);
				colaboradorTurma.setCertificacoesNomes(colaboradoresTurmaMap.get(colaboradorTurma.getId()).getCertificacoesNomes());
			}
		}
	}
	
	public boolean isCertificadoByColaboradorTurmaId(Long colaboradorTurmaId){
		if(colaboradorTurmaId == null)
			return false;
		Map<Long, ColaboradorTurma> colaboradoresTurmaMap = getDao().findCertificaçõesNomesByColaboradoresTurmasIds(colaboradorTurmaId);
		return colaboradoresTurmaMap.containsKey(colaboradorTurmaId);
	}

	public Collection<CheckBox> checkBoxColaboradoresSemCertificacaoDWR(Long empresaId, Long[] areasIds, Long[] estabelecimentosIds, Long[] certificacoesIds, String situacaoColaborador) {
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBeanOld("certificacaoManager");
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>();
		Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
		
		for (Long certificacaoId : certificacoesIds) {
			Collection<Curso> cursos = certificacaoManager.findCursosByCertificacaoId(certificacaoId);
			if(cursos.size() == 0)	
				continue;
			
			Long[] cursosIds = new CollectionUtil<Curso>().convertCollectionToArrayIds(cursos, "getId");
			colaboradorCertificacaosRetorno.addAll(getDao().findColaboradoresCertificacoesQueNaoParticipamDoCurso(empresaId, areasIds, estabelecimentosIds, null, situacaoColaborador, cursosIds)); 
		}
		
    	Collection<Long> colaboradoresIdsAdicionados = new ArrayList<Long>();
    	for (ColaboradorCertificacao colaboradorCert : colaboradorCertificacaosRetorno) {
    		if(!colaboradoresIdsAdicionados.contains(colaboradorCert.getColaboradorId())){
	    		CheckBox checkBox = new CheckBox();
				checkBox.setId(colaboradorCert.getColaboradorId());
				checkBox.setNome(colaboradorCert.getColaborador().getNome());
				colaboradoresIdsAdicionados.add(colaboradorCert.getColaboradorId());
				checkboxes.add(checkBox);
			}
    	}
    	
    	if(checkboxes.size() > 0)
    		return new CollectionUtil<CheckBox>().sortCollectionStringIgnoreCase(checkboxes, "nome");
    	else
    		return checkboxes;
	}
	
	public Collection<ColaboradorCertificacao> colaboradoresSemCertificacao(Long empresaId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds, Long[] certificacoesIds, String situacaoColaborador) {
		CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBean("certificacaoManager");
		Collection<Certificacao> certificacoes = certificacaoManager.findCollectionByIdProjection(certificacoesIds);
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = avaliacaoPraticaManager.findMapByCertificacaoId(certificacoesIds);
		Collection<ColaboradorCertificacao> colaboradorCertificacaosRetorno = new ArrayList<ColaboradorCertificacao>(); 

		for (Certificacao certificacao : certificacoes) {
			Collection<Curso> cursos = certificacaoManager.findCursosByCertificacaoId(certificacao.getId());
			if(cursos.size() == 0)	
				continue;
			
			Long[] cursosIds = new CollectionUtil<Curso>().convertCollectionToArrayIds(cursos, "getId");
			Collection<ColaboradorCertificacao> colaboradoresCertificacaoes = getDao().findColaboradoresCertificacoesQueNaoParticipamDoCurso(empresaId, areasIds, estabelecimentosIds, colaboradoresIds, situacaoColaborador, cursosIds); 
			if(colaboradoresCertificacaoes.size() == 0)
				continue;

			for (ColaboradorCertificacao colabCertificacao : colaboradoresCertificacaoes) {
				for (Curso curso : cursos) {
					ColaboradorCertificacao colaboradorCertificacao = (ColaboradorCertificacao) colabCertificacao.clone();
					colaboradorCertificacao.setCertificacao((Certificacao) certificacao.clone());
					colaboradorCertificacao.setNomeCurso(curso.getNome());
					colaboradorCertificacao.setPeriodoTurma("Não realizou o curso");
					colaboradorCertificacao.getCertificacao().setAprovadoNaTurma(false);
					colaboradorCertificacaosRetorno.add(colaboradorCertificacao);
				}
				
				if(mapAvaliacoesPraticas.containsKey(certificacao.getId())){
					for (AvaliacaoPratica avaliacaoPratica : mapAvaliacoesPraticas.get(certificacao.getId())){
						if(avaliacaoPratica.getId() == null) 
							continue;

						ColaboradorCertificacao colabCertificacaoAVPratica = (ColaboradorCertificacao) colabCertificacao.clone();
						colabCertificacaoAVPratica.setPeriodoTurma("Não possui nota da av. prática");
						colabCertificacaoAVPratica.setNomeCurso("Avaliação Prática: " + avaliacaoPratica.getTitulo());
						colabCertificacaoAVPratica.setCertificacao((Certificacao) certificacao.clone());
						colabCertificacaoAVPratica.getCertificacao().setAprovadoNaTurma(false);
						colaboradorCertificacaosRetorno.add(colabCertificacaoAVPratica);
					}
				}
			}
		}
		
		return colaboradorCertificacaosRetorno;
	}

	public void setAvaliacaoPraticaManager( AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager) {
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}

	public boolean existiColaboradorCertificadoByTurma(Long turmaId) {
		return getDao().existiColaboradorCertificadoByTurma(turmaId);
	}
}