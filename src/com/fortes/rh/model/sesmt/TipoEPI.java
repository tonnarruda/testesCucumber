package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="tipoepi_sequence", allocationSize=1)
public class TipoEPI extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String nome;
    @Column(length=6)
    private String codigo;
    @ManyToOne
    private Empresa empresa;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoEPI")
	private Collection<TipoTamanhoEPI> tamanhoEPIs;

    public TipoEPI() 
    {
    	
	}
    
	public TipoEPI(String codigo, String nome) 
	{
		this.codigo = codigo;
		this.nome = nome;
	}
    
	public String getCodigo() 
	{
		return codigo;
	}

	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public Empresa getEmpresa()
	{
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}

	public Collection<TipoTamanhoEPI> getTamanhoEPIs() {
		return tamanhoEPIs;
	}

	public void setTamanhoEPIs(Collection<TipoTamanhoEPI> tamanhoEPIs) {
		this.tamanhoEPIs = tamanhoEPIs;
	}
}