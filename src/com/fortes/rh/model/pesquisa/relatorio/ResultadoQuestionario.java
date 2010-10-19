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

	public void montaColabRespostasDistinct()
	{
		this.colabRespostasDistinct = new ArrayList<ColaboradorResposta>();
		Long perguntaId = 0L;
		Long colaboradorQuestionarioId = 0L;
		
		for (ColaboradorResposta colaboradorResposta : this.colabRespostas)
		{
			if(!colaboradorResposta.getPergunta().getId().equals(perguntaId) || (colaboradorResposta.getColaboradorQuestionario() != null && colaboradorResposta.getColaboradorQuestionario().getId() != null && !colaboradorResposta.getColaboradorQuestionario().getId().equals(colaboradorQuestionarioId)))
			{
				if (colaboradorResposta.getColaboradorQuestionario().getId() == null)
					colaboradorResposta.getColaboradorQuestionario().setId(0L);
			
				if(this.pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA) || this.pergunta.getTipo().equals(TipoPergunta.OBJETIVA))
					colaboradorResposta.setRespostasObjetivas(getRespostasObjetivas(colabRespostas, colaboradorResposta.getPergunta().getId(), colaboradorResposta.getColaboradorQuestionario().getId()));
				if(this.pergunta.getTipo().equals(TipoPergunta.NOTA))
					if(colaboradorResposta.getValor() != null)
						colaboradorResposta.setRespostasObjetivas("" + colaboradorResposta.getValor());
				
				this.colabRespostasDistinct.add(colaboradorResposta);
			}
			
			perguntaId = colaboradorResposta.getPergunta().getId();
			colaboradorQuestionarioId = colaboradorResposta.getColaboradorQuestionario().getId();
		}
		
	}
	
	public void montaComentarioDistinct()
	{
		this.colabComentariosDistinct = new ArrayList<ColaboradorResposta>();
		Long perguntaId = 0L;
		Long colaboradorQuestionarioId = 0L;
		
		for (ColaboradorResposta colaboradorResposta : colabRespostas) 
		{
			if(StringUtils.isNotBlank(colaboradorResposta.getComentario()) && (!colaboradorResposta.getPergunta().getId().equals(perguntaId) || (colaboradorResposta.getColaboradorQuestionario() != null && colaboradorResposta.getColaboradorQuestionario().getId() != null && !colaboradorResposta.getColaboradorQuestionario().getId().equals(colaboradorQuestionarioId))))
				colabComentariosDistinct.add(colaboradorResposta);
			
			perguntaId = colaboradorResposta.getPergunta().getId();
			colaboradorQuestionarioId = colaboradorResposta.getColaboradorQuestionario().getId();
		}
	}
	
	private String getRespostasObjetivas(Collection<ColaboradorResposta> colaboradorRespostas, Long perguntaId, Long colaboradorQuestionarioId)
	{
		Collection<String> respostas = new ArrayList<String>(); 
		
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
		
			if (colaboradorResposta.getColaboradorQuestionario().getId() == null)
				colaboradorResposta.getColaboradorQuestionario().setId(0L);
			
			if(colaboradorResposta.getPergunta().getId().equals(perguntaId) && colaboradorResposta.getColaboradorQuestionario().getId().equals(colaboradorQuestionarioId))
				respostas.add(colaboradorResposta.getResposta().getLetraByOrdem());
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