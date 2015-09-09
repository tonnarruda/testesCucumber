package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.TamanhoEPIDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.dao.sesmt.TipoTamanhoEPIDao;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class TipoTamanhoEpiDaoHibernateTest extends GenericDaoHibernateTest<TipoTamanhoEPI>
{
	private TipoTamanhoEPIDao tipoTamanhoEPIDao;
	private TamanhoEPIDao tamanhoEPIDao;
	private TipoEPIDao tipoEPIDao;
	
	public TipoTamanhoEPI getEntity() {
		TipoTamanhoEPI tipoTamanhoEPI = new TipoTamanhoEPI();

		tipoTamanhoEPI.setId(null);
//		tipoTamanhoEPI.setDescricao("descrição tamanho");

		return tipoTamanhoEPI;
	}

	public GenericDao<TipoTamanhoEPI> getGenericDao()
	{
		return tipoTamanhoEPIDao;
	}

	public void setTipoEPIDao(TipoTamanhoEPI tipoTamanhoEPIDao) {
		this.tipoEPIDao = tipoEPIDao;
	}

	public void setTamanhoEPIDao(TamanhoEPIDao tamanhoEPIDao) {
		this.tamanhoEPIDao = tamanhoEPIDao;
	}

	public void setTipoTamanhoEPIDao(TipoTamanhoEPIDao tipoTamanhoEPIDao) {
		this.tipoTamanhoEPIDao = tipoTamanhoEPIDao;
	}
}