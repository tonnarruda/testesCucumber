package com.fortes.rh.web.action.avaliacao;


import java.util.Collection;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AvaliacaoPraticaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private AvaliacaoPratica avaliacaoPratica;
	private Collection<AvaliacaoPratica> avaliacaoPraticas;

	private void prepare() throws Exception
	{
		if(avaliacaoPratica != null && avaliacaoPratica.getId() != null)
			avaliacaoPratica = (AvaliacaoPratica) avaliacaoPraticaManager.findById(avaliacaoPratica.getId());
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
		avaliacaoPratica.setEmpresa(getEmpresaSistema());
		avaliacaoPraticaManager.save(avaliacaoPratica);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		avaliacaoPraticaManager.update(avaliacaoPratica);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setTotalSize(avaliacaoPraticaManager.getCount(new String[] { "empresa.id" }, new Object[] { getEmpresaSistema().getId() }));
		avaliacaoPraticas = avaliacaoPraticaManager.find(getPage(), getPagingSize(), new String[] {"empresa.id"}, new Object[] { getEmpresaSistema().getId() }, new String[] { "titulo" });
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			avaliacaoPraticaManager.remove(avaliacaoPratica.getId());
			addActionMessage("Avaliação prática excluída com sucesso.");
		}
		catch (Exception e)
		{
			ExceptionUtil.traduzirMensagem(this, e);
			e.printStackTrace();
			addActionError("Não foi possível excluir esta avaliação prática.");
		}

		return list();
	}
	
	public AvaliacaoPratica getAvaliacaoPratica()
	{
		if(avaliacaoPratica == null)
			avaliacaoPratica = new AvaliacaoPratica();
		return avaliacaoPratica;
	}

	public void setAvaliacaoPratica(AvaliacaoPratica avaliacaoPratica)
	{
		this.avaliacaoPratica = avaliacaoPratica;
	}

	public void setAvaliacaoPraticaManager(AvaliacaoPraticaManager avaliacaoPraticaManager)
	{
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}
	
	public Collection<AvaliacaoPratica> getAvaliacaoPraticas()
	{
		return avaliacaoPraticas;
	}
}
