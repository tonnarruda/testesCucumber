package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoCurriculoManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.CandidatoEditAction;
import com.opensymphony.xwork.ActionContext;

public class CandidatoEditActionTest_JUnit4
{
	private CandidatoEditAction action = new CandidatoEditAction();
	private CandidatoManager manager;
	private CandidatoCurriculoManager candidatoCurriculoManager;
	private EmpresaManager empresaManager;
	private EstadoManager estadoManager;
	private CargoManager cargoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private CidadeManager cidadeManager;
	private AreaInteresseManager areaInteresseManager;
	private ConhecimentoManager conhecimentoManager;
	private FormacaoManager formacaoManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private ExperienciaManager experienciaManager;
	private BairroManager bairroManager;
	private ComoFicouSabendoVagaManager comoFicouSabendoVagaManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;

	@Before
    public void setUp() throws Exception
    {
        manager = mock(CandidatoManager.class);
        candidatoCurriculoManager = mock(CandidatoCurriculoManager.class);
        empresaManager = mock(EmpresaManager.class);
        estadoManager = mock(EstadoManager.class);
        cargoManager = mock(CargoManager.class);
        bairroManager = mock(BairroManager.class);
        comoFicouSabendoVagaManager = mock(ComoFicouSabendoVagaManager.class);
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        cidadeManager = mock(CidadeManager.class);
        areaInteresseManager = mock(AreaInteresseManager.class);
        conhecimentoManager = mock(ConhecimentoManager.class);
        formacaoManager = mock(FormacaoManager.class);
        candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
        experienciaManager = mock(ExperienciaManager.class);
        configuracaoCampoExtraVisivelObrigadotorioManager = mock(ConfiguracaoCampoExtraVisivelObrigadotorioManager.class);
        configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);

