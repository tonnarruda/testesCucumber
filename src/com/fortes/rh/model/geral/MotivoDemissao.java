package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="motivodemissao_sequence", allocationSize=1)
public class MotivoDemissao extends AbstractModel implements Serializable
{
	@Transient
	public static Boolean ATIVO = true;
	@Transient
	public static Boolean INATIVO = false;
	
	@Column(length=50)
	private String motivo;

	@ManyToOne
	private Empresa empresa;

	private boolean turnover;
	private boolean reducaoDeQuadro;
	private boolean ativo = ATIVO;
	
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

	public boolean isTurnover() 
	{
		return turnover;
	}
	
	public void setTurnover(boolean turnover) 
	{
		this.turnover = turnover;
	}
	
	public String getDescricaoTurnover(){
		if (turnover) {
			return "Sim";
		} else {
			return "Não";
		}
	}
	
	public void setEmpresaId(Long empresaId)
	{
		if (empresa == null)
			empresa = new Empresa();
		
		empresa.setId(empresaId);
	}

	public boolean isReducaoDeQuadro() {
		return reducaoDeQuadro;
	}

	public void setReducaoDeQuadro(boolean reducaoDeQuadro) {
		this.reducaoDeQuadro = reducaoDeQuadro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}