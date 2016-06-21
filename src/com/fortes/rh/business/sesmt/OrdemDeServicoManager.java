package com.fortes.rh.business.sesmt;

import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;

public interface OrdemDeServicoManager extends GenericManager<OrdemDeServico>
{
	public OrdemDeServico findOrdemServicoProjection(Long id);
	public OrdemDeServico montaOrdemDeServico(OrdemDeServico ordemDeServico, Colaborador colaborador, Empresa empresaSistema, Date dataOdemDeServico);
	public OrdemDeServico ordemDeServicoAtual(Long colaboradorId);
}
