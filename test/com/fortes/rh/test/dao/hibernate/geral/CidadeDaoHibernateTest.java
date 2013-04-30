package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;

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
		estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1.setCodigoAC("00123");
		cidadeDao.save(cidade1);
		
		Cidade cidade2 = CidadeFactory.getEntity();
		cidade2.setUf(estado);
		cidade2.setCodigoAC("");
		cidadeDao.save(cidade2);
		
		Cidade cidade3 = CidadeFactory.getEntity();
		cidade3.setUf(estado);
		cidade3.setCodigoAC(null);
		cidadeDao.save(cidade3);
		
		Collection<Cidade> cidades = cidadeDao.findSemCodigoAC();
		
		assertFalse(cidades.contains(cidade1));
		assertTrue(cidades.contains(cidade2));
		assertTrue(cidades.contains(cidade3));
		
	}
	
	public void testFindCodigoACDuplicado_SEM_Duplicacao() {
		
		Estado CE = EstadoFactory.getEntity(1L);
		CE.setNome("Ceara");
		estadoDao.save(CE);

		Estado RN = EstadoFactory.getEntity(1L);
		RN.setNome("Rio Grande do NOrte");
		estadoDao.save(RN);
		
		Cidade fortaleza = CidadeFactory.getEntity();
		fortaleza.setCodigoAC("0001");
		fortaleza.setUf(CE);
		cidadeDao.save(fortaleza);

		Cidade natal = CidadeFactory.getEntity();
		natal.setCodigoAC("0001");
		natal.setUf(RN);
		cidadeDao.save(natal);

		assertEquals("", cidadeDao.findCodigoACDuplicado());
	}
	
	public void testFindCodigoACDuplicado_COM_Duplicacao() {
		
		Estado CE = EstadoFactory.getEntity(1L);
		CE.setNome("Ceara");
		estadoDao.save(CE);
		
		Estado RN = EstadoFactory.getEntity(1L);
		RN.setNome("Rio Grande do NOrte");
		estadoDao.save(RN);
		
		Cidade fortaleza = CidadeFactory.getEntity();
		fortaleza.setNome("Fortaleza");
		fortaleza.setCodigoAC("0001");
		fortaleza.setUf(CE);
		cidadeDao.save(fortaleza);
		
		Cidade natal = CidadeFactory.getEntity();
		natal.setNome("Natal");
		natal.setCodigoAC("0001");
		natal.setUf(RN);
		cidadeDao.save(natal);
		
		Cidade pipa = CidadeFactory.getEntity();
		pipa.setNome("Pipa");
		pipa.setUf(RN);
		pipa.setCodigoAC("0001");
		cidadeDao.save(pipa);
		
		assertEquals("0001", cidadeDao.findCodigoACDuplicado());
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