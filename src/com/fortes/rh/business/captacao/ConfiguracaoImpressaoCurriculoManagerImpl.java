package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoImpressaoCurriculoDao;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;

@Component
public class ConfiguracaoImpressaoCurriculoManagerImpl extends GenericManagerImpl<ConfiguracaoImpressaoCurriculo, ConfiguracaoImpressaoCurriculoDao> implements ConfiguracaoImpressaoCurriculoManager
{
	@Autowired
	ConfiguracaoImpressaoCurriculoManagerImpl(ConfiguracaoImpressaoCurriculoDao configuracaoImpressaoCurriculoDao) {
		setDao(configuracaoImpressaoCurriculoDao);
	}
	
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
