package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class UsuarioDaoHibernateTest extends GenericDaoHibernateTest<Usuario>
{
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
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
		usuario1.setLogin("teste1");
		usuario1.setSenha("1234");
		usuarioDao.save(usuario1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2.setAcessoSistema(true);
		usuario2.setLogin("teste2");
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
	
	
	public void testFindEmailsByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setLogin("babauuu1_");
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmailColaborador("teste1@teste1.com");
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2.setLogin("babauuu2_");
		usuarioDao.save(usuario2);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmailColaborador("teste2@teste2.com");
		colaborador2.setUsuario(usuario2);
		colaboradorDao.save(colaborador2);
		
		Long[] usuariosIds = new Long[]{usuario.getId(), usuario2.getId()};
		
		String[] emails = usuarioDao.findEmailsByUsuario(usuariosIds);
		assertEquals(2, emails.length);
		assertEquals(colaborador.getContato().getEmail(), emails[0]);
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
		
		Usuario usuario3 = UsuarioFactory.getEntity();
		usuario3.setAcessoSistema(false);
		usuario3.setNome("babauuu_2");
		usuarioDao.save(usuario3);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa2.setEmpresa(empresa2);
		usuarioEmpresa2.setUsuario(usuario2);
		usuarioEmpresaDao.save(usuarioEmpresa2);
		
		UsuarioEmpresa usuarioEmpresa3 = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa3.setEmpresa(empresa);
		usuarioEmpresa3.setUsuario(usuario3);
		usuarioEmpresaDao.save(usuarioEmpresa3);
		
		Collection<Usuario> usuarios = usuarioDao.findAllSelect(empresa.getId());
		assertEquals(1, usuarios.size());
		
		Usuario usuarioRetorno = (Usuario) usuarios.toArray()[0];
		assertEquals("babauuu_1", usuarioRetorno.getNome());
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
		usuario.setLogin("usuario");
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
		usuario.setLogin("usuario");
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setEmailColaborador("colab@gmail.com");
		colab.setUsuario(usuario);
		colaboradorDao.save(colab);
		
		Papel papel = new Papel();
		papel.setCodigo("ROLE");
		papel.setNome("role");
		papel.setUrl("#");
		papel.setMenu(false);
		papel.setOrdem(1);
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
		empresa.setNome("Empresa");
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("usuario");
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setEmailColaborador("colab@gmail.com");
		colab.setUsuario(usuario);
		colaboradorDao.save(colab);
		
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalMae.setNome("Area Mãe");
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setNome("Area");
		areaOrganizacional.setResponsavel(colab);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setData(new Date());
		historicoColaborador.setColaborador(colab);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador);

		Papel papel = new Papel();
		papel.setCodigo("ROLE");
		papel.setNome("role");
		papel.setUrl("#");
		papel.setMenu(false);
		papel.setOrdem(1);
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
		
		usuarioDao.getHibernateTemplateByGenericDao().flush();
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(), LongUtil.collectionToCollectionLong(areas), true, null); 
		assertEquals("colab@gmail.com", ((String) retorno[0]));
	}

	public void testFindEmailByPerfilAndResponsavelComPermissaoDeVerTodosColaboradores()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNome("Empresa");
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("usuario");
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setEmailColaborador("colab@gmail.com");
		colab.setEmpresa(empresa);
		colab.setUsuario(usuario);
		colaboradorDao.save(colab);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalMae.setNome("Area Mãe");
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setNome("Area");
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);
		
		Papel papel = new Papel();
		papel.setCodigo("ROLE");
		papel.setNome("role");
		papel.setUrl("#");
		papel.setMenu(false);
		papel.setOrdem(1);
		papelDao.save(papel);
		
		Papel papel1 = new Papel();
		papel1.setCodigo("ROLE_COLAB_VER_TODOS");
		papel1.setNome("role");
		papel1.setUrl("#");
		papel1.setMenu(false);
		papel1.setOrdem(2);
		papelDao.save(papel1);
		
		Collection<Papel> papeis = Arrays.asList(papel, papel1);
		
		Perfil perfil = new Perfil();
		perfil.setNome("perfil");
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setPerfil(perfil);
		usuarioEmpresaDao.save(usuarioEmpresa);
		
		usuarioDao.getHibernateTemplateByGenericDao().flush();
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(), LongUtil.collectionToCollectionLong(areas), true, null); 
		assertEquals("colab@gmail.com", ((String) retorno[0]));
	}
	
	public void testFindEmailByPerfilAndResponsavelApenasComPermissaoDeAprovarOrreprovarSolicitacaoDesligamento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNome("Empresa");
		empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario.setLogin("usuario");
		usuarioDao.save(usuario);
		
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setEmailColaborador("colab@gmail.com");
		colab.setUsuario(usuario);
		colaboradorDao.save(colab);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalMae.setNome("Area Mãe");
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setNome("Area");
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);

		Papel papel = new Papel();
		papel.setCodigo("ROLE");
		papel.setNome("role");
		papel.setUrl("#");
		papel.setMenu(false);
		papel.setOrdem(1);
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
		
		String[] retorno = usuarioDao.findEmailsByPerfilAndGestor(papel.getCodigo(), empresa.getId(),  LongUtil.collectionToCollectionLong(areas), true, null); 
		assertEquals(0, retorno.length);
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

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
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