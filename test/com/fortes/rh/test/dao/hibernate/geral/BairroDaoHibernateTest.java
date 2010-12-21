package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class BairroDaoHibernateTest extends GenericDaoHibernateTest<Bairro>
{
	private BairroDao bairroDao;
	private CidadeDao cidadeDao;
	private EstadoDao estadoDao;
	private SolicitacaoDao solicitacaoDao;

	public Bairro getEntity()
	{
		Bairro bairro = new Bairro();
		bairro.setNome("bairro");
		bairro.setCidade(null);

		return bairro;
	}

	public void testList()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);

		Cidade cidade2 = CidadeFactory.getEntity();
		cidade2.setUf(estado);
		cidade2 = cidadeDao.save(cidade2);

		Bairro bairro1 = new Bairro();
		bairro1.setNome("a");
		bairro1.setCidade(cidade1);
		bairro1 = bairroDao.save(bairro1);

		Bairro bairro2 = new Bairro();
		bairro2.setNome("b");
		bairro2.setCidade(cidade1);
		bairro2 = bairroDao.save(bairro2);

		Bairro bairro3 = new Bairro();
		bairro3.setNome("ba");
		bairro3.setCidade(cidade2);
		bairro3 = bairroDao.save(bairro3);

		// testa busca por cidadeId
		Bairro bairro = new Bairro();
		bairro.setCidade(cidade1);

		Collection<Bairro> bairros = bairroDao.list(1, 10, bairro);

		assertEquals(2, bairros.size());
		// testa ordem da busca
		Bairro bairroDoBanco = (Bairro) bairros.toArray()[0];
		assertEquals(bairro1.getId(), bairroDoBanco.getId());

		// testa busca por bairroNome e cidadeId
		bairros = bairroDao.list(1, 10, bairro1);
		assertEquals(1, bairros.size());
	}

	public void testExisteBairro()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);

		Bairro bairro1 = new Bairro();
		bairro1.setNome("testeTeste");
		bairro1.setCidade(cidade1);
		bairro1 = bairroDao.save(bairro1);

		Bairro bairro2 = new Bairro();
		bairro2.setNome("Testeteste");
		bairro2.setCidade(cidade1);
		bairro2 = bairroDao.save(bairro2);

		assertTrue(bairroDao.existeBairro(bairro2));
	}
	
	public void testExisteBairroUpdate()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);
		
		Bairro bairro1 = new Bairro();
		bairro1.setNome("Testetes");
		bairro1.setCidade(cidade1);
		bairro1 = bairroDao.save(bairro1);
		
		assertFalse(bairroDao.existeBairro(bairro1));
	}

	public void testFindAllSelect()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);

		Bairro bairro1 = new Bairro();
		bairro1.setNome("Teste");
		bairro1.setCidade(cidade1);
		bairro1 = bairroDao.save(bairro1);

		Bairro bairro2 = new Bairro();
		bairro2.setNome("teste");
		bairro2.setCidade(cidade1);
		bairro2 = bairroDao.save(bairro2);

		assertEquals(2, bairroDao.findAllSelect(cidade1.getId()).size());
	}
	
	public void testGetBairrosByIds()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);
		
		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);
		
		Bairro bairro1 = new Bairro();
		bairro1.setNome("Teste");
		bairro1.setCidade(cidade1);
		bairro1 = bairroDao.save(bairro1);
		
		Bairro bairro2 = new Bairro();
		bairro2.setNome("teste");
		bairro2.setCidade(cidade1);
		bairro2 = bairroDao.save(bairro2);
		
		Long[] bairroIds = new Long[]{bairro1.getId(), bairro2.getId()};
		
		assertEquals(2, bairroDao.getBairrosByIds(bairroIds).size());
	}
	
	public void testFindByIdProjection()
	{
		Bairro bairro = new Bairro();
		bairro.setNome("Teste");
		bairro = bairroDao.save(bairro);
		
		assertEquals(bairro, bairroDao.findByIdProjection(bairro.getId()));
	}
	
	public void testFindBairrosNomes()
	{
		Bairro bairro = new Bairro();
		bairro.setNome("Teste");
		bairro = bairroDao.save(bairro);
		
		assertEquals(true, bairroDao.findBairrosNomes().size() > 0);
	}

	public void testGetBairrosBySolicitacao()
	{
		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade1 = CidadeFactory.getEntity();
		cidade1.setUf(estado);
		cidade1 = cidadeDao.save(cidade1);

		Bairro bairro1 = new Bairro();
		bairro1.setNome("Teste");
		bairro1.setCidade(cidade1);
		bairro1 = bairroDao.save(bairro1);

		Bairro bairro2 = new Bairro();
		bairro2.setNome("teste");
		bairro2.setCidade(cidade1);
		bairro2 = bairroDao.save(bairro2);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro1);
		bairros.add(bairro2);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setBairros(bairros);
		solicitacao.setAreaOrganizacional(null);
		solicitacao.setFaixaSalarial(null);
		solicitacao = solicitacaoDao.save(solicitacao);

		assertEquals(2, bairroDao.getBairrosBySolicitacao(solicitacao.getId()).size());
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}

	public GenericDao<Bairro> getGenericDao()
	{
		return bairroDao;
	}

	public void setBairroDao(BairroDao bairroDao)
	{
		this.bairroDao = bairroDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao)
	{
		this.cidadeDao = cidadeDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

}