package com.fortes.rh.web.action.desenvolvimento;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
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

	private Curso curso;
	private String[] avaliacaoCursoCheck;
	private Collection<CheckBox> avaliacaoCursoCheckList = new ArrayList<CheckBox>();

	private String nomeCursoBusca;
	private int page;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(curso != null && curso.getId() != null)
			curso = (Curso) cursoManager.findById(curso.getId());
		
		Collection<AvaliacaoCurso> avaliacoes = avaliacaoCursoManager.findAll(new String[]{"titulo"});
		avaliacaoCursoCheckList = CheckListBoxUtil.populaCheckListBox(avaliacoes, "getId", "getTitulo");
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

		if (!curso.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionError("O Curso solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		
		CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>();
		curso.setAvaliacaoCursos(collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, avaliacaoCursoCheck));

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
}