package com.fortes.rh.web.action.geral;


import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ComoFicouSabendoVagaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ComoFicouSabendoVagaManager comoFicouSabendoVagaManager;
	private ComoFicouSabendoVaga comoFicouSabendoVaga;
	private Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas;
	private Collection<Candidato> dataSource;
	
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
		return Action.SUCCESS;
	}
	
	public String imprimirRelatorioComoFicouSabendoVaga()
    {
//    	dataSource = comoFicouSabendoVagaManager.findCandidatosComoFicouSabendoVaga(dataIni, dataFim);

//   	   	String titulo = "Avaliação";
//   	   	String filtro = avaliacao.getTitulo();
//   	   	
//    	parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
//    	parametros.put("FORMA_ECONOMICA", imprimirFormaEconomica );
    	
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

	public void setComoFicouSabendoVagaManager(ComoFicouSabendoVagaManager comoFicouSabendoVagaManager)
	{
		this.comoFicouSabendoVagaManager = comoFicouSabendoVagaManager;
	}
	
	public Collection<ComoFicouSabendoVaga> getComoFicouSabendoVagas()
	{
		return comoFicouSabendoVagas;
	}

	public Collection<Candidato> getDataSource() {
		return dataSource;
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
}
