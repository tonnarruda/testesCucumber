package com.fortes.f2rh;

import java.math.BigDecimal;

import com.fortes.model.type.File;
import com.fortes.rh.util.StringUtil;

public class Curriculo {
	
	private int id;
	private String updated_rh;
	private String created_rh;
	private String nome;
	private String endereco;
	private String estado;
	private String cidade_rh;
	private String bairro;
	private String cep;
	private String data_nascimento_rh;
	private String nacionalidade;
	private String estado_civil;
	private String sexo;
	private String escolaridade_rh;
	private String telefone_rh;
	private String ddd_rh;
	private String area_formacao;
	private String curso;
	private String observacoes_complementares;
	private String idioma;
	private String cargo;
	
	private BigDecimal salario;
	private User user;
	
	private String foto_file_name;
	private File foto;

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

	public String getS() {
		return StringUtil.montaTokenF2rh(this.nome);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCpf() {
		return this.user.getLogin();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getUpdated_rh() {
		return updated_rh;
	}

	public void setUpdated_rh(String updatedRh) {
		updated_rh = updatedRh;
	}

	public String getCreated_rh() {
		return created_rh;
	}

	public void setCreated_rh(String createdRh) {
		created_rh = createdRh;
	}

	public String getData_nascimento_rh() {
		return data_nascimento_rh;
	}

	public void setData_nascimento_rh(String dataNascimentoRh) {
		data_nascimento_rh = dataNascimentoRh;
	}

	public String getTelefone_rh() {
		return telefone_rh;
	}

	public void setTelefone_rh(String telefoneRh) {
		telefone_rh = telefoneRh;
	}

	public String getDdd_rh() {
		return ddd_rh;
	}

	public void setDdd_rh(String dddRh) {
		ddd_rh = dddRh;
	}

	public File getFoto() {
		return foto;
	}
	public void setFoto(File foto) {
		this.foto = foto;
	}
	
	public String getFoto_file_name() {
		return foto_file_name;
	}

	public void setFoto_file_name(String foto_file_name) {
		this.foto_file_name = foto_file_name;
	}

}
