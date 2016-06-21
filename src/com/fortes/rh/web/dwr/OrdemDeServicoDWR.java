package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.util.DateUtil;

public class OrdemDeServicoDWR {
	
	private OrdemDeServicoManager ordemDeServicoManager;
	private EmpresaManager empresaManager;

	public OrdemDeServico recarregaDadosOrdemDeServico(Long idOdemDeServico, Long colaboradorId, Long empresaId, String dataOrdemDeServico){
		OrdemDeServico ordemDeServico = new OrdemDeServico();
		ordemDeServico.setId(idOdemDeServico);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
		
		Empresa empresa = empresaManager.findByIdProjection(empresaId); 
		ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(ordemDeServico, colaborador, empresa, DateUtil.criarDataDiaMesAno(dataOrdemDeServico));
		return ordemDeServico;
	}

	public void setOrdemDeServicoManager(OrdemDeServicoManager ordemDeServicoManager) {
		this.ordemDeServicoManager = ordemDeServicoManager;
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}
