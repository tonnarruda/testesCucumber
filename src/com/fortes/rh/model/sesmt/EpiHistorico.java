package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="epihistorico_sequence", allocationSize=1)
public class EpiHistorico extends AbstractModel implements Serializable
{
    @Column(length=20)
    private String atenuacao;
    @Temporal(TemporalType.DATE)
    private Date vencimentoCA;
    private Integer validadeUso;
    @Column(length=20)
    private String CA;

    @ManyToOne
    private Epi epi;

    @Temporal(TemporalType.DATE)
    private Date data;
    
    public EpiHistorico() {}

    public EpiHistorico(Long id, String atenuacao, Date vencimentoCA, Integer validadeUso, String cA, Long epiId, Date data) 
    {
    	this.setId(id);
		this.atenuacao = atenuacao;
		this.vencimentoCA = vencimentoCA;
		this.validadeUso = validadeUso;
		this.CA = cA;
		this.data = data;
		this.setProjectionEpiId(epiId);
	}

	//Projection
    public void setProjectionEpiId(Long epiId)
	{
		if(this.epi == null)
			this.epi = new Epi();
		
		this.epi.setId(epiId);
	}
    public void setProjectionEpiNome(String epiNome)
    {
    	if(this.epi == null)
    		this.epi = new Epi();
    	
    	this.epi.setNome(epiNome);
    }
    
    public String getDescricao()
    {
    	String caDesc = "sem CA";
    	String validadeTmp = ""+validadeUso;
    	String validadeDesc = "sem validade de uso";
    	if(StringUtils.isNotEmpty(CA))
    		caDesc = CA;
    	if(StringUtils.isNotEmpty(validadeTmp))
    		validadeDesc = validadeTmp;
    	
    	return DateUtil.formataDiaMesAno(data) + " - " + caDesc + " - " + validadeDesc;
    }

	public String getAtenuacao()
	{
		return atenuacao;
	}
	public void setAtenuacao(String atenuacao)
	{
		this.atenuacao = atenuacao;
	}
	public String getCA()
	{
		return CA;
	}
	public void setCA(String ca)
	{
		CA = ca;
	}
	public Epi getEpi()
	{
		return epi;
	}
	public void setEpi(Epi epi)
	{
		this.epi = epi;
	}
	public Integer getValidadeUso()
	{
		return validadeUso;
	}
	public void setValidadeUso(Integer validadeUso)
	{
		this.validadeUso = validadeUso;
	}
	public Date getVencimentoCA()
	{
		return vencimentoCA;
	}
	public void setVencimentoCA(Date vencimentoCA)
	{
		this.vencimentoCA = vencimentoCA;
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