package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class CursoListAction extends MyActionSupportList
{
	private CursoManager cursoManager;
	private EmpresaManager empresaManager;
	private Collection<Curso> cursos;
	private Curso curso;
	private String nomeCursoBusca;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private String[] empresasCheck;
	private String novoTituloCursoClonado = "";

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		curso = getCurso();
		curso.setNome(nomeCursoBusca);
		
		setTotalSize(cursoManager.getCount(curso, getEmpresaSistema().getId()));
		cursos = cursoManager.findByFiltro(getPage(), getPagingSize(), curso, getEmpresaSistema().getId());
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_T&D_CAD");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");
		

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if (curso != null && curso.getId() != null && cursoManager.verifyExists(new String[]{"id", "empresa.id"}, new Object[]{curso.getId(),getEmpresaSistema().getId()}))
		{
			cursoManager.remove(curso.getId());
			addActionMessage("Curso excluído com sucesso.");
		}
		else
			addActionError("O Curso solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");

		return Action.SUCCESS;
	}
	
	public String clonar(){
		try {
			cursoManager.clonar(curso.getId(),getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(empresasCheck), novoTituloCursoClonado);
			addActionSuccess("Curso clonado com sucesso.");
			
		} catch (Exception e) {
			addActionError("Não foi possível clonar o curso.");
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public Curso getCurso()
	{
		if (curso == null)
		{
			curso = new Curso();
		}
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public String getNomeCursoBusca()
	{
		return nomeCursoBusca;
	}

	public void setNomeCursoBusca(String nomeCursoBusca)
	{
		this.nomeCursoBusca = nomeCursoBusca;
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

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setNovoTituloCursoClonado(String novoTituloCursoClonado) {
		this.novoTituloCursoClonado = novoTituloCursoClonado;
	}

}