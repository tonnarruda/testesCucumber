package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.sesmt.ComissaoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.dicionario.FuncaoComissaoEleitoral;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class ComissaoEleicaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	@Autowired private ComissaoEleicaoManager comissaoEleicaoManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private EleicaoManager eleicaoManager;

	private Collection<ComissaoEleicao> comissaoEleicaos;

	private ComissaoEleicao comissaoEleicao;
	private FuncaoComissaoEleitoral funcaoComissaoEleitoral = new FuncaoComissaoEleitoral();
	private Eleicao eleicao;
	
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] colaboradorsCheck;
	private String[] funcaoComissaos;
	private String[] comissaoEleicaoIds;
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();

	private String nomeBusca;

	public String list() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		comissaoEleicaos = comissaoEleicaoManager.findByEleicao(eleicao.getId());
		eleicao = (Eleicao) eleicaoManager.findByIdProjection(eleicao.getId());
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		comissaoEleicaoManager.remove(comissaoEleicao.getId());
		list();

		return Action.SUCCESS;
	}

	public String saveFuncao() throws Exception
	{
		try
		{
			comissaoEleicaoManager.updateFuncao(comissaoEleicaoIds, funcaoComissaos);
			addActionMessage("Funções gravadas com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Erro ao alterar Funções.");
		}

		list();

		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if (comissaoEleicao != null && comissaoEleicao.getId() != null)
			comissaoEleicao = (ComissaoEleicao) comissaoEleicaoManager.findById(comissaoEleicao.getId());
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
		try
		{
			comissaoEleicaoManager.save(colaboradorsCheck, eleicao);
		}
		catch (Exception e)
		{
			addActionError("Erro ao incluir Colaborador.");
		}

		list();
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		comissaoEleicaoManager.update(comissaoEleicao);
		return Action.SUCCESS;
	}

	public Collection<ComissaoEleicao> getComissaoEleicaos()
	{
		return comissaoEleicaos;
	}

	public ComissaoEleicao getComissaoEleicao()
	{
		if (comissaoEleicao == null)
		{
			comissaoEleicao = new ComissaoEleicao();
		}
		return comissaoEleicao;
	}

	public void setComissaoEleicao(ComissaoEleicao comissaoEleicao)
	{
		this.comissaoEleicao = comissaoEleicao;
	}

	public Eleicao getEleicao()
	{
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public Collection<CheckBox> getColaboradorsCheckList()
	{
		return colaboradorsCheckList;
	}

	public void setColaboradorsCheck(String[] colaboradorsCheck)
	{
		this.colaboradorsCheck = colaboradorsCheck;
	}

	public FuncaoComissaoEleitoral getFuncaoComissaoEleitoral()
	{
		return funcaoComissaoEleitoral;
	}

	public void setFuncaoComissaos(String[] funcaoComissaos)
	{
		this.funcaoComissaos = funcaoComissaos;
	}

	public void setComissaoEleicaoIds(String[] comissaoEleicaoIds)
	{
		this.comissaoEleicaoIds = comissaoEleicaoIds;
	}

	public String[] getFuncaoComissaos()
	{
		return funcaoComissaos;
	}
}