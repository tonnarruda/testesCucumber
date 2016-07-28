package com.fortes.rh.test.business.acesso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.core.Constraint;

import com.fortes.rh.business.acesso.UsuarioManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.exception.LoginExisteException;
import com.fortes.rh.exception.SenhaNaoConfereException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.util.StringUtil;

public class UsuarioManagerTest extends MockObjectTestCaseManager<UsuarioManagerImpl> implements TesteAutomaticoManager
{
	Mock usuarioDao;
	Mock colaboradorManager;
	Mock usuarioEmpresaManager;
	Mock areaOrganizacionalManager;
	Mock gerenciadorComunicacaoManager;

    protected void setUp() throws Exception
    {
        manager = new UsuarioManagerImpl();

        usuarioDao = new Mock(UsuarioDao.class);
        manager.setDao((UsuarioDao) usuarioDao.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        manager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
        manager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        manager.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacionalManager.proxy());
        
        gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
        manager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
    }

    public void testFindByLogin()
    {
    	String login = "login";
    	Usuario usuario = new Usuario();
    	usuario.setId(1L);

		usuarioDao.expects(once()).method("findByLogin").with(eq(login)).will(returnValue(usuario));
    	assertEquals(usuario, manager.findByLogin(login));
    }

    public void testSave()
    {
    	Usuario usuario = new Usuario();
    	usuario.setId(1L);
    	usuario.setSenha("senha");

    	usuarioDao.expects(once()).method("save").with(eq(usuario)).will(returnValue(usuario));
    	assertEquals(usuario, manager.save(usuario));
    }

    public void testUpdate()
    {
    	Usuario usuario = new Usuario();
    	usuario.setId(1L);
    	usuario.setSenha("senha");

    	usuarioDao.expects(once()).method("update").with(eq(usuario));

    	manager.update(usuario);
    }

    public void testUpdateMantendoSenha()
    {
    	Usuario usuario = new Usuario();
    	usuario.setId(1L);

    	usuarioDao.expects(once()).method("update").with(eq(usuario));
    	usuarioDao.expects(once()).method("findById").with(eq(usuario.getId())).will(returnValue(usuario));

    	manager.update(usuario);
    }

    public void testPrepareCriarUsuario()
    {
    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);

    	colaboradorManager.expects(once()).method("findByIdProjectionUsuario").with(eq(colaborador.getId())).will(returnValue(colaborador));

