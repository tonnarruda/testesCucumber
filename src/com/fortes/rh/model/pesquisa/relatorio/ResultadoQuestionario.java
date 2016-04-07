package com.fortes.rh.model.pesquisa.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.util.StringUtil;

public class ResultadoQuestionario implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Pergunta pergunta;
	private Collection<Resposta> respostas;
	private Collection<ColaboradorResposta> colabRespostas;//não mude esse nome,da pau no relatorio se for colaboradorRespostas, Francisco Barroso
	private Collection<ColaboradorResposta> colabRespostasDistinct;//retira as respostas multiplas escolhas
	private Collection<ColaboradorResposta> colabComentariosDistinct;//tive que duplicar o ireport ta perdendo a coleção quando utilizada para listar as respostas
																	// recebe apenas as respostas c/ comentário preenchido

	public Collection<ColaboradorResposta> getColabComentariosDistinct()
	{
		return colabComentariosDistinct;
	}

	public Collection<ColaboradorResposta> getColabRespostasDistinct()
	{
		return colabRespostasDistinct;
	}
	
//TODO Refatorar 
	public void montaColabRespostasDistinct()
	{
		this.colabRespostasDistinct = new ArrayList<ColaboradorResposta>();
		Long perguntaId = 0L;
		Long colaboradorQuestionarioId = 0L;
		
		for (ColaboradorResposta colaboradorResposta : this.colabRespostas)
		{
			if(!colaboradorResposta.getPergunta().getId().equals(perguntaId) || 
			(colaboradorResposta.getColaboradorQuestionario() != null && 
				colaboradorResposta.getColaboradorQuestionario().getId() != null && 
				!colaboradorResposta.getColaboradorQuestionario().getId().equals(colaboradorQuestionarioId)))
			{
				if (colaboradorResposta.getColaboradorQuestionario().getId() == null)
					colaboradorResposta.getColaboradorQuestionario().setId(0L);
			
				if(this.pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA) || this.pergunta.getTipo().equals(TipoPergunta.OBJETIVA) || this.pergunta.getTipo().equals(TipoPergunta.NOTA))
					colaboradorResposta.setRespostasObjetivas(getRespostasObjetivas(colabRespostas, colaboradorResposta.getPergunta().getId(), colaboradorResposta.getColaboradorQuestionario().getId()));

				this.colabRespostasDistinct.add(colaboradorResposta);
			}
			
			perguntaId = colaboradorResposta.getPergunta().getId();
			colaboradorQuestionarioId = colaboradorResposta.getColaboradorQuestionario().getId();
		}
		
	}
	
	//ninguem sabe o que é isso.
	public void montaComentarioDistinct()
	{
		this.colabComentariosDistinct = new ArrayList<ColaboradorResposta>();
		Long perguntaId = 0L;
		Long colaboradorQuestionarioIdTmp = 0L;
		
		for (ColaboradorResposta colaboradorResposta : colabRespostas) 
		{
			if(StringUtils.isNotBlank(colaboradorResposta.getComentario()) && 
			(!colaboradorResposta.getPergunta().getId().equals(perguntaId) || 
			(colaboradorResposta.getColaboradorQuestionario() != null && 
			colaboradorResposta.getColaboradorQuestionario().getId() != null &&  
			!colaboradorQuestionarioIdTmp.equals(colaboradorResposta.getColaboradorQuestionario().getId()) 
			)))
			{
				colabComentariosDistinct.add(colaboradorResposta);
				colaboradorQuestionarioIdTmp = colaboradorResposta.getColaboradorQuestionario().getId();
			}
			
			perguntaId = colaboradorResposta.getPergunta().getId();
		}
	}
	
	public void montaComentarioPesquisaDistinct()
	{
		this.colabComentariosDistinct = new ArrayList<ColaboradorResposta>();
		Boolean adicionar;
		
		for (ColaboradorResposta colaboradorResposta : colabRespostas){
			if(StringUtils.isNotBlank(colaboradorResposta.getComentario()))
			{
				adicionar = true;
				
				for (ColaboradorResposta colaboradorRespostaDist : colabComentariosDistinct){
					if(colaboradorRespostaDist.getComentario().equals(colaboradorResposta.getComentario()))	{
						adicionar = false;
						break;
					}
				}
				
				if(adicionar)
					colabComentariosDistinct.add(colaboradorResposta);
			}
				
		}
	}
	
	private String getRespostasObjetivas(Collection<ColaboradorResposta> colaboradorRespostas, Long perguntaId, Long colaboradorQuestionarioId)
	{
		Collection<String> respostas = new ArrayList<String>(); 
		
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
		
			if (colaboradorResposta.getColaboradorQuestionario().getId() == null)
				colaboradorResposta.getColaboradorQuestionario().setId(0L);
			
			if(colaboradorResposta.getPergunta().getId().equals(perguntaId) && colaboradorResposta.getColaboradorQuestionario().getId().equals(colaboradorQuestionarioId) && colaboradorResposta.getResposta()!=null)
			{
				if (colaboradorResposta.getPergunta().getTipo()!=null && colaboradorResposta.getPergunta().getTipo().equals(TipoPergunta.NOTA))
					respostas.add(colaboradorResposta.getValor() != null ? "Nota " + colaboradorResposta.getValor() : "-");
				else
					respostas.add(colaboradorResposta.getResposta().getLetraByOrdem());
			}
		}	
		
		return StringUtil.converteCollectionToString(respostas);
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}
	
	public Collection<ColaboradorResposta> getColabRespostas()
	{
		return colabRespostas;
	}
	public void setColabRespostas(Collection<ColaboradorResposta> colabRespostas)
	{
		this.colabRespostas = colabRespostas;
	}
	public Collection<Resposta> getRespostas()
	{
		return respostas;
	}
	public void setRespostas(Collection<Resposta> respostas)
	{
		this.respostas = respostas;
	}

	public void setColabComentariosDistinct(Collection<ColaboradorResposta> colabComentariosDistinct)
	{
		this.colabComentariosDistinct = colabComentariosDistinct;
	}
}