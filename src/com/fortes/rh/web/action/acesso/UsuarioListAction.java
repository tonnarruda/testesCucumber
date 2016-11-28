package com.fortes.rh.web.action.acesso;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class UsuarioListAction extends MyActionSupportList
{
	@Autowired
	private UsuarioManager usuarioManager;
	@Autowired
	private UsuarioEmpresaManager usuarioEmpresaManager;
	@Autowired
	private EmpresaManager empresaManager;

	private Collection<Empresa> empresas;
	private Collection<Usuario> usuarios;
	private Collection<Perfil> perfils;
	private Collection<UsuarioEmpresa> usuarioEmpresas;

	private Empresa empresa;
	private Usuario usuario;
	private UsuarioEmpresa usuarioEmpresa;
	
	private Map<String,Object> parametros = new HashMap<String, Object>();

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String list() throws Exception
	{

		String usuarioNome = "";
		if(usuario != null)
			usuarioNome = usuario.getNome();

		empresas = empresaManager.findToList(new String[]{"id","nome"}, new String[]{"id","nome"}, new String[]{"nome asc"});

		usuarios = usuarioManager.findUsuarios(getPage(), getPagingSize(), usuarioNome, empresa);

		setTotalSize(usuarioManager.getCountUsuario(usuarioNome, empresa));

		return Action.SUCCESS;
	}

	public String imprimirUsuariosPerfis()
	{
		parametros = RelatorioUtil.getParametrosRelatorio("Perfis dos Usuários por Empresa", getEmpresaSistema(), "");
		usuarioEmpresas = usuarioEmpresaManager.findPerfisEmpresas();
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			usuarioManager.removeUsuario(usuario);
			addActionMessage("Usuário excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir este Usuário. Utilize o campo \"Ativo\" para retirar o acesso do usuário ao sistema.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}


	public Usuario getUsuario(){
		if(usuario == null){
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario){
		this.usuario=usuario;
	}

	public void setUsuarios(Collection<Usuario> usuarios)
	{
		this.usuarios = usuarios;
	}

	public Collection<Perfil> getPerfils()
	{
		return perfils;
	}

	public void setPerfils(Collection<Perfil> perfils)
	{
		this.perfils = perfils;
	}

	public UsuarioEmpresa getUsuarioEmpresa()
	{
		return usuarioEmpresa;
	}

	public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa)
	{
		this.usuarioEmpresa = usuarioEmpresa;
	}

	public Collection<UsuarioEmpresa> getUsuarioEmpresas()
	{
		return usuarioEmpresas;
	}

	public void setUsuarioEmpresas(Collection<UsuarioEmpresa> usuarioEmpresas)
	{
		this.usuarioEmpresas = usuarioEmpresas;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}
}