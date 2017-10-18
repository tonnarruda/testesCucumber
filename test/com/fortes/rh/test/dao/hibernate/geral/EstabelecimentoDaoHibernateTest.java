package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.sesmt.EngenheiroResponsavelFactory;
import com.fortes.rh.test.factory.sesmt.MedicoCoordenadorFactory;


public class EstabelecimentoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Estabelecimento>
{
	@Autowired
	private EstabelecimentoDao estabelecimentoDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private GrupoACDao grupoACDao;
	@Autowired
	private CidadeDao cidadeDao;
	@Autowired
	private EstadoDao estadoDao;
	@Autowired
	private MedicoCoordenadorDao medicoCoordenadorDao;
	@Autowired
	private EngenheiroResponsavelDao engenheiroResponsavelDao;

	public Estabelecimento getEntity() {
		return EstabelecimentoFactory.getEntity();
	}

	@Override
	public GenericDao<Estabelecimento> getGenericDao() {
		return estabelecimentoDao;
	}

	@Test
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

		Assert.assertTrue(estabelecimentoDao.remove("123456", idEmpresa));

		Estabelecimento est = estabelecimentoDao.findByCodigo("123456", "001122", "XXX");

		Assert.assertNull(est);
	}

	@Test
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

		Assert.assertNotNull(est);
		Assert.assertEquals("123456", est.getCodigoAC());
	}

	@Test
	public void testFindEstabelecimentoCodigoAc()
	{
		Estabelecimento estabelecimentoRetorno = EstabelecimentoFactory.getEntity();
		estabelecimentoRetorno.setCodigoAC("0001");
		estabelecimentoRetorno = estabelecimentoDao.save(estabelecimentoRetorno);

		Estabelecimento estabelecimento = estabelecimentoDao.findEstabelecimentoCodigoAc(estabelecimentoRetorno.getId());

		Assert.assertEquals("0001", estabelecimento.getCodigoAC());
	}

	@Test
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
		
		Assert.assertEquals("Estabelecimentos da empresa 1", 1, estabelecimentosEmpresa1.size());
		Assert.assertEquals("Estabelecimentos geral", 2, estabelecimentosGeral.size());
	}

	@Test
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

		Assert.assertEquals(estabelecimento, estabelecimentoRetorno);
	}

	@Test
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

		Assert.assertTrue(existeCnpj);

		Estabelecimento estabelecimentoRetornoFalse = EstabelecimentoFactory.getEntity();
		estabelecimentoRetornoFalse.setComplementoCnpj("1256");
		estabelecimentoRetornoFalse.setEmpresa(empresa);
		estabelecimentoRetornoFalse = estabelecimentoDao.save(estabelecimentoRetornoFalse);

		existeCnpj = estabelecimentoDao.verificaCnpjExiste("1256", estabelecimentoRetornoFalse.getId(),estabelecimentoRetornoFalse.getEmpresa().getId());

		Assert.assertFalse(existeCnpj);

	}

	@Test
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

		Assert.assertEquals("Test 1", 3, estabelecimentos.size());
		Assert.assertEquals("Test 2", estabelecimento1.getId(), ((Estabelecimento)(estabelecimentos.toArray()[0])).getId() );
		Assert.assertEquals("Test 3", estabelecimento4.getId(), ((Estabelecimento)(estabelecimentos.toArray()[1])).getId() );
		Assert.assertEquals("Test 4", estabelecimento2.getId(), ((Estabelecimento)(estabelecimentos.toArray()[2])).getId() );
	}
	
	@Test
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
		
		Assert.assertEquals("Test 1", 3, estabelecimentos.size());
		Assert.assertEquals("Test 2", estabelecimento1.getId(), ((Estabelecimento)(estabelecimentos.toArray()[0])).getId() );
		Assert.assertEquals("Test 3", estabelecimento4.getId(), ((Estabelecimento)(estabelecimentos.toArray()[1])).getId() );
		Assert.assertEquals("Test 4", estabelecimento2.getId(), ((Estabelecimento)(estabelecimentos.toArray()[2])).getId() );
	}
	
	@Test
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
		
		Assert.assertEquals("Test 1", 4, estabelecimentos.size());
		Assert.assertEquals("Test 2", estabelecimento1.getId(), ((Estabelecimento)(estabelecimentos.toArray()[0])).getId() );
		Assert.assertEquals("Test 3", "empresa 01 - A", ((Estabelecimento)(estabelecimentos.toArray()[0])).getDescricaoComEmpresa() );
	}
	
	@Test
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
		
		Assert.assertEquals(2, estabelecimentoDao.findSemCodigoAC(empresa1.getId()).size());
		Assert.assertEquals(1, estabelecimentoDao.findSemCodigoAC(empresa2.getId()).size());
		
	}

	@Test
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
		
		Assert.assertEquals(estabelecimento, estabelecimentoRetorno);
	}
	
	@Test
	public void testUpdateCodigoAC()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC(null);
		estabelecimentoDao.save(estabelecimento);
		
		Assert.assertNull(estabelecimento.getCodigoAC());
		
		estabelecimentoDao.updateCodigoAC(estabelecimento.getId(), "0001");
		
		Assert.assertEquals("0001", estabelecimentoDao.findEntidadeComAtributosSimplesById(estabelecimento.getId()).getCodigoAC());
	}
	
	@Test
	public void testFindByMedicoCoordenador()
	{
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		MedicoCoordenador medicoCoordenador1 = MedicoCoordenadorFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.ALGUNS, Arrays.asList(estabelecimento1));
		medicoCoordenadorDao.save(medicoCoordenador1);
		
		MedicoCoordenador medicoCoordenador2 = MedicoCoordenadorFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.ALGUNS, Arrays.asList(estabelecimento2));
		medicoCoordenadorDao.save(medicoCoordenador2);
		
		MedicoCoordenador medicoCoordenador3 = MedicoCoordenadorFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.TODOS, null);
		medicoCoordenadorDao.save(medicoCoordenador3);
		
		MedicoCoordenador medicoCoordenador4 = MedicoCoordenadorFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.ALGUNS, Arrays.asList(estabelecimento1, estabelecimento2));
		medicoCoordenadorDao.save(medicoCoordenador4);
		
		Collection<Estabelecimento> estabelecimentosDoMedico1 = estabelecimentoDao.findByMedicoCoordenador(medicoCoordenador1.getId());
		Collection<Estabelecimento> estabelecimentosDoMedico2 = estabelecimentoDao.findByMedicoCoordenador(medicoCoordenador2.getId());
		Collection<Estabelecimento> estabelecimentosDoMedico3 = estabelecimentoDao.findByMedicoCoordenador(medicoCoordenador3.getId());
		Collection<Estabelecimento> estabelecimentosDoMedico4 = estabelecimentoDao.findByMedicoCoordenador(medicoCoordenador4.getId());
		
		Assert.assertEquals(1, estabelecimentosDoMedico1.size());
		Assert.assertEquals(estabelecimento1.getId(), estabelecimentosDoMedico1.iterator().next().getId());
		
		Assert.assertEquals(1, estabelecimentosDoMedico2.size());
		Assert.assertEquals(estabelecimento2.getId(), estabelecimentosDoMedico2.iterator().next().getId());
		
		Assert.assertTrue(estabelecimentosDoMedico3.isEmpty());
		
		Assert.assertEquals(2, estabelecimentosDoMedico4.size());
		Assert.assertEquals(estabelecimento1.getId(), (estabelecimentosDoMedico4.toArray(new Estabelecimento[1])[0]).getId());
		Assert.assertEquals(estabelecimento2.getId(), (estabelecimentosDoMedico4.toArray(new Estabelecimento[1])[1]).getId());
	}
	
	@Test
	public void testFindByEngenheiroResponsavel()
	{
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		EngenheiroResponsavel engenheiroResponsavel1 = EngenheiroResponsavelFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.ALGUNS, Arrays.asList(estabelecimento1));
		engenheiroResponsavelDao.save(engenheiroResponsavel1);
		
		EngenheiroResponsavel engenheiroResponsavel2 = EngenheiroResponsavelFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.ALGUNS, Arrays.asList(estabelecimento2));
		engenheiroResponsavelDao.save(engenheiroResponsavel2);
		
		EngenheiroResponsavel engenheiroResponsavel3 = EngenheiroResponsavelFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.TODOS, null);
		engenheiroResponsavelDao.save(engenheiroResponsavel3);
		
		EngenheiroResponsavel engenheiroResponsavel4 = EngenheiroResponsavelFactory.getEntity(null, null, TipoEstabelecimentoResponsavel.ALGUNS, Arrays.asList(estabelecimento1, estabelecimento2));
		engenheiroResponsavelDao.save(engenheiroResponsavel4);
		
		Collection<Estabelecimento> estabelecimentosDoEngenheiro1 = estabelecimentoDao.findByEngenheiroResponsavel(engenheiroResponsavel1.getId());
		Collection<Estabelecimento> estabelecimentosDoEngenheiro2 = estabelecimentoDao.findByEngenheiroResponsavel(engenheiroResponsavel2.getId());
		Collection<Estabelecimento> estabelecimentosDoEngenheiro3 = estabelecimentoDao.findByEngenheiroResponsavel(engenheiroResponsavel3.getId());
		Collection<Estabelecimento> estabelecimentosDoEngenheiro4 = estabelecimentoDao.findByEngenheiroResponsavel(engenheiroResponsavel4.getId());
		
		Assert.assertEquals(1, estabelecimentosDoEngenheiro1.size());
		Assert.assertEquals(estabelecimento1.getId(), estabelecimentosDoEngenheiro1.iterator().next().getId());
		
		Assert.assertEquals(1, estabelecimentosDoEngenheiro2.size());
		Assert.assertEquals(estabelecimento2.getId(), estabelecimentosDoEngenheiro2.iterator().next().getId());
		
		Assert.assertTrue(estabelecimentosDoEngenheiro3.isEmpty());
		
		Assert.assertEquals(2, estabelecimentosDoEngenheiro4.size());
		Assert.assertEquals(estabelecimento1.getId(), (estabelecimentosDoEngenheiro4.toArray(new Estabelecimento[1])[0]).getId());
		Assert.assertEquals(estabelecimento2.getId(), (estabelecimentosDoEngenheiro4.toArray(new Estabelecimento[1])[1]).getId());
	}
}