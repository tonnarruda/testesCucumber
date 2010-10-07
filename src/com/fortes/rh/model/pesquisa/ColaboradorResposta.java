package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorresposta_sequence", allocationSize=1)
public class ColaboradorResposta extends AbstractModel implements Serializable
{
	@ManyToOne
    private Pergunta pergunta;
    @ManyToOne
    private Resposta resposta;
    @Lob
    private String comentario;
    private Integer valor;
    @ManyToOne
    private ColaboradorQuestionario colaboradorQuestionario;
    @ManyToOne
    private AreaOrganizacional areaOrganizacional;
    @ManyToOne
    private Estabelecimento estabelecimento;

    @Transient
    private String respostasObjetivas;
    
	@NaoAudita
	public boolean verificaResposta(Pergunta pergunta)
    {
    	switch (pergunta.getTipo())
		{
			case TipoPergunta.NOTA:
				return valor != null;
			case TipoPergunta.OBJETIVA:
				return resposta.getId() != null;
			case TipoPergunta.SUBJETIVA:
				return comentario != null && !comentario.equals("");
		}

    	return false;
    }
	
	@NaoAudita
	public Integer[] getNotas()
	{
		if(this.pergunta != null && this.pergunta.getNotaMinima() != null && this.pergunta.getNotaMaxima() != null)
		{
			Collection<Integer> notas = new ArrayList<Integer>();
			
			for (int i = this.pergunta.getNotaMinima(); i <= this.pergunta.getNotaMaxima(); i++)
			{
				notas.add(i);
			}
			
			return notas.toArray(new Integer[]{});
		}
		else
			return new Integer[]{};
	}
	
	@NaoAudita
	public boolean temResposta()
	{
		return (resposta != null && resposta.getId() != null) || (valor != null);
	}
	
	@NaoAudita
	public boolean temPergunta() {
		return pergunta != null && pergunta.getId() != null;
	}
	
	//Projections
    public void setProjectionColaboradorQuestionarioId(Long colaboradorQuestionarioId)
    {
    	if(this.colaboradorQuestionario == null)
    		this.colaboradorQuestionario = new ColaboradorQuestionario();

    	this.colaboradorQuestionario.setId(colaboradorQuestionarioId);
    }

