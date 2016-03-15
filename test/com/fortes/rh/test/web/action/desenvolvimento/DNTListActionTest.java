package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.DNTListAction;

public class DNTListActionTest extends MockObjectTestCase
{
	private DNTListAction action;
	private Mock DNTManager;
	private Mock colaboradorTurmaManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	private Mock estabelecimentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new DNTListAction();

        DNTManager = new Mock(DNTManager.class);
        action.setDNTManager((DNTManager) DNTManager.proxy());

        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        DNTManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testList() throws Exception
    {
    	Collection<DNT> dnts = new ArrayList<DNT>();
    	action.setGestor(false);
    	
    	DNTManager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));
    	DNTManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(dnts));
    	assertEquals("success", action.list());
    	assertEquals(dnts, action.getDnts());
    }

    public void testDelete() throws Exception
    {
    	Collection<DNT> dnts = new ArrayList<DNT>();
    	action.setGestor(false);

    	DNT dnt = DntFactory.getEntity(1L);
    	action.setDnt(dnt);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	dnt.setEmpresa(empresa);
    	
    	DNTManager.expects(once()).method("findById").with(eq(dnt.getId())).will(returnValue(dnt));
    	DNTManager.expects(once()).method("remove").with(eq(dnt));

    	DNTManager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));
    	DNTManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(dnts));
    	assertEquals("success", action.delete());
    }
    
    public void testDeleteEmpresaErrada() throws Exception
    {
    	Collection<DNT> dnts = new ArrayList<DNT>();
    	action.setGestor(false);

    	DNT dnt = DntFactory.getEntity(1L);
    	action.setDnt(dnt);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(2L);
    	dnt.setEmpresa(empresa);
    	
    	DNTManager.expects(once()).method("findById").with(eq(dnt.getId())).will(returnValue(dnt));

    	DNTManager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));
    	DNTManager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(dnts));
    	assertEquals("success", action.delete());
    }
      
    public void testGets() throws Exception
    {
    	action.setDnt(null);
    	assertNotNull(action.getDnt());
    	
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	action.setColaboradors(colaboradors);
    	assertEquals(colaboradors, action.getColaboradors());
    	
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	action.setAreas(areas);
    	assertEquals(areas, action.getAreas());
    	
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	action.setAreaFiltro(areaOrganizacional);
    	assertEquals(areaOrganizacional, action.getAreaFiltro());
    	
    	String msg = "teste";
    	action.setMsgAlert(msg);
    	assertEquals(msg, action.getMsgAlert());
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	action.setEstabelecimento(estabelecimento);
    	assertEquals(estabelecimento, action.getEstabelecimento());
    	
    	action.setGestor(true);
    	assertEquals(true, action.isGestor());
    	
    	assertNull(action.getEstabelecimentos());
    	assertNull(action.getColaboradorTurmas());
    	
    }
}