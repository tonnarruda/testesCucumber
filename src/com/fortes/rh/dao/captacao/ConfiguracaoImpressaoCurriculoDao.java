package com.fortes.rh.dao.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;

public interface ConfiguracaoImpressaoCurriculoDao extends GenericDao<ConfiguracaoImpressaoCurriculo> 
{
	ConfiguracaoImpressaoCurriculo findByUsuario(Long usuarioId, Long empresaId);
}
