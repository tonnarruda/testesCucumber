package com.fortes.rh.model.json;

import java.util.Date;

import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.util.DateUtil;

public class ColaboradorJson {

	private String id;
	private String nome;
	private String dataNascimento;
	private String sexo;
	private String cpf;
	private String rg;
	private String uf;
	private String cidade;
	private String cep;
	private String logradouro;
	private String numero;
	private String bairro;
	private String email;
	private String ddd;
	private String telefone;
	private String escolaridade;
	private String nomeMae;
	private String nomePai;
	private String matricula;
	private String colocacao;
	private String dataAdmissao;
	private String dataDesligamento;
	private String dataEncerramentoContrato;
	private String cargo;
	private String area;
	private String funcao;
	private EmpresaJson empresa;
	
	public ColaboradorJson(){
		
	}
	
	public ColaboradorJson(Long id, String nome, Date dataNascimento, char sexo, String cpf, String rg, String uf, String cidade,
			String cep, String logradouro, String numero, String bairro, String email, String ddd, String telefone, String escolaridade, String nomeMae,
			String nomePai, String matricula, String colocacao, Date dataAdmissao, Date dataDesligamento,
			Date dataEncerramentoContrato, String cargo, String area, String funcao, Long empresaId, String empresaNome, String empresaBaseCnpj) 
	{
		this.id = String.valueOf(id);
		this.nome = nome;
		this.dataNascimento = DateUtil.formataDiaMesAno(dataNascimento);
		this.sexo = String.valueOf(sexo);
		this.cpf = cpf;
		this.rg = rg;
		this.uf = uf;
		this.cidade = cidade;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.email = email;
		this.ddd = ddd;
		this.telefone = telefone;
		this.escolaridade = Escolaridade.getEscolaridade(escolaridade);
		this.nomeMae = nomeMae;
		this.nomePai = nomePai;
		this.matricula = matricula;
		this.colocacao = new Vinculo().get(colocacao);
		this.dataAdmissao = DateUtil.formataDiaMesAno(dataAdmissao);
		this.dataDesligamento = DateUtil.formataDiaMesAno(dataDesligamento);
		this.dataEncerramentoContrato = DateUtil.formataDiaMesAno(dataEncerramentoContrato);
		this.cargo = cargo;
		this.area = area;
		this.funcao = funcao;
		this.empresa = new EmpresaJson(empresaId, empresaNome, empresaBaseCnpj);
	}
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getRg() {
		return rg;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEscolaridade() {
		return escolaridade;
	}
	
	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}
	
	public String getNomeMae() {
		return nomeMae;
	}
	
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	public String getNomePai() {
		return nomePai;
	}
	
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getColocacao() {
		return colocacao;
	}
	
	public void setColocacao(String colocacao) {
		this.colocacao = colocacao;
	}
	
	public String getDataAdmissao() {
		return dataAdmissao;
	}
	
	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	
	public String getDataDesligamento() {
		return dataDesligamento;
	}
	
	public void setDataDesligamento(String dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}
	
	public String getDataEncerramentoContrato() {
		return dataEncerramentoContrato;
	}
	
	public void setDataEncerramentoContrato(String dataEncerramentoContrato) {
		this.dataEncerramentoContrato = dataEncerramentoContrato;
	}
	
	public String getCargo() {
		return cargo;
	}
	
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getFuncao() {
		return funcao;
	}
	
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}


	public EmpresaJson getEmpresa() {
		return empresa;
	}


	public void setEmpresaJson(EmpresaJson empresa) {
		this.empresa = empresa;
	}
}
