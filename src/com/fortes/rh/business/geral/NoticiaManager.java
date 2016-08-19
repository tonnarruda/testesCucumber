package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Noticia;

public interface NoticiaManager extends GenericManager<Noticia>
{
	Collection<Noticia> findByUsuario(Long usuarioId);
	void carregarUltimasNoticias(Long usuarioId);
	void importarUltimasNoticias();
	Noticia find(String texto, String link, Integer criticidade);
	void despublicarTodas();
	Collection<Noticia> findUrgentesNaoLidasPorUsuario(Long usuarioId);
}