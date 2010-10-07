package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class PesquisaListAction extends MyActionSupportList
{
    private PesquisaManager pesquisaManager;

    private Pesquisa pesquisa;

    private Collection<Pesquisa> pesquisas = new ArrayList<Pesquisa>();

	@SuppressWarnings("unused")
	private String areasIds;//Não apague ta sendo usado no ftl

	private TipoPergunta tipoPergunta = new TipoPergunta();

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

}