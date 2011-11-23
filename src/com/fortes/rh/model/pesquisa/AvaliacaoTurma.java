package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="avaliacaoturma_sequence", allocationSize=1)
public class AvaliacaoTurma extends AbstractModel implements Serializable, Cloneable
{
	@OneToOne
	private Questionario questionario;
	private boolean ativa = true;

	@Transient
	private Turma turma ;
	
	
	public AvaliacaoTurma()
	{
	}

	// Construtores para HQL
	public AvaliacaoTurma(Long id, int questionarioQuantidadeDeResposta, boolean ativa, Long questionarioId, String questionarioTitulo, boolean questionarioLiberado, int questionarioTipo, boolean questionarioAplicarPorAspecto, Long empresaId)
	{
    	this.setId(id);
		this.setAtiva(ativa);

		if(this.questionario == null)
			this.questionario = new Questionario();

		if(this.questionario.getEmpresa() == null)
			this.questionario.setEmpresa(new Empresa());

		this.questionario.setId(questionarioId);
		this.questionario.setTitulo(questionarioTitulo);
		this.questionario.setLiberado(questionarioLiberado);
		this.questionario.setTipo(questionarioTipo);
		this.questionario.setAplicarPorAspecto(questionarioAplicarPorAspecto);
		this.questionario.setQuantidadeDeResposta(questionarioQuantidadeDeResposta);
    	this.questionario.getEmpresa().setId(empresaId);
	}

    //Projections
    public void setProjectionTurmaId(Long projectionTurmaId)
    {
    	if(this.turma == null)
    		this.turma = new Turma();

    	this.turma.setId(projectionTurmaId);
    }

    public void setProjectionQuestionarioId(Long projectionQuestionarioId)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();
    	
    	this.questionario.setId(projectionQuestionarioId);
    }
    
    public void setProjectionQuestionarioCabecalho(String projectionQuestionarioCabecalho)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setCabecalho(projectionQuestionarioCabecalho);
    }

    public void setProjectionQuestionarioAnonimo(boolean projectionQuestionarioAnonimo)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setAnonimo(projectionQuestionarioAnonimo);
    }

    public void setProjectionQuestionarioLiberado(boolean projectionQuestionarioLiberado)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setLiberado(projectionQuestionarioLiberado);
    }

    public void setProjectionQuestionarioTitulo(String projectionQuestionarioTitulo)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setTitulo(projectionQuestionarioTitulo);
    }

    public void setProjectionQuestionarioTipo(int projectionQuestionarioTipo)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setTipo(projectionQuestionarioTipo);
    }

    public void setProjectionQuestionarioAplicarPorAspecto(boolean projectionQuestionarioAplicarPorAspecto)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setAplicarPorAspecto(projectionQuestionarioAplicarPorAspecto);
    }

    public void setProjectionQuestionarioDataInicio(Date projectionQuestionarioDataInicio)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setDataInicio(projectionQuestionarioDataInicio);
    }

    public void setProjectionQuestionarioDataFim(Date projectionQuestionarioDataFim )
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setDataFim(projectionQuestionarioDataFim);
    }

    public void setProjectionQuestionarioEmpresaId(Long projectionQuestionarioEmpresaId)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());

    	this.questionario.getEmpresa().setId(projectionQuestionarioEmpresaId);
    }

    public void setProjectionEmailRemetente(String projectionEmailRemetente)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());

    	this.questionario.getEmpresa().setEmailRemetente(projectionEmailRemetente);
    }

    public void setProjectionEmailRespRH(String projectionEmailRespRH)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	if(this.questionario.getEmpresa() == null)
    		this.questionario.setEmpresa(new Empresa());

    	this.questionario.getEmpresa().setEmailRespRH(projectionEmailRespRH);
    }
    
    public String getQuestionarioTitulo()
    {
    	if (this.questionario == null)
    		this.questionario = new Questionario();
    	
    	return this.questionario.getTitulo();
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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}