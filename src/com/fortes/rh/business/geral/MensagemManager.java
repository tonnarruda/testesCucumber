package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Mensagem;

public interface MensagemManager extends GenericManager<Mensagem>
{
	String formataMensagemCancelamentoFaixaSalarialHistorico(String mensagem, FaixaSalarialHistorico faixaSalarialHistorico);

	String formataMensagemCancelamentoHistoricoColaborador(String mensagem, HistoricoColaborador historicoColaborador);
}