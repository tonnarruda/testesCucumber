package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="motivosolicitacao_sequence", allocationSize=1)
public class MotivoSolicitacao extends AbstractModel implements Serializable
{
    @Column(length=100)
    @ChaveDaAuditoria
    private String descricao;
    
    private boolean turnover;
    private boolean considerarQtdColaboradoresPorCargo;
    
    @Transient
    private int qtdContratados;

	public String getDescricao()
	{
		return descricao;
	}
	
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	public boolean isTurnover() 
	{
		return turnover;
	}
	
	public String getTurnoverFormatado() 
	{
		return turnover ? "Sim" : "Não";
	}
	
	public void setTurnover(boolean turnover) 
	{
		this.turnover = turnover;
	}

	public int getQtdContratados() {
		return qtdContratados;
	}

	public void setQtdContratados(int qtdContratados) {
		this.qtdContratados = qtdContratados;
	}

	public boolean isConsiderarQtdColaboradoresPorCargo() {
		return considerarQtdColaboradoresPorCargo;
	}

	public void setConsiderarQtdColaboradoresPorCargo(
			boolean considerarQtdColaboradoresPorCargo) {
		this.considerarQtdColaboradoresPorCargo = considerarQtdColaboradoresPorCargo;
	}
}