package com.fortes.rh.web.dwr;

import java.util.Date;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.util.DateUtil;

public class OrdemDeServicoDWR {
	
	private OrdemDeServicoManager ordemDeServicoManager;
	private EmpresaManager empresaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	public OrdemDeServico recarregaDadosOrdemDeServico(Long colaboradorId, Long empresaId, String dataOrdemDeServico) throws Exception{
		
		try {
			validaDataOrdemDeServico(colaboradorId, DateUtil.criarDataDiaMesAno(dataOrdemDeServico));
			checaDataUltimaOrdemDeServicoImpressa(colaboradorId, DateUtil.criarDataDiaMesAno(dataOrdemDeServico));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		Empresa empresa = empresaManager.findByIdProjection(empresaId); 
		OrdemDeServico ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(colaboradorId, empresa, DateUtil.criarDataDiaMesAno(dataOrdemDeServico));
		return ordemDeServico;
	}

	private void validaDataOrdemDeServico(Long colaboradorId, Date dataHistorico) throws Exception {
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.findHistoricoColaboradorByData(colaboradorId, dataHistorico);
		if(historicoColaborador != null){
			if(historicoColaborador.getFuncao() == null || historicoColaborador.getFuncao().getId() == null)
				throw new Exception("Não é possível inserir uma ordem de serviço para a data: <b>" + DateUtil.formataDate(dataHistorico, "dd/MM/yyyy") +"</b>, pois o histórico do colaborador não possui função.");
			else if(historicoColaborador.getColaborador().getDataAdmissao().after(dataHistorico))
				throw new Exception("Não é possível inserir uma ordem de serviço com data anterior a data de Admissão do colaborador.");
			else if (historicoColaborador.getColaborador().isDesligado() && historicoColaborador.getColaborador().getDataDesligamento().before(dataHistorico))
				throw new Exception("Não é possível inserir uma ordem de serviço com data posterior a data de desligamento do colaborador.");
		}else
			throw new Exception("Não é possível inserir uma ordem de serviço com data anterior a data de Admissão do colaborador.");
	}
	
	private void checaDataUltimaOrdemDeServicoImpressa(Long colaboradorId, Date dataOrdemDeServico) throws Exception{
		OrdemDeServico ordemDeServico = ordemDeServicoManager.findUltimaOrdemDeServico(colaboradorId);
		if(ordemDeServico != null && dataOrdemDeServico.before(ordemDeServico.getData()))
			throw new Exception("Não é possível inserir uma ordem de serviço.</br></br>A data informada está inferior a data da última Ordem de Serviço impressa."
					+ "</br></br>Data da última ordem de serviço: " + ordemDeServico.getDataFormatada());
	}

	public void setOrdemDeServicoManager(OrdemDeServicoManager ordemDeServicoManager) {
		this.ordemDeServicoManager = ordemDeServicoManager;
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
	
	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}
}
