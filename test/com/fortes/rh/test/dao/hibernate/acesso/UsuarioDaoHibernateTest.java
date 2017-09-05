package com.fortes.rh.test.dao.hibernate.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.acesso.PapelFactory;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class UsuarioDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Usuario>
{
	@Autowired private UsuarioDao usuarioDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired private UsuarioEmpresaDao usuarioEmpresaDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private PerfilDao perfilDao;
	@Autowired private PapelDao papelDao;

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

	@Test
	public void testFindByLogin()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("login");
		usuario = usuarioDao.save(usuario);

		assertEquals(usuario, usuarioDao.findByLogin(usuario.getLogin()));
	}

	@Test
	public void testFindByLoginNull()
	{
		Usuario usuario = new Usuario();
		usuario.setLogin("erro");

		assertNull(usuarioDao.findByLogin(usuario.getLogin()));
	}
	
	@Test
	public void testDesativaSuperAdmin()
	{
		Usuario usuarioJoao = UsuarioFactory.getEntity();
		usuarioJoao.setLogin("joaobla12");
		usuarioJoao.setSuperAdmin(true);
		usuarioDao.save(usuarioJoao);

		Usuario usuarioMaria = UsuarioFactory.getEntity();
		usuarioMaria.setLogin("mariabla12");
		usuarioDao.save(usuarioMaria);
		
		assertTrue(usuarioDao.findByLogin(usuarioJoao.getLogin()).isSuperAdmin());
		usuarioDao.desativaSuperAdmin();

		assertFalse(usuarioDao.findByLogin(usuarioJoao.getLogin()).isSuperAdmin());
		assertFalse(usuarioDao.findByLogin(usuarioMaria.getLogin()).isSuperAdmin());
	}

	@Test
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

	@Test
	public void testFindUsuariosNull()
	{
		Empresa empresa = new Empresa();
		Usuario usuario = new Usuario();

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);

		assertTrue(usuarioDao.findUsuarios(0, 0, usuario.getNome(), usuarioEmpresa.getEmpresa()).isEmpty());
	}
	
	@Test
	public void testFindUsuariosLoginVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);

		UsuarioEmpresa usuarioEmpresa1 = new UsuarioEmpresa();
		usuarioEmpresa1.setUsuario(usuario1);
		usuarioEmpresa1.setEmpresa(empresa);
		usuarioEmpresaDao.save(usuarioEmpresa1);
		
		UsuarioEmpresa usuarioEmpresa2 = new UsuarioEmpresa();
		usuarioEmpresa2.setUsuario(usuario2);
		usuarioEmpresa2.setEmpresa(empresa);
		usuarioEmpresaDao.save(usuarioEmpresa2);

		assertEquals(2, usuarioDao.findUsuarios(0, 15, usuario1.getNome(), empresa).size());
		
		usuario2.setLogin(null);
		usuarioDao.update(usuario2);
		
		assertEquals(1, usuarioDao.findUsuarios(0, 15, usuario1.getNome(), empresa).size());
	}

	@Test
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

	@Test
	public void testGetCountUsuarioNull()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setNome("nome");
		usuarioDao.save(usuario);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresaDao.save(usuarioEmpresa);

		assertTrue(usuarioDao.getCountUsuario(usuario.getNome(), usuarioEmpresa.getEmpresa()).intValue() > 0);
	}
	
	@Test
	public void testRemoveAcessoSistema()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("teste0123568");
		usuario.setSenha("1234");
		usuarioDao.save(usuario);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setUsuario(usuario);
		colaboradorDao.save(colaborador1);
		
		usuarioDao.desativaAcessoSistema(true, colaborador1.getId());
		
		Usuario usuarioRetorno1 = usuarioDao.findByIdProjection(usuario.getId());
		
		assertEquals(false, usuarioRetorno1.isAcessoSistema());
		assertNull(usuarioRetorno1.getLogin());
		assertNull(usuarioRetorno1.getSenha());
	}
	
	@Test
	public void testDesativaAcessoSistema()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("teste0123568");
		usuario.setSenha("1234");
		usuarioDao.save(usuario);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setUsuario(usuario);
		colaboradorDao.save(colaborador1);
		
		usuarioDao.desativaAcessoSistema(false, colaborador1.getId());
		
		Usuario usuarioRetorno1 = usuarioDao.findByIdProjection(usuario.getId());
		
		assertFalse(usuarioRetorno1.isAcessoSistema());
	}

	@Test
	public void testRemoveAcessoSistemaMultiploColaboradores()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuario1.setAcessoSistema(true);
		usuario1.setLogin("teste1ddssfr3322");
		usuario1.setSenha("1234");
		usuarioDao.save(usuario1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2.setAcessoSistema(true);
		usuario2.setLogin("teste2qqwweerr444");
		usuario2.setSenha("1234");
		usuarioDao.save(usuario2);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setUsuario(usuario1);
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setUsuario(usuario2);
		colaboradorDao.save(colaborador2);
		
		usuarioDao.desativaAcessoSistema(true, new Long[]{colaborador1.getId(), colaborador2.getId()});
		
		Usuario usuarioRetorno1 = usuarioDao.findByIdProjection(usuario2.getId());
		Usuario usuarioRetorno2 = usuarioDao.findByIdProjection(usuario2.getId());
		
		assertEquals(false, usuarioRetorno1.isAcessoSistema());
		assertNull(usuarioRetorno1.getLogin());
		assertNull(usuarioRetorno1.getSenha());
		
		assertEquals(false, usuarioRetorno2.isAcessoSistema());
		assertNull(usuarioRetorno2.getLogin());
		assertNull(usuarioRetorno2.getSenha());
	}

	@Test
	public void testExisteLogin()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("login");
		usuario = usuarioDao.save(usuario);

		assertEquals(usuario, usuarioDao.findByLogin(usuario));
	}

	@Test
	public void testExisteLoginFalse()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("1@a&1;s2;d2e3r5r4r5gf4g899.");

		assertEquals(null, usuarioDao.findByLogin(usuario));
	}

	@Test
	public void testFindAllSelect()
	{
		usuarioDao.findAllSelect();
	}
	
	@Test
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
	
	@Test
	public void testFindEmailsByUsuarioComEmailDesconsiderado()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity("João", null, usuario1, "teste1@teste1.com");
		colaboradorDao.save(colaborador1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity("João", null, usuario2, "teste2@teste2.com");
		colaboradorDao.save(colaborador2);
		
		Long[] usuariosIds = new Long[]{usuario1.getId(), usuario2.getId()};
		
		String[] emails = usuarioDao.findEmailsByUsuario(usuariosIds, colaborador1.getContato().getEmail());
		assertEquals(1, emails.length);
		assertEquals(colaborador2.getContato().getEmail(), emails[0]);
	}
	
	@Test
	public void testFindEmailsByUsuarioSemEmailDesconsiderado()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity("João", null, usuario1, "teste1@teste1.com");
		colaboradorDao.save(colaborador1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity("João", null, usuario2, "teste2@teste2.com");
		colaboradorDao.save(colaborador2);
		
		Long[] usuariosIds = new Long[]{usuario1.getId(), usuario2.getId()};
		
		String[] emails = usuarioDao.findEmailsByUsuario(usuariosIds, null);
		assertEquals(2, emails.length);
		assertEquals(colaborador1.getContato().getEmail(), emails[0]);
		assertEquals(colaborador2.getContato().getEmail(), emails[1]);
	}
	
	@Test
	public void testFindAllSelectEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Usuario usuario = UsuarioFactory.getEntity(null, true);
		usuarioDao.save(usuario);

		Usuario usuario2 = UsuarioFactory.getEntity(null, true);
		usuarioDao.save(usuario2);
		
		Usuario usuario3 = UsuarioFactory.getEntity(null, false);
		usuarioDao.save(usuario3);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, null);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity(null, usuario2, empresa2, null);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		UsuarioEmpresa usuarioEmpresa3 = UsuarioEmpresaFactory.getEntity(null, usuario3, empresa, null);
		usuarioEmpresaDao.save(usuarioEmpresa3);
		
		Collection<Usuario> usuarios = usuarioDao.findAllSelect(empresa.getId());
		assertEquals(1, usuarios.size());
		
		Usuario usuarioRetorno = (Usuario) usuarios.toArray()[0];
		assertEquals(usuario.getId(), usuarioRetorno.getId());
	}
	
	@Test
	public void testFindEmpresas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNome("Empresa 1");
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setNome("Empresa 2");
		empresaDao.save(empresa2);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("usuario" + new Date().getTime());
		usuarioDao.save(usuario);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa2.setEmpresa(empresa2);
		usuarioEmpresa2.setUsuario(usuario);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		Collection<Empresa> empresas = usuarioDao.findEmpresas(usuario.getLogin());
		assertEquals(2, empresas.size());
		
		Empresa EmpresaRetorno1 = ((Empresa) empresas.toArray()[0]); 
		assertEquals("Empresa 1", EmpresaRetorno1.getNome());
	}
	
	@Test
	public void testFindEmpresasUsuarioSOS()
	{
		for (int i = 0; i < 10; i++) {
			Empresa empresa = EmpresaFactory.getEmpresa("Empresa "+i, null, null);
			empresaDao.save(empresa);
		}
		
		Collection<Empresa> empresas = usuarioDao.findEmpresas("SOS");
		assertTrue(empresas.size() >= 10);
	}
	
	@Test
	public void testSetUltimoLogin()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("SetUltimoLogin");
		usuarioDao.save(usuario);
		usuarioDao.setUltimoLogin(usuario.getId());
		
		Usuario usuarioDoBanco = usuarioDao.findByLogin("SetUltimoLogin");
		assertEquals(DateUtil.formataAnoMes(new Date()),DateUtil.formataAnoMes(usuarioDoBanco.getUltimoLogin()));
		
	}
	
	@Test
	public void testFindEmailsByPerfil()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNome("Empresa");
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("usuario" + new Date().getTime());
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setEmailColaborador("colab@gmail.com");
		colab.setUsuario(usuario);
		colaboradorDao.save(colab);
		
		Papel papel = PapelFactory.getEntity(null, "ROLE");
		papelDao.save(papel);
		Collection<Papel> papeis = Arrays.asList(papel);
		
		Perfil perfil = new Perfil();
		perfil.setNome("perfil");
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setPerfil(perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		String[] retorno = usuarioDao.findEmailsByPerfil("ROLE", empresa.getId()); 
		assertEquals("colab@gmail.com", ((String) retorno[0]));
	}
	
	@Test
	public void testFindEmailByPerfilAndResponsavelComGestor()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity("usuario" + new Date().getTime(), true);
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity("João", null, usuario, "colab@gmail.com");
		colaboradorDao.save(colab);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, colab, null, null);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);
		
		Papel papel = PapelFactory.getEntity(null, "ROLE");
		papelDao.save(papel);
		Collection<Papel> papeis = Arrays.asList(papel);
		
		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		usuarioDao.getHibernateTemplateByGenericDao().flush();
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(), LongUtil.collectionToCollectionLong(areas), true, null); 
		assertEquals("colab@gmail.com", ((String) retorno[0]));
	}

	@Test
	public void testFindEmailByPerfilAndResponsavelComPermissaoDeVerTodosColaboradores()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity("usuario" + new Date().getTime(), true);
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity("João", empresa, usuario, "colab@gmail.com");
		colaboradorDao.save(colab);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);
		
		Papel papel = PapelFactory.getEntity(null, "ROLE");
		papelDao.save(papel);
		
		Papel papel1 = PapelFactory.getEntity(null, "ROLE_COLAB_VER_TODOS");
		papelDao.save(papel1);
		
		Collection<Papel> papeis = Arrays.asList(papel, papel1);
		
		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null,usuario, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		usuarioDao.getHibernateTemplateByGenericDao().flush();
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(), LongUtil.collectionToCollectionLong(areas), true, null); 
		assertEquals("colab@gmail.com", ((String) retorno[0]));
	}
	
	@Test
	public void testFindEmailByPerfilAndResponsavelApenasComPermissaoDeAprovarOrreprovarSolicitacaoDesligamento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity("usuario", true);
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity("João", null, usuario, "colab@gmail.com");
		colaboradorDao.save(colab);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);

		Papel papel = PapelFactory.getEntity(null, "ROLE");
		papelDao.save(papel);
		
		Collection<Papel> papeis = Arrays.asList(papel);
		
		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(null, usuario, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(),  LongUtil.collectionToCollectionLong(areas), true, null); 
		assertEquals(0, retorno.length);
	}
	
	@Test
	public void testFindEmailByPerfilAndResponsavelDesconsiderandoEmail()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario1 = UsuarioFactory.getEntity("usuario1", true);
		usuarioDao.save(usuario1);
		
		Colaborador colab1 = ColaboradorFactory.getEntity("João", null, usuario1, "colab_1@gmail.com");
		colaboradorDao.save(colab1);
		
		Usuario usuario2 = UsuarioFactory.getEntity("usuario2", true);
		usuarioDao.save(usuario2);
		
		Colaborador colab2 = ColaboradorFactory.getEntity("João", null, usuario2, "colab_2@gmail.com");
		colaboradorDao.save(colab2);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(null, colab1, null, null);
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, colab2, null, null);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);
		
		Papel papel = PapelFactory.getEntity(null, "ROLE");
		papelDao.save(papel);
		Collection<Papel> papeis = Arrays.asList(papel);
		
		Perfil perfil = new Perfil();
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa1 = UsuarioEmpresaFactory.getEntity(null, usuario1, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa1);
		
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity(null, usuario2, empresa, perfil);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		usuarioDao.getHibernateTemplateByGenericDao().flush();
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(), LongUtil.collectionToCollectionLong(areas), false, colab1.getContato().getEmail()); 
		
		assertEquals(1, retorno.length);
		assertEquals(colab2.getContato().getEmail(), ((String) retorno[0]));
	}
	
	@Test
	public void testIsResponsavelOrCoResponsavelReturnFalse() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		assertFalse(usuarioDao.isResponsavelOrCoResponsavel(usuario.getId()));
	}
	
	@Test
	public void testIsResponsavelOrCoResponsavelReturnTrue() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(colaborador);
		areaOrganizacional.setCoResponsavel(colaborador);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		areaOrganizacionalDao.getHibernateTemplateByGenericDao().flush();
		
		assertTrue(usuarioDao.isResponsavelOrCoResponsavel(usuario.getId()));
	}
	
	@Test
	public void testFindEmailByUsuarioId()
	{
		Usuario usuario  = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador.setEmailColaborador("email@email.com.br");
		colaboradorDao.save(colaborador);
		
		assertEquals(colaborador.getContato().getEmail(), usuarioDao.findEmailByUsuarioId(usuario.getId()));
	}
	
	@Test
	public void testFindEmailByUsuarioIdColaboradorSemEmail()
	{
		Usuario usuario  = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador.setEmailColaborador(null);
		colaboradorDao.save(colaborador);
		
		assertNull(usuarioDao.findEmailByUsuarioId(usuario.getId()));
	}

	@Test
	public void testFindEmailByUsuarioIdUsuarioSemColaborador()
	{
		Usuario usuario  = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		assertNull(usuarioDao.findEmailByUsuarioId(usuario.getId()));
	}
	
	public GenericDao<Usuario> getGenericDao()
	{
		return usuarioDao;
	}
}