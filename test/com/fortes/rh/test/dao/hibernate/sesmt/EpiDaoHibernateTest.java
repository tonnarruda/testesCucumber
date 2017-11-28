package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoFuncaoFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.util.DateUtil;

public class EpiDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Epi>
{
	@Autowired private EpiDao epiDao;
	@Autowired private EpiHistoricoDao epiHistoricoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private AmbienteDao ambienteDao;
	@Autowired private HistoricoAmbienteDao historicoAmbienteDao;
	@Autowired private RiscoDao riscoDao;
	@Autowired private RiscoAmbienteDao riscoAmbienteDao;
	@Autowired private HistoricoFuncaoDao historicoFuncaoDao;
	@Autowired private TipoEPIDao tipoEPIDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private RiscoFuncaoDao riscoFuncaoDao;

	private Empresa empresa;

	public Epi getEntity()
	{
		setEmpresa();

		Epi epi = new Epi();

		epi.setId(null);
		epi.setNome("nome da epi");
		epi.setFabricante("TecToy");
		epi.setEmpresa(empresa);
		epi.setTipoEPI(null);

		return epi;
	}
	
	public GenericDao<Epi> getGenericDao() {
		return epiDao;
	}
	
	@Test
	public void testFindEpisGetCount() 
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Epi epi1 = EpiFactory.getEntity();
		epi1.setEmpresa(empresa1);
		epiDao.save(epi1);
		
		Epi epi2 = EpiFactory.getEntity();
		epi2.setEmpresa(empresa1);
		epiDao.save(epi2);
		
		Epi epi3 = EpiFactory.getEntity();
		epi3.setEmpresa(empresa1);
		epiDao.save(epi3);
		
		Epi epi4 = EpiFactory.getEntity();
		epi4.setEmpresa(empresa1);
		epiDao.save(epi4);
		
		Epi epi5 = EpiFactory.getEntity();
		epi5.setEmpresa(empresa1);
		epiDao.save(epi5);
		
		Epi epi6 = EpiFactory.getEntity();
		epi6.setEmpresa(empresa2);
		epiDao.save(epi6);
		
		assertEquals(new Integer(5), epiDao.getCount(empresa1.getId(), null, null));

