package com.fortes.rh.test.business.acesso;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fortes.rh.business.acesso.UsuarioManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.LoginExisteException;
import com.fortes.rh.exception.SenhaNaoConfereException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.StringUtil;

public class UsuarioManagerTest
{
	private static UsuarioManagerImpl usuarioManager = new UsuarioManagerImpl();
	private static UsuarioDao usuarioDao;
	private static ColaboradorManager colaboradorManager;
	private static UsuarioEmpresaManager usuarioEmpresaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private static AreaOrganizacionalManager areaOrganizacionalManager;

	@BeforeClass
    public static void setUpClass() throws Exception
    {
        colaboradorManager = mock(ColaboradorManager.class);
        usuarioManager.setColaboradorManager(colaboradorManager);
        
        usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
        usuarioManager.setUsuarioEmpresaManager(usuarioEmpresaManager);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        usuarioManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
    }
	
	@Before
	public void setUp(){
		 usuarioDao = mock(UsuarioDao.class);
		 usuarioManager.setDao(usuarioDao);
		 
		 gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		 usuarioManager.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);
	}
	
	@Test
	public void findByLogin()
	{
		String login = "";
		usuarioManager.findByLogin(login);
		verify(usuarioDao, times(1)).findByLogin(eq(login));
	}
	
	@Test
	public void testFindUsuarioProjection() 
	{
		Long usuarioId = 5L;
		usuarioManager.findEmailByUsuarioId(usuarioId);
		verify(usuarioDao, times(1)).findEmailByUsuarioId(usuarioId);
	}
	
	@Test
	public void testSave()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L, "senha");
    	when(usuarioDao.save(eq(usuario))).thenReturn(usuario);

    	assertEquals(usuario, usuarioManager.save(usuario));
    }
	
	@Test
	public void testUpdate()
    {
		Usuario usuario = UsuarioFactory.getEntity(1L, "senha");
    	usuarioManager.update(usuario);
    	verify(usuarioDao, times(1)).update(usuario);
    }

	@Test
	public void testUpdateMantendoSenha()
	{
		Usuario usuarioTemp = UsuarioFactory.getEntity(1L,"senhaQueEstaNoBanco");
		Usuario usuarioASerAtualizado = UsuarioFactory.getEntity(1L,"");
		
		when(usuarioDao.findById(eq(usuarioASerAtualizado.getId()))).thenReturn(usuarioTemp);
		
		usuarioManager.update(usuarioASerAtualizado);
		verify(usuarioDao, times(1)).update(usuarioASerAtualizado);
	}
	
	@Test
	public void testPrepareCriarUsuario()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		when(colaboradorManager.findByIdProjectionUsuario(eq(colaborador.getId()))).thenReturn(colaborador);
		assertEquals(colaborador, usuarioManager.prepareCriarUsuario(colaborador));
	}
	
	@Test
	public void testUpdateSenha()
	{
		Usuario usuario = preparaUpdateSenha("senha", "senha nova", "senha nova");
		usuarioManager.updateSenha(usuario);
		verify(usuarioDao, times(1)).update(usuario);
	}
	
	@Test
	public void testUpdateSenhaComNovaSenhaDifefrenteDaConfNovaSenha()
	{
		Usuario usuario = preparaUpdateSenha("senha", "senha nova", "nova");
		usuarioManager.updateSenha(usuario);
		verify(usuarioDao, never()).update(usuario);
	}

	private Usuario preparaUpdateSenha(String senhaAntiga, String novaSenha, String confNovaSenha){
		Usuario usuario = UsuarioFactory.getEntity(1L, senhaAntiga);
		usuario.setNovaSenha(novaSenha);
		usuario.setConfNovaSenha(confNovaSenha);
		
		Usuario usuarioOriginal = UsuarioFactory.getEntity(1L,StringUtil.encodeString(senhaAntiga));

		when(usuarioDao.findById(eq(usuario.getId()))).thenReturn(usuarioOriginal);
		
		return usuario;
	}
	
	@Test
	public void testFindUsuarios()
    {
    	int page = 1;
    	int pagingSize = 1;
    	String nomeBusca = "nome";
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	usuarioManager.findUsuarios(page, pagingSize, nomeBusca, empresa);
    	verify(usuarioDao, times(1)).findUsuarios(eq(page), eq(pagingSize), eq(nomeBusca), eq(empresa));
    }
	
	@Test
	public void testGetCountUsuario()
	{
		String nomeBusca = "nome";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		usuarioManager.getCountUsuario(nomeBusca, empresa);
		verify(usuarioDao, times(1)).getCountUsuario(eq(nomeBusca), eq(empresa));
	}
	
	@Test
	public void testFindAllSelect()
	{
		Collection<Usuario> usuarios = new ArrayList<Usuario>();

		when(usuarioDao.findAllSelect()).thenReturn(usuarios);
		
		assertEquals(usuarios, usuarioManager.findAllSelect());
		verify(usuarioDao, times(1)).findAllSelect();
	}
	
	@Test
	public void testFindAllBySelectUsuarioEmpresa()
    {
		Long empresaId = 2L;
		
		UsuarioEmpresa us1 = UsuarioEmpresaFactory.getEntity(1L, new Usuario());
    	UsuarioEmpresa us2 = UsuarioEmpresaFactory.getEntity(2L, new Usuario());
    	UsuarioEmpresa us3 = UsuarioEmpresaFactory.getEntity(3L, new Usuario());

    	Collection<UsuarioEmpresa> usuarioEmpresas = Arrays.asList(us1, us2, us3);

    	when(usuarioEmpresaManager.findAllBySelectUsuarioEmpresa(empresaId) ).thenReturn(usuarioEmpresas);
    	assertEquals(3, usuarioManager.findAllBySelectUsuarioEmpresa(empresaId).size());
    }
	
	@Test
	public void testRemoveUsuario() throws Exception
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);

		UsuarioEmpresa us1 = UsuarioEmpresaFactory.getEntity(1L, usuario);

		Collection<UsuarioEmpresa> usuarioEmpresas = Arrays.asList(us1);

		when(usuarioEmpresaManager.find(eq(new String[]{"usuario.id"}), eq(new Object[]{usuario.getId()}))).thenReturn(usuarioEmpresas);

		usuarioManager.removeUsuario(usuario);

		verify(usuarioEmpresaManager).remove(us1.getId());
		verify(usuarioDao).remove(new Long[]{usuario.getId()});
	}
	
	@Test
	public void testExisteLoginComLoginNaoExistenteNoBanco()
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		usuario.setLogin("log");

		when(usuarioDao.findByLogin(eq(usuario))).thenReturn(null);
		assertFalse(usuarioManager.existeLogin(usuario));
	}
	
	@Test
	public void testExisteLoginExisteMasEDoUsuarioVerificado()
	{
		Usuario usuario = UsuarioFactory.getUsuarioComLogin(1L, "login");
		
		when(usuarioDao.findByLogin(eq(usuario))).thenReturn(usuario);
		assertFalse(usuarioManager.existeLogin(usuario));
	}
	
	@Test
	public void testExisteLoginExisteEPentenceAOutroUsuario()
	{
		Usuario usuario = UsuarioFactory.getUsuarioComLogin(1L, "log");
		Usuario usuarioBanco = UsuarioFactory.getUsuarioComLogin(2L, "log");

		when(usuarioDao.findByLogin(eq(usuario))).thenReturn(usuarioBanco);
		assertTrue(usuarioManager.existeLogin(usuario));
	}
	
	@Test
	public void testPopulaCheckOrderNome()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L, usuario);

    	Collection<UsuarioEmpresa> usuarioEmpresas = Arrays.asList(usuarioEmpresa);
    	when(usuarioEmpresaManager.findAllBySelectUsuarioEmpresa(eq(empresa.getId()))).thenReturn(usuarioEmpresas);
    	assertEquals(1, usuarioManager.populaCheckOrderNome(empresa.getId()).size());
    }
	
	@Test
	public void testRemoveAcessoSistema()
	{
		Long[] colaboradoresIds = null;
		usuarioManager.removeAcessoSistema(colaboradoresIds);
		
		verify(usuarioDao, (times(1))).desativaAcessoSistema(eq(true), eq(colaboradoresIds));

	}
	
	@Test
	public void testReativaAcessoSistema()
	{
		Long colaboradorId = 2L;
		usuarioManager.reativaAcessoSistema(colaboradorId);
		verify(usuarioDao, times(1)).reativaAcessoSistema(eq(colaboradorId));
	}
	
	@Test
	public void testDesativaAcessoSistema()
	{
		Long[] colaboradoresIds = null;
		usuarioManager.desativaAcessoSistema(colaboradoresIds);
		verify(usuarioDao, (times(1))).desativaAcessoSistema(eq(false), eq(colaboradoresIds));
	}
	
	@Test
	public void testLoginExiste()
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		Usuario usuarioRetorno = UsuarioFactory.getUsuarioComLogin(2L, "milosa");
		LoginExisteException loginExisteException = null;

		try
		{
			when(usuarioDao.findByLogin(eq(usuario))).thenReturn(usuarioRetorno);
			usuarioManager.save(usuario, null, null, null);
		}
		catch (LoginExisteException e)
		{
			loginExisteException = e;
		}
		catch (SenhaNaoConfereException e)
		{
			fail("Exception não esperada");
		}
		catch (Exception e)
		{
			fail("Exception não esperada");
		}

		assertNotNull(loginExisteException);
	}
	
	@Test
    public void testConfirmaSenha()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L, "1234");
    	SenhaNaoConfereException senhaNaoConfereException = null;
    	try
    	{
    		when(usuarioDao.findByLogin(eq(usuario))).thenReturn(usuario);
    		usuarioManager.save(usuario, null, null, null);
    	}
    	catch (LoginExisteException e)
    	{
    		fail("Exception não esperada");
    	}
    	catch (SenhaNaoConfereException e)
    	{
    		senhaNaoConfereException = e;
    	}
    	catch (Exception e)
    	{
    		fail("Exception não esperada");
    	}

    	assertNotNull(senhaNaoConfereException);
    }
	
	@Test
	public void testCriaUsuario()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L, "1234");
    	usuario.setConfNovaSenha("1234");

    	Long colaboradorId = null;
    	String[] empresasIds = new String[]{};
    	String[] perfils = new String[]{};

    	Usuario usuarioRetorno = null;
    	try
    	{
    		when(usuarioDao.findByLogin(eq(usuario))).thenReturn(usuario);
    		when(usuarioDao.save(eq(usuario))).thenReturn(usuario);
    		usuarioRetorno = usuarioManager.save(usuario, colaboradorId, empresasIds, perfils);
    	}
    	catch (LoginExisteException e)
    	{
    		fail("Exception não esperada");
    	}
    	catch (SenhaNaoConfereException e)
    	{
    		fail("Exception não esperada");
    	}
    	catch (Exception e)
    	{
    		fail("Exception não esperada");
    	}

    	assertEquals(usuario, usuarioRetorno);
    }
	
	@Test	
	public void testAtualizaUsuario()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L, "1234");
    	usuario.setConfNovaSenha("1234");

    	Long colaboradorId = null;
    	String[] empresasIds = new String[]{};
    	String[] perfils = new String[]{};

    	try
    	{
    		when(usuarioDao.findByLogin(eq(usuario))).thenReturn(usuario);
    		usuarioManager.update(usuario, colaboradorId, empresasIds, perfils);
    	}
    	catch (LoginExisteException e)
    	{
    		fail("Exception não esperada");
    	}
    	catch (SenhaNaoConfereException e)
    	{
    		fail("Exception não esperada");
    	}
    	catch (Exception e)
    	{
    		fail("Exception não esperada");
    	}
    }
	
	@Test(expected=ColecaoVaziaException.class)
	public void testCreateAutoException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Usuario usuario = null;  

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	when(colaboradorManager.findSemUsuarios(eq(empresa.getId()), eq(usuario))).thenReturn(colaboradores);
		usuarioManager.createAuto(empresa, null, null);
    }
	
	@Test
	public void testCreateAuto()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	Perfil perfil = new Perfil();

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador);
    	when(colaboradorManager.findSemUsuarios(empresa.getId(), null)).thenReturn(colaboradores);
    	when(usuarioDao.findByLogin(anyString())).thenReturn(null);
    	when(usuarioDao.save(usuario)).thenReturn(usuario);
    	when(usuarioEmpresaManager.findByUsuarioEmpresa(usuario.getId(), empresa.getId())).thenReturn(null);
    	
    	Exception ex = null;
    	try
    	{
    		usuarioManager.createAuto(empresa, perfil, "");
    	}
    	catch (Exception e)
    	{
    		
    		e.printStackTrace();
    		ex = e;
    	}
    	assertNull(ex);
    }
	
	@Test
	public void testFindEmailByPerfilAndGestor()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Collection<Long> areaInativaIds = null;
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	Collection<AreaOrganizacional> areaOrganizacionals = Arrays.asList(areaOrganizacional);
    	String role = "ROLE";
    	Collection<Long> areasIds = Arrays.asList(areaOrganizacional.getId());
    	boolean isVerTodosColaboradores = true;
    	String emailDesconsiderado = null;
    	String[] emails = null;
    	
    	when(areaOrganizacionalManager.findAllListAndInativas(true, areaInativaIds, new Long[]{empresa.getId()})).thenReturn(areaOrganizacionals);
    	when(areaOrganizacionalManager.getAncestrais(eq(areaOrganizacionals), eq(areaOrganizacional.getId()))).thenReturn(areaOrganizacionals);
    	when(usuarioDao.findEmailsByPerfilAndGestor(eq(role), eq(empresa.getId()), eq(areasIds), eq(isVerTodosColaboradores), eq(emailDesconsiderado))).thenReturn(emails);
    	
    	assertArrayEquals(emails, usuarioManager.findEmailByPerfilAndGestor(role, empresa.getId(), areaOrganizacional.getId(), isVerTodosColaboradores, emailDesconsiderado));
    }
	
	@Test
	public void testFindEmpresas() 
	{
		String usuarioNome = "usuario";
		usuarioManager.findEmpresas(usuarioNome);
		verify(usuarioDao, times(1)).findEmpresas(usuarioNome);
	}
	
	@Test
	public void testFindEmailsByUsuario()
	{
		Long[] usuariosIds = {1L, 25L};
		String notEmail = "not@email.com.br";
		usuarioManager.findEmailsByUsuario(usuariosIds, notEmail);
		verify(usuarioDao, times(1)).findEmailsByUsuario(usuariosIds, notEmail);
	}

	@Test
	public void testSetUltimoLogin() 
	{
		Long id = 1L;
		usuarioManager.setUltimoLogin(id);
		verify(usuarioDao, times(1)).setUltimoLogin(id);
	}

	@Test
	public void testFindAllSelectByEmpresa() 
	{
		Long empresaId = 2L;
		usuarioManager.findAllSelect(empresaId);
		verify(usuarioDao, times(1)).findAllSelect(empresaId);
	}
	
	@Test
	public void testUpdateConfiguracoesMensagens() 
	{
		Long usuarioId = 1L;
		String caixasMensagens = "";
		usuarioManager.updateConfiguracoesMensagens(usuarioId, caixasMensagens);
		verify(usuarioDao, times(1)).updateConfiguracoesMensagens(usuarioId, caixasMensagens);
	}

	@Test
	public void testFindEmailsByPerfil() 
	{
		String role = "ROLE_CAD_CANDIDATO";
		Long empresaId = 1L;
		usuarioManager.findEmailsByPerfil(role, empresaId);
		verify(usuarioDao, times(1)).findEmailsByPerfil(role, empresaId);
	}

	@Test
	public void testFindByAreaEstabelecimento()
	{
		Long[] areasIds = new Long[]{};
		Long[] estabelecimentosIds = null;
		usuarioManager.findByAreaEstabelecimento(areasIds, estabelecimentosIds);
		verify(colaboradorManager, times(1)).findUsuarioByAreaEstabelecimento(areasIds, estabelecimentosIds);
	}
		
	@Test
	public void testIsResponsavelOrCoResponsavel(){
		Long usuarioId = 5L;
		usuarioManager.isResponsavelOrCoResponsavel(usuarioId);
		verify(usuarioDao, times(1)).isResponsavelOrCoResponsavel(usuarioId);
	}
	
	@Test
	public void testFindEmailByUsuarioId() {
		Long usuarioId = 1L;
		usuarioManager.findEmailByUsuarioId(usuarioId);
		verify(usuarioDao).findEmailByUsuarioId(usuarioId);
	}
}
