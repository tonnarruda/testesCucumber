package com.fortes.rh.test.business.ws;

import java.util.Arrays;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.ws.RHServiceIntranetImpl;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.UsuarioIntranet;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;

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
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo");
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("Área Organizacional");
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setHistoricoColaborador(historicoColaborador);
		
		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaborador.getId()), eq(StatusRetornoAC.CONFIRMADO)).will(returnValue(colaborador));
		
		UsuarioIntranet usuarioIntranet =  rHServiceIntranetImpl.getUsuario(colaborador.getId());
		
		assertEquals("Área Organizacional",usuarioIntranet.getAreaNome());
		assertEquals("Cargo",usuarioIntranet.getCargoNome());
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
		
		Map<Long, String> areasOrganizacionais =  rHServiceIntranetImpl.getListaSetor(new Long[]{empresa1.getId(),empresa2.getId()});
		
		assertEquals(2 ,areasOrganizacionais.size());
		assertEquals(empresa1.getNome()+" - "+areaOrganizacional1.getNome(),areasOrganizacionais.get(areaOrganizacional1.getId()));
		assertEquals(empresa2.getNome()+" - "+areaOrganizacional2.getNome(),areasOrganizacionais.get(areaOrganizacional2.getId()));
	}

	public void testGetUsuarioPorSetor() throws Exception
	{
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional1.setNome("Área Organizacional 1");
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional1);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setHistoricoColaborador(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setHistoricoColaborador(historicoColaborador2);
		
		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(eq(new Long[]{areaOrganizacional1.getId()})).will(returnValue(Arrays.asList(colaborador1, colaborador2)));
		
		Long[] colaboradoresIds =  rHServiceIntranetImpl.getUsuarioPorSetor(areaOrganizacional1.getId());
		
		assertEquals(2 ,colaboradoresIds.length);
	}
}
