package com.fortes.rh.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter
{
	static byte[] encodedBytes = new byte[]{89, 122, 66, 116, 99, 68, 82, 106, 100, 68, 66, 121};
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
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
	    request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, username);

	    return this.getAuthenticationManager().authenticate(authRequest);
    }

	@Override
	 protected void successfulAuthentication(HttpServletRequest request,
	         HttpServletResponse response, Authentication authResult)
	         throws IOException, ServletException {
	     super.successfulAuthentication(request, response, authResult);
	 
	     System.out.println("==successful login==");
	 }

	 @Override
	 protected void unsuccessfulAuthentication(HttpServletRequest request,
	         HttpServletResponse response, AuthenticationException failed)
	         throws IOException, ServletException {
	     super.unsuccessfulAuthentication(request, response, failed);
	 
	     System.out.println("==failed login==");
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
