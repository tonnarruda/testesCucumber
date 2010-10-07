package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;

public class EstabelecimentoDaoHibernateTest extends GenericDaoHibernateTest<Estabelecimento>
{
	private EstabelecimentoDao estabelecimentoDao;
	private EmpresaDao empresaDao;

	public Estabelecimento getEntity()
	{
		return EstabelecimentoFactory.getEntity();
	}

	public GenericDao<Estabelecimento> getGenericDao()
	{
		return estabelecimentoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void testRemoveByCodigo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");

		Long idEmpresa = empresaDao.save(empresa).getId();

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimento.setCodigoAC("123456");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		estabelecimentoDao.remove("123456", idEmpresa);

		Estabelecimento est = estabelecimentoDao.findByCodigo("123456", "001122");

		assertNull(est);
	}

	public void testFindByCodigo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimento.setCodigoAC("123456");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Estabelecimento est = estabelecimentoDao.findByCodigo("123456", "001122");

		assertNotNull(est);
		assertEquals("123456", est.getCodigoAC());
	}

	public void testFindEstabelecimentoCodigoAc()
	{
		Estabelecimento estabelecimentoRetorno = EstabelecimentoFactory.getEntity();
		estabelecimentoRetorno.setCodigoAC("0001");
		estabelecimentoRetorno = estabelecimentoDao.save(estabelecimentoRetorno);

		Estabelecimento estabelecimento = estabelecimentoDao.findEstabelecimentoCodigoAc(estabelecimentoRetorno.getId());

		assertEquals("0001", estabelecimento.getCodigoAC());
	}

	public void testFindEstabelecimento()
	{
		Estabelecimento estabelecimentoRetorno = EstabelecimentoFactory.getEntity();
		estabelecimentoRetorno.setNome("Mamae");
		estabelecimentoDao.save(estabelecimentoRetorno);
		
		Long[] estabelecimentosIds = {estabelecimentoRetorno.getId()};
		
		Collection<Estabelecimento> estabelecimentos = estabelecimentoDao.findEstabelecimentos(estabelecimentosIds);
		
		assertEquals(1, estabelecimentos.size());
	}

	public void testFindEstabelecimentoByCodigoAc()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC("010203");
		estabelecimento.setEmpresa(empresa);
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Estabelecimento estabelecimentoRetorno = estabelecimentoDao.findEstabelecimentoByCodigoAc(estabelecimento.getCodigoAC(), empresa.getCodigoAC());

		assertEquals(estabelecimento, estabelecimentoRetorno);
	}

	public void testVerificaCnpjExiste()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setComplementoCnpj("0001");
		estabelecimento.setEmpresa(empresa);
		estabelecimento= estabelecimentoDao.save(estabelecimento);

		Estabelecimento estabelecimentoRetorno = EstabelecimentoFactory.getEntity();
		estabelecimentoRetorno.setComplementoCnpj("0001");
		estabelecimentoRetorno.setEmpresa(empresa);
		estabelecimentoRetorno = estabelecimentoDao.save(estabelecimentoRetorno);

		boolean existeCnpj = estabelecimentoDao.verificaCnpjExiste("0001", estabelecimentoRetorno.getId(), estabelecimentoRetorno.getEmpresa().getId());

		assertTrue(existeCnpj);

		Estabelecimento estabelecimentoRetornoFalse = EstabelecimentoFactory.getEntity();
		estabelecimentoRetornoFalse.setComplementoCnpj("1256");
		estabelecimentoRetornoFalse.setEmpresa(empresa);
		estabelecimentoRetornoFalse = estabelecimentoDao.save(estabelecimentoRetornoFalse);

		existeCnpj = estabelecimentoDao.verificaCnpjExiste("1256", estabelecimentoRetornoFalse.getId(),estabelecimentoRetornoFalse.getEmpresa().getId());

		assertFalse(existeCnpj);

	}

	public void testAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setId(2L);

		empresa = empresaDao.save(empresa);
		empresa2 = empresaDao.save(empresa2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setEmpresa(empresa);
		estabelecimento1.setNome("A");

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setEmpresa(empresa);
		estabelecimento2.setNome("C");

		Estabelecimento estabelecimento3 = EstabelecimentoFactory.getEntity();
		estabelecimento3.setEmpresa(empresa2);
		estabelecimento3.setNome("B");

		Estabelecimento estabelecimento4 = EstabelecimentoFactory.getEntity();
		estabelecimento4.setEmpresa(empresa);
		estabelecimento4.setNome("B");

		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);
		estabelecimento3 = estabelecimentoDao.save(estabelecimento3);
		estabelecimento4 = estabelecimentoDao.save(estabelecimento4);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoDao.findAllSelect(empresa.getId());

		assertEquals("Test 1", 3, estabelecimentos.size());
		assertEquals("Test 2", estabelecimento1.getId(), ((Estabelecimento)(estabelecimentos.toArray()[0])).getId() );
		assertEquals("Test 3", estabelecimento4.getId(), ((Estabelecimento)(estabelecimentos.toArray()[1])).getId() );
		assertEquals("Test 4", estabelecimento2.getId(), ((Estabelecimento)(estabelecimentos.toArray()[2])).getId() );
	}
	
	public void testAllSelect2()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("empresa 01");
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		empresa2.setNome("empresa 02");
		
		empresa = empresaDao.save(empresa);
		empresa2 = empresaDao.save(empresa2);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setEmpresa(empresa);
		estabelecimento1.setNome("A");
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setEmpresa(empresa);
		estabelecimento2.setNome("C");
		
		Estabelecimento estabelecimento3 = EstabelecimentoFactory.getEntity();
		estabelecimento3.setEmpresa(empresa2);
		estabelecimento3.setNome("B");
		
		Estabelecimento estabelecimento4 = EstabelecimentoFactory.getEntity();
		estabelecimento4.setEmpresa(empresa);
		estabelecimento4.setNome("B");
		
		estabelecimentoDao.save(estabelecimento1);
		estabelecimentoDao.save(estabelecimento2);
		estabelecimentoDao.save(estabelecimento3);
		estabelecimentoDao.save(estabelecimento4);
		
		Collection<Estabelecimento> estabelecimentos = estabelecimentoDao.findAllSelect(new Long[]{empresa.getId(), empresa2.getId()});
		
		assertEquals("Test 1", 4, estabelecimentos.size());
		assertEquals("Test 2", estabelecimento1.getId(), ((Estabelecimento)(estabelecimentos.toArray()[0])).getId() );
		assertEquals("Test 3", "empresa 01 - A", ((Estabelecimento)(estabelecimentos.toArray()[0])).getDescricaoComEmpresa() );
	}
}