		assertEquals(5, epiDao.findEpis(1, 10, empresa1.getId(), null, null).size());
		assertEquals(2, epiDao.findEpis(1, 2, empresa1.getId(), null, null).size());
		assertEquals(1, epiDao.findEpis(3, 2, empresa1.getId(), null, null).size());
	}

	@Test
	public void testFindByIdProjection()
	{
		setEmpresa();

		Epi epi = new Epi();
		epi.setEmpresa(empresa);
		epi = epiDao.save(epi);

		Epi epiRetorno = epiDao.findByIdProjection(epi.getId());

		assertEquals(epi.getId(), epiRetorno.getId());
	}

	@Test
	public void testFindByVencimentoCa()
	{
		Date data = new Date();

		TipoEPI tipoEPI = new TipoEPI();
		tipoEPIDao.save(tipoEPI);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
		epi.setTipoEPI(tipoEPI);
		epi.setEmpresa(empresa);
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setData(data);
		epiHistorico.setVencimentoCA(data);
		epiHistorico.setEpi(epi);
		epiHistoricoDao.save(epiHistorico);

		Collection<Epi> colecao = epiDao.findByVencimentoCa(data, empresa.getId(), new Long[]{tipoEPI.getId()});
		assertEquals(1, colecao.size());
	}
	
	@Test
	public void testFindEpisDoAmbiente()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);
		
		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Epi epi2 = new Epi();
		epiDao.save(epi2);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setRisco(risco);
		riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertEquals(1, epiDao.findEpisDoAmbiente(ambiente.getId(), new Date()).size());
	}

	@Test
	public void testFindByRiscoAmbiente()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);

		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setRisco(risco);
		riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertEquals(epis, epiDao.findByRiscoAmbienteOuFuncao(risco.getId(), ambiente.getId(), new Date(), true));
	}
	
	@Test
	public void testFindByRiscoFuncao()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);

		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(new Date());
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncaoDao.save(historicoFuncao);
		
		RiscoFuncao riscoFuncao = RiscoFuncaoFactory.getEntity();
		riscoFuncao.setRisco(risco);
		riscoFuncao.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao);
		
		assertEquals(epis, epiDao.findByRiscoAmbienteOuFuncao(risco.getId(), funcao.getId(), new Date(), false));
	}

	@Test
	public void testFindByRisco()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);
		
		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setRisco(risco);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertEquals(epis, epiDao.findByRisco(risco.getId()));
	}

	@Test
	public void testFindByIdHistoricoFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Epi epi1 = new Epi();
		epi1.setEmpresa(empresa);
		epi1 = epiDao.save(epi1);

		Epi epi2 = new Epi();
		epi2.setEmpresa(empresa);
		epi2 = epiDao.save(epi2);

		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		epis.add(epi2);

		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();
		historicoFuncao.setEpis(epis);
		historicoFuncao.setFuncao(funcao);
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);

		Collection<Epi> episRetorno = epiDao.findByHistoricoFuncao(historicoFuncao.getId());

		assertEquals(2, episRetorno.size());
	}	
	
	@Test
	public void testFindSincronizarEpiInteresse() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa = empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epiDao.save(epi);
		
		Collection<Epi> episRetorno = epiDao.findSincronizarEpiInteresse(empresa.getId());

		assertEquals(1, episRetorno.size());
	}
	
	@Test
	public void testFindFabricantesDistinctByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Epi epi1 = EpiFactory.getEntity();
		epi1.setFabricante("plast");
		epi1.setEmpresa(empresa);
		epiDao.save(epi1);

		Epi epi2 = EpiFactory.getEntity();
		epi2.setFabricante("gaviao");
		epi2.setEmpresa(empresa);
		epiDao.save(epi2);

		Epi epi3 = EpiFactory.getEntity();
		epi3.setFabricante("plast");
		epi3.setEmpresa(empresa);
		epiDao.save(epi3);


		Collection<String> fabricantes = epiDao.findFabricantesDistinctByEmpresa(empresa.getId());
		assertEquals(2, fabricantes.size());
		assertEquals(epi2.getFabricante(), fabricantes.toArray()[0]);
		assertEquals(epi1.getFabricante(), fabricantes.toArray()[1]);
	}
	
	@Test
	public void testFindAllSelect()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 01, 2011);
		Date data2 = DateUtil.criarDataMesAno(02, 02, 2011);
		
		TipoEPI tipoEPI = new TipoEPI();
		tipoEPIDao.save(tipoEPI);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
		epi.setTipoEPI(tipoEPI);
		epi.setEmpresa(empresa);
		epiDao.save(epi);

		Epi epi2 = EpiFactory.getEntity();
		epi2.setTipoEPI(tipoEPI);
		epi2.setEmpresa(empresa);
		epiDao.save(epi2);
		
		EpiHistorico eh1 = new EpiHistorico();
		eh1.setData(data1);
		eh1.setVencimentoCA(data1);
		eh1.setEpi(epi);
		epiHistoricoDao.save(eh1);

		EpiHistorico eh2 = new EpiHistorico();
		eh2.setData(data2);
		eh2.setVencimentoCA(data2);
		eh2.setEpi(epi);
		epiHistoricoDao.save(eh2);

		EpiHistorico eh3 = new EpiHistorico();
		eh3.setData(data1);
		eh3.setVencimentoCA(data1);
		eh3.setEpi(epi2);
		epiHistoricoDao.save(eh3);

		Collection<Epi> colecao = epiDao.findAllSelect(empresa.getId());
		assertEquals(2, colecao.size());
	}
	
	@Test
	public void testFindPriorizandoEpiRelacionado()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 01, 2011);
		Date data2 = DateUtil.criarDataMesAno(02, 02, 2011);
	
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);

		Funcao funcao2 = FuncaoFactory.getEntity();
		funcaoDao.save(funcao2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFuncao(funcao);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(data1);
		historicoColaboradorDao.save(historicoColaborador);
		
		TipoEPI tipoEPI = new TipoEPI();
		tipoEPIDao.save(tipoEPI);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
		epi.setTipoEPI(tipoEPI);
		epi.setEmpresa(empresa);
		epi.setAtivo(true);
		epiDao.save(epi);

		Epi epi2 = EpiFactory.getEntity();
		epi2.setTipoEPI(tipoEPI);
		epi2.setEmpresa(empresa);
		epi2.setAtivo(false);
		epiDao.save(epi2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao.setEpis(Arrays.asList(epi));
		historicoFuncao.setData(data1);
		historicoFuncaoDao.save(historicoFuncao);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setFuncao(funcao2);
		historicoFuncao2.setFuncaoNome(funcao2.getNome());
		historicoFuncao2.setEpis(Arrays.asList(epi2));
		historicoFuncao2.setData(data1);
		historicoFuncaoDao.save(historicoFuncao2);
		
		EpiHistorico eh1 = new EpiHistorico();
		eh1.setData(data1);
		eh1.setVencimentoCA(data1);
		eh1.setEpi(epi);
		epiHistoricoDao.save(eh1);

		EpiHistorico eh2 = new EpiHistorico();
		eh2.setData(data2);
		eh2.setVencimentoCA(data2);
		eh2.setEpi(epi);
		epiHistoricoDao.save(eh2);

		EpiHistorico eh3 = new EpiHistorico();
		eh3.setData(data1);
		eh3.setVencimentoCA(data1);
		eh3.setEpi(epi2);
		epiHistoricoDao.save(eh3);

		assertEquals("Todos os EPIs", 2, epiDao.findPriorizandoEpiRelacionado(empresa.getId(), colaborador.getId(), false).size());
		assertEquals("Somente os EPIs ativos", 1, epiDao.findPriorizandoEpiRelacionado(empresa.getId(), colaborador.getId(), true).size());
	}
	
	private void setEmpresa()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
	}
}