package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MensagemVO implements Serializable {

	private Long usuarioMensagemId;
	private String remetente;
	private String link;
	private boolean anexo;
	private Character tipo;
	private Date data;
	private String texto;
	private boolean lida;

	public String getRemetente() {
		return remetente;
	}
	
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Character getTipo() {
		return tipo;
	}
	
	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public String getTextoAbreviado()
	{
		
		if (this.texto == null)
			return "";
		
		if(this.texto.length() > 120)
			return this.texto.substring(0, 120) + "...";
		else
			return this.texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isLida() {
		return lida;
	}

	public void setLida(boolean lida) {
		this.lida = lida;
	}

	public Long getUsuarioMensagemId() {
		return usuarioMensagemId;
	}

	public void setUsuarioMensagemId(Long usuarioMensagemId) {
		this.usuarioMensagemId = usuarioMensagemId;
	}

	public boolean isAnexo() {
		return anexo;
	}

	public void setAnexo(boolean anexo) {
		this.anexo = anexo;
	}
}
