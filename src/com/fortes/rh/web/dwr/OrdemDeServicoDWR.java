package com.fortes.rh.web.dwr;

import java.util.Date;

import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;

public class OrdemDeServicoDWR {
	
	private OrdemDeServicoManager ordemDeServicoManager;
	
	public OrdemDeServico recarregaDadosOrdemDeServico(Long idOdemDeServico, Empresa empresa, Date dataOrdemDeServico){
		OrdemDeServico ordemDeServico = new OrdemDeServico();
		ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(ordemDeServico, null, empresa, dataOrdemDeServico);
		return ordemDeServico;
	}
	

}
