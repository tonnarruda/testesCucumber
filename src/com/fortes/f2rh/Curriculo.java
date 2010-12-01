package com.fortes.f2rh;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Curriculo {
	
	private int id;
	private String updated_at;
	private String created_at;
	private String nome;
	private String endereco;
	private String estado;
	private String cidade_rh;
	private String bairro;
	private String cep;
	private String data_nascimento;
	private String nacionalidade;
	private String estado_civil;
	private String sexo;
	private String escolaridade_rh;
	private String area_formacao;
	private String curso;
	private String observacoes_complementares;
	private String idioma;
	private String cargo;
	
	private BigDecimal salario;
	private User user;
	private ArrayList<CurriculoTelefone> curriculo_telefones;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curriculo other = (Curriculo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getCidade_rh() {
		return cidade_rh;
	}

	public void setCidade_rh(String cidade_rh) {
		this.cidade_rh = cidade_rh;
	}

	public String getEscolaridade_rh() {
		return escolaridade_rh;
	}

	public void setEscolaridade_rh(String escolaridade_rh) {
		this.escolaridade_rh = escolaridade_rh;
	}

	public String getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getCpf() {
		return this.user.getLogin();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<CurriculoTelefone> getCurriculo_telefones() {
		return curriculo_telefones;
	}

	public void setCurriculo_telefones(
			ArrayList<CurriculoTelefone> curriculo_telefones) {
		this.curriculo_telefones = curriculo_telefones;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getEstado_civil() {
		return estado_civil;
	}

	public void setEstado_civil(String estado_civil) {
		this.estado_civil = estado_civil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public String getArea_formacao() {
		return area_formacao;
	}

	public void setArea_formacao(String area_formacao) {
		this.area_formacao = area_formacao;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getObservacoes_complementares() {
		return observacoes_complementares;
	}

	public void setObservacoes_complementares(String observacoes_complementares) {
		this.observacoes_complementares = observacoes_complementares;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String createdAt) {
		created_at = createdAt;
	}

}
