package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.exception.RemoveCascadeException;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EpiListAction extends MyActionSupportList
{
	@Autowired private EpiManager epiManager;
	@Autowired private TipoEPIManager tipoEPIManager;

	private Collection<Epi> epis;
	private Epi epi;
	
	private String[] tipoEPICheck;
	private Collection<CheckBox> tipoEPICheckList = new ArrayList<CheckBox>();
	
	private Date venc;
	private Collection<Epi> dataSource;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	
	private String epiNome;
	private char ativo = 'S';

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		Boolean valueCombo = BooleanUtil.getValueCombo(ativo);
		setTotalSize(epiManager.getCount(getEmpresaSistema().getId(), epiNome, valueCombo ));
		epis = epiManager.findEpis(getPage(), getPagingSize(), getEmpresaSistema().getId(), epiNome, valueCombo);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
		
			if(epi == null || epi.getId() == null || !epiManager.verifyExists(new String[]{"id","empresa.id"}, new Object[]{epi.getId(),getEmpresaSistema().getId()}))
				addActionMessage("O EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			else
			{
				epiManager.removeEpi(epi);
				addActionSuccess("EPI excluído com sucesso.");
			}
		}
		
		catch (RemoveCascadeException e)
		{
			addActionWarning(e.getMessage());
		}
		catch (DataIntegrityViolationException e) 
		{
			addActionWarning("Não foi possível remover o EPI. Existem registros vinculados a ele.");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			addActionError("Erro ao remover EPI.");
			e.printStackTrace();
		}
		
		return list();
	}

	public String prepareImprimirVencimentoCa() throws Exception
	{
		tipoEPICheckList = tipoEPIManager.getByEmpresa(getEmpresaSistema().getId());
		return SUCCESS;
	}

	public String imprimirVencimentoCa() throws Exception
	{
		String titulo = "EPIs com CA a vencer até " + DateUtil.formataDate(venc, "dd/MM/yyyy");
		parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), "");
		dataSource = epiManager.findByVencimentoCa(venc, getEmpresaSistema().getId(), tipoEPICheck);

		if (dataSource.isEmpty())
		{
			addActionMessage("Não existem EPIs com CA a vencer até a data informada.");
			prepareImprimirVencimentoCa();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public Collection<Epi> getEpis()
	{
		return epis;
	}

	public Epi getEpi()
	{
		if(epi == null)
			epi = new Epi();
		return epi;
	}

	public void setEpi(Epi epi)
	{
		this.epi=epi;
	}

	public Date getVenc()
	{
		return venc;
	}

	public void setVenc(Date venc)
	{
		this.venc = venc;
	}

	public Collection<Epi> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public String[] getTipoEPICheck() {
		return tipoEPICheck;
	}

	public void setTipoEPICheck(String[] tipoEPICheck) {
		this.tipoEPICheck = tipoEPICheck;
	}

	public Collection<CheckBox> getTipoEPICheckList() {
		return tipoEPICheckList;
	}

	public void setTipoEPICheckList(Collection<CheckBox> tipoEPICheckList) {
		this.tipoEPICheckList = tipoEPICheckList;
	}

	public String getEpiNome() {
		return epiNome;
	}

	public void setEpiNome(String epiNome) {
		this.epiNome = epiNome;
	}

	public char getAtivo() {
		return ativo;
	}

	public void setAtivo(char ativo) {
		this.ativo = ativo;
	}
}