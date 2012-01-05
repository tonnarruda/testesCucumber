package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;
import com.fortes.rh.util.DateUtil;

public class FaturamentoMensalDaoHibernateTest extends GenericDaoHibernateTest<FaturamentoMensal>
{
	private FaturamentoMensalDao faturamentoMensalDao;
	private EmpresaDao empresaDao;

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
		
		Collection<FaturamentoMensal> faturamentos = faturamentoMensalDao.findByPeriodo(inicio, fim, empresa.getId());
		assertEquals(2, faturamentos.size());
	}
	
	public void testFindAtual()
	{
		Date data1 = DateUtil.criarDataMesAno(1, 2, 2000);
		Date data2 = DateUtil.criarDataMesAno(1, 11, 2000);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		FaturamentoMensal janeiro = new FaturamentoMensal();
		janeiro.setMesAno(data1);
		janeiro.setValor(200.0);
		janeiro.setEmpresa(empresa);
		faturamentoMensalDao.save(janeiro);
		
		FaturamentoMensal novembro = new FaturamentoMensal();
		novembro.setMesAno(data2);
		novembro.setValor(200.0);
		novembro.setEmpresa(empresa);
		faturamentoMensalDao.save(novembro);
		
		FaturamentoMensal faturamento = faturamentoMensalDao.findAtual(DateUtil.criarDataMesAno(1, 12, 2000));
		assertEquals(novembro, faturamento);
	}
	
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
		
		assertEquals(500.0, faturamentoMensalDao.somaByPeriodo(dataIni, dataFim, empresa1.getId()));
		assertEquals(0.0, faturamentoMensalDao.somaByPeriodo(dataIni, dataFim, empresa2.getId()));
		
	}
	
	public void setFaturamentoMensalDao(FaturamentoMensalDao faturamentoMensalDao)
	{
		this.faturamentoMensalDao = faturamentoMensalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
