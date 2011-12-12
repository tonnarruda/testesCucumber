package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.model.type.File;
import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManagerImpl;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.webwork.ServletActionContext;

public class EmpresaManagerTest extends MockObjectTestCase
{
	private EmpresaManagerImpl empresaManager = new EmpresaManagerImpl();
	private Mock empresaDao = null;
	private Mock colaboradorManager;
	private Mock estabelecimentoManager;
	private Mock areaOrganizacionalManager;
	private Mock faixaSalarialManager;
	private Mock indiceManager;
	private Mock OcorrenciaManager;
	private Mock cidadeManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        empresaDao = new Mock(EmpresaDao.class);
        empresaManager.setDao((EmpresaDao) empresaDao.proxy());
        
        colaboradorManager = new Mock(ColaboradorManager.class);
        
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        empresaManager.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        empresaManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        empresaManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());

        indiceManager = new Mock(IndiceManager.class);
        empresaManager.setIndiceManager((IndiceManager) indiceManager.proxy());

        OcorrenciaManager = new Mock(OcorrenciaManager.class);
        empresaManager.setOcorrenciaManager((OcorrenciaManager) OcorrenciaManager.proxy());

        cidadeManager = new Mock(CidadeManager.class);
        empresaManager.setCidadeManager((CidadeManager) cidadeManager.proxy());
        
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

    public void testAjustaCombo()
    {
    	assertNull(empresaManager.ajustaCombo(-1L, null));
    	assertNull(empresaManager.ajustaCombo(null, null));
    	assertEquals(new Long(22), empresaManager.ajustaCombo(22L, null));
    }

    public void testSaveLogo()
    {
    	File logo = new File();
    	logo.setName("teste.jpg");

    	String retorno = "";
		retorno = empresaManager.saveLogo(logo, null);

    	assertNotNull(retorno);
    }

    public void testSetLogo() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setLogoUrl("");

    	File logo = new File();
    	logo.setName("teste.jpg");
    	empresa = empresaManager.setLogo(empresa, logo, null, null, null);

    	assertNotNull(empresa.getLogoUrl());
    }
    
    public void testGetEmpresasNaoListadas()
    {
    	Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
    	Collection<Empresa> empresasExibidas = new ArrayList<Empresa>();
    	
    	Empresa vega = EmpresaFactory.getEmpresa(1L);
    	vega.setNome("vega");
    	Empresa papi = EmpresaFactory.getEmpresa(2L);
    	papi.setNome("papi");
    	Empresa casique = EmpresaFactory.getEmpresa(3L);
    	Empresa fortes = EmpresaFactory.getEmpresa(4L);

    	UsuarioEmpresa joaoVega = UsuarioEmpresaFactory.getEntity(1L);
    	joaoVega.setEmpresa(vega);
    	UsuarioEmpresa joaoPapi = UsuarioEmpresaFactory.getEntity(2L);
    	joaoPapi.setEmpresa(papi);
    	
    	usuarioEmpresas.add(joaoPapi);
    	    	
    	empresasExibidas.add(fortes);
    	assertEquals("papi", empresaManager.getEmpresasNaoListadas(usuarioEmpresas, empresasExibidas));
    	
    	empresasExibidas.add(casique);
    	assertEquals("papi", empresaManager.getEmpresasNaoListadas(usuarioEmpresas, empresasExibidas));

    	usuarioEmpresas.add(joaoVega);
    	assertEquals("papi,vega", empresaManager.getEmpresasNaoListadas(usuarioEmpresas, empresasExibidas));
 
    	empresasExibidas.add(vega);
    	empresasExibidas.add(papi);
    	assertEquals(null, empresaManager.getEmpresasNaoListadas(usuarioEmpresas, empresasExibidas));

    	usuarioEmpresas.clear();
    	assertEquals(null, empresaManager.getEmpresasNaoListadas(usuarioEmpresas, empresasExibidas));
    }

    public void testFindExibirSalarioById() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	empresaDao.expects(once()).method("findExibirSalarioById").with(eq(empresa.getId())).will(returnValue(false));

    	boolean exibir = empresaManager.findExibirSalarioById(empresa.getId());

    	assertFalse(exibir);
    }
    
    public void testSelecionaEmpresa() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(null);
    	
    	empresaDao.expects(once()).method("findByUsuarioPermissao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	assertEquals(0, empresaManager.selecionaEmpresa(empresa, 2L, "ROLE_REL_ANIVERSARIANTES").length);
    	
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);
    	empresaDao.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(empresas));
    	assertEquals(1, empresaManager.selecionaEmpresa(empresa, 1L, "ROLE_REL_ANIVERSARIANTES").length);
    	
    	empresa.setId(1L);
    	assertEquals(1, empresaManager.selecionaEmpresa(empresa, 2L, "ROLE_REL_ANIVERSARIANTES").length);

    }
    
    public void testCriarEmpresa() throws Exception
    {
    	TEmpresa empresaAC = new TEmpresa();
    	empresaAC.setCodigoAC("0011");
    	empresaAC.setNome("nome");
    	empresaAC.setRazaoSocial("razaoSocial");
    	
    	empresaDao.expects(once()).method("save").with(ANYTHING);
    	
    	assertEquals(true, empresaManager.criarEmpresa(empresaAC));
    }
    
    public void testCriarEmpresaException() throws Exception
    {
    	TEmpresa empresaAC = new TEmpresa();
    	empresaAC.setCodigoAC("0011");
    	empresaAC.setNome("nome");
    	empresaAC.setRazaoSocial("razaoSocial");
    	
    	empresaDao.expects(once()).method("save").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null, ""))));
    	
    	assertEquals(false, empresaManager.criarEmpresa(empresaAC));
    }
    
    public void testVerifyExistsCnpjSemCnpj() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setCnpj("123456");
    	
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresaDao.expects(once()).method("verifyExistsCnpj").with(eq(empresa.getCnpj())).will(returnValue(empresas));
    	
    	assertEquals(false, empresaManager.verifyExistsCnpj(empresa.getId(), empresa.getCnpj()));
    }
    
    public void testVerifyExistsCnpjPropriaEdicao() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setCnpj("123456");
    	
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);
    	
    	empresaDao.expects(once()).method("verifyExistsCnpj").with(eq(empresa.getCnpj())).will(returnValue(empresas));
    	
    	assertEquals(false, empresaManager.verifyExistsCnpj(empresa.getId(), empresa.getCnpj()));
    }
    
    public void testFindCidade()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	empresaDao.expects(once()).method("findCidade").with(eq(empresa.getId())).will(returnValue("Palmacia"));
    	assertEquals("Palmacia", empresaManager.findCidade(empresa.getId()));

    	empresaDao.expects(once()).method("findCidade").with(eq(empresa.getId())).will(returnValue(null));
    	assertEquals("", empresaManager.findCidade(empresa.getId()));
    }

    public void testVerifyExistsCnpjJaCadastrado() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setCnpj("123456");
    	
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);
    	
    	empresaDao.expects(once()).method("verifyExistsCnpj").with(eq(empresa.getCnpj())).will(returnValue(empresas));
    	
    	Empresa empresaJaCadastrada = EmpresaFactory.getEmpresa(2L);
    	assertEquals(true, empresaManager.verifyExistsCnpj(empresaJaCadastrada.getId(), empresa.getCnpj()));
    }
    
    public void testFindDistinctEmpresasByQuestionario()
    {
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	Long questionarioId=12L;
    	
		empresaDao.expects(once()).method("findDistinctEmpresaByQuestionario").with(eq(questionarioId)).will(returnValue(empresas));
		
		assertNotNull(empresaManager.findDistinctEmpresasByQuestionario(questionarioId));    
	}
    
    public void testPopulaCadastrosCheckBox()
    {
    	assertEquals(10, empresaManager.populaCadastrosCheckBox().size());
    }

    public void testValidaIntegracaoAC()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setAcIntegra(true);
    	
    	MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
    	colaboradorManager.expects(once()).method("findSemCodigoAC").with(eq(empresa.getId())).will(returnValue(Arrays.asList(ColaboradorFactory.getEntity())));
    	estabelecimentoManager.expects(once()).method("findSemCodigoAC").with(eq(empresa.getId())).will(returnValue(Arrays.asList(EstabelecimentoFactory.getEntity())));
    	areaOrganizacionalManager.expects(once()).method("findSemCodigoAC").with(eq(empresa.getId())).will(returnValue(Arrays.asList(AreaOrganizacionalFactory.getEntity())));
    	faixaSalarialManager.expects(once()).method("findSemCodigoAC").with(eq(empresa.getId())).will(returnValue(Arrays.asList(FaixaSalarialFactory.getEntity())));
    	indiceManager.expects(once()).method("findSemCodigoAC").will(returnValue(Arrays.asList(IndiceFactory.getEntity())));
    	OcorrenciaManager.expects(once()).method("findSemCodigoAC").with(eq(empresa.getId())).will(returnValue(Arrays.asList(OcorrenciaFactory.getEntity())));
    	cidadeManager.expects(once()).method("findSemCodigoAC").will(returnValue(Arrays.asList(CidadeFactory.getEntity())));

    	colaboradorManager.expects(once()).method("findCodigoACDuplicado").will(returnValue("1"));
    	estabelecimentoManager.expects(once()).method("findCodigoACDuplicado").will(returnValue(""));
    	areaOrganizacionalManager.expects(once()).method("findCodigoACDuplicado").will(returnValue(""));
    	faixaSalarialManager.expects(once()).method("findCodigoACDuplicado").will(returnValue("2"));
    	indiceManager.expects(once()).method("findCodigoACDuplicado").will(returnValue(""));
    	OcorrenciaManager.expects(once()).method("findCodigoACDuplicado").will(returnValue("3"));
    	cidadeManager.expects(once()).method("findCodigoACDuplicado").will(returnValue(""));
    	
    	
    	Collection<String> collectionMsgs = empresaManager.validaIntegracaoAC(empresa);
    	
    	assertEquals(12, collectionMsgs.size());
    	
    	String msgs = StringUtil.converteCollectionToString(collectionMsgs);
    	
    	assertEquals("Não foi possível habilitar a integração com o AC Pessoal. Verifique os seguintes itens:," +
    			"- Existe(m) 1 colaborador(es) sem código AC.," +
    			"- Existe(m) colaborador(es) com código AC duplicado: 1," +
    			"- Existe(m) 1 estabelecimento(s) sem código AC.," +
    			"- Existe(m) 1 área(s) organizacional(is) sem código AC.," +
    			"- Existe(m) 1 faixa(s) salarial(is) sem código AC.," +
    			"- Existe(m) faixa(s) salarial(is) com código AC duplicado: 2," +
    			"- Existe(m) 1 índice(s) sem código AC.," +
    			"- Existe(m) 1 ocorrencia(s) sem código AC.," +
    			"- Existe(m) ocorrencia(s) com código AC duplicado: 3," +
    			"- Existe(m) 1 cidade(s) sem código AC.," +
    			"Entre em contato com o suporte técnico.", msgs);
    }
}