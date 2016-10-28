package com.fortes.rh.business.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EventoDao;
import com.fortes.rh.model.sesmt.Evento;

@Component
public class EventoManagerImpl extends GenericManagerImpl<Evento, EventoDao> implements EventoManager
{
	@Autowired
	EventoManagerImpl(EventoDao eventoDao) {
		setDao(eventoDao);
	}
}
