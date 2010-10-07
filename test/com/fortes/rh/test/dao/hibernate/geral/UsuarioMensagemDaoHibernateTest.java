package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.factory.geral.UsuarioMensagemFactory;

public class UsuarioMensagemDaoHibernateTest extends GenericDaoHibernateTest
{
	private UsuarioDao usuarioDao;
	private MensagemDao mensagemDao;
	private EmpresaDao empresaDao;
	private UsuarioMensagemDao usuarioMensagemDao;

	public UsuarioMensagem getEntity()
	{
		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();

		usuarioMensagem.setId(null);

		return usuarioMensagem;
	}

	public GenericDao<UsuarioMensagem> getGenericDao()
	{
		return usuarioMensagemDao;
	}

	public void testListaUsuarioMensagem()
	{
		Mensagem mensagem = MensagemFactory.getEntity();
		mensagem = mensagemDao.save(mensagem);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setLida(false);

		usuarioMensagem = usuarioMensagemDao.save(usuarioMensagem);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem);

		Collection<UsuarioMensagem> retorno = usuarioMensagemDao.listaUsuarioMensagem(usuario.getId(), empresa.getId());

		assertEquals(usuarioMensagems.size(), retorno.size());
	}

	public void testFindByIdProjection()
	{
		Mensagem mensagem = MensagemFactory.getEntity();
		mensagem = mensagemDao.save(mensagem);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setLida(false);

		usuarioMensagem = usuarioMensagemDao.save(usuarioMensagem);

		UsuarioMensagem retorno = usuarioMensagemDao.findByIdProjection(usuarioMensagem.getId(), empresa.getId());

		assertEquals(usuarioMensagem.getId(), retorno.getId());
	}

	public void testPossuiMensagemNaoLidaRetornaVerdadeiro()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		UsuarioMensagem usuarioMensagem1 = new UsuarioMensagem();
		usuarioMensagem1.setUsuario(usuario);
		usuarioMensagem1.setLida(false);
		usuarioMensagem1.setEmpresa(empresa);
		usuarioMensagem1 = usuarioMensagemDao.save(usuarioMensagem1);

		UsuarioMensagem usuarioMensagem2 = new UsuarioMensagem();
		usuarioMensagem2.setUsuario(usuario);
		usuarioMensagem2.setLida(false);
		usuarioMensagem2.setEmpresa(empresa);
		usuarioMensagem2 = usuarioMensagemDao.save(usuarioMensagem2);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem1);
		usuarioMensagems.add(usuarioMensagem2);

		boolean retorno = usuarioMensagemDao.possuiMensagemNaoLida(usuario.getId(), empresa.getId());

		assertEquals(true, retorno);
	}

	public void testPossuiMensagemNaoLidaRetornaFalso()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
				
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		UsuarioMensagem usuarioMensagem1 = new UsuarioMensagem();
		usuarioMensagem1.setUsuario(usuario);
		usuarioMensagem1.setLida(true);
		usuarioMensagem1.setEmpresa(empresa);
		usuarioMensagem1 = usuarioMensagemDao.save(usuarioMensagem1);

		UsuarioMensagem usuarioMensagem2 = new UsuarioMensagem();
		usuarioMensagem1.setUsuario(usuario);
		usuarioMensagem2.setLida(true);
		usuarioMensagem2.setEmpresa(empresa);
		usuarioMensagem2 = usuarioMensagemDao.save(usuarioMensagem2);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem1);
		usuarioMensagems.add(usuarioMensagem2);

		boolean retorno = usuarioMensagemDao.possuiMensagemNaoLida(usuario.getId(), empresa.getId());

		assertEquals(false, retorno);
	}

	public void testFindProximo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		UsuarioMensagem usuarioMensagem1 = UsuarioMensagemFactory.getEntity();
		usuarioMensagem1.setUsuario(usuario);
		usuarioMensagem1.setEmpresa(empresa);
		usuarioMensagemDao.save(usuarioMensagem1);
		UsuarioMensagem usuarioMensagem2 = UsuarioMensagemFactory.getEntity();
		usuarioMensagem2.setUsuario(usuario);
		usuarioMensagem2.setEmpresa(empresa);
		usuarioMensagemDao.save(usuarioMensagem2);

		assertNotNull(usuarioMensagemDao.findAnteriorOuProximo(usuarioMensagem1.getId(), usuario.getId(), usuarioMensagem1.getEmpresa().getId(), 'P'));
	}

	public void setUsuarioMensagemDao(UsuarioMensagemDao usuarioMensagemDao)
	{
		this.usuarioMensagemDao = usuarioMensagemDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setMensagemDao(MensagemDao mensagemDao)
	{
		this.mensagemDao = mensagemDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

}
