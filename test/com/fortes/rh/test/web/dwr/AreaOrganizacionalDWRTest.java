package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockBooleanUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSessionMap;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.web.dwr.AreaOrganizacionalDWR;
import com.opensymphony.webwork.dispatcher.SessionMap;

public class AreaOrganizacionalDWRTest extends MockObjectTestCase
{
	private AreaOrganizacionalDWR areaOrganizacionalDWR;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		areaOrganizacionalDWR = new AreaOrganizacionalDWR();

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		areaOrganizacionalDWR.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		
		Mockit.redefineMethods(BooleanUtil.class, MockBooleanUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(SessionMap.class, MockSessionMap.class);
	}

	public void testeGetEmailsResponsaveis()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmailColaborador("colaborador@teste.com");
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity(1L);
		area1.setEmpresa(empresa);
		area1.setResponsavel(colaborador);
		area1.setEmailsNotificacoes("teste1@teste.com;teste2@teste.com");
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(2L);
		area2.setEmpresa(empresa);
		area2.setEmailsNotificacoes("teste3@teste.com;teste4@teste.com;teste5@teste.com");
		area2.setAreaMae(area1);
		
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area1);
		areas.add(area2);
		
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("getAncestrais").with(ANYTHING, ANYTHING).will(returnValue(areas));
		
		try 
		{
			Map<Object, Object> map = areaOrganizacionalDWR.getEmailsResponsaveis(area2.getId(), empresa.getId());
			assertTrue(map.containsKey("colaborador@teste.com"));
			assertTrue(map.containsKey("teste1@teste.com"));
			assertTrue(map.containsKey("teste3@teste.com"));
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	public void testVerificaMaternidade()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setAreaMaeId(1L);

		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").withAnyArguments().will(returnValue(false));
		Exception exc = null;
		try
		{
			areaOrganizacionalDWR.verificaMaternidade(areaOrganizacional.getId());			
		}
		catch (Exception e)
		{
			exc = e;
		}
		
		assertNull(exc);
	}

	public void testVerificaMaternidadeSemAreaMae()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setAreaMaeId(null);

		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").withAnyArguments().will(returnValue(true));

		Exception exc = null;
		try
		{
			areaOrganizacionalDWR.verificaMaternidade(areaOrganizacional.getId());			
		}
		catch (Exception e)
		{
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	public void testGetByEmpresa() throws Exception
	{
		Long empresaId = 1L;
		areaOrganizacionalManager.expects(once()).method("findByEmpresa").with(eq(empresaId)).will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		assertEquals(0, areaOrganizacionalDWR.getByEmpresa(empresaId).size());
	}
	
	public void testGetByEmpresaTodas() throws Exception
	{
		Long empresaId = -1L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		for (AreaOrganizacional areaOrganizacional : areaOrganizacionals) {
			areaOrganizacional.setEmpresa(EmpresaFactory.getEmpresa(1L));
		}
		
		areaOrganizacionalManager.expects(once()).method("findByEmpresa").with(eq(empresaId)).will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		
		assertEquals(1, areaOrganizacionalDWR.getByEmpresa(empresaId).size());
	}
	
	public void testGetByEmpresas() throws Exception
	{
		Long empresaId = 0L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		((AreaOrganizacional)areaOrganizacionals.toArray()[0]).setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		areaOrganizacionalManager.expects(once()).method("findByEmpresasIds").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		
		assertEquals(1, areaOrganizacionalDWR.getByEmpresas(empresaId, new Long[]{1L}, 'T').size());
		
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		empresaId = 1L;
		assertEquals(1, areaOrganizacionalDWR.getByEmpresas(empresaId, new Long[]{1L}, 'T').size());
	}
	
	public void testGetPemitidasByEmpresasSemVerTodasAreas() throws Exception
	{
		Long empresaId = 0L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		((AreaOrganizacional)areaOrganizacionals.toArray()[0]).setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		MockSecurityUtil.roles = new String[]{};
		
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativasByUsuarioId").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		assertEquals(1, areaOrganizacionalDWR.getPemitidasByEmpresas("", null, empresaId, new Long[]{1L}).size());

	}
	
	public void testGetPemitidasByEmpresasComVerAreasTodasComTodasEmpresas() throws Exception
	{
		Long empresaId = 0L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		((AreaOrganizacional)areaOrganizacionals.toArray()[0]).setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		
		areaOrganizacionalManager.expects(once()).method("findByEmpresasIds").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		assertEquals(1, areaOrganizacionalDWR.getPemitidasByEmpresas("", null, empresaId, new Long[]{1L}).size());
		
	}
	
	public void testGetPemitidasByEmpresasComVerTodasAreasComUmaEmpresa() throws Exception
	{
		Long empresaId = 0L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		((AreaOrganizacional)areaOrganizacionals.toArray()[0]).setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		empresaId = 1L;
		assertEquals(1, areaOrganizacionalDWR.getPemitidasByEmpresas("", null, empresaId, new Long[]{1L}).size());
	}
	
	public void testFindAllListAndInativas() throws Exception
	{
		Long empresaId = 0L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		((AreaOrganizacional)areaOrganizacionals.toArray()[0]).setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		
		assertEquals(1, areaOrganizacionalDWR.findAllListAndInativas(empresaId, 1L).size());
	}
}
