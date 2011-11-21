package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.util.CollectionUtil;

public class CidadeDaoHibernateTest extends GenericDaoHibernateTest<Cidade>
{
	private CidadeDao cidadeDao;
	private EstadoDao estadoDao;

	public Cidade getEntity()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);

		return cidade;
	}

	public void testFindAllSelect()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);

		assertEquals(1, cidadeDao.findAllSelect(estado.getId()).size());
	}
	
	public void testFindByNome()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setNome("Párácuru");//o acento faz parte do teste
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);
		
		assertEquals("Párácuru", cidadeDao.findByNome("ParacÚrÚ", estado.getId()).getNome());
	}

	public void testFindByCodigoAC()
	{
		Estado estado = EstadoFactory.getEntity();
		estado.setSigla("XY");
		estado = estadoDao.save(estado);

		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1.setCodigoAC("00123");
		cidade1 = cidadeDao.save(cidade1);

		Cidade cidadeRetorno = cidadeDao.findByCodigoAC(cidade1.getCodigoAC(), estado.getSigla());

		assertEquals(cidade1.getId(), cidadeRetorno.getId());
		assertEquals(estado.getId(), cidadeRetorno.getUf().getId());
	}
	
	public void testFindAllByUf()
	{
		Estado estado = EstadoFactory.getEntity();
		estado.setSigla("XY");
		estado = estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);
		
		assertEquals(1, cidadeDao.findAllByUf(estado.getSigla()).size());
	}
	
	public void testFindByIdProjection()
	{
		Estado estado = EstadoFactory.getEntity();
		estado.setSigla("XY");
		estado = estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1.setCodigoAC("00123");
		cidade1 = cidadeDao.save(cidade1);
		
		Cidade cidadeRetorno = cidadeDao.findByIdProjection(cidade1.getId());
		
		assertEquals(cidade1.getId(), cidadeRetorno.getId());
		assertEquals(estado.getId(), cidadeRetorno.getUf().getId());
	}

	public void testFindSemCodigoAC() {
		
		Estado estado = EstadoFactory.getEntity();
		estado.setSigla("XY");
		estado = estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity(1L);
		cidade1.setUf(estado);
		cidade1.setCodigoAC("00123");
		cidade1 = cidadeDao.save(cidade1);
		
		Cidade cidade2 = CidadeFactory.getEntity(2L);
		cidade2.setUf(estado);
		cidade2.setCodigoAC("");
		cidade2 = cidadeDao.save(cidade2);
		
		Cidade cidade3 = CidadeFactory.getEntity(3L);
		cidade3.setUf(estado);
		cidade3.setCodigoAC(null);
		cidade3 = cidadeDao.save(cidade3);
		
		Collection<Cidade> cidades = cidadeDao.findSemCodigoAC();
		
		Long[] ids = new CollectionUtil<Cidade>().convertCollectionToArrayIds(cidades);
		
		List<Long> lista = Arrays.asList(ids);
		
		assertFalse(lista.contains(1L));
		assertTrue(lista.contains(2L));
		assertTrue(lista.contains(3L));
//		assertEquals(2, cidadeDao.findSemCodigoAC().size());
		
		
	}
	
	public void setCidadeDao(CidadeDao cidadeDao)
	{
		this.cidadeDao = cidadeDao;
	}

	public GenericDao<Cidade> getGenericDao()
	{
		return cidadeDao;
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}
}