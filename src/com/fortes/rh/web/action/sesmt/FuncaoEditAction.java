package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class FuncaoEditAction extends MyActionSupportEdit
{
	private FuncaoManager funcaoManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private ExameManager exameManager;
	private EpiManager epiManager;
	private RiscoManager riscoManager;
	private CursoManager cursoManager;
	private CodigoCBOManager codigoCBOManager;

	private Funcao funcao;
	private HistoricoFuncao historicoFuncao;

	private Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();

    private Collection<Ambiente> ambientes;

	private Collection<RiscoFuncao> riscosFuncoes;

	private String[] riscoChecks;

    
    private Colaborador colaborador;
    private HistoricoColaborador historicoColaborador;
    private Collection<Funcao> funcaos = new ArrayList<Funcao>();

    private AreaOrganizacional areaBusca;
    private String nomeBusca;
    private int page;

    private Long[] examesChecked;
	private Collection<CheckBox> examesCheckList = new HashSet<CheckBox>();
	private Long[] episChecked;
	private Collection<CheckBox> episCheckList = new HashSet<CheckBox>();
	private Collection<CheckBox> cursosCheckList = new HashSet<CheckBox>();
	private Long[] cursosChecked;
	private String descricaoCBO;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(funcao != null && funcao.getId() != null)
			funcao = funcaoManager.findByIdProjection(funcao.getId());

		Collection<Exame> exames = exameManager.findByEmpresaComAsoPadrao(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.populaCheckListBox(exames, "getId", "getNome", null);
		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), true);
		setCursosCheckList(cursoManager.populaCheckListCurso(getEmpresaSistema().getId()));
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		
		riscosFuncoes = riscoManager.findRiscosFuncoesByEmpresa(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		descricaoCBO = codigoCBOManager.findDescricaoByCodigo(funcao.getCodigoCbo());
		historicoFuncaos = historicoFuncaoManager.findToList(new String[]{"id","descricao","data"}, new String[]{"id","descricao","data"}, new String[]{"funcao.id"}, new Object[]{funcao.getId()}, new String[]{"data desc"});

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		funcao.setEmpresa(getEmpresaSistema());
		historicoFuncaoManager.saveFuncaoHistorico(funcao, historicoFuncao, examesChecked, episChecked, cursosChecked, riscoChecks, riscosFuncoes);
		addActionSuccess("Função " + funcao.getNome() + " cadastrada com sucesso.");
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		funcao.setEmpresa(getEmpresaSistema());
		funcaoManager.update(funcao);
		addActionSuccess("Função " + funcao.getNome() + "  atualizada com sucesso.");
		return Action.SUCCESS;
	}

	public Funcao getFuncao()
	{
		if(funcao == null)
			funcao = new Funcao();
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public HistoricoFuncao getHistoricoFuncao()
	{
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao)
	{
		this.historicoFuncao = historicoFuncao;
	}

	public Collection<HistoricoFuncao> getHistoricoFuncaos()
	{
		return historicoFuncaos;
	}

	public void setHistoricoFuncaos(Collection<HistoricoFuncao> historicoFuncaos)
	{
		this.historicoFuncaos = historicoFuncaos;
	}

	public Collection<Ambiente> getAmbientes()
	{
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes)
	{
		this.ambientes = ambientes;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public Collection<Funcao> getFuncaos()
	{
		return funcaos;
	}

	public void setFuncaos(Collection<Funcao> funcaos)
	{
		this.funcaos = funcaos;
	}
	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public AreaOrganizacional getAreaBusca()
	{
		return areaBusca;
	}

	public void setAreaBusca(AreaOrganizacional areaBusca)
	{
		this.areaBusca = areaBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Long[] getExamesChecked()
	{
		return examesChecked;
	}

	public void setExamesChecked(Long[] examesChecked)
	{
		this.examesChecked = examesChecked;
	}

	public Collection<CheckBox> getExamesCheckList()
	{
		return examesCheckList;
	}

	public void setExameManager(ExameManager exameManager)
	{
		this.exameManager = exameManager;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public Collection<CheckBox> getEpisCheckList()
	{
		return episCheckList;
	}

	public void setCursosCheckList(Collection<CheckBox> cursosCheckList) {
		this.cursosCheckList = cursosCheckList;
	}
	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public void setCursosChecked(Long[] cursosChecked) {
		this.cursosChecked = cursosChecked;
	}

	public void setEpisChecked(Long[] episChecked)
	{
		this.episChecked = episChecked;
	}

	public String[] getRiscoChecks() {
		return riscoChecks;
	}

	public void setRiscoChecks(String[] riscoChecks) {
		this.riscoChecks = riscoChecks;
	}

	public Collection<RiscoFuncao> getRiscosFuncoes() {
		return riscosFuncoes;
	}

	public void setRiscosFuncoes(Collection<RiscoFuncao> riscosFuncoes) {
		this.riscosFuncoes = riscosFuncoes;
	}
	
	public String getDescricaoCBO() {
		return descricaoCBO;
    }

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}
	
	public void setCodigoCBOManager(CodigoCBOManager codigoCBOManager) {
		this.codigoCBOManager = codigoCBOManager;
	}
}