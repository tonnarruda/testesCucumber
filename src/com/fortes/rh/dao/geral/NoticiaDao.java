package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Noticia;

public interface NoticiaDao extends GenericDao<Noticia> 
{
	Collection<Noticia> findByUsuario(Long usuarioId);
	Noticia findByTexto(String texto);
	void despublicarTodas();
}
