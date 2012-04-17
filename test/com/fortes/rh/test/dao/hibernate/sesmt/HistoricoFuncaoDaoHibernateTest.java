package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.util.DateUtil;
@SuppressWarnings("deprecation")
public class HistoricoFuncaoDaoHibernateTest extends GenericDaoHibernateTest<HistoricoFuncao>
{
	private HistoricoFuncaoDao historicoFuncaoDao;
	private FuncaoDao funcaoDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private EpiDao epiDao;

	public HistoricoFuncao getEntity()
	{
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();

		historicoFuncao.setId(1L);
		historicoFuncao.setDescricao("asd");
		historicoFuncao.setData(new Date());
		historicoFuncao.setFuncao(null);

		return historicoFuncao;
	}

	public void testGetHistoricosByDateFuncaos() throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setNome("FA");

		Funcao f2 = new Funcao();
		f2.setNome("FB");

		f1 = funcaoDao.save(f1);
		f2 = funcaoDao.save(f2);

		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setData(new Date("2007/10/01"));
		historicoFuncao1.setFuncao(f1);

		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(new Date("2008/01/01"));
		historicoFuncao2.setFuncao(f1);

		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setData(new Date("2009/01/01"));
		historicoFuncao3.setFuncao(f1);

		HistoricoFuncao historicoFuncao4 = new HistoricoFuncao();
		historicoFuncao4.setData(new Date("2007/01/01"));
		historicoFuncao4.setFuncao(f2);

		HistoricoFuncao historicoFuncao5 = new HistoricoFuncao();
		historicoFuncao5.setData(new Date("2008/01/01"));
		historicoFuncao5.setFuncao(f2);

		HistoricoFuncao historicoFuncao6 = new HistoricoFuncao();
		historicoFuncao6.setData(new Date("2009/01/01"));
		historicoFuncao6.setFuncao(f2);

		historicoFuncao1 = historicoFuncaoDao.save(historicoFuncao1);
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		historicoFuncao3 = historicoFuncaoDao.save(historicoFuncao3);
		historicoFuncao4 = historicoFuncaoDao.save(historicoFuncao4);
		historicoFuncao5 = historicoFuncaoDao.save(historicoFuncao5);
		historicoFuncao6 = historicoFuncaoDao.save(historicoFuncao6);

		Collection<Long> funcaoIds = new ArrayList<Long>();
		funcaoIds.add(f1.getId());
		funcaoIds.add(f2.getId());

		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoDao.getHistoricosByDateFuncaos(funcaoIds, new Date("2008/01/01"));

