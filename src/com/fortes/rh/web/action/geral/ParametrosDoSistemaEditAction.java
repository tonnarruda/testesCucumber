package com.fortes.rh.web.action.geral;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GastoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ParametrosDoSistemaEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private PerfilManager perfilManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager ;
	private CidadeManager cidadeManager  ;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private GastoManager gastoManager;
	private OcorrenciaManager ocorrenciaManager;

	private Collection<Estabelecimento> estabelecimentos;
	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<Colaborador> colaboradors;
	private Collection<Cidade> cidades;
	private Collection<FaixaSalarial> faixaSalarials;
	private Collection<Indice> indices ;
	private Collection<Gasto> gastos;
	private Collection<Ocorrencia> ocorrencias ;
	
	private Empresa empresa;
	private ParametrosDoSistema parametrosDoSistema;

	private Collection<Perfil> perfils;

	private String[] camposCandidatoObrigatorios;
	private String[] camposCandidatoVisivels;
	
	private boolean habilitaCampoExtra;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();


	
	public String prepareUpdate() throws Exception
	{
		parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		perfils = perfilManager.findAll();
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaAux = parametrosDoSistemaManager.findById(parametrosDoSistema.getId());

		if(StringUtils.isBlank(parametrosDoSistema.getEmailPass()))
			parametrosDoSistema.setEmailPass(parametrosDoSistemaAux.getEmailPass());

		if (parametrosDoSistemaAux.getModulos() != null)
			parametrosDoSistema.setModulos(parametrosDoSistemaAux.getModulos());

		parametrosDoSistemaManager.update(parametrosDoSistema);
		
		perfils = perfilManager.findAll();
		addActionMessage("Configurações do Sistema atualizadas com sucesso!");

		return Action.SUCCESS;
	}
	
	public String listCamposCandidato() throws Exception
	{
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraCandidato();
		if(habilitaCampoExtra)
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});		
				
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		return Action.SUCCESS;
	}
	
	public String prepareDeleteSemCodigoAC() throws Exception
	{
		empresa = getEmpresaSistema();
		
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		if(usuarioLogado.getId() != 1L)
			return Action.INPUT;
		
		estabelecimentos = estabelecimentoManager.findSemCodigoAC(empresa.getId());
		areaOrganizacionals = areaOrganizacionalManager.findSemCodigoAC(empresa.getId());
		colaboradors = colaboradorManager.findSemCodigoAC(empresa.getId());
		cidades = cidadeManager.findSemCodigoAC();
		faixaSalarials = faixaSalarialManager.findSemCodigoAC(empresa.getId());
		indices = indiceManager.findSemCodigoAC();
		gastos = gastoManager.findSemCodigoAC(empresa.getId());
		ocorrencias = ocorrenciaManager.findSemCodigoAC(empresa.getId());
		
		return Action.SUCCESS;
		
	}
	
	public String updateCamposCandidato() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaTmp = parametrosDoSistemaManager.findById(1L);
		
		parametrosDoSistemaTmp.setCamposCandidatoObrigatorio(StringUtil.converteArrayToString(camposCandidatoObrigatorios));
		parametrosDoSistemaTmp.setCamposCandidatoVisivel(StringUtil.converteArrayToString(camposCandidatoVisivels));
		parametrosDoSistemaTmp.setCamposCandidatoTabs(parametrosDoSistema.getCamposCandidatoTabs());
		
		parametrosDoSistemaManager.update(parametrosDoSistemaTmp);
		
		return Action.SUCCESS;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		if(parametrosDoSistema == null)
			parametrosDoSistema = new ParametrosDoSistema();
		return parametrosDoSistema;
	}

	public void setParametrosDoSistema(ParametrosDoSistema parametrosDoSistema)
	{
		this.parametrosDoSistema = parametrosDoSistema;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setPerfilManager(PerfilManager perfilManager)
	{
		this.perfilManager = perfilManager;
	}

	public Collection<Perfil> getPerfils()
	{
		return perfils;
	}

	public void setPerfils(Collection<Perfil> perfils)
	{
		this.perfils = perfils;
	}

	public String[] getCamposCandidatoObrigatorios() {
		return camposCandidatoObrigatorios;
	}

	public void setCamposCandidatoObrigatorios(String[] camposCandidatoObrigatorios) {
		this.camposCandidatoObrigatorios = camposCandidatoObrigatorios;
	}

	public String[] getCamposCandidatoVisivels() {
		return camposCandidatoVisivels;
	}

	public void setCamposCandidatoVisivels(String[] camposCandidatoVisivels) {
		this.camposCandidatoVisivels = camposCandidatoVisivels;
	}

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras() {
		return configuracaoCampoExtras;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals() {
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public Collection<Cidade> getCidades() {
		return cidades;
	}

	public void setCidadeManager(CidadeManager cidadeManager) {
		this.cidadeManager = cidadeManager;
	}

	public Collection<FaixaSalarial> getFaixaSalarials() {
		return faixaSalarials;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setIndiceManager(IndiceManager indiceManager) {
		this.indiceManager = indiceManager;
	}

	public Collection<Indice> getIndices() {
		return indices;
	}

	public void setGastoManager(GastoManager gastoManager) {
		this.gastoManager = gastoManager;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager) {
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public Collection<Gasto> getGastos() {
		return gastos;
	}

	public Collection<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

}