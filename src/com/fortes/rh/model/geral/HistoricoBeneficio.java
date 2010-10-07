package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicobeneficio_sequence", allocationSize=1)
public class HistoricoBeneficio extends AbstractModel implements Serializable
{
    @ManyToOne
    private Beneficio beneficio;

	@Temporal(TemporalType.DATE)
	private Date data;
	private Double valor;
	private Double paraColaborador;
	private Double paraDependenteDireto;
	private Double paraDependenteIndireto;

    public void setProjectionBeneficioId(Long beneficioId)
    {
    	if (this.beneficio == null)
    		this.beneficio = new Beneficio();

    	this.beneficio.setId(beneficioId);
    }

    public void setProjectionBeneficioNome(String beneficioNome)
    {
    	if (this.beneficio == null)
    		this.beneficio = new Beneficio();

    	this.beneficio.setNome(beneficioNome);
    }

    public void setProjectionEmpresaId(Long empresaId)
    {
    	if (this.beneficio == null)
    		this.beneficio = new Beneficio();

    	if(this.beneficio.getEmpresa() == null)
    		this.beneficio.setEmpresa(new Empresa());

    	this.beneficio.getEmpresa().setId(empresaId);
    }

	public Beneficio getBeneficio()
	{
		return beneficio;
	}
	public void setBeneficio(Beneficio beneficio)
	{
		this.beneficio = beneficio;
	}
	public Double getParaColaborador()
	{
		return paraColaborador;
	}
	public void setParaColaborador(Double paraColaborador)
	{
		this.paraColaborador = paraColaborador;
	}
	public Double getParaDependenteDireto()
	{
		return paraDependenteDireto;
	}
	public void setParaDependenteDireto(Double paraDependenteDireto)
	{
		this.paraDependenteDireto = paraDependenteDireto;
	}
	public Double getParaDependenteIndireto()
	{
		return paraDependenteIndireto;
	}
	public void setParaDependenteIndireto(Double paraDependenteIndireto)
	{
		this.paraDependenteIndireto = paraDependenteIndireto;
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
}