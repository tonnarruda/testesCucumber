package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

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
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.CandidatoEditAction;
import com.opensymphony.xwork.ActionContext;

public class CandidatoEditActionTest extends MockObjectTestCase
{
	private CandidatoEditAction action;
	private Mock manager;
	private Mock candidatoCurriculoManager;
	private Mock empresaManager;
	private Mock estadoManager;
	private Mock cargoManager;
	private Mock parametrosDoSistemaManager;
	private Mock configuracaoCampoExtraManager;
	private Mock cidadeManager;
	private Mock areaInteresseManager;
	private Mock conhecimentoManager;
	private Mock formacaoManager;
	private Mock candidatoIdiomaManager;
	private Mock experienciaManager;
	private Mock bairroManager;
	private Mock comoFicouSabendoVagaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CandidatoEditAction();
        manager = new Mock(CandidatoManager.class);
        candidatoCurriculoManager = new Mock(CandidatoCurriculoManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        estadoManager = new Mock(EstadoManager.class);
        cargoManager = new Mock(CargoManager.class);
        bairroManager = new Mock(BairroManager.class);
        comoFicouSabendoVagaManager = new Mock(ComoFicouSabendoVagaManager.class);

        action.setCandidatoManager((CandidatoManager) manager.proxy());
        action.setCandidatoCurriculoManager((CandidatoCurriculoManager) candidatoCurriculoManager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setEstadoManager((EstadoManager) estadoManager.proxy());
        action.setCargoManager((CargoManager) cargoManager.proxy());
        action.setBairroManager((BairroManager) bairroManager.proxy());
        action.setComoFicouSabendoVagaManager((ComoFicouSabendoVagaManager)comoFicouSabendoVagaManager.proxy());
        
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
        action.setConfiguracaoCampoExtraManager((ConfiguracaoCampoExtraManager) configuracaoCampoExtraManager.proxy());
        
        cidadeManager = mock(CidadeManager.class);
        action.setCidadeManager((CidadeManager) cidadeManager.proxy());
        
        areaInteresseManager = mock(AreaInteresseManager.class);
        action.setAreaInteresseManager((AreaInteresseManager) areaInteresseManager.proxy());
        
        conhecimentoManager = mock(ConhecimentoManager.class);
        action.setConhecimentoManager((ConhecimentoManager) conhecimentoManager.proxy());
        
        formacaoManager = mock(FormacaoManager.class);
        action.setFormacaoManager((FormacaoManager) formacaoManager.proxy());
        
        candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
        action.setCandidatoIdiomaManager((CandidatoIdiomaManager) candidatoIdiomaManager.proxy());
        
        experienciaManager = mock(ExperienciaManager.class);
        action.setExperienciaManager((ExperienciaManager) experienciaManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(ActionContext.class, MockActionContext.class);

    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }
    
    public void testPrepareInsertCurriculoTexto() throws Exception
    {
    	prepareMocksInsertCurriculo();
    	
    	assertEquals(action.prepareInsertCurriculoTexto(), "success");
    	
    	assertNotNull(action.getBairros());
    	assertNotNull(action.getUfs());
    	assertEquals(5, action.getMaxCandidataCargo());
    }

    public void testInsertCurriculoTextoException() throws Exception
    {
    	prepareMocksInsertCurriculo();
    	
    	action.setCandidatoManager(null);
    	assertEquals("input" , action.insertCurriculoTexto());
    }

    public void testInsertCurriculoTextoExceptionCurriculo() throws Exception
    {
    	prepareMocksInsertCurriculo();
    	
    	action.setCandidato(null);
    	
    	assertEquals("input" , action.insertCurriculoTexto());
    }

    public void testInsertCurriculoTexto() throws Exception
    {
    	Candidato candidato = prepareMocksInsertCurriculo();
    	manager.expects(once()).method("verifyCPF").withAnyArguments().will(returnValue(null));
    	manager.expects(once()).method("save").with(ANYTHING).will(returnValue(candidato));
    	
    	assertEquals("success" , action.insertCurriculoTexto());
    }

	private Candidato prepareMocksInsertCurriculo() {
		Candidato candidato = CandidatoFactory.getCandidato(1L);
    	candidato.setOcrTexto("Teste");
    	candidato.setNome("francisco");

    	action.setCandidato(candidato);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setMaxCandidataCargo(5);
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);
    	
    	bairroManager.expects(once()).method("getArrayBairros").will(returnValue(new String()));
    	empresaManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(empresas));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	
    	return candidato;
	}

