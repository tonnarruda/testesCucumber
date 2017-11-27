package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashSet;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.TelaAjudaESocial;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class HistoricoFuncaoEditAction extends MyActionSupportEdit
{
	private HistoricoFuncaoManager historicoFuncaoManager;
	private FuncaoManager funcaoManager;
	private ExameManager exameManager;
	private EpiManager epiManager;
	private RiscoManager riscoManager;
	private CursoManager cursoManager;
	private CodigoCBOManager codigoCBOManager;

	private HistoricoFuncao historicoFuncao = new HistoricoFuncao();
	private Funcao funcao;

	private Long[] examesChecked;
	private Collection<CheckBox> examesCheckList = new HashSet<CheckBox>();
	private Long[] episChecked;
	private Collection<CheckBox> episCheckList = new HashSet<CheckBox>();
	private Long[] riscoChecks;
	private Collection<CheckBox> cursosCheckList = new HashSet<CheckBox>();
	private Long[] cursosChecked;

	private Collection<Risco> riscos;
	private Collection<RiscoFuncao> riscosFuncoes;
	private String descricaoCBO;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		Boolean epiAtivo = true;
		
		if(historicoFuncao != null && historicoFuncao.getId() != null)
		{
			historicoFuncao = historicoFuncaoManager.findById(historicoFuncao.getId());
			epiAtivo = null;
			descricaoCBO = codigoCBOManager.findDescricaoByCodigo(historicoFuncao.getCodigoCbo());
		}

		Collection<Exame> exames = exameManager.findByEmpresaComAsoPadrao(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.populaCheckListBox(exames, "getId", "getNome", null);
		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), epiAtivo);
		setCursosCheckList(cursoManager.populaCheckListCurso(getEmpresaSistema().getId()));
		riscosFuncoes = riscoManager.findRiscosFuncoesByEmpresa(getEmpresaSistema().getId());

		setTelaAjuda(TelaAjudaESocial.EDICAO_HISTORICO_FUNCAO);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		
		funcao = funcaoManager.findByIdProjection(funcao.getId());
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao.setCodigoCbo(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), null).getCodigoCbo());
		descricaoCBO = codigoCBOManager.findDescricaoByCodigo(historicoFuncao.getCodigoCbo());
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		examesCheckList = CheckListBoxUtil.marcaCheckListBox(examesCheckList, historicoFuncao.getExames(), "getId");
		episCheckList = CheckListBoxUtil.marcaCheckListBox(episCheckList, historicoFuncao.getEpis(), "getId");
		cursosCheckList = CheckListBoxUtil.marcaCheckListBox(cursosCheckList, historicoFuncao.getCursos(), "getId");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			historicoFuncao.setFuncao(funcao);
			historicoFuncaoManager.saveHistorico(historicoFuncao, examesChecked, episChecked, riscoChecks, cursosChecked, riscosFuncoes);
			funcaoManager.atualizaNomeUltimoHistorico(funcao.getId());
			addActionSuccess("Histórico da função cadastrado com sucesso.");
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionWarning(e.getMessage());
			prepareInsert(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar o histórico da função.");
			prepareInsert(); 
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			historicoFuncaoManager.saveHistorico(historicoFuncao, examesChecked, episChecked, riscoChecks, cursosChecked, riscosFuncoes);
			funcaoManager.atualizaNomeUltimoHistorico(funcao.getId());
			addActionSuccess("Histórico da função atualizado com sucesso.");
			return Action.SUCCESS;

		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareInsert(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao atualizar o histórico da função.");
			prepareInsert(); 
			return Action.INPUT;
		}
	}

	public HistoricoFuncao getHistoricoFuncao()
	{
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao)
	{
		this.historicoFuncao = historicoFuncao;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public Funcao getFuncao()
	{
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public void setExameManager(ExameManager exameManager)
	{
		this.exameManager = exameManager;
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

	public Collection<CheckBox> getEpisCheckList()
	{
		return episCheckList;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public void setEpisChecked(Long[] episChecked)
	{
		this.episChecked = episChecked;
	}

	public Long[] getEpisChecked() {
		return episChecked;
	}

	public Collection<Risco> getRiscos() {
		return riscos;
	}

	public Collection<RiscoFuncao> getRiscosFuncoes() {
		return riscosFuncoes;
	}

	public void setRiscosFuncoes(Collection<RiscoFuncao> riscosFuncoes) {
		this.riscosFuncoes = riscosFuncoes;
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public Long[] getRiscoChecks() {
		return riscoChecks;
	}

	public void setRiscoChecks(Long[] riscoChecks) {
		this.riscoChecks = riscoChecks;
	}

	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public void setCursosCheckList(Collection<CheckBox> cursosCheckList) {
		this.cursosCheckList = cursosCheckList;
	}

	public Long[] getCursosChecked() {
		return cursosChecked;
	}

	public void setCursosChecked(Long[] cursosChecked) {
		this.cursosChecked = cursosChecked;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}

	public String getDescricaoCBO() {
		return descricaoCBO;
	}

	public void setDescricaoCBO(String descricaoCBO) {
		this.descricaoCBO = descricaoCBO;
	}

	public void setCodigoCBOManager(CodigoCBOManager codigoCBOManager) {
		this.codigoCBOManager = codigoCBOManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager) {
		this.funcaoManager = funcaoManager;
	}
}