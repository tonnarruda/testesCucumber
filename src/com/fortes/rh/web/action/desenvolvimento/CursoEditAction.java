package com.fortes.rh.web.action.desenvolvimento;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class CursoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private CursoManager cursoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private Curso curso;
	private boolean codigoTRUCurso;
	private boolean compartilharCursos;
	private String nomeCursoBusca;
	private int page;
	
	private String[] avaliacaoCursoCheck;
	private Collection<CheckBox> avaliacaoCursoCheckList = new ArrayList<CheckBox>();
	private Long[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	
	private Collection<Empresa> empresas;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(curso != null && curso.getId() != null)
			curso = (Curso) cursoManager.findById(curso.getId());
		
		codigoTRUCurso = getEmpresaSistema().isCodigoTruCurso();
		Collection<AvaliacaoCurso> avaliacoes = avaliacaoCursoManager.findAll(new String[]{"titulo"});
		avaliacaoCursoCheckList = CheckListBoxUtil.populaCheckListBox(avaliacoes, "getId", "getTitulo");
		
		empresas = empresaManager.findEmpresasPermitidas(true, getEmpresaSistema().getId(), getUsuarioLogado().getId(), null);
		empresas.remove(getEmpresaSistema());
		empresasCheckList = CheckListBoxUtil.populaCheckListBox(empresas, "getId","getNome");
		
		compartilharCursos = parametrosDoSistemaManager.findById(1L).getCompartilharCursos();
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		avaliacaoCursoCheckList = CheckListBoxUtil.marcaCheckListBox(avaliacaoCursoCheckList, curso.getAvaliacaoCursos(), "getId");
		empresasCheckList = CheckListBoxUtil.marcaCheckListBox(empresasCheckList, curso.getEmpresasParticipantes(), "getId");

		if (!curso.getEmpresa().getId().equals(getEmpresaSistema().getId()) && !curso.getEmpresasParticipantesIds().contains(getEmpresaSistema().getId()))
		{
			addActionWarning("O Curso solicitado não existe ou não esta compartilhada para a empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		
		CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>();
		curso.setAvaliacaoCursos(collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, avaliacaoCursoCheck));

		Collection<Empresa> empresasParticipantes = new CollectionUtil<Empresa>().convertArrayLongToCollection(Empresa.class, empresasCheck);
		
		curso.setEmpresasParticipantes(empresasParticipantes);
		curso.setEmpresa(getEmpresaSistema());
		cursoManager.save(curso);

		return Action.SUCCESS;
	}
	

	public String update() throws Exception
	{
		if (!curso.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionError("O Curso solicitado não existe na empresa.");
			return Action.INPUT;
		}

		try
		{
			Collection<Empresa> empresasParticipantes = new CollectionUtil<Empresa>().convertArrayLongToCollection(Empresa.class, empresasCheck);
			curso.setEmpresasParticipantes(empresasParticipantes);
			
			cursoManager.update(curso, getEmpresaSistema(), avaliacaoCursoCheck);
		}
		catch (Exception e)
		{
			addActionError("Erro ao editar curso");
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}


	public Object getModel()
	{
		return getCurso();
	}

	public Curso getCurso()
	{
		if(curso == null)
			curso = new Curso();
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public Collection<CheckBox> getAvaliacaoCursoCheckList()
	{
		return avaliacaoCursoCheckList;
	}

	public void setAvaliacaoCursoCheck(String[] avaliacaoCursoCheck)
	{
		this.avaliacaoCursoCheck = avaliacaoCursoCheck;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public String getNomeCursoBusca()
	{
		return nomeCursoBusca;
	}

	public void setNomeCursoBusca(String nomeCursoBusca)
	{
		this.nomeCursoBusca = nomeCursoBusca;
	}

	public boolean isCodigoTRUCurso() {
		return codigoTRUCurso;
	}

	public void setCodigoTRUCurso(boolean codigoTRUCurso) {
		this.codigoTRUCurso = codigoTRUCurso;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheck(Long[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public boolean isCompartilharCursos() {
		return compartilharCursos;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}