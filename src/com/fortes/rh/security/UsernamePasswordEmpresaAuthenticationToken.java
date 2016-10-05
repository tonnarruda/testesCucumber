package com.fortes.rh.security;

import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;

@SuppressWarnings("serial")
public class UsernamePasswordEmpresaAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	private String empresa;
	private String SOSSeed;

	public UsernamePasswordEmpresaAuthenticationToken(String username, String password, String empresa, String SOSSeed){
		super(username, password);
		this.empresa = empresa;
		this.SOSSeed = SOSSeed;
	}

	public String getEmpresa(){
		return empresa;
	}

	public void setEmpresa(String empresa){
		this.empresa = empresa;
	}
	
	public String getSOSSeed() {
		return SOSSeed;
	}

	public void setSOSSeed(String sOSSeed) {
		SOSSeed = sOSSeed;
	}
}
