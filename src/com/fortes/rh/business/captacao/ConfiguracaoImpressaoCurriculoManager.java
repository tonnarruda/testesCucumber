package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;

public interface ConfiguracaoImpressaoCurriculoManager extends GenericManager<ConfiguracaoImpressaoCurriculo>
{

	ConfiguracaoImpressaoCurriculo findByUsuario(Long usuarioId, Long empresaId);

	void saveOrUpdate(ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo);
}
