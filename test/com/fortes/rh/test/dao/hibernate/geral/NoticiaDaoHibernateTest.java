package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.NoticiaDao;
import com.fortes.rh.dao.geral.UsuarioNoticiaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.UsuarioNoticia;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;

public class NoticiaDaoHibernateTest extends GenericDaoHibernateTest<Noticia>
{
	private NoticiaDao noticiaDao;
	private UsuarioNoticiaDao usuarioNoticiaDao;
	private UsuarioDao usuarioDao;

	public Noticia getEntity()
	{
		Noticia noticia = new Noticia();
		noticia.setTexto("Conheça a Fortes");
		noticia.setLink("http://www.fortesinformatica.com.br");
		noticia.setCriticidade(2);
		noticia.setPublicada(true);

		return noticia;
	}
	
	public void testFindByUsuario()
	{
		Noticia noticia1 = getEntity();
		noticiaDao.save(noticia1);

		Noticia noticia2 = getEntity();
		noticiaDao.save(noticia2);
		
		Noticia noticia3 = getEntity();
		noticiaDao.save(noticia3);
		
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		UsuarioNoticia un1 = new UsuarioNoticia(usuario1, noticia1);
		usuarioNoticiaDao.save(un1);
		
		UsuarioNoticia un2 = new UsuarioNoticia(usuario1, noticia2);
		usuarioNoticiaDao.save(un2);
		
		assertTrue("Usuario 1", noticiaDao.findByUsuario(usuario1.getId()).size() >= 3);
		assertTrue("Usuario 2", noticiaDao.findByUsuario(usuario2.getId()).size() >= 3);
	}
	
	public void testFindByTexto()
	{
		Noticia noticia1 = getEntity();
		noticia1.setTexto("noticia 1");
		noticiaDao.save(noticia1);

		Noticia noticia2 = getEntity();
		noticia2.setTexto("noticia 2");
		noticiaDao.save(noticia2);
		
		Noticia noticia3 = getEntity();
		noticia3.setTexto("noticia 1");
		noticiaDao.save(noticia3);
		
		assertEquals(noticia3.getId(), noticiaDao.findByTexto("noticia 1").getId());
	}

	public void setNoticiaDao(NoticiaDao noticiaDao) {
		this.noticiaDao = noticiaDao;
	}

	public void setUsuarioNoticiaDao(UsuarioNoticiaDao usuarioNoticiaDao) {
		this.usuarioNoticiaDao = usuarioNoticiaDao;
	}

	public GenericDao<Noticia> getGenericDao() 
	{
		return this.noticiaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}