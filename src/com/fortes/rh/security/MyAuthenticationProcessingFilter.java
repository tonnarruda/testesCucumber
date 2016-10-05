package com.fortes.rh.security;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;

public class MyAuthenticationProcessingFilter extends AuthenticationProcessingFilter
{
	static byte[] encodedBytes = new byte[]{89, 122, 66, 116, 99, 68, 82, 106, 100, 68, 66, 121};
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
    	String username  = obtainUsername(request);
    	String password  = obtainPassword(request);
    	String empresaId = obtainEmpresa(request);
    	String SOSSeed = obtainContraSenha(request);

	    if (username == null) 
	        username = "";
	    
	    if (username.toUpperCase().equals("SOS")) 
	        username = "SOS";

	    if (password == null) 
	        password = "";

	    if (empresaId == null) 
	    	empresaId = "";

	    if (SOSSeed == null) 
	    	SOSSeed = "";

	    UsernamePasswordEmpresaAuthenticationToken authRequest = new UsernamePasswordEmpresaAuthenticationToken(username, password, empresaId, SOSSeed);

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
    
    protected String obtainContraSenha(HttpServletRequest request)
    {
        return request.getParameter("j_SOSSeed");
    }
}
