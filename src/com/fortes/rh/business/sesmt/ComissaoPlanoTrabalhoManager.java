package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public interface ComissaoPlanoTrabalhoManager extends GenericManager<ComissaoPlanoTrabalho>
{
	ComissaoPlanoTrabalho findByIdProjection(Long id);
	Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId);
	void removeByComissao(Long id);
	Collection<ComissaoPlanoTrabalho> findImprimirPlanoTrabalho(Long comissaoId) throws ColecaoVaziaException;
}