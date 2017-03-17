package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoPlanoTrabalhoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

@Component
public class ComissaoPlanoTrabalhoManagerImpl extends GenericManagerImpl<ComissaoPlanoTrabalho, ComissaoPlanoTrabalhoDao> implements ComissaoPlanoTrabalhoManager
{
	@Autowired
	ComissaoPlanoTrabalhoManagerImpl(ComissaoPlanoTrabalhoDao fooDao) {
		setDao(fooDao);
	}
	
	public Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId, String situacao, Long responsavelId, Long corresponsavelId)
	{
		return getDao().findByComissao(comissaoId, situacao, responsavelId, corresponsavelId);
	}
	
	public ComissaoPlanoTrabalho findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void removeByComissao(Long comissaoId)
	{
		getDao().removeByComissao(comissaoId);
	}

	public Collection<ComissaoPlanoTrabalho> findImprimirPlanoTrabalho(Long comissaoId, String situacao, Long responsavelId, Long corresponsavelId) throws ColecaoVaziaException
	{
		Collection<ComissaoPlanoTrabalho> colecao = getDao().findByComissao(comissaoId, situacao, responsavelId, corresponsavelId);

		if (colecao.isEmpty())
		{
			throw new ColecaoVaziaException("Nenhum plano de trabalho para esta comissão.");
		}

		return colecao;
	}
}