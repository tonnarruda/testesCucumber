package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.util.DateUtil;

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
		tabelaReajusteColaborador.setTipoReajuste('C');

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
		
		TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
		tabela1.setEmpresa(empresa);
		tabela1.setAprovada(true);
		tabela1.setTipoReajuste(TipoReajuste.COLABORADOR);
		tabelaReajusteColaboradorDao.save(tabela1);

		TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
		tabela2.setEmpresa(empresa);
		tabela2.setAprovada(true);
		tabela2.setTipoReajuste(TipoReajuste.FAIXA_SALARIAL);
		tabelaReajusteColaboradorDao.save(tabela2);
		
		assertEquals(2, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), null, true, null, null).size());
		assertEquals(1, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), TipoReajuste.COLABORADOR, true, null, null).size());
		assertEquals(1, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), TipoReajuste.FAIXA_SALARIAL, true, null, null).size());
	}

	
	public void testFindAllSelectNoPeriodo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2016);
		Date data2 = DateUtil.criarDataMesAno(25, 1, 2016);
		
		TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
		tabela1.setEmpresa(empresa);
		tabela1.setAprovada(true);
		tabela1.setTipoReajuste(TipoReajuste.COLABORADOR);
		tabela1.setData(data1);
		tabelaReajusteColaboradorDao.save(tabela1);

		TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
		tabela2.setEmpresa(empresa);
		tabela2.setAprovada(true);
		tabela2.setTipoReajuste(TipoReajuste.COLABORADOR);
		tabela2.setData(data2);
		tabelaReajusteColaboradorDao.save(tabela2);
		
		assertEquals(1, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), null, true, DateUtil.criarDataMesAno(1, 12, 2015), DateUtil.criarDataMesAno(20, 1, 2016)).size());
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
		tabelaReajusteColaborador.setDissidio(true);
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		tabelaReajusteColaboradorDao.updateSetAprovada(tabelaReajusteColaborador.getId(), true);
		assertEquals(1, tabelaReajusteColaboradorDao.findAllSelect(empresa.getId(), TipoReajuste.COLABORADOR, true, null, null).size());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}