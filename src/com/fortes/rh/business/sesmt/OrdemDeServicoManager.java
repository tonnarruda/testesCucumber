package com.fortes.rh.business.sesmt;

import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.security.spring.aop.callback.OrdemDeServicoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface OrdemDeServicoManager extends GenericManager<OrdemDeServico>
{
	public OrdemDeServico findOrdemServicoProjection(Long id);
	public OrdemDeServico montaOrdemDeServico(Long colaboradorId, Empresa empresaSistema, Date dataOdemDeServico);
	public OrdemDeServico findUltimaOrdemDeServico(Long colaboradorId);
	public OrdemDeServico findUltimaOrdemDeServicoImpressa(Long colaboradorId);
	@Audita(operacao="Imprimir Ordem de Serviço", auditor=OrdemDeServicoAuditorCallbackImpl.class)
	public void auditaImpressao(OrdemDeServico ordemDeServico, Usuario usuarioLogado);
}
