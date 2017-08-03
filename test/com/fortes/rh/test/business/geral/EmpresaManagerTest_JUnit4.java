package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManager;
import com.fortes.rh.business.geral.EmpresaManagerImpl;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.dicionario.TipoEntidade;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;

public class EmpresaManagerTest_JUnit4
{
	private EmpresaManagerImpl empresaManager = new EmpresaManagerImpl();
	private EmpresaDao empresaDao;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private OcorrenciaManager OcorrenciaManager;
	private CidadeManager cidadeManager;
	private ConhecimentoManager conhecimentoManager;
	private HabilidadeManager habilidadeManager;
	private AtitudeManager atitudeManager;
	private AreaInteresseManager areaInteresseManager;
	private CargoManager cargoManager;
	private OcorrenciaManager ocorrenciaManager;
	private EpiManager epiManager;
	private TurmaManager turmaManager;
	private MotivoDemissaoManager motivoDemissaoManager;
	private RiscoManager riscoManager;
	private AmbienteManager ambienteManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;
	
    @Before
	public void setUp() throws Exception{
    	empresaDao = mock(EmpresaDao.class);
    	estabelecimentoManager = mock(EstabelecimentoManager.class);
    	areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
    	faixaSalarialManager = mock(FaixaSalarialManager.class);
    	indiceManager = mock(IndiceManager.class);
    	OcorrenciaManager = mock(OcorrenciaManager.class);
    	cidadeManager = mock(CidadeManager.class);
    	conhecimentoManager = mock(ConhecimentoManager.class);
    	habilidadeManager = mock(HabilidadeManager.class);
    	atitudeManager = mock(AtitudeManager.class);
    	areaInteresseManager = mock(AreaInteresseManager.class);
    	ocorrenciaManager = mock(OcorrenciaManager.class);
    	motivoDemissaoManager = mock(MotivoDemissaoManager.class);
    	riscoManager = mock(RiscoManager.class);
    	ambienteManager = mock(AmbienteManager.class);
    	turmaManager = mock(TurmaManager.class);
    	epiManager = mock(EpiManager.class);
    	colaboradorManager = mock(ColaboradorManager.class);
    	cargoManager = mock(CargoManager.class);
    	configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
    	configuracaoCampoExtraVisivelObrigadotorioManager = mock(ConfiguracaoCampoExtraVisivelObrigadotorioManager.class);
    	
    	empresaManager.setDao(empresaDao);
        empresaManager.setEstabelecimentoManager(estabelecimentoManager);
        empresaManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
        empresaManager.setFaixaSalarialManager(faixaSalarialManager);
        empresaManager.setIndiceManager(indiceManager);
        empresaManager.setOcorrenciaManager(OcorrenciaManager);
        empresaManager.setCidadeManager(cidadeManager);
        empresaManager.setConhecimentoManager(conhecimentoManager);
        empresaManager.setHabilidadeManager(habilidadeManager);
        empresaManager.setAtitudeManager(atitudeManager);
        empresaManager.setAreaInteresseManager(areaInteresseManager);
        empresaManager.setOcorrenciaManager(ocorrenciaManager);
        empresaManager.setMotivoDemissaoManager(motivoDemissaoManager);
        empresaManager.setRiscoManager(riscoManager);
        empresaManager.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
        empresaManager.setConfiguracaoCampoExtraVisivelObrigadotorioManager(configuracaoCampoExtraVisivelObrigadotorioManager);
        MockSpringUtilJUnit4.mocks.put("ambienteManager", ambienteManager);
        MockSpringUtilJUnit4.mocks.put("turmaManager", turmaManager);
        MockSpringUtilJUnit4.mocks.put("epiManager", epiManager);
        MockSpringUtilJUnit4.mocks.put("colaboradorManager", colaboradorManager);
        MockSpringUtilJUnit4.mocks.put("cargoManager", cargoManager);
        
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
	}
    
    @Test
    public void testSincronizar(){
    	
    	String[] cadastrosCheck = new String[]{TipoEntidade.AREAS,TipoEntidade.CONHECIMENTOS,TipoEntidade.HABILIDADES,TipoEntidade.ATITUDES, TipoEntidade.AREAS_INTERESSE, TipoEntidade.CARGOS,
    			TipoEntidade.TIPOS_OCORRENCIA, TipoEntidade.EPIS, TipoEntidade.CURSOSETURMAS, TipoEntidade.MOTIVOS_DESLIGAMENTO, TipoEntidade.RISCOS, TipoEntidade.AMBIENTE};
    	
    	Exception ex = null;
    	try {
			empresaManager.sincronizaEntidades(1L, 2L, cadastrosCheck, new String[]{});
		} catch (Exception e) {
			ex = e;
		}
    	
    	assertNull(ex);
    } 
    
