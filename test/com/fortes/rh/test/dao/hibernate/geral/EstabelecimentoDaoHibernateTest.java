package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class EstabelecimentoDaoHibernateTest extends GenericDaoHibernateTest<Estabelecimento>
{
	private EstabelecimentoDao estabelecimentoDao;
	private EmpresaDao empresaDao;
	private GrupoACDao grupoACDao;
	private CidadeDao cidadeDao;
	private EstadoDao estadoDao;

	public Estabelecimento getEntity()
	{
		return EstabelecimentoFactory.getEntity();
	}

	public void testRemoveByCodigo()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa.setCodigoAC("001122");

		Long idEmpresa = empresaDao.save(empresa).getId();

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimento.setCodigoAC("123456");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		assertEquals(true, estabelecimentoDao.remove("123456", idEmpresa));

		Estabelecimento est = estabelecimentoDao.findByCodigo("123456", "001122", "XXX");

		assertNull(est);
	}

	public void testFindByCodigo()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa.setCodigoAC("001122");
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimento.setCodigoAC("123456");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Estabelecimento est = estabelecimentoDao.findByCodigo("123456", "001122", "XXX");

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
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setEmpresa(empresa1);
		estabelecimento1.setNome("Estab 1");
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setNome("Estab 2");
		estabelecimentoDao.save(estabelecimento2);
		
		Long[] estabelecimentosIds = {estabelecimento2.getId(), estabelecimento1.getId()};
		
		Collection<Estabelecimento> estabelecimentosEmpresa1 = estabelecimentoDao.findEstabelecimentos(estabelecimentosIds, empresa1.getId());
		Collection<Estabelecimento> estabelecimentosGeral = estabelecimentoDao.findEstabelecimentos(estabelecimentosIds, null);
		
		assertEquals("Estabelecimentos da empresa 1", 1, estabelecimentosEmpresa1.size());
		assertEquals("Estabelecimentos geral", 2, estabelecimentosGeral.size());
	}

	public void testFindEstabelecimentoByCodigoAc()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("001122");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC("010203");
		estabelecimento.setEmpresa(empresa);
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Estabelecimento estabelecimentoRetorno = estabelecimentoDao.findEstabelecimentoByCodigoAc(estabelecimento.getCodigoAC(), empresa.getCodigoAC(), "XXX");

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
	
	public void testAllByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setId(2L);
		
		empresa = empresaDao.save(empresa);
		empresa2 = empresaDao.save(empresa2);
		
		Estado estado = new Estado();
		estado.setSigla("CE");
		estado.setNome("Ceará");
		estadoDao.save(estado);

		Cidade cidade = new Cidade();
		cidade.setUf(estado);
		cidade.setNome("Bostaleza");
		cidadeDao.save(cidade);
		
		Endereco endereco = new Endereco();
		endereco.setCidade(cidade);
		endereco.setUf(estado);
		endereco.setLogradouro("logradouro");
		endereco.setBairro("bairro");
		endereco.setCep("60000000");
		endereco.setNumero("123");
		endereco.setComplemento("complemento");
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setEmpresa(empresa);
		estabelecimento1.setNome("A");
		estabelecimento1.setEndereco(endereco);
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setEmpresa(empresa);
		estabelecimento2.setNome("C");
		estabelecimento2.setEndereco(endereco);
		estabelecimentoDao.save(estabelecimento2);
		
		Estabelecimento estabelecimento3 = EstabelecimentoFactory.getEntity();
		estabelecimento3.setEmpresa(empresa2);
		estabelecimento3.setNome("B");
		estabelecimento3.setEndereco(endereco);
		estabelecimentoDao.save(estabelecimento3);
		
		Estabelecimento estabelecimento4 = EstabelecimentoFactory.getEntity();
		estabelecimento4.setEmpresa(empresa);
		estabelecimento4.setNome("B");
		estabelecimento4.setEndereco(endereco);
		estabelecimentoDao.save(estabelecimento4);
		
		Collection<Estabelecimento> estabelecimentos = estabelecimentoDao.findByEmpresa(empresa.getId());
		
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
	
	public void testFindSemCodigoAC() {
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1.setCodigoAC("1");
		estabelecimento1.setEmpresa(empresa1);
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setCodigoAC(null);
		estabelecimento2.setEmpresa(empresa1);
		estabelecimentoDao.save(estabelecimento2);
		
		Estabelecimento estabelecimento3 = EstabelecimentoFactory.getEntity();
		estabelecimento3.setCodigoAC(null);
		estabelecimento3.setEmpresa(empresa1);
		estabelecimentoDao.save(estabelecimento3);
		
		Estabelecimento estabelecimento4 = EstabelecimentoFactory.getEntity();
		estabelecimento4.setCodigoAC("4");
		estabelecimento4.setEmpresa(empresa2);
		estabelecimentoDao.save(estabelecimento4);
		
		Estabelecimento estabelecimento5 = EstabelecimentoFactory.getEntity();
		estabelecimento5.setCodigoAC(null);
		estabelecimento5.setEmpresa(empresa2);
		estabelecimentoDao.save(estabelecimento5);
		
		assertEquals(2, estabelecimentoDao.findSemCodigoAC(empresa1.getId()).size());
		assertEquals(1, estabelecimentoDao.findSemCodigoAC(empresa2.getId()).size());
		
	}

	public void testFindComEnderecoById()
	{
		Estado uf = EstadoFactory.getEntity();
		uf.setSigla("CE");
		estadoDao.save(uf);
		
		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(uf);
		cidade.setNome("Fortaleza");
		cidadeDao.save(cidade);
		
		Endereco endereco = new Endereco();
		endereco.setUf(uf);
		endereco.setCidade(cidade);
		endereco.setLogradouro("Rua do Trilho");
		endereco.setNumero("999");
		endereco.setComplemento("apto 333");
		endereco.setCep("60140140");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("matriz");
		estabelecimento.setComplementoCnpj("0001");
		estabelecimento.setEndereco(endereco);
		estabelecimentoDao.save(estabelecimento);
		
		Estabelecimento estabelecimentoRetorno = estabelecimentoDao.findComEnderecoById(estabelecimento.getId());
		
		assertEquals(estabelecimento, estabelecimentoRetorno);
	}
	
	public void testUpdateCodigoAC()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC(null);
		estabelecimentoDao.save(estabelecimento);
		
		assertNull(estabelecimento.getCodigoAC());
		
		estabelecimentoDao.updateCodigoAC(estabelecimento.getId(), "0001");
		
		assertEquals("0001", estabelecimentoDao.findEntidadeComAtributosSimplesById(estabelecimento.getId()).getCodigoAC());
	}

	public GenericDao<Estabelecimento> getGenericDao() {
		return estabelecimentoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}
	
	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public void setEstadoDao(EstadoDao estadoDao) {
		this.estadoDao = estadoDao;
	}
}