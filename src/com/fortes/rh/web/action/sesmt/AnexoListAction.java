package com.fortes.rh.web.action.sesmt;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.AnexoManager;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AnexoListAction extends MyActionSupportList
{
	@Autowired private AnexoManager anexoManager;

	private Anexo anexo;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		anexo = anexoManager.findById(anexo.getId());
		anexoManager.remove(new Long[]{anexo.getId()});

		String pasta = null;

		if(anexo.getOrigem() == OrigemAnexo.LTCAT || anexo.getOrigem() == OrigemAnexo.PPRA)
			pasta = "sesmt";

		ArquivoUtil.deletaArquivos(pasta, new String[]{anexo.getUrl()});

		return anexoManager.getStringRetorno(anexo.getOrigem());
	}

	public Anexo getAnexo(){
		if(anexo == null){
			anexo = new Anexo();
		}
		return anexo;
	}

	public void setAnexo(Anexo anexo)
	{
		this.anexo = anexo;
	}
}