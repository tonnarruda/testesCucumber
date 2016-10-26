package com.fortes.rh.model.captacao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CandidatoJsonVO
{
	@Id
	private String id;
	
	private String nome;
	private String dataNascimento;
	private String sexo;
	private String cpf;
	private String escolaridade;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String email;
	private String foneFixo;
	private String celular;
	private String estadoCivil;
	private String mae;
	private String rg;
	private String pis;
	private String[] funcoesPretendidas;
	private String registroProficional;
	private String validadeRegistro;

    public CandidatoJsonVO()
    {
    }

	public CandidatoJsonVO(String id, String nome, String dataNascimento, String sexo, String cpf, String escolaridade, String cep, String logradouro, String numero, String complemento,
			String bairro, String cidade, String estado, String email, String foneFixo, String celular, String estadoCivil,	String mae, String rg, String pis, String[] funcoesPretendidas,
			String registroProficional, String validadeRegistro) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cpf = cpf;
		this.escolaridade = escolaridade;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.email = email;
		this.foneFixo = foneFixo;
		this.celular = celular;
		this.estadoCivil = estadoCivil;
		this.mae = mae;
		this.rg = rg;
		this.pis = pis;
		this.funcoesPretendidas = funcoesPretendidas;
		this.registroProficional = registroProficional;
		this.validadeRegistro = validadeRegistro;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}
	
	public String getEstado(){
		return estado;
	}

	public String getEmail() {
		return email;
	}

	public String getFoneFixo() {
		return foneFixo;
	}

	public String getCelular() {
		return celular;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public String getMae() {
		return mae;
	}

	public String getRg() {
		return rg;
	}

	public String getPis() {
		return pis;
	}

	public String getRegistroProficional() {
		return registroProficional;
	}

	public String getValidadeRegistro() {
		return validadeRegistro;
	}

	public String[] getFuncoesPretendidas() {
		return funcoesPretendidas;
	}
}