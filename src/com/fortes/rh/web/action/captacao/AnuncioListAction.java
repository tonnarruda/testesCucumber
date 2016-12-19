package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AnuncioListAction extends MyActionSupportList
{
	@Autowired private AnuncioManager anuncioManager;

	private Collection<Anuncio> anuncios;

	private Anuncio anuncio;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		anuncios = anuncioManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		anuncioManager.remove(anuncio.getId());

		return Action.SUCCESS;
	}

	public Collection<Anuncio> getAnuncios()
	{
		return anuncios;
	}

	public Anuncio getAnuncio()
	{
		if (anuncio == null)
		{
			anuncio = new Anuncio();
		}
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio)
	{
		this.anuncio = anuncio;
	}
}