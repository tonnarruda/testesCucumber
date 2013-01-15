package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteIndiceManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ReajusteIndiceEditAction extends MyActionSupportEdit
{
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private ReajusteIndiceManager reajusteIndiceManager;
	private IndiceManager indiceManager;
	
	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();
	private Collection<Indice> indices = new ArrayList<Indice>();
	
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private ReajusteIndice reajusteIndice;
	private Indice indice;
	
	private String[] indicesCheck;
	private Collection<CheckBox> indicesCheckList = new ArrayList<CheckBox>();
	
	private char dissidioPor;
	private Double valorDissidio;
	
	public String prepareInsert() throws Exception
	{
		if (getEmpresaSistema().isAcIntegra())
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no AC Pessoal.");
		
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.INDICE);
		
		indices = indiceManager.findAll(getEmpresaSistema());
		
		return Action.SUCCESS;
	}

	public String prepareDissidio() throws Exception
	{
		if (getEmpresaSistema().isAcIntegra())
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no AC Pessoal.");

		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.INDICE);
		indicesCheckList = indiceManager.findOpcoesDissidio(getEmpresaSistema());
		
		return Action.SUCCESS;
	}
	
	public String insertColetivo() throws Exception
	{
		try
		{
			reajusteIndiceManager.insertReajustes(tabelaReajusteColaborador.getId(), LongUtil.arrayStringToArrayLong(indicesCheck), dissidioPor, valorDissidio);
			addActionMessage("Propostas de reajuste gravadas com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao gravar as propostas de reajuste coletivo");
		}
		finally
		{
			prepareDissidio();
		}

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

	public void setIndicesCheck(String[] indicesCheck) 
	{
		this.indicesCheck = indicesCheck;
	}

	public Collection<CheckBox> getIndicesCheckList() 
	{
		return indicesCheckList;
	}

	public char getDissidioPor() 
	{
		return dissidioPor;
	}

	public void setDissidioPor(char dissidioPor) 
	{
		this.dissidioPor = dissidioPor;
	}

	public Double getValorDissidio() 
	{
		return valorDissidio;
	}

	public void setValorDissidio(Double valorDissidio) 
	{
		this.valorDissidio = valorDissidio;
	}

	public void setReajusteIndiceManager(ReajusteIndiceManager reajusteIndiceManager) 
	{
		this.reajusteIndiceManager = reajusteIndiceManager;
	}
}