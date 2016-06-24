package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;

public class RiscoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private RiscoManager riscoManager;
	private EpiManager epiManager;

	private Collection<Epi> epis = new ArrayList<Epi>();
	private Collection<CheckBox> episCheckList = new ArrayList<CheckBox>();

	private String[] episCheck;
	private Risco risco;
	private Map<String, String> grupoRiscos;
	private String msgAlert;
	
	private Collection<Risco> riscos;


	private void prepare() throws Exception
	{
		if(risco != null && risco.getId() != null)
			risco = riscoManager.findById(risco.getId());

		grupoRiscos = GrupoRisco.getInstance();
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), true);

		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if(getRisco().getEmpresa() == null || risco.getEmpresa().getId() == null || !risco.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addErro("O risco solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return ERROR;
		}

		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), null);
		episCheckList = CheckListBoxUtil.marcaCheckListBox(episCheckList, riscoManager.findEpisByRisco(risco.getId()), "getId");

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			risco.setEpis(epiManager.populaEpi(episCheck));
			risco.setEmpresa(getEmpresaSistema());
			
			riscoManager.save(risco);
			
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareInsert();
			addErro("Não foi possível gravar o risco.");
			return INPUT;
		}
	}
	
	public String update() throws Exception
	{
		if(risco == null || risco.getId() == null || !riscoManager.verifyExists(new String[]{"id", "empresa.id"}, new Object[]{risco.getId(), getEmpresaSistema().getId()}))
		{
			addErro("O risco solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return ERROR;
		}
		
		try
		{
			risco.setEpis(epiManager.populaEpi(episCheck));
			risco.setEmpresa(getEmpresaSistema());
			riscoManager.update(risco);
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareUpdate();
			addErro("Não foi possível gravar o risco.");
			return INPUT;
		}
	}
	
	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa"};
		Object[] values = new Object[]{getEmpresaSistema()};
		String[] orders = new String[]{"descricao"};

		setTotalSize(riscoManager.getCount(keys, values));
		riscos = riscoManager.find(getPage(), getPagingSize(), keys, values, orders);

		addErro(msgAlert);

		return SUCCESS;
	}

	public String delete() throws Exception
	{
		if(getRisco().getId() == null || !riscoManager.verifyExists(new String[]{"id", "empresa.id"}, new Object[]{risco.getId(), getEmpresaSistema().getId()}))
		{
			addErro("O Risco solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
		}
		else
		{
			try {
				riscoManager.remove(new Long[]{risco.getId()});
				addActionSuccess("Risco excluído com sucesso.");
			} catch (Exception e) {
				e.printStackTrace();
				ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este Risco.");			
			}
		}

		return list();
	}
	
	private void addErro(String anErrorMessage)
	{
		if (StringUtils.isNotBlank(anErrorMessage))
		{
			msgAlert = anErrorMessage;
			addActionError(anErrorMessage);
		}
	}

	public Risco getRisco()
	{
		if(risco == null)
			risco = new Risco();
		return risco;
	}

	public void setRisco(Risco risco)
	{
		this.risco = risco;
	}

	public void setRiscoManager(RiscoManager riscoManager)
	{
		this.riscoManager = riscoManager;
	}

	public Map<String, String> getGrupoRiscos()
	{
		return grupoRiscos;
	}

	public void setGrupoRiscos(Map<String, String> grupoRiscos)
	{
		this.grupoRiscos = grupoRiscos;
	}

	public String[] getEpisCheck()
	{
		return episCheck;
	}

	public void setEpisCheck(String[] episCheck)
	{
		this.episCheck = episCheck;
	}

	public Collection<CheckBox> getEpisCheckList()
	{
		return episCheckList;
	}

	public void setEpisCheckList(Collection<CheckBox> episCheckList)
	{
		this.episCheckList = episCheckList;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public Collection<Epi> getEpis()
	{
		return epis;
	}

	public void setEpis(Collection<Epi> epis)
	{
		this.epis = epis;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}
	
	public Collection<Risco> getRiscos() {
		return riscos;
	}

	public void setMsgAlert(String msgAlert) {
		this.msgAlert = msgAlert;
	}
}