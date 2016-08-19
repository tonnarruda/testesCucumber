package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.NoticiaDao;
import com.fortes.rh.dao.geral.UsuarioNoticiaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.UsuarioNoticia;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.geral.NoticiaFactory;
import com.fortes.rh.test.factory.geral.UsuarioNoticiaFactory;
import com.fortes.rh.util.CollectionUtil;

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
	
	public void testFind()
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
		
		assertEquals(noticia3.getId(), noticiaDao.find("noticia 1", null, null).getId());
	}
	
	public void testFindUrgentesNaoLidasPorUsuario()
	{
		Usuario usuario1 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario1);
		
		Usuario usuario2 = UsuarioFactory.getEntity();
		usuarioDao.save(usuario2);
		
		Noticia noticia1 = NoticiaFactory.getEntity(null, "link 1", -1);
		noticiaDao.save(noticia1);
		
		Noticia noticia2 = NoticiaFactory.getEntity(null, "link 2", -1);
		noticiaDao.save(noticia2);
		
		UsuarioNoticia usuarioNoticia1 = UsuarioNoticiaFactory.getEntity(null, usuario1, noticia1);
		usuarioNoticiaDao.save(usuarioNoticia1);
		
		UsuarioNoticia usuarioNoticia2 = UsuarioNoticiaFactory.getEntity(null, usuario1, noticia2);
		usuarioNoticiaDao.save(usuarioNoticia2);
		
		UsuarioNoticia usuarioNoticia3 = UsuarioNoticiaFactory.getEntity(null, usuario2, noticia2);
		usuarioNoticiaDao.save(usuarioNoticia3);
		
		Collection<Noticia> noticias1 = noticiaDao.findUrgentesNaoLidasPorUsuario(usuario1.getId());
		Collection<Noticia> noticias2 = noticiaDao.findUrgentesNaoLidasPorUsuario(usuario2.getId());
		
		assertFalse("Não contêm notícias na coleção 1", CollectionUtils.containsAny(noticias1, Arrays.asList(noticia1,noticia2)));
		assertFalse("Não contêm notícia 2 na coleção 2", CollectionUtils.containsAny(noticias2, Arrays.asList(noticia2)));
		assertTrue("Contêm notícia 1 na coleção 2", CollectionUtils.containsAny(noticias2, Arrays.asList(noticia1)));
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