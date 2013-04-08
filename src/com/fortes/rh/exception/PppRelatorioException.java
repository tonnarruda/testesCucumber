package com.fortes.rh.exception;

import java.util.Date;

import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.util.DateUtil;

public class PppRelatorioException extends Exception
{
	private static final long serialVersionUID = 7236474252747821848L;
	
	private StringBuilder historicoSemAmbiente = new StringBuilder();
	private StringBuilder historicoSemFuncao = new StringBuilder();
	private StringBuilder historicoSemHistoricoAmbiente = new StringBuilder();
	private StringBuilder historicoSemHistoricoFuncao = new StringBuilder();
	private StringBuilder historicoAmbienteSemMedicao = new StringBuilder();
	
	private boolean ativo=false;
	
	public PppRelatorioException()
	{
		super("Não foi possível gerar o relatório. Verifique as informações abaixo antes de prosseguir: <br>");
	}
	
	public PppRelatorioException(String mensagem)
	{
		super(mensagem);
	}
	
	private void ativar()
	{
		ativo = true;
	}
	
	public void addHistoricoSemAmbiente(Date dataHistorico)
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		
		historicoSemAmbiente.append(dataFmt.concat(" - Situação do colaborador não possui Ambiente definido.<br>"));
	}
	
	public void addHistoricoSemFuncao(Date dataHistorico)
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		
		historicoSemFuncao.append(dataFmt.concat(" - Situação do colaborador não possui Função definida.<br>"));
	}
	
	public void addHistoricoSemHistoricoAmbiente(Date dataHistorico) 
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		
		historicoSemHistoricoAmbiente.append(dataFmt.concat(" - Ambiente do colaborador não possui histórico nesta data.<br>"));
	}
	
	public void addHistoricoSemHistoricoFuncao(Date dataHistorico) 
	{
		ativar();
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		historicoSemHistoricoFuncao.append(dataFmt.concat(" - Função do colaborador não possui histórico nesta data.<br>"));
	}
	
	// mensagens que barram a geração do PPP. (errors)
	public final String getMensagemDeInformacao()
	{
		StringBuilder messageFmt = new StringBuilder();
		messageFmt.append(getMessage());
		messageFmt.append(historicoSemAmbiente);
		messageFmt.append(historicoSemFuncao);
		messageFmt.append(historicoSemHistoricoAmbiente);
		messageFmt.append(historicoSemHistoricoFuncao);
		messageFmt.append(historicoAmbienteSemMedicao);
		
		return messageFmt.toString().trim();
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void addAmbienteSemMedicao(Ambiente ambiente, Date historicoAmbienteData)
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(historicoAmbienteData);
		
		// adiciona a mensagem apenas se ainda não houver para esta data
		if (historicoAmbienteSemMedicao.indexOf(dataFmt) == -1)
			historicoAmbienteSemMedicao.append(dataFmt + " - Ambiente (" + ambiente.getNome()  + ") possui riscos mas não possui medição nesta data.<br>");
	}
}
