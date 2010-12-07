package com.fortes.rh.model.relatorio;


public class PerguntaFichaMedica
{
	private String pergunta;
	private String comentario;
	private Integer tipo;
	
	public PerguntaFichaMedica(String pergunta, String comentario, Integer tipo)
	{
		super();
		this.pergunta = pergunta;
		this.comentario = comentario;
		this.tipo = tipo;
	}
	public PerguntaFichaMedica() {
		// TODO Auto-generated constructor stub
	}
	public String getPergunta()
	{
		return pergunta;
	}
	public void setPergunta(String pergunta)
	{
		this.pergunta = pergunta;
	}
	public String getComentario()
	{
		return comentario;
	}
	public void setComentario(String comentario)
	{
		this.comentario = comentario;
	}
	public Integer getTipo()
	{
		return tipo;
	}
	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}
}