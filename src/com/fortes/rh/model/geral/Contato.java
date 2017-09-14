/* Autor: Igo Coelho
 * Data: 26/05/2006 */
package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.NaoAudita;

public class Contato implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Column(length=5)
	private String ddd;
	@Column(length=10)
	private String foneFixo;
	@Column(length=10)
	private String foneCelular;
	@Column(length=200)
	private String email;
	@Column(length=30)
	private String nomeContato;
	@Column(length=5)
	private String dddCelular;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFoneCelular()
	{
		return foneCelular;
	}

	public void setFoneCelular(String foneCelular)
	{
		this.foneCelular = foneCelular;
	}

	public String getFoneFixo()
	{
		return foneFixo;
	}

	public void setFoneFixo(String foneFixo)
	{
		this.foneFixo = foneFixo;
	}

	public String getDdd()
	{
		return ddd;
	}

	public void setDdd(String ddd)
	{
		this.ddd = ddd;
	}
	@NaoAudita
	public String getFoneFixoFormatado()
	{
		return StringUtil.criarMascaraTelefone(foneFixo);
	}
	@NaoAudita
	public String getFoneCelularFormatado()
	{
		return StringUtil.criarMascaraTelefone(foneCelular);
	}
	@NaoAudita
	public String getFoneContatoFormatado()
	{
		String result = "";
		
		if (StringUtils.isNotBlank(ddd))
			result += "(" + ddd + ") ";
		
		if (StringUtils.isNotBlank(foneFixo))
			result += StringUtil.criarMascaraTelefone(foneFixo);
		
		if (StringUtils.isNotBlank(foneCelular))
		{ 
			if (StringUtils.isNotBlank(foneFixo))
				result += " / " + StringUtil.criarMascaraTelefone(foneCelular);
			else
				result += StringUtil.criarMascaraTelefone(foneCelular);
		}


		return result;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("email", this.email)
				.append("dddFoneFixo", this.ddd)
				.append("foneFixo", this.foneFixo)
				.append("dddCelular", this.dddCelular)
				.append("foneCelular", this.foneCelular).toString();
	}
	
	public String getNomeContato()
	{
		return nomeContato;
	}

	public void setNomeContato(String nomeContato)
	{
		this.nomeContato = nomeContato;
	}

	public String getDddCelular() {
		return dddCelular;
	}

	public void setDddCelular(String dddCelular) {
		this.dddCelular = dddCelular;
	}
}