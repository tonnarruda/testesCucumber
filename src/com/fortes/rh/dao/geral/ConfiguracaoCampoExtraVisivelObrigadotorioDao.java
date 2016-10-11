package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;

public interface ConfiguracaoCampoExtraVisivelObrigadotorioDao extends GenericDao<ConfiguracaoCampoExtraVisivelObrigadotorio> {

	public ConfiguracaoCampoExtraVisivelObrigadotorio findByEmpresaId(Long empresaId, String tipoConfiguracao);

	public Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> findCollectionByEmpresaId(Long empresaId, String[] tiposConfiguracao);

	public void removeByEmpresaAndTipoConfig(Long empresaId, String[] tipoConfiguracao);
}
