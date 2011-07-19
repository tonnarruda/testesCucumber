package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoLimiteColaborador_sequence", allocationSize=1)
public class ConfiguracaoLimiteColaborador extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;
	@OneToOne
	private AreaOrganizacional areaOrganizacional;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}
	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}
	
	public void setProjectionAreaOrganizacionalNome(String areaOrganizacionalNome)
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		
		areaOrganizacional.setNome(areaOrganizacionalNome);
	}
	
}
