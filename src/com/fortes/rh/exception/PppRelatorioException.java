package com.fortes.rh.exception;

import java.util.Date;

import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.opensymphony.xwork.ActionContext;

public class PppRelatorioException extends Exception
{
	private static final long serialVersionUID = 7236474252747821848L;
	
	private StringBuilder historicoSemAmbiente = new StringBuilder();
	private StringBuilder historicoSemFuncao = new StringBuilder();
	private StringBuilder historicoSemHistoricoAmbiente = new StringBuilder();
	private StringBuilder historicoSemHistoricoFuncao = new StringBuilder();
	private StringBuilder historicoAmbienteSemMedicao = new StringBuilder();
	private StringBuilder historicoFuncaoSemMedicao = new StringBuilder();
	
	private boolean ativo=false;
	
	public PppRelatorioException()
	{
		super("Existem pendências para a geração desse relatório. Verifique as informações abaixo antes de prosseguir: <br>");
	}
	
	public PppRelatorioException(String mensagem)
	{
		super(mensagem);
	}
	
	private void ativar()
	{
		ativo = true;
	}
	
	public void addHistoricoSemAmbiente(Date dataHistorico, Long colaboradorId)
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		String msg = dataFmt.concat(" - Situação do colaborador não possui ambiente definido.");
		
		selecionaLink(historicoSemAmbiente, colaboradorId, msg);
	}
	
	public void addHistoricoSemFuncao(Date dataHistorico, Long colaboradorId)
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		String msg = dataFmt.concat(" - Situação do colaborador não possui função definida.");
		
		selecionaLink(historicoSemFuncao, colaboradorId, msg);
	}

	private void selecionaLink(StringBuilder msgHistorico, Long colaboradorId, String msg) {
		if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_AMBIENTE_FUNCAO_COLABORADOR"}))
			msgHistorico.append("<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=" + colaboradorId + "'>" + msg + "</a><br />");
		else if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CAD_HISTORICOCOLABORADOR"}))
			msgHistorico.append("<a href='../../cargosalario/historicoColaborador/historicoColaboradorList.action?colaborador.id=" + colaboradorId + "'>" + msg + "</a><br />");
		else
			msgHistorico.append(msg + "<br />");
	}
	
	public void addHistoricoSemHistoricoAmbiente(Date dataHistorico, Long ambienteId) 
	{
		ativar();
		
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		
		if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CAD_AMBIENTE"}))
			historicoSemHistoricoAmbiente.append("<a href='../ambiente/prepareUpdate.action?ambiente.id=" + ambienteId + "'>" + dataFmt.concat(" - Ambiente do colaborador não possui histórico nesta data.</a><br />"));
		else
			historicoSemHistoricoAmbiente.append(dataFmt.concat(" - Ambiente do colaborador não possui histórico nesta data.<br />"));
	}
	
	public void addHistoricoSemHistoricoFuncao(Date dataHistorico, Long funcaoId, Long cargoId) 
	{
		ativar();
		String dataFmt = DateUtil.formataDiaMesAno(dataHistorico);
		
		if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_FUNCAO"}))
			historicoSemHistoricoFuncao.append("<a href='../historicoFuncao/list.action?funcao.id=" + funcaoId + "'>" + dataFmt.concat(" - Função do colaborador não possui histórico nesta data.</a><br />"));
		else
			historicoSemHistoricoFuncao.append(dataFmt.concat(" - Função do colaborador não possui histórico nesta data.<br />"));
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
		messageFmt.append(historicoFuncaoSemMedicao);
		
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
		{
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CAD_MEDICAORISCO"}))
				historicoAmbienteSemMedicao.append("<a href='../medicaoRisco/list.action?controlaRiscoPor=A&showFilter=true&ambiente.id=" + ambiente.getId() + "'>" + dataFmt + " - Ambiente <strong>" + ambiente.getNome()  + "</strong> possui riscos mas não possui medição nesta data.</a><br />");
			else
				historicoAmbienteSemMedicao.append(dataFmt + " - Ambiente <strong>" + ambiente.getNome()  + "</strong> possui riscos mas não possui medição nesta data.<br>");
		}
	}
	
	public void addFuncaoSemMedicao(Funcao funcao, Date historicoFuncaoData)
	{
		ativar();
		String dataFmt = DateUtil.formataDiaMesAno(historicoFuncaoData);
		
		// adiciona a mensagem apenas se ainda não houver para esta data
		if (historicoFuncaoSemMedicao.indexOf(dataFmt) == -1)
		{
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CAD_MEDICAORISCO"}))
				historicoFuncaoSemMedicao.append("<a href='../medicaoRisco/list.action?controlaRiscoPor=F&showFilter=true&funcao.id=" + funcao.getId() + "'>" + dataFmt + " - Função <strong>" + funcao.getNome()  + "</strong> possui riscos mas não possui medição nesta data.</a><br />");
			else
				historicoFuncaoSemMedicao.append(dataFmt + " - Função <strong>" + funcao.getNome()  + "</strong> possui riscos mas não possui medição nesta data.<br>");
		}
	}
}
