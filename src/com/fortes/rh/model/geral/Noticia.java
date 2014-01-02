package com.fortes.rh.model.geral;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="noticia_sequence", allocationSize=1)
public class Noticia extends AbstractModel
{
	public static final String ULTIMAS_NOTICIAS = "ULTIMAS_NOTICIAS";
	
	private String texto;
	private String link;
	private Integer criticidade;
	
	@OneToMany(mappedBy="noticia")
	private Collection<UsuarioNoticia> usuarioNoticias;
	
	@Transient
	private Boolean lida;
	
	public Noticia() {
		
	}
	
	public Noticia(String texto, String link, Integer criticidade) {
		this.texto = texto;
		this.link = link;
		this.criticidade = criticidade;
	}

	public Noticia(Long id, String texto, String link, Integer criticidade, Boolean lida) {
		this.setId(id);
		this.texto = texto;
		this.link = link;
		this.criticidade = criticidade;
		this.lida = lida;
	}

	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Integer getCriticidade() {
		return criticidade;
	}
	
	public void setCriticidade(Integer criticidade) {
		this.criticidade = criticidade;
	}

	public Collection<UsuarioNoticia> getUsuarioNoticias() {
		return usuarioNoticias;
	}

	public void setUsuarioNoticias(Collection<UsuarioNoticia> usuarioNoticias) {
		this.usuarioNoticias = usuarioNoticias;
	}
	
	public Boolean getLida() {
		return lida;
	}

	public void setLida(Boolean lida) {
		this.lida = lida;
	}
}