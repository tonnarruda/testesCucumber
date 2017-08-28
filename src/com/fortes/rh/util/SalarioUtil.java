package com.fortes.rh.util;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;

public class SalarioUtil
{
	public static Double getValor(int tipoSalario, FaixaSalarial faixaSalarial, Indice indice, Double quantidade, Double valor)
	{
		switch (tipoSalario)
		{
			case TipoAplicacaoIndice.CARGO:
			{
 				if(faixaSalarial.getFaixaSalarialHistoricoAtual() != null && faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo() != null)
				{
					switch (faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo())
					{
						case TipoAplicacaoIndice.VALOR:
							return faixaSalarial.getFaixaSalarialHistoricoAtual().getValor();
						case TipoAplicacaoIndice.INDICE:
						{
							Indice indiceFaixaSalarialHistoricoAtual = faixaSalarial.getFaixaSalarialHistoricoAtual().getIndice();
							
							if(indiceFaixaSalarialHistoricoAtual!=null && indiceFaixaSalarialHistoricoAtual.getIndiceHistoricoAtual().getValor() == null)
								indiceFaixaSalarialHistoricoAtual.getIndiceHistoricoAtual().setValor(0.0);
							
							return indiceFaixaSalarialHistoricoAtual.getIndiceHistoricoAtual().getValor() * faixaSalarial.getFaixaSalarialHistoricoAtual().getQuantidade();
						}
					}					
				}
				break;
			}
			case TipoAplicacaoIndice.INDICE:
				if(indice.getIndiceHistoricoAtual() != null && indice.getIndiceHistoricoAtual().getValor() != null)
					return indice.getIndiceHistoricoAtual().getValor() * quantidade;
				else
					break;
			case TipoAplicacaoIndice.VALOR:
				return valor;
		}

		return null;
	}
}
