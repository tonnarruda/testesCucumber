package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.RelatorioAnaliseDesempenhoColaborador;
import com.fortes.rh.model.avaliacao.ResultadoCompetencia;
import com.fortes.rh.model.avaliacao.ResultadoCompetenciaColaborador;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ConfiguracaoNivelCompetenciaManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetencia, ConfiguracaoNivelCompetenciaDao> implements ConfiguracaoNivelCompetenciaManager 
{
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	private ConfiguracaoNivelCompetenciaCriterioManager configuracaoNivelCompetenciaCriterioManager;
	private ConfigHistoricoNivelManager configHistoricoNivelManager;
	private ConfiguracaoNivelCompetenciaCandidatoManager configuracaoNivelCompetenciaCandidatoManager;
	private static Long AUTOAVALIACAO = 0L;
	private static Long OUTROSAVALIADORES = -1L;

	@TesteAutomatico
	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data) {
		return getDao().findByFaixa(faixaSalarialId, data);
	}

	@TesteAutomatico
	public Collection<ConfiguracaoNivelCompetencia> findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) {
		return getDao().findByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}
	
	public void criaCNCColaboradorByCNCCnadidato(Colaborador colaborador, Long idCandidato, Solicitacao solicitacao, HistoricoColaborador historico) 
	{
		ConfiguracaoNivelCompetenciaCandidato cncCandidato = configuracaoNivelCompetenciaCandidatoManager.findByCandidatoAndSolicitacao(idCandidato, solicitacao.getId());
		if(cncCandidato != null && cncCandidato.getId() != null){
			Collection<ConfiguracaoNivelCompetencia> cNCCandidatoVincuadasComCNCFaixaSalarial = new ArrayList<ConfiguracaoNivelCompetencia>();
			Collection<ConfiguracaoNivelCompetencia> competenciasDoCandidato = findByCandidatoAndSolicitacao(idCandidato, solicitacao.getId());
			Collection<ConfiguracaoNivelCompetencia> cncsFaixaSalarial = getDao().findByConfiguracaoNivelCompetenciaFaixaSalarial(cncCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId());
		
			for (ConfiguracaoNivelCompetencia competeciaDoCandidato : competenciasDoCandidato) 
			{
				for (ConfiguracaoNivelCompetencia cncFaixaSalarial : cncsFaixaSalarial) 
				{
					if (competeciaDoCandidato.getCompetenciaId().equals(cncFaixaSalarial.getCompetenciaId()) && competeciaDoCandidato.getTipoCompetencia().equals(cncFaixaSalarial.getTipoCompetencia()))
					{
						competeciaDoCandidato.setId(null);
						competeciaDoCandidato.setFaixaSalarial(null);
						competeciaDoCandidato.setConfiguracaoNivelCompetenciaFaixaSalarial(null);
						cNCCandidatoVincuadasComCNCFaixaSalarial.add(competeciaDoCandidato);
					}
				}
			}
			
			if (cNCCandidatoVincuadasComCNCFaixaSalarial != null && cNCCandidatoVincuadasComCNCFaixaSalarial.size() > 0)
			{
				ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
				configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
				configuracaoNivelCompetenciaColaborador.setFaixaSalarial(historico.getFaixaSalarial());
				configuracaoNivelCompetenciaColaborador.setData(historico.getData());
				configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarialId(cncCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId());
	
				saveCompetenciasColaborador(cNCCandidatoVincuadasComCNCFaixaSalarial, configuracaoNivelCompetenciaColaborador);
			}
		}
	}

	public void saveCompetenciasCandidato(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, Long faixaSalarialId, Long candidatoId, Solicitacao solicitacao) 
	{
		getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, solicitacao.getId());
		configuracaoNivelCompetenciaCandidatoManager.removeByCandidatoAndSolicitacao(candidatoId, solicitacao.getId());

		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = null;
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) {
			if (configuracaoNivelCompetencia.getCompetenciaId() != null) {
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				if(configuracaoNivelCompetenciaCandidato == null)
					configuracaoNivelCompetenciaCandidato = criaConfiguracaoNivelCompetenciaCandidato(candidatoId, solicitacao, faixaSalarialId);
				configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato);
				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	private ConfiguracaoNivelCompetenciaCandidato criaConfiguracaoNivelCompetenciaCandidato(Long candidatoId, Solicitacao solicitacao, Long faixaSalarialId) {
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = new ConfiguracaoNivelCompetenciaCandidato();
		configuracaoNivelCompetenciaCandidato.setData(new Date());
		configuracaoNivelCompetenciaCandidato.setCandidatoId(candidatoId);
		configuracaoNivelCompetenciaCandidato.setSolicitacaoId(solicitacao.getId());
		configuracaoNivelCompetenciaCandidato.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarialId, solicitacao.getData()));
		configuracaoNivelCompetenciaCandidatoManager.save(configuracaoNivelCompetenciaCandidato);
		return configuracaoNivelCompetenciaCandidato;
	}

	public void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		ajustaConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		
		if (configuracaoNivelCompetenciaColaborador.getId() == null) { 
			configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.save(configuracaoNivelCompetenciaColaborador);
		} else {
			configuracaoNivelCompetenciaColaboradorManager.update(configuracaoNivelCompetenciaColaborador);
			configuracaoNivelCompetenciaCriterioManager.removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
			getDao().deleteByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		} 

		if(configuracaoNiveisCompetencias != null) {
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias){
				if (configuracaoNivelCompetencia.getCompetenciaId() != null){
					configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
					setConfiguracaoNivelCompetenciaCriterios(configuracaoNivelCompetencia);
					getDao().save(configuracaoNivelCompetencia);
				}
			}
		}
	}

	private void setConfiguracaoNivelCompetenciaCriterios(ConfiguracaoNivelCompetencia configuracaoNivelCompetencia) 
	{
		if (configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios() != null && configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios().size() > 0) {
			Collection<ConfiguracaoNivelCompetenciaCriterio> criterios = new ArrayList<ConfiguracaoNivelCompetenciaCriterio>();
			criterios.addAll(configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios());
			
			configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios().clear();
			for (ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio : criterios) {
				if (configuracaoNivelCompetenciaCriterio.getCriterioId() != null ) {
					configuracaoNivelCompetenciaCriterio.setConfiguracaoNivelCompetencia(configuracaoNivelCompetencia);
					configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaCriterios().add(configuracaoNivelCompetenciaCriterio);
				}
			}
		}
	}
	
	public void saveCompetenciasColaboradorAndRecalculaPerformance(Long empresaId, Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador)
	{
		saveCompetenciasColaborador(configuracaoNiveisCompetencias, configuracaoNivelCompetenciaColaborador);
		
		if (configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario() != null && configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getId() != null)
		{
			ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
			ColaboradorQuestionario colaboradorQuestionario = colaboradorQuestionarioManager.findById(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getId());

			if (colaboradorQuestionario.getAvaliacaoDesempenho() != null && colaboradorQuestionario.getAvaliacaoDesempenho().getId() != null)
			{
				ColaboradorRespostaManager colaboradorRespostaManager  = (ColaboradorRespostaManager) SpringUtil.getBean("colaboradorRespostaManager");
				colaboradorRespostaManager.calculaPerformance(colaboradorQuestionario, empresaId, configuracaoNiveisCompetencias);
			
				colaboradorQuestionarioManager.save(colaboradorQuestionario);
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

	public void saveCompetenciasFaixaSalarial(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) throws Exception
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
	
	public Collection<Solicitacao> getCompetenciasCandidato(Long empresaId, Long candidatoId) 
	{
		Collection<Solicitacao> solicitacoesComConfigNiveisCompetenciaCandidato = new ArrayList<Solicitacao>();
		Collection<ConfiguracaoNivelCompetencia> cncsCandidato = getDao().findByCandidato(candidatoId);

		if (!cncsCandidato.isEmpty()) 
		{
			for (ConfiguracaoNivelCompetencia cncCandidato : cncsCandidato) 
			{
				Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaEmpresa = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);
				for (ConfiguracaoNivelCompetencia nivelCompetenciaEmpresa : niveisCompetenciaEmpresa) 
				{
					if (cncCandidato.getCompetenciaId().equals(nivelCompetenciaEmpresa.getCompetenciaId())
							&& cncCandidato.getTipoCompetencia().equals(nivelCompetenciaEmpresa.getTipoCompetencia())) {
						cncCandidato.setCompetenciaDescricao(nivelCompetenciaEmpresa.getCompetenciaDescricao());
						break;
					}
				}
			}

			Solicitacao solicitacao = new Solicitacao();
			for (ConfiguracaoNivelCompetencia cncCandidato : cncsCandidato) 
			{
				if(!cncCandidato.getConfiguracaoNivelCompetenciaCandidato().getSolicitacao().getId().equals(solicitacao.getId())){

					if(solicitacao.getId() != null)
						solicitacoesComConfigNiveisCompetenciaCandidato.add(solicitacao);
					
					Long nivelCompetenciahistoricoId = cncCandidato.getConfiguracaoNivelCompetenciaCandidato().getConfiguracaoNivelCompetenciaFaixaSalarial().getNivelCompetenciaHistorico().getId(); 
					solicitacao = cncCandidato.getConfiguracaoNivelCompetenciaCandidato().getSolicitacao();
					solicitacao.setNivelCompetencias(nivelCompetenciaManager.findAllSelect(empresaId, nivelCompetenciahistoricoId , null));
					solicitacao.setFaixaSalarial(cncCandidato.getFaixaSalarial());
					solicitacao.setConfiguracaoNivelCompetencias(new ArrayList<ConfiguracaoNivelCompetencia>());
				}

				solicitacao.getConfiguracaoNivelCompetencias().add(cncCandidato);	
			}
			solicitacoesComConfigNiveisCompetenciaCandidato.add(solicitacao);
		
			for (Solicitacao solicitacaoConf : solicitacoesComConfigNiveisCompetenciaCandidato) 
			{
				ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = solicitacaoConf.getConfiguracaoNivelCompetencias().iterator().next().getConfiguracaoNivelCompetenciaCandidato().getConfiguracaoNivelCompetenciaFaixaSalarial();
				Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetenciaDaFaixa = getDao().findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getId());
			
				for(ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaSolicitacao : solicitacaoConf.getConfiguracaoNivelCompetencias())
				{
					for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaDaFaixa : configuracoesNivelCompetenciaDaFaixa) 
					{
						if(configuracaoNivelCompetenciaDaFaixa.getCompetenciaDescricao() != null && configuracaoNivelCompetenciaDaFaixa.getCompetenciaDescricao().equals(configuracaoNivelCompetenciaSolicitacao.getCompetenciaDescricao()))
							configuracaoNivelCompetenciaSolicitacao.setNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaDaFaixa.getNivelCompetencia());
					}
				}
			}
		}

		return solicitacoesComConfigNiveisCompetenciaCandidato;
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId, Long configuracaoNivelCompetenciaFaixaSalarialId) {
		Collection<ConfiguracaoNivelCompetencia> configuracoesNiveisCompetencia = getDao().findByConfiguracaoNivelCompetenciaColaborador(null, configuracaoNivelCompetenciaColaboradorId, configuracaoNivelCompetenciaFaixaSalarialId);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracoesNiveisCompetencia) {
			configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaCriterios(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetencia.getId(), configuracaoNivelCompetenciaFaixaSalarialId));
		}
		
		return configuracoesNiveisCompetencia;
	}
	
	@TesteAutomatico
	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId)
	{
		return getDao().findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarialId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data, Long configuracaoNivelCompetenciaFaixaSalarialId, Long avaliadorId, Long avaliacaoDesempenhoId) {
		
		Collection<ConfiguracaoNivelCompetencia> configuracoesNiveisCompetencia = new ArrayList<ConfiguracaoNivelCompetencia>();
		ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager = (ConfiguracaoCompetenciaAvaliacaoDesempenhoManager) SpringUtil.getBeanOld("configuracaoCompetenciaAvaliacaoDesempenhoManager");
		
		if(configuracaoCompetenciaAvaliacaoDesempenhoManager.existe(configuracaoNivelCompetenciaFaixaSalarialId,avaliacaoDesempenhoId))
			configuracoesNiveisCompetencia = getDao().findCompetenciaByFaixaSalarial(faixaId, data, configuracaoNivelCompetenciaFaixaSalarialId, avaliadorId, avaliacaoDesempenhoId);
		else
			configuracoesNiveisCompetencia = getDao().findCompetenciaByFaixaSalarial(faixaId, data, configuracaoNivelCompetenciaFaixaSalarialId, null, null);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracoesNiveisCompetencia) {
			configuracaoNivelCompetencia.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(configuracaoNivelCompetencia.getCompetenciaId(), configuracaoNivelCompetenciaFaixaSalarialId, configuracaoNivelCompetencia.getTipoCompetencia().charValue()));
		}
		
		return configuracoesNiveisCompetencia;
	}

	public Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds, Long faixaSalarialId, Date data) 
	{
		Collection<ConfiguracaoNivelCompetencia> configuracaoAbaixos = new ArrayList<ConfiguracaoNivelCompetencia>();
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findCompetenciaColaborador(data, null, competenciasIds, faixaSalarialId, false);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasFaixas = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		Long configuracaoNivelCompetenciaFaixaSalarialId = null;
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) 
		{	
			if(!configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaFaixaSalarial().getId().equals(configuracaoNivelCompetenciaFaixaSalarialId)){
				configuracaoNivelCompetenciaFaixaSalarialId = configuracaoNivelCompetencia.getConfiguracaoNivelCompetenciaFaixaSalarial().getId();
				configuracaoNivelCompetenciasFaixas = getDao().findCompetenciaByFaixaSalarial(null, null, configuracaoNivelCompetenciaFaixaSalarialId, null, null);;
			}
			
			for (ConfiguracaoNivelCompetencia competenciaFaixa : configuracaoNivelCompetenciasFaixas)
			{
				if(competenciaFaixa.getCompetenciaId().equals(configuracaoNivelCompetencia.getCompetenciaId()) && competenciaFaixa.getTipoCompetencia().equals(configuracaoNivelCompetencia.getTipoCompetencia()))
				{
					configuracaoNivelCompetencia.setNivelCompetenciaDescricaoProjection(competenciaFaixa.getNivelCompetencia().getDescricao());
					configuracaoNivelCompetencia.setNivelCompetenciaOrdemProjection(competenciaFaixa.getNivelCompetencia().getOrdem());
					break;
				}
			}
			
			if(configuracaoNivelCompetencia.getNivelCompetencia() == null  || configuracaoNivelCompetencia.getNivelCompetencia().getOrdem() == null )
				continue;

			if (configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() != null && configuracaoNivelCompetencia.getNivelCompetenciaColaborador().getOrdem() < configuracaoNivelCompetencia.getNivelCompetencia().getOrdem())
				configuracaoAbaixos.add(configuracaoNivelCompetencia);
		}

		return configuracaoAbaixos;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Date dataIni, Date dataFim, Long empresaId, Long faixaSalarialId, Long[] competenciasIds) {
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracoesNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findByDataAndFaixaSalarial(dataIni, dataFim, faixaSalarialId);
		
		Collection<ConfiguracaoNivelCompetenciaVO> vos = new ArrayList<ConfiguracaoNivelCompetenciaVO>();
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasColaborador;
		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo;
		Collection<NivelCompetencia> niveis; 

		int totalGapExcedenteAoCargo;
		int totalPontosColaborador;
		int totalPontosFaixa;
		int valorGap = 0;
		
		for (ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador : configuracoesNivelCompetenciaColaborador) {
			Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasFaixas = getDao().findCompetenciasConfiguracaoNivelCompetenciaFaixaSalarial(competenciasIds, configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getId());
			matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
			totalPontosFaixa = 0;
			totalPontosColaborador = 0;
			totalGapExcedenteAoCargo = 0;
			niveis = nivelCompetenciaManager.findAllSelect(empresaId, configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getNivelCompetenciaHistorico().getId(), configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getData());
			configuracaoNivelCompetenciasColaborador = getDao().findByConfiguracaoNivelCompetenciaColaborador(competenciasIds, configuracaoNivelCompetenciaColaborador.getId(), configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getId()  );

			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa : configuracaoNivelCompetenciasFaixas) {
				ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaExigidaPelaFaixa = null;
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetenciasColaborador) 
					if(configuracaoNivelCompetencia.getCompetenciaDescricao().equals(configuracaoNivelCompetenciaFaixa.getCompetenciaDescricao())){
						configuracaoNivelCompetenciaExigidaPelaFaixa = configuracaoNivelCompetencia;
						valorGap = configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getOrdem() - configuracaoNivelCompetenciaFaixa.getNivelCompetencia().getOrdem();
						totalPontosColaborador += configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getOrdem();
						if(valorGap > 0)
							totalGapExcedenteAoCargo += valorGap ;
						break;
					}
				
				for (NivelCompetencia nivel : niveis){
					boolean isConfiguracaoColaborador = configuracaoNivelCompetenciaExigidaPelaFaixa != null ? nivel.getDescricao().equals(configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getDescricao()) : false;
					boolean isConfiguracaoFaixa = configuracaoNivelCompetenciaFaixa.getNivelCompetencia().getDescricao().equals(nivel.getDescricao());
					matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoNivelCompetenciaFaixa.getCompetenciaDescricao(), nivel.getOrdem() + " - " + nivel.getDescricao(),isConfiguracaoFaixa, isConfiguracaoColaborador));
				}
				totalPontosFaixa += configuracaoNivelCompetenciaFaixa.getNivelCompetencia().getOrdem();
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoNivelCompetenciaFaixa.getCompetenciaDescricao(), "GAP", false, false, valorGap));
			}
			configuracaoNivelCompetenciaColaboradorManager.verificaAvaliadorAnonimo(configuracaoNivelCompetenciaColaborador);
			ConfiguracaoNivelCompetenciaVO vo = new ConfiguracaoNivelCompetenciaVO(configuracaoNivelCompetenciaColaborador.getColaborador().getNome(), configuracaoNivelCompetenciaColaborador.getAvaliador().getNome(), DateUtil.formataDiaMesAno(configuracaoNivelCompetenciaColaborador.getData()), matrizModelo); 
			vo.setTotalPontosFaixa(totalPontosFaixa);
			vo.somaTotalPontos(totalPontosColaborador);
			vo.setTotalGapExcedenteAoCargo(totalGapExcedenteAoCargo);
			vos.add(vo);
		}
		return vos;
	}
	
	public Collection<MatrizCompetenciaNivelConfiguracao> montaConfiguracaoNivelCompetenciaByFaixa(Long empresaId, Long faixaSalarialId, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) 
	{
		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
		if(configuracaoNivelCompetenciaFaixaSalarial != null){
			Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasExigidasPelaFaixa = getDao().findCompetenciaByFaixaSalarial(faixaSalarialId, configuracaoNivelCompetenciaFaixaSalarial.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null);
	
			Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId, configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
	
			for (ConfiguracaoNivelCompetencia competenciaExigidaPelaFaixa : configuracaoNivelCompetenciasExigidasPelaFaixa) 
			{
				competenciaExigidaPelaFaixa.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(competenciaExigidaPelaFaixa.getCompetenciaId(), configuracaoNivelCompetenciaFaixaSalarial.getId(), competenciaExigidaPelaFaixa.getTipoCompetencia().charValue()));
				for (NivelCompetencia nivel : niveis) 
				{
					boolean isConfiguracaoFaixa = competenciaExigidaPelaFaixa.getNivelCompetencia().getDescricao().equals(nivel.getDescricao());
					boolean hasCriterios = (competenciaExigidaPelaFaixa.getCriteriosAvaliacaoCompetencia() != null && competenciaExigidaPelaFaixa.getCriteriosAvaliacaoCompetencia().size() > 0);
					matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaExigidaPelaFaixa.getCompetenciaDescricao(), nivel.getOrdem() + " - " + nivel.getDescricao(), isConfiguracaoFaixa, false, hasCriterios, false));
				}
				
				if ( competenciaExigidaPelaFaixa.getCriteriosAvaliacaoCompetencia() != null ) {
					for( CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia : competenciaExigidaPelaFaixa.getCriteriosAvaliacaoCompetencia() ) {
						for (NivelCompetencia nivel : niveis)
							matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(criterioAvaliacaoCompetencia.getDescricao(), nivel.getOrdem() + " - " + nivel.getDescricao(), false, false, false, true));
					}
				}
			}
		}
		return matrizModelo;
	}
	
	public Collection<MatrizCompetenciaNivelConfiguracao> montaMatrizCNCByQuestionario(ColaboradorQuestionario colaboradorQuestionario, Long empresaId) 
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findByData(colaboradorQuestionario.getRespondidaEm(), colaboradorQuestionario.getColaborador().getId(), colaboradorQuestionario.getAvaliador().getId(), colaboradorQuestionario.getId());
		if(configuracaoNivelCompetenciaColaborador != null){
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial =  configuracaoNivelCompetenciaFaixaSalarialManager.findByProjection(configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getId());
			
			Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasExigidasPelaFaixa = getDao().findCompetenciaByFaixaSalarial(configuracaoNivelCompetenciaColaborador.getFaixaSalarial().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null);
				
			Collection<ConfiguracaoNivelCompetencia> competenciasDoColaborador = getDao().findByConfiguracaoNivelCompetenciaColaborador(null, configuracaoNivelCompetenciaColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarial.getId());
			
			Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId, configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
			
			Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
			for (ConfiguracaoNivelCompetencia competenciaExigidaPelaFaixa : configuracaoNivelCompetenciasExigidasPelaFaixa) 
			{
				ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = montaCompetencia( competenciasDoColaborador, niveis, matrizModelo, competenciaExigidaPelaFaixa, configuracaoNivelCompetenciaFaixaSalarial.getId());
				
				montaCriteriosDaCompetencia(niveis, matrizModelo, competenciaExigidaPelaFaixa, configuracaoNivelCompetencia);
				
				competenciaExigidaPelaFaixa.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(competenciaExigidaPelaFaixa.getCompetenciaId(), configuracaoNivelCompetenciaFaixaSalarial.getId(), competenciaExigidaPelaFaixa.getTipoCompetencia().charValue()));
			}
			return matrizModelo;
		}
		else return null;
	}

	private ConfiguracaoNivelCompetencia montaCompetencia( Collection<ConfiguracaoNivelCompetencia> competenciasDoColaborador, Collection<NivelCompetencia> niveis, Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo, ConfiguracaoNivelCompetencia competenciaExigidaPelaFaixa, Long configuracaoNivelCompetenciaFaixaSalarialId) {
		boolean isConfiguracaoColaborador;
		boolean isConfiguracaoFaixa;

		competenciaExigidaPelaFaixa.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(competenciaExigidaPelaFaixa.getCompetenciaId(), configuracaoNivelCompetenciaFaixaSalarialId, competenciaExigidaPelaFaixa.getTipoCompetencia().charValue()));
		
		ConfiguracaoNivelCompetencia competenciaDoColaboradorIgualExigidaPelaFaixa = null;
		
		for (ConfiguracaoNivelCompetencia competenciaDoColaborador : competenciasDoColaborador) 
			if(competenciaExigidaPelaFaixa.getCompetenciaDescricao().equals(competenciaDoColaborador.getCompetenciaDescricao())){
				competenciaDoColaborador.setConfiguracaoNivelCompetenciaCriterios(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(competenciaDoColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarialId));
				competenciaDoColaboradorIgualExigidaPelaFaixa = competenciaDoColaborador;
				break;
			}

		for (NivelCompetencia nivel : niveis){						
			isConfiguracaoColaborador = competenciaDoColaboradorIgualExigidaPelaFaixa != null && competenciaDoColaboradorIgualExigidaPelaFaixa.getNivelCompetencia().getDescricao().equals(nivel.getDescricao());
			isConfiguracaoFaixa = competenciaExigidaPelaFaixa.getNivelCompetencia().getDescricao().equals(nivel.getDescricao());
			matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(competenciaExigidaPelaFaixa.getCompetenciaDescricao(), nivel.getOrdem() + " - " + nivel.getDescricao(), isConfiguracaoFaixa, isConfiguracaoColaborador));
		}
		
		return competenciaDoColaboradorIgualExigidaPelaFaixa;
	}

	private void montaCriteriosDaCompetencia( Collection<NivelCompetencia> niveis, Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo, ConfiguracaoNivelCompetencia competenciaExigidaPelaFaixa, ConfiguracaoNivelCompetencia competenciaDoColaboradorIgualExigidaPelaFaixa) {
		boolean isConfiguracaoColaborador;
		
		for( CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia : competenciaExigidaPelaFaixa.getCriteriosAvaliacaoCompetencia() ) {
			ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterioAtual = null;
			if(competenciaDoColaboradorIgualExigidaPelaFaixa != null && competenciaDoColaboradorIgualExigidaPelaFaixa.getConfiguracaoNivelCompetenciaCriterios() != null){
				for (ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio : competenciaDoColaboradorIgualExigidaPelaFaixa.getConfiguracaoNivelCompetenciaCriterios()) {
					if ( configuracaoNivelCompetenciaCriterio.getCriterioDescricao().equals(criterioAvaliacaoCompetencia.getDescricao())){
						competenciaDoColaboradorIgualExigidaPelaFaixa.getConfiguracaoNivelCompetenciaCriterios().remove(configuracaoNivelCompetenciaCriterio);
						configuracaoNivelCompetenciaCriterioAtual = configuracaoNivelCompetenciaCriterio;
						break;
					}
				}
			}
			for (NivelCompetencia nivel : niveis) {
				isConfiguracaoColaborador = configuracaoNivelCompetenciaCriterioAtual!= null && configuracaoNivelCompetenciaCriterioAtual.getNivelCompetencia().getDescricao().equals(nivel.getDescricao());
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(criterioAvaliacaoCompetencia.getDescricao(), nivel.getOrdem() + " - " + nivel.getDescricao(), false, isConfiguracaoColaborador, false, true));
			}
		}
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> montaMatrizCompetenciaCandidato(Long empresaId, Long faixaSalarialId, Solicitacao solicitacao) 
	{
		Collection<ConfiguracaoNivelCompetenciaVO> tabelasCandidatos = new ArrayList<ConfiguracaoNivelCompetenciaVO>();

		Collection<Long> candidatosDaSolicitacaoIds = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacao.getId());
		if(candidatosDaSolicitacaoIds.isEmpty())
			return tabelasCandidatos;

		Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetenciaRespostasCandidato = new ArrayList<ConfiguracaoNivelCompetencia>();
		ConfiguracaoNivelCompetencia competenciaDoCandidatoIgualExigidaPelaFaixa = new ConfiguracaoNivelCompetencia();
		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo;
		ConfiguracaoNivelCompetenciaVO tabelaCandidato;
		int totalPontosFaixa;
		
		for (Long candidatoId : candidatosDaSolicitacaoIds) {

			ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = configuracaoNivelCompetenciaCandidatoManager.findByCandidatoAndSolicitacao(candidatoId, solicitacao.getId());
			if(configuracaoNivelCompetenciaCandidato == null || configuracaoNivelCompetenciaCandidato.getId() == null)
				break;

			Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciaExigidasPelaFaixa = getDao().findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId());
			configuracoesNivelCompetenciaRespostasCandidato = getDao().findConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato.getId());
			Collection<ConfigHistoricoNivel> configHistoricoNiveis = configHistoricoNivelManager.findByNivelCompetenciaHistoricoId(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getNivelCompetenciaHistorico().getId());
			
			totalPontosFaixa = 0;
			matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
			tabelaCandidato = new ConfiguracaoNivelCompetenciaVO();
			
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaExigidaPelaFaixa : configuracaoNivelCompetenciaExigidasPelaFaixa) 
			{
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaRespostasCandidato : configuracoesNivelCompetenciaRespostasCandidato) 
					if(configuracaoNivelCompetenciaExigidaPelaFaixa.getCompetenciaDescricao().equals(configuracaoNivelCompetenciaRespostasCandidato.getCompetenciaDescricao()))
					{
						competenciaDoCandidatoIgualExigidaPelaFaixa = configuracaoNivelCompetenciaRespostasCandidato;
						break;
					}
				
				for (ConfigHistoricoNivel configHistoricoNivel : configHistoricoNiveis ) 
				{
					
					boolean isConfiguracaoCandidato = competenciaDoCandidatoIgualExigidaPelaFaixa.getNivelCompetencia() != null ? 
													configHistoricoNivel.getNivelCompetencia().getDescricao().equals(competenciaDoCandidatoIgualExigidaPelaFaixa.getNivelCompetencia().getDescricao()) : false;
					boolean isConfiguracaoFaixa = configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getDescricao().equals(configHistoricoNivel.getNivelCompetencia().getDescricao());
					matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoNivelCompetenciaExigidaPelaFaixa.getCompetenciaDescricao(), configHistoricoNivel.getOrdem() + " - " + configHistoricoNivel.getNivelCompetencia().getDescricao(),isConfiguracaoFaixa, isConfiguracaoCandidato));
				}
	
				totalPontosFaixa += configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getOrdem();
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getOrdem(), configuracaoNivelCompetenciaExigidaPelaFaixa.getCompetenciaDescricao(), "GAP", false, false));
	
			}
			tabelaCandidato = new ConfiguracaoNivelCompetenciaVO(configuracaoNivelCompetenciaCandidato.getCandidato().getNome(), clonaMatriz(matrizModelo), totalPontosFaixa);
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaRespostasCandidato : configuracoesNivelCompetenciaRespostasCandidato) 
				tabelaCandidato.configuraNivelCandidato(configuracaoNivelCompetenciaRespostasCandidato.getCompetenciaDescricao(), configuracaoNivelCompetenciaRespostasCandidato.getNivelCompetencia());
			
			tabelasCandidatos.add(tabelaCandidato);
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

	@TesteAutomatico
	public void removeByFaixas(Long[] faixaSalarialIds) 
	{
		getDao().removeByFaixas(faixaSalarialIds);
	}

	@TesteAutomatico
	public void removeColaborador(Colaborador colaborador) {
		getDao().removeColaborador(colaborador);
	}

	public void removeConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelColaboradorId) throws Exception, FortesException
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelColaboradorId);
		
		if(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario() != null && configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getId() != null)
			throw new FortesException("Esta configuração de competência não pode ser excluída, pois existe dependência com avaliação de desempenho.");
		
		configuracaoNivelCompetenciaCriterioManager.removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelColaboradorId);
		getDao().removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelColaboradorId);
		configuracaoNivelCompetenciaColaboradorManager.remove(configuracaoNivelColaboradorId);
	}

	public void removeConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelFaixaSalarialId) throws Exception
	{
		// TODO: Criar teste
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findById(configuracaoNivelFaixaSalarialId);
		
		verificaDependencias(configuracaoNivelCompetenciaFaixaSalarial);
		
		getDao().removeByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelFaixaSalarialId);
		configuracaoNivelCompetenciaFaixaSalarialManager.remove(configuracaoNivelFaixaSalarialId);
	}

	private void verificaDependencias(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) throws Exception
	{
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> proximasConfiguracoesDafaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findProximasConfiguracoesAposData(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
		Date dataDaProximaConfiguracaodaFaixaSalarial = (proximasConfiguracoesDafaixaSalarial.size() == 0 ? null : ((ConfiguracaoNivelCompetenciaFaixaSalarial) proximasConfiguracoesDafaixaSalarial.toArray()[0]).getData());

		if(existeDependenciaComCompetenciasDoColaborador(configuracaoNivelCompetenciaFaixaSalarial, dataDaProximaConfiguracaodaFaixaSalarial))
			throw new FortesException("Esta configuração de competência não pode ser excluída, pois existe dependência com competências do colaborador.");

		if(existeDependenciaComCompetenciasDoCandidato(configuracaoNivelCompetenciaFaixaSalarial, dataDaProximaConfiguracaodaFaixaSalarial))
			throw new FortesException("Esta configuração de competência não pode ser excluída, pois existe dependência com competências do candidato.");
	}
		
	private boolean existeDependenciaComCompetenciasDoColaborador(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Date dataDaProximaConfiguracaodaFaixaSalarial) 
	{
		return configuracaoNivelCompetenciaColaboradorManager.existeDependenciaComCompetenciasDaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial);
	}

	private boolean existeDependenciaComCompetenciasDoCandidato(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Date dataDaProximaConfiguracaodaFaixaSalarial)
	{
		return getDao().existeDependenciaComCompetenciasDoCandidato(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial);
	}
	
	public void removeByCandidato(Long candidatoId) {
		getDao().removeByCandidato(candidatoId);	
		configuracaoNivelCompetenciaCandidatoManager.removeByCandidato(candidatoId);
	}

	@TesteAutomatico
	public Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId) {
		return getDao().findCompetenciasIdsConfiguradasByFaixaSolicitacao(faixaSalarialId);
	}

	@TesteAutomatico
	public Integer somaConfiguracoesByFaixa(Long faixaSalarialId) {
		return getDao().somaConfiguracoesByFaixa(faixaSalarialId);
	}

	@TesteAutomatico
	public Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(	Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor) 
	{
		return getDao().findColaboradoresCompetenciasAbaixoDoNivel(	empresaId, estabelecimentoIds, areaIds, colaboradoresAvaliados, agruparPor);
	}
	
	@TesteAutomatico
	public Collection<Competencia> findCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, Date dataIni, Date dataFim) 
	{
		return getDao().findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, dataIni, dataFim);
	}
	
	@TesteAutomatico(metodoMock="removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixasSalariais")
	public void removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixaSalarial(Long[] faixaIds)
	{
		getDao().removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixasSalariais(faixaIds);
	}
	
	@TesteAutomatico(metodoMock="removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixasSalariais")
	public void removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixaSalarial(Long[] faixaIds)
	{
		getDao().removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixasSalariais(faixaIds);
	}

	@TesteAutomatico
	public Collection<Colaborador> findDependenciaComColaborador(Long faixaSalarialId, Date data) 
	{
		return getDao().findDependenciaComColaborador(faixaSalarialId, data);
	}
	
	@TesteAutomatico
	public Collection<Candidato> findDependenciaComCandidato(Long faixaSalarialId, Date data)
	{
		return getDao().findDependenciaComCandidato(faixaSalarialId, data);
	}

	public void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) 
	{
		getDao().removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
		configuracaoNivelCompetenciaCandidatoManager.removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}
	
	public boolean existeConfiguracaoNivelCompetencia(Long competenciaId, char tipoCompetencia) {
		return getDao().verifyExists(new String[]{"competenciaId", "tipoCompetencia"}, new Object[]{competenciaId, tipoCompetencia});
	}

	@TesteAutomatico
	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		getDao().removeBySolicitacaoId(solicitacaoId);
	}

	@TesteAutomatico
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciasAndPesos(Long avaliacaoDesempenhoId, Long avaliadoId) 
	{
		return getDao().findCompetenciasAndPesos(avaliacaoDesempenhoId, avaliadoId);
	}

	public RelatorioAnaliseDesempenhoColaborador montaRelatorioAnaliseDesempenhoColaborador(Long avaliacaoDesempenhoId, Long avaliadoId, Collection<Long> avaliadoresIds, Integer notaMinimaMediaGeralCompetencia) {
		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = new RelatorioAnaliseDesempenhoColaborador();
		relatorioAnaliseDesempenhoColaborador.setNotaMinimaMediaGeralCompetencia(notaMinimaMediaGeralCompetencia);
		relatorioAnaliseDesempenhoColaborador.setResultadosCompetenciaColaborador(montaRelatorioResultadoCompetencia(avaliacaoDesempenhoId, avaliadoId, avaliadoresIds));
		relatorioAnaliseDesempenhoColaborador.setValorMaximoGrafico(nivelCompetenciaManager.getOrdemMaximaByAavaliacaoDesempenhoAndAvaliado(avaliacaoDesempenhoId, avaliadoId));
		
		return relatorioAnaliseDesempenhoColaborador;
	}
	
	private LinkedList<ResultadoCompetenciaColaborador> montaRelatorioResultadoCompetencia(Long avaliacaoDesempenhoId, Long avaliadoId, Collection<Long> avaliadoresIds) {
		LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = getDao().findByAvaliacaaDesempenhoAndAvaliado(avaliacaoDesempenhoId, avaliadoId);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = new LinkedList<ResultadoCompetenciaColaborador>();
		
		Map<String, LinkedList<ConfiguracaoNivelCompetencia>> cncAbrupadoPorCompetencia = new HashMap<String, LinkedList<ConfiguracaoNivelCompetencia>>();
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) {
			if(!cncAbrupadoPorCompetencia.containsKey(configuracaoNivelCompetencia.getCompetencia().getNome()))
				cncAbrupadoPorCompetencia.put(configuracaoNivelCompetencia.getCompetencia().getNome(), new LinkedList<ConfiguracaoNivelCompetencia>());
			
			cncAbrupadoPorCompetencia.get(configuracaoNivelCompetencia.getCompetencia().getNome()).add(configuracaoNivelCompetencia);
		}
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador;
		for (String competenciaNome : cncAbrupadoPorCompetencia.keySet()) {
			resultadoCompetenciaColaborador = new ResultadoCompetenciaColaborador();
			resultadoCompetenciaColaborador.setResultadoCompetencias(montaResultadoCompetencia(cncAbrupadoPorCompetencia.get(competenciaNome), avaliadoId, avaliadoresIds));
			resultadoCompetenciaColaborador.setCompetenciaNome(competenciaNome);
			resultadoCompetenciaColaboradores.add(resultadoCompetenciaColaborador);
		}
		
		return resultadoCompetenciaColaboradores;
	}

	private LinkedList<ResultadoCompetencia> montaResultadoCompetencia(LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias, Long avaliadoId, Collection<Long> avaliadoresIds) {
		LinkedList<ResultadoCompetencia> resultadoCompetencias = new LinkedList<ResultadoCompetencia>();
		Integer totalDeCompetencia = configuracaoNivelCompetencias.size();
		HashMap<Long, LinkedList<ConfiguracaoNivelCompetencia>> cncAgrupadoPorCargo = montaMapCNC(configuracaoNivelCompetencias, avaliadoId, avaliadoresIds);
		
		Double somaTotalDaOrdem = 0.0;
		ResultadoCompetencia resultadoCompetencia;
		for (Long key : cncAgrupadoPorCargo.keySet()) {
			Double somaOrdem = 0.0;
			resultadoCompetencia = new ResultadoCompetencia();
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : cncAgrupadoPorCargo.get(key))
				somaOrdem += configuracaoNivelCompetencia.getNivelCompetencia().getOrdem();

			somaTotalDaOrdem += somaOrdem;
			
			if(key.equals(AUTOAVALIACAO))
				resultadoCompetencia.setNome("Auto-Avaliação");
			else if(key.equals(OUTROSAVALIADORES))
				resultadoCompetencia.setNome("Outros Avaliadores");
			else
				resultadoCompetencia.setNome(((ConfiguracaoNivelCompetencia) cncAgrupadoPorCargo.get(key).toArray()[0]).getCargo().getNome());
			
			resultadoCompetencia.setOrdem(somaOrdem / cncAgrupadoPorCargo.get(key).size());
			resultadoCompetencias.add(resultadoCompetencia);
		}
		
		if(configuracaoNivelCompetencias.size() > 0){
			resultadoCompetencia = new ResultadoCompetencia();
			resultadoCompetencia.setNome("Média");
			resultadoCompetencia.setOrdem(somaTotalDaOrdem / totalDeCompetencia);
			resultadoCompetencias.add(resultadoCompetencia);
		}
		
		return resultadoCompetencias;
	}

	private HashMap<Long, LinkedList<ConfiguracaoNivelCompetencia>> montaMapCNC(LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias, Long avaliadoId, Collection<Long> avaliadoresIds) {
		HashMap<Long, LinkedList<ConfiguracaoNivelCompetencia>> cncAgrupadoPorCargo = new LinkedHashMap<Long, LinkedList<ConfiguracaoNivelCompetencia>>();
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasAdicionadosNoMap = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		if(avaliadoresIds.contains(avaliadoId))
			avaliadoresIds.remove(avaliadoId);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) {
			if(configuracaoNivelCompetencia.getColaborador().getId().equals(avaliadoId)){
				if(!cncAgrupadoPorCargo.containsKey(AUTOAVALIACAO))
					cncAgrupadoPorCargo.put(AUTOAVALIACAO, new LinkedList<ConfiguracaoNivelCompetencia>());
				
				cncAgrupadoPorCargo.get(AUTOAVALIACAO).add(configuracaoNivelCompetencia);
				configuracaoNivelCompetenciasAdicionadosNoMap.add(configuracaoNivelCompetencia);
			}
		}
		
		for (Long avaliadorId : avaliadoresIds){
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) {
				if(configuracaoNivelCompetencia.getColaborador().getId().equals(avaliadorId)){
					if(!cncAgrupadoPorCargo.containsKey(configuracaoNivelCompetencia.getCargo().getId()))
						cncAgrupadoPorCargo.put(configuracaoNivelCompetencia.getCargo().getId(), new LinkedList<ConfiguracaoNivelCompetencia>());
					
					cncAgrupadoPorCargo.get(configuracaoNivelCompetencia.getCargo().getId()).add(configuracaoNivelCompetencia);
					configuracaoNivelCompetenciasAdicionadosNoMap.add(configuracaoNivelCompetencia);
				}
			}
		}

		configuracaoNivelCompetencias.removeAll(configuracaoNivelCompetenciasAdicionadosNoMap);
		if(configuracaoNivelCompetencias.size() > 0)
			cncAgrupadoPorCargo.put(OUTROSAVALIADORES, configuracaoNivelCompetencias);
		
		return cncAgrupadoPorCargo;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}
	
	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}
		
	public CriterioAvaliacaoCompetenciaManager getCriterioAvaliacaoCompetenciaManager() {
		return criterioAvaliacaoCompetenciaManager;
	}

	public void setCriterioAvaliacaoCompetenciaManager(CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager) {
		this.criterioAvaliacaoCompetenciaManager = criterioAvaliacaoCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaCriterioManager(ConfiguracaoNivelCompetenciaCriterioManager configuracaoNivelCompetenciaCriterioManager) {
		this.configuracaoNivelCompetenciaCriterioManager = configuracaoNivelCompetenciaCriterioManager;
	}

	public void setConfigHistoricoNivelManager(ConfigHistoricoNivelManager configHistoricoNivelManager) {
		this.configHistoricoNivelManager = configHistoricoNivelManager;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarialManager getConfiguracaoNivelCompetenciaFaixaSalarialManager() {
		return this.configuracaoNivelCompetenciaFaixaSalarialManager;
	}

	public void setConfiguracaoNivelCompetenciaCandidatoManager(ConfiguracaoNivelCompetenciaCandidatoManager configuracaoNivelCompetenciaCandidatoManager) {
		this.configuracaoNivelCompetenciaCandidatoManager = configuracaoNivelCompetenciaCandidatoManager;
	}
}