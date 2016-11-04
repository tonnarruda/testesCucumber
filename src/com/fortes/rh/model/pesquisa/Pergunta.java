package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="pergunta_sequence", allocationSize=1)
public class Pergunta extends AbstractModel implements Serializable, Cloneable
{
    private Integer ordem;
    @Lob
    private String texto;
    private boolean comentario;
    @Lob
    private String textoComentario;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="pergunta")
    private Collection<Resposta> respostas;
    private Integer tipo;
    @ManyToOne
    private Aspecto aspecto;
    @ManyToOne
    private Questionario questionario;
    
    private Integer notaMaxima;
    private Integer notaMinima;
    
    private Integer peso; // Peso p/ Avaliação do período de Experiência
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Avaliacao avaliacao;

    @Transient
    private Pesquisa pesquisa;
    @Transient
    private String media; //Impressao do resultado do questionario
    
    @Transient
    private Collection<ColaboradorResposta> colaboradorRespostas;
    
    public Pergunta() {
	}

	public Pergunta(int peso, Integer ordem, String textoComentario) {
		this.peso = peso;
		this.ordem = ordem;
		this.textoComentario = textoComentario;
	}

	public Long getQuestionarioOrAvaliacaoId()
	{
		Long questionarioOrAvaliacaoId = null;
		if(this.getAvaliacao() != null)
			questionarioOrAvaliacaoId = this.getAvaliacao().getId();
		else {
			if (this.getQuestionario() != null)
				questionarioOrAvaliacaoId = this.getQuestionario().getId();
		}
		
		return questionarioOrAvaliacaoId;
	}
	
	@NaoAudita
    public String getRespostasFormatada()
    {
    	StringBuilder respostasTxt = new StringBuilder("");
    	
    	if(this.respostas != null)
    	{
    		for (Resposta resp : this.respostas)
			{
				respostasTxt.append("(\u00A0\u00A0)\u00A0\u00A0" + resp.getTexto() + "     ");
			}
    	}
    	
    	return respostasTxt.toString();
    }
	
	@NaoAudita
	public void addColaboradorResposta(ColaboradorResposta colaboradorResposta) {
		
		if (colaboradorRespostas == null)
			colaboradorRespostas = new ArrayList<ColaboradorResposta>();
		
		colaboradorRespostas.add(colaboradorResposta);
	}

	//Projections

    public void setProjectionAspectoId(Long aspectoId)
    {
    	if(this.aspecto == null)
    		this.aspecto = new Aspecto();

    	this.aspecto.setId(aspectoId);
    }

    public void setProjectionAspectoNome(String aspectoNome)
    {
    	if(this.aspecto == null)
    		this.aspecto = new Aspecto();

    	this.aspecto.setNome(aspectoNome);
    }

    public void setProjectionQuestionarioId(Long questionarioId)
    {
    	if(this.questionario == null)
    		this.questionario = new Questionario();

    	this.questionario.setId(questionarioId);
    }

    public void setProjectionPesquisaId(Long pesquisaId)
    {
    	if(this.pesquisa == null)
    		this.pesquisa = new Pesquisa();

    	this.pesquisa.setId(pesquisaId);
    }
    
    public void setProjectionAvaliacaoId(Long avaliacaoId)
    {
    	if(this.avaliacao == null)
    		this.avaliacao = new Avaliacao();
    	
    	this.avaliacao.setId(avaliacaoId);
    }

    public Pesquisa getPesquisa()
	{
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa)
	{
		this.pesquisa = pesquisa;
	}

	public void setOrdem(Integer ordem)
	{
		this.ordem = ordem;
	}

	public boolean isComentario()
	{
		return comentario;
	}

    public void setComentario(boolean comentario)
	{
		this.comentario = comentario;
	}

	public String getTextoComentario()
	{
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario)
	{
		this.textoComentario = textoComentario;
	}

	public Integer getOrdem()
	{
		return ordem;
	}
	public void setOrdem(int ordem)
	{
		this.ordem = ordem;
	}
	public Collection<Resposta> getRespostas()
	{
		return respostas;
	}
	public void setRespostas(Collection<Resposta> respostas)
	{
		this.respostas = respostas;
	}
	public String getTexto()
	{
		return texto;
	}
	public void setTexto(String texto)
	{
		this.texto = texto;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", super.getId())
				.append("ordem", this.ordem).append("comentario",
						this.comentario).append("textoComentario", this.textoComentario).append(
						"texto", this.texto).toString();
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

	public Aspecto getAspecto()
	{
		return aspecto;
	}

	public void setAspecto(Aspecto aspecto)
	{
		this.aspecto = aspecto;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Integer getTipo()
	{
		return tipo;
	}

	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}

	public String getTipoTexto()
	{
		return (String) new TipoPergunta().get(this.tipo);
	}

	public Integer getNotaMaxima()
	{
		return notaMaxima;
	}

	public void setNotaMaxima(Integer notaMaxima)
	{
		this.notaMaxima = notaMaxima;
	}

	public Integer getNotaMinima()
	{
		return notaMinima;
	}

	public void setNotaMinima(Integer notaMinima)
	{
		this.notaMinima = notaMinima;
	}

	public String getOrdemMaisTexto()
	{
		return this.ordem + " ) " + this.texto;
	}

	public String getMedia()
	{
		return media;
	}

	public void setMedia(String media)
	{
		this.media = media;
	}

	/**
	 * Peso usado para Avaliações
	 * @return
	 */
	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas() {
		return colaboradorRespostas;
	}

	public void setColaboradorRespostas(Collection<ColaboradorResposta> colaboradorRespostas) {
		this.colaboradorRespostas = colaboradorRespostas;
	}

	public void setAspectoNome(String nomeAspecto) {
		if(aspecto == null)
			aspecto = new Aspecto();
		
		aspecto.setNome(nomeAspecto);
		
	}
}