package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class BeneficioEditAction extends MyActionSupportEdit
{
	@Autowired private BeneficioManager beneficioManager;
	@Autowired private HistoricoBeneficioManager historicoBeneficioManager;

	private Beneficio beneficio;

	private HistoricoBeneficio historicoBeneficio;

	private Collection<HistoricoBeneficio> historicoBeneficios = new ArrayList<HistoricoBeneficio>();

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(beneficio != null && beneficio.getId() != null) {
			beneficio = (Beneficio) beneficioManager.findById(beneficio.getId());
		}

	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if(beneficio == null || beneficio.getEmpresa() == null || !getEmpresaSistema().getId().equals(beneficio.getEmpresa().getId()))
		{
			beneficio = null;
			addActionError("O Benefício solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		String[] properties = new String[]{"id","data","valor","paraColaborador","paraDependenteDireto","paraDependenteIndireto"};
		String[] sets = new String[]{"id","data","valor","paraColaborador","paraDependenteDireto","paraDependenteIndireto"};

		historicoBeneficios = historicoBeneficioManager.findToList(properties, sets, new String[]{"beneficio.id"}, new Object[]{beneficio.getId()}, new String[]{"data desc"});

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			beneficio.setEmpresa(getEmpresaSistema());
			beneficioManager.saveHistoricoBeneficio(beneficio, historicoBeneficio);
		}
		catch (Exception e)
		{
			addActionError("Erro no cadastro de Beneficio");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if(beneficio == null || beneficio.getEmpresa() == null || !getEmpresaSistema().getId().equals(beneficio.getEmpresa().getId()))
		{
			addActionError("O Benefício solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.INPUT;
		}
		else
		{
			beneficioManager.update(beneficio);
			return Action.SUCCESS;
		}
	}

	public Beneficio getBeneficio(){
		if(beneficio == null){
			beneficio = new Beneficio();
		}
		return beneficio;
	}

	public void setBeneficio(Beneficio beneficio){
		this.beneficio=beneficio;
	}

	public Collection<HistoricoBeneficio> getHistoricoBeneficios()
	{
		return this.historicoBeneficios;
	}

	public void setHistoricoBeneficios(Collection<HistoricoBeneficio> historicoBeneficios)
	{
		this.historicoBeneficios = historicoBeneficios;
	}

	public HistoricoBeneficio getHistoricoBeneficio()
	{
		return historicoBeneficio;
	}

	public void setHistoricoBeneficio(HistoricoBeneficio historicoBeneficio)
	{
		this.historicoBeneficio = historicoBeneficio;
	}
}