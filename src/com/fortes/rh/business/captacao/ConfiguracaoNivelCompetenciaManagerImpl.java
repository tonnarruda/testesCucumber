package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
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

	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data) {
		return getDao().findByFaixa(faixaSalarialId, data);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) {
		return getDao().findByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}
	
	public void criaCNCColaboradorByCNCCnadidato(Colaborador colaborador, Long idCandidato, Solicitacao solicitacao, HistoricoColaborador historico) 
	{
		Collection<ConfiguracaoNivelCompetencia> cNCCandidatoVincuadasComCNCFaixaSalarial = new ArrayList<ConfiguracaoNivelCompetencia>();
		Collection<ConfiguracaoNivelCompetencia> cncsCandidato = findByCandidatoAndSolicitacao(idCandidato, solicitacao.getId());
		Collection<ConfiguracaoNivelCompetencia> cncsFaixaSalarial = getDao().findByFaixa(historico.getFaixaSalarial().getId(), historico.getData());
	
		for (ConfiguracaoNivelCompetencia cncCandidato : cncsCandidato) 
		{
			for (ConfiguracaoNivelCompetencia cncFaixaSalarial : cncsFaixaSalarial) 
			{
				if (cncCandidato.getCompetenciaId().equals(cncFaixaSalarial.getCompetenciaId()) && cncCandidato.getTipoCompetencia().equals(cncFaixaSalarial.getTipoCompetencia()))
				{
					cncCandidato.setId(null);
					cncCandidato.setCandidato(null);
					cncCandidato.setFaixaSalarial(null);
					cncCandidato.setConfiguracaoNivelCompetenciaFaixaSalarial(null);
					cNCCandidatoVincuadasComCNCFaixaSalarial.add(cncCandidato);
				}
			}
		}
		
		if (cNCCandidatoVincuadasComCNCFaixaSalarial != null && cNCCandidatoVincuadasComCNCFaixaSalarial.size() > 0)
		{
			ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
			configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
			configuracaoNivelCompetenciaColaborador.setFaixaSalarial(historico.getFaixaSalarial());
			configuracaoNivelCompetenciaColaborador.setData(historico.getData());

			saveCompetenciasColaborador(cNCCandidatoVincuadasComCNCFaixaSalarial, configuracaoNivelCompetenciaColaborador);
		}
	}

	public void saveCompetenciasCandidato(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, Long faixaSalarialId, Long candidatoId, Long solicitacaoId) 
	{
		getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId, solicitacaoId);

		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) {
			if (configuracaoNivelCompetencia.getCompetenciaId() != null) {
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				configuracaoNivelCompetencia.setCandidatoIdProjection(candidatoId);
				configuracaoNivelCompetencia.setSolicitacaoId(solicitacaoId);

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
		Collection<ConfiguracaoNivelCompetencia> cncsCandidato = getDao().findByCandidatoAndSolicitacao(candidatoId, null);

		if (!cncsCandidato.isEmpty()) 
		{
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaEmpresa = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);

			for (ConfiguracaoNivelCompetencia cncCandidato : cncsCandidato) 
			{
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
				if(!cncCandidato.getSolicitacao().getId().equals(solicitacao.getId())){

					if(solicitacao.getId() != null)
						solicitacoesComConfigNiveisCompetenciaCandidato.add(solicitacao);

					solicitacao = cncCandidato.getSolicitacao();
					solicitacao.setNivelCompetencias(nivelCompetenciaManager.findAllSelect(empresaId, null, solicitacao.getData()));
					solicitacao.setFaixaSalarial(cncCandidato.getFaixaSalarial());
					solicitacao.setConfiguracaoNivelCompetencias(new ArrayList<ConfiguracaoNivelCompetencia>());
				}

				solicitacao.getConfiguracaoNivelCompetencias().add(cncCandidato);	
			}
			solicitacoesComConfigNiveisCompetenciaCandidato.add(solicitacao);
		
			for (Solicitacao solicitacaoConf : solicitacoesComConfigNiveisCompetenciaCandidato) 
			{
				Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetenciaDaFaixa = getDao().findByFaixa(solicitacaoConf.getFaixaSalarial().getId(), solicitacaoConf.getData());
			
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

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId, Long configuracaoNivelCompetenciaFaixaSalarialId, Date data) {
		Collection<ConfiguracaoNivelCompetencia> configuracoesNiveisCompetencia = getDao().findByConfiguracaoNivelCompetenciaColaborador(null, configuracaoNivelCompetenciaColaboradorId, data);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracoesNiveisCompetencia) {
			configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaCriterios(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetencia.getId(), configuracaoNivelCompetenciaFaixaSalarialId));
		}
		
		return configuracoesNiveisCompetencia;
	}
	
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
			configuracaoNivelCompetenciasColaborador = getDao().findByConfiguracaoNivelCompetenciaColaborador(competenciasIds, configuracaoNivelCompetenciaColaborador.getId(), configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial().getData());

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
	
	public Collection<MatrizCompetenciaNivelConfiguracao> montaConfiguracaoNivelCompetenciaByFaixa(Long empresaId, Long faixaSalarialId, Date data) 
	{
		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial =  configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarialId, data);
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
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial =  configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(configuracaoNivelCompetenciaColaborador.getFaixaSalarial().getId(), colaboradorQuestionario.getRespondidaEm());
			
			Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasExigidasPelaFaixa = getDao().findCompetenciaByFaixaSalarial(configuracaoNivelCompetenciaColaborador.getFaixaSalarial().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null);
				
			Collection<ConfiguracaoNivelCompetencia> competenciasDoColaborador = getDao().findByConfiguracaoNivelCompetenciaColaborador(null, configuracaoNivelCompetenciaColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
			
			Collection<NivelCompetencia> niveis = nivelCompetenciaManager.findAllSelect(empresaId, null, configuracaoNivelCompetenciaFaixaSalarial.getData());
			
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

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciaExigidasPelaFaixa = getDao().findCompetenciaByFaixaSalarial(faixaSalarialId, solicitacao.getData(), null, null, null);
		Collection<ConfigHistoricoNivel> configHistoricoNiveis = configHistoricoNivelManager.findByEmpresaAndDataNivelCompetenciaHistorico(empresaId, solicitacao.getData());

		Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetenciaCandidato = new ArrayList<ConfiguracaoNivelCompetencia>();
		ConfiguracaoNivelCompetencia competenciaDoCandidatoIgualExigidaPelaFaixa = new ConfiguracaoNivelCompetencia();
		Collection<MatrizCompetenciaNivelConfiguracao> matrizModelo;
		ConfiguracaoNivelCompetenciaVO tabelaCandidato;
		int totalPontosFaixa;
		
		for (Long candidatoId : candidatosDaSolicitacaoIds) {
			configuracoesNivelCompetenciaCandidato = getDao().findBySolicitacaoIdCandidatoIdAndDataNivelCompetenciaHistorico(solicitacao.getId(), candidatoId, solicitacao.getData());
			
			if(configuracoesNivelCompetenciaCandidato.size() == 0)
				break;
			
			totalPontosFaixa = 0;
			matrizModelo = new ArrayList<MatrizCompetenciaNivelConfiguracao>();
			tabelaCandidato = new ConfiguracaoNivelCompetenciaVO();
			
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaExigidaPelaFaixa : configuracaoNivelCompetenciaExigidasPelaFaixa) 
			{
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaCandidato : configuracoesNivelCompetenciaCandidato) 
					if(configuracaoNivelCompetenciaExigidaPelaFaixa.getCompetenciaDescricao().equals(configuracaoNivelCompetenciaCandidato.getCompetenciaDescricao()))
					{
						competenciaDoCandidatoIgualExigidaPelaFaixa = configuracaoNivelCompetenciaCandidato;
						break;
					}
				
				for (ConfigHistoricoNivel configHistoricoNivel : configHistoricoNiveis  ) 
				{
					boolean isConfiguracaoCandidato = configHistoricoNivel.getNivelCompetencia().getDescricao().equals(competenciaDoCandidatoIgualExigidaPelaFaixa.getNivelCompetencia().getDescricao());
					boolean isConfiguracaoFaixa = configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getDescricao().equals(configHistoricoNivel.getNivelCompetencia().getDescricao());
					matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoNivelCompetenciaExigidaPelaFaixa.getCompetenciaDescricao(), configHistoricoNivel.getOrdem() + " - " + configHistoricoNivel.getNivelCompetencia().getDescricao(),isConfiguracaoFaixa, isConfiguracaoCandidato));
				}
	
				totalPontosFaixa += configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getOrdem();
				matrizModelo.add(new MatrizCompetenciaNivelConfiguracao(configuracaoNivelCompetenciaExigidaPelaFaixa.getNivelCompetencia().getOrdem(), configuracaoNivelCompetenciaExigidaPelaFaixa.getCompetenciaDescricao(), "GAP", false, false));
	
			}
			tabelaCandidato = new ConfiguracaoNivelCompetenciaVO(configuracoesNivelCompetenciaCandidato.iterator().next().getCandidato().getNome(), clonaMatriz(matrizModelo), totalPontosFaixa);

			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaCandidato : configuracoesNivelCompetenciaCandidato) 
				tabelaCandidato.configuraNivelCandidato(configuracaoNivelCompetenciaCandidato.getCompetenciaDescricao(), configuracaoNivelCompetenciaCandidato.getNivelCompetencia());
			
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

	public Collection<Colaborador> findDependenciaComColaborador(Long faixaSalarialId, Date data) 
	{
		return getDao().findDependenciaComColaborador(faixaSalarialId, data);
	}
	
	public Collection<Candidato> findDependenciaComCandidato(Long faixaSalarialId, Date data)
	{
		return getDao().findDependenciaComCandidato(faixaSalarialId, data);
	}

	public void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) 
	{
		getDao().removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}

	public boolean existeConfiguracaoNivelCompetencia(Long competenciaId, char tipoCompetencia) {
		return getDao().verifyExists(new String[]{"competenciaId", "tipoCompetencia"}, new Object[]{competenciaId, tipoCompetencia});
	}

	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		getDao().removeBySolicitacaoId(solicitacaoId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciasAndPesos(Long avaliacaoDesempenhoId, Long avaliadoId) 
	{
		return getDao().findCompetenciasAndPesos(avaliacaoDesempenhoId, avaliadoId);
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
}