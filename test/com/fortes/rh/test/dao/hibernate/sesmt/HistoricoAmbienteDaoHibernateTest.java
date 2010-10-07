package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoAmbienteDaoHibernateTest extends GenericDaoHibernateTest<HistoricoAmbiente>
{
	private HistoricoAmbienteDao historicoAmbienteDao;
	private AmbienteDao ambienteDao;
//	private RiscoAmbienteDao riscoAmbienteDao;
	private RiscoDao riscoDao;

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

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}
}