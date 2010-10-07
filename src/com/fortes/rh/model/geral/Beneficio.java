/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="beneficio_sequence", allocationSize=1)
public class Beneficio extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="beneficio")
	private Collection<HistoricoBeneficio> historicoBeneficios;

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
				.append("nome",	this.nome).append("id", this.getId()).toString();
	}

	public Empresa getEmpresa()
	{
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Collection<HistoricoBeneficio> getHistoricoBeneficios()
	{
		return historicoBeneficios;
	}

	public void setHistoricoBeneficios(Collection<HistoricoBeneficio> historicoBeneficios)
	{
		this.historicoBeneficios = historicoBeneficios;
	}
}