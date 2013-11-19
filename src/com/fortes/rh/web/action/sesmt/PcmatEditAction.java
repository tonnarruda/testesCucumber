package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.business.sesmt.PcmatManager;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class PcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private PcmatManager pcmatManager;
	private ObraManager obraManager;
	
	private Pcmat pcmat;
	private Obra obra;
	
	private Collection<Pcmat> pcmats;
	private Collection<Obra> obras;

	private void prepare() throws Exception
	{
		if(pcmat != null && pcmat.getId() != null)
			pcmat = (Pcmat) pcmatManager.findById(pcmat.getId());
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
		pcmatManager.save(pcmat);
		addActionSuccess("PCMAT cadastrado com sucesso.");
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		pcmatManager.update(pcmat);
		addActionSuccess("PCMAT atualizado com sucesso.");
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		obras = obraManager.findAllSelect(null, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}
	
	public String listPcmats() throws Exception
	{
		pcmats = pcmatManager.findByObra(obra.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			pcmatManager.remove(pcmat.getId());
			addActionSuccess("PCMAT excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este PCMAT.");
		}

		return Action.SUCCESS;
	}
	
	public Pcmat getPcmat()
	{
		if(pcmat == null)
			pcmat = new Pcmat();
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat)
	{
		this.pcmat = pcmat;
	}

	public void setPcmatManager(PcmatManager pcmatManager)
	{
		this.pcmatManager = pcmatManager;
	}
	
	public Collection<Pcmat> getPcmats()
	{
		return pcmats;
	}

	public Collection<Obra> getObras() {
		return obras;
	}

	public void setObraManager(ObraManager obraManager) {
		this.obraManager = obraManager;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}
}
