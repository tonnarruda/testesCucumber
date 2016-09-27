package com.fortes.rh.test.security;

import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.UserDetailsImpl;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class UserDetailsImplTest extends TestCase
{
	public void testUserDetailsImpl()
	{
		Long id = 1L;
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("empresa");
		empresa.setControlaRiscoPor('A');
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(id, "nome", "username", "1234", false, new Date(), null, false, false, false, false, "menu", empresa, colaborador, null);
		
		assertEquals(id, userDetailsImpl.getId());
		assertEquals("nome", userDetailsImpl.getNome());
		assertEquals("username", userDetailsImpl.getUsername());
		assertEquals("1234", userDetailsImpl.getPassword());
		assertEquals(empresa, userDetailsImpl.getEmpresa());
		assertEquals(colaborador, userDetailsImpl.getColaborador());
		assertEquals(null, userDetailsImpl.getAuthorities());
		assertEquals(false, userDetailsImpl.getEmpresaAcIntegra());
		assertEquals(id, userDetailsImpl.getEmpresaId());
		assertEquals("empresa", userDetailsImpl.getEmpresaNome());
		assertEquals("menu", userDetailsImpl.getMenuFormatado());
		assertEquals(false, userDetailsImpl.isAccountNonExpired());
		assertEquals(false, userDetailsImpl.isAccountNonLocked());
		assertEquals(false, userDetailsImpl.isCredentialsNonExpired());
		assertEquals(false, userDetailsImpl.isEnabled());
		assertEquals('A', userDetailsImpl.getEmpresaControlaRiscoPor());
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		userDetailsImpl.setAuthorities(null);
		userDetailsImpl.setEmpresa(empresa2);
		userDetailsImpl.setMenuFormatado("menu teste");
		
		assertEquals(empresa2, userDetailsImpl.getEmpresa());
		assertEquals("menu teste", userDetailsImpl.getMenuFormatado());
	}
}