		assertEquals("Test 1", 4, historicoFuncaos.size());
		assertEquals("Test 2", historicoFuncao2.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[0]).getId());
		assertEquals("Test 3", historicoFuncao1.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[1]).getId());
		assertEquals("Test 4", historicoFuncao5.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[2]).getId());
		assertEquals("Test 5", historicoFuncao4.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[3]).getId());

		historicoFuncaos = historicoFuncaoDao.getHistoricosByDateFuncaos(funcaoIds, new Date("2008/01/02"));

		assertEquals("Test 6", 4, historicoFuncaos.size());
		assertEquals("Test 7", historicoFuncao2.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[0]).getId());
		assertEquals("Test 8", historicoFuncao1.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[1]).getId());
		assertEquals("Test 9", historicoFuncao5.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[2]).getId());
		assertEquals("Test 10", historicoFuncao4.getId(), ((HistoricoFuncao) historicoFuncaos.toArray()[3]).getId());

		historicoFuncaos = historicoFuncaoDao.getHistoricosByDateFuncaos(funcaoIds, new Date("2010/01/02"));
		assertEquals("Test 12", 6, historicoFuncaos.size());

		historicoFuncaos = historicoFuncaoDao.getHistoricosByDateFuncaos(funcaoIds, new Date("2007/05/01"));
		assertEquals("Test 13", 1, historicoFuncaos.size());
	}

	public void testFindEpisByData() throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setNome("FA");
		
		Funcao f2 = new Funcao();
		f2.setNome("FB");
		
		funcaoDao.save(f1);
		funcaoDao.save(f2);
		
		Epi epi1 = EpiFactory.getEntity();
		epi1.setNome("epi 1");
		epiDao.save(epi1);		

		Epi epi2 = EpiFactory.getEntity();
		epi2.setNome("epi 2");
		epiDao.save(epi2);		
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		epis.add(epi2);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setData(new Date("2007/05/01"));
		historicoFuncao1.setEpis(epis);
		historicoFuncao1.setFuncao(f1);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(new Date("2008/05/01"));
		historicoFuncao2.setFuncao(f1);
		
		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setData(new Date("2007/01/01"));
		historicoFuncao3.setFuncao(f2);
		
		HistoricoFuncao historicoFuncao4 = new HistoricoFuncao();
		historicoFuncao4.setData(new Date("2008/01/01"));
		historicoFuncao4.setFuncao(f2);
				
		historicoFuncaoDao.save(historicoFuncao1);
		historicoFuncaoDao.save(historicoFuncao2);
		historicoFuncaoDao.save(historicoFuncao3);
		historicoFuncaoDao.save(historicoFuncao4);
		
		Collection<Long> funcaoIds = new ArrayList<Long>();
		funcaoIds.add(f1.getId());
		funcaoIds.add(f2.getId());
		
		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoDao.findEpis(funcaoIds, new Date("2008/01/01"));
		
		assertEquals("Test 1", 1, historicoFuncaos.size());
	}
	
	public void testFindByData() throws Exception
	{
		Date hoje = DateUtil.criarDataMesAno(17, 4, 2012);
		
		Funcao funcao = new Funcao();
		funcao.setNome("funcao");
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setData(hoje);
		historicoFuncao1.setFuncao(funcao);
		historicoFuncaoDao.save(historicoFuncao1);
		
		assertEquals("Inserção", historicoFuncao1, historicoFuncaoDao.findByData(hoje, null));
		assertEquals("Atualização própria", null, historicoFuncaoDao.findByData(hoje, historicoFuncao1.getId()));
		assertEquals("Atualização outra", historicoFuncao1, historicoFuncaoDao.findByData(hoje, 0L));
	}

	public void testFindHistoricoByFuncoesId()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("2324");
		empresa.setRazaoSocial("social");

		empresa = empresaDao.save(empresa);

		Cargo cargo = new Cargo();
		cargo.setEmpresa(empresa);
		cargo.setNomeMercado("nomeMercado");
		cargo = cargoDao.save(cargo);

		Funcao f1 = new Funcao();
		f1.setNome("FA");
		f1.setCargo(cargo);

		Funcao f2 = new Funcao();
		f2.setNome("FB");
		f2.setCargo(cargo);

		f1 = funcaoDao.save(f1);
		f2 = funcaoDao.save(f2);

		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setData(DateUtil.criarDataMesAno(1, 1, 2005));
		historicoFuncao1.setFuncao(f1);

		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoFuncao2.setFuncao(f1);

		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setData(DateUtil.criarDataMesAno(1, 3, 2007));
		historicoFuncao3.setFuncao(f1);

		HistoricoFuncao historicoFuncao4 = new HistoricoFuncao();
		historicoFuncao4.setData(DateUtil.criarDataMesAno(1, 6, 2007));
		historicoFuncao4.setFuncao(f2);

		HistoricoFuncao historicoFuncao5 = new HistoricoFuncao();
		historicoFuncao5.setData(DateUtil.criarDataMesAno(1, 12, 2008));
		historicoFuncao5.setFuncao(f2);

		HistoricoFuncao historicoFuncao6 = new HistoricoFuncao();
		historicoFuncao6.setData(DateUtil.criarDataMesAno(1, 12, 2009));
		historicoFuncao6.setFuncao(f2);

		historicoFuncao1 = historicoFuncaoDao.save(historicoFuncao1);
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		historicoFuncao3 = historicoFuncaoDao.save(historicoFuncao3);
		historicoFuncao4 = historicoFuncaoDao.save(historicoFuncao4);
		historicoFuncao5 = historicoFuncaoDao.save(historicoFuncao5);
		historicoFuncao6 = historicoFuncaoDao.save(historicoFuncao6);

		Collection<Long> ids = new ArrayList<Long>();
		ids.add(f1.getId());
		ids.add(f2.getId());

		Collection<HistoricoFuncao> examesRelatorios = historicoFuncaoDao.findHistoricoByFuncoesId(ids,DateUtil.criarDataMesAno(1, 1, 2006),DateUtil.criarDataMesAno(1, 12, 2007));
		assertEquals(3, examesRelatorios.size());
	}
	
	public void testRemoveByFuncoes()
	{		
		Funcao funcao1 = new Funcao();
		funcao1 = funcaoDao.save(funcao1);
		
		Funcao funcao2 = new Funcao();		
		funcao2 = funcaoDao.save(funcao2);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setFuncao(funcao1);
		historicoFuncao1 = historicoFuncaoDao.save(historicoFuncao1);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setFuncao(funcao2);
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		
		Long[] funcaoIds = new Long[]{funcao1.getId(), funcao2.getId()};
		
		historicoFuncaoDao.removeByFuncoes(funcaoIds);
		
		assertNull(historicoFuncaoDao.findById(historicoFuncao1.getId(), null));
		assertNull(historicoFuncaoDao.findById(historicoFuncao2.getId(), null));		
	}
	
	public void testFindByIdProjection()
	{		
		Funcao funcao = new Funcao();
		funcao = funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);
		
		assertEquals(historicoFuncao, historicoFuncaoDao.findByIdProjection(historicoFuncao.getId()));
	}

	public void testGetHistoricosFuncoesByHistoricoColaborador()
	{
		//TODO:construir teste
	}

	public void setFuncaoDao(FuncaoDao funcaoDao)
	{
		this.funcaoDao = funcaoDao;
	}

	public GenericDao<HistoricoFuncao> getGenericDao()
	{
		return historicoFuncaoDao;
	}

	public void setHistoricoFuncaoDao(HistoricoFuncaoDao historicoFuncaoDao)
	{
		this.historicoFuncaoDao = historicoFuncaoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}
}