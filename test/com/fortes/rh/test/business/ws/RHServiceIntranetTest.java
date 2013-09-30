package com.fortes.rh.test.business.ws;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.ws.RHServiceIntranetImpl;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.UsuarioIntranet;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;

public class RHServiceIntranetTest extends MockObjectTestCase
{
	private RHServiceIntranetImpl rHServiceIntranetImpl = new RHServiceIntranetImpl();
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		colaboradorManager = new Mock(ColaboradorManager.class);
		rHServiceIntranetImpl.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		rHServiceIntranetImpl.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
	}
	
	public void testGetUsuario() throws Exception
	{
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setDesligado(true);
		
		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaborador.getId()), eq(StatusRetornoAC.CONFIRMADO)).will(returnValue(colaborador));
		
		Boolean usuarioDesligado =  rHServiceIntranetImpl.usuarioIsDesligado(colaborador.getId().toString());
		
		assertTrue(usuarioDesligado);
	}
	
	public void testGetListaSetor() throws Exception
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa(1L);
		empresa1.setNome("Empresa 1");
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		empresa2.setNome("Empresa 2");
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional1.setEmpresa(empresa1);
		areaOrganizacional1.setNome("Área Organizacional 1");
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional2.setEmpresa(empresa2);
		areaOrganizacional2.setNome("Área Organizacional 2");
		
		
		areaOrganizacionalManager.expects(once()).method("findByEmpresasIds").with(eq(new Long[]{empresa1.getId(),empresa2.getId()}), eq(true)).will(returnValue(Arrays.asList(areaOrganizacional1,areaOrganizacional2)));
		
		Map<String, String> areasOrganizacionais =  rHServiceIntranetImpl.getListaSetor(new String[]{empresa1.getId().toString(),empresa2.getId().toString()});
		
		assertEquals(2 ,areasOrganizacionais.size());
		assertEquals(empresa1.getNome()+" - "+areaOrganizacional1.getNome(),areasOrganizacionais.get(areaOrganizacional1.getId()));
		assertEquals(empresa2.getNome()+" - "+areaOrganizacional2.getNome(),areasOrganizacionais.get(areaOrganizacional2.getId()));
	}

	public void testAtualizaUsuarios() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome("Cargo I");
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setEmpresa(empresa);
		colaborador1.setAreaOrganizacional(areaOrganizacional);
		colaborador1.setCargoIdProjection(cargo.getId());
		colaborador1.setCargoNomeProjection(cargo.getNome());
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setEmpresa(empresa);
		colaborador2.setAreaOrganizacional(areaOrganizacional);
		colaborador2.setCargoIdProjection(cargo.getId());
		colaborador2.setCargoNomeProjection(cargo.getNome());
		
		colaboradorManager.expects(once()).method("findByEmpresa").with(eq(empresa.getId())).will(returnValue(Arrays.asList(colaborador1, colaborador2)));
		
		Collection<UsuarioIntranet> usuarioIntranets =  rHServiceIntranetImpl.atualizaUsuarios(empresa.getId().toString());
		
		assertEquals(2 ,usuarioIntranets.size());
	}
}
