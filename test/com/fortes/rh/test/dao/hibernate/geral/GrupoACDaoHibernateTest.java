package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TGrupo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.GrupoACFactory;

public class GrupoACDaoHibernateTest extends GenericDaoHibernateTest<GrupoAC>
{
	private GrupoACDao grupoACDao;

	@Override
	public GrupoAC getEntity()
	{
		return GrupoACFactory.getEntity();
	}

	@Override
	public GenericDao<GrupoAC> getGenericDao()
	{
		return grupoACDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao)
	{
		this.grupoACDao = grupoACDao;
	}

	public void testFindTGrupos()
	{
		GrupoAC grupo1 = new GrupoAC();
		grupo1.setDescricao("Lagoa");
		grupo1.setCodigo("XXX");
		grupoACDao.save(grupo1);

		GrupoAC grupo2 = new GrupoAC();
		grupo2.setDescricao("Comete");
		grupo2.setCodigo("KKK");
		grupoACDao.save(grupo2);
		
		TGrupo[] grupos = grupoACDao.findTGrupos();
		
		int size = grupos.length;
		assertTrue( size >= 2);
		
		assertEquals("XXX", grupos[size - 1].getCodigo());
	}
	
	public void testFindByCodigo()
	{
		GrupoAC grupo1 = new GrupoAC();
		grupo1.setDescricao("Lagoa");
		grupo1.setCodigo("XXX");
		grupoACDao.save(grupo1);
		
		GrupoAC grupo2 = new GrupoAC();
		grupo2.setDescricao("Comete");
		grupo2.setCodigo("KKK");
		grupoACDao.save(grupo2);
		
		GrupoAC retorno = grupoACDao.findByCodigo("XXX");
		assertEquals("XXX", retorno.getCodigo());
	}
}
