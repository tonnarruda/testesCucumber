package com.fortes.rh.business.cargosalario;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.ReajusteIndiceDao;
import com.fortes.rh.model.cargosalario.ReajusteIndice;

public class ReajusteIndiceManagerImpl extends GenericManagerImpl<ReajusteIndice, ReajusteIndiceDao> implements ReajusteIndiceManager
{
	private IndiceManager indiceManager;

	public void setIndiceManager(IndiceManager indiceManager) 
	{
		this.indiceManager = indiceManager;
	}
}