package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;

public interface ConfiguracaoCampoExtraVisivelObrigadotorioManager extends GenericManager<ConfiguracaoCampoExtraVisivelObrigadotorio>
{
	public ConfiguracaoCampoExtraVisivelObrigadotorio findByEmpresaId(Long empresaId, String tipoConfiguracao);

	public void saveOrUpdateConfCamposExtras(Long empresaId, String camposVisivesis, String[] tiposConfiguracao);

	public void removeByEmpresaAndTipoConfig(Long empresaId, String[] tipoConfiguracao); 
}