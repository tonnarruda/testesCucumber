package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;

public class ConfiguracaoCampoExtraVisivelObrigadotorioFactory
{
	public static ConfiguracaoCampoExtraVisivelObrigadotorio getEntity()
	{
		ConfiguracaoCampoExtraVisivelObrigadotorio configCampos = new ConfiguracaoCampoExtraVisivelObrigadotorio();
		return configCampos;
	}

	public static ConfiguracaoCampoExtraVisivelObrigadotorio getEntity(Long empresaId, String camposExtrasVisiveis, String  tipoConfiguracaoCampoExtra )
	{
		ConfiguracaoCampoExtraVisivelObrigadotorio configCampos = new ConfiguracaoCampoExtraVisivelObrigadotorio();
		configCampos.setEmpresaId(empresaId);
		configCampos.setCamposExtrasVisiveis(camposExtrasVisiveis);
		configCampos.setTipoConfiguracaoCampoExtra(tipoConfiguracaoCampoExtra);
		configCampos.setCamposExtrasObrigatorios("");
		return configCampos;
	}
}
