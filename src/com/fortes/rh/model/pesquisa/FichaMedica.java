package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="fichamedica_sequence", allocationSize=1)
public class FichaMedica extends AbstractModel implements Serializable, Cloneable
{
	@OneToOne
	private Questionario questionario;
	private boolean ativa = true;
	private String rodape;

	@Transient
	private Date respondidaEm;

	public FichaMedica()
	{
	}

	// Construtores para HQL
	public FichaMedica(Long id, String rodape, int questionarioQuantidadeDeResposta, boolean ativa, Long questionarioId, String questionarioTitulo, boolean questionarioLiberado, int questionarioTipo, boolean questionarioAplicarPorAspecto, Long empresaId)
	{
    	this.setId(id);
		this.setAtiva(ativa);
		this.setRodape(rodape);

		inicializaQuestionarioComEmpresa();

		this.questionario.setId(questionarioId);
		this.questionario.setTitulo(questionarioTitulo);
		this.questionario.setLiberado(questionarioLiberado);
		this.questionario.setTipo(questionarioTipo);
		this.questionario.setAplicarPorAspecto(questionarioAplicarPorAspecto);
		this.questionario.setQuantidadeDeResposta(questionarioQuantidadeDeResposta);
    	this.questionario.getEmpresa().setId(empresaId);
	}

	public FichaMedica(String questionarioTitulo, Date respondidaEm)
	{
		setQuestionarioTitulo(questionarioTitulo);
		this.respondidaEm = respondidaEm;
	}

	private void setQuestionarioTitulo(String questionarioTitulo)
	{
		inicializaQuestionario();
		questionario.setTitulo(questionarioTitulo);
	}

    //Projections
    public void setProjectionQuestionarioId(Long projectionQuestionarioId)
    {
    	inicializaQuestionario();

    	this.questionario.setId(projectionQuestionarioId);
    }

    public void setProjectionQuestionarioCabecalho(String projectionQuestionarioCabecalho)
    {
    	inicializaQuestionario();

    	this.questionario.setCabecalho(projectionQuestionarioCabecalho);
    }

    public void setProjectionQuestionarioAnonimo(boolean projectionQuestionarioAnonimo)
    {
    	inicializaQuestionario();

    	this.questionario.setAnonimo(projectionQuestionarioAnonimo);
    }

    public void setProjectionQuestionarioLiberado(boolean projectionQuestionarioLiberado)
    {
    	inicializaQuestionario();

    	this.questionario.setLiberado(projectionQuestionarioLiberado);
    }

    public void setProjectionQuestionarioTitulo(String projectionQuestionarioTitulo)
    {
    	inicializaQuestionario();

    	this.questionario.setTitulo(projectionQuestionarioTitulo);
    }

    public void setProjectionQuestionarioTipo(int projectionQuestionarioTipo)
    {
    	inicializaQuestionario();

    	this.questionario.setTipo(projectionQuestionarioTipo);
    }

    public void setProjectionQuestionarioAplicarPorAspecto(boolean projectionQuestionarioAplicarPorAspecto)
    {
    	inicializaQuestionario();

    	this.questionario.setAplicarPorAspecto(projectionQuestionarioAplicarPorAspecto);
    }

    public void setProjectionQuestionarioDataInicio(Date projectionQuestionarioDataInicio)
    {
    	inicializaQuestionario();

    	this.questionario.setDataInicio(projectionQuestionarioDataInicio);
    }

    public void setProjectionQuestionarioDataFim(Date projectionQuestionarioDataFim )
    {
    	inicializaQuestionario();

    	this.questionario.setDataFim(projectionQuestionarioDataFim);
    }

    public void setProjectionQuestionarioEmpresaId(Long projectionQuestionarioEmpresaId)
    {
    	inicializaQuestionarioComEmpresa();

    	this.questionario.getEmpresa().setId(projectionQuestionarioEmpresaId);
    }

    public void setProjectionEmailRemetente(String projectionEmailRemetente)
    {
    	inicializaQuestionarioComEmpresa();

    	this.questionario.getEmpresa().setEmailRemetente(projectionEmailRemetente);
    }

    public void setProjectionEmailRespRH(String projectionEmailRespRH)
    {
    	inicializaQuestionarioComEmpresa();

    	this.questionario.getEmpresa().setEmailRespRH(projectionEmailRespRH);
    }
    
	private void inicializaQuestionario()
	{
		if(this.questionario == null)
    		this.questionario = new Questionario();
	}
	
    private void inicializaQuestionarioComEmpresa()
    {
    	inicializaQuestionario();
    	
    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());
    }

	@Override
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

    public String toString()
    {
        ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        string.append("id", super.getId());

        return string.toString();
    }

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public boolean isAtiva()
	{
		return ativa;
	}

	public void setAtiva(boolean ativa)
	{
		this.ativa = ativa;
	}

	public String getRodape()
	{
		return rodape;
	}

	public void setRodape(String rodape)
	{
		this.rodape = rodape;
	}

	public String getRespondidaEm()
	{
		String dataFormatada = "-";
		if (respondidaEm != null)
		{
			dataFormatada = DateUtil.formataDiaMesAno(respondidaEm);
		}
		return dataFormatada;
	}
}