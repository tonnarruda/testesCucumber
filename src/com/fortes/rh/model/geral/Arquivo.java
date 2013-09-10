package com.fortes.rh.model.geral;

import java.io.File;
import java.util.Date;

public class Arquivo {
	private String nome;
	private Long tamanho;
	private Date data;
	
	public Arquivo(File file) {
		this.nome = file.getName();
		this.tamanho = file.length();
		this.data = new Date(file.lastModified());
	}

	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public Long getTamanho()
	{
		return tamanho;
	}
	
	public void setTamanho(Long tamanho)
	{
		this.tamanho = tamanho;
	}
	
	public Date getData()
	{
		return data;
	}
	
	public void setData(Date data)
	{
		this.data = data;
	}
	
}