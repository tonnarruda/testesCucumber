package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class BairroListAction extends MyActionSupportList
{
	private BairroManager bairroManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;

	private Collection<Bairro> bairros = new ArrayList<Bairro>();
	private Collection<Estado> estados = new ArrayList<Estado>();
	private Collection<Cidade> cidades = new ArrayList<Cidade>();

	private Bairro bairro;
	private Estado estado;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		this.setTotalSize(bairroManager.getCount());

		bairros = bairroManager.list(this.getPage(), this.getPagingSize(), bairro);
		estados = estadoManager.findAll(new String[]{"sigla"});

		if(estado != null && estado.getId() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{Long.valueOf(estado.getId())}, new String[]{"nome"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			bairroManager.remove(new Long[]{bairro.getId()});
			addActionMessage("Bairro excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este bairro.");
		}

		return list();
	}

	public Collection<Bairro> getBairros() {
		return bairros;
	}

	public Bairro getBairro(){
		if(bairro == null){
			bairro = new Bairro();
		}
		return bairro;
	}

	public void setBairro(Bairro bairro){
		this.bairro=bairro;
	}

	public void setBairroManager(BairroManager bairroManager){
		this.bairroManager=bairroManager;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public Collection<Estado> getEstados()
	{
		return estados;
	}

	public Estado getEstado()
	{
		return estado;
	}

	public void setEstado(Estado estado)
	{
		this.estado = estado;
	}

	public Collection<Cidade> getCidades()
	{
		return cidades;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}
}