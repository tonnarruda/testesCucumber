package com.fortes.rh.security;

import java.util.Map;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.SpringUtil;
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
		if(sc == null)
			return null;

		Long id = ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getId();

		return ((UsuarioManager) SpringUtil.getBean("usuarioManager")).findById(id);
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
		
		return ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getColaborador();
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

	public static boolean hasLoggedUser() {
		Usuario user = getUsuarioLoged(ActionContext.getContext().getSession());
		return user != null;
	}
}