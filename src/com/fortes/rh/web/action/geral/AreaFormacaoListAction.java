package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AreaFormacaoListAction extends MyActionSupportList
{
	@Autowired private AreaFormacaoManager areaFormacaoManager;

	private Collection<AreaFormacao> areaFormacaos;

	private AreaFormacao areaFormacao;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setTotalSize(areaFormacaoManager.getCount(areaFormacao));
		areaFormacaos = areaFormacaoManager.findByFiltro(getPage(), getPagingSize(), areaFormacao);
		
		return Action.SUCCESS;
	}

	public void setAreaFormacaos(Collection<AreaFormacao> areaFormacaos) {
		this.areaFormacaos = areaFormacaos;
	}

	public String delete() throws Exception
	{
		areaFormacaoManager.remove(areaFormacao.getId());
		addActionMessage("Área de Formação excluída com sucesso.");

		return Action.SUCCESS;
	}

	public Collection<AreaFormacao> getAreaFormacaos() {
		return areaFormacaos;
	}

	public AreaFormacao getAreaFormacao(){
		if(areaFormacao == null){
			areaFormacao = new AreaFormacao();
		}
		return areaFormacao;
	}

	public void setAreaFormacao(AreaFormacao areaFormacao){
		this.areaFormacao=areaFormacao;
	}
}