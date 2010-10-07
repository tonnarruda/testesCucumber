package com.fortes.rh.model.captacao.relatorio;

import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.Vinculo;

public class SolicitacaoPessoalRelatorio
{
	private Solicitacao solicitacao;
	private String beneficios;

	public SolicitacaoPessoalRelatorio()
	{
		
	}
	
	public SolicitacaoPessoalRelatorio(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;

//		CollectionUtil<Beneficio> cu = new CollectionUtil<Beneficio>();
//		String[] nomeBeneficios = cu.convertCollectionToArrayString(beneficios, "getNome");
//
//		this.beneficios = Arrays.toString(nomeBeneficios).replaceAll("[\\[ \\]]" , "");
//		this.beneficios = this.beneficios.replaceAll(",", ", ");
//		if(this.beneficios.equals("null"))
//			this.beneficios = "";
	}

	public String getEscolaridade()
	{
		Escolaridade escolaridade = new Escolaridade();
		return (String) escolaridade.get(solicitacao.getEscolaridade());
	}

	public String getSexo()
	{
		Sexo sexo = new Sexo();
		return (String) sexo.get(solicitacao.getSexo());
	}

	public String getVinculo()
	{
		Vinculo vinculo = new Vinculo();
		return (String) vinculo.get(solicitacao.getVinculo());
	}

	public String getMotivoSolicitacao()
	{
		return solicitacao.getMotivoSolicitacao().getDescricao();
	}

	public String getBeneficios()
	{
		return beneficios;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public void setBeneficios(String beneficios)
	{
		this.beneficios = beneficios;
	}

}
