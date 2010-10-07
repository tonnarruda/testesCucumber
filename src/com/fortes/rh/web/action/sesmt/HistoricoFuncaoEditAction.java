package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashSet;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class HistoricoFuncaoEditAction extends MyActionSupportEdit
{
	private HistoricoFuncaoManager historicoFuncaoManager;
	private ExameManager exameManager;
	private EpiManager epiManager;

	private HistoricoFuncao historicoFuncao = new HistoricoFuncao();
	private Funcao funcao;
	private Cargo cargoTmp;

	private Long[] examesChecked;
	private Collection<CheckBox> examesCheckList = new HashSet<CheckBox>();
	private Long[] episChecked;
	private Collection<CheckBox> episCheckList = new HashSet<CheckBox>();

	private boolean veioDoSESMT;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(historicoFuncao != null && historicoFuncao.getId() != null)
			historicoFuncao = historicoFuncaoManager.findByIdProjection(historicoFuncao.getId());

		Collection<Exame> exames = exameManager.findAllSelect(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.populaCheckListBox(exames, "getId", "getNome");
		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		examesCheckList = CheckListBoxUtil.marcaCheckListBox(examesCheckList, historicoFuncao.getExames(), "getId");
		episCheckList = CheckListBoxUtil.marcaCheckListBox(episCheckList, historicoFuncao.getEpis(), "getId");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		historicoFuncao.setFuncao(funcao);
		historicoFuncaoManager.saveHistorico(historicoFuncao, examesChecked, episChecked);

		if(veioDoSESMT)
			return "SUCESSO_VEIO_SESMT";
		else
			return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		historicoFuncaoManager.updateHistorico(historicoFuncao, examesChecked, episChecked);

		if(veioDoSESMT)
			return "SUCESSO_VEIO_SESMT";
		else
			return Action.SUCCESS;
	}

	public HistoricoFuncao getHistoricoFuncao()
	{
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao)
	{
		this.historicoFuncao = historicoFuncao;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public Funcao getFuncao()
	{
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public Cargo getCargoTmp()
	{
		return cargoTmp;
	}

	public void setCargoTmp(Cargo cargoTmp)
	{
		this.cargoTmp = cargoTmp;
	}

	public void setExameManager(ExameManager exameManager)
	{
		this.exameManager = exameManager;
	}

	public Long[] getExamesChecked()
	{
		return examesChecked;
	}

	public void setExamesChecked(Long[] examesChecked)
	{
		this.examesChecked = examesChecked;
	}

	public Collection<CheckBox> getExamesCheckList()
	{
		return examesCheckList;
	}

	public Collection<CheckBox> getEpisCheckList()
	{
		return episCheckList;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public void setEpisChecked(Long[] episChecked)
	{
		this.episChecked = episChecked;
	}

	public boolean isVeioDoSESMT()
	{
		return veioDoSESMT;
	}

	public void setVeioDoSESMT(boolean veioDoSESMT)
	{
		this.veioDoSESMT = veioDoSESMT;
	}
}