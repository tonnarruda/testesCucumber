package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.PppRelatorioException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

public class PppEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorManager colaboradorManager;
	private FuncaoManager funcaoManager;
	private HistoricoColaboradorManager historicoColaboradorManager; 

	private Colaborador colaborador;
	private String nit;
	private String responsavel;
	private Date data;
	private String observacoes;
	private String[] respostas;
	
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private Collection<PppRelatorio> dataSource;
	
	private Collection<Colaborador> colaboradors;
	private String nomeBusca;
	private String cpfBusca;
	private boolean imprimirRecibo = false;

	@SuppressWarnings("unchecked")
	public String list() throws Exception
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("cpfBusca", StringUtil.removeMascara(cpfBusca));
		parametros.put("empresaId", getEmpresaSistema().getId());
		parametros.put("areaId", null);

		setTotalSize(colaboradorManager.getCount(parametros));
		colaboradors = colaboradorManager.findList(getPage(), getPagingSize(), parametros);

		return Action.SUCCESS;
	}

	public String prepareRelatorio() throws Exception
	{
		if(Autenticador.isDemo())
    		addActionMessage("Este relatório não pode ser impresso na Versão Demonstração.");
		data = new Date();
		
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaborador.getId());
		
		if (StringUtils.isBlank(responsavel))
			responsavel =  colaborador.getEmpresa().getRepresentanteLegal();
		if (StringUtils.isBlank(nit))
			nit = colaborador.getEmpresa().getNitRepresentanteLegal();
		
		boolean historicoAposAdmissao = historicoColaboradorManager.verifyDataHistoricoAdmissao(colaborador.getId());
		
		if (historicoAposAdmissao)
			addActionMessage("Atenção: Data da primeira situação do colaborador após a data de admissão.");
		
		return Action.SUCCESS;
	}
	
	public String gerarRelatorio() throws Exception
	{
		if(Autenticador.isDemo())
		{
			prepareRelatorio();
			return Action.INPUT;
		}
		
		try
		{
			String imgDir = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separatorChar;
			
			parametros = RelatorioUtil.getParametrosRelatorio("PPP", getEmpresaSistema(), "");
			parametros.put("IDCOLABORADOR", colaborador.getId());
			parametros.put("IMG_DIR", imgDir);
			parametros.put("IMPRIMIR_RECIBO", imprimirRecibo);
			
			dataSource = funcaoManager.populaRelatorioPpp(colaborador, data, nit, responsavel, observacoes, respostas);
			
			return SUCCESS;
		}
		catch (PppRelatorioException pppRelatorioException)
		{
			addActionMessage(pppRelatorioException);
			prepareRelatorio();
			return INPUT;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			addActionError("Erro ao gerar relatório.");
			prepareRelatorio();
			return INPUT;
		}
	}

	private void addActionMessage(PppRelatorioException pppRelatorioException) 
	{
		addActionMessage(pppRelatorioException.getMensagemDeInformacao());
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public String getNit()
	{
		return nit;
	}
	public void setNit(String nit)
	{
		this.nit = nit;
	}
	public String getResponsavel()
	{
		return responsavel;
	}
	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	public Collection<PppRelatorio> getDataSource()
	{
		return dataSource;
	}
	
	public String getObservacoes()
	{
		return observacoes;
	}
	public void setObservacoes(String observacoes)
	{
		this.observacoes = observacoes;
	}
	public void setFuncaoManager(FuncaoManager funcaoManager) {
		this.funcaoManager = funcaoManager;
	}
	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public String getCpfBusca() {
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca) {
		this.cpfBusca = cpfBusca;
	}

	public void setRespostas(String[] respostas) {
		this.respostas = respostas;
	}

	public String[] getRespostas() {
		return respostas;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isImprimirRecibo() {
		return imprimirRecibo;
	}

	public void setImprimirRecibo(boolean imprimirRecibo) {
		this.imprimirRecibo = imprimirRecibo;
	}
}