    @Test
    public void testPopulaCadastrosCheckBox()
    {
    	assertEquals(12, empresaManager.populaCadastrosCheckBox().size());
    }
    
    @Test
    public void testAtualizaCamposExtras() 
	{
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	boolean habilitaCampoExtraCandidato = true;
    	boolean habilitaCampoExtraColaborador = true;
    	boolean habilitaCampoExtraAtualizarMeusDados = true;
    	
    	ConfiguracaoCampoExtra confText1 = ConfiguracaoCampoExtraFactory.getEntity(1L, empresa, "Campo de Texto 1", "texto1", true, true);
    	confText1.setEmpresa(empresa);
    	ConfiguracaoCampoExtra confData1 = ConfiguracaoCampoExtraFactory.getEntity(2L, empresa, "Campo de Data 1", "data1", true, false);
    	confData1.setEmpresa(empresa);
    	String camposVisivesisColaborador = "texto1,data1,";
    	String camposVisivesisCandidato = "texto1,";
    	
    	Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = Arrays.asList(confText1, confData1); 
    	empresaManager.atualizaCamposExtras(configuracaoCampoExtras, empresa, habilitaCampoExtraColaborador, habilitaCampoExtraCandidato, habilitaCampoExtraAtualizarMeusDados);
    	
    	verify(configuracaoCampoExtraManager, never()).removeAllNotModelo();
    	verify(empresaDao, times(1)).updateCampoExtra(empresa.getId(), habilitaCampoExtraColaborador, habilitaCampoExtraCandidato, habilitaCampoExtraAtualizarMeusDados);
    	verify(configuracaoCampoExtraVisivelObrigadotorioManager).saveOrUpdateConfCamposExtras(empresa.getId(), camposVisivesisColaborador, new String[]{TipoConfiguracaoCampoExtra.COLABORADOR.getTipo()});
    	verify(configuracaoCampoExtraVisivelObrigadotorioManager).saveOrUpdateConfCamposExtras(empresa.getId(), camposVisivesisCandidato, new String[]{TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()});
	}
    
    @Test
    public void testAtualizaCamposExtrasTodasAsEmpresas() 
	{
    	Empresa empresa = EmpresaFactory.getEmpresa(null);
    	boolean habilitaCampoExtraCandidato = true;
    	boolean habilitaCampoExtraColaborador = false;
    	boolean habilitaCampoExtraAtualizarMeusDados = false;
    			
    	ConfiguracaoCampoExtra confText1 = ConfiguracaoCampoExtraFactory.getEntity(1L, empresa, "Campo de Texto 1", "texto1", true, true);
    	confText1.setEmpresa(empresa);
    	ConfiguracaoCampoExtra confData1 = ConfiguracaoCampoExtraFactory.getEntity(2L, empresa, "Campo de Data 1", "data1", true, false);
    	confData1.setEmpresa(empresa);
    	String camposVisivesisColaborador = "texto1,data1,";
    	String camposVisivesisCandidato = "texto1,";
    	
    	Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = Arrays.asList(confText1, confData1); 
    	Collection<Empresa> empresas = Arrays.asList(empresa);
    	
    	when(empresaDao.findTodasEmpresas()).thenReturn(empresas);
    	
    	empresaManager.atualizaCamposExtras(configuracaoCampoExtras, empresa, habilitaCampoExtraColaborador, habilitaCampoExtraCandidato, habilitaCampoExtraAtualizarMeusDados);
    	
    	verify(configuracaoCampoExtraManager, times(1)).removeAllNotModelo();
    	verify(empresaDao, times(1)).updateCampoExtra(empresa.getId(), habilitaCampoExtraColaborador, habilitaCampoExtraCandidato, habilitaCampoExtraAtualizarMeusDados);
    	verify(configuracaoCampoExtraVisivelObrigadotorioManager).saveOrUpdateConfCamposExtras(empresa.getId(), camposVisivesisColaborador, new String[]{TipoConfiguracaoCampoExtra.COLABORADOR.getTipo()});
    	verify(configuracaoCampoExtraVisivelObrigadotorioManager).saveOrUpdateConfCamposExtras(empresa.getId(), camposVisivesisCandidato, new String[]{TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()});
	}
}