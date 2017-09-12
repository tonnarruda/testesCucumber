package com.fortes.rh.web.action.geral;


import static com.fortes.rh.util.CheckListBoxUtil.marcaCheckListBox;
import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.ModulosSistema;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ParametrosDoSistemaEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	private static final long PERFIL_AUTORIZAR_PARTICIPACAO_COLAB_EM_SOLICITACAO_DE_PESSOAL = 684L;
	
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private PerfilManager perfilManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager ;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private OcorrenciaManager ocorrenciaManager;
	private EmpresaManager empresaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;

	private Collection<Estabelecimento> estabelecimentos;
	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<Colaborador> colaboradors;
	private Collection<FaixaSalarial> faixaSalarials;
	private Collection<Indice> indices ;
	private Collection<Ocorrencia> ocorrencias ;
	private Collection<Perfil> perfils;
	private Collection<Empresa> empresas;
	private ConfiguracaoCampoExtraVisivelObrigadotorio campoExtraVisivelObrigadotorio;
	
	private Empresa empresa;
	private ParametrosDoSistema parametrosDoSistema;

	private String entidade;
	private String[] camposObrigatorios;
	private String[] camposVisivels; 
	private String camposTabs; 
	private Collection<String> horariosBackup;
	private Collection<CheckBox> horariosBackupList = new ArrayList<CheckBox>();
	
	private boolean habilitaCampoExtra;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	
	private Collection<CheckBox> modulosSistema = new ArrayList<CheckBox>();
	private String[] modulosSistemaCheck;

	private Long[] estabelecimentoIds;
	private Long[] areaIds;
	private Long[] colaboradorIds;
	private Long[] faixaSalarialIds;
	private Long[] indiceIds;
	private Long[] ocorrenciaIds;
	
	private String logErro;
	
	public String prepareUpdate() throws Exception
	{
		setVideoAjuda(1123L);
		
		parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		perfils = perfilManager.findAll();
		populoHorariosBackupList();
		populaModulosSitema();
		
		return Action.SUCCESS;
	}

	private void populaModulosSitema()
	{
		LinkedHashMap<String, String> modulosDoSistema = new ModulosSistema();
		modulosSistema = CheckListBoxUtil.populaCheckListBox(modulosDoSistema);
		int chave = parametrosDoSistema.getModulosPermitidosSomatorio();
		
		Collection<String> modulosMarcados = new ArrayList<String>();
		for (String modulo : modulosDoSistema.keySet())
		{
			int moduloValor = (new Integer(modulo)).intValue();
			if((chave & moduloValor) == moduloValor)
				modulosMarcados.add(modulo);
		}
		
		modulosSistemaCheck = new CollectionUtil<String>().convertCollectionToArrayString(modulosMarcados);
		modulosSistema = CheckListBoxUtil.marcaCheckListBox(modulosSistema, modulosSistemaCheck);

		CheckBox moduloInfo = new CheckBox();
		moduloInfo.setId("99");
		moduloInfo.setNome("Info. Funcionais");
		moduloInfo.setSelecionado(true);
		moduloInfo.setDesabilitado(true);
		modulosSistema.add(moduloInfo);
		
		CheckBox moduloUtilitario = new CheckBox();
		moduloUtilitario.setId("100");
		moduloUtilitario.setNome("Utilitários");
		moduloUtilitario.setSelecionado(true);
		moduloUtilitario.setDesabilitado(true);
		modulosSistema.add(moduloUtilitario);
	}
	
	private void populoHorariosBackupList() 
	{
		HashMap<String, String> horasBackupMap = new LinkedHashMap<String, String>();
		for(int i=0; i < 24; i++)
			horasBackupMap.put(i + "",i + ":00");
		horariosBackupList = populaCheckListBox(horasBackupMap);
		if(parametrosDoSistema.getHorariosBackup() != null)
			horariosBackupList = marcaCheckListBox(horariosBackupList, parametrosDoSistema.getHorariosBackup().split(","));
	}

	public String update() throws Exception
	{
		String retorno = Action.SUCCESS;
		ParametrosDoSistema parametrosDoSistemaAux = parametrosDoSistemaManager.findById(parametrosDoSistema.getId());
		
		if(parametrosDoSistemaAux.isAutorizacaoGestorNaSolicitacaoPessoal() != parametrosDoSistema.isAutorizacaoGestorNaSolicitacaoPessoal())
			retorno = "successAlterandoMenu"; 
		
		if(StringUtils.isBlank(parametrosDoSistema.getEmailPass()))
			parametrosDoSistema.setEmailPass(parametrosDoSistemaAux.getEmailPass());

		if(getUsuarioLogado().getId() != 1){
			parametrosDoSistema.setModulosPermitidosSomatorio(parametrosDoSistemaAux.getModulosPermitidosSomatorio());
			if(parametrosDoSistema.getProximaVersao() == null)
				parametrosDoSistema.setProximaVersao(parametrosDoSistemaAux.getProximaVersao());
		}else{
			parametrosDoSistema.setModulosPermitidosSomatorio(calculaModulosSitema());
			if(parametrosDoSistema.getProximaVersao() !=  null && DateUtil.diferencaEntreDatas(new Date(), parametrosDoSistema.getProximaVersao(), true) >= 0) 
				Autenticador.setDemo(false);
		}

		parametrosDoSistema.setHorariosBackup(StringUtil.converteCollectionToString(horariosBackup));
		
		ServletActionContext.getRequest().getSession().setMaxInactiveInterval(parametrosDoSistema.getSessionTimeout());

		if(parametrosDoSistemaAux.isAutorizacaoGestorNaSolicitacaoPessoal() && !parametrosDoSistema.isAutorizacaoGestorNaSolicitacaoPessoal()){
			perfilManager.removePerfilPapelByPapelId(PERFIL_AUTORIZAR_PARTICIPACAO_COLAB_EM_SOLICITACAO_DE_PESSOAL);
			gerenciadorComunicacaoManager.removeByOperacao(new Integer[]{Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId(), Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId()});
		}
		
		parametrosDoSistemaManager.update(parametrosDoSistema);
		
		perfils = perfilManager.findAll();
		populoHorariosBackupList();
		populaModulosSitema();
		addActionSuccess("Configurações do sistema atualizadas com sucesso.");
			
		return retorno;
	}
	
	private Integer calculaModulosSitema()
	{
		Integer somatorioModulos = 0;
		
		if(modulosSistemaCheck != null)
			for (String moduloMarcado : modulosSistemaCheck) 
				somatorioModulos += (new Integer(moduloMarcado)).intValue(); 
		
		return somatorioModulos;
	}
	
	public String listCampos() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public String configCampos() throws Exception
	{
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		return Action.SUCCESS + "_" + entidade;
	}

	public String listCamposExtras() throws Exception
	{
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		return Action.SUCCESS;
	}
	
	public String configCamposExtras() throws Exception
	{
		boolean compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresa.getId(), getUsuarioLogado().getId());
		
		String tipoCampoExtra = "ativoColaborador";
		if((entidade == null || !entidade.equals(TipoConfiguracaoCampoExtra.COLABORADOR.getTipo())))
			tipoCampoExtra = "ativoCandidato";
				
		configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{tipoCampoExtra, "empresa.id"}, new Object[]{true, empresa.getId()}, new String[]{"ordem"});
		campoExtraVisivelObrigadotorio = configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), entidade);
		if(campoExtraVisivelObrigadotorio == null){
			campoExtraVisivelObrigadotorio = new ConfiguracaoCampoExtraVisivelObrigadotorio();
			campoExtraVisivelObrigadotorio.setEmpresa(empresa);
			campoExtraVisivelObrigadotorio.setTipoConfiguracaoCampoExtra(entidade);
		}
		return Action.SUCCESS + "_" + entidade;
	}
	
	public String updateConfigCamposExtras() throws Exception
	{
		entidade = campoExtraVisivelObrigadotorio.getTipoConfiguracaoCampoExtra();
		empresa = campoExtraVisivelObrigadotorio.getEmpresa();
		if(camposVisivels!= null && camposVisivels.length > 0){
			campoExtraVisivelObrigadotorio.setCamposExtrasVisiveis(StringUtil.converteArrayToString(camposVisivels));
			campoExtraVisivelObrigadotorio.setCamposExtrasObrigatorios(StringUtil.converteArrayToString(camposObrigatorios));
			configuracaoCampoExtraVisivelObrigadotorioManager.saveOrUpdate(campoExtraVisivelObrigadotorio);
		}
		else{
			configuracaoCampoExtraVisivelObrigadotorioManager.removeByEmpresaAndTipoConfig(campoExtraVisivelObrigadotorio.getEmpresa().getId(), new String[]{campoExtraVisivelObrigadotorio.getTipoConfiguracaoCampoExtra()});
		}
		return Action.SUCCESS;
	}
	
	public String prepareDeleteSemCodigoAC() throws Exception
	{
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		if(usuarioLogado.getId() != 1L)
			return Action.INPUT;
		
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		else
			empresa = empresaManager.findByIdProjection(empresa.getId());
		
		empresas = empresaManager.findComCodigoAC();

		estabelecimentos = estabelecimentoManager.findSemCodigoAC(empresa.getId());
		areaOrganizacionals = areaOrganizacionalManager.findSemCodigoAC(empresa.getId());
		colaboradors = colaboradorManager.findSemCodigoAC(empresa.getId());
		faixaSalarials = faixaSalarialManager.findSemCodigoAC(empresa.getId());
		indices = indiceManager.findSemCodigoAC(empresa);
		ocorrencias = ocorrenciaManager.findSemCodigoAC(empresa.getId(), true);

		Collection<String> msgs = empresaManager.verificaIntegracaoAC(empresa);
		if (msgs.size() > 1)
			setActionMessages(msgs);
		
		if(!estabelecimentos.isEmpty() || !areaOrganizacionals.isEmpty() || !colaboradors.isEmpty() || !faixaSalarials.isEmpty() || !indices.isEmpty() || !ocorrencias.isEmpty())
			addActionMessage("- Existem entidades sem código AC");
		
		return Action.SUCCESS;
	}
	
	public String deleteSemCodigoAC() throws Exception
	{
		try {
			colaboradorOcorrenciaManager.deleteOcorrencias(ocorrenciaIds);
			colaboradorManager.deleteColaborador(colaboradorIds);
			estabelecimentoManager.deleteEstabelecimento(estabelecimentoIds);
			areaOrganizacionalManager.deleteAreaOrganizacional(areaIds);
			faixaSalarialManager.deleteFaixaSalarial(faixaSalarialIds);
			indiceManager.deleteIndice(indiceIds);
			
		} catch (DataIntegrityViolationException e) {
			SQLException ex = ((BatchUpdateException)e.getCause()).getNextException();
			logErro = ex.getMessage();
			return Action.INPUT;
		} catch (ConstraintViolationException e) {
			PSQLException ex = ((PSQLException)e.getCause());
			logErro = ex.getMessage();
			return Action.INPUT;
		} catch (Exception e) {
			logErro = e.getMessage() + '\n' + StringUtils.join(e.getStackTrace(), '\n');
			return Action.INPUT;
		}
		finally{
			prepareDeleteSemCodigoAC();			
		}
		
		addActionMessage("Entidades excluídas com sucesso.");
		return Action.SUCCESS;
	}
	
	public String updateCamposCandidato() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaTmp = parametrosDoSistemaManager.findById(1L);
		
		parametrosDoSistemaTmp.setCamposCandidatoObrigatorio(StringUtil.converteArrayToString(camposObrigatorios));
		parametrosDoSistemaTmp.setCamposCandidatoVisivel(StringUtil.converteArrayToString(camposVisivels));
		parametrosDoSistemaTmp.setCamposCandidatoTabs(camposTabs);
		
		parametrosDoSistemaManager.update(parametrosDoSistemaTmp);
		setActionMsg("Campos do candidato gravados com sucesso");
		
		return Action.SUCCESS;
	}

	public String updateCamposColaborador() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaTmp = parametrosDoSistemaManager.findById(1L);
		
		parametrosDoSistemaTmp.setCamposColaboradorObrigatorio(StringUtil.converteArrayToString(camposObrigatorios));
		parametrosDoSistemaTmp.setCamposColaboradorVisivel(StringUtil.converteArrayToString(camposVisivels));
		parametrosDoSistemaTmp.setCamposColaboradorTabs(camposTabs);
		
		parametrosDoSistemaManager.update(parametrosDoSistemaTmp);
		setActionMsg("Campos do colaborador gravados com sucesso");
		
		return Action.SUCCESS;
	}
	
	public String updateCamposCandidatoExterno() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaTmp = parametrosDoSistemaManager.findById(1L);
		
		parametrosDoSistemaTmp.setCamposCandidatoExternoObrigatorio(StringUtil.converteArrayToString(camposObrigatorios));
		parametrosDoSistemaTmp.setCamposCandidatoExternoVisivel(StringUtil.converteArrayToString(camposVisivels));
		parametrosDoSistemaTmp.setCamposCandidatoExternoTabs(camposTabs);
		
		parametrosDoSistemaManager.update(parametrosDoSistemaTmp);
		setActionMsg("Campos do candidato pelo módulo externo gravados com sucesso");
		
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

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public String[] getCamposObrigatorios() {
		return camposObrigatorios;
	}

	public void setCamposObrigatorios(String[] camposObrigatorios) {
		this.camposObrigatorios = camposObrigatorios;
	}

	public String[] getCamposVisivels() {
		return camposVisivels;
	}

	public void setCamposVisivels(String[] camposVisivels) {
		this.camposVisivels = camposVisivels;
	}

	public String getCamposTabs() {
		return camposTabs;
	}

	public void setCamposTabs(String camposTabs) {
		this.camposTabs = camposTabs;
	}

	public Collection<CheckBox> getHorariosBackupList() {
		return horariosBackupList;
	}

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
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

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager) {
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public Collection<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

	public void setEmpresas(Collection<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setAreaOrganizacionals(
			Collection<AreaOrganizacional> areaOrganizacionals) {
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public void setEstabelecimentoIds(Long[] estabelecimentoIds) {
		this.estabelecimentoIds = estabelecimentoIds;
	}

	public void setAreaIds(Long[] areaIds) {
		this.areaIds = areaIds;
	}

	public void setColaboradorIds(Long[] colaboradorIds) {
		this.colaboradorIds = colaboradorIds;
	}

	public void setFaixaSalarialIds(Long[] faixaSalarialIds) {
		this.faixaSalarialIds = faixaSalarialIds;
	}

	public void setIndiceIds(Long[] indiceIds) {
		this.indiceIds = indiceIds;
	}

	public void setOcorrenciaIds(Long[] ocorrenciaIds) {
		this.ocorrenciaIds = ocorrenciaIds;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager) {
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public String getLogErro() {
		return logErro;
	}

	public void setLogErro(String logErro) {
		this.logErro = logErro;
	}

	public void setHorariosBackup(Collection<String> horariosBackup) {
		this.horariosBackup = horariosBackup;
	}

	public Collection<CheckBox> getModulosSistema() {
		return modulosSistema;
	}

	public void setModulosSistemaCheck(String[] modulosSistemaCheck) {
		this.modulosSistemaCheck = modulosSistemaCheck;
	}

	public String[] getModulosSistemaCheck() {
		return modulosSistemaCheck;
	}

	public void setGerenciadorComunicacaoManager( GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public ConfiguracaoCampoExtraVisivelObrigadotorio getCampoExtraVisivelObrigadotorio() {
		return campoExtraVisivelObrigadotorio;
	}

	public void setCampoExtraVisivelObrigadotorio( ConfiguracaoCampoExtraVisivelObrigadotorio campoExtraVisivelObrigadotorio) {
		this.campoExtraVisivelObrigadotorio = campoExtraVisivelObrigadotorio;
	}

	public void setConfiguracaoCampoExtraVisivelObrigadotorioManager(
			ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager) {
		this.configuracaoCampoExtraVisivelObrigadotorioManager = configuracaoCampoExtraVisivelObrigadotorioManager;
	}
}