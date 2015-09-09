package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="epi_sequence", allocationSize=1)
public class Epi extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String nome;
    @Column(length=10)
    private String codigo;
    private String descricao;
    @Column(length=100)
    private String fabricante;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private TipoEPI tipoEPI;

    private boolean fardamento;
    private boolean ativo = true;
    
    @Transient
    private boolean relacionadoAoColaborador;

    @Transient
    private EpiHistorico epiHistorico;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="epi")
    private Collection<EpiHistorico> epiHistoricos;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="epi")
    private Collection<SolicitacaoEpiItem> solicitacaoEpiItems;
    
    @Transient
    private Long tipoEPIId;

    public Epi()
    { }

    public Epi(Long id, String nome, Boolean ativo, String fabricante, String CA, Date vencimento)
    {
    	setId(id);
    	this.nome = nome;
    	this.ativo = ativo;
    	this.fabricante = fabricante;
    	this.epiHistorico = new EpiHistorico();
    	this.epiHistorico.setCA(CA);
    	this.epiHistorico.setVencimentoCA(vencimento);
    }
    public Epi(String nome, String fabricante)
    {
    	this.nome = nome;
    	this.fabricante = fabricante;
    }
    public Epi(Long id, String nome)
    {
    	this.setId(id);
    	this.nome = nome;
    }

    public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}
    public void setTipoEPIIdProjection(Long tipoEPIIdProjection)
    {
    	if (this.tipoEPI == null)
    		this.tipoEPI = new TipoEPI();
    	this.tipoEPI.setId(tipoEPIIdProjection);
    }
    public TipoEPI getTipoEPI()
	{
		return tipoEPI;
	}
	public void setTipoEPI(TipoEPI tipoEPI)
	{
		this.tipoEPI = tipoEPI;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public String getFabricante()
	{
		return fabricante;
	}
	public void setFabricante(String fabricante)
	{
		this.fabricante = fabricante;
	}
	public String getNomeInativo()
	{
		if(!ativo)
			return getNome() + " (inativo)";
		else
			return getNome();
	}

	public String getNomeFichaEpi()
	{
		if(nome == null)
			return "";
		if(nome.length() > 45)
			return nome.substring(0, 44) + "...";
		
		return nome;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
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
	public EpiHistorico getEpiHistorico()
	{
		return epiHistorico;
	}
	public void setEpiHistorico(EpiHistorico epiHistorico)
	{
		this.epiHistorico = epiHistorico;
	}
	public Collection<EpiHistorico> getEpiHistoricos()
	{
		return epiHistoricos;
	}
	public void setEpiHistoricos(Collection<EpiHistorico> epiHistoricos)
	{
		this.epiHistoricos = epiHistoricos;
	}
	public boolean getFardamento()
	{
		return fardamento;
	}
	public void setFardamento(boolean fardamento)
	{
		this.fardamento = fardamento;
	}
	public Collection<SolicitacaoEpiItem> getSolicitacaoEpiItems()
	{
		return solicitacaoEpiItems;
	}
	public void setSolicitacaoEpiItems(Collection<SolicitacaoEpiItem> solicitacaoEpiItems)
	{
		this.solicitacaoEpiItems = solicitacaoEpiItems;
	}

	public boolean isRelacionadoAoColaborador() {
		return relacionadoAoColaborador;
	}

	public void setRelacionadoAoColaborador(boolean relacionadoAoColaborador) {
		this.relacionadoAoColaborador = relacionadoAoColaborador;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTipoEPIId() {
		return tipoEPIId;
	}

	public void setTipoEPIId(Long tipoEPIId) {
		this.tipoEPIId = tipoEPIId;
	}
}