package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.GastoManager;
import com.fortes.rh.business.geral.GrupoGastoManager;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class GastoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private GastoManager gastoManager;
	private GrupoGastoManager grupoGastoManager;

	private Gasto gasto;
	private GrupoGasto grupoGastoAgrupar;

	private Collection<GrupoGasto> grupoGastos;
	private Collection<Gasto> gastos;
	private Collection<CheckBox> gastosCheckList = new ArrayList<CheckBox>();
	private Collection<Gasto> gastoDoGrupos;

	private String[] gastosCheck;
	private String nome;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(gasto != null && gasto.getId() != null)
			gasto = (Gasto) gastoManager.findById(gasto.getId());

		this.grupoGastos = grupoGastoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"nome"});

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

	// TODO refatorar isso!!
	public String insert() throws Exception
	{
		gastos = gastoManager.find(new String[]{"nome", "empresa.id"}, new Object[]{gasto.getNome(), getEmpresaSistema().getId()});

		gasto.setEmpresa(getEmpresaSistema());

		if(gastos.size() == 0)
		{
			gastoManager.save(gasto);
		}
		else
		{

			for (Gasto gastoAux : gastos)
			{

				if(!gastoAux.getNome().equals(gasto.getNome()))
				{
					gastoManager.save(gasto);
					return Action.SUCCESS;
				}
				else
				{
					addActionError("Já existe um investimento com este nome!");
					prepare();
					return Action.INPUT;
				}

			}

		}
		return Action.SUCCESS;

	}

	public String update() throws Exception
	{
		gasto.setEmpresa(getEmpresaSistema());
		gastoManager.update(gasto);
		return Action.SUCCESS;
	}

	public String prepareAgrupar() throws Exception
	{
		grupoGastoAgrupar = grupoGastoManager.findByIdProjection(grupoGastoAgrupar.getId());
		gastoDoGrupos = gastoManager.findGastosDoGrupo(grupoGastoAgrupar.getId());
		gastosCheckList = CheckListBoxUtil.populaCheckListBox(gastoManager.getGastosSemGrupo(getEmpresaSistema().getId()),"getId","getNome", null);

		return Action.SUCCESS;
	}

	public String agrupar() throws Exception
	{
		try
		{
			gastoManager.agrupar(grupoGastoAgrupar, gastosCheck);
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Ocorreu um erro ao agrupar os investimentos.");
			prepareAgrupar();
			return Action.INPUT;
		}
	}

	public Object getModel()
	{
		return getGasto();
	}

	public Gasto getGasto()
	{
		if(gasto == null)
			gasto = new Gasto();
		return gasto;
	}

	public void setGasto(Gasto gasto)
	{
		this.gasto = gasto;
	}

	public void setGastoManager(GastoManager gastoManager)
	{
		this.gastoManager = gastoManager;
	}

	public void setGrupoGastoManager(GrupoGastoManager grupoGastoManager)
	{
		this.grupoGastoManager = grupoGastoManager;
	}

	public Collection<GrupoGasto> getGrupoGastos()
	{
		return grupoGastos;
	}

	public void setGrupoGastos(Collection<GrupoGasto> grupoGastos)
	{
		this.grupoGastos = grupoGastos;
	}

	public GrupoGasto getGrupoGastoAgrupar()
	{
		return grupoGastoAgrupar;
	}

	public void setGrupoGastoAgrupar(GrupoGasto grupoGastoAgrupar)
	{
		this.grupoGastoAgrupar = grupoGastoAgrupar;
	}

	public String[] getGastosCheck()
	{
		return gastosCheck;
	}

	public void setGastosCheck(String[] gastosCheck)
	{
		this.gastosCheck = gastosCheck;
	}

	public Collection<CheckBox> getGastosCheckList()
	{
		return gastosCheckList;
	}

	public void setGastosCheckList(Collection<CheckBox> gastosCheckList)
	{
		this.gastosCheckList = gastosCheckList;
	}

	public Collection<Gasto> getGastos()
	{
		return gastos;
	}

	public void setGastos(Collection<Gasto> gastos)
	{
		this.gastos = gastos;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<Gasto> getGastoDoGrupos()
	{
		return gastoDoGrupos;
	}

	public void setGastoDoGrupos(Collection<Gasto> gastoDoGrupos)
	{
		this.gastoDoGrupos = gastoDoGrupos;
	}

}