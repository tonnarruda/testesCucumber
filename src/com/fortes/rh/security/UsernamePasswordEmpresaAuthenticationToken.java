package com.fortes.rh.security;

import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;

public class UsernamePasswordEmpresaAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	private String empresa;

	public String getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(String empresa)
	{
		this.empresa = empresa;
	}

	public UsernamePasswordEmpresaAuthenticationToken(String username, String password, String empresa)
	{
		super(username, password);
		this.empresa = empresa;
	}
}
