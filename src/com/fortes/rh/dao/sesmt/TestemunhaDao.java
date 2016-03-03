package com.fortes.rh.dao.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Testemunha;

public interface TestemunhaDao extends GenericDao<Testemunha>
{
	public void removerDependenciaComCat(Long idTestemunha, String nomeAtributo);

}