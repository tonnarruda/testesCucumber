package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;

public class PpraEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	private EstabelecimentoManager estabelecimentoManager;
	private AmbienteManager ambienteManager;
	
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
	private Map<Integer, String> locaisAmbiente;
	private Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao();

	public String prepareRelatorio() throws Exception
	{
    	if(Autenticador.isDemo())
    		addActionMessage("Este relatório não pode ser impresso na Versão Demonstração.");

    	locaisAmbiente = LocalAmbiente.mapLocalAmbiente();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		
		if(ModelUtil.hasNotNull("getId", estabelecimento)) 
			ambienteCheckList = ambienteManager.populaCheckBox(getEmpresaSistema().getId(), estabelecimento.getId(), localAmbiente, data);
		else 
			ambienteCheckList = ambienteManager.populaCheckBox(getEmpresaSistema().getId(), null, localAmbiente, data);
		
		ambienteCheckList = CheckListBoxUtil.marcaCheckListBox(ambienteCheckList, ambienteCheck);
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
			dataSource = ambienteManager.montaRelatorioPpraLtcat(getEmpresaSistema(), estabelecimento, localAmbiente, data, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
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

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
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

	public void setAmbienteManager(AmbienteManager ambienteManager) {
		this.ambienteManager = ambienteManager;
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

	public Map<Integer, String> getLocaisAmbiente() {
		return locaisAmbiente;
	}

	public void setLocaisAmbiente(Map<Integer, String> locaisAmbiente) {
		this.locaisAmbiente = locaisAmbiente;
	}

	public Integer getLocalAmbiente() {
		return localAmbiente;
	}

	public void setLocalAmbiente(Integer localAmbiente) {
		this.localAmbiente = localAmbiente;
	}
}