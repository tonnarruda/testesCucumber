/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.web.action.geral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
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

	private Collection<Estado> ufs = null;
	private Collection<Cidade> cidades = new ArrayList<Cidade>();
	private Empresa empresa;
	
	private Empresa empresaOrigem;
	private Empresa empresaDestino;
	private String[] cadastrosCheck;
	private Collection<CheckBox> cadastrosCheckList;
	private ParametrosDoSistema parametrosDoSistema;

	private File logo;
	private File logoCert;
	private Collection<Empresa> empresas;
	private Collection<GrupoAC> grupoACs;

	public String execute() throws Exception
	{
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
		return Action.SUCCESS;
	}
	
	public String prepareInsert() throws Exception
	{
		prepare();
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
		empresa = empresaManager.setLogo(empresa, logo, "logoEmpresas", logoCert);
		
		if(StringUtils.isEmpty(empresa.getLogoUrl()))
			empresa.setLogoUrl("fortes.gif");
		
		empresaManager.save(empresa);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		empresa = empresaManager.setLogo(empresa, logo, "logoEmpresas", logoCert);
		empresaManager.update(empresa);

		if(empresa.equals(getEmpresaSistema()))
		{
			empresa = empresaManager.findById(empresa.getId());
			SecurityUtil.setEmpresaSession(ActionContext.getContext().getSession(), empresa);
		}

		return Action.SUCCESS;
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
			empresaManager.sincronizaEntidades(empresaOrigem.getId(), empresaDestino.getId(), cadastrosCheck);
			
			addActionMessage("Cadastros importados com sucesso.");
			
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
}