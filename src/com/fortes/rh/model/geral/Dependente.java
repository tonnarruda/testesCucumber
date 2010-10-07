/* Autor: Igo Coelho
 * Data: 16/06/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="dependente_sequence", allocationSize=1)
public class Dependente extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@ManyToOne
	private Colaborador colaborador;
	@Column(length=12)
	private String seqAc;

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getSeqAc()
	{
		return seqAc;
	}

	public void setSeqAc(String seqAc)
	{
		this.seqAc = seqAc;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nome", this.nome).append("id", this.getId()).append(
						"seqAc", this.seqAc).append("colaborador",
						this.colaborador).append("dataNascimento",
						this.dataNascimento).toString();
	}

}