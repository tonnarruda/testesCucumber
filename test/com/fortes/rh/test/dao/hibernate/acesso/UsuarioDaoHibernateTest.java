package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.DateUtil;

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
		usuario.setSenha("1234");
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);
		
		usuarioDao.desativaAcessoSistema(colaborador.getId());
		
		Usuario usuarioRetorno = usuarioDao.findByIdProjection(usuario.getId());
		
		assertEquals(false, usuarioRetorno.isAcessoSistema());
		assertNull(usuarioRetorno.getLogin());
		assertNull(usuarioRetorno.getSenha());
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
	
	public void testReativaAcessoSistema()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(false);
		usuario.setLogin("babauuu_");
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		usuarioDao.reativaAcessoSistema(colaborador.getId());

		Usuario usuarioDoBanco = usuarioDao.findByLogin("babauuu_");
		assertTrue(usuarioDoBanco.isAcessoSistema());
	}
	
	public void testFindAllSelectEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setNome("babauuu_1");
		usuarioDao.save(usuario);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2.setAcessoSistema(true);
		usuario2.setNome("babauuu_2");
		usuarioDao.save(usuario2);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa2.setEmpresa(empresa2);
		usuarioEmpresa2.setUsuario(usuario2);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		Collection<Usuario> usuarios = usuarioDao.findAllSelect(empresa.getId());
		assertEquals(1, usuarios.size());
		
		Usuario usuarioRetorno = (Usuario) usuarios.toArray()[0];
		assertEquals("babauuu_1", usuarioRetorno.getNome());
	}
	
	public void testSetUltimoLogin()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("SetUltimoLogin");
		usuarioDao.save(usuario);
		usuarioDao.setUltimoLogin(usuario.getId());
		
		Usuario usuarioDoBanco = usuarioDao.findByLogin("SetUltimoLogin");
		assertEquals(DateUtil.formataAnoMes(new Date()),DateUtil.formataAnoMes(usuarioDoBanco.getUltimoLogin()));
		
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