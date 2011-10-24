/* Autor: Igo Coelho
 * Data: 19/06/2006
 * Requisito: RFA023 */
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
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "etapaseletiva_sequence", allocationSize = 1)
public class EtapaSeletiva extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	private int ordem;
	@ManyToOne
	private Empresa empresa;
    
	public  EtapaSeletiva() {
		
	}

	public  EtapaSeletiva(Long id, String nome) {
		this.nome = nome;
		setId(id);
	}
	
	public void setEmpresaId(Long empresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();

		this.empresa.setId(empresaId);
	}

	@ChaveDaAuditoria
	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public int getOrdem()
	{
		return ordem;
	}

	public void setOrdem(int ordem)
	{
		this.ordem = ordem;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nome", this.nome).append("id", this.getId()).append(
						"ordem", this.ordem).toString();
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