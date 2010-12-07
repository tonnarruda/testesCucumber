package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoPerformance_sequence", allocationSize=1)
public class ConfiguracaoPerformance extends AbstractModel implements Serializable
{
	@OneToOne
    private Usuario usuario;
	@Column(length=10)
	private String caixa;
	private int ordem;
	private boolean aberta;
	
	public ConfiguracaoPerformance()
	{
		super();
	}
		
	public ConfiguracaoPerformance(Long usuarioId, String caixa, int ordem, boolean aberta) 
	{
		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);
		
		this.usuario = usuario;
		this.caixa = caixa;
		this.ordem = ordem;
		this.aberta = aberta;
	}

	public ConfiguracaoPerformance(Usuario usuario, String caixa, int ordem, boolean aberta) 
	{
		this.usuario = usuario;
		this.caixa = caixa;
		this.ordem = ordem;
		this.aberta = aberta;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getCaixa() {
		return caixa;
	}
	public void setCaixa(String caixa) {
		this.caixa = caixa;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public boolean isAberta() {
		return aberta;
	}
	public void setAberta(boolean aberta) {
		this.aberta = aberta;
	}
}