    public void testPrepareInsertCurriculo() throws Exception
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);
    	action.setCandidato(candidato);

    	manager.expects(once()).method("findByCandidatoId").with(eq(candidato.getId())).will(returnValue(candidato));

    	assertEquals(action.prepareInsertCurriculo(), "success");
    }

    public void testPrepareInsertCurriculoPlus() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(1L);
    	action.setCandidato(candidato);

    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	empresa.setMaxCandidataCargo(5);
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);

    	manager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new Candidato()));
    	manager.expects(once()).method("findCargosByCandidatoId").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));

    	empresaManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(empresas));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	assertEquals("success", action.prepareInsertCurriculoPlus());
    }

    public void testInsertCurriculo() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(null);
    	candidato.getEndereco().setCidade(new Cidade());
    	candidato.getEndereco().setUf(new Estado());
    	action.setCandidato(candidato);

    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	empresa.setMaxCandidataCargo(5);
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);

    	//manager.expects(once()).method("validaQtdCadastros").isVoid();
    	cargoManager.expects(once()).method("populaCargos").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	cargoManager.expects(once()).method("populaCargos").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	manager.expects(once()).method("saveCandidatoCurriculo").with(ANYTHING,ANYTHING,ANYTHING);
    	manager.expects(once()).method("verifyCPF").withAnyArguments().will(returnValue(null));
    	assertEquals("success", action.insertCurriculo());

    	candidato.setId(1L);
    	action.setCandidato(candidato);
    	//manager.expects(once()).method("validaQtdCadastros").isVoid();
    	cargoManager.expects(once()).method("populaCargos").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	manager.expects(once()).method("saveCandidatoCurriculo").with(ANYTHING,ANYTHING,ANYTHING).will(throwException(new Exception()));
    	manager.expects(once()).method("verifyCPF").withAnyArguments().will(returnValue(null));
    	assertEquals("input", action.insertCurriculo());

    	candidato.setId(null);
    	action.setCandidato(candidato);
    	//manager.expects(once()).method("validaQtdCadastros").isVoid();
    	cargoManager.expects(once()).method("populaCargos").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	empresaManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(empresas));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	manager.expects(once()).method("verifyCPF").withAnyArguments().will(returnValue(null));
    	
    	assertEquals("input",action.insertCurriculo());
    }

    public void testInsertCurriculoCPFExiste() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(null);
    	candidato.setPessoal(new Pessoal());
    	candidato.getPessoal().setCpf("123");
    	action.setCandidato(candidato);

    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	empresa.setMaxCandidataCargo(5);
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);

    	empresaManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(empresas));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	cargoManager.expects(once()).method("populaCargos").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	manager.expects(once()).method("verifyCPF").withAnyArguments().will(returnValue(null));
    	
    	assertEquals("input", action.insertCurriculo());
    }
    
    public void testPrepareUpdateExamePalografico() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(1L);
    	candidato.setPessoal(new Pessoal());
    	candidato.getPessoal().setCpf("123");
    	candidato.setExamePalografico("///_|/-\\\\-||||");
    	action.setCandidato(candidato);
    	
    	manager.expects(once()).method("findByIdProjection").with(eq(candidato.getId())).will(returnValue(candidato));

    	action.prepareUpdateExamePalografico();
    	    	   	
    	assertEquals(candidato.getExamePalografico(), action.getCandidato().getExamePalografico());
    }

    public void testPrepareUpdateCurriculo() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(1L);
    	candidato.setOcrTexto("ocr");
    	action.setCandidato(candidato);

    	manager.expects(once()).method("findByIdProjection").with(eq(candidato.getId())).will(returnValue(candidato));
    	manager.expects(once()).method("getOcrTextoById").with(eq(candidato.getId())).will(returnValue("ocr"));
    	candidatoCurriculoManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<CandidatoCurriculo>()));

    	assertEquals("success", action.prepareUpdateCurriculo());
    }

    public void testUpdateCurriculo() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(1L);
    	candidato.setOcrTexto("ocr");
    	action.setCandidato(candidato);

    	manager.expects(once()).method("atualizaTextoOcr").with(eq(candidato));
    	manager.expects(once()).method("verifyCPF").withAnyArguments().will(returnValue(null));
    	
    	assertEquals("success", action.updateCurriculo());

    	action.setCandidato(null);
    	assertEquals("input", action.updateCurriculo());
    }
    
    public void testPrepareInsertPeloModuloExterno() throws Exception
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	action.setCandidato(candidato);
    	
    	Collection<Empresa> empresas = Arrays.asList(EmpresaFactory.getEmpresa(1L));
    	
    	action.setModuloExterno(true);
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	
    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	parametrosDoSistemaManager.expects(once()).method("ajustaCamposExtras").with(ANYTHING, ANYTHING);
    	configuracaoCampoExtraManager.expects(once()).method("findAllNomes").will(returnValue(new String[]{}));
    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	empresaManager.expects(once()).method("findToList").will(returnValue(empresas));
    	empresaManager.expects(once()).method("findById").will(returnValue(EmpresaFactory.getEmpresa(1L)));
    	cargoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Cargo>()));
    	areaInteresseManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<AreaInteresse>()));
    	conhecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Conhecimento>()));
    	comoFicouSabendoVagaManager.expects(once()).method("findAllSemOutros").will(returnValue(new ArrayList<ComoFicouSabendoVagaManager>()));
    	comoFicouSabendoVagaManager.expects(once()).method("findById").will(returnValue(new ComoFicouSabendoVaga(1L,"Outros")));
    	
    	assertEquals("success", action.prepareInsert());
    }

    public void testInsertCurriculoException() throws Exception
    {
    	Collection<Empresa> empresas = Arrays.asList(EmpresaFactory.getEmpresa(1L));
    	
    	action.setModuloExterno(false);
    	action.setUfs(new ArrayList<Estado>());
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	
    	cargoManager.expects(once()).method("populaCargos").will(returnValue(new ArrayList<Cargo>()));
    	cargoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Cargo>()));
    	estadoManager.expects(once()).method("findAll");
    	empresaManager.expects(once()).method("findToList").will(returnValue(empresas));
    	
    	action.setCandidatoManager(null); 
    	
    	assertEquals("input", action.insertCurriculo());
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setEmpresa(empresa);
    	action.setCandidato(candidato);
    	
    	Collection<Empresa> empresas = Arrays.asList(empresa);
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	
    	estadoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<Estado>()));
    	manager.expects(once()).method("findByIdProjection").with(eq(candidato.getId())).will(returnValue(candidato));
    	manager.expects(once()).method("getFoto").with(eq(candidato.getId()));
    	manager.expects(once()).method("findConhecimentosByCandidatoId").with(eq(candidato.getId()));
    	manager.expects(once()).method("findCargosByCandidatoId").with(eq(candidato.getId()));
    	manager.expects(once()).method("findAreaInteressesByCandidatoId").with(eq(candidato.getId()));
    	empresaManager.expects(once()).method("findToList").will(returnValue(empresas));
    	cargoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Cargo>()));
    	areaInteresseManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<AreaInteresse>()));
    	conhecimentoManager.expects(atLeastOnce()).method("findAllSelect").with(eq(new Long[]{1L})).will(returnValue(new ArrayList<Conhecimento>()));
    	formacaoManager.expects(once()).method("findByCandidato").will(returnValue(new ArrayList<Formacao>()));
    	candidatoIdiomaManager.expects(once()).method("findByCandidato").will(returnValue(new ArrayList<CandidatoIdioma>()));
    	experienciaManager.expects(once()).method("findByCandidato").will(returnValue(new ArrayList<Experiencia>()));
    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	comoFicouSabendoVagaManager.expects(once()).method("findAllSemOutros").will(returnValue(new ArrayList<ComoFicouSabendoVagaManager>()));
    	comoFicouSabendoVagaManager.expects(once()).method("findById").will(returnValue(new ComoFicouSabendoVaga()));    	
    	parametrosDoSistemaManager.expects(once()).method("ajustaCamposExtras").with(ANYTHING, ANYTHING);
    	configuracaoCampoExtraManager.expects(once()).method("findAllNomes").will(returnValue(new String[]{}));

    	
    	assertEquals("success", action.prepareUpdate());
    }
    
    public void testGets() throws Exception
    {
		action.getCandidato();
    }

}