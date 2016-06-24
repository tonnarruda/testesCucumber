package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.EventoManager;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class EventoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private EventoManager eventoManager;
	private Evento evento;
	private Collection<Evento> eventos;

	private void prepare() throws Exception
	{
		if(evento != null && evento.getId() != null)
			evento = (Evento) eventoManager.findById(evento.getId());

	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		eventoManager.save(evento);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		eventoManager.update(evento);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		eventos = eventoManager.findAll(new String[]{"nome"});
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getEvento();
	}

	public String delete() throws Exception
	{
		try
		{
			eventoManager.remove(evento.getId());
			addActionSuccess("Evento excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este evento.");
		}

		return list();
	}
	
	public Evento getEvento()
	{
		if(evento == null)
			evento = new Evento();
		return evento;
	}

	public void setEvento(Evento evento)
	{
		this.evento = evento;
	}

	public void setEventoManager(EventoManager eventoManager)
	{
		this.eventoManager = eventoManager;
	}
	
	public Collection<Evento> getEventos()
	{
		return eventos;
	}
}
