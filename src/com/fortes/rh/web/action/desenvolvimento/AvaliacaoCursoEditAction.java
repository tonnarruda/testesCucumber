package com.fortes.rh.web.action.desenvolvimento;

import java.util.Map;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings( { "serial" })
public class AvaliacaoCursoEditAction extends MyActionSupport implements ModelDriven
{
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private AvaliacaoCurso avaliacaoCurso;
	private Map tipos = new TipoAvaliacaoCurso();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if (avaliacaoCurso != null && avaliacaoCurso.getId() != null)
			avaliacaoCurso = (AvaliacaoCurso) avaliacaoCursoManager.findById(avaliacaoCurso.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		avaliacaoCursoManager.save(avaliacaoCurso);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		avaliacaoCursoManager.update(avaliacaoCurso);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getAvaliacaoCurso();
	}

	public AvaliacaoCurso getAvaliacaoCurso()
	{
		if (avaliacaoCurso == null)
			avaliacaoCurso = new AvaliacaoCurso();
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