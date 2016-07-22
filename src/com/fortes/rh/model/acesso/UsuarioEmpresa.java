package com.fortes.rh.model.acesso;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "usuarioEmpresa_sequence", allocationSize = 1)
public class UsuarioEmpresa extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	private Perfil perfil;

	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa;


	public UsuarioEmpresa() 
	{	
	}
	
	public UsuarioEmpresa(Usuario usuario, Perfil perfil, Empresa empresa) {
		this.usuario = usuario;
		this.perfil = perfil;
		this.empresa = empresa;
	}
	
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Perfil getPerfil()
	{
		return perfil;
	}

	public void setPerfil(Perfil perfil)
	{
		this.perfil = perfil;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public void setUsuarioId(Long usuarioId)
	{
		if(usuario == null)
			usuario = new Usuario();

		usuario.setId(usuarioId);
	}
	
	public void setPerfilId(Long perfilId)
	{
		if(this.perfil == null)
			this.perfil = new Perfil();
		
		this.perfil.setId(perfilId);
	}

	public void setUsuarioNome(String usuarioNome)
	{
		if(usuario == null)
			usuario = new Usuario();

		usuario.setNome(usuarioNome);

	}

	public void setUsuarioAcessoSistema(boolean usuarioAcessoSistema)
	{
		if(usuario == null)
			usuario = new Usuario();

		usuario.setAcessoSistema(usuarioAcessoSistema);

	}

	public void setUsuarioLogin(String usuarioLogin)
	{
		if(usuario == null)
			usuario = new Usuario();

		usuario.setLogin(usuarioLogin);

	}

	public void setPerfilNome(String perfilNome)
	{
		if(perfil == null)
			perfil = new Perfil();

		perfil.setNome(perfilNome);

	}


	public void setEmpresaId(Long empresaId)
	{
		if(empresa == null)
			empresa = new Empresa();

		empresa.setId(empresaId);

	}

	public void setEmpresaNome(String empresaNome)
	{
		if(empresa == null)
			empresa = new Empresa();

		empresa.setNome(empresaNome);

	}

	public Empresa getEmpresa()
	{
		return empresa;
	}
}