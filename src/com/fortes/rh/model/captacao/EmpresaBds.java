/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020 */
package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@Entity
@SequenceGenerator(name="sequence", sequenceName="empresabds_sequence", allocationSize=1)
public class EmpresaBds extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@Column(length=100)
	private String contato;
	@Column(length=10)
	private String fone;
	@Column(length=120)
 	private String email;
	@Column(length=5)
 	private String ddd;
 	@ManyToOne
 	private Empresa empresa;

	//Projection
	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();

		this.empresa.setId(projectionEmpresaId);
	}
	
	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDdd()
	{
		return ddd;
	}

	public void setDdd(String ddd)
	{
		this.ddd = ddd;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nome", this.nome).append("contato", this.contato)
				.append("id", this.getId()).append("email", this.email).append(
						"fone", this.fone).append("ddd", this.ddd).toString();
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

}