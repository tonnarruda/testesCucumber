package com.fortes.rh.model.sesmt.relatorio;

public class QtdPorFuncaoRelatorio {
	private Long funcaoId;
	private String funcaoNome;
	private Integer qtdHomens = 0;
	private Integer qtdMulheres = 0;

	public QtdPorFuncaoRelatorio() {
	}

	public QtdPorFuncaoRelatorio(Long funcaoId, String funcaoNome, Integer qtdHomens, Integer qtdMulheres) {
		this.funcaoId = funcaoId;
		this.funcaoNome = funcaoNome;
		this.qtdHomens = qtdHomens;
		this.qtdMulheres = qtdMulheres;
	}

	public Integer getQtdHomens() {
		return qtdHomens;
	}

	public Integer getQtdMulheres() {
		return qtdMulheres;
	}

	public String getFuncaoNome() {
		return funcaoNome;
	}
	
	public Long getFuncaoId()
	{
		return funcaoId;
	}
	
	public void setFuncaoId(Long funcaoId)
	{
		this.funcaoId = funcaoId;
	}
	
	public void setQtdHomesAndMulheres(Integer qtdHomens, Integer qtdMulheres)
	{
		if(qtdHomens != null && qtdHomens > 0)
			this.qtdHomens = qtdHomens;

		if(qtdMulheres != null && qtdMulheres > 0)
			this.qtdMulheres = qtdMulheres;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QtdPorFuncaoRelatorio other = (QtdPorFuncaoRelatorio) obj;
		if (funcaoId == null) {
			if (other.funcaoId != null)
				return false;
		} else if (!funcaoId.equals(other.funcaoId))
			return false;
		if (funcaoNome == null) {
			if (other.funcaoNome != null)
				return false;
		} else if (!funcaoNome.equals(other.funcaoNome))
			return false;
		return true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoId == null) ? 0 : funcaoId.hashCode());
		result = prime * result + ((funcaoNome == null) ? 0 : funcaoNome.hashCode());
		return result;
	}

}