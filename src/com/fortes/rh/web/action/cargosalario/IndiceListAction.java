package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class IndiceListAction extends MyActionSupportList
{
	private IndiceManager indiceManager;

	private Collection<Indice> indices;

	private Indice indiceAux = new Indice();
	private boolean integradoAC;

	public String list() throws Exception
	{
		setTotalSize(indiceManager.getCount(indiceAux.getNome()));
		indices = indiceManager.findIndices(getPage(), getPagingSize(), indiceAux.getNome());
		
		integradoAC = getEmpresaSistema().isAcIntegra();
		
		if(integradoAC)
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no Fortes Pessoal.");

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			integradoAC = getEmpresaSistema().isAcIntegra();
			if(!integradoAC)
			{
				indiceManager.removeIndice(indiceAux.getId());
				addActionSuccess("Índice excluído com sucesso.");
			}
			
		} catch (Exception e) {
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este índice.");
		}

		return Action.SUCCESS;
	}

	public Collection<Indice> getIndices()
	{
		return indices;
	}

	public Indice getIndiceAux()
	{
		if (indiceAux == null)
			indiceAux = new Indice();

		return indiceAux;
	}

	public void setIndiceAux(Indice indiceAux)
	{
		this.indiceAux = indiceAux;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}
}