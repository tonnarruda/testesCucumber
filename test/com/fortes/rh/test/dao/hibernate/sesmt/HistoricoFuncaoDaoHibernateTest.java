package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoFuncaoFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.util.DateUtil;
@SuppressWarnings("deprecation")
public class HistoricoFuncaoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<HistoricoFuncao>
{
	@Autowired private HistoricoFuncaoDao historicoFuncaoDao;
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private CargoDao cargoDao;
	@Autowired private EpiDao epiDao;
	@Autowired private ExameDao exameDao;
	@Autowired private RiscoDao riscoDao;
	@Autowired private RiscoFuncaoDao riscoFuncaoDao;

	public HistoricoFuncao getEntity()
	{
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();

		historicoFuncao.setId(1L);
		historicoFuncao.setDescricao("asd");
		historicoFuncao.setData(new Date());
		historicoFuncao.setFuncao(null);

		return historicoFuncao;
	}

	public GenericDao<HistoricoFuncao> getGenericDao() {
		return historicoFuncaoDao;
	}

	@Test
	public void testGetHistoricosByDateFuncaos() throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setNome("FA");

		Funcao f2 = new Funcao();
		f2.setNome("FB");

		f1 = funcaoDao.save(f1);
		f2 = funcaoDao.save(f2);

		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setFuncaoNome(f1.getNome());
		historicoFuncao1.setData(new Date("2007/10/01"));
		historicoFuncao1.setFuncao(f1);

		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(new Date("2008/01/01"));
		historicoFuncao2.setFuncao(f1);
		historicoFuncao2.setFuncaoNome(f1.getNome());

		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setData(new Date("2009/01/01"));
		historicoFuncao3.setFuncao(f1);
		historicoFuncao3.setFuncaoNome(f1.getNome());

		HistoricoFuncao historicoFuncao4 = new HistoricoFuncao();
		historicoFuncao4.setData(new Date("2007/01/01"));
		historicoFuncao4.setFuncao(f2);
		historicoFuncao4.setFuncaoNome(f2.getNome());

		HistoricoFuncao historicoFuncao5 = new HistoricoFuncao();
		historicoFuncao5.setData(new Date("2008/01/01"));
		historicoFuncao5.setFuncao(f2);
		historicoFuncao5.setFuncaoNome(f2.getNome());

		HistoricoFuncao historicoFuncao6 = new HistoricoFuncao();
		historicoFuncao6.setData(new Date("2009/01/01"));
		historicoFuncao6.setFuncao(f2);
		historicoFuncao6.setFuncaoNome(f2.getNome());

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

	@Test
	public void testFindEpisByData() throws Exception
	{
		Funcao f1 = FuncaoFactory.getEntity(null, "FA");
		funcaoDao.save(f1);
		
		Funcao f2 = FuncaoFactory.getEntity(null, "FB");
		funcaoDao.save(f2);
		
		Epi epi1 = EpiFactory.getEntity(null, "epi 1", null);
		epiDao.save(epi1);		

		Epi epi2 = EpiFactory.getEntity(null, "epi 2", null);
		epiDao.save(epi2);		
		
		Collection<Epi> epis = Arrays.asList(epi1, epi2);
		
		HistoricoFuncao historicoFuncao1 = HistoricoFuncaoFactory.getEntity(f1, DateUtil.criarDataDiaMesAno("01/05/2007"), epis);
		historicoFuncaoDao.save(historicoFuncao1);
		
		HistoricoFuncao historicoFuncao2 = HistoricoFuncaoFactory.getEntity(f1, DateUtil.criarDataDiaMesAno("01/05/2008"), null);
		historicoFuncaoDao.save(historicoFuncao2);
		
		HistoricoFuncao historicoFuncao3 = HistoricoFuncaoFactory.getEntity(f2, DateUtil.criarDataDiaMesAno("01/01/2007"), epis);
		historicoFuncaoDao.save(historicoFuncao3);
		
		HistoricoFuncao historicoFuncao4 = HistoricoFuncaoFactory.getEntity(f2, DateUtil.criarDataDiaMesAno("01/01/2008"), null);
		historicoFuncaoDao.save(historicoFuncao4);
		
		Collection<Long> funcaoIds = Arrays.asList(f1.getId(), f2.getId());
		
		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoDao.findEpis(funcaoIds, new Date("2008/01/01"));
		Collection<HistoricoFuncao> historicosVazios = historicoFuncaoDao.findEpis(null, new Date("2008/01/01"));
		
		assertEquals("Test 1", 1, historicoFuncaos.size());
		assertNull("Test 2", historicosVazios);
	}
	
