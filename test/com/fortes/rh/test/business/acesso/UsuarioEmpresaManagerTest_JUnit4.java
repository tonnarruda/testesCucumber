package com.fortes.rh.test.business.acesso;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManagerImpl;
import com.fortes.rh.model.geral.AreaOrganizacional;

public class UsuarioEmpresaManagerTest_JUnit4
{
	private UsuarioEmpresaManagerImpl usuarioEmpresaManager;
	private UsuarioEmpresaDao usuarioEmpresaDao;

	@Before
	public void setUp(){
		usuarioEmpresaManager = new UsuarioEmpresaManagerImpl();
		usuarioEmpresaDao = mock(UsuarioEmpresaDao.class);
		usuarioEmpresaManager.setDao(usuarioEmpresaDao);
	}
	@Test
	public void testFindUsuarioResponsavelOuCoResponsavelPorAreaOrganizacional(){
		Long colaboradorId = 1L;
		Collection<Long> areasIds = new ArrayList<Long>();
		int tipoResponsavel = AreaOrganizacional.RESPONSAVEL;
		
		Collection<UsuarioEmpresa> usuariosEmpresa = new ArrayList<UsuarioEmpresa>();
		
		when(usuarioEmpresaDao.findUsuarioResponsavelOuCoResponsavelPorAreaOrganizacional(areasIds, colaboradorId, tipoResponsavel)).thenReturn(usuariosEmpresa);
		assertEquals(usuariosEmpresa.size(), usuarioEmpresaDao.findUsuarioResponsavelOuCoResponsavelPorAreaOrganizacional(areasIds, colaboradorId, tipoResponsavel).size());
	}
}