        action.setCandidatoManager(manager);
        action.setCandidatoCurriculoManager(candidatoCurriculoManager);
        action.setEmpresaManager(empresaManager);
        action.setEstadoManager( estadoManager);
        action.setCargoManager(cargoManager);
        action.setBairroManager(bairroManager);
        action.setComoFicouSabendoVagaManager(comoFicouSabendoVagaManager);
        action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        action.setCidadeManager(cidadeManager);
        action.setAreaInteresseManager(areaInteresseManager);
        action.setConhecimentoManager(conhecimentoManager);
        action.setFormacaoManager(formacaoManager);
        action.setCandidatoIdiomaManager(candidatoIdiomaManager);
        action.setExperienciaManager(experienciaManager);
        action.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
        action.setConfiguracaoCampoExtraVisivelObrigadotorioManager(configuracaoCampoExtraVisivelObrigadotorioManager);

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(ActionContext.class, MockActionContext.class);

    }
    
	@Test
    public void testPrepareInsertPeloModuloExterno() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setCampoExtraCandidato(true);
    	Collection<Empresa> empresas = Arrays.asList(empresa);
    	ConfiguracaoCampoExtraVisivelObrigadotorio configCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "texto1", TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo()); 
    
    	Candidato candidato = CandidatoFactory.getCandidato();
    	action.setCandidato(candidato);

    	action.setModuloExterno(true);
    	action.setEmpresaId(empresa.getId());
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	
    	when(comoFicouSabendoVagaManager.findAllSemOutros()).thenReturn(new ArrayList<ComoFicouSabendoVaga>());
    	when(comoFicouSabendoVagaManager.findById(1L)).thenReturn(new ComoFicouSabendoVaga(1L,"Outros"));
    	when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(parametrosDoSistema);
    	when(empresaManager.findById(1L)).thenReturn(empresa);
    	when(cargoManager.findAllSelect("nomeMercado", true, Cargo.ATIVO, empresa.getId())).thenReturn(new ArrayList<Cargo>());
    	when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo())).thenReturn(configCampoExtraVisivelObrigadotorio);
    	when(configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, empresa.getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());
    	when(estadoManager.findAll(new String[]{"sigla"})).thenReturn(new ArrayList<Estado>());
    	when(configuracaoCampoExtraManager.findAllNomes()).thenReturn(new String[]{});
    	when(areaInteresseManager.findAllSelect(empresa.getId())).thenReturn(new ArrayList<AreaInteresse>());
		when(conhecimentoManager.findAllSelect(empresa.getId())).thenReturn(new ArrayList<Conhecimento>());
		when(empresaManager.findToList(new String[]{"maxCandidataCargo"}, new String[]{"maxCandidataCargo"}, new String[]{"id"}, new Object[]{empresa.getId()})).thenReturn(empresas);
    	assertEquals("success", action.prepareInsert());
    }
	
	@Test
    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setCampoExtraCandidato(true);
    	Collection<Empresa> empresas = Arrays.asList(empresa);
    	ConfiguracaoCampoExtraVisivelObrigadotorio configCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "texto1", TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()); 
    
    	Candidato candidato = CandidatoFactory.getCandidato();
    	action.setCandidato(candidato);

    	action.setModuloExterno(false);
    	action.setEmpresaId(empresa.getId());
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	
    	when(comoFicouSabendoVagaManager.findAllSemOutros()).thenReturn(new ArrayList<ComoFicouSabendoVaga>());
    	when(comoFicouSabendoVagaManager.findById(1L)).thenReturn(new ComoFicouSabendoVaga(1L,"Outros"));
    	when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(parametrosDoSistema);
    	when(empresaManager.findById(1L)).thenReturn(empresa);
    	when(cargoManager.findAllSelect("nomeMercado", true, Cargo.ATIVO, empresa.getId())).thenReturn(new ArrayList<Cargo>());
    	when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo())).thenReturn(configCampoExtraVisivelObrigadotorio);
    	when(configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, empresa.getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());
    	when(estadoManager.findAll(new String[]{"sigla"})).thenReturn(new ArrayList<Estado>());
    	when(configuracaoCampoExtraManager.findAllNomes()).thenReturn(new String[]{});
    	when(areaInteresseManager.findAllSelect(empresa.getId())).thenReturn(new ArrayList<AreaInteresse>());
		when(conhecimentoManager.findAllSelect(empresa.getId())).thenReturn(new ArrayList<Conhecimento>());
		when(empresaManager.findToList(new String[]{"maxCandidataCargo"}, new String[]{"maxCandidataCargo"}, new String[]{"id"}, new Object[]{empresa.getId()})).thenReturn(empresas);
    	assertEquals("success", action.prepareInsert());
    }

	@Test
    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setEmpresa(empresa);
    	action.setCandidato(candidato);
    	
    	Collection<Empresa> empresas = Arrays.asList(empresa);
    	ConfiguracaoCampoExtraVisivelObrigadotorio configCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "texto1", TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()); 
        
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	
    	when(comoFicouSabendoVagaManager.findAllSemOutros()).thenReturn(new ArrayList<ComoFicouSabendoVaga>());
    	when(comoFicouSabendoVagaManager.findById(1L)).thenReturn(new ComoFicouSabendoVaga());
    	when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(parametrosDoSistema);
    	when(empresaManager.findById(1L)).thenReturn(empresa);
    	when(cargoManager.findAllSelect("nomeMercado", true, Cargo.ATIVO, empresa.getId())).thenReturn(new ArrayList<Cargo>());
    	when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo())).thenReturn(configCampoExtraVisivelObrigadotorio);
    	when(configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, empresa.getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());
    	when(estadoManager.findAll(new String[]{"sigla"})).thenReturn(new ArrayList<Estado>());
    	when(configuracaoCampoExtraManager.findAllNomes()).thenReturn(new String[]{});
    	when(areaInteresseManager.findAllSelect(empresa.getId())).thenReturn(new ArrayList<AreaInteresse>());
		when(conhecimentoManager.findAllSelect(empresa.getId())).thenReturn(new ArrayList<Conhecimento>());
		when(empresaManager.findToList(new String[]{"maxCandidataCargo"}, new String[]{"maxCandidataCargo"}, new String[]{"id"}, new Object[]{empresa.getId()})).thenReturn(empresas);
		when(manager.findByIdProjection(eq(candidato.getId()))).thenReturn(candidato);
		when(formacaoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Experiencia>());
    	assertEquals("success", action.prepareUpdate());
    }
}