package com.fortes.rh.web.dwr;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.util.DateUtil;

public class OrdemDeServicoDWR {
	
	private OrdemDeServicoManager ordemDeServicoManager;
	private EmpresaManager empresaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	public OrdemDeServico recarregaDadosOrdemDeServico(Long idOdemDeServico, Long colaboradorId, Long empresaId, String dataOrdemDeServico) throws Exception{
		validaDataOrdemDeServico(colaboradorId, dataOrdemDeServico);
		
		OrdemDeServico ordemDeServico = new OrdemDeServico();
		ordemDeServico.setId(idOdemDeServico);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
		
		Empresa empresa = empresaManager.findByIdProjection(empresaId); 
		ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(ordemDeServico, colaborador, empresa, DateUtil.criarDataDiaMesAno(dataOrdemDeServico));
		return ordemDeServico;
	}

	private void validaDataOrdemDeServico(Long colaboradorId, String dataHistorico) throws Exception {
		if(historicoColaboradorManager.existeHistoricoComFuncao(colaboradorId, DateUtil.criarDataDiaMesAno(dataHistorico)))
			throw new Exception("Não é possível inserir uma ordem de serviço para esta data pois o colaborador não possui um histórico com função nesta data");
		
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
