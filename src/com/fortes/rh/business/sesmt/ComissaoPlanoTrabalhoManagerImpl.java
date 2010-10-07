package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoPlanoTrabalhoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public class ComissaoPlanoTrabalhoManagerImpl extends GenericManagerImpl<ComissaoPlanoTrabalho, ComissaoPlanoTrabalhoDao> implements ComissaoPlanoTrabalhoManager
{
	public Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId)
	{
		return getDao().findByComissao(comissaoId);
	}

	public ComissaoPlanoTrabalho findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void removeByComissao(Long comissaoId)
	{
		getDao().removeByComissao(comissaoId);
	}

	public Collection<ComissaoPlanoTrabalho> findImprimirPlanoTrabalho(Long comissaoId) throws ColecaoVaziaException
	{
		Collection<ComissaoPlanoTrabalho> colecao = getDao().findByComissao(comissaoId);

		if (colecao.isEmpty())
		{
			throw new ColecaoVaziaException("Nenhum plano de trabalho para esta comissão.");
		}

		return colecao;
	}
}