package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoRelatorioDinamico_sequence", allocationSize=1)
public class ConfiguracaoRelatorioDinamico extends AbstractModel implements Serializable
{
	@OneToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
	@Column(length=600)
	private String campos = "";
	@Column(length=100)
	private String titulo = "";
	
	public ConfiguracaoRelatorioDinamico() 
	{
	}
	
	public ConfiguracaoRelatorioDinamico(Usuario usuario, String campos, String titulo) 
	{
		super();
		this.usuario = usuario;
		this.campos = campos;
		this.titulo = titulo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getCampos() {
		return campos;
	}
	public void setCampos(String campos) {
		this.campos = campos;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
