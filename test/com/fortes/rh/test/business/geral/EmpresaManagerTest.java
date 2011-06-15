package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.EmpresaManagerImpl;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.webwork.ServletActionContext;

public class EmpresaManagerTest extends MockObjectTestCase
{
	private EmpresaManagerImpl empresaManager = new EmpresaManagerImpl();
	private Mock empresaDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        empresaDao = new Mock(EmpresaDao.class);

        empresaManager.setDao((EmpresaDao) empresaDao.proxy());
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
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
    	empresa = empresaManager.setLogo(empresa, logo, null, null);

    	assertNotNull(empresa.getLogoUrl());
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
}