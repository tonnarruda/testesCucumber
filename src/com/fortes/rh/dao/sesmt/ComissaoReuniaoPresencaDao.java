package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;

/**
 * @author Tiago Lopes
 */
public interface ComissaoReuniaoPresencaDao extends GenericDao<ComissaoReuniaoPresenca>
{
	Collection<ComissaoReuniaoPresenca> findByReuniao(Long comissaoReuniaoId);
	void removeByReuniao(Long comissaoReuniaoId);
	Collection<ComissaoReuniaoPresenca> findByComissao(Long comissaoId);
}