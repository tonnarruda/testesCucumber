package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.PapelFactory;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class UsuarioDaoHibernateTest extends GenericDaoHibernateTest<Usuario>
{
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private UsuarioEmpresaDao usuarioEmpresaDao;
	private ColaboradorDao colaboradorDao;
	private PerfilDao perfilDao;
	private PapelDao papelDao;

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
	
	
	public void testFindEmailsByUsuarioComEmailDesconsiderado()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(null, usuario1, "teste1@teste1.com");
		colaboradorDao.save(colaborador1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(null, usuario2, "teste2@teste2.com");
		colaboradorDao.save(colaborador2);
		
		Long[] usuariosIds = new Long[]{usuario1.getId(), usuario2.getId()};
		
		String[] emails = usuarioDao.findEmailsByUsuario(usuariosIds, colaborador1.getContato().getEmail());
		assertEquals(1, emails.length);
		assertEquals(colaborador2.getContato().getEmail(), emails[0]);
	}
	
	public void testFindEmailsByUsuarioSemEmailDesconsiderado()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(null, usuario1, "teste1@teste1.com");
		colaboradorDao.save(colaborador1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(null, usuario2, "teste2@teste2.com");
		colaboradorDao.save(colaborador2);
		
		Long[] usuariosIds = new Long[]{usuario1.getId(), usuario2.getId()};
		
		String[] emails = usuarioDao.findEmailsByUsuario(usuariosIds, null);
		assertEquals(2, emails.length);
		assertEquals(colaborador1.getContato().getEmail(), emails[0]);
		assertEquals(colaborador2.getContato().getEmail(), emails[1]);
	}
	
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
	
	
	public void testFindEmpresasUsuarioFortes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNome("Empresa 1");
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setNome("Empresa 2");
		empresaDao.save(empresa2);
		
		Collection<Empresa> empresas = usuarioDao.findEmpresas("fortes");
		assertTrue(empresas.size() >= 2);
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
	
	public void testFindEmailByPerfilAndResponsavelComGestor()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity("usuario" + new Date().getTime(), true);
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity(null, usuario, "colab@gmail.com");
		colaboradorDao.save(colab);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, colab, null);
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

	public void testFindEmailByPerfilAndResponsavelComPermissaoDeVerTodosColaboradores()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity("usuario" + new Date().getTime(), true);
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity(empresa, usuario, "colab@gmail.com");
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
	
	public void testFindEmailByPerfilAndResponsavelApenasComPermissaoDeAprovarOrreprovarSolicitacaoDesligamento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity("usuario", true);
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity(null, usuario, "colab@gmail.com");
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
	
	public void testFindEmailByPerfilAndResponsavelDesconsiderandoEmail()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Usuario usuario1 = UsuarioFactory.getEntity("usuario1", true);
		usuarioDao.save(usuario1);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(null, usuario1, "colab_1@gmail.com");
		colaboradorDao.save(colab1);
		
		Usuario usuario2 = UsuarioFactory.getEntity("usuario2", true);
		usuarioDao.save(usuario2);
		
		Colaborador colab2 = ColaboradorFactory.getEntity(null, usuario2, "colab_2@gmail.com");
		colaboradorDao.save(colab2);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(null, colab1, null);
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, colab2, null);
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
	
	public void testIsResponsavelOrCoResponsavelReturnFalse() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		assertFalse(usuarioDao.isResponsavelOrCoResponsavel(usuario.getId()));
	}
	
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

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setUsuarioEmpresaDao(UsuarioEmpresaDao usuarioEmpresaDao)
	{
		this.usuarioEmpresaDao = usuarioEmpresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setPerfilDao(PerfilDao perfilDao) {
		this.perfilDao = perfilDao;
	}

	public void setPapelDao(PapelDao papelDao) {
		this.papelDao = papelDao;
	}

}