package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="areaformacao_sequence", allocationSize=1)
public class AreaFormacao extends AbstractModel implements Serializable
{
    @Column(length=100)
    @ChaveDaAuditoria
    private String nome;

	public AreaFormacao()
	{

	}

	public AreaFormacao(Long id, String nome)
	{
		this.setId(id);
		this.nome = nome;
	}

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nome", this.nome).append("id", this.getId()).toString();
	}
}