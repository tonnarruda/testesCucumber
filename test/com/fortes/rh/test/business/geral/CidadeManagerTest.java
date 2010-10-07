package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.CidadeManagerImpl;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class CidadeManagerTest extends MockObjectTestCase
{
	CidadeManagerImpl cidadeManager = null;
	Mock cidadeDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		cidadeManager = new CidadeManagerImpl();

		cidadeDao = new Mock(CidadeDao.class);
		cidadeManager.setDao((CidadeDao) cidadeDao.proxy());
	}

	public void testFindAllSelect()
	{
		Cidade cidade = new Cidade();
		cidade.setId(1L);

		Collection<Cidade> cidades = new ArrayList<Cidade>();
		cidades.add(cidade);

		cidadeDao.expects(once()).method("findAllSelect").with(eq(cidade.getId())).will(returnValue(cidades));

		assertEquals(1, cidadeManager.findAllSelect(cidade.getId()).size());
	}

	public void testFindByCodigoAC()
	{
		Cidade cidade = new Cidade();
		cidade.setId(1L);
		cidade.setCodigoAC("001");
		
		String sigla = "CE";

		cidadeDao.expects(once()).method("findByCodigoAC").with(eq(cidade.getCodigoAC()), eq(sigla)).will(returnValue(cidade));

		assertEquals(cidade, cidadeManager.findByCodigoAC(cidade.getCodigoAC(), sigla));

	}
	
	public void testFindByEstado()
	{
		Cidade cidade = new Cidade();
		cidade.setId(1L);

		Collection<Cidade> cidades = new ArrayList<Cidade>();
		cidades.add(cidade);

		Estado estado = EstadoFactory.getEntity(1L);
		
		cidadeDao.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(cidades));		
		assertEquals(1, cidadeManager.findByEstado(estado).size());
	}
	
	public void testFindByEstadoNull()
	{
		Estado estado = null;
		assertEquals(0, cidadeManager.findByEstado(estado).size());
	}
}
