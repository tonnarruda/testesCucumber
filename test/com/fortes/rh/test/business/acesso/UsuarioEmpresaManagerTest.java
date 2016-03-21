package com.fortes.rh.test.business.acesso;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManagerImpl;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;

public class UsuarioEmpresaManagerTest extends MockObjectTestCase
{
	UsuarioEmpresaManagerImpl usuarioEmpresaManager;
	Mock usuarioEmpresaDao;

	protected void setUp(){

		usuarioEmpresaManager = new UsuarioEmpresaManagerImpl();

		usuarioEmpresaDao = new Mock(UsuarioEmpresaDao.class);
		usuarioEmpresaManager.setDao((UsuarioEmpresaDao) usuarioEmpresaDao.proxy());

	}
	
	public void testCriar()
	{
		Usuario usuario = UsuarioFactory.getEntity();
    	String[] empresaIds = new String[]{};
    	String[] selectPerfils = new String[]{};
    	
		usuarioEmpresaManager.save(usuario, empresaIds, selectPerfils);
	}
	
	public void testRemoveAllUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		usuarioEmpresaDao.expects(once()).method("removeAllUsuario").with(eq(usuario)).isVoid();
		usuarioEmpresaManager.removeAllUsuario(usuario);
	}
	
	public void testFindAllBySelectUsuarioEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		usuarioEmpresaDao.expects(once()).method("findAllBySelectUsuarioEmpresa").with(eq(empresa.getId())).will(returnValue(usuarioEmpresas));
		assertEquals(usuarioEmpresas, usuarioEmpresaManager.findAllBySelectUsuarioEmpresa(empresa.getId()));
	}
	
	public void testFindUsuariosByEmpresaRoleSetorPessoal()
	{
		String codigoAC = "";
		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		usuarioEmpresaDao.expects(once()).method("findUsuariosByEmpresaRole").with(new Constraint[]{eq(codigoAC), eq(""), eq(null), ANYTHING, ANYTHING}).will(returnValue(usuarioEmpresas));
		assertEquals(usuarioEmpresas, usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(codigoAC, "", null));
	}
	
	public void testFindByUsuarioEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);
		usuarioEmpresas.add(usuarioEmpresa);
		
		usuarioEmpresaDao.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING}).will(returnValue(usuarioEmpresas));
		assertEquals(usuarioEmpresa, usuarioEmpresaManager.findByUsuarioEmpresa(usuario.getId(), empresa.getId()));
	}
	
	public void testFindByUsuarioEmpresaVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		
		usuarioEmpresaDao.expects(once()).method("find").with(ANYTHING, ANYTHING).will(returnValue(usuarioEmpresas));
		assertEquals(null, usuarioEmpresaManager.findByUsuarioEmpresa(usuario.getId(), empresa.getId()));
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		
		usuarioEmpresaDao.expects(once()).method("findByUsuario").with(eq(usuario.getId())).will(returnValue(usuarioEmpresas));
		assertEquals(usuarioEmpresas, usuarioEmpresaManager.findByUsuario(usuario.getId()));
	}
	
	public void testSave()
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		String[] empresaIds = new String[]{"1"};
		String[] selectPerfils = new String[]{"1"};
		
		usuarioEmpresaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		usuarioEmpresaManager.save(usuario, empresaIds, selectPerfils);
	}
	
	public void testContainsRole(){
		usuarioEmpresaDao.expects(once()).method("containsRole").with(eq(2L), eq(1L), eq("xxx")).will(returnValue(true));
		assertTrue(usuarioEmpresaManager.containsRole(2L, 1L, "xxx"));
	}
}
