package com.fortes.rh.util;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;

public class HistoricoColaboradorUtil
{
	/**
	 * Se Mudar o salário, é vertical<br>
	 * Se não mudar salário, é horizontal (somente cargo ou faixa)<br>
	 * Se mudar somente a área, é transferencia (não muda cargo, nem faixa, nem salário)<br>
	 * Se for discídio é reajuste (NÃO VAI SER FEITO AGORA)
	 * @param reajuste: Reajuste a ser analisado
	 * @return motivo: Motivo do Reajuste
	 */
	public static String getMotivoReajuste(ReajusteColaborador reajuste, HistoricoColaborador historicoColaborador)
	{
		if(!reajuste.getSalarioAtual().equals(reajuste.getSalarioProposto()))
			return MotivoHistoricoColaborador.PROMOCAO;
		else
			if(reajuste.getFaixaSalarialAtual().getCargo().getId() != reajuste.getFaixaSalarialProposta().getCargo().getId() ||
			reajuste.getFaixaSalarialAtual().getId() != reajuste.getFaixaSalarialProposta().getId())
				return MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL;

		if(reajuste.getAreaOrganizacionalProposta().getId() != historicoColaborador.getAreaOrganizacional().getId())
			return MotivoHistoricoColaborador.TRANSFERENCIA;

		return MotivoHistoricoColaborador.MUDANCA_FUNCAO;
	}

}
