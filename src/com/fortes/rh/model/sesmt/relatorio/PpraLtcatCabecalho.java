package com.fortes.rh.model.sesmt.relatorio;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.util.StringUtil;

public class PpraLtcatCabecalho 
{
	private Empresa empresa;
	private Estabelecimento estabelecimento;
	private String ambienteNome = "";
	private String ambienteDescricao = "";
	private Integer qtdHomens=0;
	private Integer qtdMulheres=0;
	private String funcoes = "";
	private Integer localAmbiente;
	private String cnpjEstabelecimentoDeTerceiros;
	
	public PpraLtcatCabecalho() {
	}
	
	public PpraLtcatCabecalho(Empresa empresa, Estabelecimento estabelecimento, Ambiente ambiente) 
	{
		this.empresa = empresa;
		this.estabelecimento = estabelecimento;
		this.ambienteNome = ambiente.getNome();
		this.ambienteDescricao = ambiente.getHistoricoAtual().getDescricao();
		this.localAmbiente = ambiente.getHistoricoAtual().getLocalAmbiente();
		this.cnpjEstabelecimentoDeTerceiros = ambiente.getHistoricoAtual().getCnpjEstabelecimentoDeTerceirosFormatado();
	}

	public String getCnpjFormatado()
	{
		return StringUtil.formataCnpj(empresa.getCnpj(), estabelecimento.getComplementoCnpj());
	}
	
	public String getCepFormatado()
	{
		return StringUtil.formataCep(estabelecimento.getEndereco().getCep());
	}
	
	public Integer getQtdTotal()
	{
		return qtdHomens+qtdMulheres;
	}
	
	public String getAmbienteNome() {
		return ambienteNome;
	}

	public void setAmbienteNome(String ambienteNome) {
		this.ambienteNome = ambienteNome;
	}

	public String getAmbienteDescricao() {
		return ambienteDescricao;
	}

	public void setAmbienteDescricao(String ambienteDescricao) {
		this.ambienteDescricao = ambienteDescricao;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(String funcoes) {
		this.funcoes = funcoes;
	}

	public Integer getQtdHomens() {
		return qtdHomens;
	}

	public void setQtdHomens(Integer qtdHomens) {
		this.qtdHomens = qtdHomens;
	}

	public Integer getQtdMulheres() {
		return qtdMulheres;
	}

	public void setQtdMulheres(Integer qtdMulheres) {
		this.qtdMulheres = qtdMulheres;
	}

	public Integer getLocalAmbiente() {
		return localAmbiente;
	}

	public void setLocalAmbiente(Integer localAmbiente) {
		this.localAmbiente = localAmbiente;
	}

	public String getCnpjEstabelecimentoDeTerceiros() {
		return cnpjEstabelecimentoDeTerceiros;
	}

	public void setCnpjEstabelecimentoDeTerceiros(String cnpjEstabelecimentoDeTerceiros) {
		this.cnpjEstabelecimentoDeTerceiros = cnpjEstabelecimentoDeTerceiros;
	}
}
