package com.fortes.rh.test.business.acesso;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.acesso.PerfilManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.test.factory.acesso.PapelFactory;
import com.fortes.rh.test.factory.acesso.PerfilFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class PerfilManagerTest
{
	private static PerfilManagerImpl perfilManager = new PerfilManagerImpl();
	private static PerfilDao perfilDao;
	private static PapelManager papelManager;
	private ColaboradorManager colaboradorManager;
	
	@BeforeClass
	public static void setUpClass()
	{
		perfilDao = mock(PerfilDao.class);
		papelManager = mock(PapelManager.class);

		perfilManager.setDao(perfilDao);
		perfilManager.setPapelManager(papelManager);
	}
	
	@Before
	public void setUp(){
		colaboradorManager = mock(ColaboradorManager.class);
		MockSpringUtilJUnit4.mocks.put("colaboradorManager", colaboradorManager);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
	}
	
	
	@Test
	public void testRemovePerfilPapelByPapelId() {
		Long papelId = 680L;
		
		perfilManager.removePerfilPapelByPapelId(papelId);
		verify(perfilDao, times(1)).removePerfilPapelByPapelId(papelId);
	}
	
	@Test
	public void testFindAll()
	{
		Integer page = 1;
		Integer pagingSize = 15;
		
		perfilManager.findAll(page, pagingSize);
		verify(perfilDao, times(1)).findAll(eq(page), eq(pagingSize));
	}
	
	@Test
	public void testGetCount()
	{
		perfilDao.getCount();
		verify(perfilDao, times(1)).getCount();
	}
	
	@Test
	public void testMontaPermissoes()
	{
		Papel p1 = PapelFactory.getEntity(1L);
		Papel p2 = PapelFactory.getEntity(2L);

		Collection<Papel> papeis = Arrays.asList(p1, p2);

		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		
		assertEquals(2, perfilManager.montaPermissoes(perfil).length);
		assertEquals("1", perfilManager.montaPermissoes(perfil)[0]);
		assertEquals("2", perfilManager.montaPermissoes(perfil)[1]);
	}
	
	@Test
	public void testGetEmailsByRoleLiberaSolicitacao()
	{
		Perfil perfil1 = PerfilFactory.getEntity(1L, "Administrador");
		Perfil perfil2 = PerfilFactory.getEntity(2L, "Gerente de RH");
		
		Collection<Perfil> perfis = Arrays.asList(perfil1, perfil2);
		Long empresaId = 1L;
		Collection<String> emails = new ArrayList<String>();
		emails.add("ze@empresa.com");
		
		when(perfilDao.findPerfisByCodigoPapel(eq("ROLE_LIBERA_SOLICITACAO"))).thenReturn(perfis);
		when(colaboradorManager.findEmailsDeColaboradoresByPerfis(eq(perfis), eq(empresaId))).thenReturn(emails);
		
		assertEquals(1, perfilManager.getEmailsByRoleLiberaSolicitacao(empresaId).size());
	}	
	
	@Test
	public void testFindPapeis()
	{
		Papel p1 = PapelFactory.getEntity(1L);
		Papel p2 = PapelFactory.getEntity(2L);
		Papel p3 = PapelFactory.getEntity(3L);

		Collection<Papel> papeis = Arrays.asList(p1, p2, p3);

		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		
		Long[] perfisIds = new Long[] { p1.getId() };
		
		when(perfilDao.findByIds(eq(perfisIds))).thenReturn(Arrays.asList(perfil));
		when(papelManager.montarArvore(papeis)).thenReturn("");
		when(papelManager.findByPerfil(eq(perfil.getId()))).thenReturn(papeis);
		
		assertEquals(1, perfilManager.findPapeis(perfisIds).size());
	}

}
