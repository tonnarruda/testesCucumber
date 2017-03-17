package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;

public class PpraEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private AmbienteManager ambienteManager;
	
	private Collection<Estabelecimento> estabelecimentos;
	
	private Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
	private String[] ambienteCheck;
	
	private Estabelecimento estabelecimento;
	
	private Date data;
	private boolean gerarPpra;
	private boolean gerarLtcat;
	private boolean exibirComposicaoSesmt;
	private boolean quebrarPagina;
	
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private Collection<PpraLtcatRelatorio> dataSource = new ArrayList<PpraLtcatRelatorio>();

	public String prepareRelatorio() throws Exception
	{
    	if(Autenticador.isDemo())
    		addActionMessage("Este relatório não pode ser impresso na Versão Demonstração.");
    	
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		
		if (estabelecimento != null)
			ambienteCheckList = ambienteManager.populaCheckBox(estabelecimento.getId());
		
		return SUCCESS;
	}
	
	public String gerarRelatorio() throws Exception
	{
		if(Autenticador.isDemo()){
			prepareRelatorio();
			return INPUT;
		}
		
		try
		{
			parametros = RelatorioUtil.getParametrosRelatorio("PPRA", getEmpresaSistema(), null);
			parametros.put("EXIBIR_LOGO_EMPRESA", getEmpresaSistema().isExibirLogoEmpresaPpraLtcat());
			parametros.put("EXIBIR_COMPOSICAO_SESMT", exibirComposicaoSesmt);
			parametros.put("AGRUPADO_POR_AMBIENTE", getEmpresaSistema().getControlaRiscoPor() == 'A');
			parametros.put("QUEBRAR_PAGINA", isQuebrarPagina());
			
			
			dataSource = ambienteManager.montaRelatorioPpraLtcat(getEmpresaSistema(), estabelecimento.getId(), data, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		}
		catch (ColecaoVaziaException e)
		{
			addActionError(e.getMessage());
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
		
		return SUCCESS;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public boolean isGerarPpra() {
		return gerarPpra;
	}

	public void setGerarPpra(boolean gerarPpra) {
		this.gerarPpra = gerarPpra;
	}

	public boolean isGerarLtcat() {
		return gerarLtcat;
	}

	public void setGerarLtcat(boolean gerarLtcat) {
		this.gerarLtcat = gerarLtcat;
	}

	public String[] getAmbienteCheck() {
		return ambienteCheck;
	}

	public void setAmbienteCheck(String[] ambienteCheck) {
		this.ambienteCheck = ambienteCheck;
	}

	public Collection<CheckBox> getAmbienteCheckList() {
		return ambienteCheckList;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<PpraLtcatRelatorio> getDataSource() {
		return dataSource;
	}

	public boolean isExibirComposicaoSesmt() {
		return exibirComposicaoSesmt;
	}

	public void setExibirComposicaoSesmt(boolean exibirComposicaoSesmt) {
		this.exibirComposicaoSesmt = exibirComposicaoSesmt;
	}

	public boolean isQuebrarPagina() {
		return quebrarPagina;
	}

	public void setQuebrarPagina(boolean quebrarPagina) {
		this.quebrarPagina = quebrarPagina;
	}
}