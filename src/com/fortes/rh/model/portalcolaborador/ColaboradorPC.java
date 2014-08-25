package com.fortes.rh.model.portalcolaborador;

import java.util.List;

import javax.persistence.Transient;

import com.fortes.rh.model.geral.Colaborador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ColaboradorPC extends AbstractAdapterPC
{
	@SerializedName("empresa")
	private EmpresaPC empresaPC;
	@SerializedName("endereco_attributes")
	private EnderecoPC enderecoPC;
	private String nome;
	@SerializedName("nome_comercial")
	private String nomeComercial;
	private String email; 
	private String cpf;
	private String ddd;
	private String telefone;
	private String celular; 
	private String escolaridade;
	@SerializedName("estado_civil")
	private String estadoCivil;
	private String conjuge; 
	private String pai; 
	private String mae; 
	@SerializedName("qtd_filhos")
	private Integer qtdFilhos;
	@SerializedName("historico_colaboradores")
	private List<HistoricoColaboradorPC> historicosPc;
	
	@Transient
	private ArquivoPC foto;
	
	public ColaboradorPC() {

	}

	public ColaboradorPC(Colaborador colaborador) 
	{
		this.nome 			= colaborador.getNome();
		this.nomeComercial 	= colaborador.getNomeComercial();
		
		if (colaborador.getEmpresa() != null)
		{
			this.setEmpresaPC(new EmpresaPC(colaborador.getEmpresa()));
		}
		
		if (colaborador.getEndereco() != null)
		{
			this.setEnderecoPC(new EnderecoPC(colaborador.getEndereco()));
		}
		
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
			this.estadoCivil	= colaborador.getPessoal().getEstadoCivil();
			this.conjuge 		= colaborador.getPessoal().getConjuge();
			this.pai 			= colaborador.getPessoal().getPai();
			this.mae 			= colaborador.getPessoal().getMae();
			this.qtdFilhos 		= colaborador.getPessoal().getQtdFilhos();
		}
		
		if (colaborador.getFoto() != null)
		{
			this.foto = new ArquivoPC(colaborador);
		}
	}
	
	public EmpresaPC getEmpresaPC() {
		return empresaPC;
	}

	public void setEmpresaPC(EmpresaPC empresaPC) {
		this.empresaPC = empresaPC;
	}
	
	public EnderecoPC getEnderecoPC() {
		return enderecoPC;
	}

	public void setEnderecoPC(EnderecoPC enderecoPC) {
		this.enderecoPC = enderecoPC;
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
	
	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
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
	
	public List<HistoricoColaboradorPC> getHistoricosPc() {
		return historicosPc;
	}
	
	public void setHistoricosPc(List<HistoricoColaboradorPC> historicosPc) {
		this.historicosPc = historicosPc;
	}

	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("colaborador", gson.toJsonTree(this));
		jsonObject.add("foto", gson.toJsonTree(this.foto));
		
		return jsonObject.toString();
	}

}
