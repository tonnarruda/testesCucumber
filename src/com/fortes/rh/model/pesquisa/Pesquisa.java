package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="pesquisa_sequence", allocationSize=1)
public class Pesquisa extends AbstractModel implements Serializable, Cloneable
{
    @OneToOne
    private Questionario questionario;
    private boolean exibirPerformanceProfissional;

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

	private void inicializaQuestionario() {
		if(this.questionario == null)
    		this.questionario = new Questionario();
	}

    public void setProjectionQuestionarioDataFim(Date projectionQuestionarioDataFim )
    {
    	inicializaQuestionario();
    	
    	this.questionario.setDataFim(projectionQuestionarioDataFim);
    }
    
    public void setProjectionQuestionarioEmpresaId(Long projectionQuestionarioEmpresaId)
    {
    	inicializaQuestionario();
    	
    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());
    	
    	this.questionario.getEmpresa().setId(projectionQuestionarioEmpresaId);
    }
    
    public void setProjectionEmailRemetente(String projectionEmailRemetente)
    {
    	inicializaQuestionario();
    	
    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());

    	this.questionario.getEmpresa().setEmailRemetente(projectionEmailRemetente);
    }

    public void setProjectionEmailRespRH(String projectionEmailRespRH)
    {
    	inicializaQuestionario();
    	
    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());

    	this.questionario.getEmpresa().setEmailRespRH(projectionEmailRespRH);
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

	public boolean isExibirPerformanceProfissional() {
		return exibirPerformanceProfissional;
	}

	public void setExibirPerformanceProfissional(boolean exibirPerformanceProfissional) {
		this.exibirPerformanceProfissional = exibirPerformanceProfissional;
	}
}