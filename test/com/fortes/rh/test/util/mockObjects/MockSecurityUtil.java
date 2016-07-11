package com.fortes.rh.test.util.mockObjects;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.acegisecurity.context.SecurityContext;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.UserDetailsImpl;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class MockSecurityUtil
{
	public static boolean verifyRole = false;
	public static String[] roles = new String[]{"ROLE_COMPROU_SESMT"};

	public static Empresa getEmpresaSession(Map session)
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		empresa.setAcIntegra(true);
		return empresa;
	}

	public static Usuario getUsuarioLoged(Map session)
	{
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("nome");
		return usuario;
	}
	
	public static Colaborador getColaboradorSession(Map session)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		return colaborador;
	}

	public static boolean verifyRole(Map session, String[] rolesVerify)
	{
		for (String roleVerify : rolesVerify)
			for (String role : roles)
				if(roleVerify.equals(role))
					return true;
		
		return verifyRole;
	}

	public static String getNomeUsuarioLogedByDWR(HttpSession session) 
	{
		return "UsuarioTeste";
	}
	
	public static Long getIdUsuarioLoged(Map session)
	{
		return 1L;
	}
}
