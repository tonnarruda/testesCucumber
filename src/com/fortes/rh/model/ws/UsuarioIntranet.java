package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UsuarioIntranet implements Serializable
{
	private String nomeComercial;
	private String email;
	private String celular;
	private Date dataNascimento;
	private Long empresaId;
	private String areaNome;
	private String cargoNome;
	private boolean desligado;
	
	public String getNomeComercial() {
		return nomeComercial;
	}
	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Long getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
	public String getAreaNome() {
		return areaNome;
	}
	public void setAreaNome(String areaNome) {
		this.areaNome = areaNome;
	}
	public String getCargoNome() {
		return cargoNome;
	}
	public void setCargoNome(String cargoNome) {
		this.cargoNome = cargoNome;
	}
	public boolean isDesligado() {
		return desligado;
	}
	public void setDesligado(boolean desligado) {
		this.desligado = desligado;
	}
	
}