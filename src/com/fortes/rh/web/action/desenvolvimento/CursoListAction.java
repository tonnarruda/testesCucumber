package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class CursoListAction extends MyActionSupportList
{
	private CursoManager cursoManager;

	private Collection<Curso> cursos;
	private Curso curso;

	private String nomeCursoBusca;

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

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
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