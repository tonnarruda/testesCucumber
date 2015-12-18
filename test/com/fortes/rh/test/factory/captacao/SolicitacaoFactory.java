package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;

public class SolicitacaoFactory
{
	public static Solicitacao getSolicitacao(Long id)
	{
		Solicitacao solicitacao = getSolicitacao();
		solicitacao.setId(id);
		
		return solicitacao;
	}

	public static Solicitacao getSolicitacao(FaixaSalarial faixaSalarial, Date data)
	{
		Solicitacao solicitacao = getSolicitacao();
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setData(data);
		
		return solicitacao;
	}
	
	public static Solicitacao getSolicitacao()
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setAreaOrganizacional(null);
		solicitacao.setFaixaSalarial(null);
		solicitacao.setData(new Date());
		solicitacao.setEscolaridade("a");
		solicitacao.setIdadeMaxima(50);
		solicitacao.setIdadeMinima(15);
		solicitacao.setInfoComplementares("infor");
		solicitacao.setQuantidade(200);
		solicitacao.setRemuneracao(1500.00);
		solicitacao.setSexo("m");
		solicitacao.setVinculo("a");
		solicitacao.setSolicitante(null);
		solicitacao.setEncerrada(false);
		solicitacao.setEmpresa(null);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);

		return solicitacao;
	}
}
