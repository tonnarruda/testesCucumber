package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoMedicaoRiscoFactory;
import com.ibm.icu.util.Calendar;

public class RiscoMedicaoRiscoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<RiscoMedicaoRisco>
{
	@Autowired private RiscoDao riscoDao;
	@Autowired private RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	@Autowired private MedicaoRiscoDao medicaoRiscoDao;
	@Autowired private AmbienteDao ambienteDao;
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private HistoricoFuncaoDao historicoFuncaoDao;
	@Autowired private HistoricoAmbienteDao historicoAmbienteDao;

	@Override
	public RiscoMedicaoRisco getEntity()
	{
		return RiscoMedicaoRiscoFactory.getEntity();
	}

	@Override
	public GenericDao<RiscoMedicaoRisco> getGenericDao()
	{
		return riscoMedicaoRiscoDao;
	}
	
	@Test
	public void testRemoveByMedicaoRisco()
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity();
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
		
		assertTrue(riscoMedicaoRiscoDao.removeByMedicaoRisco(medicaoRisco.getId()));
	}
	
	@Test
	public void testFindByRiscoAteData()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		MedicaoRisco medicaoRiscoAmbiente = MedicaoRiscoFactory.getEntity();
		medicaoRiscoAmbiente.setData(antes.getTime());
		medicaoRiscoAmbiente.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRiscoAmbiente);

		MedicaoRisco medicaoRiscoFuncao = MedicaoRiscoFactory.getEntity();
		medicaoRiscoFuncao.setData(antes.getTime());
		medicaoRiscoFuncao.setFuncao(funcao);
		medicaoRiscoDao.save(medicaoRiscoFuncao);
		
		RiscoMedicaoRisco riscoMedicaoRiscoAmbiente = new RiscoMedicaoRisco();
		riscoMedicaoRiscoAmbiente.setRisco(risco);
		riscoMedicaoRiscoAmbiente.setMedicaoRisco(medicaoRiscoAmbiente);
		riscoMedicaoRiscoAmbiente.setTecnicaUtilizada("Técnica 1 Ambiente");
		riscoMedicaoRiscoDao.save(riscoMedicaoRiscoAmbiente);
		
		RiscoMedicaoRisco riscoMedicaoRiscoFuncao = new RiscoMedicaoRisco();
		riscoMedicaoRiscoFuncao.setRisco(risco);
		riscoMedicaoRiscoFuncao.setMedicaoRisco(medicaoRiscoFuncao);
		riscoMedicaoRiscoFuncao.setTecnicaUtilizada("Técnica 1 Função");
		riscoMedicaoRiscoDao.save(riscoMedicaoRiscoFuncao);
		
		saveDadosMedicao();
		
		assertEquals("Teste: Buscando Riscos medições do Ambiente até hoje" ,1, riscoMedicaoRiscoDao.findByRiscoAteData(risco.getId(), ambiente.getId(), hoje.getTime(), true).size());
		assertEquals("Teste: Buscando Riscos medições da Função até hoje" ,1, riscoMedicaoRiscoDao.findByRiscoAteData(risco.getId(), funcao.getId(), hoje.getTime(), false).size());
	}
	
	@Test
	public void testFindMedicoesDeRiscosDoAmbiente()
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbienteDao.save(historicoAmbiente);
		
		Risco risco = RiscoFactory.getEntity(1L);
		riscoDao.save(risco);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setData(new Date());
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity(1L);
		riscoMedicaoRisco.setRisco(risco);
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);

		RiscoMedicaoRisco riscoMedicaoRisco2 = RiscoMedicaoRiscoFactory.getEntity(2L);
		riscoMedicaoRisco2.setRisco(risco);
		riscoMedicaoRisco2.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco2);
		
		assertEquals(2, riscoMedicaoRiscoDao.findMedicoesDeRiscosDoAmbiente(ambiente.getId(), new Date()).size());
	}
	
	@Test
	public void testFindMedicoesDeRiscosDaFuncao()
	{
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setFuncaoNome(funcao.getNome());
		historicoFuncao.setData(new Date());
		historicoFuncaoDao.save(historicoFuncao);
		
		Risco risco = RiscoFactory.getEntity(1L);
		riscoDao.save(risco);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setData(new Date());
		medicaoRisco.setFuncao(funcao);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity(1L);
		riscoMedicaoRisco.setRisco(risco);
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco2 = RiscoMedicaoRiscoFactory.getEntity(2L);
		riscoMedicaoRisco2.setRisco(risco);
		riscoMedicaoRisco2.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco2);
		
		assertEquals(2, riscoMedicaoRiscoDao.findMedicoesDeRiscosDaFuncao(funcao.getId(), new Date()).size());
	}
	
	private void saveDadosMedicao() 
	{
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setRisco(risco);
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco.setTecnicaUtilizada("Técnica 1");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
	}
	
	@Test
	public void testFindUltimaAteDataAmbiente()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setData(antes.getTime());
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		MedicaoRisco resultado = riscoMedicaoRiscoDao.findUltimaAteData(ambiente.getId(), hoje.getTime(), true);
		assertNotNull(resultado);
		assertEquals(resultado.getId(), medicaoRisco.getId());
	}
	
	@Test
	public void testFindUltimaAteDataFuncao()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setData(antes.getTime());
		medicaoRisco.setFuncao(funcao);
		medicaoRiscoDao.save(medicaoRisco);
		
		MedicaoRisco resultado = riscoMedicaoRiscoDao.findUltimaAteData(funcao.getId(), hoje.getTime(), false);
		assertNotNull(resultado);
		assertEquals(resultado.getId(), medicaoRisco.getId());
	}
	
	@Test
	public void testFindUltimaAteDataSemResultado()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setData(hoje.getTime());
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		MedicaoRisco resultado = riscoMedicaoRiscoDao.findUltimaAteData(ambiente.getId(), antes.getTime(), true);
		assertNull(resultado);
	}
}
