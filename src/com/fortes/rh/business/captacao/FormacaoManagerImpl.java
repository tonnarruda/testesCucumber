/* autor: Moesio Medeiros
 * data: 16/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
 */

package com.fortes.rh.business.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

@Component
public class FormacaoManagerImpl extends GenericManagerImpl<Formacao, FormacaoDao> implements FormacaoManager
{
	@Autowired
	public FormacaoManagerImpl(FormacaoDao fooDao) {
        setDao(fooDao);
    }
	
	public void removeCandidato(Candidato candidato)
	{
		getDao().removeCandidato(candidato);
	}

	public void removeColaborador(Colaborador colaborador)
	{
		getDao().removeColaborador(colaborador);
	}

	public Collection<Formacao> findInCandidatos(Long[] candidatoIds)
	{
		return getDao().findInCandidatos(candidatoIds);
	}

	public void montaFormacaosBDS(Collection<Formacao> formacaos, Candidato candidato)
	{
		for (Formacao formacao : formacaos)
		{
			formacao.setId(null);
			formacao.setColaborador(null);
			formacao.setAreaFormacao(null);
			formacao.setCandidato(candidato);

			save(formacao);
		}
	}

	public Collection<Formacao> findByColaborador(Long colaboradorId)
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public Collection<Formacao> findByCandidato(Long candidatoId)
	{
		return getDao().findByCandidato(candidatoId);
	}
}