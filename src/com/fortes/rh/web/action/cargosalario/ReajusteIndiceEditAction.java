package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

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
	@Autowired private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	@Autowired private ReajusteIndiceManager reajusteIndiceManager;
	
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
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no Fortes Pessoal.");
		
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.INDICE);
		
		return Action.SUCCESS;
	}
	
	public String insert() throws Exception
	{
		try
		{
			reajusteIndiceManager.insertReajustes(tabelaReajusteColaborador.getId(), new Long[] { indice.getId() }, dissidioPor, valorDissidio);
			addActionMessage("Proposta de reajuste gravada com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao gravar a proposta de reajuste");
		}
		finally
		{
			prepareInsert();
		}

		return Action.SUCCESS;
	}
	
	public String prepareUpdate() throws Exception
	{
		reajusteIndice = reajusteIndiceManager.findByIdProjection(reajusteIndice.getId());
		
		return Action.SUCCESS;
	}
	
	public String update() throws Exception
	{
		try
		{
			reajusteIndiceManager.updateValorProposto(reajusteIndice.getId(), reajusteIndice.getValorAtual(), dissidioPor, valorDissidio);
			addActionMessage("Proposta de reajuste alterada com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao alterar a proposta de reajuste");
		}
		finally
		{
			prepareUpdate();
		}

		return Action.SUCCESS;
	}

	public String prepareDissidio() throws Exception
	{
		if (getEmpresaSistema().isAcIntegra())
			addActionMessage("A manutenção do Cadastro de Índices deve ser realizada no Fortes Pessoal.");

		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.INDICE);
		
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
	
	public String delete() throws Exception 
	{
		reajusteIndiceManager.remove(new Long[]{ reajusteIndice.getId() });

		return Action.SUCCESS;
	}

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors() 
	{
		return tabelaReajusteColaboradors;
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
}