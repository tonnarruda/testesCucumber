package com.fortes.rh.web.action.geral;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ClienteManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Cliente;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ClienteEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ClienteManager clienteManager;
	private Cliente cliente;
	private Collection<Cliente> clientes;
	private boolean usuarioFortes=false;

	private void prepare() throws Exception
	{
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		usuarioFortes = usuarioLogado.getId() == 1L;
		
		if(cliente != null && cliente.getId() != null)
			cliente = (Cliente) clienteManager.findById(cliente.getId());
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
		clienteManager.save(cliente);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		clienteManager.update(cliente);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		prepare();
		
		clientes = clienteManager.findAll(new String[]{"nome"});
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			clienteManager.remove(cliente.getId());
			addActionMessage("Cliente excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este cliente.");
		}

		return SUCCESS;
	}
	
	public Cliente getCliente()
	{
		if(cliente == null)
			cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}
	
	public Collection<Cliente> getClientes()
	{
		return clientes;
	}

	public boolean isUsuarioFortes() {
		return usuarioFortes;
	}

	public void setUsuarioFortes(boolean usuarioFortes) {
		this.usuarioFortes = usuarioFortes;
	}
}