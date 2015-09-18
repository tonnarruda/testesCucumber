package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.TamanhoEPIDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.dao.sesmt.TipoTamanhoEPIDao;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.TipoEpiFactory;

public class TipoTamanhoEPIDaoHibernateTest extends GenericDaoHibernateTest<TipoTamanhoEPI> {
	private TipoTamanhoEPIDao tipoTamanhoEPIDao;
	private TamanhoEPIDao tamanhoEPIDao;
	private TipoEPIDao tipoEPIDao;

	public GenericDao<TipoTamanhoEPI> getGenericDao()
	{
		return tipoTamanhoEPIDao;
	}

	@Override
	public TipoTamanhoEPI getEntity() {
		TipoTamanhoEPI tipoTamanhoEPI = new TipoTamanhoEPI();
		TamanhoEPI tamanhoEPI = new TamanhoEPI();
		tamanhoEPI.setId(1L);
		tamanhoEPI.setDescricao("Tamanho");
		tamanhoEPIDao.save(tamanhoEPI);
		
		TipoEPI tipoEPI = TipoEpiFactory.getEntity();
		tipoEPIDao.save(tipoEPI);
		
		tipoTamanhoEPI.setId(1L);
		tipoTamanhoEPI.setTipoEPI(tipoEPI);
		tipoTamanhoEPI.setTamanhoEPIs(tamanhoEPI);
		tipoTamanhoEPI.setAtivo(true);
		tipoTamanhoEPIDao.save(tipoTamanhoEPI);
		
		return tipoTamanhoEPI;
	}
	
	public void testRemoveByTipoEPI()
	{
		TamanhoEPI tamanhoEPI = new TamanhoEPI();
		tamanhoEPI.setId(1L);
		tamanhoEPI.setDescricao("Tamanho");
		tamanhoEPIDao.save(tamanhoEPI);
		
		TipoEPI tipoEPI = TipoEpiFactory.getEntity();
		tipoEPIDao.save(tipoEPI);
		
		TipoTamanhoEPI tipoTamanhoEPI = new TipoTamanhoEPI();
		tipoTamanhoEPI.setId(1L);
		tipoTamanhoEPI.setTipoEPI(tipoEPI);
		tipoTamanhoEPI.setTamanhoEPIs(tamanhoEPI);
		tipoTamanhoEPI.setAtivo(true);
		tipoTamanhoEPIDao.save(tipoTamanhoEPI);
		
		tipoTamanhoEPIDao.removeByTipoEPI(tipoEPI.getId());
		
		Collection<TipoTamanhoEPI> tipoTamanhoEPIs = tipoTamanhoEPIDao.find(new String[]{"tipoEPI.id"},new Object[]{tipoEPI.getId()});
		assertEquals("Turma 1", 0, tipoTamanhoEPIs.size());
	}

	public void setTipoTamanhoEPIDao(TipoTamanhoEPIDao tipoTamanhoEPIDao) {
		this.tipoTamanhoEPIDao = tipoTamanhoEPIDao;
	}

	public void setTamanhoEPIDao(TamanhoEPIDao tamanhoEPIDao) {
		this.tamanhoEPIDao = tamanhoEPIDao;
	}

	public void setTipoEPIDao(TipoEPIDao tipoEPIDao) {
		this.tipoEPIDao = tipoEPIDao;
	}
}