    public void setProjectionPerguntaTexto(String perguntaTexto)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	this.pergunta.setTexto(perguntaTexto);
    }

    public void setProjectionPerguntaTipo(Integer perguntaTipo)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	this.pergunta.setTipo(perguntaTipo);
    }

    public void setProjectionPerguntaNotaMinima(Integer perguntaNotaMinima)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	this.pergunta.setNotaMinima(perguntaNotaMinima);
    }

    public void setProjectionPerguntaNotaMaxima(Integer perguntaNotaMaxima)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	this.pergunta.setNotaMaxima(perguntaNotaMaxima);
    }
    
    public void setProjectionPerguntaOrdem(Integer perguntaOrdem)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	this.pergunta.setOrdem(perguntaOrdem);
    }

    public void setProjectionPerguntaAspectoId(Long perguntaAspectoId)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	if (this.pergunta.getAspecto() == null)
    		this.pergunta.setAspecto(new Aspecto());

    	this.pergunta.getAspecto().setId(perguntaAspectoId);
    }

    public void setProjectionPerguntaAspectoNome(String perguntaAspectoNome)
    {
    	if (this.pergunta == null)
    		this.pergunta = new Pergunta();

    	if (this.pergunta.getAspecto() == null)
    		this.pergunta.setAspecto(new Aspecto());

    	this.pergunta.getAspecto().setNome(perguntaAspectoNome);
    }

    public void setProjectionRespostaTexto(String respostaTexto)
    {
    	if (this.resposta == null)
    		this.resposta = new Resposta();

    	this.resposta.setTexto(respostaTexto);
    }

    public void setProjectionColaboradorId(Long colaboradorId)
    {
    	if(this.colaboradorQuestionario == null)
    		this.colaboradorQuestionario = new ColaboradorQuestionario();

    	if(this.colaboradorQuestionario.getColaborador() == null)
    		this.colaboradorQuestionario.setColaborador(new Colaborador());

    	this.colaboradorQuestionario.getColaborador().setId(colaboradorId);
    }

    public void setProjectionColaboradorNomeComercial(String colaboradorNomeComercial)
    {
    	if(this.colaboradorQuestionario == null)
    		this.colaboradorQuestionario = new ColaboradorQuestionario();

    	if(this.colaboradorQuestionario.getColaborador() == null)
    		this.colaboradorQuestionario.setColaborador(new Colaborador());

    	if(StringUtils.isEmpty(this.colaboradorQuestionario.getColaborador().getNomeComercial()))
    		this.colaboradorQuestionario.getColaborador().setNomeComercial(colaboradorNomeComercial);
    }
    
    public void setProjectionAvaliadorNomeComercial(String avaliadorNomeComercial)
    {
    	if(this.colaboradorQuestionario == null)
    		this.colaboradorQuestionario = new ColaboradorQuestionario();
    	
    	if(this.colaboradorQuestionario.getAvaliador() == null)
    		this.colaboradorQuestionario.setAvaliador(new Colaborador());
    	
    	this.colaboradorQuestionario.getAvaliador().setNomeComercial(avaliadorNomeComercial);
    }
    
    public void setProjectionAvaliadorId(Long avaliadorId)
    {
    	if(this.colaboradorQuestionario == null)
    		this.colaboradorQuestionario = new ColaboradorQuestionario();

    	if(this.colaboradorQuestionario.getAvaliador() == null)
    		this.colaboradorQuestionario.setAvaliador(new Colaborador());

    	this.colaboradorQuestionario.getAvaliador().setId(avaliadorId);
    }

    public void setProjectionPerguntaId(Long perguntaId)
    {
    	if(this.pergunta == null)
    		this.pergunta = new Pergunta();

    	this.pergunta.setId(perguntaId);
    }
    
    public void setProjectionPerguntaPeso(Integer perguntaPeso)
    {
    	if(this.pergunta == null)
    		this.pergunta = new Pergunta();
    	
    	this.pergunta.setPeso(perguntaPeso);
    }

    public void setProjectionRespostaId(Long respostaId)
    {
    	if(this.resposta == null)
    		this.resposta = new Resposta();

    	this.resposta.setId(respostaId);
    }
    
    public void setProjectionRespostaPeso(Integer respostaPeso)
    {
    	if(this.resposta == null)
    		this.resposta = new Resposta();
    	
    	this.resposta.setPeso(respostaPeso);
    }
    
    public void setProjectionRespostaOrdem(Integer ordem)
    {
    	if(this.resposta == null)
    		this.resposta = new Resposta();
    	
    	if(ordem == null)
    		this.resposta.setOrdem(0);
    	else
    		this.resposta.setOrdem(ordem);
    }

    public Pergunta getPergunta()
	{
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}
	public Resposta getResposta()
	{
		return resposta;
	}
	public void setResposta(Resposta resposta)
	{
		this.resposta = resposta;
	}
	public void setRespostaId(Long id)
	{
		if(resposta == null)
			resposta = new Resposta();
		resposta.setId(id);
		setResposta(resposta);
	}

	public void setPerguntaId(Long id)
	{
		if(pergunta == null)
			pergunta = new Pergunta();
		pergunta.setId(id);
		setPergunta(pergunta);
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("pergunta", this.pergunta).append("resposta",
						this.resposta).toString();
	}
	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		return colaboradorQuestionario;
	}
	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}
	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}
	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}
	public String getComentario()
	{
		return comentario;
	}
	public void setComentario(String comentario)
	{
		this.comentario = comentario;
	}
	public Integer getValor()
	{
		return valor;
	}
	public void setValor(Integer valor)
	{
		this.valor = valor;
	}
	public String getRespostasObjetivas()
	{
		return respostasObjetivas;
	}
	public void setRespostasObjetivas(String respostasObjetivas)
	{
		this.respostasObjetivas = respostasObjetivas;
	}
	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}
}