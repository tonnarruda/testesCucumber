
package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class TipoEPIListAction extends MyActionSupportList
{
	@Autowired private TipoEPIManager tipoEPIManager;

	private Collection<TipoEPI> tipoEPIs;

	private TipoEPI tipoEPI;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception {

		String[] keys = new String[]{"empresa"};
		Object[] values = new Object[]{getEmpresaSistema()};
		String[] orders = new String[]{"nome"};

		setTotalSize(tipoEPIManager.getCount(keys, values));
		tipoEPIs = tipoEPIManager.find(getPage(), getPagingSize(), keys, values, orders);

		if(!msgAlert.equals(""))
			addActionError(msgAlert);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try{
			if(tipoEPI == null || tipoEPI.getId() == null || !tipoEPIManager.verifyExists(new String[]{"id","empresa.id"}, new Object[]{tipoEPI.getId(),getEmpresaSistema().getId()}))
				addActionError("O Tipo de EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			else
			{
				tipoEPIManager.remove(new Long[]{tipoEPI.getId()});
				addActionMessage("Tipo de EPI excluído com sucesso.");
			}
		}
		catch (Exception e){
			addActionWarning("O Tipo de EPI não pode ser excluído pois possue dependência com 'EPI' ");
		}

		return list();
	}

	public Collection<TipoEPI> getTipoEPIs() {
		return tipoEPIs;
	}

	public TipoEPI getTipoEPI(){
		if(tipoEPI == null){
			tipoEPI = new TipoEPI();
		}
		return tipoEPI;
	}

	public void setTipoEPI(TipoEPI tipoEPI){
		this.tipoEPI=tipoEPI;
	}
}