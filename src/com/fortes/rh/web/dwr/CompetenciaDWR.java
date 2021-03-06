package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

public class CompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private CompetenciaManager competenciaManager;

	@SuppressWarnings("rawtypes")
	public Map getCompetenciasColaboradorByFaixaSalarialAndData(Long faixaId, String data)
	{
		if(!"  /  /    ".equals(data) && !"".equals(data) && faixaId != null && faixaId != 0)
			return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, DateUtil.criarDataDiaMesAno(data), null), "getId", "getNome", Competencia.class);
		
		return new LinkedHashMap();
	}
	
	@SuppressWarnings("rawtypes")
	public Map getCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, String dataIni, String dataFim)
	{
		if(!"  /  /    ".equals(dataIni) && !"".equals(dataIni) && !"  /  /    ".equals(dataFim) && !"".equals(dataFim) && faixaId != null && faixaId != 0)
			return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, DateUtil.criarDataDiaMesAno(dataIni), DateUtil.criarDataDiaMesAno(dataFim)), "getId", "getNome", Competencia.class);

		return new LinkedHashMap();
	}
	
	public String findVinculosCompetencia(Long faixaId, String dataString)
	{
		String msg = "";
		
		Date data = DateUtil.criarDataDiaMesAno(dataString);
		
		Collection<Colaborador> colaboradores = configuracaoNivelCompetenciaManager.findDependenciaComColaborador(faixaId, data);
		Collection<Candidato> candidatos = configuracaoNivelCompetenciaManager.findDependenciaComCandidato(faixaId, data);
		
		if(colaboradores.size() > 0 || candidatos.size() > 0)
		{
			msg = "O histórico de competência da faixa salarial não pode ser criado nesta data (" + DateUtil.formataDiaMesAno(data) + "), pois possui dependência com o(s) histórico(s) da(s) competência(s) de:";
		
			Candidato cand = null;
			for (int i = 0; i < candidatos.size(); i++) 
			{
				cand = ((Candidato) candidatos.toArray()[i]);
				if(i==0)
					msg += "</br></br>Candidato(s): ";
				
				msg += cand.getNome();
				
				if(i+1 != candidatos.size())
					msg += ", ";
				
				if(data.before(cand.getDataAtualizacao()))
					data = cand.getDataAtualizacao();
			}
			
			Colaborador colab = null;
			boolean exibeTitulo = true;
			for (int i = 0; i < colaboradores.size(); i++) 
			{
				colab = ((Colaborador) colaboradores.toArray()[i]);
				if(colab.getAvaliacaoDesempenhoTitulo() == null || "".equals(colab.getAvaliacaoDesempenhoTitulo()))
				{
					if(exibeTitulo){				
						msg += "</br></br>Colaborador(es): ";
						exibeTitulo = false;
					}
					
					msg += colab.getNome() + ", ";
				}
				
				if(data.before(colab.getDataAtualizacao()))
					data = colab.getDataAtualizacao();
			}
			
			if(!exibeTitulo)
				msg = msg.substring(0, msg.length()-2);
		
			exibeTitulo = true;
			for (int i = 0; i < colaboradores.size(); i++) 
			{
				colab = ((Colaborador) colaboradores.toArray()[i]);
				if(colab.getAvaliacaoDesempenhoTitulo() != null && !"".equals(colab.getAvaliacaoDesempenhoTitulo()))
				{
					if(exibeTitulo){
						msg += "</br></br>Avaliação(ões): ";
						exibeTitulo = false;					
					}
					
					msg += colab.getAvaliacaoDesempenhoTitulo() + ", ";
				}
			}
			
			if(!exibeTitulo)
				msg = msg.substring(0, msg.length()-2);
			
			msg += "</br></br>Só é permitido criar histórico de competência para esta faixa salarial a partir do dia " + DateUtil.formataDiaMesAno(DateUtil.incrementaDias(data,1));
		}
		
		return msg;
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByAvaliacoes(Long empresaId, Long[] avaliacoesIds, String competenciasConsideradas)
	{
		Collection<Competencia> competencias = competenciaManager.findByAvaliacoesDesempenho(empresaId, avaliacoesIds, competenciasConsideradas);
			
		return new CollectionUtil<Competencia>().convertCollectionToMap(competencias, "getId", "getNome");
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setCompetenciaManager(CompetenciaManager competenciaManager) {
		this.competenciaManager = competenciaManager;
	}
}
