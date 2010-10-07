package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="motivosolicitacao_sequence", allocationSize=1)
public class MotivoSolicitacao extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String descricao;

	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
}