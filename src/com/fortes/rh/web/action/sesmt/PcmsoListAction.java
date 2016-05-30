package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.PcmsoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PCMSO;
import com.fortes.rh.security.licenca.AutenticadorJarvis;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class PcmsoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private PcmsoManager pcmsoManager;
	private EstabelecimentoManager estabelecimentoManager;
	
	private Date dataIni;
	private Date dataFim;
	private Estabelecimento estabelecimento;
	
	private boolean exibirAgenda;
	private boolean exibirDistColaboradorSetor;
	private boolean exibirRiscos;
	private boolean exibirEpis;
	private boolean exibirExames;
	private boolean exibirAcidentes;
	private boolean exibirComposicaoSesmt;
	
	private Collection<PCMSO> dataSource;

	private Map<String, Object> parametros = new HashMap<String, Object>();
	
	private Collection<Estabelecimento> estabelecimentos;

	public String prepareRelatorio() throws Exception
	{
    	if(AutenticadorJarvis.isDemo())
    		addActionMessage("Este relatório não pode ser impresso na Versão Demonstração.");
    	
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}
	
	public String gerarRelatorio() throws Exception
	{
		if(AutenticadorJarvis.isDemo())
		{
			prepareRelatorio();
			return Action.INPUT;
		}
		
		try
		{
			dataSource = pcmsoManager.montaRelatorio(dataIni, dataFim, estabelecimento, getEmpresaSistema().getId(), exibirAgenda, exibirDistColaboradorSetor, exibirRiscos, exibirEpis, exibirExames, exibirAcidentes, exibirComposicaoSesmt);
			parametros = RelatorioUtil.getParametrosRelatorio("", getEmpresaSistema(), "");			
			
			return Action.SUCCESS;
		
		} catch (ColecaoVaziaException e)
		{
			prepareRelatorio();
			e.printStackTrace();
			addActionMessage(e.getMessage());
		} catch (Exception e)
		{
			prepareRelatorio();
			addActionError("Erro ao gerar PCMSO");
		}
		
		return Action.INPUT;
	}
	
	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setPcmsoManager(PcmsoManager pcmsoManager)
	{
		this.pcmsoManager = pcmsoManager;
	}
	
	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}
	public Collection<PCMSO> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public boolean isExibirAgenda() {
		return exibirAgenda;
	}

	public void setExibirAgenda(boolean exibirAgenda) {
		this.exibirAgenda = exibirAgenda;
	}

	public boolean isExibirDistColaboradorSetor() {
		return exibirDistColaboradorSetor;
	}

	public void setExibirDistColaboradorSetor(boolean exibirDistColaboradorSetor) {
		this.exibirDistColaboradorSetor = exibirDistColaboradorSetor;
	}

	public boolean isExibirRiscos() {
		return exibirRiscos;
	}

	public void setExibirRiscos(boolean exibirRiscos) {
		this.exibirRiscos = exibirRiscos;
	}

	public boolean isExibirEpis() {
		return exibirEpis;
	}

	public void setExibirEpis(boolean exibirEpis) {
		this.exibirEpis = exibirEpis;
	}

	public boolean isExibirExames() {
		return exibirExames;
	}

	public void setExibirExames(boolean exibirExames) {
		this.exibirExames = exibirExames;
	}

	public boolean isExibirAcidentes() {
		return exibirAcidentes;
	}

	public void setExibirAcidentes(boolean exibirAcidentes) {
		this.exibirAcidentes = exibirAcidentes;
	}

	public boolean isExibirComposicaoSesmt() {
		return exibirComposicaoSesmt;
	}

	public void setExibirComposicaoSesmt(boolean exibirComposicaoSesmt) {
		this.exibirComposicaoSesmt = exibirComposicaoSesmt;
	}
}