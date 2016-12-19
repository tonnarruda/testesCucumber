package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class FaixaSalarialListAction extends MyActionSupportList
{
	@Autowired private CargoManager cargoManager;
	@Autowired private FaixaSalarialManager faixaSalarialManager;
	private Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
	private FaixaSalarial faixaSalarial;
	private Cargo cargo;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		faixaSalarials = faixaSalarialManager.findFaixaSalarialByCargo(cargo.getId());
		if(faixaSalarials.size() > 0)
		{
			FaixaSalarial faixa = (FaixaSalarial) faixaSalarials.toArray()[0];
			cargo.setNome(faixa.getCargo().getNome());
		}
		else
		{
			cargo = cargoManager.findByIdProjection(cargo.getId());
		}

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{

		try {
			cargo = cargoManager.findByIdProjection(cargo.getId());
			faixaSalarialManager.deleteFaixaSalarial(faixaSalarial.getId(),getEmpresaSistema());
			addActionSuccess("Faixa salarial excluída com sucesso.");
			
		} catch (Exception e) {
			addActionWarning("Essa faixa salarial não pode ser removida pois possui dependências no sistema. <br/> Detalhes: " + e.getMessage());
		}

		return Action.SUCCESS;
	}

	public Collection getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public FaixaSalarial getFaixaSalarial(){
		if(faixaSalarial == null){
			faixaSalarial = new FaixaSalarial();
		}
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial){
		this.faixaSalarial=faixaSalarial;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public void setFaixaSalarials(Collection<FaixaSalarial> faixaSalarials)
	{
		this.faixaSalarials = faixaSalarials;
	}
}