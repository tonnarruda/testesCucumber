/* Autor: Robertson Freitas
 * Data: 04/07/2006
 * Requisito: RFA019 - Solicitar Banco de Dados Solidário
 */

package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.model.captacao.SolicitacaoBDS;

public interface SolicitacaoBDSManager extends GenericManager<SolicitacaoBDS>
{
	boolean validaArquivoBDS(File arquivoBDS);
}