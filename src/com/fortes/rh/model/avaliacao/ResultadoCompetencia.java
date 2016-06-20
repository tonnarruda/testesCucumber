package com.fortes.rh.model.avaliacao;

public class ResultadoCompetencia
{
	private String nome;
	private Double ordem;

	public ResultadoCompetencia() {
	}
	
	public ResultadoCompetencia(String nome, Double ordem) {
		this.nome = nome;
		this.ordem = ordem;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getOrdem() {
		return ordem;
	}
	public void setOrdem(Double ordem) {
		this.ordem = ordem;
	}
}
