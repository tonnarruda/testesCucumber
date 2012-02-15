package com.fortes.rh.model.geral;


public class Video
{
	private Long id;
	private String titulo;
	private String descricao;
	private String exe;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getExe() {
		return exe;
	}
	
	public void setExe(String exe) {
		this.exe = exe;
	}
}
