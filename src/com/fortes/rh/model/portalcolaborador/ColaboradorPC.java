package com.fortes.rh.model.portalcolaborador;

import com.fortes.rh.model.geral.Colaborador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ColaboradorPC {

	private Long id;
	@SerializedName("empresa_id")
	private Long empresaId;
	private String nome;
	@SerializedName("nome_comercial")
	private String nomeComercial;
	private String email; 
	private String cpf;
	private String ddd;
	private String telefone;
	private String celular; 
	private String escolaridade; 
	private String conjuge; 
	private String pai; 
	private String mae; 
	@SerializedName("qtd_filhos")
	private Integer qtdFilhos;
	
	public ColaboradorPC() {

	}

	public ColaboradorPC(Colaborador colaborador) 
	{
		this.id 			= colaborador.getId();
		this.empresaId 		= colaborador.getEmpresa().getId();
		this.nome 			= colaborador.getNome();
		this.nomeComercial 	= colaborador.getNomeComercial();
		
		if (colaborador.getContato() != null)
		{
			this.email 			= colaborador.getContato().getEmail();
			this.ddd 			= colaborador.getContato().getDdd();
			this.telefone 		= colaborador.getContato().getFoneFixo();
			this.celular 		= colaborador.getContato().getFoneCelular();
		}

		if (colaborador.getPessoal() != null)
		{
			this.cpf 			= colaborador.getPessoal().getCpf();
			this.escolaridade 	= colaborador.getPessoal().getEscolaridade();
			this.conjuge 		= colaborador.getPessoal().getConjuge();
			this.pai 			= colaborador.getPessoal().getPai();
			this.mae 			= colaborador.getPessoal().getMae();
			this.qtdFilhos 		= colaborador.getPessoal().getQtdFilhos();
		}
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getEmpresaId() {
		return empresaId;
	}
	
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
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
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getDdd() {
		return ddd;
	}
	
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getEscolaridade() {
		return escolaridade;
	}
	
	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}
	
	public String getConjuge() {
		return conjuge;
	}
	
	public void setConjuge(String conjuge) {
		this.conjuge = conjuge;
	}
	
	public String getPai() {
		return pai;
	}
	
	public void setPai(String pai) {
		this.pai = pai;
	}
	
	public String getMae() {
		return mae;
	}
	
	public void setMae(String mae) {
		this.mae = mae;
	}
	
	public Integer getQtdFilhos() {
		return qtdFilhos;
	}
	
	public void setQtdFilhos(Integer qtdFilhos) {
		this.qtdFilhos = qtdFilhos;
	}
	
	public String toString()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("colaborador", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}
