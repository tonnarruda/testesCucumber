package com.fortes.rh.test.business.acesso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.acesso.PerfilManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class PerfilManagerTest extends MockObjectTestCase
{
	private Mock perfilDao;
	private PerfilManagerImpl perfilManager;
	private Mock colaboradorManager;
	private Mock papelManager;
	
	protected void setUp()
	{
		perfilManager = new PerfilManagerImpl();
		
		perfilDao = new Mock(PerfilDao.class);
		perfilManager.setDao((PerfilDao) perfilDao.proxy());
				
		colaboradorManager = new Mock(ColaboradorManager.class);

		papelManager = new Mock(PapelManager.class);
		perfilManager.setPapelManager((PapelManager) papelManager.proxy());

		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

	public void testMontaPermissoes()
	{
		Collection<Papel> papeis = new ArrayList<Papel>();

		Papel p1 = new Papel();
		p1.setId(1L);

		Papel p2 = new Papel();
		p2.setId(2L);

		papeis.add(p1);
		papeis.add(p2);

		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		
		assertEquals(2, perfilManager.montaPermissoes(perfil).length);
		assertEquals("1", perfilManager.montaPermissoes(perfil)[0]);
		assertEquals("2", perfilManager.montaPermissoes(perfil)[1]);
	}

	public void testGetEmailsByRoleLiberaSolicitacao()
	{
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		Perfil perfil = new Perfil();
		perfil.setId(1L);
		perfil.setNome("Administrador");
		Perfil perfil2 = new Perfil();
		perfil2.setId(2L);
		perfil2.setNome("Gerente de RH");
		
		Collection<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);
		perfis.add(perfil2);
		
		String roleLiberarSolicitacao = "ROLE_LIBERA_SOLICITACAO";
		perfilDao.expects(once()).method("findPerfisByCodigoPapel").with(eq(roleLiberarSolicitacao)).will(returnValue(perfis));
		
		Long empresaId = 1L;
		Collection<String> emails = new ArrayList<String>();
		emails.add("ze@empresa.com");
		
		colaboradorManager.expects(once()).method("findEmailsDeColaboradoresByPerfis").with(eq(perfis), eq(empresaId)).will(returnValue(emails));
		
		assertEquals(1, perfilManager.getEmailsByRoleLiberaSolicitacao(empresaId).size());
	}
	
	public void testFindPapeis()
	{
		Collection<Papel> papeis = new ArrayList<Papel>();

		Papel p1 = new Papel();
		p1.setId(1L);

		Papel p2 = new Papel();
		p2.setId(2L);
		
		Papel p3 = new Papel();
		p2.setId(3L);

		papeis.add(p1);
		papeis.add(p2);
		papeis.add(p3);

		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		
		Long[] perfisIds = new Long[] { p1.getId() };
		
		perfilDao.expects(once()).method("findByIds").with(eq(perfisIds)).will(returnValue(Arrays.asList(perfil)));
		papelManager.expects(once()).method("montarArvore").with(ANYTHING);
		papelManager.expects(once()).method("findByPerfil").with(eq(perfil.getId()));
		
		assertEquals(1, perfilManager.findPapeis(perfisIds).size());
	}
}
