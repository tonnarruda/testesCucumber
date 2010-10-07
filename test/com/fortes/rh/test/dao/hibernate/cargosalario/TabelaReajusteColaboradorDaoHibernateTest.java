package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;

public class TabelaReajusteColaboradorDaoHibernateTest extends GenericDaoHibernateTest<TabelaReajusteColaborador>
{
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	private EmpresaDao empresaDao;

	public TabelaReajusteColaborador getEntity()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();

		tabelaReajusteColaborador.setId(null);
		tabelaReajusteColaborador.setData(new Date());
		tabelaReajusteColaborador.setNome("nome da tabela de reajuste");
		tabelaReajusteColaborador.setReajusteColaboradors(null);
		tabelaReajusteColaborador.setEmpresa(null);

		return tabelaReajusteColaborador;
	}

	public GenericDao<TabelaReajusteColaborador> getGenericDao()
	{
		return tabelaReajusteColaboradorDao;
	}

	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void testFindByIdProjection()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		TabelaReajusteColaborador retorno = tabelaReajusteColaboradorDao.findByIdProjection(tabelaReajusteColaborador.getId());

		assertEquals(tabelaReajusteColaborador.getId(), retorno.getId());
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(true);
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
		
		assertEquals(1, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), true).size());
	}
	
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
		
		assertEquals(new Integer(1), tabelaReajusteColaboradorDao.getCount(empresa.getId()));
	}
	
	public void testFindAllList()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
		
		assertEquals(1, tabelaReajusteColaboradorDao.findAllList(1, 10, empresa.getId()).size());
	}

	public void testUpdateSetAprovada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setAprovada(false);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		tabelaReajusteColaboradorDao.updateSetAprovada(tabelaReajusteColaborador.getId(), true);
		assertEquals(1, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), true).size());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}