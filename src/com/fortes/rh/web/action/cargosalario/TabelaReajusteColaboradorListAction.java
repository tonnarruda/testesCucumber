package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;

import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class TabelaReajusteColaboradorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private ReajusteColaboradorManager reajusteColaboradorManager;

	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors;

	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private String msgAlert;

	public String list() throws Exception 
	{
		setTotalSize(tabelaReajusteColaboradorManager.getCount(getEmpresaSistema().getId()));
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllList(getPage(), getPagingSize(), getEmpresaSistema().getId());

		tabelaReajusteColaboradorManager.marcaUltima(tabelaReajusteColaboradors);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			tabelaReajusteColaboradorManager.remove(tabelaReajusteColaborador);
			addActionSuccess("Planejamento de Realinhamento excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este Planejamento de Realinhamento.");
		}

		return list();
	}

	public String cancelarReajuste() throws Exception
	{
		try
		{
			tabelaReajusteColaboradorManager.cancelar(tabelaReajusteColaborador.getTipoReajuste(), tabelaReajusteColaborador.getId(), getEmpresaSistema());
			addActionSuccess("Cancelamento efetuado com sucesso.");
		}
		catch (IntegraACException e)
		{
			e.printStackTrace();
			addActionError(e.getMensagemDetalhada());
		}
		catch (Exception e)
		{
			String message = "Não foi possível efetuar o cancelamento.";
			
			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();
			
			e.printStackTrace();
			addActionError(message);
		}
		
		return list();
	}

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors() 
	{
		return tabelaReajusteColaboradors;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador()
	{
		if(tabelaReajusteColaborador == null){
			tabelaReajusteColaborador = new TabelaReajusteColaborador();
		}
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		this.tabelaReajusteColaborador=tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager)
	{
		this.tabelaReajusteColaboradorManager=tabelaReajusteColaboradorManager;
	}

	public ReajusteColaboradorManager getReajusteColaboradorManager()
	{
		return reajusteColaboradorManager;
	}

	public void setReajusteColaboradorManager(
			ReajusteColaboradorManager reajusteColaboradorManager)
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}
}