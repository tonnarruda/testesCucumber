package com.fortes.rh.security;

import java.util.Date;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
public class UserDetailsImpl implements UserDetails
{
	private Long id;
	private String nome;
	private String username;
	private String password;
	private Date ultimoLogin;
	private boolean superAdmin;

	private GrantedAuthority[] authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired; 
	private boolean enabled;
	private String menuFormatado;
	private Empresa empresa;
	private Colaborador colaborador;
	private String versao = "";

	public UserDetailsImpl(Long id, String nome, String username, String password, boolean superAdmin, Date ultimoLogin, 
			GrantedAuthority[] authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, 
			String menuFormatado, Empresa empresa, Colaborador colaborador)
	{
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.superAdmin = superAdmin;
		this.ultimoLogin = ultimoLogin;
		this.authorities = authorities;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.menuFormatado = menuFormatado;
		this.empresa = empresa;
		this.colaborador = colaborador;
	}

	public GrantedAuthority[] getAuthorities()
	{
		return authorities;
	}

	public Long getId()
	{
		return id;
	}

	public String getNome()
	{
		return nome;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public boolean isAccountNonExpired()
	{
		return accountNonExpired;
	}

	public boolean isAccountNonLocked()
	{
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired()
	{
		return credentialsNonExpired;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public String getMenuFormatado()
	{
		return menuFormatado;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setAuthorities(GrantedAuthority[] authorities)
	{
		this.authorities = authorities;
	}

	public void setMenuFormatado(String menuFormatado)
	{
		this.menuFormatado = menuFormatado;
	}

	public String getEmpresaNome()
	{
		return empresa.getNome();
	}

	public Long getEmpresaId()
	{
		return empresa.getId();
	}
	
	public Date getUltimoLogin() 
	{
		return ultimoLogin;
	}

	public String getUltimoLoginFormatado() 
	{
		String uLogin = "Último login: ";
		if (ultimoLogin != null)
			uLogin += DateUtil.formataDate(ultimoLogin, "dd/MM/yyyy - HH:mm");
		else
			uLogin = "";
		
		return uLogin;
	}
	
	public void setUltimoLogin(Date ultimoLogin) 
	{
		this.ultimoLogin = ultimoLogin;
	}

	public boolean getEmpresaAcIntegra()
	{
		return empresa.isAcIntegra();
	}
	
	public char getEmpresaControlaRiscoPor()
	{
		return empresa.getControlaRiscoPor();
	}
	
	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public boolean getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}
}