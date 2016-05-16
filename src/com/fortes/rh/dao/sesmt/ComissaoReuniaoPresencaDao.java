package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;

/**
 * @author Tiago Lopes
 */
public interface ComissaoReuniaoPresencaDao extends GenericDao<ComissaoReuniaoPresenca>
{
	Collection<ComissaoReuniaoPresenca> findByReuniao(Long comissaoReuniaoId);
	void removeByReuniao(Long comissaoReuniaoId);
	Collection<ComissaoReuniaoPresenca> findByComissao(Long comissaoId, boolean ordenarPorDataNome);
	boolean existeReuniaoPresenca(Long comissaoId, Collection<Long> colaboradorIds);
	Collection<ComissaoReuniaoPresenca> findPresencasByComissao(Long comissaoId);
	List<ComissaoReuniaoPresenca> findPresencaColaboradoresByReuniao(Long comissaoReuniaoId, Date dataReuniao);
}