package com.fortes.rh.web.action.acesso;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class PerfilListAction extends MyActionSupportList {
	private static final long serialVersionUID = 1L;

	private PerfilManager perfilManager = null;

	private Collection<Perfil> perfils = null;

	private Perfil perfil;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	public String list() throws Exception {
		
		setTotalSize(perfilManager.getCount());

		perfils = perfilManager.findAll(getPage(),getPagingSize());

		return Action.SUCCESS;
	}

	
	public String imprimirPerfis() throws Exception
	{
		perfils = perfilManager.findPapeis();
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
}