package com.fortes.rh.business.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TestemunhaDao;
import com.fortes.rh.model.sesmt.Testemunha;

@Component
public class TestemunhaManagerImpl extends GenericManagerImpl<Testemunha, TestemunhaDao> implements TestemunhaManager
{
	@Autowired 
	public TestemunhaManagerImpl(TestemunhaDao testemunhaDao) {
		setDao(testemunhaDao);
	}
	
	public void removeTestemunha(Long idTestemunha, String nomeAtributo) {
		removerDependenciaComCat(idTestemunha, nomeAtributo);
		getDao().remove(idTestemunha);
	}
	
	private void removerDependenciaComCat(Long idTestemunha, String nomeAtributo){
		getDao().removerDependenciaComCat(idTestemunha, nomeAtributo);
	}
}