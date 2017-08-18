package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.fortes.rh.business.geral.AreaOrganizacionalManagerImpl;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.util.CollectionUtil;
import com.opensymphony.webwork.dispatcher.SessionMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class})
public class AreaOrganizacionalManagerTest_Junit4 {
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private AreaOrganizacionalManagerImpl areaOrganizacionalManager = new AreaOrganizacionalManagerImpl();
	private MockHttpServletRequest request = new MockHttpServletRequest();

	@Before
	public void setUp() throws Exception {
		areaOrganizacionalDao = mock(AreaOrganizacionalDao.class);
		areaOrganizacionalManager.setDao(areaOrganizacionalDao);

		PowerMockito.mockStatic(SecurityUtil.class);
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
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testFiltraPermitidasByEmpresasAndUsuarioComTodasAsAreasComTodasAsEmpresas() throws Exception {
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Map session = new SessionMap(request);
		
		Long[] empresaIds = new Long[]{empresa.getId()};
		
		when(SecurityUtil.getIdUsuarioLoged(session)).thenReturn(1l);
		when(SecurityUtil.getEmpresaSession(session)).thenReturn(empresa);
		when(SecurityUtil.verifyRole(session ,new String[]{"ROLE_VER_AREAS"})).thenReturn(true);
		when(areaOrganizacionalManager.findByEmpresasIds(empresaIds,AreaOrganizacional.TODAS)).thenReturn(areaOrganizacionals);
		
		Collection<AreaOrganizacional> areasRetorno = areaOrganizacionalManager.filtraPermitidasByEmpresasAndUsuario(request, 0L, empresaIds);
		
		assertEquals(1, areasRetorno.size());
	}

	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testFiltraPermitidasByEmpresasAndUsuarioComTodasAsAreasSemTodasAsEmpresas() throws Exception {
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Map session = new SessionMap(request);
		
		when(SecurityUtil.getIdUsuarioLoged(session)).thenReturn(1l);
		when(SecurityUtil.getEmpresaSession(session)).thenReturn(empresa);
		when(SecurityUtil.verifyRole(session ,new String[]{"ROLE_VER_AREAS"})).thenReturn(true);
		when(areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresa.getId())).thenReturn(areaOrganizacionals);
		
		Collection<AreaOrganizacional> areasRetorno = areaOrganizacionalManager.filtraPermitidasByEmpresasAndUsuario(request, empresa.getId(), null);
		
		assertEquals(1, areasRetorno.size());
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testFiltraPermitidasByEmpresasAndUsuarioSemTodasAsAreasComTodasAsEmpresas() throws Exception {
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Map session = new SessionMap(request);
		
		Long usuarioLogadoId = 1L;
		Long[] empresaIds = new Long[]{empresa.getId()};
		
		when(SecurityUtil.getIdUsuarioLoged(session)).thenReturn(usuarioLogadoId);
		when(SecurityUtil.getEmpresaSession(session)).thenReturn(empresa);
		when(SecurityUtil.verifyRole(session ,new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
		when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(empresa.getId(), usuarioLogadoId, AreaOrganizacional.TODAS, null)).thenReturn(areaOrganizacionals);
		
		Collection<AreaOrganizacional> areasRetorno = areaOrganizacionalManager.filtraPermitidasByEmpresasAndUsuario(request, null, empresaIds);
		
		assertEquals(1, areasRetorno.size());
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testFiltraPermitidasByEmpresasAndUsuarioSemTodasAsAreasSemTodasAsEmpresas() throws Exception {
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		Empresa empresa1 = EmpresaFactory.getEmpresa(1L);
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		Map session = new SessionMap(request);

		Long usuarioLogadoId = 1L;
		Long[] empresaIds = new Long[]{empresa1.getId()};

		when(SecurityUtil.getIdUsuarioLoged(session)).thenReturn(usuarioLogadoId);
		when(SecurityUtil.getEmpresaSession(session)).thenReturn(empresa2);
		when(SecurityUtil.verifyRole(session ,new String[]{"ROLE_VER_AREAS"})).thenReturn(false);
		when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(empresa1.getId(), usuarioLogadoId, AreaOrganizacional.TODAS, null)).thenReturn(areaOrganizacionals);

		Collection<AreaOrganizacional> areasRetorno = areaOrganizacionalManager.filtraPermitidasByEmpresasAndUsuario(request, empresa1.getId(), empresaIds);

		assertEquals(1, areasRetorno.size());
	}
	
	@Test
	public void testFindCollectionFilhasByAreasIds() {
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(2L);

		areas.add(area);
		areas.add(area2);

		Long[] areasIds = new Long[] { area.getId(), area2.getId() };

		when(areaOrganizacionalDao.findCollectionFilhasByAreasIds(areasIds)).thenReturn(areas);

		Collection<AreaOrganizacional> areaCollection = areaOrganizacionalManager.findCollectionFilhasByAreasIds(areasIds);

		assertEquals(areas.size(), areaCollection.size());
	}
	
}