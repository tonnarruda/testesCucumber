package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class FaturamentoMensalDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<FaturamentoMensal>
{
	@Autowired
	private FaturamentoMensalDao faturamentoMensalDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private EstabelecimentoDao estabelecimentoDao;

	@Override
	public FaturamentoMensal getEntity()
	{
		return FaturamentoMensalFactory.getEntity();
	}

	@Override
	public GenericDao<FaturamentoMensal> getGenericDao()
	{
		return faturamentoMensalDao;
	}

	@Test
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		FaturamentoMensal janeiro = new FaturamentoMensal();
		janeiro.setMesAno(new Date());
		janeiro.setValor(200.0);
		janeiro.setEmpresa(empresa);
		faturamentoMensalDao.save(janeiro);
		
		Collection<FaturamentoMensal> faturamentos = faturamentoMensalDao.findAllSelect(empresa.getId());
		assertEquals(1, faturamentos.size());
	}
	@Test
	public void testFindByPeriodo()
	{
		Date inicio = DateUtil.criarDataMesAno(1, 2, 2000);
		Date fim = DateUtil.criarDataMesAno(1, 11, 2000);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		FaturamentoMensal janeiro = new FaturamentoMensal();
		janeiro.setMesAno(inicio);
		janeiro.setValor(200.0);
		janeiro.setEmpresa(empresa);
		faturamentoMensalDao.save(janeiro);
		
		FaturamentoMensal novembro = new FaturamentoMensal();
		novembro.setMesAno(fim);
		novembro.setValor(200.0);
		novembro.setEmpresa(empresa);
		faturamentoMensalDao.save(novembro);
		
		Collection<FaturamentoMensal> faturamentos = faturamentoMensalDao.findByPeriodo(inicio, fim, empresa.getId(), null);
		assertEquals(2, faturamentos.size());
	}
	@Test
	public void testFindByPeriodoPorEstabelecimento()
	{
		Date inicio = DateUtil.criarDataMesAno(1, 2, 2000);
		Date fim = DateUtil.criarDataMesAno(1, 11, 2000);
		
		Estabelecimento estabelecimento= EstabelecimentoFactory.getEntity(1l);

		Empresa empresa = EmpresaFactory.getEmpresa();
		
		empresaDao.save(empresa);
		
		FaturamentoMensal janeiro = new FaturamentoMensal();
		janeiro.setMesAno(inicio);
		janeiro.setValor(200.0);
		janeiro.setEmpresa(empresa);
		janeiro.setEstabelecimento(estabelecimento);
		
		faturamentoMensalDao.save(janeiro);
		
		FaturamentoMensal novembro = new FaturamentoMensal();
		novembro.setMesAno(fim);
		novembro.setValor(200.0);
		novembro.setEmpresa(empresa);
		novembro.setEstabelecimento(estabelecimento);
		
		faturamentoMensalDao.save(novembro);
		
		Collection<FaturamentoMensal> faturamentos = faturamentoMensalDao.findByPeriodo(inicio, fim, empresa.getId(), new Long[]{estabelecimento.getId()});
		assertEquals(2, faturamentos.size());
	}
	@Test
	public void testSomaByPeriodo() {
		Date dataIni = DateUtil.criarDataMesAno(1, 2, 2000);
		Date dataFim = DateUtil.criarDataMesAno(1, 11, 2000);
		Date dataFora = DateUtil.criarDataMesAno(1, 12, 2000);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		FaturamentoMensal janeiro = new FaturamentoMensal();
		janeiro.setMesAno(dataIni);
		janeiro.setValor(200.0);
		janeiro.setEmpresa(empresa1);
		faturamentoMensalDao.save(janeiro);
		
		FaturamentoMensal outubro = new FaturamentoMensal();
		outubro.setMesAno(dataFim);
		outubro.setValor(150.0);
		outubro.setEmpresa(empresa2);
		faturamentoMensalDao.save(outubro);
		
		FaturamentoMensal novembro = new FaturamentoMensal();
		novembro.setMesAno(dataFim);
		novembro.setValor(300.0);
		novembro.setEmpresa(empresa1);
		faturamentoMensalDao.save(novembro);
		
		FaturamentoMensal dezembro = new FaturamentoMensal();
		dezembro.setMesAno(dataFora);
		dezembro.setValor(400.0);
		dezembro.setEmpresa(empresa1);
		faturamentoMensalDao.save(dezembro);
		
		assertEquals("Empresa 1",new Double(500.0), faturamentoMensalDao.somaByPeriodo(dataIni, dataFim, new Long[]{empresa1.getId()}));
		assertEquals("Empresa 2",new Double(150.0), faturamentoMensalDao.somaByPeriodo(dataIni, dataFim, new Long[]{empresa2.getId()}));
		assertEquals("Empresa 1 e 2",new Double(650.0), faturamentoMensalDao.somaByPeriodo(dataIni, dataFim, new Long[]{empresa1.getId(), empresa2.getId()}));
		assertEquals("Empresa desconhecida",new Double(0.0), faturamentoMensalDao.somaByPeriodo(dataIni, dataFim, new Long[]{100000001L}));
		
	}
	
	@Test
	public void testVerificaSeExisteNaMesmaDataEstabelecimentoNotInId(){
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity();
		faturamentoMensal.setMesAno(DateUtil.criarDataMesAno(1, 1, 2017));
		faturamentoMensal.setEmpresa(empresa);
		faturamentoMensal.setEstabelecimento(estabelecimento);
		faturamentoMensalDao.save(faturamentoMensal);
		
		assertFalse(faturamentoMensalDao.isExisteNaMesmaDataAndEstabelecimento(faturamentoMensal));
	}
	
	@Test
	public void testVerificaSeExisteNaMesmaDataEstabelecimento(){
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity();
		faturamentoMensal.setMesAno(DateUtil.criarDataMesAno(1, 1, 2017));
		faturamentoMensal.setEmpresa(empresa);
		faturamentoMensal.setEstabelecimento(estabelecimento);
		
		assertFalse(faturamentoMensalDao.isExisteNaMesmaDataAndEstabelecimento(faturamentoMensal));
	}
	
	@Test
	public void testVerificaSeExisteNaMesmaDataEstabelecimentoSemEstabelecimento(){
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity();
		faturamentoMensal.setMesAno(DateUtil.criarDataMesAno(1, 1, 2017));
		faturamentoMensal.setEmpresa(empresa);
		
		assertFalse(faturamentoMensalDao.isExisteNaMesmaDataAndEstabelecimento(faturamentoMensal));
	}
	
	@Test
	public void testVerificaSeExisteNaMesmaDataEstabelecimentoMesmoEstabelecimento(){
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		FaturamentoMensal faturamentoMensal1 = FaturamentoMensalFactory.getEntity();
		faturamentoMensal1.setMesAno(DateUtil.criarDataMesAno(1, 1, 2017));
		faturamentoMensal1.setEmpresa(empresa);
		faturamentoMensal1.setEstabelecimento(estabelecimento1);
		faturamentoMensalDao.save(faturamentoMensal1);
		
		FaturamentoMensal faturamentoMensal2 = FaturamentoMensalFactory.getEntity();
		faturamentoMensal2.setMesAno(DateUtil.criarDataMesAno(1, 1, 2017));
		faturamentoMensal2.setEmpresa(empresa);
		faturamentoMensal2.setEstabelecimento(estabelecimento1);
		
		assertTrue(faturamentoMensalDao.isExisteNaMesmaDataAndEstabelecimento(faturamentoMensal2));
	}
	
	public void setFaturamentoMensalDao(FaturamentoMensalDao faturamentoMensalDao)
	{
		this.faturamentoMensalDao = faturamentoMensalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
