package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class RiscoAmbienteDaoHibernateTest extends GenericDaoHibernateTest<RiscoAmbiente>
{
	private RiscoAmbienteDao riscoAmbienteDao;
	private HistoricoAmbienteDao historicoAmbienteDao;
	private AmbienteDao ambienteDao;
	RiscoDao riscoDao;
	EmpresaDao empresaDao;

	@Override
	public RiscoAmbiente getEntity()
	{
		return RiscoAmbienteFactory.getEntity();
	}

	@Override
	public GenericDao<RiscoAmbiente> getGenericDao()
	{
		return riscoAmbienteDao;
	}

	public void setRiscoAmbienteDao(RiscoAmbienteDao riscoAmbienteDao)
	{
		this.riscoAmbienteDao = riscoAmbienteDao;
	}
	
	public void testRemoveByHistoricoAmbiente()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbienteDao.save(historicoAmbiente);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertTrue(riscoAmbienteDao.removeByHistoricoAmbiente(historicoAmbiente.getId()));
	}
	
	public void testFindRiscosByAmbienteData()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAtras = Calendar.getInstance();
		doisMesesAtras.add(Calendar.MONTH, -2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente.setEmpresa(empresa);
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(doisMesesAtras.getTime());
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbienteAtual = new HistoricoAmbiente();
		historicoAmbienteAtual.setData(hoje);
		historicoAmbienteAtual.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbienteAtual);
		
		Risco risco1 = RiscoFactory.getEntity();
		risco1.setEmpresa(empresa);
		risco1.setDescricao("Calor");
		riscoDao.save(risco1);
		Risco risco2 = RiscoFactory.getEntity();
		risco2.setEmpresa(empresa);
		risco2.setDescricao("Ruído");
		riscoDao.save(risco2);
		
		RiscoAmbiente riscoAmbienteDoisMesesAtras = RiscoAmbienteFactory.getEntity();
		riscoAmbienteDoisMesesAtras.setRisco(risco1);
		riscoAmbienteDoisMesesAtras.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbienteDoisMesesAtras);
		
		RiscoAmbiente riscoAmbienteAtual1 = RiscoAmbienteFactory.getEntity();
		riscoAmbienteAtual1.setRisco(risco1);
		riscoAmbienteAtual1.setHistoricoAmbiente(historicoAmbienteAtual);
		riscoAmbienteDao.save(riscoAmbienteAtual1);
		RiscoAmbiente riscoAmbienteAtual2 = RiscoAmbienteFactory.getEntity();
		riscoAmbienteAtual2.setRisco(risco2);
		riscoAmbienteAtual2.setHistoricoAmbiente(historicoAmbienteAtual);
		riscoAmbienteDao.save(riscoAmbienteAtual2);
		
		Collection<Risco> riscos = riscoAmbienteDao.findRiscosByAmbienteData(ambiente.getId(), doisMesesAtras.getTime());
		
		assertEquals(1, riscos.size());
		assertEquals(risco1, (Risco)riscos.toArray()[0]);
	}
	
	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao) {
		this.historicoAmbienteDao = historicoAmbienteDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
