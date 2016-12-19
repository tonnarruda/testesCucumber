package com.fortes.rh.web.action.captacao;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoCandidatoListAction extends MyActionSupportList
{
	@Autowired private HistoricoCandidatoManager historicoCandidatoManager;
	@Autowired private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	private Collection<HistoricoCandidato> historicoCandidatos;
	private HistoricoCandidato historicoCandidato;
	private CandidatoSolicitacao candidatoSolicitacao;

	private Long etapaSeletivaId;
	private char visualizar;
	private String indicadoPor;
	private Map<String, Object> parametros;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		candidatoSolicitacao = candidatoSolicitacaoManager.findByCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidatos = historicoCandidatoManager.findList(candidatoSolicitacao);

		return Action.SUCCESS;
	}

	public String imprimirHistorico() throws Exception
	{
		list();
		
		parametros = RelatorioUtil.getParametrosRelatorio("Histórico do Candidato na Solicitação", getEmpresaSistema(), 
				"Área: " + candidatoSolicitacao.getNomeArea()+ 
				"\nCargo: " + candidatoSolicitacao.getNomeCargo() + 
				"\nSolicitante: " + candidatoSolicitacao.getNomeSolicitante()+ 
				"\nCandidato: " + candidatoSolicitacao.getCandidato().getNome());
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		historicoCandidatoManager.remove(new Long[]{historicoCandidato.getId()});
		return Action.SUCCESS;
	}

	public Collection getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}

	public CandidatoSolicitacao getCandidatoSolicitacao()
	{
		return candidatoSolicitacao;
	}

	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao)
	{
		this.candidatoSolicitacao = candidatoSolicitacao;
	}

	public HistoricoCandidato getHistoricoCandidato()
	{
		return historicoCandidato;
	}

	public void setHistoricoCandidato(HistoricoCandidato historicoCandidato)
	{
		this.historicoCandidato = historicoCandidato;
	}

	public Long getEtapaSeletivaId()
	{
		return etapaSeletivaId;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}
	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}
}