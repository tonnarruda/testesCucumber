package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.HistoricoColaboradorBeneficioManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoColaboradorBeneficioListAction extends MyActionSupportList
{
	private HistoricoColaboradorBeneficioManager historicoColaboradorBeneficioManager;
	private ColaboradorManager colaboradorManager;

	private Collection<HistoricoColaboradorBeneficio> historicoColaboradorBeneficios;

	private HistoricoColaboradorBeneficio historicoColaboradorBeneficio;
	private Colaborador colaborador;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		historicoColaboradorBeneficios = historicoColaboradorBeneficioManager.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()}, new String[]{"data"});

		if(!msgAlert.equals(""))
			addActionError(msgAlert);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		historicoColaboradorBeneficio.setColaborador(colaborador);

		if (!isUltimoHistorico(historicoColaboradorBeneficio))
			addActionError("Não é possível deletar este histórico, pois já foi inserido um histórico após este.");
		else
			historicoColaboradorBeneficioManager.deleteHistorico(historicoColaboradorBeneficio);

		return list();
	}

	private boolean isUltimoHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio)
	{
		HistoricoColaboradorBeneficio ultimoHistorico = historicoColaboradorBeneficioManager.getUltimoHistorico(historicoColaboradorBeneficio.getColaborador().getId());

		if (ultimoHistorico == null || ultimoHistorico.getId() == null || ultimoHistorico.getId().equals(historicoColaboradorBeneficio.getId()))
			return true;

		return false;
	}

	public Collection<HistoricoColaboradorBeneficio> getHistoricoColaboradorBeneficios() {
		return historicoColaboradorBeneficios;
	}


	public HistoricoColaboradorBeneficio getHistoricoColaboradorBeneficio(){
		if(historicoColaboradorBeneficio == null){
			historicoColaboradorBeneficio = new HistoricoColaboradorBeneficio();
		}
		return historicoColaboradorBeneficio;
	}

	public void setHistoricoColaboradorBeneficio(HistoricoColaboradorBeneficio historicoColaboradorBeneficio){
		this.historicoColaboradorBeneficio=historicoColaboradorBeneficio;
	}

	public void setHistoricoColaboradorBeneficioManager(HistoricoColaboradorBeneficioManager historicoColaboradorBeneficioManager){
		this.historicoColaboradorBeneficioManager=historicoColaboradorBeneficioManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
}