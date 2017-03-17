package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class InsereNonoDigitoEditAction extends MyActionSupportEdit
{
	private Long[] estadosCheck;
	private Collection<CheckBox> estadosCheckList = new ArrayList<CheckBox>();
	
	@Autowired private EstadoManager estadoManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private CandidatoManager candidatoManager;
	

	public String prepareInsert() throws Exception {
		Collection<Estado> estados = estadoManager.findAll(new String[]{"nome"});
		
		estadosCheckList = CheckListBoxUtil.populaCheckListBox(estados, "getId", "getNome", null);

		return Action.SUCCESS;
	}
	
	public String insert() throws Exception {
		
		try {
			colaboradorManager.insereNonoDigitoCelular(estadosCheck);
			candidatoManager.inserirNonoDigitoCelular(estadosCheck);
		} catch (Exception e) {
			addActionError("Erro tentar inserir o nono dígito.");
		} finally {
			prepareInsert();
		}
		
		addActionSuccess("Nono dígito inserido com sucesso.");

		return Action.SUCCESS;
	}

	public Long[] getEstadosCheck() {
		return estadosCheck;
	}

	public void setEstadosCheck(Long[] estadosCheck) {
		this.estadosCheck = estadosCheck;
	}

	public Collection<CheckBox> getEstadosCheckList() {
		return estadosCheckList;
	}

	public void setEstadosCheckList(Collection<CheckBox> estadosCheckList) {
		this.estadosCheckList = estadosCheckList;
	}
}