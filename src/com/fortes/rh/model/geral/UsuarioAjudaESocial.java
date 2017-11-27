package com.fortes.rh.model.geral;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;

@Entity
@SequenceGenerator(name="sequence", sequenceName="usuarioajudaesocial_sequence", allocationSize=1)
public class UsuarioAjudaESocial extends AbstractModel
{
	@ManyToOne
	private Usuario usuario;

	private String telaAjuda;
	
	public UsuarioAjudaESocial()
	{
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTelaAjuda() {
		return telaAjuda;
	}

	public void setTelaAjuda(String telaAjuda) {
		this.telaAjuda = telaAjuda;
	}
	
	public void setUsuarioId(Long usuarioId){
		if(this.usuario == null)
			this.usuario = new Usuario();
		
		this.usuario.setId(usuarioId);
	}
}