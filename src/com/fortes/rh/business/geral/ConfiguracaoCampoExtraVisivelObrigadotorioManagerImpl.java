package com.fortes.rh.business.geral;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraVisivelObrigadotorioDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;

public class ConfiguracaoCampoExtraVisivelObrigadotorioManagerImpl extends GenericManagerImpl<ConfiguracaoCampoExtraVisivelObrigadotorio, ConfiguracaoCampoExtraVisivelObrigadotorioDao> implements ConfiguracaoCampoExtraVisivelObrigadotorioManager
{
	public ConfiguracaoCampoExtraVisivelObrigadotorio findByEmpresaId( Long empresaId, String tipoConfiguracao) {
		return getDao().findByEmpresaId(empresaId, tipoConfiguracao);
	}
	
	public  Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> findByEmpresaId(Long empresaId, String[] tiposConfiguracao) {
		return getDao().findCollectionByEmpresaId(empresaId, tiposConfiguracao);
	}
	
	public void removeByEmpresaAndTipoConfig(Long empresaId, String[] tipoConfiguracao) {
		getDao().removeByEmpresaAndTipoConfig(empresaId, tipoConfiguracao);
	}
	
	public void saveOrUpdateConfCamposExtras(Long empresaId, String camposVisivesis, String[] tiposConfiguracao) {
		Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> configCampos = findByEmpresaId(empresaId, tiposConfiguracao);
		if(configCampos != null && configCampos.size() > 0){
			for (ConfiguracaoCampoExtraVisivelObrigadotorio configCampo : configCampos) {
				configCampo.setCamposExtrasVisiveis((camposVisivesis.substring(0, camposVisivesis.length() -1)));
				ajustaCamposObrigatorios(configCampo, camposVisivesis);
				getDao().update(configCampo);
			}
		}
		else
			salvaConfigCampos(empresaId, camposVisivesis, tiposConfiguracao);
	}
	
	private void salvaConfigCampos(Long empresaId, String camposVisivesis, String[] tiposConfiguracao) {
		for (String tipo : tiposConfiguracao) {
			camposVisivesis = (camposVisivesis.substring(0, camposVisivesis.length() -1));
			ConfiguracaoCampoExtraVisivelObrigadotorio configCamposColaborador = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresaId, camposVisivesis, tipo);
			getDao().save(configCamposColaborador);
		}
	}

	private void ajustaCamposObrigatorios(ConfiguracaoCampoExtraVisivelObrigadotorio configCampo, String camposVisivesis) {
		String camposObrigatorios = configCampo.getCamposExtrasObrigatorios() != null ?  configCampo.getCamposExtrasObrigatorios():"";
		Collection<String> obrigatorios = CollectionUtils.intersection(Arrays.asList(camposObrigatorios.split(",")) , Arrays.asList(camposVisivesis.split(",")));
		configCampo.setCamposExtrasObrigatorios(StringUtils.join(obrigatorios.iterator(), ","));
	}
}