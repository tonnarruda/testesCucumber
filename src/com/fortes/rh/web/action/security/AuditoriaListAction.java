package com.fortes.rh.web.action.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.security.AuditoriaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class AuditoriaListAction extends MyActionSupport
{
	@Autowired private AuditoriaManager auditoriaManager;
	@Autowired private UsuarioManager usuarioManager;

	private Collection<Auditoria> auditorias = new ArrayList<Auditoria>();
	private Collection<Usuario> usuarios = new ArrayList<Usuario>();

	//campos do filtro
	private Date dataIni;
	private Date dataFim;
	private Usuario usuario;
	private String operacao;
	private String entidade;
	private String dados;
	private List<String> operacoes = new ArrayList<String>();
	private Map entidades;
	

	private int page = 1;
	private Integer totalSize = 0;
	private static final int pagingSize = 20;
	@SuppressWarnings("unused")
	private Long empresaId;
	private boolean showFilter = false;

	private Auditoria auditoriaView;
	private String detalhes; 

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String listaOperacoes() {
		
		operacoes = auditoriaManager.findOperacoesPeloModulo(entidade);
		
		return Action.SUCCESS;
	}
	
	public String prepareList() throws Exception
	{
		//Consulta com projections trazendo apenas o necessario para o combo
		usuarios = usuarioManager.findAllSelect();
		
		operacoes = auditoriaManager.findOperacoesPeloModulo(entidade);
		
		entidades = auditoriaManager.findEntidade(getEmpresaId());

		return Action.SUCCESS;
	}

	public String viewAuditoria() throws Exception
	{
		auditoriaView = auditoriaManager.projectionFindById(auditoriaView.getId(), getEmpresaId());
		auditoriaView.setDados(auditoriaView.getDados().replace("</null>", "").replace("<null>", "").replace("null", "").replace("}", "").replace("{", "").replace("\"", ""));
		
//		detalhes = auditoriaManager.getDetalhes(auditoriaView.getDados());
//		auditoriaView.setDados(detalhes);
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String listFiltro() throws Exception
	{
		Map parametros = new HashMap();
		parametros.put("dataIni", dataIni);
		parametros.put("dataFim", dataFim);
		parametros.put("usuarioId", usuario.getId());
		parametros.put("operacao", operacao);
		parametros.put("entidade", entidade);
		parametros.put("dados", dados);

		totalSize = auditoriaManager.getCount(parametros, getEmpresaId());
		auditorias = auditoriaManager.list(page, pagingSize, parametros, getEmpresaId());

		return prepareList();
	}

	public Collection<Auditoria> getAuditorias()
	{
		return auditorias;
	}

	public void setAuditorias(Collection<Auditoria> auditorias)
	{
		this.auditorias = auditorias;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public Map getEntidades()
	{
		return entidades;
	}

	public void setEntidades(Map entidades)
	{
		this.entidades = entidades;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public Collection<Usuario> getUsuarios()
	{
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios)
	{
		this.usuarios = usuarios;
	}

	public String getEntidade()
	{
		return entidade;
	}

	public void setEntidade(String entidade)
	{
		this.entidade = entidade;
	}

	public String getOperacao()
	{
		return operacao;
	}

	public void setOperacao(String operacao)
	{
		this.operacao = operacao;
	}

	public Map getOperacoes()
	{
		Map<String, String> map = new LinkedHashMap<String, String>(operacoes.size());
		map.put("", "Todas");
		map.put("Inserção", "Inserção");
		map.put("Atualização", "Atualização");
		map.put("Remoção", "Remoção");
		
		for (String op : operacoes)
			map.put(op, op);
		
		return map;
	}
	
	public Auditoria getAuditoriaView()
	{
		return auditoriaView;
	}

	public void setAuditoriaView(Auditoria auditoriaView)
	{
		this.auditoriaView = auditoriaView;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public void setTotalSize(Integer totalSize)
	{
		this.totalSize = totalSize;
	}

	public Integer getTotalSize()
	{
		return totalSize;
	}

	public int getPagingSize()
	{
		return pagingSize;
	}

	public Long getEmpresaId()
	{
		return SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getId();
	}
	
	public String updateFilter()
	{
		return Action.NONE;
	}
	
	public boolean getShowFilter() 
	{
		return showFilter;
	}

	public void setShowFilter(boolean filterState) 
	{
		this.showFilter = filterState;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}	
}