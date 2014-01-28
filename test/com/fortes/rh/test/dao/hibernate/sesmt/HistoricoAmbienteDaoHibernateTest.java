package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoAmbienteDaoHibernateTest extends GenericDaoHibernateTest<HistoricoAmbiente>
{
	private HistoricoAmbienteDao historicoAmbienteDao;
	private AmbienteDao ambienteDao;
	private RiscoAmbienteDao riscoAmbienteDao;
	private RiscoDao riscoDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;

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

	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao)
	{
		this.historicoAmbienteDao = historicoAmbienteDao;
	}
	
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
	
	public void testFindbyAmbiente()
	{
		Ambiente ambiente = new Ambiente();
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		assertEquals(1,historicoAmbienteDao.findByAmbiente(ambiente.getId()).size());
	}
	
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
	
	@SuppressWarnings("deprecation")
	public void testFindUltimoHistoricoAteData()
	{
		Date data1 = DateUtil.criarAnoMesDia(2009, 01, 10);
		
		Ambiente ambiente = new Ambiente();
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(data1);
		historicoAmbienteDao.save(historicoAmbiente);
		
		assertEquals(historicoAmbiente, historicoAmbienteDao.findUltimoHistoricoAteData(ambiente.getId(), new Date()));
	}
	
	@SuppressWarnings("deprecation")
	public void testFindDadosNoPeriodo()
	{
		Date dataIni = DateUtil.criarAnoMesDia(2009, 01, 10);
		Date dataFim = DateUtil.criarAnoMesDia(2009, 03, 20);
		
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

	public void testFindRiscosAmbientes() throws Exception
	{
		Ambiente a1 = new Ambiente();
		a1.setNome("a1");
		ambienteDao.save(a1);
		
		Ambiente a2 = new Ambiente();
		a2.setNome("a2");
		ambienteDao.save(a2);
		
		Risco risco1 = RiscoFactory.getEntity();
		risco1.setDescricao("Risco 1");
		riscoDao.save(risco1);		

		Risco risco2 = RiscoFactory.getEntity();
		risco1.setDescricao("Risco 2");
		riscoDao.save(risco2);		

		HistoricoAmbiente historico1 = new HistoricoAmbiente();
		historico1.setData(new Date("2007/05/01"));
		historico1.setAmbiente(a1);
		historicoAmbienteDao.save(historico1);
		
		HistoricoAmbiente historico2 = new HistoricoAmbiente();
		historico2.setData(new Date("2008/05/01"));
		historico2.setAmbiente(a1);
		historicoAmbienteDao.save(historico2);
		
		HistoricoAmbiente historico3 = new HistoricoAmbiente();
		historico3.setData(new Date("2007/01/01"));
		historico3.setAmbiente(a2);
		historicoAmbienteDao.save(historico3);
		
		HistoricoAmbiente historico4 = new HistoricoAmbiente();
		historico4.setData(new Date("2008/01/01"));
		historico4.setAmbiente(a2);
		historicoAmbienteDao.save(historico4);
		
		RiscoAmbiente riscoAmbiente1 = RiscoAmbienteFactory.getEntity();
		riscoAmbiente1.setRisco(risco1);
		riscoAmbiente1.setHistoricoAmbiente(historico2);
		riscoAmbienteDao.save(riscoAmbiente1);

		RiscoAmbiente riscoAmbiente2 = RiscoAmbienteFactory.getEntity();
		riscoAmbiente2.setRisco(risco2);
		riscoAmbiente2.setHistoricoAmbiente(historico4);
		riscoAmbienteDao.save(riscoAmbiente2);
		
		Collection<Long> ambientesIds = new ArrayList<Long>();
		ambientesIds.add(a1.getId());
		ambientesIds.add(a2.getId());
		
		Collection<HistoricoAmbiente> historicoAmbientes = historicoAmbienteDao.findRiscosAmbientes(ambientesIds, new Date("2008/01/01"));
		Collection<HistoricoAmbiente> historicosVazios = historicoAmbienteDao.findRiscosAmbientes(null, new Date("2008/01/01"));
		
		assertEquals("Test 1", 1, historicoAmbientes.size());
		assertNull("Test 2", historicosVazios);
	}
	
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

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}

	public void setRiscoAmbienteDao(RiscoAmbienteDao riscoAmbienteDao) {
		this.riscoAmbienteDao = riscoAmbienteDao;
	}

	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	
	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}
}