
package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.sesmt.TamanhoEPIManager;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class TamanhoEPIListAction extends MyActionSupportList
{
	@Autowired private TamanhoEPIManager tamanhoEPIManager;

	private Collection<TamanhoEPI> tamanhoEPIs;

	private TamanhoEPI tamanhoEPI;

	public String list() throws Exception {
		tamanhoEPIs = tamanhoEPIManager.findAll(new String[]{"descricao"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception	{
		try {
			tamanhoEPIManager.remove(tamanhoEPI);
			addActionMessage("Tamanho excluído com sucesso.");

		} catch (DataIntegrityViolationException e) {
			addActionWarning("Não é possível excluir o Tamanho, pois possui dependências.");
			e.printStackTrace();

		} catch (ConstraintViolationException e) {
			addActionError("Não é possível excluir o Tamanho, pois possui dependências.");
			e.printStackTrace();
		
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}

		return list();
	}

	public Collection<TamanhoEPI> getTamanhoEPIs() {
		return tamanhoEPIs;
	}

	public void setTamanhoEPIs(Collection<TamanhoEPI> tamanhoEPIs) {
		this.tamanhoEPIs = tamanhoEPIs;
	}

	public TamanhoEPI getTamanhoEPI() {
		return tamanhoEPI;
	}

	public void setTamanhoEPI(TamanhoEPI tamanhoEPI) {
		this.tamanhoEPI = tamanhoEPI;
	}
}