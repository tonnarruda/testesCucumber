package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoMedicaoRiscoFactory;
import com.ibm.icu.util.Calendar;

public class RiscoMedicaoRiscoDaoHibernateTest extends GenericDaoHibernateTest<RiscoMedicaoRisco>
{
	private RiscoDao riscoDao;
	private RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	private MedicaoRiscoDao medicaoRiscoDao;
	private AmbienteDao ambienteDao;
	private RiscoAmbienteDao riscoAmbienteDao;

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

	public void setRiscoMedicaoRiscoDao(RiscoMedicaoRiscoDao riscoMedicaoRiscoDao)
	{
		this.riscoMedicaoRiscoDao = riscoMedicaoRiscoDao;
	}
	
	public void testRemoveByMedicaoRisco()
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity();
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
		
		assertTrue(riscoMedicaoRiscoDao.removeByMedicaoRisco(medicaoRisco.getId()));
	}
	
	public void testFindByRiscoAteData()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setData(antes.getTime());
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setRisco(risco);
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco.setTecnicaUtilizada("Técnica 1");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
		
		saveDadosMedicao();
		
		assertEquals("Teste: Buscando Riscos medições até hoje" ,1, riscoMedicaoRiscoDao.findByRiscoAteData(risco.getId(), ambiente.getId(), hoje.getTime()).size());
	}
	
	
	public void testFindMedicoesDeRiscosDoAmbiente()
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		ambienteDao.save(ambiente);
		
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
	
	private void saveDadosMedicao() 
	{
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
//		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
//		riscoAmbiente.setRisco(risco);
//		riscoAmbienteDao.save(riscoAmbiente);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setRisco(risco);
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco.setTecnicaUtilizada("Técnica 1");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
	}
	
	public void testFindUltimaAteData()
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
		
		MedicaoRisco resultado = riscoMedicaoRiscoDao.findUltimaAteData(ambiente.getId(), hoje.getTime());
		assertNotNull(resultado);
		assertEquals(resultado.getId(), medicaoRisco.getId());
	}
	
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
		
		MedicaoRisco resultado = riscoMedicaoRiscoDao.findUltimaAteData(ambiente.getId(), antes.getTime());
		assertNull(resultado);
	}
	
	public void setMedicaoRiscoDao(MedicaoRiscoDao medicaoRiscoDao) {
		this.medicaoRiscoDao = medicaoRiscoDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setRiscoAmbienteDao(RiscoAmbienteDao riscoAmbienteDao) {
		this.riscoAmbienteDao = riscoAmbienteDao;
	}
}
