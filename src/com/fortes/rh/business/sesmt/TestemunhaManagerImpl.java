package com.fortes.rh.business.sesmt;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TestemunhaDao;
import com.fortes.rh.model.sesmt.Testemunha;

public class TestemunhaManagerImpl extends GenericManagerImpl<Testemunha, TestemunhaDao> implements TestemunhaManager
{
	public void removeTestemunha(Long idTestemunha, String nomeAtributo) {
		removerDependenciaComCat(idTestemunha, nomeAtributo);
		getDao().remove(idTestemunha);
	}
	
	private void removerDependenciaComCat(Long idTestemunha, String nomeAtributo){
		getDao().removerDependenciaComCat(idTestemunha, nomeAtributo);
	}
}