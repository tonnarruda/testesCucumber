package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EstabelecimentoListAction extends MyActionSupportList
{
	private EstabelecimentoManager estabelecimentoManager;

	private Collection<Estabelecimento> estabelecimentos;

	private Estabelecimento estabelecimento;
	private boolean integradoAC;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		
		String[] keys = new String[]{"empresa.id"};
		Object[] valores = new Object[]{getEmpresaSistema().getId()};
		String[] ordem = new String[]{"nome"};
		
		setTotalSize(estabelecimentoManager.getCount(keys, valores));
		estabelecimentos = estabelecimentoManager.find(getPage(),getPagingSize(), keys, valores, ordem);
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if (!getEmpresaSistema().isAcIntegra())
		{
			String[] key = new String[]{"id","empresa.id"};
			Object[] values = new Object[]{estabelecimento.getId(), getEmpresaSistema().getId()};
			
			if(estabelecimentoManager.verifyExists(key, values))
			{
				estabelecimentoManager.remove(new Long[]{estabelecimento.getId()});
				addActionMessage("Estabelecimento excluído com sucesso.");
			}
			else
				addActionError("O Estabelecimento solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
		}
		else
			addActionError("O Estabelecimento não pode ser excluído por conta da integração com o Fortes Pessoal.");

		return Action.SUCCESS;
	}

	public Collection getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public Estabelecimento getEstabelecimento()
	{
		if(estabelecimento == null)
		{
			estabelecimento = new Estabelecimento();
		}
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento){
		this.estabelecimento=estabelecimento;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager){
		this.estabelecimentoManager=estabelecimentoManager;
	}
}