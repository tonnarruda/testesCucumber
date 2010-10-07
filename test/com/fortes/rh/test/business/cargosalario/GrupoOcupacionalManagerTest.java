package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManagerImpl;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.web.tags.CheckBox;

public class GrupoOcupacionalManagerTest extends MockObjectTestCase
{
	GrupoOcupacionalManagerImpl grupoOcupacionalManager = null;
	Mock grupoOcupacionalDao = null;
	Mock cargoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		grupoOcupacionalManager = new GrupoOcupacionalManagerImpl();

		grupoOcupacionalDao = new Mock(GrupoOcupacionalDao.class);
		grupoOcupacionalManager.setDao((GrupoOcupacionalDao) grupoOcupacionalDao.proxy());

		cargoManager = new Mock(CargoManager.class);
		grupoOcupacionalManager.setCargoManager((CargoManager) cargoManager.proxy());
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testGetCount()
	{
		Collection<GrupoOcupacional> grupoOcupacionals = new ArrayList<GrupoOcupacional>();

		grupoOcupacionalDao.expects(once()).method("getCount").with(ANYTHING).will(returnValue(grupoOcupacionals.size()));

		int retorno = grupoOcupacionalManager.getCount(1L);

		assertEquals(grupoOcupacionals.size(), retorno);
	}

	public void testFindAllSelect()
	{
		Collection<GrupoOcupacional> grupoOcupacionals = new ArrayList<GrupoOcupacional>();
		Long empresaId = 1L;

		grupoOcupacionalDao.expects(once()).method("findAllSelect").with(eq(0), eq(0), eq(empresaId)).will(returnValue(grupoOcupacionals));

		assertEquals("Sem paginação", grupoOcupacionals, grupoOcupacionalManager.findAllSelect(empresaId));

		grupoOcupacionalDao.expects(once()).method("findAllSelect").with(eq(1), eq(15), eq(empresaId)).will(returnValue(grupoOcupacionals));

		assertEquals("Com paginação", grupoOcupacionals, grupoOcupacionalManager.findAllSelect(1, 15, empresaId));
	}
	
	public void testFindByIdProjection()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional(1L);
		
		grupoOcupacionalDao.expects(once()).method("findByIdProjection").with(eq(grupoOcupacional.getId())).will(returnValue(grupoOcupacional));

		Cargo cargo = CargoFactory.getEntity(1L);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);
		
		cargoManager.expects(once()).method("findByGrupoOcupacional").with(eq(grupoOcupacional.getId())).will(returnValue(cargos));
		
		assertEquals(grupoOcupacional, grupoOcupacionalManager.findByIdProjection(grupoOcupacional.getId()));
	}

	public void testPopulaCheckOrderNome()
	{
		Long empresaId = 1L;
		Collection<GrupoOcupacional> grupoOcupacionals = GrupoOcupacionalFactory.getCollection();

		grupoOcupacionalDao.expects(once()).method("findAllSelect").with(eq(0),eq(0),eq(empresaId)).will(returnValue(grupoOcupacionals));
		Collection<CheckBox> checks = grupoOcupacionalManager.populaCheckOrderNome(empresaId);
		assertEquals(1, checks.size());

		grupoOcupacionalDao.expects(once()).method("findAllSelect").with(eq(0),eq(0),eq(empresaId));
		checks = grupoOcupacionalManager.populaCheckOrderNome(empresaId);
		assertEquals(0, checks.size());
	}
}

