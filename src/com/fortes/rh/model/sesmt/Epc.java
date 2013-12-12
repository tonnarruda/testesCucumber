package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="epc_sequence", allocationSize=1)
public class Epc extends AbstractModel implements Serializable
{
    @Column(length=30)
    private String codigo;
    @Column(length=100)
    private String descricao;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Empresa empresa;
    
    public Epc() {
	}
    
    public Epc(Long id, String codigo, String descricao) {
		setId(id);
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}

	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
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