	@Test
	public void testFindByData() throws Exception
	{
		Date hoje = DateUtil.criarDataMesAno(17, 4, 2012);
		
		Funcao funcao = new Funcao();
		funcao.setNome("funcao");
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setData(hoje);
		historicoFuncao1.setFuncao(funcao);
		historicoFuncao1.setFuncaoNome(funcao.getNome());
		historicoFuncaoDao.save(historicoFuncao1);
		
		assertEquals("Inserção", historicoFuncao1.getId(), historicoFuncaoDao.findByData(hoje, null, funcao.getId()).getId());
		assertEquals("Atualização própria", null, historicoFuncaoDao.findByData(hoje, historicoFuncao1.getId(), funcao.getId()));
		assertEquals("Atualização outra", historicoFuncao1.getId(), historicoFuncaoDao.findByData(hoje, 0L, funcao.getId()).getId());
	}

	@Test
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

		Funcao f1 = FuncaoFactory.getEntity("FA", empresa);

		Funcao f2 = FuncaoFactory.getEntity("FB", empresa);
		f1 = funcaoDao.save(f1);
		f2 = funcaoDao.save(f2);

		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setData(DateUtil.criarDataMesAno(1, 1, 2005));
		historicoFuncao1.setFuncao(f1);
		historicoFuncao1.setFuncaoNome(f1.getNome());

		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoFuncao2.setFuncao(f1);
		historicoFuncao2.setFuncaoNome(f1.getNome());

		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setData(DateUtil.criarDataMesAno(1, 3, 2007));
		historicoFuncao3.setFuncao(f1);
		historicoFuncao3.setFuncaoNome(f1.getNome());

		HistoricoFuncao historicoFuncao4 = new HistoricoFuncao();
		historicoFuncao4.setData(DateUtil.criarDataMesAno(1, 6, 2007));
		historicoFuncao4.setFuncao(f2);
		historicoFuncao4.setFuncaoNome(f2.getNome());

		HistoricoFuncao historicoFuncao5 = new HistoricoFuncao();
		historicoFuncao5.setData(DateUtil.criarDataMesAno(1, 12, 2008));
		historicoFuncao5.setFuncao(f2);
		historicoFuncao5.setFuncaoNome(f2.getNome());

		HistoricoFuncao historicoFuncao6 = new HistoricoFuncao();
		historicoFuncao6.setData(DateUtil.criarDataMesAno(1, 12, 2009));
		historicoFuncao6.setFuncao(f2);
		historicoFuncao6.setFuncaoNome(f2.getNome());

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
	
	@Test
	public void testRemoveByFuncoes()
	{		
		Funcao funcao1 = FuncaoFactory.getEntity();
		funcao1 = funcaoDao.save(funcao1);
		
		Funcao funcao2 = FuncaoFactory.getEntity();		
		funcao2 = funcaoDao.save(funcao2);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setFuncao(funcao1);
		historicoFuncao1.setFuncaoNome(funcao1.getNome());
		historicoFuncao1 = historicoFuncaoDao.save(historicoFuncao1);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setFuncao(funcao2);
		historicoFuncao2.setFuncaoNome(funcao2.getNome());
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		
		Long[] funcaoIds = new Long[]{funcao1.getId(), funcao2.getId()};
		
		historicoFuncaoDao.removeByFuncoes(funcaoIds);
		
		assertNull(historicoFuncaoDao.findById(historicoFuncao1.getId(), null));
		assertNull(historicoFuncaoDao.findById(historicoFuncao2.getId(), null));		
	}
	
	@Test
	public void testFindByIdProjection()
	{		
		Funcao funcao = FuncaoFactory.getEntity();
		funcao = funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);
		
