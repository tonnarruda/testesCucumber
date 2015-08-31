/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA004 */
package com.fortes.rh.web.action.geral;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.exception.AreaColaboradorException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class AreaOrganizacionalEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	private AreaOrganizacional areaOrganizacional;
	private boolean podeEditarAreaMae;
	private boolean limitado;

	private Collection<AreaOrganizacional> areas;
	private Collection<Colaborador> responsaveis;
	private Collection<Colaborador> coResponsaveis;
	
	private String[] emailsNotificacoes;

	private String msgAlert;

	public String execute() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		Long areaId = null;
		if(areaOrganizacional != null && areaOrganizacional.getId() != null && areaOrganizacional.getNome() == null){
			areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areaOrganizacional.getId());
			areaId = areaOrganizacional.getAreaMaeId();
		}

		areas = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, areaId);
		responsaveis = colaboradorManager.findAllSelect(getEmpresaSistema().getId(), "nome");
		coResponsaveis = responsaveis;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		podeEditarAreaMae = true;
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String prepareUpdate() throws Exception
	{
		prepare();
		if(areaOrganizacional != null && areaOrganizacional.getEmpresa() != null && areaOrganizacional.getEmpresa().getId() != null && getEmpresaSistema().getId().equals(areaOrganizacional.getEmpresa().getId()))
		{
			podeEditarAreaMae =	!getEmpresaSistema().isAcIntegra();

			if(StringUtils.isNotBlank(areaOrganizacional.getCodigoAC()) && historicoColaboradorManager.verifyExists(new String[]{"areaOrganizacional.id"}, new Object[]{areaOrganizacional.getId()}))
			{
				limitado = true;
				podeEditarAreaMae = false;
			}
			
			areas = areaOrganizacionalManager.getNaoFamilia(areas ,areaOrganizacional.getId());

			return Action.SUCCESS;
		}
		else
		{
			msgAlert = "A Área Organizacional solicitada não existe na empresa " + getEmpresaSistema().getNome() +".";
			return "error.area";
		}
	}

	public String insert() throws Exception
	{
		try {
			areaOrganizacional.setEmailsNotificacoes(StringUtils.deleteWhitespace(StringUtils.join(emailsNotificacoes, ";")));
			areaOrganizacionalManager.insertLotacaoAC(areaOrganizacional, getEmpresaSistema());
			return Action.SUCCESS;
		} catch (Exception e) {
			prepareInsert();
			e.printStackTrace();

			if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getTargetException() instanceof IntegraACException) 
				addActionError(ExceptionUtil.getMensagem(e, "Cadastro não pôde ser realizado no Fortes Pessoal."));
			else if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getTargetException() instanceof AreaColaboradorException)
				addActionWarning(((InvocationTargetException)e).getTargetException().getLocalizedMessage());
			else 
				addActionError(ExceptionUtil.getMensagem(e, "Cadastro não pôde ser realizado."));
				
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		if(areaOrganizacional == null || areaOrganizacional.getEmpresa() == null || areaOrganizacional.getEmpresa().getId() == null || !getEmpresaSistema().getId().equals(areaOrganizacional.getEmpresa().getId())){
			setActionMsg("A área organizacional solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
			return "error.area";
		}

		try {
			areaOrganizacional.setEmailsNotificacoes(StringUtils.deleteWhitespace(StringUtils.join(emailsNotificacoes, ";")));
			areaOrganizacionalManager.editarLotacaoAC(areaOrganizacional, getEmpresaSistema());
			
			return Action.SUCCESS;
			
		} catch (IntegraACException e) {
			prepare();
			e.printStackTrace();
			addActionError("Cadastro não pôde ser realizado no Fortes Pessoal.");
			
			return Action.INPUT;
			
		} catch (Exception e) {
			prepareInsert();
			e.printStackTrace();
			addActionError(ExceptionUtil.getMensagem(e, "Cadastro não pôde ser realizado."));
			
			return Action.INPUT;
		}
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional=areaOrganizacional;
	}

	public Object getModel()
	{
		return getAreaOrganizacional();
	}

	public Collection<AreaOrganizacional> getAreas()
	{
		return areas;
	}

	public void setAreas(Collection<AreaOrganizacional> areas)
	{
		this.areas = areas;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager=areaOrganizacionalManager;
	}

	public Collection<Colaborador> getResponsaveis()
	{
		return responsaveis;
	}

	public void setResponsaveis(Collection<Colaborador> responsaveis)
	{
		this.responsaveis = responsaveis;
	}

	public boolean isPodeEditarAreaMae()
	{
		return podeEditarAreaMae;
	}

	public void setPodeEditarAreaMae(boolean podeEditarAreaMae)
	{
		this.podeEditarAreaMae = podeEditarAreaMae;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public boolean isLimitado()
	{
		return limitado;
	}

	public void setLimitado(boolean limitado)
	{
		this.limitado = limitado;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public String[] getEmailsNotificacoes() {
		return emailsNotificacoes;
	}

	public void setEmailsNotificacoes(String[] emailsNotificacoes) {
		this.emailsNotificacoes = emailsNotificacoes;
	}

	public Collection<Colaborador> getCoResponsaveis() {
		return coResponsaveis;
	}

}