package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;

public class ConfiguracaoNivelCompetenciaManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetencia, ConfiguracaoNivelCompetenciaDao> implements ConfiguracaoNivelCompetenciaManager
{
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	
	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId) 
	{
		return getDao().findByFaixa(faixaSalarialId);
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId) {
		return getDao().findByCandidato(candidatoId);
	}
	
	public void saveCompetencias(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId) 
	{
		if(candidatoId == null)
			getDao().deleteConfiguracaoByFaixa(faixaSalarialId);
		else
			getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais)
		{
			if (configuracaoNivelCompetencia.getCompetenciaId() != null)
			{
				configuracaoNivelCompetencia.setFaixaSalarialIdProjection(faixaSalarialId);
				if(candidatoId != null)
					configuracaoNivelCompetencia.setCandidatoIdProjection(candidatoId);
				
				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}
	
	public void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		//falta testar e deletar
		configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.save(configuracaoNivelCompetenciaColaborador);
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNiveisCompetencias) 
		{
			if (configuracaoNivelCompetencia.getCompetenciaId() != null)
			{
				configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
				getDao().save(configuracaoNivelCompetencia);
			}
		}
	}

	public Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId) 
	{
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos = getDao().findByCandidato(candidatoId);
		
		if(!niveisCompetenciaFaixaSalariaisSalvos.isEmpty())
		{
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);
			for (ConfiguracaoNivelCompetencia nivelCompetenciaCandidato : niveisCompetenciaFaixaSalariaisSalvos)
			{
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) 
				{
					if(nivelCompetenciaCandidato.getCompetenciaId().equals(configuracaoNivelCompetencia.getCompetenciaId()) && nivelCompetenciaCandidato.getTipoCompetencia().equals(configuracaoNivelCompetencia.getTipoCompetencia()))
					{
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

	public Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long configuracaoNivelCompetenciaColaboradorId) {
		return getDao().findByColaborador(configuracaoNivelCompetenciaColaboradorId);
	}
}
