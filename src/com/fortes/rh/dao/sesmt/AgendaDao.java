package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;

public interface AgendaDao extends GenericDao<Agenda> 
{

	Collection<Agenda> findByPeriodo(Date dataIni, Date dataFim, Long empresaId, Estabelecimento estabelecimento);
	Collection<Agenda> findByPeriodoEvento(Date dataIni, Date dataFim, Estabelecimento estabelecimento, Evento evento);
	Agenda findByIdProjection(Long id);
}
