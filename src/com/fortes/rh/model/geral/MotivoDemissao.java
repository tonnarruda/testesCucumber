package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@Entity
@SequenceGenerator(name="sequence", sequenceName="motivodemissao_sequence", allocationSize=1)
public class MotivoDemissao extends AbstractModel implements Serializable
{
	@Column(length=50)
	private String motivo;

	@ManyToOne
	private Empresa empresa;

	@ChaveDaAuditoria
    public String getMotivo()
	{
		return motivo;
	}
	public String getMotivoFormatado()
	{
		return motivo == null ? "[não informado]" : motivo;
	}
	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaId)
	{
		if (empresa == null)
			empresa = new Empresa();
		
		empresa.setId(empresaId);
	}
}