package com.fortes.rh.model.avaliacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="avaliacaoDesempenho_sequence", allocationSize=1)
public class AvaliacaoDesempenho extends AbstractModel implements Serializable, Cloneable 
{
	@ManyToOne
	private Avaliacao avaliacao;
	
	@Temporal(TemporalType.DATE)
    private Date inicio;
    @Temporal(TemporalType.DATE)
    private Date fim;
    
    @Column(length=100)
    private String titulo;
    
    private boolean anonima;
    private boolean permiteAutoAvaliacao;
    private boolean liberada;
    private boolean exibirPerformanceProfissional;
	private boolean exibeResultadoAutoAvaliacao;
	
    @ManyToOne
	private Empresa empresa;
    
    public AvaliacaoDesempenho() {}
    
    public AvaliacaoDesempenho(String titulo)
	{
		this.titulo = titulo;
	}

    @NaoAudita
    public String getPeriodoFormatado()
    {
    	String periodo = "";
    	if (inicio != null)
    		periodo = DateUtil.formataDiaMesAno(inicio);
    	if (fim != null)
			periodo += " - " + DateUtil.formataDiaMesAno(fim);
    	
    	return periodo;
    }
    
	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto.");
	   }
	}
    
    //Projection
    public void setProjectionAvaliacaoId(Long avaliacaoId)
    {
    	if (avaliacao == null)
    		avaliacao = new Avaliacao();
    	
    	avaliacao.setId(avaliacaoId);
    }
    
    public void setProjectionAvaliacaoTitulo(String avaliacaoTitulo)
    {
    	if (avaliacao == null)
    		avaliacao = new Avaliacao();
    	
    	avaliacao.setTitulo(avaliacaoTitulo);
    }
    
    public void setProjectionAvaliacaoAvaliarCompetenciasCargo(boolean avaliarCompetenciasCargo)
    {
    	if (avaliacao == null)
    		avaliacao = new Avaliacao();
    	
    	avaliacao.setAvaliarCompetenciasCargo(avaliarCompetenciasCargo);
    }
    
	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public String getTituloComEmpresa() {
		if(this.titulo != null ){
			String titulo = this.titulo;
			
			if(this.empresa.getNome() != null)
				titulo += " (" + this.empresa.getNome() + ")";
			return titulo;
		}
		else return "";
	}
    
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isAnonima() {
		return anonima;
	}
	public void setAnonima(boolean anonima) {
		this.anonima = anonima;
	}

	public boolean isPermiteAutoAvaliacao() {
		return permiteAutoAvaliacao;
	}

	public void setPermiteAutoAvaliacao(boolean permiteAutoAvaliacao) {
		this.permiteAutoAvaliacao = permiteAutoAvaliacao;
	}

	public boolean isLiberada() {
		return liberada;
	}

	public void setLiberada(boolean liberada) {
		this.liberada = liberada;
	}

	public boolean isExibirPerformanceProfissional()
	{
		return exibirPerformanceProfissional;
	}

	public void setExibirPerformanceProfissional(boolean exibirPerformanceProfissional)
	{
		this.exibirPerformanceProfissional = exibirPerformanceProfissional;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaId) {
		inicializaEmpresa();
		this.empresa.setId(empresaId);
	}
	
    public void setEmpresaNome(String empresaNome)
   	{
   		inicializaEmpresa();
   		empresa.setNome(empresaNome);
   	}

	private void inicializaEmpresa() {
		if(this.empresa == null)
			this.empresa = new Empresa();
	}

	public boolean isExibeResultadoAutoAvaliacao() {
		return exibeResultadoAutoAvaliacao;
	}

	public void setExibeResultadoAutoAvaliacao(boolean exibeResultadoAutoAvaliacao) {
		this.exibeResultadoAutoAvaliacao = exibeResultadoAutoAvaliacao;
	}
}
