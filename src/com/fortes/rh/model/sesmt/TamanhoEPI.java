package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@Table(name="tamanhoepi")
@SequenceGenerator(name="sequence", sequenceName="tamanhoepi_sequence", allocationSize=1)
public class TamanhoEPI extends AbstractModel implements Serializable
{
    @Column(length=30)
    private String descricao;
    
    public TamanhoEPI() {
    	
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}