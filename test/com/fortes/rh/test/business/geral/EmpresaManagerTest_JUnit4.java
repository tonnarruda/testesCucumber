package com.fortes.rh.test.business.geral;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
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
import com.fortes.rh.business.geral.EmpresaManagerImpl;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.dicionario.TipoEntidade;
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

    @Before
	public void setUp() throws Exception{
    	empresaDao = mock(EmpresaDao.class);
        empresaManager.setDao(empresaDao);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        empresaManager.setEstabelecimentoManager(estabelecimentoManager);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        empresaManager.setAreaOrganizacionalManager(areaOrganizacionalManager);

        faixaSalarialManager = mock(FaixaSalarialManager.class);
        empresaManager.setFaixaSalarialManager(faixaSalarialManager);

        indiceManager = mock(IndiceManager.class);
        empresaManager.setIndiceManager(indiceManager);

        OcorrenciaManager = mock(OcorrenciaManager.class);
        empresaManager.setOcorrenciaManager(OcorrenciaManager);

        cidadeManager = mock(CidadeManager.class);
        empresaManager.setCidadeManager(cidadeManager);
        
        conhecimentoManager = mock(ConhecimentoManager.class);
        empresaManager.setConhecimentoManager(conhecimentoManager);
        
        habilidadeManager = mock(HabilidadeManager.class);
        empresaManager.setHabilidadeManager(habilidadeManager);
        
        atitudeManager = mock(AtitudeManager.class);
        empresaManager.setAtitudeManager(atitudeManager);
        
        areaInteresseManager = mock(AreaInteresseManager.class);
        empresaManager.setAreaInteresseManager(areaInteresseManager);

        ocorrenciaManager = mock(OcorrenciaManager.class);
        empresaManager.setOcorrenciaManager(ocorrenciaManager);
        
        motivoDemissaoManager = mock(MotivoDemissaoManager.class);
        empresaManager.setMotivoDemissaoManager(motivoDemissaoManager);
        
        riscoManager = mock(RiscoManager.class);
        empresaManager.setRiscoManager(riscoManager);
        
        ambienteManager = mock(AmbienteManager.class);
        MockSpringUtilJUnit4.mocks.put("ambienteManager", ambienteManager);
        
        turmaManager = mock(TurmaManager.class);
        MockSpringUtilJUnit4.mocks.put("turmaManager", turmaManager);
        
        epiManager = mock(EpiManager.class);
        MockSpringUtilJUnit4.mocks.put("epiManager", epiManager);
        
        colaboradorManager = mock(ColaboradorManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorManager", colaboradorManager);

        cargoManager = mock(CargoManager.class);
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

}