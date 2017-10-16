package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="fatorDeRisco_sequence", allocationSize=1)
public class FatorDeRisco extends AbstractModel implements Serializable{
	
	private String codigo;
	private String descricao;
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricaoComCodigo(){
		if(StringUtils.isNotBlank(getCodigo()) && StringUtils.isNotBlank(getDescricao()))
			return this.getCodigo() + " - " + getDescricao();
		return "";
	}
}