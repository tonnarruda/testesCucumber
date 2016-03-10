package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;

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
		solicitacao.setEmpresa(null);
		setStatusAndAndamento(solicitacao, StatusAprovacaoSolicitacao.ANALISE, false, false);

		return solicitacao;
	}
	
	public static Solicitacao getSolicitacao(Empresa empresa, Usuario solicitante, AreaOrganizacional areaOrganizacional, boolean invisivelParaGestor, boolean encerrada, boolean suspensa){
		Solicitacao solicitacao = getSolicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setInvisivelParaGestor(invisivelParaGestor);
		setAndamento(solicitacao, encerrada, suspensa);
		return solicitacao;
	}
	
	public static Solicitacao getSolicitacao(Empresa empresa, FaixaSalarial faixaSalarial, boolean encerrada, boolean suspensa, char status, int quantidade, Date data ){
		Solicitacao solicitacao = getSolicitacao();
		setStatusAndAndamento(solicitacao, status, encerrada, suspensa);
		solicitacao.setData(data);
		solicitacao.setEmpresa(empresa);		
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setQuantidade(quantidade);
		return solicitacao;
	}
	
	public static Solicitacao getSolicitacao(Empresa empresa, Estabelecimento estabelecimento, FaixaSalarial faixaSalarial, boolean encerrada, boolean suspensa, char status, int quantidade, Date data ){
		Solicitacao solicitacao = getSolicitacao(empresa, faixaSalarial, encerrada, suspensa, status, quantidade, data);
		solicitacao.setEstabelecimento(estabelecimento);
		return solicitacao;
	}
	
	public static Solicitacao getSolicitacao(Empresa empresa, AreaOrganizacional areaOrganizacional, FaixaSalarial faixaSalarial, boolean encerrada, boolean suspensa, char status, int quantidade, Date data ){
		Solicitacao solicitacao = getSolicitacao(empresa, faixaSalarial, encerrada, suspensa, status, quantidade, data);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		return solicitacao;
	}
	
	private static void setAndamento(Solicitacao solicitacao, boolean encerrada, boolean suspensa){
		solicitacao.setEncerrada(encerrada);
		solicitacao.setSuspensa(suspensa);
	}
	
	private static void setStatusAndAndamento(Solicitacao solicitacao, char status, boolean encerrada, boolean suspensa){
		setAndamento(solicitacao, encerrada, suspensa);
		solicitacao.setStatus(status);
	}
}