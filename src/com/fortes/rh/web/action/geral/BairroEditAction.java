package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class BairroEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private BairroManager bairroManager;
	@Autowired private EstadoManager estadoManager;
	@Autowired private CidadeManager cidadeManager;

	private Bairro bairro;
	private Bairro bairroDestino;
	private Estado estado;
	private Cidade cidade;

	private Collection<Estado> estados = new ArrayList<Estado>();
	private Collection<Cidade> cidades = new ArrayList<Cidade>();
	private Collection<Bairro> bairros = new ArrayList<Bairro>();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(bairro != null && bairro.getId() != null)
		{
			bairro = bairroManager.findById(bairro.getId());
			estado = bairro.getCidade().getUf();
		}

		cidades = cidadeManager.findByEstado(estado);

		this.estados = estadoManager.findAll(new String[]{"sigla"});
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
		if(!bairroManager.existeBairro(bairro))
		{
			bairroManager.save(bairro);
			return Action.SUCCESS;
		}
		else
		{
			addActionError("Este bairro já existe na cidade selecionada.");
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		if(!bairroManager.existeBairro(bairro))
		{
			bairroManager.update(bairro);
			return Action.SUCCESS;
		}
		else
		{
			addActionError("Este bairro já existe da cidade selecionada.");
			prepareUpdate();
			return Action.INPUT;
		}
	}

	public String prepareMigrar() throws Exception
	{
		estados = estadoManager.findAll(new String[]{"sigla"});
		
		cidades = cidadeManager.findByEstado(estado);			
		bairros = bairroManager.findByCidade(cidade);
		
		return Action.SUCCESS;
	}

	public String migrar() throws Exception
	{
		if(bairro.getId().equals(bairroDestino.getId()))
			addActionError("Não é permitido transferir registros para o mesmo Bairro.");
		else
		{
			try
			{
				bairroManager.migrarRegistros(bairro, bairroDestino);			
				addActionMessage("Registros transferidos com sucesso.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addActionError("Erro ao transferir registros.");
			}			
		}
		
		prepareMigrar();
		bairro = null;
		bairroDestino = null;
		
		return Action.SUCCESS;
	}
	
	public Object getModel()
	{
		return getBairro();
	}

	public Bairro getBairro()
	{
		if(bairro == null)
			bairro = new Bairro();
		return bairro;
	}

	public void setBairro(Bairro bairro)
	{
		this.bairro = bairro;
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

	public Collection<Bairro> getBairros()
	{
		return bairros;
	}

	public Bairro getBairroDestino()
	{
		return bairroDestino;
	}

	public void setBairroDestino(Bairro bairroDestino)
	{
		this.bairroDestino = bairroDestino;
	}

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}
}