package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ReajusteFaixaSalarialEditAction extends MyActionSupportEdit
{
	private CargoManager cargoManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	
	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();
	
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();	
	private String[] faixasCheck;
	private Collection<CheckBox> faixasCheckList = new ArrayList<CheckBox>();
	
	public String prepareDissidio() throws Exception
	{
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.FAIXA_SALARIAL);
		
		cargosCheckList = cargoManager.populaCheckBox(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String aplicarDissidio() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public Collection<CheckBox> getFaixasCheckList() {
		return faixasCheckList;
	}

	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public void setFaixasCheck(String[] faixasCheck) {
		this.faixasCheck = faixasCheck;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador() {
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador) {
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors() {
		return tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradors(	Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors) {
		this.tabelaReajusteColaboradors = tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager) {
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}
}