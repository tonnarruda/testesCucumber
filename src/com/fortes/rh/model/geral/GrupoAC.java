package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="grupoAC_sequence", allocationSize=1)
public class GrupoAC extends AbstractModel implements Serializable
{
	@Column(length=20)
	private String descricao;
	@Column(length=3)
	private String codigo;
    @Column(length=120)
    private String acUrlSoap;
    @Column(length=120)
    private String acUrlWsdl;
    @Column(length=100)
    private String acUsuario;
    @Column(length=30)
    private String acSenha;
    
	public GrupoAC(String codigo, String descricao) 
	{
		super();
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public GrupoAC() 
	{
	}

	public String getCodigoDescricao() {
		return codigo + " - " + descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getAcUrlSoap() {
		return acUrlSoap;
	}

	public void setAcUrlSoap(String acUrlSoap) {
		this.acUrlSoap = acUrlSoap;
	}

	public String getAcUrlWsdl() {
		return acUrlWsdl;
	}

	public void setAcUrlWsdl(String acUrlWsdl) {
		this.acUrlWsdl = acUrlWsdl;
	}

	public String getAcUsuario() {
		return acUsuario;
	}

	public void setAcUsuario(String acUsuario) {
		this.acUsuario = acUsuario;
	}

	public String getAcSenha() {
		return acSenha;
	}

	public void setAcSenha(String acSenha) {
		this.acSenha = acSenha;
	}
}
