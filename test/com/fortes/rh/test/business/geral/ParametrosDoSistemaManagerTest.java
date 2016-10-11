package com.fortes.rh.test.business.geral;

import java.util.Locale;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ParametrosDoSistemaManagerImpl;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.ws.AcPessoalClientSistema;

public class ParametrosDoSistemaManagerTest extends MockObjectTestCase
{
	private ParametrosDoSistemaManagerImpl parametrosDoSistemaManager = new ParametrosDoSistemaManagerImpl();
	private Mock parametrosDoSistemaDao = null;
	private Mock acPessoalClientSistema;

    protected void setUp() throws Exception
    {
        super.setUp();
        parametrosDoSistemaDao = new Mock(ParametrosDoSistemaDao.class);
        parametrosDoSistemaManager.setDao((ParametrosDoSistemaDao) parametrosDoSistemaDao.proxy());
        
        acPessoalClientSistema = mock(AcPessoalClientSistema.class);
        parametrosDoSistemaManager.setAcPessoalClientSistema((AcPessoalClientSistema) acPessoalClientSistema.proxy());
    }

    public void testFindByIdProjection()
	{
    	ParametrosDoSistema ps = new ParametrosDoSistema();
    	ps.setId(1L);

    	parametrosDoSistemaDao.expects(once()).method("findByIdProjection").with(eq(ps.getId())).will(returnValue(ps));

    	ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(ps.getId());

    	assertEquals(parametrosDoSistema.getId(), ps.getId());
	}

    public void testGetSistemaAtualizado()
    {
    	ParametrosDoSistema ps = new ParametrosDoSistema();
    	ps.setId(1L);
    	ps.setAtualizadoSucesso(true);

    	parametrosDoSistemaDao.expects(once()).method("findByIdProjection").with(eq(ps.getId())).will(returnValue(ps));

    	assertTrue(parametrosDoSistemaManager.getSistemaAtualizado());
    }

    public void testVerificaCompatibilidadeComAC()
	{
    	String versaoAC = "3.566.232";
    	String versaoMinimaCompativel = "3.566.232";

		boolean retorno = parametrosDoSistemaManager.verificaCompatibilidadeComWebServiceAC(versaoAC, versaoMinimaCompativel);

		assertTrue("Compatível", retorno);

		versaoMinimaCompativel = "3.567.232";

		retorno = parametrosDoSistemaManager.verificaCompatibilidadeComWebServiceAC(versaoAC, versaoMinimaCompativel);

		assertFalse("Incompatível", retorno);
	}

    Empresa empresa = EmpresaFactory.getEmpresa(1L);
    
    public void testGetVersaoWebServiceAC() throws Exception
    {
		acPessoalClientSistema.expects(once()).method("getVersaoWebServiceAC").with(eq(empresa)).will(returnValue("1.1.1.2"));
    	assertEquals("1.1.1.2", parametrosDoSistemaManager.getVersaoWebServiceAC(empresa));
    }
    public void testIsACIntegrado() throws Exception
    {
    	acPessoalClientSistema.expects(once()).method("idACIntegrado").with(eq(empresa)).will(returnValue(true));
    	assertTrue(parametrosDoSistemaManager.isACIntegrado(empresa));
    }

    public void testIsIdiomaCorreto()
    {
//    	System.setProperty("user.country","BR");
//		System.setProperty("user.language", "pt");
    	Locale.setDefault(new Locale("pt","BR"));
    	
    	assertTrue(parametrosDoSistemaManager.isIdiomaCorreto());

//    	System.setProperty("user.country","US");
//    	System.setProperty("user.language", "en");
    	Locale.setDefault(new Locale("en","US"));
    	
    	assertFalse(parametrosDoSistemaManager.isIdiomaCorreto());

//    	System.setProperty("user.country","BR");
//    	System.setProperty("user.language", "en");
    	Locale.setDefault(new Locale("en","BR"));
    	
    	assertFalse(parametrosDoSistemaManager.isIdiomaCorreto());
    }
}
