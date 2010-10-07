package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;

public interface AgendaManager extends GenericManager<Agenda>
{
	Collection<Agenda> findByPeriodo(Date dataIni, Date dataFim, Long empresaId, Estabelecimento estabelecimento);
	Collection<Agenda> findByPeriodoEvento(Date dataIni, Date dataFim, Estabelecimento estabelecimento, Evento evento);
	Agenda findByIdProjection(Long id);
	void save(Agenda agenda, Integer qtdPeriodo, Integer periodicidade, Integer tipoPeriodo) throws Exception;
}