		assertEquals(historicoFuncao, historicoFuncaoDao.findByIdProjection(historicoFuncao.getId()));
	}

	@Test
	public void testFindByFuncoes()
	{
		Date data = DateUtil.criarDataMesAno(01, 01, 2012);
		Date datafutura = DateUtil.criarDataMesAno(01, 01, 2013);
		
		Exame exame1 = ExameFactory.getEntity();
		exameDao.save(exame1);
		
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);
		
		Exame exame3 = ExameFactory.getEntity();
		exameDao.save(exame3);
		
		Collection<Exame> exames = Arrays.asList(exame1, exame2, exame3);
				
		Funcao funcao = FuncaoFactory.getEntity();
		funcao = funcaoDao.save(funcao);
		
		Funcao funcao2 = FuncaoFactory.getEntity();
		funcao2 = funcaoDao.save(funcao2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setExames(exames);
		historicoFuncao.setData(data);
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setExames(exames);
		historicoFuncao2.setData(datafutura);
		historicoFuncao2.setFuncao(funcao);
		historicoFuncao2.setFuncaoNome(funcao.getNome());
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		
		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setExames(exames);
		historicoFuncao3.setData(data);
		historicoFuncao3.setFuncao(funcao2);
		historicoFuncao3.setFuncaoNome(funcao2.getNome());
		historicoFuncao3 = historicoFuncaoDao.save(historicoFuncao3);
		
		Long[] funcoesIds = new Long[] {funcao.getId()};
		
		Collection<Funcao> funcoes = historicoFuncaoDao.findByFuncoes(data, funcoesIds);
		
		assertEquals(3, funcoes.size());
	}
	
	@Test
	public void testFindByFuncao()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 01, 2012);
		Date data2 = DateUtil.criarDataMesAno(01, 02, 2012);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcao = funcaoDao.save(funcao);
		
		Funcao funcao2 = FuncaoFactory.getEntity();
		funcao2 = funcaoDao.save(funcao2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(data1);
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(data2);
		historicoFuncao2.setFuncao(funcao);
		historicoFuncao2.setFuncaoNome(funcao.getNome());
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		
		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setData(data1);
		historicoFuncao3.setFuncao(funcao2);
		historicoFuncao3.setFuncaoNome(funcao2.getNome());
		historicoFuncao3 = historicoFuncaoDao.save(historicoFuncao3);
		
		Collection<HistoricoFuncao> funcoes = historicoFuncaoDao.findByFuncao(funcao.getId());
		
		assertEquals(2, funcoes.size());
	}
	
	@Test
	public void testFindByFuncaoAndData()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 01, 2012);
		Date data2 = DateUtil.criarDataMesAno(01, 02, 2012);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcao = funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(data1);
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setData(data2);
		historicoFuncao2.setFuncao(funcao);
		historicoFuncao2.setFuncaoNome(funcao.getNome());
		historicoFuncao2 = historicoFuncaoDao.save(historicoFuncao2);
		
		HistoricoFuncao historicoFuncaoRetornoDoBanco = historicoFuncaoDao.findByFuncaoAndData(funcao.getId(), new Date());
		
		assertEquals(historicoFuncao2.getId(), historicoFuncaoRetornoDoBanco.getId());
	}
	
	@Test
	public void testFindDadosNoPeriodo()
	{
		Date dataIni = DateUtil.criarAnoMesDia(2009, 01, 10);
		Date dataFim = DateUtil.criarAnoMesDia(2009, 03, 20);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
				
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(dataIni);
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncaoDao.save(historicoFuncao);

		RiscoFuncao riscoFuncao = RiscoFuncaoFactory.getEntity();
		riscoFuncao.setRisco(risco);
		riscoFuncao.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao);
		
		List<DadosAmbienteOuFuncaoRisco> retorno = historicoFuncaoDao.findDadosNoPeriodo(funcao.getId(), dataIni, dataFim);
		
		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testFindUltimoHistoricoAteData()
	{
		Date data = DateUtil.criarDataMesAno(01, 07, 2017);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setData(data);
		historicoFuncaoDao.save(historicoFuncao);
		
		assertEquals(funcao.getId(), historicoFuncaoDao.findUltimoHistoricoAteData(funcao.getId(), new Date()).getFuncao().getId());
	}
}