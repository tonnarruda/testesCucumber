package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial"})
public class OcorrenciaProvidenciaEditAction extends MyActionSupportList
{
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ProvidenciaManager providenciaManager;

	private ColaboradorOcorrencia colaboradorOcorrencia;
	private String ocorrenciaDescricao;
	private String colaboradorNome;
	private char comProvidencia;
	
	private Collection<Ocorrencia> ocorrencias;
	private Collection<Providencia> providencias;
	private Collection<ColaboradorOcorrencia> colaboradorOcorrencias;
	
	public String list() throws Exception
	{
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		
		Collection<Colaborador> colaboradores = colaboradorOcorrenciaManager.findColaboraesPermitidosByUsuario(usuarioLogado, null, getEmpresaSistema().getId(), SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}), false);
		Long[] colaboradoresIds = new CollectionUtil<Colaborador>().convertCollectionToArrayIds(colaboradores);
		
		setTotalSize(colaboradorOcorrenciaManager.findByFiltros(0, 0, colaboradorNome, ocorrenciaDescricao, comProvidencia(), colaboradoresIds, getEmpresaSistema().getId()).size());
		colaboradorOcorrencias = colaboradorOcorrenciaManager.findByFiltros(getPage(), getPagingSize(), colaboradorNome, ocorrenciaDescricao, comProvidencia(), colaboradoresIds, getEmpresaSistema().getId());
		
		if(colaboradorOcorrencias.size() == 0)
			addActionMessage("Não existem providências a serem listadas para o filtro informado");
		
		return Action.SUCCESS;
	}
	
	public String prepare() throws Exception
	{
		colaboradorOcorrencia = (ColaboradorOcorrencia) colaboradorOcorrenciaManager.findById(colaboradorOcorrencia.getId());
		providencias = providenciaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao"});
		return SUCCESS;
	}
	
	public String update() throws Exception
	{
		if(colaboradorOcorrencia.getProvidencia().getId() == null)
			colaboradorOcorrencia.setProvidencia(null);
		
		colaboradorOcorrenciaManager.update(colaboradorOcorrencia);
		return SUCCESS;
	}
	
	private Boolean comProvidencia()
	{
		if(comProvidencia == 'S')
			return false;
		else if(comProvidencia == 'C')
			return true;
		
		return null;
	}

	public Collection<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

	public Collection<Providencia> getProvidencias() {
		return providencias;
	}

	public Collection<ColaboradorOcorrencia> getColaboradorOcorrencias() {
		return colaboradorOcorrencias;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager) 
	{
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public ColaboradorOcorrencia getColaboradorOcorrencia() {
		return colaboradorOcorrencia;
	}

	public void setColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia) {
		this.colaboradorOcorrencia = colaboradorOcorrencia;
	}

	public void setOcorrenciaDescricao(String ocorrenciaDescricao) {
		this.ocorrenciaDescricao = ocorrenciaDescricao;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public void setProvidenciaManager(ProvidenciaManager providenciaManager) {
		this.providenciaManager = providenciaManager;
	}

	public String getOcorrenciaDescricao() {
		return ocorrenciaDescricao;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public char getComProvidencia() {
		return comProvidencia;
	}

	public void setComProvidencia(char comProvidencia) {
		this.comProvidencia = comProvidencia;
	}
}