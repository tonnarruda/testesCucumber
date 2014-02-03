package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoCampoExtra_sequence", allocationSize=1)
public class ConfiguracaoCampoExtra extends AbstractModel implements Serializable, Cloneable
{
	private Boolean ativoColaborador = false;
	private Boolean ativoCandidato = false;
	@Column(length=15)
	private String nome;
	@Column(length=60)
	private String titulo;
	private Integer ordem = 0;
	private Integer posicao = 0;
	private String tipo;
	@Column(length=30)
	private String descricao;
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	@Transient
	private String conteudo;
	
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

	public void setEmpresaId(Long empresaId)
	{
		if(empresa == null)
			empresa = new Empresa();
		empresa.setId(empresaId);
	}
	
	public int getSize()
	{
		if(tipo.equals("texto"))
			return 150;
		else
			return 50;// data, valor ou numero
	}
	

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	public Boolean getAtivoColaborador() {
		return ativoColaborador;
	}


	public void setAtivoColaborador(Boolean ativoColaborador) {
		this.ativoColaborador = ativoColaborador;
	}


	public Integer getOrdem() {
		return ordem;
	}


	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}


	public Integer getPosicao() {
		return posicao;
	}


	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Boolean getAtivoCandidato() {
		return ativoCandidato;
	}

	public void setAtivoCandidato(Boolean ativoCandidato) {
		this.ativoCandidato = ativoCandidato;
	}

	
	public String getConteudo()
	{
		return conteudo;
	}

	
	public void setConteudo(String conteudo)
	{
		this.conteudo = conteudo;
	}

}
