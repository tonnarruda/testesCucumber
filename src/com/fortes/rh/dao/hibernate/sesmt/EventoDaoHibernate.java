package com.fortes.rh.dao.hibernate.sesmt;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EventoDao;
import com.fortes.rh.model.sesmt.Evento;

@Component
public class EventoDaoHibernate extends GenericDaoHibernate<Evento> implements EventoDao
{
}
