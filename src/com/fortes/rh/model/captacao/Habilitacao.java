/* Autor: Gustavo Fortes - Francisco Barroso
 * Data: 28/05/2007
 * Requisito:
 */
package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

public class Habilitacao implements Serializable
{
	@Column(length=11)
	private String numeroHab;
	@Column(length=30)
	private String registro;
	@Temporal(TemporalType.DATE)
	private Date emissao;
	@Temporal(TemporalType.DATE)
	private Date vencimento;
	@Column(length=3)
	private String categoria;
	@ManyToOne
	private Estado habUf;

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Date getEmissao() {
		return emissao;
	}
	@NaoAudita
	public String getEmissaoFormatada() {
		return DateUtil.formataDiaMesAno(emissao);
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public String getNumeroHab() {
		return numeroHab;
	}
	public void setNumeroHab(String numeroHab) {
		this.numeroHab = numeroHab;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public Date getVencimento() {
		return vencimento;
	}
	@NaoAudita
	public String getVencimentoFormatada() {
		return DateUtil.formataDiaMesAno(vencimento);
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public Estado getHabUf()
	{
		return habUf;
	}

	public void setHabUf(Estado habUf)
	{
		this.habUf = habUf;
	}
	
	@NaoAudita
	public void setHabUfSigla(String sigla){
		if(this.habUf == null)
			habUf = new Estado();
		
		this.habUf.setSigla(sigla);
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("vencimento", this.vencimento).append("categoria",
						this.categoria).append("emissao", this.emissao).append(
						"numeroHab", this.numeroHab).append("registro",
						this.registro).toString();
	}

}