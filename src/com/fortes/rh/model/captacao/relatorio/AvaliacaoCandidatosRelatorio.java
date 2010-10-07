package com.fortes.rh.model.captacao.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.util.MathUtil;

public class AvaliacaoCandidatosRelatorio implements Serializable
{
	private static final long serialVersionUID = 1L;

	private EtapaSeletiva etapaSeletiva;
	private Integer qtdAptos = 0;
	private Integer qtdNaoAptos = 0;
	private Integer total;

	public AvaliacaoCandidatosRelatorio()	{ }

	public AvaliacaoCandidatosRelatorio(String etapaNome, int qtdAptos, int qtdNaoAptos)
	{
		if (this.etapaSeletiva == null)
			this.etapaSeletiva = new EtapaSeletiva();

		this.etapaSeletiva.setNome(etapaNome);
		this.qtdAptos = qtdAptos;
		this.qtdNaoAptos = qtdNaoAptos;
	}

	public AvaliacaoCandidatosRelatorio(int count, String etapaNome, boolean apto)
	{
		if (this.etapaSeletiva == null)
			this.etapaSeletiva = new EtapaSeletiva();

		this.etapaSeletiva.setNome(etapaNome);
		if (apto)
			this.qtdAptos = count;
		else
			this.qtdNaoAptos = count;

	}

	public double getPercentualAptos()
	{
		double retorno = ((double)qtdAptos / getTotal()) * 100;
		return MathUtil.round(retorno, 2);
	}

	public double getPercentualNaoAptos()
	{
		double retorno = ((double)qtdNaoAptos / getTotal()) * 100;
		return MathUtil.round(retorno, 2);
	}

	public int getQtdAptos()
	{
		return qtdAptos;
	}

	public int getQtdNaoAptos()
	{
		return qtdNaoAptos;
	}

	public EtapaSeletiva getEtapaSeletiva()
	{
		return etapaSeletiva;
	}

	public Integer getTotal()
	{
		this.total = this.qtdAptos+this.qtdNaoAptos;
		return this.total;
	}

	@Override
	public boolean equals(Object obj)
	{
		AvaliacaoCandidatosRelatorio avaliacaoCandidatosRelatorio = (AvaliacaoCandidatosRelatorio)obj;
		if (avaliacaoCandidatosRelatorio.getEtapaSeletiva() == null || this.getEtapaSeletiva() == null)
			return false;

		return avaliacaoCandidatosRelatorio.getEtapaSeletiva().getNome().equals(this.getEtapaSeletiva().getNome());
	}

	@Override
	public int hashCode()
	{
		return this.getEtapaSeletiva().getNome().hashCode();
	}

	/**
	 * Os seguintes métodos adicionam efetivamente um valor apenas uma vez (se o valor atual for zero).
	 * @see CandidatoManager.findRelatorioAvaliacaoCandidatos()
	 */
	public void setOnceQtdAptos(int qtdAptos)
	{
		this.qtdAptos += (this.qtdAptos == 0 ? qtdAptos : 0);
	}

	public void setOnceQtdNaoAptos(int qtdNaoAptos)
	{
		this.qtdNaoAptos += (this.qtdNaoAptos == 0 ? qtdNaoAptos : 0);
	}

}
