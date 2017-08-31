package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoAmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoAmbienteDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<HistoricoAmbiente>
{
	@Autowired private HistoricoAmbienteDao historicoAmbienteDao;
	@Autowired private AmbienteDao ambienteDao;
	@Autowired private RiscoAmbienteDao riscoAmbienteDao;
	@Autowired private RiscoDao riscoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private EstabelecimentoDao estabelecimentoDao;

	public HistoricoAmbiente getEntity()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();

		historicoAmbiente.setId(1L);
		historicoAmbiente.setDescricao("asd");
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDataInativo(new Date());

		return historicoAmbiente;
	}

	public GenericDao<HistoricoAmbiente> getGenericDao()
	{
		return historicoAmbienteDao;
	}

	@Test
	public void testRemoveByAmbiente()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbienteDao.save(historicoAmbiente);
		
		Collection<HistoricoAmbiente> historicoAmbientes = new ArrayList<HistoricoAmbiente>();
		historicoAmbientes.add(historicoAmbiente);
		
		Ambiente ambiente = new Ambiente();
		ambiente.setHistoricoAmbientes(historicoAmbientes);
		ambienteDao.save(ambiente);
		
		historicoAmbienteDao.removeByAmbiente(ambiente.getId());
	}
	
	@Test
	public void testFindbyAmbiente()
	{
		Ambiente ambiente = new Ambiente();
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		assertEquals(1,historicoAmbienteDao.findByAmbiente(ambiente.getId()).size());
	}
	
	@Test
	public void testFindUltimoHistorico()
	{
		Ambiente ambiente = new Ambiente();
		ambienteDao.save(ambiente);
		
		Date hoje = Calendar.getInstance().getTime();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(antes.getTime());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = new HistoricoAmbiente();
		historicoAmbiente2.setAmbiente(ambiente);
		historicoAmbiente2.setData(hoje);
		historicoAmbienteDao.save(historicoAmbiente2);
		
		HistoricoAmbiente resultado = historicoAmbienteDao.findUltimoHistorico(ambiente.getId());
		
		assertEquals(hoje, resultado.getData());
	}
	
	@Test
	public void testFindUltimoHistoricoAteData()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 10, 2009);
		
		Ambiente ambiente = new Ambiente();
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(data1);
		historicoAmbienteDao.save(historicoAmbiente);
		
		assertEquals(ambiente.getId(), historicoAmbienteDao.findUltimoHistoricoAteData(ambiente.getId(), new Date()).getAmbiente().getId());
	}
	
	@Test
	public void testFindDadosNoPeriodo()
	{
		Date dataIni = DateUtil.criarDataMesAno(01, 10, 2009);
		Date dataFim = DateUtil.criarDataMesAno(03, 20, 2009);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(dataIni);
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		
		historicoAmbienteDao.findDadosNoPeriodo(ambiente.getId(), dataIni, dataFim);
	}

	@Test
	public void testFindRiscosAmbientes() throws Exception
	{
		Ambiente a1 = AmbienteFactory.getEntity("A1", null, null);
		ambienteDao.save(a1);
		
		Ambiente a2 = AmbienteFactory.getEntity("A2", null, null);
		ambienteDao.save(a2);
		
		Risco risco1 = RiscoFactory.getEntity(null, "Risco 1", null);
		riscoDao.save(risco1);		

		Risco risco2 = RiscoFactory.getEntity(null, "Risco 2", null);
		riscoDao.save(risco2);		

		HistoricoAmbiente historico1 = HistoricoAmbienteFactory.getEntity(a1, DateUtil.criarDataDiaMesAno("01/05/2007"));
		historicoAmbienteDao.save(historico1);
		
		HistoricoAmbiente historico2 = HistoricoAmbienteFactory.getEntity(a1, DateUtil.criarDataDiaMesAno("01/05/2008"));
		historicoAmbienteDao.save(historico2);
		
		HistoricoAmbiente historico3 = HistoricoAmbienteFactory.getEntity(a2, DateUtil.criarDataDiaMesAno("01/01/2007"));
		historicoAmbienteDao.save(historico3);
		
		HistoricoAmbiente historico4 = HistoricoAmbienteFactory.getEntity(a2, DateUtil.criarDataDiaMesAno("01/01/2008"));
		historicoAmbienteDao.save(historico4);
		
		RiscoAmbiente riscoAmbiente1 = RiscoAmbienteFactory.getEntity(risco1, historico2, null);
		riscoAmbienteDao.save(riscoAmbiente1);

		RiscoAmbiente riscoAmbiente2 = RiscoAmbienteFactory.getEntity(risco2, historico4, null);
		riscoAmbienteDao.save(riscoAmbiente2);
		
		Collection<Long> ambientesIds = Arrays.asList(a1.getId(), a2.getId());
		
		Collection<HistoricoAmbiente> historicoAmbientes = historicoAmbienteDao.findRiscosAmbientes(ambientesIds, DateUtil.criarDataDiaMesAno("01/01/2008"));
		Collection<HistoricoAmbiente> historicosVazios = historicoAmbienteDao.findRiscosAmbientes(null, DateUtil.criarDataDiaMesAno("01/01/2008"));
		
		assertEquals("Test 1", 1, historicoAmbientes.size());
		assertNull("Test 2", historicosVazios);
	}
	
	@Test
	public void testFindByData() throws Exception
	{
		Date hoje = DateUtil.criarDataMesAno(1, 1, 2000);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente.setEmpresa(empresa);
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente1 = new HistoricoAmbiente();
		historicoAmbiente1.setData(hoje);
		historicoAmbiente1.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente1);
		
		assertEquals("Inserção", historicoAmbiente1.getId(), historicoAmbienteDao.findByData(hoje, null, ambiente.getId()).getId());
		assertEquals("Atualização própria", null, historicoAmbienteDao.findByData(hoje, historicoAmbiente1.getId(), historicoAmbiente1.getAmbiente().getId()));
		assertEquals("Atualização outra", historicoAmbiente1.getId(), historicoAmbienteDao.findByData(hoje, 0L, historicoAmbiente1.getAmbiente().getId()).getId());
	}
}