package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Lnt;

public interface LntDao extends GenericDao<Lnt> 
{
	Collection<Lnt> findAllLnt(String descricao, char status, int page, int pagingSize);
	Collection<Lnt> findLntsNaoFinalizadas(Date dataInicio);
	void finalizar(Long lntId);
	void reabrir(Long lntId);
	Collection<Lnt> findPossiveisLntsColaborador(Long cursoId, Long colaboradorId);
	Long findLntColaboradorParticpa(Long cursoId, Long colaboradorId);
}
