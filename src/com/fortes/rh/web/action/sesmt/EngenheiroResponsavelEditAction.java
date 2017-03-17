package com.fortes.rh.web.action.sesmt;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class EngenheiroResponsavelEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	@Autowired private EngenheiroResponsavelManager engenheiroResponsavelManager;

	private EngenheiroResponsavel engenheiroResponsavel;

	private void prepare() throws Exception
	{
		if(engenheiroResponsavel != null && engenheiroResponsavel.getId() != null)
			engenheiroResponsavel = (EngenheiroResponsavel) engenheiroResponsavelManager.findByIdProjection(engenheiroResponsavel.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		if(engenheiroResponsavel == null || !getEmpresaSistema().getId().equals(engenheiroResponsavel.getEmpresa().getId()))
		{
			addActionError("O Engenheiro solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		engenheiroResponsavel.setEmpresa(getEmpresaSistema());
		engenheiroResponsavelManager.save(engenheiroResponsavel);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		engenheiroResponsavel.setEmpresa(getEmpresaSistema());
		engenheiroResponsavelManager.update(engenheiroResponsavel);
		return Action.SUCCESS;
	}

	public EngenheiroResponsavel getEngenheiroResponsavel()
	{
		if(engenheiroResponsavel == null)
			engenheiroResponsavel = new EngenheiroResponsavel();
		return engenheiroResponsavel;
	}

	public void setEngenheiroResponsavel(EngenheiroResponsavel engenheiroResponsavel)
	{
		this.engenheiroResponsavel = engenheiroResponsavel;
	}
}