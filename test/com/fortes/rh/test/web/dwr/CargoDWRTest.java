package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.web.dwr.CargoDWR;

@SuppressWarnings("rawtypes")
public class CargoDWRTest extends MockObjectTestCase
{
	private CargoDWR cargoDWR;
	private Mock cargoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		cargoDWR = new CargoDWR();

		cargoManager = new Mock(CargoManager.class);
		cargoDWR.setCargoManager((CargoManager) cargoManager.proxy());
	}

	public void testGetCargoByGrupo()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional(1L);

		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setGrupoOcupacional(grupoOcupacional);

		String [] grupoOcupacionalIds = {grupoOcupacional.getId().toString()};

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		cargoManager.expects(once()).method("findByGrupoOcupacionalIdsProjection").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(cargos));

		Map retorno = cargoDWR.getCargoByGrupo(grupoOcupacionalIds, 1L);

		assertEquals(1, retorno.size());
	}

	public void testGetCargoByArea()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional);

		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setAreasOrganizacionais(areaOrganizacionals);

		String [] areaOrganizacionalIds = {areaOrganizacional.getId().toString()};

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		cargoManager.expects(once()).method("findByAreasOrganizacionalIdsProjection").with(ANYTHING, ANYTHING).will(returnValue(cargos));

		Map retorno = cargoDWR.getCargoByArea(areaOrganizacionalIds, "getNomeMercado", null);

		assertEquals(1, retorno.size());
	}
	
	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		Cargo cargo = CargoFactory.getEntity(1L);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);
		
		cargoManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING,ANYTHING,ANYTHING).will(returnValue(cargos));
		
		Map retorno = cargoDWR.getByEmpresa(empresaId, new Long[]{empresaId});		
		assertEquals(1, retorno.size());
	}

	public void testGetByEmpresaTodosCargos()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);
		
		cargoManager.expects(once()).method("findAllSelectDistinctNome").will(returnValue(cargos));
		
		Map retorno = cargoDWR.getByEmpresa(-1L, new Long[]{1L});		
		assertEquals(1, retorno.size());
	}
	
	public void testGetByAreaDoHistoricoColaborador()
	{
		String[] areaOrganizacionalIds = {"1"};
		cargoManager.expects(once()).method("findByAreaDoHistoricoColaborador").with(eq(areaOrganizacionalIds)).will(returnValue(new HashMap<Object, Object>()));
		
		assertNotNull(cargoDWR.getByAreaDoHistoricoColaborador(areaOrganizacionalIds));
	}
}
