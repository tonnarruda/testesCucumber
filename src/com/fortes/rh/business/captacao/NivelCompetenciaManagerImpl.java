package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaManagerImpl extends GenericManagerImpl<NivelCompetencia, NivelCompetenciaDao> implements NivelCompetenciaManager
{
	public Collection<NivelCompetencia> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public void validaLimite(Long empresaId) throws Exception 
	{
		if (getDao().findAllSelect(empresaId).size() >= 10)
			throw new Exception("Não é permitido cadastrar mais do que dez Níveis de Competência.");
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long cargoId, Long empresaId) 
	{
		return getDao().findByCargoOrEmpresa(cargoId, empresaId);
	}

	public Integer getPontuacaoObtidaByConfiguracoesNiveisCompetencia(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaMarcados) 
	{
		Integer pontuacao = 0;
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaMarcados) {
			if(configuracaoNivelCompetencia.getNivelCompetencia() != null && configuracaoNivelCompetencia.getNivelCompetencia().getId() != null)
				pontuacao += configuracaoNivelCompetencia.getNivelCompetencia().getOrdem() * configuracaoNivelCompetencia.getPesoCompetencia();
		}
		
		return pontuacao;
	}

	public int getOrdemMaxima(Long empresaId) 
	{
		return getDao().getOrdemMaxima(empresaId);
	}

	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual) 
	{
		return getDao().existePercentual(nivelCompetenciaId, empresaId, percentual);
	}

	public boolean existeNivelCompetenciaSemPercentual(Long empresaId) 
	{
		return getDao().existeNivelCompetenciaSemPercentual(empresaId);
	}

	public void gerarPercentualIgualmente(Long empresaId) 
	{
		List<NivelCompetencia> niveisCompetencias = (List<NivelCompetencia>) getDao().findAllSelect(empresaId);

		if(niveisCompetencias != null && niveisCompetencias.size() > 0)
		{
			Double percentualMedio = 100.0/niveisCompetencias.size();

			for (int i = 0; i < niveisCompetencias.size()-1; i++) 
			{
				niveisCompetencias.get(i).setPercentual(percentualMedio * (i+1));
				getDao().update(niveisCompetencias.get(i));
			}

			niveisCompetencias.get(niveisCompetencias.size()-1).setPercentual(100.0);
			getDao().update(niveisCompetencias.get(niveisCompetencias.size()-1));
		}
	}
}
