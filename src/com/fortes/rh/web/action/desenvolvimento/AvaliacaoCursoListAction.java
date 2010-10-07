package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AvaliacaoCursoListAction extends MyActionSupportList
{
	private AvaliacaoCursoManager avaliacaoCursoManager;

	private Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
	private AvaliacaoCurso avaliacaoCurso;
	private Map tipos = new TipoAvaliacaoCurso();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		if(avaliacaoCurso != null )
			avaliacaoCursos = avaliacaoCursoManager.buscaFiltro(avaliacaoCurso.getTitulo());
		else
			avaliacaoCursos = avaliacaoCursoManager.findAll(new String[]{"titulo"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoCursoManager.remove(avaliacaoCurso.getId());
		addActionMessage("Avaliação excluída com sucesso.");

		return Action.SUCCESS;
	}

	public Collection getAvaliacaoCursos()
	{
		return avaliacaoCursos;
	}

	public AvaliacaoCurso getAvaliacaoCurso()
	{
		if (avaliacaoCurso == null)
		{
			avaliacaoCurso = new AvaliacaoCurso();
		}
		return avaliacaoCurso;
	}

	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso)
	{
		this.avaliacaoCurso = avaliacaoCurso;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public Map getTipos()
	{
		return tipos;
	}
}