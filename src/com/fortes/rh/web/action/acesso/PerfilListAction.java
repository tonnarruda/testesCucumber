package com.fortes.rh.web.action.acesso;

import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class PerfilListAction extends MyActionSupportList {
	private static final long serialVersionUID = 1L;

	private PerfilManager perfilManager = null;

	private Collection<Perfil> perfils = null;
	
	private String[] perfisCheck;
	private Collection<CheckBox> perfisCheckList = new ArrayList<CheckBox>();

	private Perfil perfil;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	public String list() throws Exception {
		
		setTotalSize(perfilManager.getCount());

		perfils = perfilManager.findAll(getPage(),getPagingSize());
		
		Collection<Perfil> todosPerfis = perfilManager.findAll(null, null);
		perfisCheckList = populaCheckListBox(todosPerfis, "getId", "getNome", null);

		return Action.SUCCESS;
	}

	
	public String imprimirPerfis() throws Exception
	{
		Long[] perfisIds = LongUtil.arrayStringToArrayLong(perfisCheck);
		perfils = perfilManager.findPapeis(perfisIds);
		
		parametros = RelatorioUtil.getParametrosRelatorio("Permissões dos Perfis", getEmpresaSistema(), "");
		return Action.SUCCESS;
	}
	
	public String delete() throws Exception {
		perfilManager.remove(new Long[] { perfil.getId() });
		addActionMessage("Perfil excluído com sucesso.");

		return Action.SUCCESS;
	}

	public Collection<Perfil> getPerfils() {
		return perfils;
	}

	public Perfil getPerfil() {
		if (perfil == null) {
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setPerfilManager(PerfilManager perfilManager) {
		this.perfilManager = perfilManager;
	}


	public Map<String, Object> getParametros() {
		return parametros;
	}


	public String[] getPerfisCheck() {
		return perfisCheck;
	}


	public void setPerfisCheck(String[] perfisCheck) {
		this.perfisCheck = perfisCheck;
	}


	public Collection<CheckBox> getPerfisCheckList() {
		return perfisCheckList;
	}


	public void setPerfisCheckList(Collection<CheckBox> perfisCheckList) {
		this.perfisCheckList = perfisCheckList;
	}
}