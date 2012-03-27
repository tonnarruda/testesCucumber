package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicofuncao_sequence", allocationSize=1)
public class HistoricoFuncao extends AbstractModel implements Serializable
{
    @Temporal(TemporalType.DATE)
    private Date data;
	@Transient
	private Date dataProximoHistorico;
    @Lob
    private String descricao;
    @ManyToOne
    private Funcao funcao;

    @ManyToMany(fetch=FetchType.EAGER)
    private Collection<Exame> exames;

    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<Epi> epis;
    
    @OneToMany(mappedBy="historicoFuncao", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Collection<RiscoFuncao> riscoFuncaos;

    @Transient
    private Epi epi;

	public HistoricoFuncao()
    {

    }

    public HistoricoFuncao(Date data, String descricao, Funcao funcao)
    {
    	this.data = data;
    	this.descricao = descricao;
    	this.funcao = funcao;
    }
    
    public HistoricoFuncao(Long funcaoId, String funcaoNome, Long epiId, String epiNome)
    {
    	if(this.funcao == null)
    		this.funcao = new Funcao();
    	
    	this.getFuncao().setId(funcaoId);
    	this.getFuncao().setNome(funcaoNome);
    	
    	if(this.epi == null)
    		this.epi = new Epi();
    	
    	this.epi.setId(epiId);
    	this.epi.setNome(epiNome);
    }
    
    //Projection
    public void setProjectionFuncaoId(Long funcaoId)
    {
    	if(this.funcao == null)
    		this.funcao = new Funcao();
    	
    	this.funcao.setId(funcaoId);
    }
    public void setProjectionFuncaoNome(String funcaoNome)
    {
    	if(this.funcao == null)
    		this.funcao = new Funcao();
    	
    	this.funcao.setNome(funcaoNome);
    }

	public Date getData()
	{
		return data;
	}
	
	public void setData(Date data)
	{
		this.data = data;
	}
	
	public Funcao getFuncao()
	{
		return funcao;
	}
	
	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
	
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	public Date getDataProximoHistorico()
	{
		return dataProximoHistorico;
	}
	
	public void setDataProximoHistorico(Date dataProximoHistorico)
	{
		this.dataProximoHistorico = dataProximoHistorico;
	}

	public String getPeriodo()
	{
		return DateUtil.formataDiaMesAno(this.data) + " a " + (this.dataProximoHistorico != null ? DateUtil.formataDiaMesAno(this.dataProximoHistorico) : "__/__/___");
	}
	
	public Collection<Exame> getExames()
	{
		return exames;
	}

	public void setExames(Collection<Exame> exames)
	{
		this.exames = exames;
	}

    public Collection<Epi> getEpis()
	{
		return epis;
	}

	public void setEpis(Collection<Epi> epis)
	{
		this.epis = epis;
	}

	public Epi getEpi()
	{
		return epi;
	}
	
	public String getDescricaoEpis()
	{
		StringBuilder descricao = new StringBuilder("");
		int cont = 1;
		if(this.epis != null && !this.epis.isEmpty())
		{
			for (Epi epi : this.epis)
			{
				descricao.append("- " + epi.getNome());
				if(cont != epis.size())
					descricao.append("\n");
				
				cont++;
			}
		}
		
		return descricao.toString();
	}

	public Collection<RiscoFuncao> getRiscoFuncaos() {
		return riscoFuncaos;
	}

	public void setRiscoFuncaos(Collection<RiscoFuncao> riscoFuncaos) {
		this.riscoFuncaos = riscoFuncaos;
	}
}