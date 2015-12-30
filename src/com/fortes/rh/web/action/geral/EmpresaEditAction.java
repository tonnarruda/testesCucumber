/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.web.action.geral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.FormulaTurnover;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class EmpresaEditAction extends MyActionSupportEdit implements ModelDriven
{
	private EmpresaManager empresaManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private GrupoACManager grupoACManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	private Collection<Estado> ufs = null;
	private Collection<Cidade> cidades = new ArrayList<Cidade>();
	private Empresa empresa;
	
	private Empresa empresaOrigem;
	private Empresa empresaDestino;
	private String[] cadastrosCheck;
	private Collection<CheckBox> cadastrosCheckList;
	private String ocorrenciaCheck;
	private Collection<CheckBox> ocorrenciaCheckList = new ArrayList<CheckBox>();
	private ParametrosDoSistema parametrosDoSistema;

	private File logo;
	private File logoCert;
	private File imgCartaoAniversariante;
	private Collection<Empresa> empresas;
	private Collection<GrupoAC> grupoACs;
	
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private Collection<Colaborador> colaboradores;
	private String ano;
	private String motivoDesintegracao;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String cartaoAniversariante() throws Exception
	{
		empresa = empresaManager.findByIdProjection(empresa.getId());
		
    	String pathBackGroundRelatorio = "";
    	
    	String pathLogo = ArquivoUtil.getPathLogoEmpresa() + empresa.getImgAniversarianteUrl();
    	java.io.File logo = new java.io.File(pathLogo);
    	if(logo.exists())
    		pathBackGroundRelatorio = pathLogo;
    	
		parametros.put("BACKGROUND", pathBackGroundRelatorio);
		
		parametros.put("MSG", empresa.getMensagemCartaoAniversariante().replaceAll("#NOMECOLABORADOR#", "Nome do Aniversariante"));
		
		colaboradores = Arrays.asList(new Colaborador());
		return Action.SUCCESS;
	}
	
	private void prepare() throws Exception
	{
		if(empresa != null && empresa.getId() != null)
			empresa = empresaManager.findById(empresa.getId());

		if (ufs == null)
			ufs = estadoManager.findAll(new String[]{"sigla"});
		
		grupoACs = grupoACManager.findAll(new String[]{"codigo"});
	}

	public String sobre() throws Exception
	{
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		ano = DateUtil.getAno();
		return Action.SUCCESS;
	}
	
	public String prepareInsert() throws Exception
	{
		prepare();
		empresa.setMensagemModuloExterno("Se você não é registrado, cadastre já seu currículo e tenha acesso às vagas disponíveis em nossa empresa.");
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if (empresa != null && empresa.getUf() != null && empresa.getUf().getId() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{empresa.getUf().getId()}, new String[]{"nome"});

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		empresa.setGrupoAC(StringUtils.stripToNull(empresa.getGrupoAC()));
		empresa.setCodigoAC(StringUtils.stripToNull(empresa.getCodigoAC()));
		
		empresa = empresaManager.setLogo(empresa, logo, "logoEmpresas", logoCert, imgCartaoAniversariante);
		
		if(StringUtils.isEmpty(empresa.getLogoUrl()))
			empresa.setLogoUrl("fortes.gif");
		
		if(StringUtils.isEmpty(empresa.getImgAniversarianteUrl()))
			empresa.setImgAniversarianteUrl("aniversariantes.jpg");
		
		empresaManager.save(empresa);
		
		try {
			gerenciadorComunicacaoManager.insereGerenciadorComunicacaoDefault(empresa);
		} catch (Exception e) {
			addActionMessage("Não foi possível inserir as configurações do gerenciador de comunicação. Entre em contato com o suporte.");
			e.printStackTrace();
		}
		
		//criacao da pasta para o modulo externo
		java.io.File pastaExterno = new java.io.File(ArquivoUtil.getPathExterno());
		java.io.File novaPastaExterno = new java.io.File(ArquivoUtil.getPathExternoEmpresa(empresa.getId()));

		try
		{
			ArquivoUtil.copiar(pastaExterno, novaPastaExterno);
		}
		catch (IOException e)
		{
			addActionMessage("Não foi possível criar uma pasta de configurações do módulo externo.");
			e.printStackTrace();
		}

		return Action.SUCCESS;
	}
	
	public String update() throws Exception
	{
		Empresa empresaAntesDaAlteracao = empresaManager.findByIdProjection(empresa.getId());
		boolean tavaIntegradaComAC = empresaAntesDaAlteracao.isAcIntegra();
		
		if ( SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_INTEGRA_FORTES_PESSOAL"}) ) {
			empresa.setGrupoAC(StringUtils.stripToNull(empresa.getGrupoAC()));
			empresa.setCodigoAC(StringUtils.stripToNull(empresa.getCodigoAC()));
		} else {
			empresa.setAcIntegra(tavaIntegradaComAC);
		}
		
		empresa = empresaManager.setLogo(empresa, logo, "logoEmpresas", logoCert, imgCartaoAniversariante);
		
		if (empresaManager.checkEmpresaCodACGrupoAC(empresa)){
			throw new Exception("Já existe uma empresa com o mesmo código AC no grupo AC especificado");
		}
		
		if (empresaManager.verificaInconcistenciaIntegracaoAC(empresa))
		{
			setActionMsg("Não foi possível habilitar a integração com o Fortes Pessoal devido a cadastros realizados no período desintegrado.<br />Entre em contato com o suporte técnico.");
			empresa.setAcIntegra(false);
			empresaManager.update(empresa);
			atualizaEmpresaSessao();
			prepareUpdate();
			return Action.INPUT;
		}
		
		empresaManager.update(empresa);
		
		empresaManager.auditaIntegracao(empresa, tavaIntegradaComAC);
		Usuario usuario = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()) ;
		empresaManager.enviaEmailInformandoDesintegracao(empresa, tavaIntegradaComAC, motivoDesintegracao, usuario.getNome() + "("+usuario.getLogin()+")" );
		
		atualizaEmpresaSessao();
		
		if(empresaAntesDaAlteracao.isControlarVencimentoPorCertificacao() != empresa.isControlarVencimentoPorCertificacao() && empresaAntesDaAlteracao.getId().equals(getEmpresaSistema().getId()))
			return "successAlterandoMenu";

		return Action.SUCCESS;
	}

	private void atualizaEmpresaSessao() 
	{
		if(empresa.equals(getEmpresaSistema()))
		{
			empresa = empresaManager.findById(empresa.getId());
			SecurityUtil.setEmpresaSession(ActionContext.getContext().getSession(), empresa);
		}
	}

	public String showLogo() throws Exception
	{
		if (empresa.getLogoUrl() != null && !empresa.getLogoUrl().equals(""))
		{
			java.io.File file = ArquivoUtil.getArquivo(empresa.getLogoUrl(),"logoEmpresas");
			showFile(file);
		}
		
		return Action.SUCCESS;
	}

	public String showLogoCertificado() throws Exception
	{
		if (empresa.getLogoCertificadoUrl() != null && !empresa.getLogoCertificadoUrl().equals(""))
		{
			java.io.File file = ArquivoUtil.getArquivo(empresa.getLogoCertificadoUrl(),"logoEmpresas");
			showFile(file);
		}
		
		return Action.SUCCESS;
	}
	
	public String showImgAniversariante() throws Exception
	{
		if (empresa.getImgAniversarianteUrl() != null && !empresa.getImgAniversarianteUrl().equals(""))
		{
			java.io.File file = ArquivoUtil.getArquivo(empresa.getImgAniversarianteUrl(), "logoEmpresas");
			showFile(file);
		}
		
		return Action.SUCCESS;
	}

	private void showFile(java.io.File file) throws IOException 
	{
		com.fortes.model.type.File arquivo = new com.fortes.model.type.File();
		arquivo.setBytes(FileUtil.getFileBytes(file));
		arquivo.setName(file.getName());
		arquivo.setSize(file.length());
		int pos = arquivo.getName().indexOf(".");
		if(pos > 0){
			arquivo.setContentType(arquivo.getName().substring(pos));
		}
		if (arquivo != null && arquivo.getBytes() != null)
		{
			HttpServletResponse response = ServletActionContext.getResponse();

			response.addHeader("Expires", "0");
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Content-type", arquivo.getContentType());
			response.addHeader("Content-Transfer-Encoding", "binary");

			response.getOutputStream().write(arquivo.getBytes());
		}
	}
	
	public String prepareImportarCadastros() 
	{
		empresas = empresaManager.findAll();
		cadastrosCheckList = empresaManager.populaCadastrosCheckBox();
		
		return SUCCESS;
	}
	
	public String importarCadastros()
	{
		try 
		{
			List<String> mensagens = empresaManager.sincronizaEntidades(empresaOrigem.getId(), empresaDestino.getId(), cadastrosCheck, ocorrenciaCheck.split(","));
			
			if (!mensagens.isEmpty()) 
			{
				for (String mensagem : mensagens)
					addActionWarning(mensagem);
				addActionSuccess("Os demais cadastros foram importados com sucesso.");
			} 
			else {
				addActionSuccess("Cadastros importados com sucesso.");
			}

			empresaOrigem = null;
			empresaDestino = null;
		} 
		catch (Exception e) {
			addActionError("Erro ao importar cadastros.");
			e.printStackTrace();
		}
		
		prepareImportarCadastros();
		
		return SUCCESS;
	}

	public Empresa getEmpresa()
	{
		if(empresa == null)
		{
			empresa = new Empresa();
		}
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Object getModel()
	{
		return getEmpresa();
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public File getLogo()
	{
		return logo;
	}

	public void setLogo(File logo)
	{
		this.logo = logo;
	}

	public Collection<Cidade> getCidades()
	{
		return cidades;
	}

	public Collection<Estado> getUfs()
	{
		return ufs;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public Empresa getEmpresaOrigem() {
		return empresaOrigem;
	}

	public void setEmpresaOrigem(Empresa empresaOrigem) {
		this.empresaOrigem = empresaOrigem;
	}

	public Empresa getEmpresaDestino() {
		return empresaDestino;
	}

	public void setEmpresaDestino(Empresa empresaDestino) {
		this.empresaDestino = empresaDestino;
	}

	public String[] getCadastrosCheck() {
		return cadastrosCheck;
	}

	public void setCadastrosCheck(String[] cadastrosCheck) {
		this.cadastrosCheck = cadastrosCheck;
	}

	public Collection<CheckBox> getCadastrosCheckList() {
		return cadastrosCheckList;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		return parametrosDoSistema;
	}

	public File getLogoCert() 
	{
		return logoCert;
	}

	public void setLogoCert(File logoCert) 
	{
		this.logoCert = logoCert;
	}

	public Collection<GrupoAC> getGrupoACs() {
		return grupoACs;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public File getImgCartaoAniversariante() {
		return imgCartaoAniversariante;
	}

	public void setImgCartaoAniversariante(File imgCartaoAniversariante) {
		this.imgCartaoAniversariante = imgCartaoAniversariante;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public Collection<CheckBox> getOcorrenciaCheckList() {
		return ocorrenciaCheckList;
	}

	public void setOcorrenciaCheck(String ocorrenciaCheck) {
		this.ocorrenciaCheck = ocorrenciaCheck;
	}
	
	public Set<Entry<Integer, String>> getOpcoesFormulaTurnover() {
		return new FormulaTurnover().entrySet();
	}

	public String getAno() {
		return ano;
	}
	
	public void setMotivoDesintegracao(String motivoDesintegracao) {
		this.motivoDesintegracao= motivoDesintegracao;
	}

	public String getMotivoDesintegracao() {
		return motivoDesintegracao;
	}
}