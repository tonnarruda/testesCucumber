package com.fortes.rh.security.spring.aop;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EventoDao;
import com.fortes.rh.model.sesmt.Evento;

/**
 * Manager utilizado apenas para testar Auditoria
 */
public class SomeManagerImpl extends GenericManagerImpl<Evento, EventoDao> implements SomeManager {

	@Override
	public Evento save(Evento entity) {
		return null;
	}
	
	@Override
	public void remove(Long id) {
	}
	
	@Override
	public void update(Evento entity) {
		Evento ev = entity;
	}
	
	@Override
	public Collection<Evento> findAll() {
		return null;
	}
	
	@Override
	public Evento findEntidadeComAtributosSimplesById(Long id) {
		Evento evento = new Evento();
		evento.setId(id);
		evento.setNome("Minha Festa");
		return evento;
	}
}
