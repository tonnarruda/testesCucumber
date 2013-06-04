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
	private Map<Character, String> tipos = new TipoAvaliacaoCurso();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String titulo = avaliacaoCurso != null ? avaliacaoCurso.getTitulo() : null;
		avaliacaoCursos = avaliacaoCursoManager.buscaFiltro(titulo);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoCursoManager.remove(avaliacaoCurso.getId());
		addActionSuccess("Avaliação excluída com sucesso.");

		return Action.SUCCESS;
	}

	public Collection<AvaliacaoCurso> getAvaliacaoCursos()
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

	public Map<Character, String> getTipos()
	{
		return tipos;
	}
}