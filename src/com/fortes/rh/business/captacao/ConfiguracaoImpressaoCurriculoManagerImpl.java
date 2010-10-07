package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoImpressaoCurriculoDao;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;

public class ConfiguracaoImpressaoCurriculoManagerImpl extends GenericManagerImpl<ConfiguracaoImpressaoCurriculo, ConfiguracaoImpressaoCurriculoDao> implements ConfiguracaoImpressaoCurriculoManager
{

	public ConfiguracaoImpressaoCurriculo findByUsuario(Long usuarioId, Long empresaId)
	{
		ConfiguracaoImpressaoCurriculo retorno = getDao().findByUsuario(usuarioId, empresaId);
		
		if(retorno == null)
			return new ConfiguracaoImpressaoCurriculo();
		else
			return retorno;
	}

	public void saveOrUpdate(ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo)
	{
		ConfiguracaoImpressaoCurriculo temp = null;
		temp = getDao().findByUsuario(configuracaoImpressaoCurriculo.getUsuario().getId(), configuracaoImpressaoCurriculo.getEmpresa().getId());
		if(temp == null)
			getDao().save(configuracaoImpressaoCurriculo);
		else
		{
			configuracaoImpressaoCurriculo.setId(temp.getId());
			if(!temp.equals(configuracaoImpressaoCurriculo))
				getDao().update(configuracaoImpressaoCurriculo);
		}
	}
}
