package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.TamanhoEPIManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.business.sesmt.TipoTamanhoEPIManager;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class TipoEPIEditAction extends MyActionSupportEdit
{
	private TipoEPIManager tipoEPIManager;
	
	private TamanhoEPIManager tamanhoEPIManager;
	
	private TipoTamanhoEPIManager tipoTamanhoEPIManager;

	private TipoEPI tipoEPI;
	private Long tipoEPIId;
	
	private Collection<TipoTamanhoEPI> tamanhoEPIs;
	
	private TamanhoEPI[] tamanhos;

	private String[] tamanhosCheck;
	private Collection<CheckBox> tamanhosCheckList = new ArrayList<CheckBox>();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(tipoEPI != null && tipoEPI.getId() != null) {
			tipoEPI = (TipoEPI) tipoEPIManager.findById(tipoEPI.getId());
			tipoEPIId = tipoEPI.getId();
		}
		
		tamanhosCheckList = tamanhoEPIManager.populaCheckOrderDescricao(tipoEPIId);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if(tipoEPI == null || tipoEPI.getId() == null || !tipoEPIManager.verifyExists(new String[]{"id","empresa.id"}, new Object[]{tipoEPI.getId(),getEmpresaSistema().getId()}))
		{
			msgAlert = "O Tipo de EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".";
			return Action.ERROR;
		}
		
		prepare();
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		tipoEPI.setEmpresa(getEmpresaSistema());
		tipoEPIManager.save(tipoEPI);
		tipoTamanhoEPIManager.salvarTamanhoEPIs(tipoEPI.getId(), tamanhoEPIs);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if(tipoEPI == null || tipoEPI.getId() == null || !tipoEPIManager.verifyExists(new String[]{"id","empresa.id"}, new Object[]{tipoEPI.getId(),getEmpresaSistema().getId()}))
		{
			addActionError("O Tipo de EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}
		
		tipoEPI.setEmpresa(getEmpresaSistema());

		tipoEPIManager.update(tipoEPI);
		
		tipoTamanhoEPIManager.salvarTamanhoEPIs(tipoEPI.getId(), tamanhoEPIs);
		return Action.SUCCESS;
	}

	public TipoEPI getTipoEPI()
	{
		if(tipoEPI == null)
			tipoEPI = new TipoEPI();
		return tipoEPI;
	}

	public void setTipoEPI(TipoEPI tipoEPI)
	{
		this.tipoEPI = tipoEPI;
	}

	public void setTipoEPIManager(TipoEPIManager tipoEPIManager)
	{
		this.tipoEPIManager = tipoEPIManager;
	}

	public String[] getTamanhosCheck() {
		return tamanhosCheck;
	}

	public void setTamanhosCheck(String[] tamanhosCheck) {
		this.tamanhosCheck = tamanhosCheck;
	}

	public Collection<CheckBox> getTamanhosCheckList() {
		return tamanhosCheckList;
	}

	public void setTamanhosCheckList(Collection<CheckBox> tamanhosCheckList) {
		this.tamanhosCheckList = tamanhosCheckList;
	}

	public void setTamanhoEPIManager(TamanhoEPIManager tamanhoEPIManager) {
		this.tamanhoEPIManager = tamanhoEPIManager;
	}

	public Collection<TipoTamanhoEPI> getTamanhoEPIs() {
		return tamanhoEPIs;
	}

	public void setTamanhoEPIs(Collection<TipoTamanhoEPI> tamanhoEPIs) {
		this.tamanhoEPIs = tamanhoEPIs;
	}

	public TamanhoEPI[] getTamanhos() {
		return tamanhos;
	}

	public void setTamanhos(TamanhoEPI[] tamanhos) {
		this.tamanhos = tamanhos;
	}

	public void setTipoTamanhoEPIManager(TipoTamanhoEPIManager tipoTamanhoEPIManager) {
		this.tipoTamanhoEPIManager = tipoTamanhoEPIManager;
	}
	
}