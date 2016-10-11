package com.fortes.rh.test.business.geral;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraVisivelObrigadotorioDao;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;

public class ConfiguracaoCampoExtraVisivelObrigadotorioManagerTest {

	private  ConfiguracaoCampoExtraVisivelObrigadotorioManagerImpl manager = new ConfiguracaoCampoExtraVisivelObrigadotorioManagerImpl();
	private ConfiguracaoCampoExtraVisivelObrigadotorioDao campoExtraVisivelObrigadotorioDao;
	
	@Before
	public void setUp() throws Exception{
		campoExtraVisivelObrigadotorioDao = mock(ConfiguracaoCampoExtraVisivelObrigadotorioDao.class);
		manager.setDao(campoExtraVisivelObrigadotorioDao);
	}
	
	@Test
	public void testFindByEmpresaId() {
		Long empresaId = 2L;
		String tipoConfiguracao = TipoConfiguracaoCampoExtra.COLABORADOR.getTipo();
		manager.findByEmpresaId(empresaId, tipoConfiguracao);
		verify(campoExtraVisivelObrigadotorioDao, times(1)).findByEmpresaId(empresaId, tipoConfiguracao);
	}
	
	@Test
	public void testFindByEmpresaIdCollection() {
		Long empresaId = 2L;
		String[] tiposConfiguracao = new String[]{TipoConfiguracaoCampoExtra.CANDIDATO.getTipo(), TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo()};
		manager.findByEmpresaId(empresaId, tiposConfiguracao);
		verify(campoExtraVisivelObrigadotorioDao, times(1)).findCollectionByEmpresaId(empresaId, tiposConfiguracao);
	}
	
	@Test
	public void testRemoveByEmpresaAndTipoConfig() {
		Long empresaId = 2L;
		String[] tipoConfiguracao = new String[]{TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()};
		manager.removeByEmpresaAndTipoConfig(empresaId, tipoConfiguracao);
		verify(campoExtraVisivelObrigadotorioDao, times(1)).removeByEmpresaAndTipoConfig(empresaId, tipoConfiguracao);
	}
	
	@Test
	public void TestSaveOrUpdateConfCamposExtrasAtualizaConfiguracaoCampos() {
		Long empresaId = 2L;
		String camposVisiveis = "texto1,";
		String[] tiposConfiguracao = new String[]{TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()};
		
		ConfiguracaoCampoExtraVisivelObrigadotorio campoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresaId, "", TipoConfiguracaoCampoExtra.CANDIDATO.getTipo());
		Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> configCampos = Arrays.asList(campoExtraVisivelObrigadotorio);
		
		when(campoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresaId, tiposConfiguracao)).thenReturn(configCampos);
		
		manager.saveOrUpdateConfCamposExtras(empresaId, camposVisiveis, tiposConfiguracao);
		
		verify(campoExtraVisivelObrigadotorioDao, times(1)).update(campoExtraVisivelObrigadotorio);
		verify(campoExtraVisivelObrigadotorioDao, never()).save(campoExtraVisivelObrigadotorio);
	}
	
	@Test
	public void testAtualizaCamposExtrasSalvaConfiguracao() {
		Long empresaId = 2L;
		String camposVisiveis = "texto1,testo2,";
		String[] tiposConfiguracao = new String[]{TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()};
		ConfiguracaoCampoExtraVisivelObrigadotorio campoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresaId, "texto1,testo2", TipoConfiguracaoCampoExtra.CANDIDATO.getTipo());
		when(campoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresaId, tiposConfiguracao)).thenReturn(new ArrayList<ConfiguracaoCampoExtraVisivelObrigadotorio>());
		manager.saveOrUpdateConfCamposExtras(empresaId, camposVisiveis, tiposConfiguracao);
		verify(campoExtraVisivelObrigadotorioDao, never()).update(campoExtraVisivelObrigadotorio);
		verify(campoExtraVisivelObrigadotorioDao, times(1)).save(any(ConfiguracaoCampoExtraVisivelObrigadotorio.class));
	}
}
