package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
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

	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId) {
		return getDao().findByFaixa(faixaSalarialId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId) {
		return getDao().findByCandidato(candidatoId);
	}

	public void saveCompetencias(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId) {
		if (candidatoId == null)
			getDao().deleteConfiguracaoByFaixa(faixaSalarialId);
		else
			getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId);

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) {
			if (configuracaoNivelCompetencia.getCompetenciaId() != null) {
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				if (candidatoId != null)
					configuracaoNivelCompetencia.setCandidatoIdProjection(candidatoId);

				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	public void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) {
		ajustaConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		if (configuracaoNivelCompetenciaColaborador.getId() != null) 
		{
			configuracaoNivelCompetenciaColaboradorManager.update(configuracaoNivelCompetenciaColaborador);
			getDao().deleteConfiguracaoNivelCompetenciaByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		} 
		else
			configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.save(configuracaoNivelCompetenciaColaborador);

		if(configuracaoNiveisCompetencias != null)
		{
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
			getDao().deleteConfiguracaoNivelCompetenciaByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getId());
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

	public Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds, Long faixaSalarialId) {
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(null, null, competenciasIds, faixaSalarialId, false);
		Collection<ConfiguracaoNivelCompetencia> configuracaoAbaixos = new ArrayList<ConfiguracaoNivelCompetencia>();

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) {
			if (configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() != null
					&& configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() < configuracaoNivelCompetencia.getNivelCompetencia().getOrdem())
				configuracaoAbaixos.add(configuracaoNivelCompetencia);
		}

		return configuracaoAbaixos;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Date dataIni, Date dataFim, Long empresaId, Long faixaSalarialId, Long[] competenciasIds) 
	{
		Collection<ConfiguracaoNivelCompetenciaVO> vos = new ArrayList<ConfiguracaoNivelCompetenciaVO>();

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(dataIni, dataFim, competenciasIds, faixaSalarialId, true);
		Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId);

		int totalPontosFaixa = 0;
		
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
				{
					competenciaNiveisConfigurados.put(competenciaNivel.getKey(), competenciaNivel.getValue());
					totalPontosFaixa += nivel.getOrdem();
				}
			}
			
			// Adiciona coluna para o gap
			matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaNivel.getKey(), "GAP", false, false, 0));
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
		
		for (ConfiguracaoNivelCompetencia configNivelCompetencia : configuracaoNivelCompetencias) 
		{
			nome = configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome();
			data = DateUtil.formataDiaMesAno(configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getData());
			competencia = configNivelCompetencia.getCompetenciaDescricao();
			nivel = configNivelCompetencia.getNivelCompetenciaColaborador().getDescricao();
			ordem = configNivelCompetencia.getNivelCompetenciaColaborador().getOrdem();
			ordemFaixa = configNivelCompetencia.getNivelCompetencia().getOrdem();
			
			if(configNivelCompetencia.isColaborador())
			{
				if(!configNCColaboradorId.equals(configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getId()))
				{
					Collection<MatrizCompetenciaNivelConfiguracao> matrizCompetenciaNivelConfiguracaos = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
					for (MatrizCompetenciaNivelConfiguracao matrizCompNivelConfig : matrizModelo)
						matrizCompetenciaNivelConfiguracaos.add(new MatrizCompetenciaNivelConfiguracao(matrizCompNivelConfig.getCompetencia(), matrizCompNivelConfig.getNivel(), matrizCompNivelConfig.getConfiguracaoFaixa(), matrizCompNivelConfig.getConfiguracao(), matrizCompNivelConfig.getGap()));
					
					configuracaoNivelCompetenciaColaboradorManager.verificaAvaliadorAnonimo(configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador());
					avaliadorNome = configNivelCompetencia.getConfiguracaoNivelCompetenciaColaborador().getAvaliador().getNome();
						
					vo = new ConfiguracaoNivelCompetenciaVO(nome, avaliadorNome, data, matrizCompetenciaNivelConfiguracaos);
					vo.setTotalPontosFaixa(totalPontosFaixa);
					vos.add(vo);
				}
				
				vo.somaTotalPontos(ordem);
				boolean isConfiguracaoFaixa = competenciaNiveisConfigurados.containsKey(competencia) && competenciaNiveisConfigurados.get(competencia).equals(nivel);

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
	
	public Collection<MatrizCompetenciaNivelConfiguracao> montaConfiguracaoNivelCompetenciaByFaixa(Long empresaId, Long faixaSalarialId) 
	{
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaByFaixaSalarial(faixaSalarialId, null);
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
				{
					competenciaNiveisConfigurados.put(competenciaNivel.getKey(), competenciaNivel.getValue());
				}
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
		getDao().removeByConfiguracaoNivelColaborador(configuracaoNivelColaboradorId);
		configuracaoNivelCompetenciaColaboradorManager.remove(configuracaoNivelColaboradorId);
	}

	public void removeConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelFaixaSalarialId)
	{
		getDao().removeByConfiguracaoNivelFaixaSalarial(configuracaoNivelFaixaSalarialId);
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

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}
}