package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ReajusteColaboradorListAction extends MyActionSupportList
{
	@Autowired private ReajusteColaboradorManager reajusteColaboradorManager;

	private Collection<ReajusteColaborador> reajusteColaboradors = new ArrayList<ReajusteColaborador>();

	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private ReajusteColaborador reajusteColaborador;
	private AreaOrganizacional areaOrganizacional;
	private GrupoOcupacional grupoOcupacional;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		reajusteColaboradorManager.remove(new Long[]{reajusteColaborador.getId()});

		return Action.SUCCESS;
	}

	public Collection getReajusteColaboradors() {
		return reajusteColaboradors;
	}

	public ReajusteColaborador getReajusteColaborador(){
		if(reajusteColaborador == null){
			reajusteColaborador = new ReajusteColaborador();
		}
		return reajusteColaborador;
	}

	public void setReajusteColaborador(ReajusteColaborador reajusteColaborador){
		this.reajusteColaborador=reajusteColaborador;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador()
	{
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(
			TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public GrupoOcupacional getGrupoOcupacional()
	{
		return grupoOcupacional;
	}

	public void setGrupoOcupacional(GrupoOcupacional grupoOcupacional)
	{
		this.grupoOcupacional = grupoOcupacional;
	}
}