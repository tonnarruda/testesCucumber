package com.fortes.rh.model.sesmt.relatorio;

public class QtdPorFuncaoRelatorio {
	private String funcaoNome;
	private Integer qtdHomens = 0;
	private Integer qtdMulheres = 0;
	private Integer qtdTotal = 0;

	public QtdPorFuncaoRelatorio() {
	}

	public QtdPorFuncaoRelatorio(String funcaoNome, Integer qtdHomens, Integer qtdMulheres) {
		this.funcaoNome = funcaoNome;
		this.qtdHomens = qtdHomens;
		this.qtdMulheres = qtdMulheres;
		this.qtdTotal = this.qtdHomens + this.qtdMulheres;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoNome == null) ? 0 : funcaoNome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QtdPorFuncaoRelatorio other = (QtdPorFuncaoRelatorio) obj;
		if (funcaoNome == null) {
			if (other.funcaoNome != null)
				return false;
		} else if (!funcaoNome.equals(other.funcaoNome))
			return false;
		return true;
	}
	
	public Integer getQtdHomens() {
		return qtdHomens;
	}

	public Integer getQtdMulheres() {
		return qtdMulheres;
	}

	public Integer getQtdTotal() {
		return qtdTotal;
	}

	public String getFuncaoNome() {
		return funcaoNome;
	}
}