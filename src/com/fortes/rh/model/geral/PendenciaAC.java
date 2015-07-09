package com.fortes.rh.model.geral;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.util.DateUtil;

public class PendenciaAC
{
	private String pendencia;
	private String detalhes;
	private int status;
	private String linkExcluir;
	private String role;
	private boolean statusCancelado;
	
	private HistoricoColaborador historicoColaborador;
	
	public PendenciaAC() {
	}
	
	public PendenciaAC(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}
	
	public void montarDetalhes() {
		
		StringBuilder detalhes = new StringBuilder();
		
		if(StringUtils.isBlank(historicoColaborador.getColaborador().getCodigoAC()))
		{
			this.pendencia = "Contratação";
			
			detalhes.append("Cadastro do colaborador "+historicoColaborador.getColaborador().getNomeComercial() + ".");
			String dataAdmissao = DateUtil.formataDiaMesAno(historicoColaborador.getColaborador().getDataAdmissao());
			String nomeCargo = historicoColaborador.getFaixaSalarial().getNomeDoCargo();
			detalhes.append(" Admissão: ").append(dataAdmissao).append(". Cargo: ").append(nomeCargo);
			setRole("ROLE_COLAB_LIST_EXCLUIR");
			setLinkExcluir("newConfirm('Confirma exclusão da pendência da contratação do colaborador?', function(){window.location='removePendenciaACColaborador.action?colaboradorId=" + historicoColaborador.getColaborador().getId() + "'});");
		}
		else
		{
			this.pendencia = "Nova Histórico de Colaborador";
			
			detalhes.append("Histórico do colaborador "+historicoColaborador.getColaborador().getNomeComercial() + ".");
			String dataHistorico = DateUtil.formataDiaMesAno(historicoColaborador.getData());
			detalhes.append(" Data: ").append(dataHistorico);
			setRole("ROLE_CAD_HISTORICOCOLABORADOR");
			setLinkExcluir("newConfirm('Confirma exclusão da pendência do novo histórico de colaborador?', function(){window.location='removePendenciaACHistoricoColaborador.action?historicoColaboradorId=" + historicoColaborador.getId() + "&colaboradorId=" + historicoColaborador.getColaborador().getId() + "'});");
		}
		
		this.detalhes = detalhes.toString();
		
		this.status = historicoColaborador.getStatus();
	}
	
	public String getDetalhes()
	{
		return detalhes;
	}
	public String getPendencia()
	{
		return pendencia;
	}
	public void setPendencia(String pendencia)
	{
		this.pendencia = pendencia;
	}
	public int getStatus()
	{
		return status;
	}
	public String getStatusDescricao()
	{
		return StatusRetornoAC.getDescricao(status);
	}	
	public void setStatus(int status) {
		this.status = status;
	}
	public void setDetalhes(String detalhes) 
	{
		this.detalhes = detalhes;
	}
	public String getLinkExcluir() 
	{
		return this.linkExcluir;
	}
	public void setLinkExcluir(String linkExcluir) 
	{
		this.linkExcluir = linkExcluir;
	}
	public String getRole() 
	{
		return role;
	}
	public void setRole(String role) 
	{
		this.role = role;
	}
	public boolean isStatusCancelado() 
	{
		return StatusRetornoAC.CANCELADO == status;
	}
}