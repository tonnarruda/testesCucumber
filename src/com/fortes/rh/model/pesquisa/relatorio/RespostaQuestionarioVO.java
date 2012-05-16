package com.fortes.rh.model.pesquisa.relatorio;

import java.io.File;

import com.fortes.rh.model.dicionario.TipoPergunta;


public class RespostaQuestionarioVO 
{
	private Long colaboradorQuestionarioId;
	private Long perguntaId;
	private Integer perguntaOrdem;
	private String perguntaTexto;
	private Boolean perguntaHasComentario;
	private Integer perguntaTipo;
	private Long respostaId;
	private Integer respostaOrdem;
	private String respostaTexto;
	private Integer respostaPeso;
	private Long aspectoId;
	private String aspectoNome;
	private String colaboradorQuestionarioObservacao;
	private Long colaboradorRespostaRespostaId;
	private Integer colaboradorRespostaValor;
	private String colaboradorRespostaComentario;
	
	public RespostaQuestionarioVO() {}
	
	public RespostaQuestionarioVO(Long colaboradorQuestionarioId, Long perguntaId, Integer perguntaOrdem, String perguntaTexto, Boolean perguntaHasComentario, Integer perguntaTipo, Long respostaId,
			Integer respostaOrdem, String respostaTexto, Integer respostaPeso, Long aspectoId, String aspectoNome, String colaboradorQuestionarioObservacao, Long colaboradorRespostaRespostaId,
			Integer colaboradorRespostaValor, String colaboradorRespostaComentario) {
		super();
		this.colaboradorQuestionarioId = colaboradorQuestionarioId;
		this.perguntaId = perguntaId;
		this.perguntaOrdem = perguntaOrdem;
		this.perguntaTexto = perguntaTexto;
		this.perguntaHasComentario = perguntaHasComentario;
		this.perguntaTipo = perguntaTipo;
		this.respostaId = respostaId;
		this.respostaOrdem = respostaOrdem;
		this.respostaTexto = respostaTexto;
		this.respostaPeso = respostaPeso;
		this.aspectoId = aspectoId;
		this.aspectoNome = aspectoNome;
		this.colaboradorQuestionarioObservacao = colaboradorQuestionarioObservacao;
		this.colaboradorRespostaRespostaId = colaboradorRespostaRespostaId;
		this.colaboradorRespostaValor = colaboradorRespostaValor;
		this.colaboradorRespostaComentario = colaboradorRespostaComentario;
	}

	public String getRespostaFormatada() {
		switch (this.perguntaTipo) {
			case TipoPergunta.NOTA:
				return "Nota: " + this.colaboradorRespostaValor;
			
			case TipoPergunta.OBJETIVA:
			case TipoPergunta.MULTIPLA_ESCOLHA:
				return this.respostaTexto;
				
			case TipoPergunta.SUBJETIVA:
				return this.colaboradorRespostaComentario;
		}
		
		return "";
	}
	
	public String getPathImage() {
		String path = ".." + File.separatorChar + ".." + File.separatorChar + "imgs" + File.separatorChar;

		switch (this.perguntaTipo) {
			case TipoPergunta.OBJETIVA:
				if (this.respostaId.equals(this.colaboradorRespostaRespostaId))
					return path + "checkedRadio.png";
				else
					return path + "uncheckedRadio.png";
				
			case TipoPergunta.MULTIPLA_ESCOLHA:
				if (this.respostaId.equals(this.colaboradorRespostaRespostaId))
					return path + "checkedBox.gif";
				else
					return path + "uncheckedBox.gif";
		}
		
		return null;
	}

	public Long getColaboradorQuestionarioId() {
		return colaboradorQuestionarioId;
	}

	public void setColaboradorQuestionarioId(Long colaboradorQuestionarioId) {
		this.colaboradorQuestionarioId = colaboradorQuestionarioId;
	}

	public Long getPerguntaId() {
		return perguntaId;
	}

	public void setPerguntaId(Long perguntaId) {
		this.perguntaId = perguntaId;
	}

	public Integer getPerguntaOrdem() {
		return perguntaOrdem;
	}

	public void setPerguntaOrdem(Integer perguntaOrdem) {
		this.perguntaOrdem = perguntaOrdem;
	}

	public String getPerguntaTexto() {
		return perguntaTexto;
	}

	public void setPerguntaTexto(String perguntaTexto) {
		this.perguntaTexto = perguntaTexto;
	}

	public Boolean getPerguntaHasComentario() {
		return perguntaHasComentario;
	}

	public void setPerguntaHasComentario(Boolean perguntaHasComentario) {
		this.perguntaHasComentario = perguntaHasComentario;
	}

	public Integer getPerguntaTipo() {
		return perguntaTipo;
	}

	public void setPerguntaTipo(Integer perguntaTipo) {
		this.perguntaTipo = perguntaTipo;
	}

	public Long getRespostaId() {
		return respostaId;
	}

	public void setRespostaId(Long respostaId) {
		this.respostaId = respostaId;
	}

	public Integer getRespostaOrdem() {
		return respostaOrdem;
	}

	public void setRespostaOrdem(Integer respostaOrdem) {
		this.respostaOrdem = respostaOrdem;
	}

	public String getRespostaTexto() {
		return respostaTexto;
	}

	public void setRespostaTexto(String respostaTexto) {
		this.respostaTexto = respostaTexto;
	}

	public Integer getRespostaPeso() {
		return respostaPeso;
	}

	public void setRespostaPeso(Integer respostaPeso) {
		this.respostaPeso = respostaPeso;
	}

	public Long getAspectoId() {
		return aspectoId;
	}

	public void setAspectoId(Long aspectoId) {
		this.aspectoId = aspectoId;
	}

	public String getAspectoNome() {
		return aspectoNome;
	}

	public void setAspectoNome(String aspectoNome) {
		this.aspectoNome = aspectoNome;
	}

	public String getColaboradorQuestionarioObservacao() {
		return colaboradorQuestionarioObservacao;
	}

	public void setColaboradorQuestionarioObservacao(String colaboradorQuestionarioObservacao) {
		this.colaboradorQuestionarioObservacao = colaboradorQuestionarioObservacao;
	}

	public Long getColaboradorRespostaRespostaId() {
		return colaboradorRespostaRespostaId;
	}

	public void setColaboradorRespostaRespostaId(Long colaboradorRespostaRespostaId) {
		this.colaboradorRespostaRespostaId = colaboradorRespostaRespostaId;
	}

	public Integer getColaboradorRespostaValor() {
		return colaboradorRespostaValor;
	}

	public void setColaboradorRespostaValor(Integer colaboradorRespostaValor) {
		this.colaboradorRespostaValor = colaboradorRespostaValor;
	}

	public String getColaboradorRespostaComentario() {
		return colaboradorRespostaComentario;
	}

	public void setColaboradorRespostaComentario(String colaboradorRespostaComentario) {
		this.colaboradorRespostaComentario = colaboradorRespostaComentario;
	}
}