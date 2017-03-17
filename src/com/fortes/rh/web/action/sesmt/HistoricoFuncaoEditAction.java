package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.Cargo;
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
	@Autowired private HistoricoFuncaoManager historicoFuncaoManager;
	@Autowired private ExameManager exameManager;
	@Autowired private RiscoManager riscoManager;
	@Autowired private CursoManager cursoManager;
	@Autowired private EpiManager epiManager;

	private HistoricoFuncao historicoFuncao = new HistoricoFuncao();
	private Funcao funcao;
	private Cargo cargoTmp;

	private Long[] examesChecked;
	private Collection<CheckBox> examesCheckList = new HashSet<CheckBox>();
	private Long[] episChecked;
	private Collection<CheckBox> episCheckList = new HashSet<CheckBox>();
	private Long[] riscoChecks;
	private Collection<CheckBox> cursosCheckList = new HashSet<CheckBox>();
	private Long[] cursosChecked;

	private Collection<Risco> riscos;
	private Collection<RiscoFuncao> riscosFuncoes;
	
	private boolean veioDoSESMT;
	
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
		}

		Collection<Exame> exames = exameManager.findByEmpresaComAsoPadrao(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.populaCheckListBox(exames, "getId", "getNome", null);
		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), epiAtivo);
		setCursosCheckList(cursoManager.populaCheckListCurso(getEmpresaSistema().getId()));
		riscosFuncoes = riscoManager.findRiscosFuncoesByEmpresa(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();

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
	
			if(veioDoSESMT)
				return "SUCESSO_VEIO_SESMT";
			else
				return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareInsert(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar o histórico da função");
			prepareInsert(); 
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			historicoFuncaoManager.saveHistorico(historicoFuncao, examesChecked, episChecked, riscoChecks, cursosChecked, riscosFuncoes);
	
			if(veioDoSESMT)
				return "SUCESSO_VEIO_SESMT";
			else
				return Action.SUCCESS;

		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareInsert(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar o histórico da função");
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

	public Funcao getFuncao()
	{
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public Cargo getCargoTmp()
	{
		return cargoTmp;
	}

	public void setCargoTmp(Cargo cargoTmp)
	{
		this.cargoTmp = cargoTmp;
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

	public void setEpisChecked(Long[] episChecked)
	{
		this.episChecked = episChecked;
	}

	public Long[] getEpisChecked() {
		return episChecked;
	}

	public boolean isVeioDoSESMT()
	{
		return veioDoSESMT;
	}

	public void setVeioDoSESMT(boolean veioDoSESMT)
	{
		this.veioDoSESMT = veioDoSESMT;
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
}