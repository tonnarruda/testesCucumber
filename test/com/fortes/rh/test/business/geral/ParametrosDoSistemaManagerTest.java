package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ParametrosDoSistemaManagerImpl;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
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
    
    public void testDisablePapeisIds()
    {
    	parametrosDoSistemaDao.expects(once()).method("disablePapeisIds").isVoid();
    	parametrosDoSistemaManager.disablePapeisIds();
    }
    
    public void testGetDiasLembretePesquisa()
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setDiasLembretePesquisa("1&2&3");
    	Collection<ParametrosDoSistema> parametrosDoSistemas = new ArrayList<ParametrosDoSistema>();
    	parametrosDoSistemas.add(parametrosDoSistema);
    	
    	parametrosDoSistemaDao.expects(once()).method("findAll").will(returnValue(parametrosDoSistemas));
    	
    	Collection<Integer> diasLembrete = parametrosDoSistemaManager.getDiasLembretePesquisa();
    	assertEquals(3, diasLembrete.size());
    }
    public void testGetDiasLembretePeriodoExperiencia()
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setDiasLembretePeriodoExperiencia("1&2&3");
    	Collection<ParametrosDoSistema> parametrosDoSistemas = new ArrayList<ParametrosDoSistema>();
    	parametrosDoSistemas.add(parametrosDoSistema);
    	
    	parametrosDoSistemaDao.expects(once()).method("findAll").will(returnValue(parametrosDoSistemas));
    	
    	Collection<Integer> diasLembrete = parametrosDoSistemaManager.getDiasLembretePeriodoExperiencia();
    	assertEquals(3, diasLembrete.size());
    }
    public void testGetDiasLembretePesquisaIsNull()
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setDiasLembretePesquisa(null);
    	Collection<ParametrosDoSistema> parametrosDoSistemas = new ArrayList<ParametrosDoSistema>();
    	parametrosDoSistemas.add(parametrosDoSistema);
    	parametrosDoSistemaDao.expects(once()).method("findAll").will(returnValue(parametrosDoSistemas));
    	
    	assertNull(parametrosDoSistemaManager.getDiasLembretePesquisa());
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
    public void testFindModulos()
    {
    	parametrosDoSistemaDao.expects(once()).method("findModulos").will(returnValue("modulos"));
    	assertEquals("modulos",parametrosDoSistemaManager.findModulos(1L));
    }
    public void testUpdateModulos()
    {
    	parametrosDoSistemaDao.expects(once()).method("updateModulos").isVoid();
    	String papeis = "1,2,30,100";
		parametrosDoSistemaManager.updateModulos(papeis);
    }
    public void testGetModulosDecodificados()
    {
    	parametrosDoSistemaDao.expects(once()).method("findModulos").will(returnValue("MSwyLDM="));
    	String[] papeis = {"1","2","3"};
    	assertEquals(papeis.length, parametrosDoSistemaManager.getModulosDecodificados().length);
    }
    public void testGetModulosDecodificadosSemModulos()
    {
    	parametrosDoSistemaDao.expects(once()).method("findModulos").will(returnValue(""));
    	assertEquals(0, parametrosDoSistemaManager.getModulosDecodificados().length);
    }

    public void testIsIdiomaCorreto()
    {
    	System.setProperty("user.country","BR");
		System.setProperty("user.language", "pt");
    	
    	assertTrue(parametrosDoSistemaManager.isIdiomaCorreto());

    	System.setProperty("user.country","US");
    	System.setProperty("user.language", "en");
    	
    	assertFalse(parametrosDoSistemaManager.isIdiomaCorreto());

    	System.setProperty("user.country","BR");
    	System.setProperty("user.language", "en");
    	
    	assertFalse(parametrosDoSistemaManager.isIdiomaCorreto());
    }
}
