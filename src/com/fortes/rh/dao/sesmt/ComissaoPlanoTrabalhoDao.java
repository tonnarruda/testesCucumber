package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public interface ComissaoPlanoTrabalhoDao extends GenericDao<ComissaoPlanoTrabalho>
{
	public Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId, String situacao, Long responsavelId, Long corresponsavelId);
	ComissaoPlanoTrabalho findByIdProjection(Long id);
	void removeByComissao(Long comissaoId);
}