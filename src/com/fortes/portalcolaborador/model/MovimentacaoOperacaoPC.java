package com.fortes.portalcolaborador.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.portalcolaborador.business.operacao.Operacao;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="movimentacaooperacaopc_sequence", allocationSize=1)
public class MovimentacaoOperacaoPC extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String operacao;
	@Lob
	private String parametros;
	
	public Operacao getOperacao()
	{
		try {
			return (Operacao) Class.forName(operacao).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setOperacao(Class<? extends Operacao> operacao)
	{
		this.operacao = operacao.getName();
	}
	
	public String getParametros()
	{
		return parametros;
	}
	
	public void setParametros(String parametros)
	{
		this.parametros = parametros;
	}
}
