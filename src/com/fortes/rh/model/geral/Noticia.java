package com.fortes.rh.model.geral;



public class Noticia
{
	private String texto;
	private String link;
	private Integer criticidade;
	private Integer[] produtos;
	
	public Noticia() {
	
	}
	
	public Noticia(String texto, String link, Integer criticidade, Integer[] produtos) {
		this.texto = texto;
		this.link = link;
		this.criticidade = criticidade;
		this.produtos = produtos;
	}

	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Integer getCriticidade() {
		return criticidade;
	}
	
	public void setCriticidade(Integer criticidade) {
		this.criticidade = criticidade;
	}
	
	public Integer[] getProdutos() {
		return produtos;
	}
	
	public void setProdutos(Integer[] produtos) {
		this.produtos = produtos;
	}
}