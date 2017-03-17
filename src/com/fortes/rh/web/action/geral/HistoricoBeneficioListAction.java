package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoBeneficioListAction extends MyActionSupport
{
	@Autowired private HistoricoBeneficioManager historicoBeneficioManager;
	@Autowired private BeneficioManager beneficioManager;

	private Collection<HistoricoBeneficio> historicoBeneficios;

	private HistoricoBeneficio historicoBeneficio;
	private Beneficio beneficio;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		historicoBeneficios = historicoBeneficioManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		String[] key = new String[]{"id","empresa.id"};
		Object[] values = new Object[]{beneficio.getId(), getEmpresaSistema().getId()};

		if(beneficioManager.verifyExists(key, values))
		{
			historicoBeneficioManager.remove(new Long[]{historicoBeneficio.getId()});
			return Action.SUCCESS;
		}
		else
		{
			addActionError("O Beneficio solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.INPUT;
		}
	}

	public Collection<HistoricoBeneficio> getHistoricoBeneficios() {
		return historicoBeneficios;
	}

	public HistoricoBeneficio getHistoricoBeneficio(){
		if(historicoBeneficio == null){
			historicoBeneficio = new HistoricoBeneficio();
		}
		return historicoBeneficio;
	}

	public void setHistoricoBeneficio(HistoricoBeneficio historicoBeneficio){
		this.historicoBeneficio=historicoBeneficio;
	}

	public Beneficio getBeneficio()
	{
		return beneficio;
	}

	public void setBeneficio(Beneficio beneficio)
	{
		this.beneficio = beneficio;
	}
}