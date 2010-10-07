package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoReuniao;

/**
 * @author Tiago Teixeira Lopes
 */
public interface ComissaoReuniaoDao extends GenericDao<ComissaoReuniao>
{
	ComissaoReuniao findByIdProjection(Long id);
	Collection<ComissaoReuniao> findByComissao(Long comissaoId);
}