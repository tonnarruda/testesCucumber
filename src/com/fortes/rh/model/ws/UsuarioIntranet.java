package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UsuarioIntranet implements Serializable
{
	private String rhId;
	private String nomeComercial;
	private String email;
	private String celular;
	private String dataNascimento;
	private String areaId;
	private String cargoId;
	private String estabelecimentoId;
	private String cpf;
	private boolean desligado;
	
	public void setNomeComercialParaRH(String colaboradorNome, String colaboradorNomeComercial)
	{
		String nomeRetorno;
		
		if (colaboradorNomeComercial != null && !colaboradorNomeComercial.equals(""))
			nomeRetorno = colaboradorNomeComercial;
		else {
			String[] nome = colaboradorNome.split(" "); 

			if(nome.length > 1)
				nomeRetorno = nome[0]+" "+nome[nome.length - 1];
			else
				nomeRetorno = nome[0];
		}

		this.nomeComercial = nomeRetorno;
	}

	public String getRhId() {
		return rhId;
	}

	public void setRhId(String rhId) {
		this.rhId = rhId;
	}

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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCargoId() {
		return cargoId;
	}

	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}

	public boolean isDesligado() {
		return desligado;
	}

	public void setDesligado(boolean desligado) {
		this.desligado = desligado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEstabelecimentoId() {
		return estabelecimentoId;
	}

	public void setEstabelecimentoId(String estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}
}