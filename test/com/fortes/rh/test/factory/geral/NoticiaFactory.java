package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Noticia;

public class NoticiaFactory
{
	public static Noticia getEntity()
	{
		Noticia noticia = new Noticia();
		noticia.setId(null);
		noticia.setCriticidade(0);
		noticia.setPublicada(true);
		noticia.setTexto("Notícia");
		noticia.setLink("link");

		return noticia;
	}
	
	public static Noticia getEntity(Long id, String link, int criticidade)
	{
		Noticia noticia = getEntity();
		noticia.setId(id);
		noticia.setLink(link);
		noticia.setCriticidade(criticidade);
		
		return noticia;
	}

}
