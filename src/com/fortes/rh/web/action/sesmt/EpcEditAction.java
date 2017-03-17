package com.fortes.rh.web.action.sesmt;


import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class EpcEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private EpcManager epcManager;

	private Epc epc;

	private boolean jaValidouEmpresa = false;

	private void prepare() throws Exception
	{
		if(epc != null && epc.getId() != null)
			epc = (Epc) epcManager.findById(epc.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if( ! jaValidouEmpresa)
			validaEmpresa();

		if(hasActionErrors())
			return ERROR;

		return Action.SUCCESS;
	}

	private void validaEmpresa()
	{
		Epc epcTmp = epcManager.findByIdProjection(epc.getId());

		if(! getEmpresaSistema().equals(epcTmp.getEmpresa()))
		{
			addActionError("O EPC solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
		}

		jaValidouEmpresa = true;
	}

	public String insert() throws Exception
	{
		valida();
		if(hasActionErrors())
		{
			prepareInsert();
			return ERROR;
		}

		epc.setEmpresa(getEmpresaSistema());

		epcManager.save(epc);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		valida();
		if(hasActionErrors())
		{
			prepareUpdate();
			return ERROR;
		}

		epc.setEmpresa(getEmpresaSistema());

		epcManager.update(epc);

		return Action.SUCCESS;
	}

	private void valida()
	{
		if(epc.getDescricao() == null || "".equals(epc.getDescricao().trim()))
				addActionError("Campo \"Descrição\" em branco.");

		// Se é update
		if(epc.getId() != null)
			validaEmpresa();
	}

	public Object getModel()
	{
		return getEpc();
	}

	public Epc getEpc()
	{
		if(epc == null)
			epc = new Epc();
		return epc;
	}

	public void setEpc(Epc epc)
	{
		this.epc = epc;
	}
}