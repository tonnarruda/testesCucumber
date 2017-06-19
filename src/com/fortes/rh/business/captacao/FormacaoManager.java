/* autor: Moesio Medeiros
 * Data: 16/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
 */

package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

public interface FormacaoManager extends GenericManager<Formacao>
{
	public void removeCandidato(Candidato candidato);
	public void removeColaborador(Colaborador colaborador);
	public Collection<Formacao> findInCandidatos(Long[] candidatoIds);
	public void montaFormacaosBDS(Collection<Formacao> formacaos, Candidato candidato);
	public Collection<Formacao> findByColaborador(Long id);
	public Collection<Formacao> findByCandidato(Long candidatoId);
	public Collection<Formacao> retornaListaSemDuplicados(Collection<Formacao> formacaos);
	public boolean checaMesmoElemento(Formacao formacao,Collection<Formacao> formacaos );
}