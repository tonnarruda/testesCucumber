package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public interface ComissaoPlanoTrabalhoManager extends GenericManager<ComissaoPlanoTrabalho>
{
	Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId, String situacao, Long responsavelId, Long corresponsavelId);
	ComissaoPlanoTrabalho findByIdProjection(Long id);
	void removeByComissao(Long id);
	Collection<ComissaoPlanoTrabalho> findImprimirPlanoTrabalho(Long comissaoId, String situacao, Long responsavelId, Long corresponsavelId) throws ColecaoVaziaException;
}