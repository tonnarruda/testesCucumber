package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;


@SuppressWarnings("serial")
public class EntrevistaListAction extends  MyActionSupportList
{
	private EntrevistaManager entrevistaManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorQuestionario colaboradorQuestionario;

	private Collection<Entrevista> entrevistas = new ArrayList<Entrevista>();

	private Entrevista entrevista;
	private Colaborador colaborador;
	
	private String[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();

	private String voltarPara = "";
	
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
   		this.setTotalSize(entrevistaManager.getCount(getEmpresaSistema().getId()));
   		entrevistas = entrevistaManager.findToListByEmpresa(getEmpresaSistema().getId(), getPage(), getPagingSize());

		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");

        return Action.SUCCESS;
	}

	public String prepareResponderEntrevista() throws Exception
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findColaboradorComEntrevistaDeDesligamento(colaborador.getId());

		if (colaboradorQuestionario == null) 
		{
			entrevistas = entrevistaManager.findAllSelect(getEmpresaSistema().getId(), Boolean.TRUE);
			colaborador = colaboradorManager.findByIdHistoricoAtual(colaborador.getId(), Boolean.FALSE);
			return Action.SUCCESS;
		}

		return "entrevistaDeDesligamentoJaRespondida";
	}

	public String delete() throws Exception
	{
		try {
			entrevistaManager.delete(entrevista.getId(), getEmpresaSistema().getId());
			addActionSuccess("Entrevista excluída com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir esta entrevista de desligamento.");
		}

		return Action.SUCCESS;
	}

	public Collection<Entrevista> getEntrevistas()
	{
		return entrevistas;
	}

    public String clonarEntrevista() throws Exception
    {
    	try
		{
			Long[] empresasIds = LongUtil.arrayStringToArrayLong(empresasCheck);
			if (empresasIds != null && empresasIds.length > 0)
				entrevistaManager.clonarEntrevista(entrevista.getId(), empresasIds);
			else
				entrevistaManager.clonarEntrevista(entrevista.getId(), getEmpresaSistema().getId());
			
    		return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível clonar o modelo de entrevista, devido a um erro interno.<br><br> Entre em contato com o administrador do sistema.");
			list();
			return Action.INPUT;
		}
    }

	public Entrevista getEntrevista(){
		if(entrevista == null){
			entrevista = new Entrevista();
		}
		return entrevista;
	}

	public void setEntrevista(Entrevista entrevista){
		this.entrevista=entrevista;
	}

	public void setEntrevistaManager(EntrevistaManager entrevistaManager){
		this.entrevistaManager=entrevistaManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
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

	public String[] getEmpresasCheck() {
		return empresasCheck;
	}

	public void setEmpresasCheck(String[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheckList(Collection<CheckBox> empresasCheckList) {
		this.empresasCheckList = empresasCheckList;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}