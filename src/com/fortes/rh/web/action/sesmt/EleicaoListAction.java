package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.exception.RemoveCascadeException;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class EleicaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	@Autowired private EleicaoManager eleicaoManager;

	private Collection<Eleicao> eleicaos;

	private Eleicao eleicao;

	public String list() throws Exception
	{
		eleicaos = eleicaoManager.findAllSelect(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			eleicaoManager.removeCascade(eleicao.getId());
			addActionMessage("Eleição excluída com sucesso.");
		}
		catch (RemoveCascadeException e)
		{
			addActionError(e.getMessage());
		}
		catch (Exception e)
		{
			addActionError("Erro ao excluir eleição.");
		}

		list();

		return Action.SUCCESS;
	}

	public Collection<Eleicao> getEleicaos()
	{
		return eleicaos;
	}

	public Eleicao getEleicao()
	{
		if (eleicao == null)
		{
			eleicao = new Eleicao();
		}
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}
}