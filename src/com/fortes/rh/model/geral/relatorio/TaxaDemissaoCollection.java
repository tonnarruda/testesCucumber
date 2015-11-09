package com.fortes.rh.model.geral.relatorio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class TaxaDemissaoCollection
{
	private Long empresaId;
	private String empresaNome;
	private Collection<TaxaDemissao> taxaDemissoes = new ArrayList<TaxaDemissao>();
	private Collection<TaxaDemissao> taxaDemissoesGrafico = new ArrayList<TaxaDemissao>();//usado no Relatório
	
	public TaxaDemissaoCollection() 
	{
	}

	public String getMedia()
	{
		if(taxaDemissoes.size() == 0)
			return "0,0%";
		
		Double soma = 0D;
		for (TaxaDemissao taxaDemissao : taxaDemissoes)
		{
			soma += taxaDemissao.getTaxaDemissao();
		}
		
		DecimalFormat formata = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		formata.applyPattern("#0.00");
		return formata.format(soma / taxaDemissoes.size()) + "%";
	}

	public Collection<TaxaDemissao> getTaxaDemissoes() {
		return taxaDemissoes;
	}

	public void setTaxaDemissoes(Collection<TaxaDemissao> taxaDemissoes) {
		this.taxaDemissoes = taxaDemissoes;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public String getEmpresaNome() {
		return "Teste";
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}

	public Collection<TaxaDemissao> getTaxaDemissoesGrafico() {
		return taxaDemissoes;
	}
}
