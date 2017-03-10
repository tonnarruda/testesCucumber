package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class IndiceListAction extends MyActionSupportList
{
	@Autowired private IndiceManager indiceManager;

	private Collection<Indice> indices;

	private Indice indiceAux = new Indice();
	private boolean integradoAC;

	public String list() throws Exception
	{
		setTotalSize(indiceManager.getCount(indiceAux.getNome()));
//		indices = indiceManager.find(getPage(),getPagingSize(), new String[]{"nome"});
		indices = indiceManager.findIndices(getPage(), getPagingSize(), indiceAux.getNome());
		
		integradoAC = getEmpresaSistema().isAcIntegra();
		
		if(integradoAC)
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no Fortes Pessoal.");

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(!integradoAC)
		{
			indiceManager.removeIndice(indiceAux.getId());
			addActionMessage("Índice excluído com sucesso.");
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

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}
}