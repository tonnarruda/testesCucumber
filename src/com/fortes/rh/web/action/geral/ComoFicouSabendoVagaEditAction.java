package com.fortes.rh.web.action.geral;


import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ComoFicouSabendoVagaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ComoFicouSabendoVagaManager comoFicouSabendoVagaManager;
	private ComoFicouSabendoVaga comoFicouSabendoVaga;
	private Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas;
	private Collection<ComoFicouSabendoVaga> dataSource;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	
	private Date dataIni;
	private Date dataFim;

	private void prepare() throws Exception
	{
		if(comoFicouSabendoVaga != null && comoFicouSabendoVaga.getId() != null)
			comoFicouSabendoVaga = (ComoFicouSabendoVaga) comoFicouSabendoVagaManager.findById(comoFicouSabendoVaga.getId());

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
		try {
			comoFicouSabendoVagaManager.save(comoFicouSabendoVaga);
			return Action.SUCCESS;
		} catch (Exception e) {
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try 
		{
			comoFicouSabendoVagaManager.update(comoFicouSabendoVaga);
			return Action.SUCCESS;
			
		} catch (Exception e) {
			prepareUpdate();
			return Action.INPUT;
		}
	}

	public String list() throws Exception
	{
		setVideoAjuda(674L);
		comoFicouSabendoVagas = comoFicouSabendoVagaManager.findAllSemOutros();
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			comoFicouSabendoVagaManager.remove(comoFicouSabendoVaga.getId());
			addActionMessage("Item excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este item.");
		}

		return list();
	}

	public String prepareRelatorioComoFicouSabendoVaga() throws Exception
	{
		setVideoAjuda(791L);
		
		return Action.SUCCESS;
	}
	
	public String imprimirRelatorioComoFicouSabendoVaga()
    {
		try{
			dataSource = comoFicouSabendoVagaManager.findCandidatosComoFicouSabendoVaga(dataIni, dataFim, getEmpresaSistema().getId());
	    	parametros = RelatorioUtil.getParametrosRelatorio("Como Ficou Sabendo da Vaga", getEmpresaSistema(), "Período: " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim));
		} catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao gerar relatorio.");
		}
    	return Action.SUCCESS;
    }
	
	public ComoFicouSabendoVaga getComoFicouSabendoVaga()
	{
		if(comoFicouSabendoVaga == null)
			comoFicouSabendoVaga = new ComoFicouSabendoVaga();
		return comoFicouSabendoVaga;
	}

	public void setComoFicouSabendoVaga(ComoFicouSabendoVaga comoFicouSabendoVaga)
	{
		this.comoFicouSabendoVaga = comoFicouSabendoVaga;
	}
	
	public Collection<ComoFicouSabendoVaga> getComoFicouSabendoVagas()
	{
		return comoFicouSabendoVagas;
	}
	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<ComoFicouSabendoVaga> getDataSource() {
		return dataSource;
	}
}