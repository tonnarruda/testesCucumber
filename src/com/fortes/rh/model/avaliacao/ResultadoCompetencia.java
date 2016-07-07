package com.fortes.rh.model.avaliacao;

public class ResultadoCompetencia
{
	private Integer identificador;
	private String nome;
	private Double ordem;

	public ResultadoCompetencia() {}
	public ResultadoCompetencia(Integer identificador,String nome, Double ordem) {
		this.identificador = identificador;
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
	public Integer getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}
	public String getIdentificadorAlafabeto() {
		return getAlafabeto(this.identificador);
	}
	public static String getAlafabeto(Integer inteiro) {
		String retorno = "";
		switch (inteiro){
			case 1:
				retorno = "A";
				break;
			case 2:
				retorno = "B";
				break;
			case 3 :
				retorno = "C";
				break;
			case 4 :
				retorno = "D";
				break;
			case 5 :
				retorno = "E";
				break;
			case 6 :
				retorno = "F";
				break;
			case 7 :
				retorno = "G";
				break;
		}

		return retorno;
	}
}
