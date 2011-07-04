package com.fortes.rh.test.dao.hibernate.acesso;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class UsuarioDaoHibernateTest extends GenericDaoHibernateTest<Usuario>
{
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;
	private UsuarioEmpresaDao usuarioEmpresaDao;
	private ColaboradorDao colaboradorDao;

	public Usuario getEntity()
	{
		Usuario usuario = new Usuario();

		usuario.setId(null);
		usuario.setNome("nome do usuario");
		usuario.setAcessoSistema(true);
		usuario.setLogin("login");
		usuario.setSenha("senha");

		return usuario;
	}

	public void testFindByLogin()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("login");
		usuario = usuarioDao.save(usuario);

		assertEquals(usuario, usuarioDao.findByLogin(usuario.getLogin()));
	}

	public void testFindByLoginNull()
	{
		Usuario usuario = new Usuario();
		usuario.setLogin("erro");

		assertNull(usuarioDao.findByLogin(usuario.getLogin()));
	}
	
	public void testDesativaSuperAdmin()
	{
		Usuario usuarioJoao = new Usuario();
		usuarioJoao.setLogin("joaobla12");
		usuarioJoao.setSuperAdmin(true);
		usuarioDao.save(usuarioJoao);

		Usuario usuarioMaria = new Usuario();
		usuarioMaria.setLogin("mariabla12");
		usuarioDao.save(usuarioMaria);
		
		assertTrue(usuarioDao.findByLogin(usuarioJoao.getLogin()).isSuperAdmin());
		usuarioDao.desativaSuperAdmin();

		assertFalse(usuarioDao.findByLogin(usuarioJoao.getLogin()).isSuperAdmin());
		assertFalse(usuarioDao.findByLogin(usuarioMaria.getLogin()).isSuperAdmin());
	}

	public void testFindUsuarios()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setNome("nome");
		usuario = usuarioDao.save(usuario);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		assertEquals(usuario, usuarioDao.findUsuarios(1, 1, usuario.getNome(), usuarioEmpresa.getEmpresa()).toArray()[0]);
	}

	public void testFindUsuariosNull()
	{
		Empresa empresa = new Empresa();
		Usuario usuario = new Usuario();

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);

		assertTrue(usuarioDao.findUsuarios(0, 0, usuario.getNome(), usuarioEmpresa.getEmpresa()).isEmpty());
	}

	public void testGetCountUsuario()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setNome("nome");
		usuario = usuarioDao.save(usuario);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		assertEquals(1, usuarioDao.getCountUsuario(usuario.getNome(), usuarioEmpresa.getEmpresa()).intValue());
	}

	public void testGetCountUsuarioNull()
	{
		Empresa empresa = new Empresa();
		Usuario usuario = new Usuario();

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);

//		assertEquals(0, usuarioDao.getCountUsuario(usuario.getNome(), usuarioEmpresa.getEmpresa()).intValue());
	}
	
	public void testDesativaAcessoSistema()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("teste0123568");
		usuario = usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);
		
		usuarioDao.desativaAcessoSistema(colaborador.getId());
		assertEquals(false, usuarioDao.findByLogin(usuario.getLogin()).isAcessoSistema());
	}

	public void testExisteLogin()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("login");
		usuario = usuarioDao.save(usuario);

		assertEquals(usuario, usuarioDao.findByLogin(usuario));
	}

	public void testExisteLoginFalse()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("1@a&1;s2;d2e3r5r4r5gf4g899.");

		assertEquals(null, usuarioDao.findByLogin(usuario));
	}

	public void testFindAllSelect()
	{
		usuarioDao.findAllSelect();
	}

	public GenericDao<Usuario> getGenericDao()
	{
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setUsuarioEmpresaDao(UsuarioEmpresaDao usuarioEmpresaDao)
	{
		this.usuarioEmpresaDao = usuarioEmpresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

}