package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TAuditoria implements Serializable
{
	private String modulo;
	private String operacao;
	private String usuario;
	
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}