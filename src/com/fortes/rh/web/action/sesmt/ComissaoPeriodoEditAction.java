package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.PersistenceException;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class ComissaoPeriodoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private ComissaoPeriodoManager comissaoPeriodoManager;
	private ComissaoMembroManager comissaoMembroManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	private ComissaoPeriodo comissaoPeriodo;
	private Comissao comissao;
	private Collection<ComissaoPeriodo> comissaoPeriodos;
	private Collection<ComissaoMembro> comissaoMembros;

	private TipoMembroComissao tipos = TipoMembroComissao.getInstance();
	private FuncaoComissao funcoes = FuncaoComissao.getInstance();
	private String[] comissaoMembroIds;
	private String[] funcaoComissaos;
	private String[] tipoComissaos;

	//pesquisa colaborador
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] colaboradorsCheck;
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();
	private String nomeBusca;
	private boolean clonar = false;
	private ComissaoMembro comissaoMembro;


	public String prepare() throws Exception
	{
		if(comissaoPeriodo != null && comissaoPeriodo.getId() != null)
		{
			comissaoPeriodo = (ComissaoPeriodo) comissaoPeriodoManager.findByIdProjection(comissaoPeriodo.getId());
			comissao = comissaoPeriodo.getComissao();
			comissaoMembros = comissaoMembroManager.findByComissaoPeriodo(comissaoPeriodo);
			areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		}
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		comissaoPeriodoManager.save(comissaoPeriodo);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			comissaoPeriodo.setComissao(comissao);
			comissaoPeriodoManager.atualiza(comissaoPeriodo, comissaoMembroIds, funcaoComissaos, tipoComissaos);
			list();
		}
		catch (Exception e)
		{
			prepare();
			addActionError("Erro ao gravar a comissão.");
			return INPUT;
		}
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			comissaoPeriodo.setComissao(comissao); // id da comissão
			comissaoPeriodoManager.remove(comissaoPeriodo);
		} catch (PersistenceException e)
		{
			addActionWarning(e.getMessage());
		}
		return list();
	}

	/**
	 * Listagem de períodos e membros das comissões ("aba" Comissões).
	 */
	public String list() throws Exception
	{
		if (comissao == null || comissao.getId() == null) // sem id da comissao, retorna para a listagem de comissoes.
			return INPUT;

		comissaoPeriodos = comissaoPeriodoManager.findByComissao(comissao.getId());
		
		return SUCCESS;
	}

	public String clonar() throws Exception
	{
		try
		{
			clonar = true;
			comissaoPeriodo = comissaoPeriodoManager.clonar(comissaoPeriodo.getId(), null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao clonar Período da Comissão.");
		}
		return prepare();
	}

	/**
	 * Manipula os membros no período
	 */
	public String insertComissaoMembro()
	{
		try
		{
			comissaoMembroManager.save(colaboradorsCheck, comissaoPeriodo);
		}
		catch (Exception e)
		{
			addActionError("Erro ao incluir membro(s) da comissão.");
		}
		return SUCCESS;
	}
	public String deleteComissaoMembro() throws Exception
	{
		comissaoMembroManager.remove(comissaoMembro.getId());
		prepare();
		return SUCCESS;
	}

	public ComissaoPeriodo getComissaoPeriodo()
	{
		if(comissaoPeriodo == null)
			comissaoPeriodo = new ComissaoPeriodo();
		return comissaoPeriodo;
	}

	public void setComissaoPeriodo(ComissaoPeriodo comissaoPeriodo)
	{
		this.comissaoPeriodo = comissaoPeriodo;
	}

	public void setComissaoPeriodoManager(ComissaoPeriodoManager comissaoPeriodoManager)
	{
		this.comissaoPeriodoManager = comissaoPeriodoManager;
	}

	public Collection<ComissaoPeriodo> getComissaoPeriodos()
	{
		return comissaoPeriodos;
	}

	public Comissao getComissao()
	{
		return comissao;
	}

	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public Collection<ComissaoMembro> getComissaoMembros()
	{
		return comissaoMembros;
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager)
	{
		this.comissaoMembroManager = comissaoMembroManager;
	}

	public FuncaoComissao getFuncoes()
	{
		return funcoes;
	}

	public TipoMembroComissao getTipos()
	{
		return tipos;
	}

	public void setComissaoMembroIds(String[] comissaoMembroIds)
	{
		this.comissaoMembroIds = comissaoMembroIds;
	}

	public String[] getFuncaoComissaos()
	{
		return funcaoComissaos;
	}

	public void setFuncaoComissaos(String[] funcaoComissaos)
	{
		this.funcaoComissaos = funcaoComissaos;
	}

	public String[] getTipoComissaos()
	{
		return tipoComissaos;
	}

	public void setTipoComissaos(String[] tipoComissaos)
	{
		this.tipoComissaos = tipoComissaos;
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

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public ComissaoMembro getComissaoMembro()
	{
		return comissaoMembro;
	}

	public void setComissaoMembro(ComissaoMembro comissaoMembro)
	{
		this.comissaoMembro = comissaoMembro;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public boolean isClonar() {
		return clonar;
	}

	public void setClonar(boolean clonar) {
		this.clonar = clonar;
	}
}