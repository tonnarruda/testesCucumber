package com.fortes.rh.model.geral;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;

@Entity
@SequenceGenerator(name="sequence", sequenceName="usuarionoticia_sequence", allocationSize=1)
public class UsuarioNoticia extends AbstractModel
{
	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Noticia noticia;
	
	public UsuarioNoticia()
	{
		
	}
	
	public UsuarioNoticia(Usuario usuario, Noticia noticia)
	{
		this.usuario = usuario;
		this.noticia = noticia;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public void setUsuarioId(Long usuarioId) {
		if (this.usuario == null)
			this.usuario = new Usuario();
		
		this.usuario.setId(usuarioId);
	}
	
	public void setNoticiaId(Long noticiaId) {
		if (this.noticia == null)
			this.noticia = new Noticia();
		
		this.noticia.setId(noticiaId);
	}
}