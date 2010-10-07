/* Autor: Robertson Freitas
 * Data: 04/07/2006
 * Requisito: RFA019 - Solicitar Banco de Dados Solidário
 */

package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.dao.captacao.SolicitacaoBDSDao;
import com.fortes.rh.model.captacao.SolicitacaoBDS;

public class SolicitacaoBDSManagerImpl extends GenericManagerImpl<SolicitacaoBDS, SolicitacaoBDSDao> implements SolicitacaoBDSManager
{
	public boolean validaArquivoBDS(File arquivoBDS)
	{
		if(arquivoBDS != null)
		{
			String nomeArquivo = arquivoBDS.getName();
			int posicPonto = nomeArquivo.lastIndexOf(".");
			String extensao = nomeArquivo.substring(posicPonto, nomeArquivo.length());

			if(extensao.equals(".fortesrh"))
				return true;
		}

		return false;
	}
}