package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class GrupoGastoDaoHibernateTest extends GenericDaoHibernateTest<GrupoGasto>
{
	private GrupoGastoDao grupoGastoDao;

	public GrupoGasto getEntity()
	{
		GrupoGasto grupoGasto = new GrupoGasto();

		grupoGasto.setId(null);
		grupoGasto.setNome("grupo gasto");
		grupoGasto.setEmpresa(null);

		return grupoGasto;
	}

	public void testFindByIdProjection()
	{
		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto = grupoGastoDao.save(grupoGasto);

		GrupoGasto grupoGastoRetorno = grupoGastoDao.findByIdProjection(grupoGasto.getId());

		assertEquals(grupoGasto, grupoGastoRetorno);
	}

	public GenericDao<GrupoGasto> getGenericDao()
	{
		return grupoGastoDao;
	}

	public void setGrupoGastoDao(GrupoGastoDao grupoGastoDao)
	{
		this.grupoGastoDao = grupoGastoDao;
	}

}