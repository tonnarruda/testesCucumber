package com.fortes.rh.test.dao.hibernate.cargosalario;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;

public class IndiceDaoHibernateTest extends GenericDaoHibernateTest<Indice>
{
	private IndiceDao indiceDao;
	private IndiceHistoricoDao indiceHistoricoDao;
	private GrupoACDao grupoACDao;

	public Indice getEntity()
	{
		return IndiceFactory.getEntity();
	}

	public GenericDao<Indice> getGenericDao()
	{
		return indiceDao;
	}

	public void testFindByIdProjection()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Indice indiceRetorno = indiceDao.findByIdProjection(indice.getId());
		assertEquals(indice, indiceRetorno);
	}

	public void testFindByCodigo()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0013254765");
		indice.setGrupoAC(grupoAC.getCodigo());
		indice = indiceDao.save(indice);

		Indice indiceRetorno = indiceDao.findByCodigo("0013254765", "XXX");
		assertEquals(indice, indiceRetorno);
	}

	public void testRemoveByCodigo()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0013254765");
		indice.setGrupoAC(grupoAC.getCodigo());
		indice = indiceDao.save(indice);

		assertTrue(indiceDao.remove("0013254765", "XXX"));
		assertNull(indiceDao.findByCodigo("0013254765", "XXX"));
	}

	public void testFindHistoricoAtual()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		indiceHistorico.setIndice(indice);

		Indice retorno = indiceDao.findHistoricoAtual(indice.getId(), null);

		assertEquals(indice.getId(), retorno.getId());
	}

	public void testFindIndiceByCodigoAc()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("010203");
		indice.setGrupoAC(grupoAC.getCodigo());
		indice = indiceDao.save(indice);
		
		Indice indiceRetorno = indiceDao.findIndiceByCodigoAc(indice.getCodigoAC(), "XXX");
		
		assertEquals(indice, indiceRetorno);
	}

	public void testFindSemCodigoAC() {
		
		Indice indice1 = IndiceFactory.getEntity();
		indice1.setCodigoAC("1");
		indiceDao.save(indice1);
		
		Indice indice2 = IndiceFactory.getEntity();
		indice2.setCodigoAC("");
		indiceDao.save(indice2);
		
		Indice indice3 = IndiceFactory.getEntity();
		indice3.setCodigoAC(null);
		indiceDao.save(indice3);
		
		assertEquals(2, indiceDao.findSemCodigoAC().size());		
	}
	
	public void setIndiceDao(IndiceDao indiceDao)
	{
		this.indiceDao = indiceDao;
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao)
	{
		this.indiceHistoricoDao = indiceHistoricoDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}
}