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
    
    public void setProjectionAvaliacaoEmpresaId(Long empresaId)
    {
    	if (avaliacao == null)
    		avaliacao = new Avaliacao();
    	
    	if (avaliacao.getEmpresa() == null)
    		avaliacao.setEmpresa(new Empresa());
    	
    	avaliacao.getEmpresa().setId(empresaId);
    }
	
    public void setProjectionAvaliacaoEmpresaNome(String empresaNome)
	{
		if (avaliacao == null)
			avaliacao = new Avaliacao();
		
		if (avaliacao.getEmpresa() == null)
			avaliacao.setEmpresa(new Empresa());
		
		avaliacao.getEmpresa().setNome(empresaNome);
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
		return titulo;
	}

	public String getTituloComEmpresa() {
		String titulo = this.titulo;
		if (this.avaliacao != null && this.avaliacao.getEmpresa() != null)
			titulo += " (" + this.avaliacao.getEmpresa().getNome() + ")";
			
		return titulo;
	}
    
    public void setEmpresaNomeProjection(String empresaNome)
    {
    	if(this.avaliacao == null)
    		avaliacao= new Avaliacao();
    	
    	if(avaliacao.getEmpresa() == null)
    		avaliacao.setEmpresa(new Empresa());
    	
    	avaliacao.getEmpresa().setNome(empresaNome);
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
}
