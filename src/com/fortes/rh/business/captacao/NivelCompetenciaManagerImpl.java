package com.fortes.rh.business.captacao;

import java.util.Collection;

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

}
