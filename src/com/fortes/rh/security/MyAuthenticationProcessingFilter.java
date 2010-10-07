package com.fortes.rh.security;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;

public class MyAuthenticationProcessingFilter extends AuthenticationProcessingFilter
{
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException
    {
    	String username  = obtainUsername(request);
    	String password  = obtainPassword(request);
    	String empresaId = obtainEmpresa(request);

	    if (username == null) {
	        username = "";
	    }

	    if (password == null) {
	        password = "";
	    }

	    if (empresaId == null) {
	    	empresaId = "";
	    }

	    UsernamePasswordEmpresaAuthenticationToken authRequest = new UsernamePasswordEmpresaAuthenticationToken(username, password, empresaId);

	    // Allow subclasses to set the "details" property
	    setDetails(request, authRequest);

	    // Place the last username attempted into HttpSession for views
	    request.getSession().setAttribute(ACEGI_SECURITY_LAST_USERNAME_KEY, username);

	    return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainEmpresa(HttpServletRequest request)
    {
        return request.getParameter("j_empresa");
    }
}
