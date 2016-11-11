package com.fortes.rh.web.dwr;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.util.DateUtil;

@Component
public class OrdemDeServicoDWR {
	
	private OrdemDeServicoManager ordemDeServicoManager;
	private EmpresaManager empresaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	public OrdemDeServico recarregaDadosOrdemDeServico(Long colaboradorId, Long empresaId, String dataOrdemDeServico) throws Exception{
		Date dataOrdemDeServico1 = DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno(dataOrdemDeServico));
		
		try {
			validaDataOrdemDeServico(colaboradorId, dataOrdemDeServico1);
			checaDataUltimaOrdemDeServicoImpressa(colaboradorId, dataOrdemDeServico1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		Empresa empresa = empresaManager.findByIdProjection(empresaId); 
		OrdemDeServico ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(colaboradorId, empresa, dataOrdemDeServico1);
		return ordemDeServico;
	}

	private void validaDataOrdemDeServico(Long colaboradorId, Date dataHistorico) throws Exception {
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.findHistoricoColaboradorByData(colaboradorId, dataHistorico);
		if(historicoColaborador != null){
			if(historicoColaborador.getFuncao() == null || historicoColaborador.getFuncao().getId() == null)
				throw new Exception("Não é possível inserir uma ordem de serviço para a data: <b>" + DateUtil.formataDate(dataHistorico, "dd/MM/yyyy") +"</b>, pois o histórico do colaborador não possui função.");
			else if(historicoColaborador.getColaborador().getDataAdmissao().compareTo(dataHistorico) > 0 )
				throw new Exception("Não é possível inserir uma ordem de serviço com data anterior a data de Admissão do colaborador.</br></br> Data de admissão: " + historicoColaborador.getColaborador().getDataAdmissaoFormatada());
			else if (historicoColaborador.getColaborador().isDesligado() && (dataHistorico.compareTo(historicoColaborador.getColaborador().getDataDesligamento()) > 0))
				throw new Exception("Não é possível inserir uma ordem de serviço com data posterior a data de desligamento do colaborador.</br></br> Data de desligamento: " + historicoColaborador.getColaborador().getDataDesligamentoFormatada());
		}else
			throw new Exception("Não é possível inserir uma ordem de serviço com data anterior a data de Admissão do colaborador.");
	}
	
	private void checaDataUltimaOrdemDeServicoImpressa(Long colaboradorId, Date dataOrdemDeServico) throws Exception{
		OrdemDeServico ordemDeServico = ordemDeServicoManager.findUltimaOrdemDeServicoImpressa(colaboradorId);
		if(ordemDeServico != null && dataOrdemDeServico.before(ordemDeServico.getData()))
			throw new Exception("Não é possível inserir uma ordem de serviço.</br></br>A data informada está inferior a data da última Ordem de Serviço impressa."
					+ "</br></br>Data da última ordem de serviço: " + ordemDeServico.getDataFormatada());
	}
	
	public OrdemDeServico carregaUltimaOrdemDeServicoByColaborador(Long colaboradorId){
		return ordemDeServicoManager.findUltimaOrdemDeServico(colaboradorId);
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
