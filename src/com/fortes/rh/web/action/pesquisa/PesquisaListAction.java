package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class PesquisaListAction extends MyActionSupportList
{
    private PesquisaManager pesquisaManager;
    private ColaboradorManager colaboradorManager;
    private EmpresaManager empresaManager;
    private QuestionarioManager questionarioManager;

    private Pesquisa pesquisa;
    private Colaborador colaborador;

    private Collection<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
    private Collection<Questionario> questionarios;

	@SuppressWarnings("unused")
	private String areasIds;//Não apague ta sendo usado no ftl

	private TipoPergunta tipoPergunta = new TipoPergunta();
	private String matricula = "";
	private String empresaCodigo = "";
	private String SESSION_CANDIDATO_NOME = "";
	private String SESSION_EMPRESA = "";
	private String grupoAC = null;

	public String execute() throws Exception
    {
        return Action.SUCCESS;
    }

    public String list() throws Exception
    {
   		this.setTotalSize(pesquisaManager.getCount(getEmpresaSistema().getId()));
   		pesquisas = pesquisaManager.findToListByEmpresa(getEmpresaSistema().getId(), getPage(), getPagingSize());

        return Action.SUCCESS;
    }
    
    public String listTrafego() throws Exception
    {   
    	try 
    	{			
			Empresa empresa = empresaManager.findByCodigoAC(empresaCodigo, grupoAC);
			colaborador = colaboradorManager.findByCodigoAC(matricula, empresa);
			
			SESSION_CANDIDATO_NOME = colaborador.getNome();
			SESSION_EMPRESA = empresa.getNome();
			
			questionarios = questionarioManager.findQuestionario(colaborador.getId());
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    		addActionError("Erro ao exibir pesquisas");
    	}
    	
    	return Action.SUCCESS;
    }

    public String delete() throws Exception
    {
		pesquisaManager.delete(pesquisa.getId(), getEmpresaSistema().getId());
		setActionMsg("Pesquisa excluída com sucesso.");

		return Action.SUCCESS;
    }

    public String clonarPesquisa() throws Exception
    {
		pesquisa = pesquisaManager.clonarPesquisa(pesquisa.getId());
		return Action.SUCCESS;
    }

	public Collection<Pesquisa> getPesquisas()
	{
		return pesquisas;
	}

	public void setPesquisaManager(PesquisaManager pesquisaManager)
	{
		this.pesquisaManager = pesquisaManager;
	}

	public Pesquisa getPesquisa()
	{
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa)
	{
		this.pesquisa = pesquisa;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setEmpresaCodigo(String empresaCodigo) {
		this.empresaCodigo = empresaCodigo;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager) {
		this.questionarioManager = questionarioManager;
	}

	public Collection<Questionario> getQuestionarios() {
		return questionarios;
	}

	public String getSESSION_CANDIDATO_NOME() {
		return SESSION_CANDIDATO_NOME;
	}

	public String getSESSION_EMPRESA() {
		return SESSION_EMPRESA;
	}

	public String getEmpresaCodigo() {
		return empresaCodigo;
	}

}