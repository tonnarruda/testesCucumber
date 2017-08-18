package com.fortes.rh.security;

import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;

@SuppressWarnings("serial")
public class UsernamePasswordEmpresaAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	private String empresa;
	private String SOSSeed;
	private String recaptchaResponse;
	private String contexto;

	public UsernamePasswordEmpresaAuthenticationToken(String username, String password, String empresa, String SOSSeed, String recaptchaResponse, String contexto){
		super(username, password);
		this.empresa = empresa;
		this.SOSSeed = SOSSeed;
		this.recaptchaResponse = recaptchaResponse;
		this.contexto = contexto;
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

	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}

	public String getContexto() {
		return contexto;
	}
}
