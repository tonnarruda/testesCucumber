package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.NaoAudita;
@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="testemunha_sequence", allocationSize=1)
public class Testemunha extends AbstractModel implements Serializable {

	@Column(length=100)
    private String nome;
	@Column(length=2)
	private String ddd;
	@Column(length=9)
    private String telefone;
	@Column(length=40)
	private String logradouro;
	@Column(length=10)
	private String numero;
	@Column(length=20)
	private String complemento;
	@Column(length=85)
 	private String bairro;
	@Column(length=100)
	private String municipio;
	@Column(length=2)
	private String uf;
	@Column(length=10)
	private String cep;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	@NaoAudita
	public String getTelefoneFormatado()
	{
		String result = "";
		
		if (StringUtils.isNotBlank(ddd))
			result += "(" + ddd + ") ";
		
		if (StringUtils.isNotBlank(telefone))
			result += StringUtil.criarMascaraTelefone(telefone);

		return result;
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
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
}