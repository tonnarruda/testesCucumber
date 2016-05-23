package com.fortes.rh.web.action.cargosalario;


import java.util.Collection;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class FaturamentoMensalEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private FaturamentoMensalManager faturamentoMensalManager;
	private FaturamentoMensal faturamentoMensal;
	private Collection<FaturamentoMensal> faturamentoMensals;

	private String dataMesAno;
	
	private void prepare() throws Exception
	{
		if(faturamentoMensal != null && faturamentoMensal.getId() != null)
		{
			faturamentoMensal = (FaturamentoMensal) faturamentoMensalManager.findById(faturamentoMensal.getId());
			dataMesAno = DateUtil.formataMesAno(faturamentoMensal.getMesAno());			
		}
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
		if(faturamentoMensalManager.verifyExists(new String[]{"mesAno", "empresa.id"}, new Object[]{DateUtil.criarDataMesAno(dataMesAno), getEmpresaSistema().getId()}))
		{
			addActionMessage("Faturamento para esse mês/ano já cadastrado.");
			return Action.INPUT;
		}
		
		faturamentoMensal.setEmpresa(getEmpresaSistema());
		faturamentoMensal.setMesAno(DateUtil.criarDataMesAno(dataMesAno));
		faturamentoMensalManager.save(faturamentoMensal);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		faturamentoMensalManager.update(faturamentoMensal);
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		faturamentoMensals = faturamentoMensalManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			faturamentoMensalManager.remove(faturamentoMensal.getId());
			addActionSuccess("Faturamento excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este Faturamento.");
		}

		return list();
	}
	
	public FaturamentoMensal getFaturamentoMensal()
	{
		if(faturamentoMensal == null)
			faturamentoMensal = new FaturamentoMensal();
		return faturamentoMensal;
	}

	public void setFaturamentoMensal(FaturamentoMensal faturamentoMensal)
	{
		this.faturamentoMensal = faturamentoMensal;
	}

	public void setFaturamentoMensalManager(FaturamentoMensalManager faturamentoMensalManager)
	{
		this.faturamentoMensalManager = faturamentoMensalManager;
	}
	
	public Collection<FaturamentoMensal> getFaturamentoMensals()
	{
		return faturamentoMensals;
	}

	public String getDataMesAno() {
		return dataMesAno;
	}

	public void setDataMesAno(String dataMesAno) {
		this.dataMesAno = dataMesAno;
	}
}
