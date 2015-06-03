package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetencia, ConfiguracaoNivelCompetenciaDao> implements ConfiguracaoNivelCompetenciaManager 
{
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data) {
		return getDao().findByFaixa(faixaSalarialId, data);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId) {
		return getDao().findByCandidato(candidatoId);
	}

	public void saveCompetenciasCandidato(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, Long faixaSalarialId, Long candidatoId) 
	{
		getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId);

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) {
			if (configuracaoNivelCompetencia.getCompetenciaId() != null) {
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				configuracaoNivelCompetencia.setCandidatoIdProjection(candidatoId);

				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	public void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		ajustaConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		
		if (configuracaoNivelCompetenciaColaborador.getId() == null) { 
			configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.save(configuracaoNivelCompetenciaColaborador);
		} else {
			configuracaoNivelCompetenciaColaboradorManager.update(configuracaoNivelCompetenciaColaborador);
			getDao().deleteByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		} 

		if(configuracaoNiveisCompetencias != null) {
			
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) 
			{
				if (configuracaoNivelCompetencia.getCompetenciaId() != null) 
				{
					configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
					getDao().save(configuracaoNivelCompetencia);
				}
			}
		}
	}
	
	private void ajustaConfiguracaoNivelCompetenciaColaborador(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador){
		if(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario() != null){
			
			if(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getId() == null)			
				configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(null);
			else {
				if(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getAvaliacaoDesempenho() != null && configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getAvaliacaoDesempenho().getId() == null)
					configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().setAvaliacaoDesempenho(null);
				
				if(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getAvaliador() != null && configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getAvaliador().getId() == null)
					configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().setAvaliador(null);
			}
		}
		
		if(configuracaoNivelCompetenciaColaborador.getAvaliador() != null && (configuracaoNivelCompetenciaColaborador.getAvaliador().getId() == null || configuracaoNivelCompetenciaColaborador.getAvaliador().getId().equals(0L)))
			configuracaoNivelCompetenciaColaborador.setAvaliador(null);
	}

	public void saveCompetenciasFaixaSalarial(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial)
	{
		if (configuracaoNivelCompetenciaFaixaSalarial.getId() != null) 
		{
			configuracaoNivelCompetenciaFaixaSalarialManager.update(configuracaoNivelCompetenciaFaixaSalarial);
			getDao().deleteByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getId());
		} 
		else
			configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.save(configuracaoNivelCompetenciaFaixaSalarial);

		if(niveisCompetenciaFaixaSalariais != null)
		{
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) 
			{
				if (configuracaoNivelCompetencia.getCompetenciaId() != null) 
				{
					configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
					getDao().save(configuracaoNivelCompetencia);
				}
			}
		}
	}

	public Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId) {
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos = getDao().findByCandidato(candidatoId);

		if (!niveisCompetenciaFaixaSalariaisSalvos.isEmpty()) {
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);
			for (ConfiguracaoNivelCompetencia nivelCompetenciaCandidato : niveisCompetenciaFaixaSalariaisSalvos) {
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) {
					if (nivelCompetenciaCandidato.getCompetenciaId().equals(configuracaoNivelCompetencia.getCompetenciaId())
							&& nivelCompetenciaCandidato.getTipoCompetencia().equals(configuracaoNivelCompetencia.getTipoCompetencia())) {
						nivelCompetenciaCandidato.setCompetenciaDescricao(configuracaoNivelCompetencia.getCompetenciaDescricao());
						break;
					}
				}
			}
		}

		return niveisCompetenciaFaixaSalariaisSalvos;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) {
		return getDao().findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaboradorId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data) {
		return getDao().findCompetenciaByFaixaSalarial(faixaId, data);
	}

	public Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds, Long faixaSalarialId, Date data) 
	{
		Collection<ConfiguracaoNivelCompetencia> configuracaoAbaixos = new ArrayList<ConfiguracaoNivelCompetencia>();
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(data, null, competenciasIds, faixaSalarialId, false);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasFaixas = getDao().findCompetenciasFaixaSalarial(competenciasIds, faixaSalarialId);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) 
		{
			for (ConfiguracaoNivelCompetencia competenciaFaixa : configuracaoNivelCompetenciasFaixas)
			{
				if((competenciaFaixa.getConfiguracaoNivelCompetenciaFaixaSalarial().getData().equals(configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getData()) 
						|| competenciaFaixa.getConfiguracaoNivelCompetenciaFaixaSalarial().getData().before(configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getData()))
						&& competenciaFaixa.getCompetenciaId().equals(configuracaoNivelCompetencia.getCompetenciaId()))
				{
					configuracaoNivelCompetencia.setNivelCompetenciaDescricaoProjection(competenciaFaixa.getNivelCompetencia().getDescricao());
					configuracaoNivelCompetencia.setNivelCompetenciaOrdemProjection(competenciaFaixa.getNivelCompetencia().getOrdem());
				}
			}
			
			if(configuracaoNivelCompetencia.getNivelCompetencia() == null  || configuracaoNivelCompetencia.getNivelCompetencia().getOrdem() == null )
				continue;

			if (configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() != null && configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() < configuracaoNivelCompetencia.getNivelCompetencia().getOrdem())
				configuracaoAbaixos.add(configuracaoNivelCompetencia);
		}

		return configuracaoAbaixos;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Date dataIni, Date dataFim, Long empresaId, Long faixaSalarialId, Long[] competenciasIds) 
	{
		Collection<ConfiguracaoNivelCompetenciaVO> vos = new ArrayList<ConfiguracaoNivelCompetenciaVO>();

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(dataIni, dataFim, competenciasIds, faixaSalarialId, true);
		Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId);
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasFaixas = getDao().findCompetenciasFaixaSalarial(competenciasIds, faixaSalarialId);
		Map<String, Collection<MatrizCompetenciaNivelConfiguracao>> matrizCompetenciaNivelConfiguracaoMap = new HashMap<String, Collection<MatrizCompetenciaNivelConfiguracao>>();
		Map<String, Map<String, String>> competenciaNiveisConfiguradosMap = new HashMap<String, Map<String, String>>();
		Map<String, Integer> totalPontosMap = new HashMap<String, Integer>();
		
		Collection<Date> datasHistoricoFaixaSalarial = new ArrayList<Date>();
		for (ConfiguracaoNivelCompetencia ConfiguracaoNivelCompetenciaFaixa : configuracaoNivelCompetenciasFaixas)
			if(!datasHistoricoFaixaSalarial.contains(ConfiguracaoNivelCompetenciaFaixa.getConfiguracaoNivelCompetenciaFaixaSalarial().getData()))
				datasHistoricoFaixaSalarial.add(ConfiguracaoNivelCompetenciaFaixa.getConfiguracaoNivelCompetenciaFaixaSalarial().getData());
		
		for(Date dataHistFaixa : datasHistoricoFaixaSalarial)
		{
			int totalPontosFaixa = 0;
			
			Map<String, String> competenciaNiveis = new HashMap<String, String>();
			for (ConfiguracaoNivelCompetencia competencia : configuracaoNivelCompetenciasFaixas) 
				if(competencia.getConfiguracaoNivelCompetenciaFaixaSalarial().getData().equals(dataHistFaixa))
					competenciaNiveis.put(competencia.getCompetenciaDescricao(), competencia.getNivelCompetencia().getDescricao());
	
			Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
			Map<String, String> competenciaNiveisConfigurados = new HashMap<String, String>();
			for (Map.Entry<String, String> competenciaNivel : competenciaNiveis.entrySet()) 
			{
				for (NivelCompetencia nivel : niveis) 
				{
					boolean isConfiguracaoFaixa = competenciaNivel.getValue().equals(nivel.getDescricao());
					matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaNivel.getKey(), nivel.getOrdem() + " - " + nivel.getDescricao(), isConfiguracaoFaixa, false));
	
					if(isConfiguracaoFaixa)
					{
						competenciaNiveisConfigurados.put(competenciaNivel.getKey(), competenciaNivel.getValue());
						totalPontosFaixa += nivel.getOrdem();
					}
				}
				
				// Adiciona coluna para o gap
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaNivel.getKey(), "GAP", false, false, 0));
			}

			String data = DateUtil.formataDiaMesAno(dataHistFaixa);
			matrizCompetenciaNivelConfiguracaoMap.put(data, matrizModelo);
			totalPontosMap.put(data, totalPontosFaixa);
			competenciaNiveisConfiguradosMap.put(data, competenciaNiveisConfigurados);
		}

		Long configNCColaboradorId = 0L;
		ConfiguracaoNivelCompetenciaVO vo = null;
		
		String nome;
		String competencia;
		String nivel;
		Integer ordem;
		Integer ordemFaixa;
		String avaliadorNome = "";
		String data;
		Date dataHistoricoFaixaSalarial = null;
		String dataHistoricoFaixaSalarialString = "";
		
		for (ConfiguracaoNivelCompetencia configNivelCompetencia : configuracaoNivelCompetencias) 
		{
			nome = configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome();
			data = DateUtil.formataDiaMesAno(configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getData());
			competencia = configNivelCompetencia.getCompetenciaDescricao();
			nivel = configNivelCompetencia.getNivelCompetenciaColaborador().getDescricao();
			ordem = configNivelCompetencia.getNivelCompetenciaColaborador().getOrdem();
			
			for (Date dataHistFaixaSalarial : datasHistoricoFaixaSalarial)
				if (configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getData().equals(dataHistFaixaSalarial) || configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getData().after(dataHistFaixaSalarial))
					dataHistoricoFaixaSalarial = dataHistFaixaSalarial;

			ordemFaixa = null;
			for (ConfiguracaoNivelCompetencia competenciaFaixa : configuracaoNivelCompetenciasFaixas)
			{
				if(competenciaFaixa.getConfiguracaoNivelCompetenciaFaixaSalarial().getData().equals(dataHistoricoFaixaSalarial) && competenciaFaixa.getCompetenciaId().equals(configNivelCompetencia.getCompetenciaId()))
				{
					ordemFaixa = competenciaFaixa.getNivelCompetencia().getOrdem();
					break;
				}
			}
			
			if(ordemFaixa == null)
				continue;
			
			dataHistoricoFaixaSalarialString = DateUtil.formataDiaMesAno(dataHistoricoFaixaSalarial);

			if(configNivelCompetencia.isColaborador())
			{
				if(!configNCColaboradorId.equals(configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getId()))
				{
					Collection<MatrizCompetenciaNivelConfiguracao> matrizCompetenciaNivelConfiguracaos = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
					for (MatrizCompetenciaNivelConfiguracao matrizCompNivelConfig : matrizCompetenciaNivelConfiguracaoMap.get(dataHistoricoFaixaSalarialString))
						matrizCompetenciaNivelConfiguracaos.add(new MatrizCompetenciaNivelConfiguracao(matrizCompNivelConfig.getCompetencia(), matrizCompNivelConfig.getNivel(), matrizCompNivelConfig.getConfiguracaoFaixa(), matrizCompNivelConfig.getConfiguracao(), matrizCompNivelConfig.getGap()));
					
					configuracaoNivelCompetenciaColaboradorManager.verificaAvaliadorAnonimo(configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador());
					avaliadorNome = configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getAvaliador().getNome();
						
					vo = new ConfiguracaoNivelCompetenciaVO(nome, avaliadorNome, data, matrizCompetenciaNivelConfiguracaos);
					vo.setTotalPontosFaixa(totalPontosMap.get(dataHistoricoFaixaSalarialString));
					vos.add(vo);
				}
				
				vo.somaTotalPontos(ordem);
				boolean isConfiguracaoFaixa = (competenciaNiveisConfiguradosMap.get(dataHistoricoFaixaSalarialString)).containsKey(competencia) && (competenciaNiveisConfiguradosMap.get(dataHistoricoFaixaSalarialString)).get(competencia).equals(nivel);

				for (MatrizCompetenciaNivelConfiguracao matrizCompNivelConfig : vo.getMatrizes()) 
				{
					if(matrizCompNivelConfig.getCompetencia().equals(competencia))
					{
						if((ordem + " - " + nivel).equals(matrizCompNivelConfig.getNivel())){
							matrizCompNivelConfig.setConfiguracaoFaixa(isConfiguracaoFaixa);
							matrizCompNivelConfig.setConfiguracao(true);
						}else if("GAP".equals(matrizCompNivelConfig.getNivel()))
							matrizCompNivelConfig.setGap(ordem - ordemFaixa);
					}
				}
				
				if((ordem - ordemFaixa) > 0)
					vo.setTotalGapExcedenteAoCargo(vo.getTotalGapExcedenteAoCargo() + ordem - ordemFaixa); 
				
				configNCColaboradorId = configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getId();
			}
		}
		
		return vos;
	}
	
	public Collection<MatrizCompetenciaNivelConfiguracao> montaConfiguracaoNivelCompetenciaByFaixa(Long empresaId, Long faixaSalarialId, Date data) 
	{
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaByFaixaSalarial(faixaSalarialId, data);
		Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId);

		Map<String, String> competenciaNiveis = new HashMap<String, String>();
		for (ConfiguracaoNivelCompetencia competencia : configuracaoNivelCompetencias) 
			competenciaNiveis.put(competencia.getCompetenciaDescricao(), competencia.getNivelCompetencia().getDescricao());

		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
		Map<String, String> competenciaNiveisConfigurados = new HashMap<String, String>();
		for (Map.Entry<String, String> competenciaNivel : competenciaNiveis.entrySet()) 
		{
			for (NivelCompetencia nivel : niveis) 
			{
				boolean isConfiguracaoFaixa = competenciaNivel.getValue().equals(nivel.getDescricao());
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaNivel.getKey(), nivel.getOrdem() + " - " + nivel.getDescricao(), isConfiguracaoFaixa, false));

				if(isConfiguracaoFaixa)
					competenciaNiveisConfigurados.put(competenciaNivel.getKey(), competenciaNivel.getValue());
			}
		}

		return matrizModelo;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaMatrizCompetenciaCandidato(Long empresaId, Long faixaSalarialId, Long solicitacaoId) 
	{
		Collection<ConfiguracaoNivelCompetenciaVO> tabelasCandidatos = new ArrayList<ConfiguracaoNivelCompetenciaVO>();

		Collection<Long> candidatosDaSolicitacaoIds = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacaoId);
		if(candidatosDaSolicitacaoIds.isEmpty())
			return tabelasCandidatos;
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaCandidato(faixaSalarialId, candidatosDaSolicitacaoIds);//Ordem da consulta é importantissima, primeiro as competencias da faixa e depois do candidato
		Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId);
		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
		
		Long idCandidatoAnterior = null;
		Long idCandidatoAtual;
		ConfiguracaoNivelCompetenciaVO tabelaCandidato = null;
		int totalPontosFaixa = 0;
		
		for (ConfiguracaoNivelCompetencia configuracaoCompetencia : configuracaoNivelCompetencias) 
		{
			if(configuracaoCompetencia.getCandidato() == null)
			{
				//monta competencia X nivel
				for (NivelCompetencia nivel : niveis) 
				{
					boolean nivelExigidoPelaFaixa = nivel.getDescricao().equals(configuracaoCompetencia.getNivelCompetencia().getDescricao());
					matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoCompetencia.getCompetenciaDescricao(), nivel.getOrdem() + " - " + nivel.getDescricao(), nivelExigidoPelaFaixa, false));
				}

				//monta coluna GAP
				totalPontosFaixa += configuracaoCompetencia.getNivelCompetencia().getOrdem();
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoCompetencia.getNivelCompetencia().getOrdem(), configuracaoCompetencia.getCompetenciaDescricao(), "GAP", false, false));
			}
			else
			{
				//configura competencia X nivel do candidato
				idCandidatoAtual = configuracaoCompetencia.getCandidato().getId();
				if(!idCandidatoAtual.equals(idCandidatoAnterior))
				{
					tabelaCandidato = new ConfiguracaoNivelCompetenciaVO(configuracaoCompetencia.getCandidato().getNome(), clonaMatriz(matrizModelo), totalPontosFaixa);
					tabelasCandidatos.add(tabelaCandidato);
				}
				
				tabelaCandidato.configuraNivelCandidato(configuracaoCompetencia.getCompetenciaDescricao(), configuracaoCompetencia.getNivelCompetencia());
				idCandidatoAnterior = idCandidatoAtual;
			}
		}
		
		return tabelasCandidatos;
	}

	private Collection<MatrizCompetenciaNivelConfiguracao> clonaMatriz(Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo) 
	{
		Collection<MatrizCompetenciaNivelConfiguracao> matriz = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
		for (MatrizCompetenciaNivelConfiguracao modelo : matrizModelo) 
		{
			matriz.add((MatrizCompetenciaNivelConfiguracao) modelo.clone());
		}
		
		return matriz;
	}

	public void removeByFaixas(Long[] faixaSalarialIds) 
	{
		getDao().removeByFaixas(faixaSalarialIds);
	}

	public void removeColaborador(Colaborador colaborador) {
		getDao().removeColaborador(colaborador);
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void removeConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelColaboradorId) {
		getDao().removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelColaboradorId);
		configuracaoNivelCompetenciaColaboradorManager.remove(configuracaoNivelColaboradorId);
	}

	public void removeConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelFaixaSalarialId)
	{
		getDao().removeByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelFaixaSalarialId);
		configuracaoNivelCompetenciaFaixaSalarialManager.remove(configuracaoNivelFaixaSalarialId);
	}

	public void removeByCandidato(Long candidatoId) {
		getDao().removeByCandidato(candidatoId);		
	}

	public Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId) {
		return getDao().findCompetenciasIdsConfiguradasByFaixaSolicitacao(faixaSalarialId);
	}

	public Integer somaConfiguracoesByFaixa(Long faixaSalarialId) {
		return getDao().somaConfiguracoesByFaixa(faixaSalarialId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId) 
	{
		return getDao().findByColaborador(colaboradorId, avaliadorId, colaboradorQuestionarioId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(	Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor) 
	{
		return getDao().findColaboradoresCompetenciasAbaixoDoNivel(	empresaId, estabelecimentoIds, areaIds, colaboradoresAvaliados, agruparPor);
	}
	
	public Collection<Competencia> findCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, Date dataIni, Date dataFim) 
	{
		return getDao().findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, dataIni, dataFim);
	}
	
	public void removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixaSalarial(Long[] faixaIds)
	{
		getDao().removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixasSalariais(faixaIds);
	}
	
	public void removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixaSalarial(Long[] faixaIds)
	{
		getDao().removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixasSalariais(faixaIds);
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}

}