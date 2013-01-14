package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ReajusteIndiceEditAction extends MyActionSupportEdit
{
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private IndiceManager indiceManager;
	
	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();
	private Collection<Indice> indices = new ArrayList<Indice>();
	
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private ReajusteIndice reajusteIndice;
	private Indice indice;
	
	public String prepareInsert() throws Exception
	{
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.INDICE);
		
		indices = indiceManager.findAll(getEmpresaSistema());
		
		if (getEmpresaSistema().isAcIntegra())
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no AC Pessoal.");
		
		return Action.SUCCESS;
	}

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors() 
	{
		return tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager) 
	{
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador() 
	{
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador) 
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public ReajusteIndice getReajusteIndice() 
	{
		return reajusteIndice;
	}

	public void setReajusteIndice(ReajusteIndice reajusteIndice) 
	{
		this.reajusteIndice = reajusteIndice;
	}

	public Indice getIndice() 
	{
		return indice;
	}

	public void setIndice(Indice indice) 
	{
		this.indice = indice;
	}

	public void setIndiceManager(IndiceManager indiceManager) 
	{
		this.indiceManager = indiceManager;
	}

	public Collection<Indice> getIndices() 
	{
		return indices;
	}
}