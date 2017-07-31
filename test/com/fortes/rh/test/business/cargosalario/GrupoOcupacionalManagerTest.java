package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManagerImpl;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.web.tags.CheckBox;

public class GrupoOcupacionalManagerTest
{
	GrupoOcupacionalManagerImpl grupoOcupacionalManager = null;
	GrupoOcupacionalDao grupoOcupacionalDao = null;
	CargoManager cargoManager;

	@Before
	public void setUp() throws Exception
	{
		grupoOcupacionalManager = new GrupoOcupacionalManagerImpl();

		grupoOcupacionalDao = mock(GrupoOcupacionalDao.class);
		grupoOcupacionalManager.setDao(grupoOcupacionalDao);

		cargoManager = mock(CargoManager.class);
		grupoOcupacionalManager.setCargoManager(cargoManager);
	}

	@Test
	public void testGetCount()
	{
		Collection<GrupoOcupacional> grupoOcupacionals = new ArrayList<GrupoOcupacional>();

		when(grupoOcupacionalDao.getCount()).thenReturn(grupoOcupacionals.size());

		int retorno = grupoOcupacionalManager.getCount(1L);

		assertEquals(grupoOcupacionals.size(), retorno);
	}

	@Test
	public void testFindAllSelect()
	{
		Collection<GrupoOcupacional> grupoOcupacionals = new ArrayList<GrupoOcupacional>();
		Long empresaId = 1L;

		when(grupoOcupacionalDao.findAllSelect(0, 0, empresaId)).thenReturn(grupoOcupacionals);

		assertEquals("Sem paginação", grupoOcupacionals, grupoOcupacionalManager.findAllSelect(empresaId));

		when(grupoOcupacionalDao.findAllSelect(1, 15, empresaId)).thenReturn(grupoOcupacionals);

		assertEquals("Com paginação", grupoOcupacionals, grupoOcupacionalManager.findAllSelect(1, 15, empresaId));
	}
	
	@Test
	public void testFindByIdProjection()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional(1L);
		
		when(grupoOcupacionalDao.findByIdProjection(grupoOcupacional.getId())).thenReturn(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity(1L);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);
		
		when(cargoManager.findByGrupoOcupacional(grupoOcupacional.getId())).thenReturn(cargos);
		
		assertEquals(grupoOcupacional, grupoOcupacionalManager.findByIdProjection(grupoOcupacional.getId()));
	}

    @Test
    public void testPopulaCheckOrderNome() 
    {
        Long empresaId = 1L;
        Collection<GrupoOcupacional> grupoOcupacionals = GrupoOcupacionalFactory.getCollection();

        when(grupoOcupacionalDao.findAllSelect(0, 0, empresaId)).thenReturn(grupoOcupacionals);
        Collection<CheckBox> checks = grupoOcupacionalManager.populaCheckOrderNome(empresaId);
        assertEquals(1, checks.size());

        when(grupoOcupacionalDao.findAllSelect(0, 0, empresaId)).thenReturn(new ArrayList<GrupoOcupacional>());
        checks = grupoOcupacionalManager.populaCheckOrderNome(empresaId);
        assertEquals(0, checks.size());
    }
	
	@Test
	public void testPopulaCheckByAreasResponsavelCoresponsavel(){
		Long empresaId = 1L;
		Long[] areasIds = new Long[]{2L};
		Collection<GrupoOcupacional> grupoOcupacionals = GrupoOcupacionalFactory.getCollection();
		
		when(grupoOcupacionalDao.findAllSelectByAreasResponsavelCoresponsavel(empresaId, areasIds)).thenReturn(grupoOcupacionals);
		Collection<CheckBox> checks = grupoOcupacionalManager.populaCheckByAreasResponsavelCoresponsavel(empresaId, areasIds);
		assertEquals(1, checks.size());
	}
	
    @Test
    public void testFindGruposUsadosPorCargosByEmpresaId() {
        Long empresaId = 2L;
        Collection<GrupoOcupacional> grupos = Arrays.asList(GrupoOcupacionalFactory.getGrupoOcupacional());

        when(grupoOcupacionalDao.findGruposUsadosPorCargosByEmpresaId(empresaId)).thenReturn(grupos);
        grupoOcupacionalManager.findGruposUsadosPorCargosByEmpresaId(empresaId);
        verify(grupoOcupacionalDao).findGruposUsadosPorCargosByEmpresaId(empresaId);
        assertEquals(1, grupos.size());
    }

    @Test
    public void testDeletarGruposInseridosENaoUtilizadosAposImportarCadastroEntreEmpresas() {
        Long empresaId = 2L;
        Collection<GrupoOcupacional> grupos = Arrays.asList(GrupoOcupacionalFactory.getGrupoOcupacional(1L));

        grupoOcupacionalManager.deletarGruposInseridosENaoUtilizadosAposImportarCadastroEntreEmpresas(new Long[] { 1L }, empresaId);
        verify(grupoOcupacionalDao).deletarGruposInseridosENaoUtilizadosAposImportarCadastroEntreEmpresas(new Long[] { 1L }, empresaId);
        assertEquals(1, grupos.size());
    }
}