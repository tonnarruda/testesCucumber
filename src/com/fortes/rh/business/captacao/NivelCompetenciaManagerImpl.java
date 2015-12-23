package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaManagerImpl extends GenericManagerImpl<NivelCompetencia, NivelCompetenciaDao> implements NivelCompetenciaManager
{
	public Collection<NivelCompetencia> findAllSelect(Long empresaId, Long nivelCompetenciaHistoricoId, Date data)
	{
		return getDao().findAllSelect(empresaId, nivelCompetenciaHistoricoId, data);
	}
	
	public Collection<NivelCompetencia> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public void validaLimite(Long empresaId) throws Exception 
	{
		if (getDao().findAllSelect(empresaId, null, null).size() >= 10)
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
				pontuacao += configuracaoNivelCompetencia.getNivelCompetencia().getOrdem() * (configuracaoNivelCompetencia.getPesoCompetencia()==null?1:configuracaoNivelCompetencia.getPesoCompetencia());
		}
		
		return pontuacao;
	}

	public int getOrdemMaxima(Long empresaId, Date data) 
	{
		return getDao().getOrdemMaxima(empresaId, data);
	}
	
	public Double getOrdemMaximaByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) 
	{
		return getDao().getOrdemMaximaByNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
	}

	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual) 
	{
		return getDao().existePercentual(nivelCompetenciaId, empresaId, percentual);
	}

	public boolean existeNivelCompetenciaSemPercentual(Long empresaId) 
	{
		return getDao().existeNivelCompetenciaSemPercentual(empresaId);
	}

}