    	assertEquals(colaborador, manager.prepareCriarUsuario(colaborador));
    }

    public void testUpdateSenha()
    {
    	Usuario usuario = new Usuario();
    	usuario.setId(1L);
    	usuario.setSenha("senha");
    	usuario.setNovaSenha("senha nova");
    	usuario.setConfNovaSenha("senha nova");

    	Usuario usuarioOriginal = new Usuario();
    	usuarioOriginal.setId(1L);
    	usuarioOriginal.setSenha(StringUtil.encodeString("senha"));

//    	usuarioDao.expects(once()).method("update").with(eq(usuario));
    	usuarioDao.expects(atLeastOnce()).method("findById").with(eq(usuario.getId())).will(returnValue(usuarioOriginal));
    	usuarioDao.expects(once()).method("update").with(eq(usuario));

    	manager.updateSenha(usuario);
    }

    public void testFindUsuarios()
    {

    	int page = 1;
    	int pagingSize = 1;
    	String nomeBusca = "nome";
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	Collection col = new ArrayList();

    	usuarioDao.expects(once()).method("findUsuarios").with(eq(page), eq(pagingSize), eq(nomeBusca), eq(empresa)).will(returnValue(col));

    	assertEquals(col, manager.findUsuarios(page, pagingSize, nomeBusca, empresa));
    }

    public void testGetCountUsuario()
    {

    	String nomeBusca = "nome";
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	Integer i = Integer.valueOf(1);

    	usuarioDao.expects(once()).method("getCountUsuario").with(eq(nomeBusca), eq(empresa)).will(returnValue(i));

    	assertEquals(i, manager.getCountUsuario(nomeBusca, empresa));
    }

    public void testFindAllSelect()
    {
    	Collection<Usuario> usuarios = new ArrayList<Usuario>();

    	usuarioDao.expects(once()).method("findAllSelect").will(returnValue(usuarios));

    	assertEquals(usuarios, manager.findAllSelect());
    }

    public void testFindAllBySelectUsuarioEmpresa()
    {
    	Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();

    	UsuarioEmpresa us1 = new UsuarioEmpresa();
    	us1.setUsuario(new Usuario());

    	UsuarioEmpresa us2 = new UsuarioEmpresa();
    	us2.setUsuario(new Usuario());

    	UsuarioEmpresa us3 = new UsuarioEmpresa();
    	us3.setUsuario(new Usuario());

    	usuarioEmpresas.add(us1);
    	usuarioEmpresas.add(us2);
    	usuarioEmpresas.add(us3);

    	usuarioEmpresaManager.expects(once()).method("findAllBySelectUsuarioEmpresa").will(returnValue(usuarioEmpresas));

    	assertEquals(3, manager.findAllBySelectUsuarioEmpresa(1L).size());
    }

    public void testRemoveUsuario() throws Exception
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L);

    	UsuarioEmpresa us1 = UsuarioEmpresaFactory.getEntity(1L, usuario, null, null);
    	UsuarioEmpresa us2 = UsuarioEmpresaFactory.getEntity(2L, usuario, null, null);
    	UsuarioEmpresa us3 = UsuarioEmpresaFactory.getEntity(3L, usuario, null, null);

    	Collection<UsuarioEmpresa> usuarioEmpresas = Arrays.asList(us1, us2, us3);

    	usuarioEmpresaManager.expects(atLeastOnce()).method("find").with(ANYTHING, ANYTHING).will(returnValue(usuarioEmpresas));
    	usuarioEmpresaManager.expects(atLeastOnce()).method("remove").with(ANYTHING);
    	usuarioDao.expects(atLeastOnce()).method("remove").with(ANYTHING);

    	manager.removeUsuario(usuario);

    	manager.setUsuarioEmpresaManager(null);

    	Exception exp = null;

    	try
		{
    		manager.removeUsuario(usuario);
		}
		catch (Exception e)
		{
			exp = e;
		}

		assertNotNull(exp);
    }

    public void testExisteLogin()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	usuario.setLogin("log");

    	usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(null));
    	assertEquals(false, manager.existeLogin(usuario));

    	Usuario usuarioRetorno = UsuarioFactory.getEntity(1L);
    	usuarioRetorno.setLogin("log");

    	usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(usuarioRetorno));
    	assertEquals(false, manager.existeLogin(usuario));

    	usuarioRetorno = UsuarioFactory.getEntity(2L);
    	usuarioRetorno.setLogin("log");

    	usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(usuarioRetorno));
    	assertEquals(true, manager.existeLogin(usuario));
    }

    public void testDesativaAcessoSistema()
    {
    	usuarioDao.expects(once()).method("desativaAcessoSistema").with(ANYTHING, ANYTHING);

    	manager.removeAcessoSistema(null);
    }

    public void testPopulaCheckOrderNome()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Usuario usuario = new Usuario();
    	usuario.setId(1L);

    	UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
    	usuarioEmpresa.setId(1L);
    	usuarioEmpresa.setUsuario(usuario);

    	Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
    	usuarioEmpresas.add(usuarioEmpresa);

    	usuarioEmpresaManager.expects(once()).method("findAllBySelectUsuarioEmpresa").with(eq(empresa.getId())).will(returnValue(usuarioEmpresas));

    	assertEquals(1, manager.populaCheckOrderNome(empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeException()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
    	usuarioEmpresaManager.expects(once()).method("findAllBySelectUsuarioEmpresa").with(eq(empresa.getId())).will(returnValue(usuarioEmpresas));

    	assertEquals(0, manager.populaCheckOrderNome(empresa.getId()).size());
    }

    public void testLoginExiste()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	Usuario usuarioRetorno = UsuarioFactory.getEntity(2L);
    	usuario.setLogin("milosa");

    	LoginExisteException loginExisteException = null;

    	try
		{
    		usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(usuarioRetorno));
			manager.save(usuario, null, null, null);
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

    public void testConfirmaSenha()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	usuario.setSenha("1234");

    	SenhaNaoConfereException senhaNaoConfereException = null;

    	try
    	{
    		usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(usuario));
    		manager.save(usuario, null, null, null);
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

    public void testCriaUsuario()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	usuario.setSenha("1234");
    	usuario.setConfNovaSenha("1234");

    	Long colaboradorId = null;
    	String[] empresasIds = new String[]{};
    	String[] perfils = new String[]{};

    	Usuario usuarioRetorno = null;
    	try
    	{
    		usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(usuario));
    		usuarioDao.expects(once()).method("save").with(eq(usuario)).will(returnValue(usuario));
    		colaboradorManager.expects(once()).method("atualizarUsuario").with(eq(colaboradorId), eq(usuario.getId()));
//    		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaboradorId), ANYTHING);
    		usuarioEmpresaManager.expects(once()).method("save").with(eq(usuario), eq(empresasIds), eq(perfils));

    		usuarioRetorno = manager.save(usuario, colaboradorId, empresasIds, perfils);
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

    public void testAtualizaUsuario()
    {
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	usuario.setSenha("1234");
    	usuario.setConfNovaSenha("1234");

    	Long colaboradorId = null;
    	String[] empresasIds = new String[]{};
    	String[] perfils = new String[]{};

    	try
    	{
    		usuarioDao.expects(once()).method("findByLogin").with(eq(usuario)).will(returnValue(usuario));
    		usuarioDao.expects(once()).method("update").with(eq(usuario)).isVoid();
    		colaboradorManager.expects(once()).method("atualizarUsuario").with(eq(colaboradorId), eq(usuario.getId()));
    		usuarioEmpresaManager.expects(once()).method("removeAllUsuario").with(eq(usuario)).isVoid();
//    		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaboradorId), ANYTHING);
    		usuarioEmpresaManager.expects(once()).method("save").with(eq(usuario), eq(empresasIds), eq(perfils));

    		manager.update(usuario, colaboradorId, empresasIds, perfils);
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

    public void testCreateAutoException()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradorManager.expects(once()).method("findSemUsuarios").with(eq(empresa.getId()), eq(null)).will(returnValue(colaboradores));

    	Exception ex = null;
    	try
		{
			manager.createAuto(empresa, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNotNull(ex);
    }

    public void testCreateAuto()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);
    	Perfil perfil = new Perfil();

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador);
    	colaboradorManager.expects(once()).method("findSemUsuarios").with(eq(empresa.getId()), eq(null)).will(returnValue(colaboradores));
    	usuarioDao.expects(once()).method("findByLogin").with(ANYTHING).will(returnValue(null));
    	usuarioDao.expects(once()).method("save").with(ANYTHING);
    	usuarioEmpresaManager.expects(once()).method("findByUsuarioEmpresa").with(ANYTHING, eq(empresa.getId())).will(returnValue(null));
    	usuarioEmpresaManager.expects(once()).method("save").with(ANYTHING);
    	colaboradorManager.expects(once()).method("atualizarUsuario").with(eq(colaborador.getId()), ANYTHING);
    	gerenciadorComunicacaoManager.expects(once()).method("enviarEmailAoCriarAcessoSistema");
    	
    	Exception ex = null;
    	try
    	{
    		manager.createAuto(empresa, perfil, "");
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		ex = e;
    	}

    	assertNull(ex);
    }
    
    public void testFindEmailByPerfilAndGestor()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	Collection<AreaOrganizacional> areaOrganizacionals = Arrays.asList(areaOrganizacional);
    	String role = "ROLE";
    	Collection<Long> areasIds = Arrays.asList(areaOrganizacional.getId());
    	boolean isVerTodosColaboradores = true;
    	String emailDesconsiderado = null;
    	String[] emails = null;
    	
    	areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(true), eq(null), eq(new Long[]{empresa.getId()})).will(returnValue(areaOrganizacionals));
    	areaOrganizacionalManager.expects(once()).method("getAncestrais").with(eq(areaOrganizacionals), eq(areaOrganizacional.getId())).will(returnValue(areaOrganizacionals));
    	usuarioDao.expects(once()).method("findEmailsByPerfilAndGestor").with(new Constraint[]{eq(role), eq(empresa.getId()), eq(areasIds), eq(isVerTodosColaboradores), eq(emailDesconsiderado)}).will(returnValue(emails));
    	
    	assertEquals(emails, manager.findEmailByPerfilAndGestor(role, empresa.getId(), areaOrganizacional.getId(), isVerTodosColaboradores, emailDesconsiderado));
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(usuarioDao);
	}
}
