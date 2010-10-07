package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public interface ComissaoPlanoTrabalhoDao extends GenericDao<ComissaoPlanoTrabalho>
{
	ComissaoPlanoTrabalho findByIdProjection(Long id);
	Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId);
	void removeByComissao(Long comissaoId);
}