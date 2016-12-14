package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;


@SuppressWarnings("serial")
public class AvaliacaoTurmaListAction extends  MyActionSupportList
{
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private EmpresaManager empresaManager;
	
	private ColaboradorQuestionario colaboradorQuestionario;

	private Collection<AvaliacaoTurma> avaliacaoTurmas = new ArrayList<AvaliacaoTurma>(0);

	private AvaliacaoTurma avaliacaoTurma;
	private Colaborador colaborador;

	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private String[] empresasCheck;
	
	private String voltarPara = "";

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
   		this.setTotalSize(avaliacaoTurmaManager.getCount(getEmpresaSistema().getId()));
   		avaliacaoTurmas = avaliacaoTurmaManager.findToListByEmpresa(getEmpresaSistema().getId(), getPage(), getPagingSize());

   		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome", null);
   		
        return Action.SUCCESS;
	}

	public String prepareResponderAvaliacaoTurma() throws Exception
	{
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoTurmaManager.delete(avaliacaoTurma.getId(), getEmpresaSistema().getId());
		addActionSuccess("Modelo de avaliação de curso excluído com sucesso.");

		return Action.SUCCESS;
	}

    public String clonarAvaliacaoTurma() throws Exception
    {
    	try
		{
    		Long[] empresasIds = LongUtil.arrayStringToArrayLong(empresasCheck);
    		if (empresasIds != null && empresasIds.length > 0)
    			avaliacaoTurmaManager.clonarAvaliacaoTurma(avaliacaoTurma.getId(), empresasIds);
    		else
    			avaliacaoTurmaManager.clonarAvaliacaoTurma(avaliacaoTurma.getId(), getEmpresaSistema().getId());
    		
    		addActionSuccess("Modelo de avaliação de curso clonado com sucesso.");
			list();
    		return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível clonar o modelo de avaliação, devido a um erro interno.<br><br> Entre em contato com o administrador do sistema.");
			list();
			return Action.INPUT;
		}
    }

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		return colaboradorQuestionario;
	}

	public String getVoltarPara()
	{
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara)
	{
		this.voltarPara = voltarPara;
	}

	public Collection<AvaliacaoTurma> getAvaliacaoTurmas()
	{
		return avaliacaoTurmas;
	}

	public void setAvaliacaoTurmas(Collection<AvaliacaoTurma> avaliacaoTurmas)
	{
		this.avaliacaoTurmas = avaliacaoTurmas;
	}

	public AvaliacaoTurma getAvaliacaoTurma()
	{
		return avaliacaoTurma;
	}

	public void setAvaliacaoTurma(AvaliacaoTurma avaliacaoTurma)
	{
		this.avaliacaoTurma = avaliacaoTurma;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager)
	{
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheckList(Collection<CheckBox> empresasCheckList) {
		this.empresasCheckList = empresasCheckList;
	}

	public String[] getEmpresasCheck() {
		return empresasCheck;
	}

	public void setEmpresasCheck(String[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}