package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.AreaOrganizacionalManagerImpl;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.util.CollectionUtil;

public class AreaOrganizacionalManagerTest_Junit4 {
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private AreaOrganizacionalManagerImpl areaOrganizacionalManager = new AreaOrganizacionalManagerImpl();

	@Before
	public void setUp() throws Exception {
		areaOrganizacionalDao = mock(AreaOrganizacionalDao.class);
		areaOrganizacionalManager.setDao(areaOrganizacionalDao);
	}

	@Test
	public void testFindByLntIdComEmpresa() {
		Lnt lnt = LntFactory.getEntity(1L);
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1l, "Nome Area", true, EmpresaFactory.getEmpresa(1L));

		Collection<AreaOrganizacional> areas = Arrays.asList(area);
		Long[] areasIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas);

		when(areaOrganizacionalDao.findByLntId(lnt.getId(), areasIds)).thenReturn(areas);

		assertEquals(areas, areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), areasIds));
	}

	@Test
	public void testIsResposnsavelOrCoResponsavelPorPropriaArea() {
		Long colaboradorId = 2L;
		when(areaOrganizacionalDao.isResposnsavelOrCoResponsavelPorPropriaArea(colaboradorId, AreaOrganizacional.RESPONSAVEL)).thenReturn(true);
		assertTrue(areaOrganizacionalManager.isResposnsavelOrCoResponsavelPorPropriaArea(colaboradorId, AreaOrganizacional.RESPONSAVEL));
	}

	@Test
	public void testRemoveVinculoConhecimentoComAreaOrganizacional() throws Exception {
		doNothing().when(areaOrganizacionalDao).removeVinculoComConhecimento(1l);
		areaOrganizacionalManager.removeVinculoComConhecimento(1l);
		verify(areaOrganizacionalDao, times(1)).removeVinculoComConhecimento(1L);
	}

}