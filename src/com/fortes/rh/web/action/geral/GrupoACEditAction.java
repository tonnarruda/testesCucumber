package com.fortes.rh.web.action.geral;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class GrupoACEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private GrupoACManager grupoACManager;
	private GrupoAC grupoAC;
	private Collection<GrupoAC> grupoACs;

	private void prepare() throws Exception
	{
		if(grupoAC != null && grupoAC.getId() != null)
			grupoAC = (GrupoAC) grupoACManager.findById(grupoAC.getId());

	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		addActionMessage("Cuidado ao alterar o código, ele pode estar sendo utilizado no Fortes Pessoal.");
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			grupoAC.setAcSenha(grupoAC.getAcSenha().toUpperCase());
			grupoACManager.save(grupoAC);
			return Action.SUCCESS;
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			prepareInsert();
			addActionError("Não é permitido cadastrar Grupo com o mesmo código.");
			return Action.INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			prepareInsert();
			addActionError("Erro ao gravar Grupo");
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			grupoAC.setAcSenha(grupoAC.getAcSenha().toUpperCase());
			grupoACManager.updateGrupo(grupoAC);
			return Action.SUCCESS;
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			prepareUpdate();
			addActionError("Não é permitido cadastrar Grupo com o mesmo código.");
			return Action.INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			prepareUpdate();
			addActionError("Erro ao editar Grupo");
			return Action.INPUT;
		}
	}

	public String list() throws Exception
	{
		grupoACs = grupoACManager.findAll(new String[]{"codigo"});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			grupoACManager.remove(grupoAC.getId());
			addActionMessage("Grupo AC excluído com sucesso.");
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este Grupo AC! Existe Empresa utilizando este grupo.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este Grupo AC.");
		}

		return list();
	}
	
	public GrupoAC getGrupoAC()
	{
		if(grupoAC == null)
			grupoAC = new GrupoAC();
		return grupoAC;
	}

	public void setGrupoAC(GrupoAC grupoAC)
	{
		this.grupoAC = grupoAC;
	}
	
	public Collection<GrupoAC> getGrupoACs()
	{
		return grupoACs;
	}
}