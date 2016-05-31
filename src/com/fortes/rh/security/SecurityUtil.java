package com.fortes.rh.security;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.userdetails.UserDetails;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.config.SessionManager;
import com.fortes.rh.exception.LoginInvalidoException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.licenca.AutenticadorJarvis;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class SecurityUtil
{
	
	public static boolean verifyRole(Map session, String[] rolesVerify)
	{
		SecurityContext sc = (SecurityContext) session.get("ACEGI_SECURITY_CONTEXT");
		GrantedAuthority[] roles =  sc.getAuthentication().getAuthorities();

		for (int i = 0; i < roles.length; i++)
		{
			for (int j = 0; j < rolesVerify.length; j++)
				if (roles[i].getAuthority().equals(rolesVerify[j]))
					return true;
		}

		return false;
	}
	
	public static Usuario getUsuarioLoged(Map session)
	{
		SecurityContext sc = getSecurityContext(session);
		if(sc == null || !(sc.getAuthentication().getPrincipal() instanceof UserDetailsImpl))
			return null;
		
		Long id = ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getId();
		
		return ((UsuarioManager) SpringUtil.getBean("usuarioManager")).findById(id);
	}
	
	public static void registraLogin(Map session)
	{
		SessionManager sessionManager = (SessionManager) SpringUtil.getBean("sessionManager");
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) getUserDetails(session);
		sessionManager.registerLogin(ServletActionContext.getRequest().getSession().getId(),userDetailsImpl);
	}
	
	public static void registraLogout()
	{
		SessionManager sessionManager = (SessionManager) SpringUtil.getBean("sessionManager");
		
		sessionManager.registerLogout(ServletActionContext.getRequest().getSession().getId());
	}
	
	public static Integer getQuantidadeUsuariosLogados()
	{
		SessionManager sessionManager = (SessionManager) SpringUtil.getBean("sessionManager");
		
		return sessionManager.getNumeroDeUsuariosAutenticadosComAcessoNormal();
	}
	
	public static Long getIdUsuarioLoged(Map session)
	{
		SecurityContext sc = getSecurityContext(session);
		if(sc == null)
			return null;
		
		return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getId();
	}

	public static Empresa getEmpresaSession(Map session)
	{
		SecurityContext sc = getSecurityContext(session);
		if(sc == null)
			return null;

		return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getEmpresa();
	}
	
	public static Colaborador getColaboradorSession(Map session)
	{
		SecurityContext sc = getSecurityContext(session);
		if(sc == null)
			return null;
		
		try {
			return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getColaborador();
		} catch (ClassCastException e) {
			return null;
		}
		
	}

	private static SecurityContext getSecurityContext(Map session)
	{
		if (session == null)
			return null;
		
		SecurityContext sc = (SecurityContext) session.get("ACEGI_SECURITY_CONTEXT");
		if(sc == null)
			return null;
		
		return sc;
	}

	public static boolean setEmpresaSession(Map session, Empresa empresa)
	{
		if (session == null)
			return false;

		SecurityContext sc = (SecurityContext) session.get("ACEGI_SECURITY_CONTEXT");
		if(sc == null)
			return false;

		((UserDetailsImpl)sc.getAuthentication().getPrincipal()).setEmpresa(empresa);

		return true;
	}
	
	public static boolean setMenuFormatadoSession(Map session, String menuFormatado)
	{
		if (session == null)
			return false;
		
		SecurityContext sc = (SecurityContext) session.get("ACEGI_SECURITY_CONTEXT");
		if(sc == null)
			return false;
		
		((UserDetailsImpl)sc.getAuthentication().getPrincipal()).setMenuFormatado(menuFormatado);
		
		return true;
	}

	public static UserDetails getUserDetails(Map session)
	{
		if (session == null)
			return null;
		
		SecurityContext sc = (SecurityContext) session.get("ACEGI_SECURITY_CONTEXT");
		if(sc == null)
			return null;
		
		return ((UserDetailsImpl)sc.getAuthentication().getPrincipal());
	}
	
	public static boolean hasLoggedUser() {
		Usuario user = getUsuarioLoged(ActionContext.getContext().getSession());
		return user != null;
	}
	
	public static boolean isAuthenticatedUser() {
		SessionManager sessionManager = (SessionManager) SpringUtil.getBean("sessionManager");
		
		return sessionManager.isUserLogged(ServletActionContext.getRequest().getSession().getId());
	}

	public static String getNomeUsuarioLogedByDWR(HttpSession session) {
		SecurityContext sc = (SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT");
		
		if(sc == null)
			return null;

		return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getUsername();
	}
	
	public static Empresa getEmpresaByDWR(HttpSession session) {
		SecurityContext sc = (SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT");
		
		if(sc == null)
			return null;

		return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getEmpresa();
	}
	
	public static String getVersao(Map session) {
		try {
			if (session == null)
				return "";

			SecurityContext sc = (SecurityContext) session.get("ACEGI_SECURITY_CONTEXT");

			if(sc == null)
				return "";

			return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getVersao();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static ParametrosDoSistema getParametrosDoSistemaSession(Map session)
	{
		SecurityContext sc = getSecurityContext(session);
		if(sc == null)
			return null;
		
		return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getParametrosDoSistema();
	}
	
	@SuppressWarnings("unchecked")
	public static void removeSessaoFiltrosConfiguracoes() 
	{
		Map<Object, Object> session = ActionContext.getContext().getSession();
		for (Map.Entry<Object, Object> entry : session.entrySet()) 
		{
			if (entry.getKey().toString().startsWith("webwork.ScopeInterceptor"))
			{
				session.remove(entry.getKey());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void autenticaLogin(Map session) throws Exception
	{
		if(AutenticadorJarvis.isVerificaLicensa() && !isAuthenticatedUser()){
			SessionManager sessionManager = (SessionManager) SpringUtil.getBean("sessionManager");
			
			boolean temAcessoRestrito = ((UserDetailsImpl)getUserDetails(session)).getHasAcessoRestrito();
			
			if(!temAcessoRestrito && sessionManager.getNumeroDeUsuariosAutenticadosComAcessoNormal() >= AutenticadorJarvis.getClient().getQtdAcessosSimultaneos())
				throw new LoginInvalidoException("Limite de login execedido.", "limiteLoginExcedido");

			registraLogin(ActionContext.getContext().getSession());
		}
	}
}