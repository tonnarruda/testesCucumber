package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class UsuarioEmpresaDaoHibernateTest extends GenericDaoHibernateTest<UsuarioEmpresa>
{
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;
	private UsuarioEmpresaDao usuarioEmpresaDao;
	private PapelDao papelDao;
	private PerfilDao perfilDao;

	public UsuarioEmpresa getEntity()
	{
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();

		usuarioEmpresa.setId(null);
		return usuarioEmpresa;
	}

	public void testFindAllBySelectUsuarioEmpresa()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		assertEquals(usuarioEmpresa, usuarioEmpresaDao.findAllBySelectUsuarioEmpresa(empresa.getId()).toArray()[0]);
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);
		
		assertEquals(usuarioEmpresa, usuarioEmpresaDao.findByUsuario(usuario.getId()).toArray()[0]);
	}

	public void testFindAllBySelectUsuarioEmpresaNull()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		assertTrue(usuarioEmpresaDao.findAllBySelectUsuarioEmpresa(empresa.getId()).isEmpty());
	}

	public void testRemoveAllUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa = usuarioEmpresaDao.save(usuarioEmpresa);

		usuarioEmpresaDao.removeAllUsuario(usuario);

		assertNull(usuarioEmpresaDao.findById(usuarioEmpresa.getId(), null));
	}
	
	public void testFindUsuariosByEmpresaRoleSetorPessoal()
	{
		Papel papel1 = new Papel();
		papel1.setCodigo("RECEBE_ALERTA_SETORPESSOAL");
		papel1 = papelDao.save(papel1);

		Papel papel2 = new Papel();
		papel2.setCodigo("TESTE");
		papel2 = papelDao.save(papel2);
		
		Collection<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel1);
		papeis.add(papel2);
		
		Perfil perfil1 = new Perfil();
		perfil1.setPapeis(papeis);
		perfil1 = perfilDao.save(perfil1);

		Perfil perfil2 = new Perfil();
		perfil2.setPapeis(papeis);
		perfil2 = perfilDao.save(perfil2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("01020304");
		empresa = empresaDao.save(empresa);
		
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuario1 = usuarioDao.save(usuario1);

		Usuario usuario2 = UsuarioFactory.getEntity();
		usuario2 = usuarioDao.save(usuario2);
		
		UsuarioEmpresa usuarioEmpresa1 = new UsuarioEmpresa();
		usuarioEmpresa1.setUsuario(usuario1);
		usuarioEmpresa1.setEmpresa(empresa);
		usuarioEmpresa1.setPerfil(perfil1);
		usuarioEmpresa1 = usuarioEmpresaDao.save(usuarioEmpresa1);

		UsuarioEmpresa usuarioEmpresa2 = new UsuarioEmpresa();
		usuarioEmpresa2.setUsuario(usuario2);
		usuarioEmpresa2.setEmpresa(empresa);
		usuarioEmpresa2.setPerfil(perfil2);
		usuarioEmpresa2 = usuarioEmpresaDao.save(usuarioEmpresa2);
		
		UsuarioEmpresa usuarioEmpresa3 = new UsuarioEmpresa();
		usuarioEmpresa3.setUsuario(usuario2);
		usuarioEmpresa3.setEmpresa(empresa);
		usuarioEmpresa3= usuarioEmpresaDao.save(usuarioEmpresa3);
		
		// por codigoAC
		assertEquals(2, usuarioEmpresaDao.findUsuariosByEmpresaRoleSetorPessoal(empresa.getCodigoAC(), null).size());
		
		// por empresaId
		assertEquals(2, usuarioEmpresaDao.findUsuariosByEmpresaRoleSetorPessoal(null, empresa.getId()).size());
	}

	public GenericDao<UsuarioEmpresa> getGenericDao()
	{
		return usuarioEmpresaDao;
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

	public void setPapelDao(PapelDao papelDao)
	{
		this.papelDao = papelDao;
	}

	public void setPerfilDao(PerfilDao perfilDao)
	{
		this.perfilDao = perfilDao;
	}

}