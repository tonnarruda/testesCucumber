package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="cliente_sequence", allocationSize=1)
public class Cliente extends AbstractModel implements Serializable
{

	private String nome;
	private String enderecoInterno;
	private String enderecoExterno;
	private String senhaFortes;
	private String versao;
	@Temporal(TemporalType.DATE)
	private Date dataAtualizacao;
	@Lob
	private String modulosAdquiridos;
	@Lob
	private String contatoGeral;
	@Lob
	private String contatoTI;
	@Lob
	private String observacao;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEnderecoInterno() {
		return enderecoInterno;
	}
	public void setEnderecoInterno(String enderecoInterno) {
		this.enderecoInterno = enderecoInterno;
	}
	public String getEnderecoExterno() {
		return enderecoExterno;
	}
	public void setEnderecoExterno(String enderecoExterno) {
		this.enderecoExterno = enderecoExterno;
	}
	public String getSenhaFortes() {
		return senhaFortes;
	}
	public void setSenhaFortes(String senhaFortes) {
		this.senhaFortes = senhaFortes;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public String getContatoGeral() {
		return contatoGeral;
	}
	public void setContatoGeral(String contatoGeral) {
		this.contatoGeral = contatoGeral;
	}
	public String getContatoTI() {
		return contatoTI;
	}
	public void setContatoTI(String contatoTI) {
		this.contatoTI = contatoTI;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getModulosAdquiridos() {
		return modulosAdquiridos;
	}
	public void setModulosAdquiridos(String modulosAdquiridos) {
		this.modulosAdquiridos = modulosAdquiridos;
	}
	
	

}
