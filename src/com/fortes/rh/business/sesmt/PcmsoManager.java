package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PCMSO;


public interface PcmsoManager
{
	Collection<PCMSO> montaRelatorio(Date dataIni, Date dataFim, Estabelecimento estabelecimento, Long empresaId, boolean exibirAgenda, boolean exibirDistColaboradorSetor, boolean exibirRiscos, boolean exibirEpis, boolean exibirExames, boolean exibirAcidentes, boolean exibirComposicaoSesmt) throws Exception;
}