package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="questionario_sequence", allocationSize=1)
public class Questionario extends AbstractModel implements Serializable, Cloneable
{
    @Column(length=100)
    private String titulo;
	private String cabecalho;
	
	@OneToOne(mappedBy="questionario", fetch = FetchType.LAZY)
	private FichaMedica fichaMedica; 
    
	@Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Temporal(TemporalType.DATE)
    private Date dataFim;

    private boolean liberado = false;
    private boolean aplicarPorAspecto = false;
    private boolean anonimo;

    @ManyToOne
    private Empresa empresa;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="questionario")
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="questionario")
	private Collection<Pergunta> perguntas;

	private int tipo;//Avaliação, Entrevista, Pesquisa (TipoQuestionario.java)

	@Transient
	private int quantidadeDeResposta;
	@Transient
	private int totalColab;
	@Transient
	private Turma turma;

	public int getTotalColab()
	{
		return totalColab;
	}

	public void setTotalColab(int totalColab)
	{
		this.totalColab = totalColab;
	}

	//Projections
    public void setProjectionEmpresaId(Long projectionEmpresaId)
    {
    	if(this.empresa == null)
    		this.empresa = new Empresa();

    	this.empresa.setId(projectionEmpresaId);
    }

    public void setProjectionTurmaId(Long projectionTurmaId)
    {
    	if(this.turma == null)
    		this.turma = new Turma();
    	
    	this.turma.setId(projectionTurmaId);
    }
    
    public void setProjectionFichaMedicaRodape(String rodape)
    {
    	if (StringUtils.isNotBlank(rodape))
    	{
		    if (this.fichaMedica == null)
		    	this.fichaMedica = new FichaMedica();
		    	
		    this.fichaMedica.setRodape(rodape);
    	}
    }
    
    public void setProjectionFichaMedicaId(Long fichaMedicaId)
    {
    	if (fichaMedicaId != null)
    	{
	    	if(this.fichaMedica == null)
	    		this.fichaMedica = new FichaMedica();
	    	
	    	this.fichaMedica.setId(fichaMedicaId);
    	}
    }
    
    public void setProjectionEmailRemetente(String projectionEmailRemetente)
    {
    	if(this.empresa == null)
    		this.empresa = new Empresa();

    	this.empresa.setEmailRemetente(projectionEmailRemetente);
    }

    public void setProjectionEmailRespRH(String projectionEmailRespRH)
    {
    	if(this.empresa == null)
    		this.empresa = new Empresa();

    	this.empresa.setEmailRespRH(projectionEmailRespRH);
    }

    public void setEmailRemetente(String emailRemetente)
    {
    	if(this.empresa == null)
    		this.empresa = new Empresa();

    	this.empresa.setEmailRemetente(emailRemetente);
    }

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return colaboradorQuestionarios;
	}
	public void setColaboradorQuestionarios(Collection<ColaboradorQuestionario> colaboradorQuestionarios)
	{
		this.colaboradorQuestionarios = colaboradorQuestionarios;
	}
	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}
	public void setPerguntas(Collection<Pergunta> perguntas)
	{
		this.perguntas = perguntas;
	}

	public String getCabecalho()
	{
		return cabecalho;
	}
	public void setCabecalho(String cabecalho)
	{
		this.cabecalho = cabecalho;
	}

	public boolean isAnonimo()
	{
		return anonimo;
	}
	public void setAnonimo(boolean anonimo)
	{
		this.anonimo = anonimo;
	}
	public boolean isAplicarPorAspecto()
	{
		return aplicarPorAspecto;
	}
	public void setAplicarPorAspecto(boolean aplicarPorAspecto)
	{
		this.aplicarPorAspecto = aplicarPorAspecto;
	}

	public boolean isLiberado()
	{
		return liberado;
	}
	public void setLiberado(boolean liberado)
	{
		this.liberado = liberado;
	}
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar.");
	   }
	}
	public int getTipo()
	{
		return tipo;
	}
	public void setTipo(int tipo)
	{
		this.tipo = tipo;
	}
	public Date getDataFim()
	{
		return dataFim;
	}
	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}
	public Date getDataInicio()
	{
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio)
	{
		this.dataInicio = dataInicio;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public int getQuantidadeDeResposta()
	{
		return quantidadeDeResposta;
	}

	public void setQuantidadeDeResposta(int quantidadeDeResposta)
	{
		this.quantidadeDeResposta = quantidadeDeResposta;
	}

	public boolean verificaTipo(int tipoQuestionario)
	{
		return tipo == tipoQuestionario;
	}
	
	public String getPeriodoFormatado()
	{
		String periodo = "";
		if (dataInicio != null)
			periodo += DateUtil.formataDiaMesAno(dataInicio);
		if (dataFim != null)
			periodo += " - " + DateUtil.formataDiaMesAno(dataFim);

		return periodo;
	}

	public FichaMedica getFichaMedica() {
		return fichaMedica;
	}

	public void setFichaMedica(FichaMedica fichaMedica) {
		this.fichaMedica = fichaMedica;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}